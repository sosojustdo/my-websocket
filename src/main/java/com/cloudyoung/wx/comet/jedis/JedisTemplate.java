package com.cloudyoung.wx.comet.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.JedisSentinelPool;
import java.util.Set;

public class JedisTemplate {
    
    private  JedisSentinelPool jedisSentinelPool;
    
    private  JedisPool jedisPool;
    
    /**
     * @Description 获取jedis实例 
     * @return    
     * @return Jedis     
     * @version V1.0
     * @auth    邹立强   (zoulq@cloud-young.com)
     * 2018年3月20日 下午2:47:27
     */
    public  Jedis getJedis() {
        if(jedisSentinelPool!=null) {
            return jedisSentinelPool.getResource();   
        }else if(jedisPool!=null) {
            return jedisPool.getResource();
        }else {
            return null;
        }
    }

    /**
     * @Description 释放jedis资源 
     * @param jedis    
     * @return void     
     * @version V1.0
     * @auth    邹立强   (zoulq@cloud-young.com)
     * 2018年3月20日 下午2:47:55
     */
    public  void returnJedis(Jedis jedis) {  
        if (jedis != null) {  
            jedis.close(); 
        }  
    }  
    
    /**
     * @Description 发布 
     * @param channel
     * @param message
     * @return    
     * @return Long     
     * @version V1.0
     * @auth    邹立强   (zoulq@cloud-young.com)
     * 2018年3月20日 下午2:44:48
     */
    public Long publish(String channel, String message) {
        Jedis jedis = getJedis();
        Long data = jedis.publish(channel, message);
        returnJedis(jedis);
        return data;
    }

    /**
     * @Description 订阅频道 
     * @param jedisPubSub
     * @param channels    
     * @return void     
     * @version V1.0
     * @auth    邹立强   (zoulq@cloud-young.com)
     * 2018年3月20日 下午2:48:15
     */
    public void subscribe(JedisPubSub jedisPubSub, String... channels) {
        Jedis jedis = getJedis();
        jedis.subscribe(jedisPubSub, channels);
        returnJedis(jedis);
    }

    /**
     * @Description 取消订阅频道 
     * @param jedisPubSub
     * @param channels    
     * @return void     
     * @version V1.0
     * @auth    邹立强   (zoulq@cloud-young.com)
     * 2018年3月20日 下午2:48:37
     */
    public void unsubscribe(JedisPubSub jedisPubSub, String... channels) {
        jedisPubSub.unsubscribe(channels);
    }
    
    /**
     * @Description set添加 成员
     * @param key
     * @param member
     * @return void
     * @version V1.0
     * @auth    邹立强   (zoulq@cloud-young.com)
     * 2018年3月20日 下午5:54:36
     */
    public Long addSetMember(String key, String member) {
        Jedis jedis = getJedis();
        Long sadd = jedis.sadd(key, member);
        returnJedis(jedis);
        return sadd;
    }
    
    /**
     * @Description set移除成员
     * @param key
     * @param member
     * @return void     
     * @version V1.0
     * @auth    邹立强   (zoulq@cloud-young.com)
     * 2018年3月20日 下午5:58:31
     */
    public Long removeSetMember(String key, String member) {
        Jedis jedis = getJedis();
        Long srem = jedis.srem(key, member);
        returnJedis(jedis);
        return srem;
    }
    
    /**
     * @Description 获取set成员集合 
     * @param key
     * @return    
     * @return Set<String>     
     * @version V1.0
     * @auth    邹立强   (zoulq@cloud-young.com)
     * 2018年3月20日 下午6:08:26
     */
    public Set<String> getSetMembers(String key) {
        Jedis jedis = getJedis();
        Set<String> smembers = jedis.smembers(key);
        returnJedis(jedis);
        return smembers;
    }
    
    /**
     * @Description 删除key 
     * @param key
     * @return    
     * @return Long     
     * @version V1.0
     * @auth    邹立强   (zoulq@cloud-young.com)
     * 2018年3月20日 下午6:04:22
     */
    public Long deleteKey(String key) {
        Jedis jedis = getJedis();
        Long del = jedis.del(key);
        returnJedis(jedis);
        return del;
    }

    public JedisSentinelPool getJedisSentinelPool() {
        return jedisSentinelPool;
    }

    public void setJedisSentinelPool(JedisSentinelPool jedisSentinelPool) {
        this.jedisSentinelPool = jedisSentinelPool;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
