package cn.com.afcat.shcpemonitor.common.cache.redis.shard;

import cn.com.afcat.shcpemonitor.common.cache.redis.IRedisOperate;
import cn.com.afcat.shcpemonitor.common.cache.redis.RedisException;
import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class ShardRedis implements IRedisOperate {
	
	private static ShardJedisManager shardJedisManager = ShardJedisManager.getInstance();
	private ShardedJedisPool shardedJedisPool;
	public ShardRedis(String nodeId){
		shardedJedisPool = shardJedisManager.getShardedJedisPool(nodeId);
		if(shardedJedisPool == null){
			throw new RedisException("shardedJedisPool node["+nodeId+"] is null");
		}
	}
	/**
	 * 给指定key的字符串值追加 value,返回新字符串值的长度。
	 * @param key
	 * @param value
	 * @return
	 */
	public Long append(String key, String value) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.append(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	/**
	 * 对key的值做的是减减操作，decr一个不存在 key，则设置key为-1
	 * @param key
	 * @return
	 * @see com.herongtech.cache.redis.IRedisOperate#decr(String)
	 */
	public Long decr(String key) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.decr(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	/**
	 * 对 key 减去指定值 ， key 不存在时候会设置 key ，并认为原来的 value为0
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param key
	 * @param value
	 * @return
	 * @see com.herongtech.cache.redis.IRedisOperate#decrBy(String, long)
	 */
	public Long decrBy(String key, long value) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.decrBy(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	/**
	 * 判断指定键是否存在
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param key
	 * @return
	 * @see com.herongtech.cache.redis.IRedisOperate#exists(String)
	 */
	public Boolean exists(String key) {
		Boolean l = false;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.exists(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	/**
	 * 设置一个key 的过期时间(单位:秒)， 返回 1 成功 ，0 表示 key已经设置过过期时间或者不存在
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param key
	 * @param value
	 * @return
	 * @see com.herongtech.cache.redis.IRedisOperate#expire(String, int)
	 */
	public Long expire(String key, int value) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.expire(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	public Long expireAt(String arg0, long arg1) {
		return null;
	}

	/**
	 * 获取key 对应的string值,如果key 不存在返回 nil。
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param key
	 * @return
	 * @see com.herongtech.cache.redis.IRedisOperate#get(String)
	 */
	public String get(String key) {
		String l = null;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}
	
	public byte[] getbyte(String key) {
        byte[] l = null;
        
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            l = jedis.get(key.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(jedis != null){
                try {
                    shardedJedisPool.returnResource(jedis);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return l;
    }

	/**
	 * 先获取 key的值，再设置 key的值。如果 key不存在返回 null 。
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param key
	 * @param value
	 * @return
	 * @see com.herongtech.cache.redis.IRedisOperate#getSet(String, String)
	 */
	public String getSet(String key, String value) {
		String l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.getSet(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	public Boolean getbit(String arg0, long arg1) {
		return null;
	}

	/**
	 * 返回 key 中字符串值的子字符串，字符串的截取范围由 start 和 end 两个偏移量决定(包括 start 和 end 在内)。
	 * 负数偏移量表示从字符串最后开始计数， -1 表示最后一个字符， -2 表示倒数第二个，以此类推。
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 * @see com.herongtech.cache.redis.IRedisOperate#getrange(String, long, long)
	 */
	public String getrange(String key, long start, long end) {
		String l = null;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.getrange(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	/**
	 * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param key
	 * @param arg1
	 * @return
	 * @see com.herongtech.cache.redis.IRedisOperate#hdel(String, String[])
	 */
	public Long hdel(String key, String... arg1) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.hdel(key, arg1);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	/**
	 * 查看哈希表 key 中，给定域 field 是否存在。
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param key
	 * @param field
	 * @return
	 * @see com.herongtech.cache.redis.IRedisOperate#hexists(String, String)
	 */
	public Boolean hexists(String key, String field) {
		Boolean l = false;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.hexists(key, field);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	/**
	 * 返回哈希表 key 中给定域 field 的值。
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param key
	 * @param filed
	 * @return
	 * @see com.herongtech.cache.redis.IRedisOperate#hget(String, String)
	 */
	public String hget(String key, String filed) {
		String l = null;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.hget(key, filed);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	/**
	 * 返回哈希表 key 中，所有的域和值。
	 * 在返回值里，紧跟每个域名(field name)之后是域的值(value)，所以返回值的长度是哈希表大小的两倍。
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param key
	 * @return
	 * @see com.herongtech.cache.redis.IRedisOperate#hgetAll(String)
	 */
	public Map<String, String> hgetAll(String key) {
		Map<String, String> l = null;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.hgetAll(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 为哈希表 key 中的域 field 的值加上增量 increment 。
	 * 增量也可以为负数，相当于对给定域进行减法操作。
	 * 如果 key 不存在，一个新的哈希表被创建并执行 HINCRBY 命令。
	 * 如果域 field 不存在，那么在执行命令前，域的值被初始化为 0 。
	 * 对一个储存字符串值的域 field 执行 HINCRBY 命令将造成一个错误。
	 * 本操作的值被限制在 64 位(bit)有符号数字表示之内。
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param key
	 * @param field
	 * @param increment
	 * @return 执行 HINCRBY 命令之后，哈希表 key 中域 field 的值。
	 * @see com.herongtech.cache.redis.IRedisOperate#hincrBy(String, String, long)
	 */
	public Long hincrBy(String key, String field, long increment) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.hincrBy(key, field, increment);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// 保证大于0--各库数据不一至可能会有问题。
		if (l < 0) {
			l = hset(key, field, "0");
		}
		return l;
	}

	
	/**
	 * 返回哈希表 key 中的所有域。
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param key
	 * @return 一个包含哈希表中所有域的表。当 key 不存在时，返回一个空表。
	 * @see com.herongtech.cache.redis.IRedisOperate#hkeys(String)
	 */
	public Set<String> hkeys(String key) {
		Set<String> l = null;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.hkeys(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 返回哈希表 key 中field的数量。
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param key
	 * @return 哈希表中域的数量。当 key 不存在时，返回 0 。
	 * @see com.herongtech.cache.redis.IRedisOperate#hlen(String)
	 */
	public Long hlen(String key) {
		Long l = null;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.hlen(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 返回哈希表 key 中，一个或多个给定域的值。
	 * 如果给定的域不存在于哈希表，那么返回一个 nil 值。
	 * 因为不存在的 key 被当作一个空哈希表来处理，所以对一个不存在的 key 进行 HMGET 操作将返回一个只带有 nil 值的表。
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param key
	 * @param field
	 * @return 一个包含多个给定域的关联值的表，表值的排列顺序和给定域参数的请求顺序一样。
	 * @see com.herongtech.cache.redis.IRedisOperate#hmget(String, String[])
	 */
	public List<String> hmget(String key, String... field) {
		List<String> l = null;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.hmget(key, field);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}
	
	/**
	 * 同时将多个 field-value (域-值)对设置到哈希表 key 中。
	 * 此命令会覆盖哈希表中已存在的域。
	 * 如果 key 不存在，一个空哈希表被创建并执行 HMSET 操作。
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param key
	 * @param arg1
	 * @return 如果命令执行成功，返回 OK 。当 key 不是哈希表(hash)类型时，返回一个错误。
	 * @see com.herongtech.cache.redis.IRedisOperate#hmset(String, Map)
	 */
	public String hmset(String key, Map<String, String> arg1) {
		String l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.hmset(key, arg1);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	/**
	 * 将哈希表 key 中的域 field 的值设为 value 。
	 * 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。
	 * 如果域 field 已经存在于哈希表中，旧值将被覆盖。
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param key
	 * @param field
	 * @param value
	 * @return 如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。
	 * 		        如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
	 * @see com.herongtech.cache.redis.IRedisOperate#hset(String, String, String)
	 */
	public Long hset(String key, String field, String value) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.hset(key, field, value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 将哈希表 key 中的域 field 的值设置为 value ，当且仅当域 field 不存在。
	 * 若域 field 已经存在，该操作无效。
	 * 如果 key 不存在，一个新哈希表被创建并执行 HSETNX 命令。
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param arg0
	 * @param field
	 * @param value
	 * @return 设置成功，返回 1 。
			        如果给定域已经存在且没有操作被执行，返回 0 。
	 * @see com.herongtech.cache.redis.IRedisOperate#hsetnx(String, String, String)
	 */
	public Long hsetnx(String arg0, String field, String value) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.hsetnx(arg0, field, value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 返回哈希表 key 中所有域的值。
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param arg0
	 * @return 一个包含哈希表中所有值的表。
	  		        当 key 不存在时，返回一个空表。
	 * @see com.herongtech.cache.redis.IRedisOperate#hvals(String)
	 */
	public List<String> hvals(String arg0) {
		List<String> l = null;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.hvals(arg0);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 将 key 中储存的数字值增一。
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
	 * 本操作的值限制在 64 位(bit)有符号数字表示之内。
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param key
	 * @return 执行 INCR 命令之后 key 的值。
	 * @see com.herongtech.cache.redis.IRedisOperate#incr(String)
	 */
	public Long incr(String key) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.incr(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 将 key 所储存的值加上增量 increment 。
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
	 * 本操作的值限制在 64 位(bit)有符号数字表示之内。
	 * 关于递增(increment) / 递减(decrement)操作的更多信息，参见 INCR 命令。
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param key
	 * @param increment
	 * @return 加上 increment 之后， key 的值。
	 * @see com.herongtech.cache.redis.IRedisOperate#incrBy(String, long)
	 */
	public Long incrBy(String key, long increment) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.incrBy(key, increment);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 返回列表 key 中，下标为 index 的元素。
	 * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
	 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
	 * 如果 key 不是列表类型，返回一个错误。
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param key
	 * @param index
	 * @return 列表中下标为 index 的元素。
			        如果 index 参数的值不在列表的区间范围内(out of range)，返回 nil 。
	 * @see com.herongtech.cache.redis.IRedisOperate#lindex(String, long)
	 */
	public String lindex(String key, long index) {
		String l = null;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.lindex(key, index);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 将值 value 插入到列表 key 当中，位于值 pivot 之前或之后。
	 * 当 pivot 不存在于列表 key 时，不执行任何操作。
	 * 当 key 不存在时， key 被视为空列表，不执行任何操作。
	 * 如果 key 不是列表类型，返回一个错误。
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param key
	 * @param position
	 * @param pivot
	 * @param value
	 * @return 如果命令执行成功，返回插入操作完成之后，列表的长度。
			        如果没有找到 pivot ，返回 -1 。
			        如果 key 不存在或为空列表，返回 0 。
	 * @see com.herongtech.cache.redis.IRedisOperate#linsert(String, redis.clients.jedis.BinaryClient.LIST_POSITION, String, String)
	 */
	public Long linsert(String key, LIST_POSITION position, String pivot,
			String value) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.linsert(key, position, pivot, value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 返回列表 key 的长度。
	 * 如果 key 不存在，则 key 被解释为一个空列表，返回 0 .
	 * 如果 key 不是列表类型，返回一个错误。
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param key
	 * @return 列表 key 的长度。
	 * @see com.herongtech.cache.redis.IRedisOperate#llen(String)
	 */
	public Long llen(String key) {
		Long l = null;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.llen(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 移除并返回列表 key 的头元素。
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param key
	 * @return 列表的头元素。
			        当 key 不存在时，返回 nil 。
	 * @see com.herongtech.cache.redis.IRedisOperate#lpop(String)
	 */
	public String lpop(String key) {
		String l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.lpop(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 将一个或多个值 value 插入到列表 key 的表头
	 * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头： 比如说，对空列表 mylist 执行命令 LPUSH mylist a b c ，列表的值将是 c b a ，这等同于原子性地执行 LPUSH mylist a 、 LPUSH mylist b 和 LPUSH mylist c 三个命令。
	 * 如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作。
	 * 当 key 存在但不是列表类型时，返回一个错误。
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param key
	 * @param values
	 * @return 执行 LPUSH 命令后，列表的长度。
	 * @see com.herongtech.cache.redis.IRedisOperate#lpush(String, String[])
	 */
	public Long lpush(String key, String... values) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.lpush(key, values);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 将值 value 插入到列表 key 的表头，当且仅当 key 存在并且是一个列表。
	 * 和 LPUSH 命令相反，当 key 不存在时， LPUSHX 命令什么也不做。
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param key
	 * @param value
	 * @return LPUSHX 命令执行之后，表的长度
	 * @see com.herongtech.cache.redis.IRedisOperate#lpushx(String, String)
	 */
	public Long lpushx(String key, String value) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.lpushx(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	/**
	 * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。
	 * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
	 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
	 * 
	 * <P>Date : 2013-1-31 </P>
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 * @see com.herongtech.cache.redis.IRedisOperate#lrange(String, long, long)
	 */
	public List<String> lrange(String key, long start, long end) {
		List<String> l = null;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.lrange(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 根据参数 count 的值，移除列表中与参数 value 相等的元素。
	 * count 的值可以是以下几种：
	 * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
	 * count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
	 * count = 0 : 移除表中所有与 value 相等的值。
	 * 
	 * 
	 * @param key
	 * @param count
	 * @param value
	 * @return 被移除元素的数量。
			        因为不存在的 key 被视作空表(empty list)，所以当 key 不存在时， LREM 命令总是返回 0 。
	 * @see com.herongtech.cache.redis.IRedisOperate#lrem(String, long, String)
	 */
	public Long lrem(String key, long count, String value) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.lrem(key, count, value);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 将列表 key 下标为 index 的元素的值设置为 value 。
	 * 当 index 参数超出范围，或对一个空列表( key 不存在)进行 LSET 时，返回一个错误。
	 * @param key
	 * @param index
	 * @param value
	 * @return 操作成功返回 ok ，否则返回错误信息。
	 * @see com.herongtech.cache.redis.IRedisOperate#lset(String, long, String)
	 */
	public String lset(String key, long index, String value) {
		String l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.lset(key, index, value);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
	 * 举个例子，执行命令 LTRIM list 0 2 ，表示只保留列表 list 的前三个元素，其余元素全部删除。
	 * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
	 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
	 * 当 key 不是列表类型时，返回一个错误。
	 * @param key
	 * @param start
	 * @param end
	 * @return 命令执行成功时，返回 ok 。
	 * @see com.herongtech.cache.redis.IRedisOperate#ltrim(String, long, long)
	 */
	public String ltrim(String key, long start, long end) {
		String l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.ltrim(key, start, end);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 移除并返回列表 key 的尾元素。
	 * @param key
	 * @return 列表的尾元素。
			        当 key 不存在时，返回 nil 。
	 * @see com.herongtech.cache.redis.IRedisOperate#rpop(String)
	 */
	public String rpop(String key) {
		String l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.rpop(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。
	 * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表尾：比如对一个空列表 mylist 执行 RPUSH mylist a b c ，得出的结果列表为 a b c ，等同于执行命令 RPUSH mylist a 、 RPUSH mylist b 、 RPUSH mylist c 。
	 * 如果 key 不存在，一个空列表会被创建并执行 RPUSH 操作。
	 * 当 key 存在但不是列表类型时，返回一个错误。
	 * @param key
	 * @param value
	 * @return
	 * @see com.herongtech.cache.redis.IRedisOperate#rpush(String, String[])
	 */
	public Long rpush(String key, String... value) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.rpush(key,value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 将值 value 插入到列表 key 的表尾，当且仅当 key 存在并且是一个列表。
	 * 和 RPUSH 命令相反，当 key 不存在时， RPUSHX 命令什么也不做。
	 * @param key
	 * @param value
	 * @return RPUSHX 命令执行之后，表的长度。
	 * @see com.herongtech.cache.redis.IRedisOperate#rpushx(String, String)
	 */
	public Long rpushx(String key, String value) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.rpushx(key, value);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。
	 * 假如 key 不存在，则创建一个只包含 member 元素作成员的集合。
	 * 当 key 不是集合类型时，返回一个错误。
	 * @param key
	 * @param member
	 * @return 被添加到集合中的新元素的数量，不包括被忽略的元素。
	 * @see com.herongtech.cache.redis.IRedisOperate#sadd(String, String[])
	 */
	public Long sadd(String key, String... member) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.sadd(key, member);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 返回集合 key 的基数(集合中元素的数量)。
	 * @param key
	 * @return
	 * @see com.herongtech.cache.redis.IRedisOperate#scard(String)
	 */
	public Long scard(String key) {
		Long l = null;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.scard(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}
	
	/**
	 * 将字符串值 value 关联到 key 。
	 * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
	 * @param key
	 * @param value
	 * @return 总是返回 OK ，因为 SET 不可能失败。
	 * @see com.herongtech.cache.redis.IRedisOperate#set(String, String)
	 */
	public String set(String key, String value) {
		String l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	@Override
    public String set(String key, byte[] value) {
	    String l = null;
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            l = jedis.set(key.getBytes(), value);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(jedis != null){
                try {
                    shardedJedisPool.returnResource(jedis);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return l;
    }
	
	/**
	 * 对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)。
	 * 位的设置或清除取决于 value 参数，可以是 0 也可以是 1 。
	 * 当 key 不存在时，自动生成一个新的字符串值。
	 * 字符串会进行伸展(grown)以确保它可以将 value 保存在指定的偏移量上。当字符串值进行伸展时，空白位置以 0 填充。
	 * offset 参数必须大于或等于 0 ，小于 2^32 (bit 映射被限制在 512 MB 之内)。
	 * @param key
	 * @param offset
	 * @param value
	 * @return 指定偏移量原来储存的位
	 * @see com.herongtech.cache.redis.IRedisOperate#setbit(String, long, boolean)
	 */
	public Boolean setbit(String key, long offset, boolean value) {
		Boolean l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.setbit(key, offset, value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。
	 * 如果 key 已经存在， SETEX 命令将覆写旧值。
	 * @param key
	 * @param seconds
	 * @param value
	 * @return 设置成功时返回 OK 。
	 		        当 seconds 参数不合法时，返回一个错误。
	 * @see com.herongtech.cache.redis.IRedisOperate#setex(String, int, String)
	 */
	public String setex(String key, int seconds, String value) {
		String l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.setex(key, seconds, value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	/**
	 * 将 key 的值设为 value ，当且仅当 key 不存在。
	 * 若给定的 key 已经存在，则 SETNX 不做任何动作。
	 * @param key
	 * @param value
	 * @return 设置成功，返回 1 。
			        设置失败，返回 0 。
	 * @see com.herongtech.cache.redis.IRedisOperate#setnx(String, String)
	 */
	public Long setnx(String key, String value) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.setnx(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 用 value 参数覆写(overwrite)给定 key 所储存的字符串值，从偏移量 offset 开始。
	 * 不存在的 key 当作空白字符串处理。
	 * @param key
	 * @param offset
	 * @param value
	 * @return 被 SETRANGE 修改之后，字符串的长度。
	 * @see com.herongtech.cache.redis.IRedisOperate#setrange(String, long, String)
	 */
	public Long setrange(String key, long offset, String value) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.setrange(key, offset, value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	/**
	 * 判断 member 元素是否集合 key 的成员。
	 * @param key
	 * @param member
	 * @return 如果 member 元素是集合的成员，返回 1 。
			        如果 member 元素不是集合的成员，或 key 不存在，返回 0 。
	 * @see com.herongtech.cache.redis.IRedisOperate#sismember(String, String)
	 */
	public Boolean sismember(String key, String member) {
		Boolean l = null;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.sismember(key, member);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 返回集合 key 中的所有成员。
	 * 不存在的 key 被视为空集合。
	 * @param key
	 * @return 集合中的所有成员。
	 * @see com.herongtech.cache.redis.IRedisOperate#smembers(String)
	 */
	public Set<String> smembers(String key) {
		Set<String> l = null;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.smembers(key);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	/**
	 * 返回给定列表、集合、有序集合 key 中经过排序的元素。
	 * 排序默认以数字作为对象，值被解释为双精度浮点数，然后进行比较。
	 * @param key
	 * @return
	 * @see com.herongtech.cache.redis.IRedisOperate#sort(String)
	 */
	public List<String> sort(String key) {
		List<String> l = null;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.sort(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 返回或保存给定列表、集合、有序集合 key 中经过排序的元素。
	 * 排序默认以数字作为对象，值被解释为双精度浮点数，然后进行比较。
	 * @param key
	 * @param param
	 * @return
	 * @see com.herongtech.cache.redis.IRedisOperate#sort(String, redis.clients.jedis.SortingParams)
	 */
	public List<String> sort(String key, SortingParams param) {
		List<String> l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.sort(key, param);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/***************************************************************************
	 * 多机无法实现此操作，会操成操作不一至。
	 */
	public String spop(String arg0) {
		return null;
	}

	/**
	 * 返回集合中的一个随机元素。
	 * @param key
	 * @return
	 * @see com.herongtech.cache.redis.IRedisOperate#srandmember(String)
	 */
	public String srandmember(String key) {
		String l = null;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.srandmember(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。
	 * 当 key 不是集合类型，返回一个错误。
	 * @param key
	 * @param member
	 * @return 被成功移除的元素的数量，不包括被忽略的元素。
	 * @see com.herongtech.cache.redis.IRedisOperate#srem(String, String[])
	 */
	public Long srem(String key, String... member) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.srem(key, member);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)。
	 * @param key
	 * @return 当 key 不存在时，返回 -2 。
				当 key 存在但没有设置剩余生存时间时，返回 -1 。
				否则，以秒为单位，返回 key 的剩余生存时间。
	 * @see com.herongtech.cache.redis.IRedisOperate#ttl(String)
	 */
	public Long ttl(String key) {
		Long l = null;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.ttl(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 返回 key 所储存的值的类型。
	 * @param key
	 * @return
	 			none (key不存在)
				string (字符串)
				list (列表)
				set (集合)
				zset (有序集)
				hash (哈希表)
	 * @see com.herongtech.cache.redis.IRedisOperate#type(String)
	 */
	public String type(String key) {
		String l = null;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.type(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	

	
	/**
	 * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
	 * 如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。
	 * score 值可以是整数值或双精度浮点数。
	 * 如果 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
	 * 当 key 存在但不是有序集类型时，返回一个错误。
	 * @param key
	 * @param score
	 * @param member
	 * @return 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员。
	 * @see com.herongtech.cache.redis.IRedisOperate#zadd(String, double, String)
	 */
	public Long zadd(String key, double score, String member) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zadd(key, score, member);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	/**
	 * 返回有序集 key 的基数。
	 * @param key
	 * @return 当 key 存在且是有序集类型时，返回有序集的基数。
				当 key 不存在时，返回 0 。
	 * @see com.herongtech.cache.redis.IRedisOperate#zcard(String)
	 */
	public Long zcard(String key) {
		Long l = null;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zcard(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量。
	 * @param key
	 * @param min
	 * @param max
	 * @return score 值在 min 和 max 之间的成员的数量。
	 * @see com.herongtech.cache.redis.IRedisOperate#zcount(String, double, double)
	 */
	public Long zcount(String key, double min, double max) {
		Long l = null;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zcount(key, min, max);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	/***************************************************************************
	 * 返回集合中score在给定区间的数量
	 */
	
	public Long zcount(String arg0, String arg1, String arg2) {
		Long l = null;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zcount(arg0, arg1, arg2);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 为有序集 key 的成员 member 的 score 值加上增量 increment 。
	 * 可以通过传递一个负数值 increment ，让 score 减去相应的值，比如 ZINCRBY key -5 member ，就是让 member 的 score 值减去 5 。
	 * 当 key 不存在，或 member 不是 key 的成员时， ZINCRBY key increment member 等同于 ZADD key increment member 。
	 * 当 key 不是有序集类型时，返回一个错误。
	 * score 值可以是整数值或双精度浮点数。
	 * @param key
	 * @param increment
	 * @param member
	 * @return member 成员的新 score 值，以字符串形式表示。
	 * @see com.herongtech.cache.redis.IRedisOperate#zincrby(String, double, String)
	 */
	public Double zincrby(String key, double increment, String member) {
		Double l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zincrby(key, increment, member);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	/**
	 * 返回有序集 key 中，指定区间内的成员。
	 * @param key
	 * @param start
	 * @param stop
	 * @return 指定区间内，带有 score 值(可选)的有序集成员的列表。
	 * @see com.herongtech.cache.redis.IRedisOperate#zrange(String, long, long)
	 */
	public Set<String> zrange(String key, long start, long stop) {
		Set<String> l = null;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zrange(key, start, stop);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/**
	 * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列。
	 * @param key
	 * @param min
	 * @param max
	 * @return 指定区间内，带有 score 值(可选)的有序集成员的列表。
	 * @see com.herongtech.cache.redis.IRedisOperate#zrangeByScore(String, double, double)
	 */
	public Set<String> zrangeByScore(String key, double min, double max) {
		Set<String> l = null;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zrangeByScore(key, min, max);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	public Set<String> zrangeByScore(String arg0, String arg1, String arg2) {
		Set<String> l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zrangeByScore(arg0, arg1, arg2);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	public Set<String> zrangeByScore(String arg0, double arg1, double arg2,
			int arg3, int arg4) {
		Set<String> l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zrangeByScore(arg0, arg1, arg2, arg3, arg4);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	public Set<String> zrangeByScore(String arg0, String arg1, String arg2,
			int arg3, int arg4) {
		Set<String> l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zrangeByScore(arg0, arg1, arg2, arg3, arg4);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	public Set<Tuple> zrangeByScoreWithScores(String arg0, double arg1,
			double arg2) {
		Set<Tuple> l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zrangeByScoreWithScores(arg0, arg1, arg2);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	public Set<Tuple> zrangeByScoreWithScores(String arg0, String arg1,
			String arg2) {
		Set<Tuple> l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zrangeByScoreWithScores(arg0, arg1, arg2);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	public Set<Tuple> zrangeByScoreWithScores(String arg0, double arg1,
			double arg2, int arg3, int arg4) {
		Set<Tuple> l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zrangeByScoreWithScores(arg0, arg1, arg2, arg3, arg4);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	public Set<Tuple> zrangeByScoreWithScores(String arg0, String arg1,
			String arg2, int arg3, int arg4) {
		Set<Tuple> l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zrangeByScoreWithScores(arg0, arg1, arg2, arg3, arg4);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	public Set<Tuple> zrangeWithScores(String arg0, long arg1, long arg2) {
		Set<Tuple> l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zrangeWithScores(arg0, arg1, arg2);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/***************************************************************************
	 * 返回有序集 key 中成员 member 的排名
	 */
	public Long zrank(String arg0, String arg1) {
		Long l = null;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zrank(arg0, arg1);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	public Long zrem(String arg0, String... arg1) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zrem(arg0, arg1);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/***************************************************************************
	 * 移除有序集 key 中，指定排名(rank)区间内的所有成员
	 */
	public Long zremrangeByRank(String arg0, long arg1, long arg2) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zremrangeByRank(arg0, arg1, arg2);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/***************************************************************************
	 * 移除有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
	 */
	public Long zremrangeByScore(String arg0, double arg1, double arg2) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zremrangeByScore(arg0, arg1, arg2);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/***************************************************************************
	 * 移除有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
	 */
	public Long zremrangeByScore(String arg0, String arg1, String arg2) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zremrangeByScore(arg0, arg1, arg2);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/***************************************************************************
	 * 返回有序集 key 中，指定区间内的成员。
	 */
	public Set<String> zrevrange(String arg0, long arg1, long arg2) {
		Set<String> l = null;
		
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zrevrange(arg0, arg1, arg2);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}
	
	
	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		Set<Tuple> l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zrevrangeWithScores(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/***************************************************************************
	 * 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。 有序集成员按
	 * score 值递减(从大到小)的次序排列。
	 */
	public Set<String> zrevrangeByScore(String arg0, double arg1, double arg2) {
		Set<String> l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zrevrangeByScore(arg0, arg1, arg2);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/***************************************************************************
	 * 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。 有序集成员按
	 * score 值递减(从大到小)的次序排列
	 */
	public Set<String> zrevrangeByScore(String arg0, String arg1, String arg2) {
		Set<String> l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zrevrangeByScore(arg0, arg1, arg2);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	
	/***************************************************************************
	 * 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。 有序集成员按
	 * score 值递减(从大到小)的次序排列
	 */
	public Set<String> zrevrangeByScore(String arg0, double arg1, double arg2,
			int arg3, int arg4) {
		Set<String> l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zrevrangeByScore(arg0, arg1, arg2, arg3, arg4);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/***************************************************************************
	 * 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。 有序集成员按
	 * score 值递减(从大到小)的次序排列
	 */
	public Set<String> zrevrangeByScore(String arg0, String arg1, String arg2,
			int arg3, int arg4) {
		Set<String> l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zrevrangeByScore(arg0, arg1, arg2, arg3, arg4);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/***************************************************************************
	 * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递减(从大到小)排序。 排名以 0 为底，也就是说， score
	 * 值最大的成员排名为 0 。
	 */
	public Long zrevrank(String arg0, String arg1) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zrevrank(arg0, arg1);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}

	
	/***************************************************************************
	 * 返回有序集 key 中，成员 member 的 score 值。 如果 member 元素不是有序集 key 的成员，或 key 不存在，返回
	 * nil
	 */
	public Double zscore(String arg0, String arg1) {
		Double l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.zscore(arg0, arg1);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}
	
	public Long del(String arg0) {
		Long l = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			l = jedis.del(arg0);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}
	@Override
	public List<String> mget(String[] keys) {
		List<String> values = new ArrayList<String>();
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			for(String key : keys){
				values.add(jedis.get(key));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return values;
	}
	
	@Override
	public boolean setPipelineEx(String arg0, Map<?, ?> storeMap, int seconds) {
		boolean flag = false;

		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			ShardedJedisPipeline pipeline = jedis.pipelined();
			for (Entry<?, ?> entry: storeMap.entrySet()) {
			   Object key = entry.getKey();
			   Object value = entry.getValue();
			   pipeline.setex(key.toString(), seconds, value.toString());
			}
			pipeline.sync();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					shardedJedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	/**
	 * 分片不支持该方法
	 * @param arg0
	 * @return
	 */
	public Set<String> keys(String arg0){
		return null;
	}

}
