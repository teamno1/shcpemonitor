package cn.com.afcat.shcpemonitor.common.cache.redis.shard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;
/**
 * redis切片集群管理
 */
public class ShardJedisManager {
	
	private static class LazyHolder{
		private static final ShardJedisManager INSTANCE = new ShardJedisManager();
	}
	private Map<String,ShardedJedisPool> shardedJedisPoolMap = new HashMap<String, ShardedJedisPool>();
	
	private ShardJedisManager(){
		try {
			initShardJedis();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public static ShardJedisManager getInstance(){
		return LazyHolder.INSTANCE;
	}
	/**
	 * 初始化切片集群
	 * @throws ConfigurationException
	 */
	private void initShardJedis() throws ConfigurationException{
		HashMap<String,ShardedJedisPool> redisConnectionMap = new HashMap<String,ShardedJedisPool>();
		XMLConfiguration routingConfig = new XMLConfiguration("redis/shardconfig.xml");
		List<Object> serverNodesList = routingConfig.getList("servernode.node.id");
		for(int clusterIndex=0;clusterIndex<serverNodesList.size();clusterIndex++){
			String nodeId = (String)serverNodesList.get(clusterIndex);
			//最大活动连接
			int maxActive = routingConfig.getInt("servernode.node("+clusterIndex+").maxActive",20);
			//最大
			int maxIdle = routingConfig.getInt("servernode.node("+clusterIndex+").maxIdle",20);
			//最长等待时间
			int maxWait = routingConfig.getInt("servernode.node("+clusterIndex+").maxWait",20);
			String hosts = routingConfig.getString("servernode.node("+clusterIndex+").hosts");
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(maxActive);
//			config.setMaxActive(maxActive);
			config.setMaxIdle(maxIdle);
			config.setMaxWaitMillis(maxWait);
//			config.setMaxWait(maxWait);
			config.setTestOnBorrow(false);
			if (hosts == null) {
				throw new ConfigurationException("RedisPool init():hosts config error!");
			} else {
				List<JedisShardInfo> jedisShardInfos= new ArrayList<JedisShardInfo>();
				String[] hoststr = hosts.split("#");
				JedisShardInfo jsi;
				String[] hostarrt = null;
				for (String host : hoststr) {
					hostarrt = host.split(":");
					jsi = new JedisShardInfo(hostarrt[0].trim(), Integer.parseInt(hostarrt[1].trim()));
					jedisShardInfos.add(jsi);
				}
				ShardedJedisPool pool = new ShardedJedisPool(config, jedisShardInfos);
				redisConnectionMap.put(nodeId, pool);
			}
		}
		this.shardedJedisPoolMap = redisConnectionMap;
	}
	/**
	 * 获取某个节点的切片集群
	 * @param nodeId
	 * @return 切片集群池
	 */
	protected ShardedJedisPool getShardedJedisPool(String nodeId){
		return shardedJedisPoolMap.get(nodeId);
	}
	

}
