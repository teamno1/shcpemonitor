package cn.om.afcat.shcpemonitor.common.cache.base;


import cn.com.afcat.shcpemonitor.common.config.Global;

/**
 * 
 *
 * @author:litao
 */
public class CacheUtil {
    
    public static String getCacheType(){
        return Global.getConfig("cacheType");
    }
    
    public static String getClientType(){
        return Global.getConfig("clientType");
    }
    
    public static String getMemcachedClientName(){
        return Global.getConfig("memcachedClientName");
    }
    
    public static String getRedisHosts(){
        return Global.getConfig("redishosts");
    }
    
    public static Integer getRedisMaxTotal(){
        return Global.getIntegerConfig("maxTotal",20);
    }
    
    public static Integer getRedisMaxIdle(){
        return Global.getIntegerConfig("maxIdle",10);
    }
    
    public static Integer getRedisMinIdle(){
        return Global.getIntegerConfig("minIdle",5);
    }
    
    public static Integer getRedisMaxWait(){
        return  Global.getIntegerConfig("maxWait");
    }
    
    public static boolean getRedisTestOnBorrow(){
        return false;
    }
    
}
