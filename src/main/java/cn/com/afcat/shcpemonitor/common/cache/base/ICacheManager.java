/********************************************
 * 文件名称: ICacheManager.java
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


/**
 * 
 *
 * @author:tao
 */
public interface ICacheManager<T extends ICache<?, ?>> {

    public abstract T getCache(String paramString);
}
