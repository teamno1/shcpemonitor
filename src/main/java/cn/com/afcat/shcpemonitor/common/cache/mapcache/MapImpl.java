/********************************************
 * 文件名称: MapImpl.java
 * 系统名称: 
 * 模块名称: 
 * 软件版权: 北京合融科技有限公司
 * 功能说明: 
 * 系统版本: 
 * 开发人员: lt
 * 开发时间: 2017-1-18
 * 审核人员:
 * 相关文档:
 * 修改记录: 修改日期    修改人员    修改说明
 *             .----.
 *          _.'__    `.
 *      .--(^)(^^)---/!\
 *    .' @          /!!!\
 *    :         ,    !!!!
 *     `-..__.-' _.-\!!!/
 *           `;_:    `"'
 *          .'"""""`.
 *          /,  lt ,\\
 *         // 嘿嘿嘿嘿      \\
 *         `-._______.-'
 *         ___`. | .'___
 *        (______|______)
 *********************************************/
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

    /*
     * (non-Javadoc)
     * 
     * @see com.hundsun.jres.interfaces.cache.BasicCache#get(java.lang.Object)
     */
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

    /*
     * (non-Javadoc)
     * 
     * @see com.hundsun.jres.interfaces.cache.BasicCache#put(java.lang.Object,
     * java.lang.Object)
     */
    public Object put(Object key, Object value) {
        return mcc.put(key, value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.hundsun.jres.interfaces.cache.BasicCache#remove(java.lang.Object)
     */
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
