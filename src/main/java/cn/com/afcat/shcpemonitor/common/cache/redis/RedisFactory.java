package cn.com.afcat.shcpemonitor.common.cache.redis;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 
 *
 * @author:tao
 */
public class RedisFactory {
    
    private static RedisFactory instance=null;
    
    private static Map<String, AppRedis> appRedisMap = new HashMap<String, AppRedis>();
    private RedisFactory(){
        try {
            initRedis();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public static RedisFactory getInstance() {
        if(instance == null)
            instance=new RedisFactory();
        return instance;
    }
    
    /**
     * 获取某个应用下的redis连接
     * RedisFactory.getRedisByAppId()<BR>
     * @param appId
     * @param token
     * @return
     */
    public IRedisOperate getRedisByAppId(String appId,String token){
        AppRedis redisApp = appRedisMap.get(appId);
        return redisApp.getRedis(token);
    }
    /**
     * 获取某个应用下的所有redis连接
     * RedisFactory.getAllRedisByAppId()<BR>
     * @param appId
     * @return
     */
    public List<IRedisOperate> getAllRedisByAppId(String appId){
        AppRedis redisApp = appRedisMap.get(appId);
        return redisApp.getAllRedis();
    }
    /**
     * 初始化
     * RedisFactory.initRedis()<BR>
     * @throws Exception
     */
    private  void initRedis() throws Exception{
        HashMap<String,AppRedis> redisConnectionMap = new HashMap<String,AppRedis>();
        XMLConfiguration appConfig = new XMLConfiguration("redis/appredis.xml");
        List<Object> appList = appConfig.getList("app.id");
        for(int index=0;index<appList.size();index++){
            String appId = (String)appList.get(index);
            String dispatch = appConfig.getString("app("+index+").dispatch");
            String clusterStrategy = appConfig.getString("app("+index+").clusterStrategy");
            String clusterIds = appConfig.getString("app("+index+").clusterIds");
            List<String> clusterIdList = new ArrayList<String>();
            if(StringUtils.isNotBlank(clusterIds)){
                String[] clusterIdstrs = clusterIds.split(":");
                for(String clusterId : clusterIdstrs){
                    clusterIdList.add(clusterId);
                }
            }
            AppRedis app = new AppRedis(appId, dispatch, clusterStrategy, clusterIdList);
            redisConnectionMap.put(appId, app);
        }
        appRedisMap = redisConnectionMap;
    }
    

}
