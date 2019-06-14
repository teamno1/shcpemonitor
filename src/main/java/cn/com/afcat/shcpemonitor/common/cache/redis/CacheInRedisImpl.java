package cn.com.afcat.shcpemonitor.common.cache.redis;

import cn.com.afcat.shcpemonitor.common.cache.base.ICache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Set;


/**
 * 
 * 
 * @author:tao
 */
public class CacheInRedisImpl implements ICache {

    private IRedisOperate redis;

    private String space;

    public CacheInRedisImpl(String name) {
        space = name;
        redis = RedisFactory.getInstance().getRedisByAppId("redis_cache", null);
    }

    @Override
    public Object put(Object key, Object value) {
        return redis.set(getSpaceKey((String) key),
                serialize(value));
    }

    @Override
    public boolean set(Object key, Object value) {
        redis.set(getSpaceKey((String) key), serialize(value));
        return true;
    }

    @Override
    public Object get(Object key) {
        return unserialize(redis.getbyte(getSpaceKey((String) key)));
    }

    @Override
    public Object remove(Object key) {
        return redis.expireAt(getSpaceKey((String) key), 0);
    }

    @Override
    public boolean containsKey(Object key) {
        return redis.exists(getSpaceKey((String) key));
    }

    @Override
    public Set keySet() {
        return redis.keys("*");// 不推荐
    }

    @Override
    public Collection values() {
        // redis.
        return null;
    }

    @Override
    public boolean isEmpty() {
        return keySet().isEmpty();
    }

    @Override
    public int size() {
        return keySet().size();
    }

    @Override
    public void clear() {
        // redis.
    }

    /**
     * 获得spacekey
     * 
     * @param key
     * @return
     */
    private String getSpaceKey(String key) {
        return space + key;
    }

    public byte[] serialize(Object object) {

        ObjectOutputStream oos = null;

        ByteArrayOutputStream baos = null;

        try {

        //序列化

        baos = new ByteArrayOutputStream();

        oos = new ObjectOutputStream(baos);

        oos.writeObject(object);

        byte[] bytes = baos.toByteArray();

        return bytes;

        } catch (Exception e) {

         

        }

        return null;

        }

    public Object unserialize(byte[] bytes) {

        ByteArrayInputStream bais = null;

        try {

        //反序列化

        bais = new ByteArrayInputStream(bytes);

        ObjectInputStream ois = new ObjectInputStream(bais);

        return ois.readObject();

        } catch (Exception e) {

         

        }

        return null;

        }
   /* public RedisImpl() {
    }
    
    public static void main(String[] args) {
      Branch brch=new Branch();
      brch.setAddress("北京");
      brch.setBankNo("3031000002");
      
      RedisImpl r=new RedisImpl();
      byte[] ss=r.serialize(brch);
      System.out.println(ss);
      Branch bb=(Branch) r.unserialize(ss);
      System.out.println(bb.getAddress());
      
       
    }*/

}
