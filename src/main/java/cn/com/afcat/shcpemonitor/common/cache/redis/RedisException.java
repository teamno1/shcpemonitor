package cn.com.afcat.shcpemonitor.common.cache.redis;

/**
 * redis路由异常类
 */
public class RedisException extends RuntimeException {
	/**
	 * 字段或域定义：<code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -5034463949539478898L;
	
	public RedisException(){
		super();
	}
	
	public RedisException(String msg){
		super(msg);
	}
}
