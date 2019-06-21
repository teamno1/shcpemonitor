package cn.com.afcat.shcpemonitor.common.cache.mapcache;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * 
 *
 * @author:tao
 */
public class MapImpl implements IMapCache {

 // 集中缓存客户端对象，客户端的通过MemcachedFactoryImpl来加载集中集中缓存连接池
    private Map mcc = new HashMap();
    private String space;

    public MapImpl(String space) {
        this.space = space;
    }

    public void clear() {
        mcc.clear();
    }

    public boolean containsKey(Object key) {
        return mcc.containsKey(key);
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

    public Object get(Object key) {
        Object object = mcc.get(key);
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

    public Object put(Object key, Object value) {
        return mcc.put(key, value);
    }

    public Object remove(Object key) {
        return mcc.remove(key);
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
            mcc.put(entry.getKey(), entry.getValue());
        }

    }

    /**
     * Entry set.
     * 
     * @return the sets the
     */
    public Set entrySet() {
        return mcc.entrySet();
    }
    
    /**
     * Entry set.
     * 
     * @return the sets the
     */
    @Override
    public boolean set(Object key, Object value) {      
        boolean result = false;
        if(mcc.containsKey((String)key)){
            mcc.remove(key);
            mcc.put(key, value);
            result = true;
        }else{
            mcc.put(key, value);
            result = true;
        }
        return result;
    }

}
