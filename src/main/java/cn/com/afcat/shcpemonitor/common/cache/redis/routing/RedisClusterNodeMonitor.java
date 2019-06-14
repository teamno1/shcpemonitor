package cn.com.afcat.shcpemonitor.common.cache.redis.routing;

import java.util.ArrayList;

/**
 * redis路由监控控制类
 */
public class RedisClusterNodeMonitor implements Runnable {
	private long interval = 60 * 1000;
	private Thread thread;
	private boolean alive = true;
	private ArrayList<RedisClusterNode> redisClusterNodes = new ArrayList<RedisClusterNode>(5);
	private ArrayList<RedisClusterNode> badNodes = new ArrayList<RedisClusterNode>();

	public RedisClusterNodeMonitor(){ 
	}
	/**
	 * 设置监控间隔
	 * @param interval
	 */
	public void setInterval(long interval) {
		this.interval = interval;
	}

	public void start(boolean daemon) {
		thread = new Thread(this);
		thread.setDaemon(daemon);
		thread.start();
	}
	/**
	 * 添加监控节点
	 * @param RedisClusterNode 监控节点
	 */
	public void addRedisClusterNode(RedisClusterNode redisClusterNodes) {
		if(redisClusterNodes==null) return;
		this.redisClusterNodes.add(redisClusterNodes);
	}
	/**
	 * 获取所有活动的监控节点
	 * @return 返回所有活动的节点集合
	 */
	public ArrayList<RedisClusterNode> getActiveNodes() {
		return this.redisClusterNodes;
	}

	public void run() {
		if (interval <= 0)
			throw new IllegalArgumentException("interval must be >0");
		while (alive) {
			sleep();
			forceDetectingBadNode(); // 先检查错误的节点是否恢复
			forceDetectingNode(); // 再检查现有的节点是否正常
		}
	}
	
	/**
	 * 让线程手工停止
	 */
	public void setStop() {
		alive = false;
		thread = null;
	}
	
	private void sleep() {
		try {
			Thread.sleep(interval);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 处理坏的节点
	 */
	private void forceDetectingBadNode() {
		if (badNodes.size() > 0) { // 如果有以前的坏节点
			ArrayList<RedisClusterNode> resumeNodes = new ArrayList<RedisClusterNode>(badNodes.size());
			for (int i = 0; i < badNodes.size(); i++) {
				RedisClusterNode node = badNodes.get(i);
				boolean result = node.checkStatus();
				try {
					if (result) { // 如果发现状态正常，那么假如到恢复节点中
						resumeNodes.add(node);
					}
				} catch (Exception ex) {
				}
			}
			if (resumeNodes.size() > 0) { // 如果有恢复的节点，那么自动加入进去
				for (RedisClusterNode rNode : resumeNodes) {
					badNodes.remove(rNode);
				}
				synchronized (redisClusterNodes) {
					redisClusterNodes.addAll(resumeNodes);
				}
				handleResumeNodeChanged(resumeNodes);
			}

		}
	}
	
	/**
	 * 检查节点的状态
	 */
	private void forceDetectingNode() {
		int threadCount = redisClusterNodes.size();
		if(threadCount==0) return;
		ArrayList<RedisClusterNode> activeNodes = new ArrayList<RedisClusterNode>();
		ArrayList<RedisClusterNode> faultNodes = new ArrayList<RedisClusterNode>();
		for (int i = 0; i < threadCount; i++) {
			RedisClusterNode node = redisClusterNodes.get(i);
			boolean result = node.checkStatus();
			try {
			System.out.println("node....."+node.getNodeId()+",checkstatus="+result);
				if (result) {
					activeNodes.add(node);
				} else {
					faultNodes.add(node);
				}
			} catch (Exception ex) {
				faultNodes.add(node);
			}
		}
		if (faultNodes.size() > 0) { // 如果检查到错误的节点有变更，执行handleNodeChanged()方法
			synchronized (redisClusterNodes) {
				redisClusterNodes.clear();
				redisClusterNodes.addAll(activeNodes);
				badNodes.addAll(faultNodes);
				System.out.println("Cluster Node List" + redisClusterNodes);
				System.out.println("bad Node List" + badNodes);
				handleFaultNodeChanged(faultNodes);
			}
		}
	}
	
	/**
	 * 处理所有坏的节点
	 * @param faultNodes 所有坏的节点
	 */
	private void handleFaultNodeChanged(ArrayList<RedisClusterNode> faultNodes) {
		for (RedisClusterNode node : faultNodes) {
			node.handleFault();
		}
	}
	
	/**
	 * 处理节点如何恢复
	 * @param resumeNodes 需要恢复的节点
	 */
	private void handleResumeNodeChanged(ArrayList<RedisClusterNode> resumeNodes) {
		for (RedisClusterNode node : resumeNodes) {
			node.resumeFault();
		}
	}

}
