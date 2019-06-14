package cn.com.afcat.shcpemonitor.common.cache.redis.routing;

import cn.com.afcat.shcpemonitor.common.cache.redis.ConsistentHash;
import cn.com.afcat.shcpemonitor.common.cache.redis.RedisException;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 * redis路由节点
 * 根据不同的策略分别提供读连接 和写连接
 * 后期实现redis节点监控 现阶段还没有实现
 */
public class RedisCluster {
	/**
	 * Cluster节点ID，这个是系统集群的唯一标识，
		在配置文件中设置，不允许重复，可以通过这个参数获取相关集群的连接
	*/
	private String clusterId; 
	
	
	/**
	 * redis集群读策略，包括最小连接、随机数、最高性能、一致性Hash策略
	 */
	String readClusterStrategy;
	
	/**
	 * 写策略(多写(复写)、尾号写策略、一致性Hash策略)
	 */
	String writeClusterStrategy;
	
	
	/**
	 * 读连接节点
	 */
	private List<String> readRedisNodeList = new ArrayList<String>();
	
	
	/**
	 * 写连接节点集合
	 */
	private List<String> writeRedisNodeList = new ArrayList<String>();
	
	/**
	 * 初始化读节点一致性Hash算法
	 */
	private ConsistentHash<String> writeHash = null;
	
	/**
	 * 初始化写节点一致性Hash算法
	 */
	private ConsistentHash<String> readHash = null;
	
	/**
	 * 定义节点的监控，这个节点监控可以监控读集群、写集群等
	 */
	//private final RedisClusterNodeMonitor monitor;
	private Map<String,JedisPool> redisPoolMap;
	
	
	public RedisCluster(String clusterId, String readClusterStrategy,
			String writeClusterStrategy,Map<String,JedisPool> redisPoolMap) {
		this.clusterId = clusterId;
		this.redisPoolMap = redisPoolMap;
		if (readClusterStrategy != null) {
			this.readClusterStrategy = readClusterStrategy;
		} else {
			this.readClusterStrategy = RedisRoutingConstants.CLUSTER_STRATEGY_MAXPERFORMANCE; //如果没有设置，默认为最高性能
		}
		
		if (writeClusterStrategy != null) {
			this.writeClusterStrategy = writeClusterStrategy;
		} else {
			this.writeClusterStrategy = RedisRoutingConstants.CLUSTER_STRATEGY_MAXPERFORMANCE; //如果没有设置，默认为最高性能
		}
//		monitor = new RedisClusterNodeMonitor();
//		monitor.start(true);
	}
	
	
	public void setNodeToLive() {
		
		
//		RedisClusterNode clusterNode = this.createClusterNode(nodeId,
//				this);
//		monitor.addClusterNode(clusterNode);
		
	}

	/**
	 * 获取读连接池
	 * 根据不同的策略获取读连接
	 * 先支持的策略为随机获取 hash获取
	 * RedisCluster.getReadRedisPool()<BR>
	 * @param token
	 * @return
	 */
	public JedisPool getReadRedisPool(String token){
		if (RedisRoutingConstants.CLUSTER_STRATEGY_RANDOM
				.equals(this.readClusterStrategy)) {
			// 如果读库是随机选择,使用随时数算法
			int nodeSize = readRedisNodeList.size();
			int index = (int) (Math.random() * nodeSize);
			String node =  readRedisNodeList.get(index);
			return getReadPool(node);
		} else if(RedisRoutingConstants.CLUSTER_STRATEGY_HASHREAD.equals(this.readClusterStrategy)){// 如果是Hash算法，这里采用的一致性Hash
			if (readHash == null) {// 初始化一致性Hash算法，只初始化一次
				readHash = new ConsistentHash<String>(readRedisNodeList);
			}
			String node =  readHash.get(token);
			return getReadPool(node);
		}else{
			throw new RedisException(
					"the stategy:"
							+ readClusterStrategy
							+ " can not support,please set the  readClusterStrategy in redisrouting.xml correct");
		}
	}
	private JedisPool getReadPool(String nodeId) {
		if (nodeId == null)
			return null;
		JedisPool JedisPool = redisPoolMap.get(nodeId);
		return JedisPool;
	}
	/**
	 * 获取写连接池 (写连接池可能包含多个)
	 * 根据不同的策略获取写连接池
	 * 现支持hash策略  复写策略
	 * @param token
	 * @return
	 */
	public List<JedisPool> getWriteRedisPool(String token)throws RedisException{
		if(RedisRoutingConstants.CLUSTER_STRATEGY_HASHWRITE.equals(this.writeClusterStrategy)){
			if (writeHash == null) {// 初始化一致性Hash算法，只初始化一次
				writeHash = new ConsistentHash<String>(writeRedisNodeList);
			}
			String nodeId = writeHash.get(token);
			List<JedisPool> redisConnectionList = new ArrayList<JedisPool>();
			JedisPool JedisPool = redisPoolMap.get(nodeId);
			redisConnectionList.add(JedisPool);
			return redisConnectionList;
		}else if(RedisRoutingConstants.CLUSTER_STRATEGY_MUTIWRITE.equals(this.writeClusterStrategy)){
			List<JedisPool> redisConnectionList = new ArrayList<JedisPool>();
			for(String nodeId : writeRedisNodeList){
				JedisPool JedisPool = redisPoolMap.get(nodeId);
				redisConnectionList.add(JedisPool);
			}
			return redisConnectionList;
//		}else if(){
//			// 如果是尾号读写
//			if (token.length() < 2)
//				throw new SQLException("illegal token:" + token
//						+ ",maybe occur error");
//			String lastTwoNumber = token.substring(token.length() - 2);
//			String tokenNode = null;
//			for (int i = 0; i < nodes.size(); i++) {
//				Map<String, Integer> trailNumberHash = nodeTrailNumber.get(nodes.get(i));
//				if (trailNumberHash != null && trailNumberHash.size() > 0) {
//					if (trailNumberHash.containsKey(lastTwoNumber)) {
//						tokenNode = nodes.get(i);
//						break;
//					}
//				}
//			}
//		
//			return getConnectionFromPool(tokenNode);
		}else{
			throw new RedisException(
					"the stategy:"
							+ writeClusterStrategy
							+ " can not support,please set the  writeClusterStrategy in redisrouting.xml correct");
		}
	}
	/**
	 * 增加读节点
	 * RedisCluster.addReadRedisNode()<BR>
	 * @param node
	 */
	protected void addReadRedisNode(String node){
		readRedisNodeList.add(node);
	}
	/**
	 * 增加写节点
	 * @param node
	 */
	protected void addWriteRedisNode(String node){
		writeRedisNodeList.add(node);
	}
	/**
	 * 移除读节点
	 * @param node
	 */
	protected void removeReadRedisNode(String node){
		boolean isSuc = readRedisNodeList.remove(node);
		if(isSuc){
			this.readHash = null;
		}
	}
	/**
	 * 移除写节点
	 * @param node
	 */
	protected void removeWriteRedisNode(String node){
		boolean isSuc = writeRedisNodeList.remove(node);
		if(isSuc){
			this.writeHash = null;
		}
	}
	/**
	 * 获取节点ID
	 * @return
	 */
	public String getClusterId() {
		return clusterId;
	}

}
