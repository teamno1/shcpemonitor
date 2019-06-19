package cn.com.afcat.shcpemonitor.common.cache.redis;

import cn.com.afcat.shcpemonitor.common.cache.redis.routing.RoutingRedis;
import cn.com.afcat.shcpemonitor.common.cache.redis.shard.ShardRedis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 应用下的集群策略
 *
 */
public class AppRedis {

    private String dispatch;
    private String clusterStrategy;
    private ConsistentHash<IRedisOperate> hash;
    private List<IRedisOperate> redisList = new ArrayList<IRedisOperate>();
    public AppRedis(String appId,String dispatch,String clusterStrategy, List<String> clusterIds){
        this.dispatch = dispatch;
        this.clusterStrategy = clusterStrategy;
        for(String clusterId : clusterIds){
            IRedisOperate redis = null;
            if("ROUTING".equals(dispatch)){
                redis = new RoutingRedis(clusterId);
            }else if("SHARD".equals(dispatch)){
                redis = new ShardRedis(clusterId);
            }
            redisList.add(redis);
        }

    }
    /**
     * 获取集群策略中的一组服务
     * AppRedis.getLafasoRedis()<BR>
     * @param token
     * @return
     */
    public IRedisOperate getRedis(String token){
        if("ROUTING".equals(dispatch)){
            IRedisOperate redis = null;
            if("HASH".equals(clusterStrategy)){
                if (hash == null) {// 初始化一致性Hash算法，只初始化一次
                    hash = new ConsistentHash<IRedisOperate>(redisList);
                }
                redis = hash.get(token);
            }else if("ONLY".equals(clusterStrategy)){
                redis = redisList.get(0);
            }
            return redis;
        }else if("SHARD".equals(dispatch)){
            IRedisOperate redis = redisList.get(0);
            return redis;
        }else{
            throw new RedisException(
                    "the dispatch:"
                            + this.dispatch
                            + " can not support,please set the  dispatch in appredis.xml correct");
        }
    }
    /**
     * 获取当前应用下的所有服务
     * AppRedis.getAllRedisCluster()<BR>
     * @return
     */
    public List<IRedisOperate> getAllRedis(){
        return Collections.unmodifiableList(redisList);
    }
   
}
