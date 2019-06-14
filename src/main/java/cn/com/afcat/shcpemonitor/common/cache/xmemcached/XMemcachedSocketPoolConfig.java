package cn.com.afcat.shcpemonitor.common.cache.xmemcached;

public class XMemcachedSocketPoolConfig {
	private String name;
	private boolean failover;
	private int initConn;
	private int minConn;
	private int maxConn;
	private int maintSleep;
	private boolean nagle;
	private int socketTo;
	private boolean aliveCheck;
	private int maxIdle;
	private String servers;
	private String weights;

	public XMemcachedSocketPoolConfig()
	  {
	    this.failover = true;
	    this.initConn = 10;
	    this.minConn = 5;
	    this.maxConn = 250;

	    this.maintSleep = 3000;
	    this.nagle = false;

	    this.socketTo = 3000;

	    this.aliveCheck = true;

	    this.maxIdle = 3000;
	  }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isFailover() {
		return failover;
	}

	public void setFailover(boolean failover) {
		this.failover = failover;
	}

	public int getInitConn() {
		return initConn;
	}

	public void setInitConn(int initConn) {
		this.initConn = initConn;
	}

	public int getMinConn() {
		return minConn;
	}

	public void setMinConn(int minConn) {
		this.minConn = minConn;
	}

	public int getMaxConn() {
		return maxConn;
	}

	public void setMaxConn(int maxConn) {
		this.maxConn = maxConn;
	}

	public int getMaintSleep() {
		return maintSleep;
	}

	public void setMaintSleep(int maintSleep) {
		this.maintSleep = maintSleep;
	}

	public boolean isNagle() {
		return nagle;
	}

	public void setNagle(boolean nagle) {
		this.nagle = nagle;
	}

	public int getSocketTo() {
		return socketTo;
	}

	public void setSocketTo(int socketTo) {
		this.socketTo = socketTo;
	}

	public boolean isAliveCheck() {
		return aliveCheck;
	}

	public void setAliveCheck(boolean aliveCheck) {
		this.aliveCheck = aliveCheck;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public String getServers() {
		return servers;
	}

	public void setServers(String servers) {
		this.servers = servers;
	}

	public String getWeights() {
		return weights;
	}

	public void setWeights(String weights) {
		this.weights = weights;
	}
}
