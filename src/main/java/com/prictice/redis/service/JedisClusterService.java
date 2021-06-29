package com.prictice.redis.service;

/**
 * @author 苏博
 * @company lhfinance.com
 * @className: JedisClusterService.java
 * @package com.prictice.redis
 * @description:
 * @date 2019/12/17 15:21
 */
public interface JedisClusterService {

    /**
     * set
     * @param key
     * @param value
     */
    String set(String key,String value);

    /**
     * get
     * @param key
     * @return
     */
    String get(String key);

    /**
     * del
     * @param key
     * @return
     */
    String del(String key);

    /**
     * 查询过期时间
     * @param key
     * @return
     */
    String ttl(String key);

    /**
     * 返回类型
     * @param key
     * @return
     */
    String type(String key);

    /**
     * 如果不存在set
     * @param key
     * @param value
     * @return
     */
    String setnx(String key,String value);

    /**
     * 设置有效期
     * @param key
     * @param time
     * @param value
     * @return
     */
    String setex(String key,int time,String value);
}
