package com.prictice.redis.service;

import com.prictice.redis.RedisUtil;
import redis.clients.jedis.JedisCluster;

/**
 * @author 苏博
 * @className: AbstractJedisCluster.java
 * @package com.prictice.redis.service
 * @description:
 * @date 2020/1/2 17:05
 */
public class AbstractJedisCluster {

    protected JedisCluster jedisCluster;

    public AbstractJedisCluster() {
        try {
            init();
        }catch (Exception e){

        };
    }

    /**
     * 初始化对象
     */
    protected void init() throws Exception{
        this.jedisCluster = RedisUtil.initJedisCluster();
    }

    /**
     * 装饰key 在key前面拼接前缀
     */
    protected String decorateKey(String key){
        return RedisUtil.REDIS_PREFIX + key;
    }

}
