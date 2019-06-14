/********************************************
 * 文件名称: ICache.java
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
package cn.com.afcat.shcpemonitor.common.cache.base;

import java.util.Collection;
import java.util.Set;

/**
 * 
 *
 * @author:tao
 */
public interface ICache<K, V> {

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
    public abstract V put(K key, V value);
    
    /**
     * 存在则替换 不存在新增<br>  
     * 
     * @param key
     *            键
     * @param value
     *            值
     * @return V 对象
     */
    public abstract boolean set(K key, V value);
     
        /**
         * 返回key键所对应的值
         * 
         * @param key
         *            键
         * @return V 键对应的值 针对于pre预加载缓存，返回结果为Map <primaryKey, Map<field,value>>
         */
     public abstract V get(K key);



        /**
         * 如果缓存数据存储中存在该键的映射关系，则将其删除。
         * 
         * @param key
         *            要从映射中移除的键
         * @return V 与指定键相关联的旧值
         */
    public abstract V remove(K key);
    

    /**
     * 缓存中是否包含指定的键对应的单元
     * 
     * @param key
     *            待匹配的键
     * 
     * @return true, 如果数据存储中包含key对应的单元,<br>
     *         false, 数据储存中不包含key对应的单元.<br>
     */
    public abstract boolean containsKey(K key);
    
    
    /**
     * 返回此映射中所包含的键的set视图<br>
     * 
     * @return Set 映射中所包含的键的set视图
     */
    public abstract Set<K> keySet();

    /**
     * 返回所有值的集合,返回所有底层存储数据
     * 
     * @return Collection 此缓存数据存储所包含的值的 collection 视图
     * 
     */
    public abstract Collection<V> values();

    /**
     * 判断缓存数据存储是否有数据，如果有数据则返回false<br>
     * ,如果没有则返回true<br>
     * 
     * @return true 如果数据存储中没有包含数据
     */
    public abstract boolean isEmpty();

    /**
     * 返回缓存数据存储中的键-值映射关系数<br>
     * 
     * @return 此映射中的键-值映射关系数
     */
    public abstract int size();


    /**
     * 清除缓存中的所有数据
     * 
     * @return
     */
    public abstract void clear();
}
