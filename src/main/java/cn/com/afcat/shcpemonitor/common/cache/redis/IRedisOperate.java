package cn.com.afcat.shcpemonitor.common.cache.redis;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis 操作
 *
 */
public interface IRedisOperate {
    
    /**
     * 给指定key的字符串值追加 value,返回新字符串值的长度。
     * @param arg0
     * @return
     */
    public Long del(String arg0) ;

    /**
     * 给指定key的字符串值追加 value,返回新字符串值的长度。
     */
    public Long append(String arg0, String arg1) ;

    
    /***
     * 对key的值做的是减减操作，decr一个不存在 key，则设置key为-1 
     */
    public Long decr(String arg0);

    
    /***
     * 同decr，减指定值。
     */
    public Long decrBy(String arg0, long arg1);

    
    /***
     * 判断指定键是否存在
     */
    public Boolean exists(String arg0) ;

    
    /***
     * 设置一个key 的过期时间(单位:秒) 
     */
    public Long expire(String arg0, int arg1) ;

    
    public Long expireAt(String arg0, long arg1) ;

    
    /***
     * 获取key 对应的string值,如果key 不存在返回 nil。
     */
    public String get(String arg0) ;
    
    public byte[] getbyte(String arg0) ;
    
    public List<String> mget(String[] keys);

    
    /***
     * 设置key 的值，并返回 key 的旧值。
     */
    public String getSet(String arg0, String arg1) ;

    
    public Boolean getbit(String arg0, long arg1) ;
    
    /***
     *  获取指定key 的value 值的子字符串。 
     */
    public String getrange(String arg0, long arg1, long arg2) ;

    
    /***
     * 返回指定hash的 field数量
     */
    public Long hdel(String arg0, String... arg1) ;
    
    /***
     * 测试指定field是否存在。
     */
    public Boolean hexists(String arg0, String arg1) ;

    
    /***
     * 获取指定的hash field。
     */
    public String hget(String arg0, String arg1);

    
    /***
     * 获取某个hash中全部的filed及value。
     */
    public Map<String, String> hgetAll(String arg0) ;

    
    /**
     * 指定的hash filed  加上给定值。
     */
    public Long hincrBy(String arg0, String arg1, long arg2) ;

    
    /***
     * 返回hash的所有 field。
     */
    public Set<String> hkeys(String arg0) ;

    
    /***
     * 返回指定hash的 field数量
     */
    public Long hlen(String arg0) ;

    
    /***
     * 获取全部指定的 hash filed。
     */
    public List<String> hmget(String arg0, String... arg1) ;

    
    /***
     * 同时设置hash的多个field。
     */
    public String hmset(String arg0, Map<String, String> arg1) ;

    
    /***
     * 设置hash field为指定值，如果 key 不存在，则先创建。
     */
    public Long hset(String arg0, String arg1, String arg2) ;

    
    /***
     * 设置hash field为指定值，如果 key 不存在，则先创建。如果 field已经存在，返回0，nx是
        not exist的意思。
     */
    public Long hsetnx(String arg0, String arg1, String arg2);

    
    /***
     * 返回hash的所有 value。
     */
    public List<String> hvals(String arg0) ;

    
    /***
     * 对 key 的值做加加操作,并返回新的值。注意 incr 一个不是 int 的 value 会返回错误，incr 一
        个不存在的key，则设置 key 为1
     */
    public Long incr(String arg0) ;

    
    /***
     * 同incr类似，加指定值  ，key 不存在时候会设置 key，并认为原来的 value 是  0 
     */
    public Long incrBy(String arg0, long arg1) ;

    
    /***
     * 返回名称为key的 list中index位置的元素 
     */
    public String lindex(String arg0, long arg1);

    
    /***
     * 在key对应list的特定位置之前或之后添加字符串元素
     */
    public Long linsert(String arg0, LIST_POSITION arg1, String arg2,
                        String arg3) ;

    
    /***
     * 返回key 对应list的长度 
     */
    public Long llen(String arg0) ;

    
    /***
     * 从list的头部删除元素，并返回删除元素
     */
    public String lpop(String arg0) ;

    
    /***
     * 从list的头增加除元素，并返增加除元素
     */
    public Long lpush(String arg0, String... arg1);

    
    /***
     * 将值 value 插入到列表 key 的表头，当且仅当 key 存在并且是一个列表。

        和 LPUSH 命令相反，当 key 不存在时， LPUSHX 命令什么也不做。
     */
    public Long lpushx(String arg0, String arg1) ;

    
    public List<String> lrange(String arg0, long arg1, long arg2) ;

    
    /***
     * 从key对应list中删除 count个和value相同的元素
     */
    public Long lrem(String arg0, long arg1, String arg2) ;

    
    /***
     * 设置list中指定下标的元素值(下标从0开始) 
     */
    public String lset(String arg0, long arg1, String arg2) ;

    
    /**
     * 保留指定key  的值范围内的数据
     */
    public String ltrim(String arg0, long arg1, long arg2) ;

    
    /***
     * 从list的尾部删除元素，并返回删除元素
     */
    public String rpop(String arg0) ;

    
    /***
     * 在key对应list的尾部添加字符串元素
     */
    public Long rpush(String arg0, String... arg1);

    
    /***
     * 将值 value 插入到列表 key 的表尾，当且仅当 key 存在并且是一个列表。

        和 RPUSH 命令相反，当 key 不存在时， RPUSHX 命令什么也不做
     */
    public Long rpushx(String arg0, String arg1) ;

    
    /***
     * 向名称为key 的set中添加元素
     */
    public Long sadd(String arg0, String... arg1) ;

    
    /***
     * 返回名称为key的 set的元素个数
     */
    public Long scard(String arg0) ;

    
    /***
     * 设置key 对应的值为 string类型的value。
     */
    public String set(String arg0, String arg1);

    /***
     * 设置key 对应的值为 byte类型的value。
     * 放整个对象时需序列化
     */
    public String set(String key, byte[] value);
    
    /***
     * 对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)。

        位的设置或清除取决于 value 参数，可以是 0 也可以是 1 。
        
        当 key 不存在时，自动生成一个新的字符串值。
        
        字符串会进行伸展(grown)以确保它可以将 value 保存在指定的偏移量上。当字符串值进行伸展时，空白位置以 0 填充。
        
        offset 参数必须大于或等于 0 ，小于 2^32 (bit 映射被限制在 512 MB 之内)。
     */
    public Boolean setbit(String arg0, long arg1, boolean arg2) ;

    
    /***
     * 设置key 对应的值为 string类型的value，并指定此键值对应的有效期
     */
    public String setex(String arg0, int arg1, String arg2) ;
    /***
     * 设置key 对应的值为 string类型的value。 如果 key 已经存在，返回 0， nx是 not exist的意思。
     */
    
    public Long setnx(String arg0, String arg1) ;

    
    /***
     * 设置指定key 的value 值的子字符串
     */
    public Long setrange(String arg0, long arg1, String arg2) ;

    
    /***
     * 测试member 是否是名称为 key的 set的元素 
     */
    public Boolean sismember(String arg0, String arg1) ;

    
    /**
     * 返回集合 key 中的所有成员
     */
    public Set<String> smembers(String arg0) ;

    
    /***
     * 返回或保存给定列表、集合、有序集合 key 中经过排序的元素。

        排序默认以数字作为对象，值被解释为双精度浮点数，然后进行比较
     */
    public List<String> sort(String arg0) ;
    
    /***
     * 返回或保存给定列表、集合、有序集合 key 中经过排序的元素。

        排序默认以数字作为对象，值被解释为双精度浮点数，然后进行比较
     */
    public List<String> sort(String arg0, SortingParams arg1) ;

    
    /***
     * 多机无法实现此操作，会操成操作不一至。 
     */
    public String spop(String arg0) ;

    
    /***
     * 随机返回名称为 key的set的一个元素，但是不删除元素 
     */
    public String srandmember(String arg0);

    
    /***
     * 删除名称为key的 set中的元素member 
     */
    public Long srem(String arg0, String... arg1) ;

    
    /***
     * 以秒为单位，返回给定 key 的剩余生存时间
     */
    public Long ttl(String arg0) ;

    
    /***
     * 返回 key 所储存的值的类型
     */
    public String type(String arg0) ;

    
    /***
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中
     */
//    public Long zadd(String arg0, Map<Double, String> arg1) ;

    
    /***
     * 向名称为 key 的 zset 中添加元素 member，score 用于排序。如果该元素已经存在，则根据
        score 更新该元素的顺序
     */
    public Long zadd(String arg0, double arg1, String arg2);

    
    /***
     * 返回集合中元素个数 
     */
    public Long zcard(String arg0) ;
    
    /***
     * 返回集合中score在给定区间的数量
     */
    public Long zcount(String arg0, double arg1, double arg2);
    /***
     * 返回集合中score在给定区间的数量
     */
    
    public Long zcount(String arg0, String arg1, String arg2);

    
    /***
     *  如果在名称为key 的zset中已经存在元素 member，则该元素的score增加 increment；否则
        向集合中添加该元素，其 score 的值为increment 
     */
    public Double zincrby(String arg0, double arg1, String arg2) ;

    
    /***
     * 返回有序集 key 中，指定区间内的成员
     */
    public Set<String> zrange(String arg0, long arg1, long arg2) ;

    
    /***
     * 返回集合中score在给定区间的元素
     */
    public Set<String> zrangeByScore(String arg0, double arg1, double arg2) ;

    
    public Set<String> zrangeByScore(String arg0, String arg1, String arg2) ;

    
    public Set<String> zrangeByScore(String arg0, double arg1, double arg2,
                                     int arg3, int arg4) ;

    
    public Set<String> zrangeByScore(String arg0, String arg1, String arg2,
                                     int arg3, int arg4) ;

    
    public Set<Tuple> zrangeByScoreWithScores(String arg0, double arg1,
                                              double arg2);

    
    public Set<Tuple> zrangeByScoreWithScores(String arg0, String arg1,
                                              String arg2) ;

    
    public Set<Tuple> zrangeByScoreWithScores(String arg0, double arg1,
                                              double arg2, int arg3, int arg4) ;
    
    public Set<Tuple> zrangeByScoreWithScores(String arg0, String arg1,
                                              String arg2, int arg3, int arg4) ;

    
    public Set<Tuple> zrangeWithScores(String arg0, long arg1, long arg2) ;

    
    /***
     * 返回有序集 key 中成员 member 的排名
     */
    public Long zrank(String arg0, String arg1) ;

    
    public Long zrem(String arg0, String... arg1) ;

    
    /***
     * 移除有序集 key 中，指定排名(rank)区间内的所有成员
     */
    public Long zremrangeByRank(String arg0, long arg1, long arg2) ;

    
    /***
     * 移除有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
     */
    public Long zremrangeByScore(String arg0, double arg1, double arg2) ;

    
    /***
     * 移除有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
     */
    public Long zremrangeByScore(String arg0, String arg1, String arg2) ;

    
    /***
     * 返回有序集 key 中，指定区间内的成员。
     */
    public Set<String> zrevrange(String arg0, long arg1, long arg2) ;

    
    /***
     * 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。
     * 有序集成员按 score 值递减(从大到小)的次序排列。
     */
    public Set<String> zrevrangeByScore(String arg0, double arg1, double arg2) ;

    
    /***
     * 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。
     * 有序集成员按 score 值递减(从大到小)的次序排列
     */
    public Set<String> zrevrangeByScore(String arg0, String arg1, String arg2) ;

    
    /***
     * 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。
     * 有序集成员按 score 值递减(从大到小)的次序排列
     */
    public Set<String> zrevrangeByScore(String arg0, double arg1, double arg2,
                                        int arg3, int arg4) ;

    
    /***
     * 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。
     * 有序集成员按 score 值递减(从大到小)的次序排列
     */
    public Set<String> zrevrangeByScore(String arg0, String arg1, String arg2,
                                        int arg3, int arg4) ;

    /***
     * 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。
     * 有序集成员按 score 值递减(从大到小)的次序排列
     */
    public Set<Tuple> zrevrangeWithScores(String key, long start, long end);

    
    /***
     * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递减(从大到小)排序。
        排名以 0 为底，也就是说， score 值最大的成员排名为 0 。
     */
    public Long zrevrank(String arg0, String arg1) ;

    
    /***
     * 返回有序集 key 中，成员 member 的 score 值。
       如果 member 元素不是有序集 key 的成员，或 key 不存在，返回 nil 
     */
    public Double zscore(String arg0, String arg1) ;
    
    /**
     * 管道，map为k，v格式的数据，后面的是时间，返回是否操作成功
     */
    boolean setPipelineEx(String arg0, Map<?, ?> storeMap, int seconds);
    
    /**
     * 查找所有符合给定模式 pattern 的 key 。
     * @param key
     * @return
     */
    Set<String> keys(String key);

}
