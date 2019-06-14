package cn.com.afcat.shcpemonitor.common.cache.redis.routing;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * redis路由监控节点
 */
@SuppressWarnings("static-access")
public class RedisClusterNode  {
	private String nodeId;
	private RedisCluster cluster;
	
	public RedisClusterNode(String nodeId, RedisCluster cluster) {
		this.nodeId = nodeId;
		this.cluster = cluster;
	}
	
    /**
     * 检查节点的状态
     * @return 如果节点正常，返回true,否则，返回false
     */
	public boolean checkStatus() {
		JedisPool pool = RedisRoutingManager.getInstance().getJedisPool(nodeId);
		boolean isConnection = false;
		Jedis jedis = null;
		try{
			jedis = pool.getResource();
			if(jedis.isConnected() && jedis.ping().equals("PONG")){
				isConnection = true;
			}
		}catch(JedisConnectionException e){
			if(jedis == null){
				try {
					Thread.currentThread().sleep(30*1000);//等待30秒钟
				} catch (InterruptedException ie1) {
					ie1.printStackTrace();
				}
				try{
					jedis = pool.getResource();
					if(jedis.isConnected() && jedis.ping().equals("PONG")){
						isConnection = true;
					}
				}catch(JedisConnectionException e1){
					
				}
			}
		}finally{
			if(jedis != null){
				pool.returnResource(jedis);
			}
		}
		return isConnection;
	}

	/**
	 * 
	 * Description : 获取节点ID <BR>   
	 * @return
	 */
	public String getNodeId() {
		return nodeId;
	}
	
    /**
     * Description :错误节点的处理 <BR>
     */
	public void handleFault() {
		try {
			cluster.removeReadRedisNode(nodeId);
			//alarmNotice.all();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    /**
     * 处理节点的恢复
     */
	public void resumeFault() {
		try {
//			cluster.addNode(nodeId);
//			alarmNotice.all();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		return nodeId;
	}
}
