package cn.com.afcat.shcpemonitor.common.cache.redis.routing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/**
 * redis路由管理
 * 读取路由配置文件 初始化配置中的路由节点信息
 */

public class RedisRoutingManager {
	/**
	 * 路由节点集合
	 */
	private Map<String,RedisCluster> redisClusterMap = new HashMap<String, RedisCluster>();
	/**
	 * redis连接池集合
	 */
	private HashMap<String,JedisPool> redisPoolMap = new HashMap<String,JedisPool>();
	
	
	private static class LazyHolder{
		private static final RedisRoutingManager INSTANCE = new RedisRoutingManager();
	}
	
	private RedisRoutingManager(){
		try {
			initDbRouting();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static RedisRoutingManager getInstance(){
		return LazyHolder.INSTANCE;
	}
	
	/**
	 * 初始化配置文件信息
	 * @throws ConfigurationException 
	 * @throws org.apache.commons.configuration.ConfigurationException 
	 * 
	 */
	private void initDbRouting() throws Exception{
		HashMap<String,JedisPool> redisConnectionMap = new HashMap<String,JedisPool>();
		XMLConfiguration routingConfig = new XMLConfiguration("redis/redisrouting.xml");
		List<Object> serverNodesList = routingConfig.getList("servernode.node.id");
		for(int clusterIndex=0;clusterIndex<serverNodesList.size();clusterIndex++){
			String nodeId = (String)serverNodesList.get(clusterIndex);
			int maxActive = routingConfig.getInt("servernode.node("+clusterIndex+").maxActive",20);
			int maxIdle = routingConfig.getInt("servernode.node("+clusterIndex+").maxIdle",20);
			int maxWait = routingConfig.getInt("servernode.node("+clusterIndex+").maxWait",20);
			String host = routingConfig.getString("servernode.node("+clusterIndex+").host");
			int port = routingConfig.getInt("servernode.node("+clusterIndex+").port");
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(maxActive);
//			config.setMaxActive(maxActive);
			config.setMaxIdle(maxIdle);
			config.setMaxWaitMillis(maxWait);
//			config.setMaxWait(maxWait);
			config.setTestOnBorrow(false);
			JedisPool pool = new JedisPool(config, host,port);
			redisConnectionMap.put(nodeId, pool);
		}
		this.redisPoolMap = redisConnectionMap;
		List<Object> clusterList = routingConfig.getList("clusternode.cnode.id");
		Map<String,RedisCluster> clusters = new HashMap<String,RedisCluster>();
		for(int clusterIndex=0;clusterIndex<clusterList.size();clusterIndex++){
			String clusterId = (String)clusterList.get(clusterIndex);
			String writerstrategy = routingConfig.getString("clusternode.cnode("+clusterIndex+").writerstrategy");
			String readstrategy = routingConfig.getString("clusternode.cnode("+clusterIndex+").readstrategy");
			String readservernodes = routingConfig.getString("clusternode.cnode("+clusterIndex+").readservernodes");
			String writeservernodes = routingConfig.getString("clusternode.cnode("+clusterIndex+").writeservernodes");
			RedisCluster cluster = new RedisCluster(clusterId,readstrategy,writerstrategy,redisConnectionMap);
			if(StringUtils.isNotBlank(readservernodes)){
				String[] nodes = readservernodes.split(":");
				for(int i = 0 ; i < nodes.length ; i++){
					String node = nodes[i];
					if(redisPoolMap.get(node) == null){
						
					}else{
						cluster.addReadRedisNode(node);	
					}
				}
			}
			if(StringUtils.isNotBlank(writeservernodes)){
				String[] nodes = writeservernodes.split(":");
				for(int i = 0 ; i < nodes.length ; i++){
					String node = nodes[i];
					if(redisPoolMap.get(node) == null){
						
					}else{
						cluster.addWriteRedisNode(node);	
					}
				}
			}
			clusters.put(clusterId,cluster);
		}
		//读取参数完毕
		//开始初始化连接池
//		for(RedisCluster cluster:clusters){
//			cluster.setNodeToLive();
//		}
		this.redisClusterMap = clusters;
	}
	/**
	 * 返回节点对应的连接池
	 * @param node
	 * @return
	 */
	protected JedisPool getJedisPool(String node){
		JedisPool pool = redisPoolMap.get(node);
		return pool;
	}
	/**
	 * 返回路由节点
	 * @param clusterId
	 * @return
	 */
	protected RedisCluster getRedisCluster(String clusterId){
		return redisClusterMap.get(clusterId);
	}

}
