package com.prictice.redis.service;

import java.util.List;
import java.util.Map;

/**
 * @author 苏博
 * @company lhfinance.com
 * @className: JedisClusterService.java
 * @package com.prictice.redis
 * @description:
 * @date 2019/12/17 15:21
 */
public interface JedisClusterHashService {

    /**
     * hset
     * @param key
     * @param keyName
     * @param value
     * @return
     */
    String hSet(String key, String keyName, String value);

    /**
     * hget
     * @param key
     * @param keyName
     * @return
     */
    String hGet(String key, String keyName);

    /**
     * hmset
     * @param key
     * @param map
     */
    String hmSet(String key, Map map);

    /**
     * hmget
     * @param key
     * @param keyNames
     * @return
     */
    List<String> hmGet(String key, String ... keyNames);


    /**
     * hgetAll
     * @param key
     * @return
     */
    Map<String,String> hGetAll(String key);
}
