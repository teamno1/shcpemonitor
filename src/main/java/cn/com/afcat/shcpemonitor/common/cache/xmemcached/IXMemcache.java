package cn.com.afcat.shcpemonitor.common.cache.xmemcached;

import cn.com.afcat.shcpemonitor.common.cache.base.ICache;
import net.rubyeye.xmemcached.CASOperation;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.exception.MemcachedException;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public interface IXMemcache extends ICache<String,Object> {
	
	/**
	 * 如果不存在key,则插入key-value至缓存中
	 * @param key : key名称
	 * @param exp : 过期时间,过期后memcached自动替换掉key-value
	 * @param value : 实际存储的对象,可以是任意的java可序列化类型
	 * @return boolean : 新增是否成功
	 * @throws TimeoutException 
	 * 		   InterruptedException
	 * 		   MemcachedException
	 */
	public boolean add(String key, int exp, Object value) throws TimeoutException,InterruptedException,MemcachedException;

	/**
	 * 如果不存在key,则插入key-value至缓存中
	 * @param key : key名称
	 * @param exp : 过期时间,过期后memcached自动替换掉key-value
	 * @param value : 实际存储的对象,可以是任意的java可序列化类型
	 * @param timeout : 超时时间,超过这个时间则认为操作失败,默认为5秒
	 * @return boolean : 新增是否成功
	 * @throws TimeoutException
	 * 		   InterruptedException
	 * 		   MemcachedException
	 */
	public boolean add(String key, int exp, Object value, long timeout) throws TimeoutException,InterruptedException,MemcachedException;
	
	/**
	 * 更新key的过期时间
	 * @param key : 关键字
	 * @param exp : 新的过期时间
	 * @return boolean : 操作是否成功
	 */
	public boolean touch(String key, int exp) throws TimeoutException,InterruptedException,MemcachedException;
	
	/**
	 * 获取缓存数据并更新过期时间
	 * @param key : 关键字
	 * @param exp : 新的过期时间
	 * @return Object : 缓存数据
	 */
	public Object getAndTouch(String key, int exp) throws TimeoutException,InterruptedException,MemcachedException;
	
	/**	CAS操作key,更新相应的值
	 * 例子：
	 *  client.cas("a", 0, new CASOperation<Integer>() {
     *            public int getMaxTries() {
     *               return 1;
     *             }
	 *
     *         	  public Integer getNewValue(long currentCAS, Integer currentValue) {
     *               return 2;
     *             }
     *   });
     *   
	 * @param key
	 * @param operation 该接口只有两个方法，一个是设置最大尝试次数的getMaxTries方法，
     *   这里是尝试一次，如果尝试超过这个次数没有更新成功将抛出一个TimeoutException，
     *   如果想无限尝试（理论上），可以将返回值设定为Integer.MAX_VALUE；
     *   另一个方法是根据当前获得的GetsResponse来决定更新数据的getNewValue方法，
     *   如果更新成功，这个方法返回的值将存储成功。
	 * @return boolean 是否更新成功
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> boolean cas(String key, CASOperation<T> operation) throws TimeoutException,InterruptedException,MemcachedException;
	
	/**
	 * 
	 * @param key
	 * @param exp 过期时间
	 * @param operation 
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> boolean cas(String key, int exp, CASOperation<T> operation) throws TimeoutException,InterruptedException,MemcachedException;

	/**
	 * cas操作
	  * @param key : 键
	 * @param exp : 过期时间
	 * @param value : 值
	 * @param cas : cas值,如果有其他线程更新key,cas会改变
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public boolean cas(String key, int exp, Object value, long cas)throws TimeoutException,InterruptedException,MemcachedException;

	/**
	 * cas操作
	 * @param key : 键
	 * @param exp : 过期时间
	 * @param value : 值
	 * @param timeout : 超时时间
	 * @param cas : cas值,如果有其他线程更新key,cas会改变
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public boolean cas(String key, int exp, Object value, Long timeout, long cas)throws TimeoutException,InterruptedException,MemcachedException;

	/**
	 * 删除value
	 * @param key
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public boolean delete(String key) throws TimeoutException,InterruptedException,MemcachedException;
	
	/**
	 * 删除value
	 * @param key
	 * @param opTimeout 操作超时时间
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public boolean delete(String key, long opTimeout) throws TimeoutException,InterruptedException,MemcachedException;

	/**
	 * 删除value
	 * @param key
	 * @param cas : 只有cas同读的时候相同才允许删除
	 * @param opTimeout 操作超时时间
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public boolean delete(String key, long cas, long opTimeout) throws TimeoutException,InterruptedException,MemcachedException;
	
	/**
	 * 获取key的值
	 * @param key
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> T get(String key) throws TimeoutException,InterruptedException,MemcachedException;
	
	/**
	 * 获取key的值
	 * @param key
	 * @param timeout : 超时时间
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> T get(String key, long timeout) throws TimeoutException,InterruptedException,MemcachedException;
	
	
	/**
	 * 获取key的值,返回的是一个对cas值,value值等的封装对象
	 * @param key
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> GetsResponse<T> gets(String key)	throws TimeoutException,InterruptedException,MemcachedException;

	/**
	 * 获取key的值,返回的是一个对cas值,value值等的封装对象
	 * @param key
	 * @param timeout : 超时时间
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> GetsResponse<T> gets(String key, long timeout)throws TimeoutException,InterruptedException,MemcachedException;

	/**
	 * 获取一系列key的值
	 * @param keyCollections  key的集合
	 * @return 
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> Map<String,T> get(Collection<String> keyCollections)throws TimeoutException,InterruptedException,MemcachedException;

	
	/**
	 * 获取一系列key的值
	 * @param keyCollections  key的集合
	 * @param timeout : 超时时间
	 * @return 
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> Map<String,T> get(Collection<String> keyCollections, long timeout)throws TimeoutException,InterruptedException,MemcachedException;
	
	/**
	 * 设置key的值,存在则替换,不存在则新增
	 * @param key 
	 * @param exp : 有效时间
	 * @param value
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public boolean set(String key, int exp, Object value)throws TimeoutException,InterruptedException,MemcachedException;
	
	/**
	 * 设置key的值,存在则替换,不存在则新增
	 * @param key 
	 * @param exp : 有效时间
	 * @param value
	 * @param timeout
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public boolean set(String key, int exp, Object value, long timeout)throws TimeoutException,InterruptedException,MemcachedException;
	
	/**
	 * 替换key的值,只有数据存在于缓存中才会替换
	 * @param key
	 * @param exp
	 * @param value
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public boolean replace(String key, int exp, Object value)throws TimeoutException,InterruptedException,MemcachedException;
	
	/**
	 * 替换key的值,只有数据存在于缓存中才会替换
	 * @param key
	 * @param exp
	 * @param value
	 * @param timeout
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public boolean replace(String key, int exp, Object value, long timeout)throws TimeoutException,InterruptedException,MemcachedException;
	
	
	
}
