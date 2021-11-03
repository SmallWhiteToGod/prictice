package com.prictice.redis.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 苏博
 * @className: JedisClusterService.java
 * @package com.prictice.redis
 * @description:
 * @date 2019/12/17 15:21
 */
public interface JedisClusterZsetService {

    /**
     * zset
     * @param key
     * @param score
     * @param value
     * @return
     */
    String zSet(String key, double score,String value);

    /**
     * zCard计数
     * @param key
     * @return
     */
    Long zCard(String key);

    /**
     * zScore查询分数
     * @param key
     * @param value
     * @return
     */
    Double zScore(String key,String value);

    /**
     * zmset
     * @param key
     * @param map
     */
    String zmSet(String key, Map<String,Double> map);

    /**
     * zrank 获取排名
     * 分数从小到大，排名从0开始
     * @param key
     * @param value
     * @return
     */
    String zRank(String key, String value);


    /**
     * 获取全部
     * zrange(0,-1)
     * @param key
     * @return
     */
    Set<String> zGetAll(String key);

    /**
     * zrange
     * @param key
     * @param start 开始的排名位置 (正序从0开始,倒序从-1开始)
     * @param end   结束的排名位置（-1表示最后一位）
     * @return
     */
    Set<String> zRange(String key, long start, long end);
}
