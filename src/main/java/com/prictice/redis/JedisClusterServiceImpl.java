package com.prictice.redis;

import com.prictice.redis.service.JedisClusterService;
import redis.clients.jedis.JedisCluster;

/**
 * @author 苏博
 * @className: JedisCluster.java
 * @package com.prictice.redis
 * @description: {jedisCluster文档 "http://xetorthio.github.io/jedis/"}
 * @date 2019/12/11 17:19
 */
public class JedisClusterServiceImpl implements JedisClusterService {

    private JedisCluster jedisCluster;

    public JedisClusterServiceImpl() {
        try {
            init();
        }catch (Exception e){

        };
    }

    /**
     * 初始化对象
     */
    private void init() throws Exception{
        this.jedisCluster = RedisUtil.initJedisCluster();
    }

    /**
     * 装饰key 在key前面拼接前缀
     */
    private String decorateKey(String key){
        return RedisUtil.REDIS_PREFIX + key;
    }

    @Override
    public String set(String key,String value){
        key = decorateKey(key);
        return jedisCluster.set(key,value);
    }

    @Override
    public String get(String key) {
        key = decorateKey(key);
        return jedisCluster.get(key);
    }

    @Override
    public String del(String key) {
        key = decorateKey(key);
        return jedisCluster.del(key).toString();
    }

    @Override
    public String ttl(String key) {
        key = decorateKey(key);
        return jedisCluster.ttl(key).toString();
    }

    @Override
    public String type(String key) {
        key = decorateKey(key);
        return jedisCluster.type(key);
    }


    @Override
    public String setnx(String key, String value) {
        key = decorateKey(key);
        return jedisCluster.setnx(key,value).toString();
    }

    @Override
    public String setex(String key, int time, String value) {
        key = decorateKey(key);
        return jedisCluster.setex(key,time,value);
    }
}
