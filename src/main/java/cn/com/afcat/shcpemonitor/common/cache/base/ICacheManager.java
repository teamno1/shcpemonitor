package cn.com.afcat.shcpemonitor.common.cache.base;


/**
 * 
 *
 * @author:litao
 */
public interface ICacheManager<T extends ICache<?, ?>> {

    public abstract T getCache(String paramString);
}
