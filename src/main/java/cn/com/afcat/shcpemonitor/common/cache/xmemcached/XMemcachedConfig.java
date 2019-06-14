package cn.com.afcat.shcpemonitor.common.cache.xmemcached;

public class XMemcachedConfig {
	private String name;
	private boolean compressEnable;
	private String defaultEncoding;
	private String errorHandler;
	private String socketPool;
	private long opTimeout;
	
	public long getOpTimeout() {
		return opTimeout;
	}
	public void setOpTimeout(long opTimeout) {
		this.opTimeout = opTimeout;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isCompressEnable() {
		return compressEnable;
	}
	public void setCompressEnable(boolean compressEnable) {
		this.compressEnable = compressEnable;
	}
	public String getDefaultEncoding() {
		return defaultEncoding;
	}
	public void setDefaultEncoding(String defaultEncoding) {
		this.defaultEncoding = defaultEncoding;
	}
	public String getErrorHandler() {
		return errorHandler;
	}
	public void setErrorHandler(String errorHandler) {
		this.errorHandler = errorHandler;
	}
	public String getSocketPool() {
		return socketPool;
	}
	public void setSocketPool(String socketPool) {
		this.socketPool = socketPool;
	}
}
