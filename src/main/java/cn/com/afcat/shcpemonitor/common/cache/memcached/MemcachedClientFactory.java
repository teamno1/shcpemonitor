package cn.com.afcat.shcpemonitor.common.cache.memcached;

import com.alisoft.xplatform.asf.cache.ICacheManager;
import com.alisoft.xplatform.asf.cache.IMemcachedCache;
import com.alisoft.xplatform.asf.cache.memcached.CacheUtil;
import com.alisoft.xplatform.asf.cache.memcached.MemcachedCacheManager;
/**
 * 集中缓存池及客户端工厂类<br>
 *
 * @author:tao
 */
public class MemcachedClientFactory {

    public static ICacheManager<IMemcachedCache> getManager(){
        return manager;
    }
    
    /**
     * memcache集群管理类
     */
    private static ICacheManager<IMemcachedCache> manager;

    /** The cfg. */
    private static String memcacheXml = "memcached.xml";

    
    
    static {
        init();
    }

    private static void init() {
        manager = CacheUtil.getCacheManager(IMemcachedCache.class,
                MemcachedCacheManager.class.getName());
        //manager.setResponseStatInterval(5*1000);
        manager.setConfigFile(memcacheXml);
        manager.start();
    }

    /**
     * 获得集中cache客户端.
     * 
     * @return the mem cached client
     * @return
     */
    public static IMemcachedCache getClient(String clientName) {
        return manager.getCache(clientName);
    }
}
