package cn.com.afcat.shcpemonitor.common.cache.xmemcached;

import cn.com.afcat.shcpemonitor.common.cache.base.CacheFactory;
import cn.com.afcat.shcpemonitor.common.cache.base.ICacheManager;

public class MemcachedCacheManager implements ICacheManager<IXMemcache> {
	public IXMemcache getCache(String name){
    return (IXMemcache) CacheFactory.getCache(name);
  }
}
