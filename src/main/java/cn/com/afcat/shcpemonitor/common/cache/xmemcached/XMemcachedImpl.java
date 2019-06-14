package cn.com.afcat.shcpemonitor.common.cache.xmemcached;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.CASOperation;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.KeyIterator;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class XMemcachedImpl implements IXMemcache {

	private MemcachedClient mcc;
	private String space;

	public XMemcachedImpl(String name) {
		this.space = name;
		this.mcc = XMemCachedClientManager.getClient();
	}

	private void isString(Object key) {
		if (!(key instanceof String)) {
			throw new UnsupportedOperationException("key must be String!");
		}
	}
	
	
	/**
	 * 向缓存中放置一条数据，如果此数据存储以前包含了一个<br>
	 * 该键的映射关系，则旧值被替换<br>
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return V 对象
	 */
	@Override
	public Object put(String key, Object value) {
		try {
			boolean success = mcc.set(getSpaceKey(key), 0, value);
			if (!success) {
				throw new RuntimeException("put key :" + key + " error!");
			} 
		} catch (Exception e) {
		    e.printStackTrace();
			throw new RuntimeException("put key :" + key + " error!");
		}
		return value;
	}
	
	
	/**
	 * 返回key键所对应的值
	 * 
	 * @param key
	 *            键
	 * @return V 键对应的值 针对于pre预加载缓存，返回结果为Map <primaryKey, Map<field,value>>
	 */
	@Override
	public Object get(String key) {
 		Object value = null;
		try {
			value =mcc.get(getSpaceKey(key));
		} catch (Exception e) {
			throw new RuntimeException("get key :" + key + " error!");
		}
		return value;
     }
	
	/**
	 * 如果缓存数据存储中存在该键的映射关系，则将其删除。
	 * 
	 * @param key
	 *            要从映射中移除的键
	 * @return V 与指定键相关联的旧值
	 */
     public Object remove(String key) {
 		try {
			Object oriObj = get(getSpaceKey(key));
			boolean result = false ;
			if (oriObj != null) {
				result = delete(getSpaceKey(key));
			}
			return result==true?oriObj:null;
		} catch (Exception e) {
			throw new RuntimeException("remove key :" + key + " error!");
		}
     }
	
	
	/**
	 * 缓存中是否包含指定的键对应的单元
	 * 
	 * @param key
	 *            待匹配的键
	 * 
	 * @return true, 如果数据存储中包含key对应的单元,<br>
	 *         false, 数据储存中不包含key对应的单元.<br>
	 */
	@Override
	public boolean containsKey(String key) {
		boolean returnValue = true;
		try {
			returnValue = !(mcc.get(key) == null);
		} catch (Exception e) {
			returnValue = false;
		}
		return returnValue;
	}
	
	/**
	 * 返回此映射中所包含的键的set视图<br>
     * 目前只提供了文本协议的迭代支持，其他协议暂未支持。
	 * 此方法开销太大，慎用
	 * 
	 * @return Set 映射中所包含的键的set视图
	 */
	public  Set<String> keySet(){
		Set<String> set = new HashSet<String>();
		List<String> keyList = XMemCachedClientManager.getServersList();
		try {
			for (String server : keyList) {
				KeyIterator it = mcc.getKeyIterator(AddrUtil.getOneAddress(server));
				while (it.hasNext()) {
					String  key = it.next();
					set.add(key);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			set = null;
		}
		return set;
	}
	
	/**
	 * 返回所有值的集合,返回所有底层存储数据
	 * 此方法开销太大,不推荐使用
	 * @return Collection 此缓存数据存储所包含的值的 collection 视图
	 * 
	 */
	public  Collection<Object> values() {
		Set values = new HashSet();
		List<String> keyList = XMemCachedClientManager.getServersList();
		try {
			for (String server : keyList) {
				KeyIterator it = mcc.getKeyIterator(AddrUtil.getOneAddress(server));
				while (it.hasNext()) {
					String  key = it.next();
					Object value = get(key);
					values.add(value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			values = null;
		}
		return values;
	}
	
	
	/**
	 * 判断缓存数据存储是否有数据，如果有数据则返回false<br>
	 * ,如果没有则返回true<br>
	 * 不推荐使用
	 * 
	 * @return true 如果数据存储中没有包含数据
	 */
	public  boolean isEmpty(){
		boolean result = true;
		List<String> keyList = XMemCachedClientManager.getServersList();
		try {
			for (String server : keyList) {
				KeyIterator it = mcc.getKeyIterator(AddrUtil.getOneAddress(server));
				while (it.hasNext()) {
					result = false;
					break;
				}
				if (result == false) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new UnsupportedOperationException("error method invoke!");
		}
		return result;
	}

	/**
	 * 返回缓存数据存储中的键-值映射关系数<br>
	 * 
	 * @return 此映射中的键-值映射关系数
	 */
	@Override
	@Deprecated
	public int size() {
		throw new UnsupportedOperationException("XMemcached not support size method!");
	}

	/**
	 * 清除缓存中的所有数据
	 * 
	 * @return
	 */
	@Override
	public  void clear(){
		try {
			XMemCachedClientManager.getClient().flushAll();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean add(String key, int exp, Object value)
			throws TimeoutException, InterruptedException, MemcachedException {
		return mcc.add(getSpaceKey(key), exp, value);
	}

	@Override
	public boolean add(String key, int exp, Object value, long timeout)
			throws TimeoutException, InterruptedException, MemcachedException {
		return mcc.add(getSpaceKey(key), exp, value, timeout);
	}

	@Override
	public boolean touch(String key, int exp) throws TimeoutException, InterruptedException, MemcachedException {
	    return mcc.touch(getSpaceKey(key), exp);
	}

	@Override
	public Object getAndTouch(String key, int exp) throws TimeoutException, InterruptedException, MemcachedException {
		return mcc.getAndTouch(getSpaceKey(key), exp);
	}

	@Override
	public <T> boolean cas(String key, CASOperation<T> operation)
			throws TimeoutException, InterruptedException, MemcachedException {
		return mcc.cas(getSpaceKey(key), operation);
	}

	@Override
	public <T> boolean cas(String key, int exp, CASOperation<T> operation)
			throws TimeoutException, InterruptedException, MemcachedException {
		return mcc.cas(getSpaceKey(key), exp, operation);
	}

	@Override
	public boolean cas(String key, int exp, Object value, long cas)
			throws TimeoutException, InterruptedException, MemcachedException {
		return mcc.cas(getSpaceKey(key), exp, value, cas);
	}

	@Override
	public boolean cas(String key, int exp, Object value, Long timeout, long cas)
			throws TimeoutException, InterruptedException, MemcachedException {
		return mcc.cas(getSpaceKey(key), exp, value, timeout, cas);
	}

	@Override
	public boolean delete(String key) throws TimeoutException, InterruptedException, MemcachedException {
		return mcc.delete(getSpaceKey(key));
	}

	@Override
	public boolean delete(String key, long opTimeout)
			throws TimeoutException, InterruptedException, MemcachedException {
		return mcc.delete(getSpaceKey(key),opTimeout);
	}

	@Override
	public boolean delete(String key, long cas, long opTimeout)
			throws TimeoutException, InterruptedException, MemcachedException {
		return mcc.delete(getSpaceKey(key),opTimeout);
	}


	@Override
	public <T> T get(String key, long timeout) throws TimeoutException, InterruptedException, MemcachedException {
		return mcc.get(getSpaceKey(key), timeout);
	}

	@Override
	public <T> GetsResponse<T> gets(String key) throws TimeoutException, InterruptedException, MemcachedException {
		return mcc.gets(getSpaceKey(key));
	}

	@Override
	public <T> GetsResponse<T> gets(String key, long timeout)
			throws TimeoutException, InterruptedException, MemcachedException {
		return mcc.gets(getSpaceKey(key), timeout);
	}

	@Override
	public <T> Map<String, T> get(Collection<String> keyCollections)
			throws TimeoutException, InterruptedException, MemcachedException {
		return mcc.get(keyCollections);
	}

	@Override
	public <T> Map<String, T> get(Collection<String> keyCollections, long timeout)
			throws TimeoutException, InterruptedException, MemcachedException {
		List<String> keyList = new ArrayList<String>(keyCollections.size());
		for (String key : keyCollections) {
			keyList.add(getSpaceKey(key));
		}
		return mcc.get(keyList, timeout);
	}

	@Override
	public boolean set(String key, int exp, Object value)
			throws TimeoutException, InterruptedException, MemcachedException {
		return mcc.set(getSpaceKey(key), exp, value);
	}

	@Override
	public boolean set(String key, int exp, Object value, long timeout)
			throws TimeoutException, InterruptedException, MemcachedException {
		return mcc.set(getSpaceKey(key), exp, value, timeout);
	}

	@Override
	public boolean replace(String key, int exp, Object value)
			throws TimeoutException, InterruptedException, MemcachedException {
		return mcc.replace(getSpaceKey(key), exp, value);
	}

	@Override
	public boolean replace(String key, int exp, Object value, long timeout)
			throws TimeoutException, InterruptedException, MemcachedException {
		return mcc.replace(getSpaceKey(key), exp, value, timeout);
	}

	/**
	 * 获得spacekey
	 * 
	 * @param key
	 * @return
	 */
	private String getSpaceKey(String key) {
		return space + key;
	}

	
	/**
	 * 存在则替换，不存在则新增，父类实现，超时时间默认永久
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public boolean set(String key, Object value) {		
		boolean result = false;
		try {
			result =  mcc.replace(getSpaceKey((String) key), 0, value);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
		return result;
	}

}
