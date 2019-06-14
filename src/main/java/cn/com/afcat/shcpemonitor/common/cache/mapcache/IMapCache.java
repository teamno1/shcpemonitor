package cn.com.afcat.shcpemonitor.common.cache.mapcache;

import cn.com.afcat.shcpemonitor.common.cache.base.ICache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;


/**
 * 
 *
 * @author:litao
 */
public interface IMapCache extends ICache<Object,Object> {

    /**
     * 缓存中是否包含指定的键对应的单元
     * 
     * @param key
     *            待匹配的键
     * 
     * @return true, 如果数据存储中包含key对应的单元,<br>
     *         false, 数据储存中不包含key对应的单元.<br>
     */
    public boolean containsKey(Object key);

    /**
     * 缓存中是否包含一个或多个与value匹配的单元
     * 
     * @param value
     *            待匹配的值
     * 
     * @return true 如果数据存储中一个或多个与value匹<br>
     *         配的单元<br>
     */
    public boolean containsValue(Object value);

    /**
     * 返回此缓存数据存储映射所包含的映射关系的 collec<br>
     * tion视图。在返回的集合中，每个元素都是一个 Map.<br>
     * Entry<br>
     * 
     * @return Set 此映射所包含的映射关系的collection视图
     */
    public Set entrySet();

    /**
     * 比较两个数据存储的结构是否相同
     * 
     * @param o
     *            缓存数据存储
     * 
     * @return true 相同, false 不相同
     */
    public boolean equals(Object o);

    /**
     * 获得hashCode
     * 
     * @return
     */
    public int hashCode();

    /**
     * 判断缓存数据存储是否有数据，如果有数据则返回false<br>
     * ,如果没有则返回true<br>
     * 
     * @return true 如果数据存储中没有包含数据
     */
    public boolean isEmpty();

    /**
     * 返回此映射中所包含的键的set视图<br>
     * 
     * @return Set 映射中所包含的键的set视图
     */
    public Set keySet();

    /**
     * 返回缓存数据存储中的键-值映射关系数<br>
     * 
     * @return 此映射中的键-值映射关系数
     */
    public int size();

    /**
     * 返回所有值的集合,返回所有底层存储数据
     * 
     * @return Collection 此缓存数据存储所包含的值的 collection 视图
     * 
     */
    public Collection values();

    /**
     * 返回key键所对应的值
     * 
     * @param key
     *            键
     * @return Object 键对应的值 针对于pre预加载缓存，返回结果为Map <primaryKey, Map<field,value>>
     */
    public Object get(Object key);

    /**
     * 向缓存中放置一条数据，如果此数据存储以前包含了一个<br>
     * 该键的映射关系，则旧值被替换<br>
     * 
     * @param key
     *            键
     * @param value
     *            值
     * @return Object 对象
     */
    public Object put(Object key, Object value);

    /**
     * 如果缓存数据存储中存在该键的映射关系，则将其删除。
     * 
     * @param key
     *            要从映射中移除的键
     * @return Object 与指定键相关联的旧值
     */
    public Object remove(Object key);

    /**
     * 清除缓存中的所有数据
     * 
     * @return
     */
    public void clear();

    /**
     * 把map中的数据Map<k,v>全部放入缓存中,这些映射关系 <br>
     * 将替换此映射目前针对指定映射的所有键的所有映射关系。<br>
     * 
     * @param t
     *            要在缓存中存储的映射关系
     */
    public void putAll(Map t);
}
