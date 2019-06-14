package cn.com.afcat.shcpemonitor.common.cache.base;

import cn.com.afcat.shcpemonitor.common.cache.mapcache.MapImpl;
import cn.com.afcat.shcpemonitor.common.cache.memcached.MemcachedImpl;
import cn.com.afcat.shcpemonitor.common.cache.redis.CacheInRedisImpl;
import cn.com.afcat.shcpemonitor.common.cache.xmemcached.XMemcachedImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 *
 * @author:litao
 */
public class CacheFactory {

    private static  Map<String, ICache> cacheMap = new HashMap<String, ICache>();
    synchronized  public static ICache getCache(String name) {
        if (cacheMap.get(name) == null) {
            ICache cacheImpl = null;
            String cacheClientType=getCacheClientType();
            if (CacheConstants.XMEMCACHED.equals(cacheClientType)) {
                cacheImpl =new XMemcachedImpl(name);
                cacheMap.put(name, cacheImpl);
                return cacheImpl;
            } else if (CacheConstants.HASHMAP.equals(cacheClientType)){
                cacheImpl = new MapImpl(name);
                cacheMap.put(name, cacheImpl);
                return cacheImpl;
            } else if(CacheConstants.MEMCACHED.equals(cacheClientType)){
                cacheImpl = new MemcachedImpl(name);
                cacheMap.put(name, cacheImpl);
                return cacheImpl;
            } else if(CacheConstants.REDIS.equals(cacheClientType)){
                cacheImpl = new CacheInRedisImpl(name);
                cacheMap.put(name, cacheImpl);
                return cacheImpl;
            } else {
                return null;
            }
        } else {
            return cacheMap.get(name);
        }
    }
    
    public static  String getCacheClientType(){
        String cacheClientType= cn.om.afcat.shcpemonitor.common.cache.base.CacheUtil.getCacheType();
        if (CacheConstants.MEMCACHED.equals(cn.om.afcat.shcpemonitor.common.cache.base.CacheUtil.getCacheType())) {
            if (CacheConstants.XMEMCACHED.equals(cn.om.afcat.shcpemonitor.common.cache.base.CacheUtil.getClientType())) {
                cacheClientType=CacheConstants.XMEMCACHED;
            }else {
                cacheClientType=CacheConstants.MEMCACHED;
            }
        }
        if(cacheClientType == null)
            cacheClientType=CacheConstants.HASHMAP;
        return cacheClientType;
    }
    
}
