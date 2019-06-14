package cn.com.afcat.shcpemonitor.common.cache.memcached;

import cn.com.afcat.shcpemonitor.common.cache.base.ICache;
import com.alisoft.xplatform.asf.cache.IMemcachedCache;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
/**
 * 
 * 集中cache存储结构,该缓存将数据放在集中的服务器上，用于分布式环节下多个应用之间的数据共享<br>
 * @author:tao
 */
public class MemcachedImpl implements ICache {
 // 集中缓存客户端对象，客户端的通过MemcachedFactoryImpl来加载集中集中缓存连接池
    private IMemcachedCache mcc;
    private String space;

    public MemcachedImpl(String space) {
        this.space = space;
        mcc = MemcachedClientFactory.getClient(cn.om.afcat.shcpemonitor.common.cache.base.CacheUtil
                .getMemcachedClientName());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.hundsun.jres.interfaces.cache.BasicCache#clear()
     */
    public void clear() {
        mcc.clear();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.hundsun.jres.interfaces.cache.BasicCache#containsKey(java.lang.Object
     * )
     */
    public boolean containsKey(Object key) {
        return mcc.containsKey(getSpaceKey((String) key));
    }

    /**
     * Contains value.
     * 
     * @param value
     *            the value
     * @return true, if successful
     */
    public boolean containsValue(Object value) {
        Collection values = mcc.values();
        return values.contains(value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.hundsun.jres.interfaces.cache.BasicCache#get(java.lang.Object)
     */
    public Object get(Object key) {
        Object object = mcc.get(getSpaceKey((String) key));
        return object;
    }

    /**
     * 如果状态中有curr_item值且为0则返回值为true 状态值中没有curr_item则集中式缓存不支持该接口
     * 
     * @return true, if is empty
     */
    public boolean isEmpty() {
        return mcc.size() == 0;
    }

    /**
     * Key set.
     * 
     * @return the sets the
     */
    public Set keySet() {
        return mcc.keySet();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.hundsun.jres.interfaces.cache.BasicCache#put(java.lang.Object,
     * java.lang.Object)
     */
    public Object put(Object key, Object value) {
        return mcc.put(getSpaceKey((String) key), value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.hundsun.jres.interfaces.cache.BasicCache#remove(java.lang.Object)
     */
    public Object remove(Object key) {
        return mcc.remove(getSpaceKey((String) key));
    }

    /**
     * Size.
     * 
     * @return the int
     */
    public int size() {
        return mcc.size();
    }

    /**
     * Values.
     * 
     * @return the collection
     */
    public Collection values() {
        return mcc.values();
    }

    /**
     * Put all.
     * 
     * @param t
     *            the t
     */
    public void putAll(Map t) {

        for (Iterator iterator = t.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            mcc.put(getSpaceKey((String) entry.getKey()), entry.getValue());
        }

    }

    /**
     * Entry set.
     * 
     * @return the sets the
     * @deprecated 不支持此方法
     */
    public Set entrySet() {
        return null;
    }
    
    /**
     * 获得spacekey
     * @param key
     * @return
     */
    public boolean set(Object key,Object object) {
        boolean result = false;
        if(mcc.containsKey(getSpaceKey((String) key))){
            result = mcc.replace(getSpaceKey((String) key), object);            
        }else{
            mcc.put(getSpaceKey((String) key), object);
            result = true;
        }
        return result;
    }
    
    /**
     * 获得spacekey
     * @param key
     * @return
     */
    private String getSpaceKey(String key) {
        return space + key;
    }
}
