package com.prictice.redis;

import com.prictice.redis.service.AbstractJedisCluster;
import com.prictice.redis.service.JedisClusterZsetService;

import java.util.Map;
import java.util.Set;

/**
 * @author 苏博
 * @className: JedisClusterZsetServiceImpl.java
 * @package com.prictice.redis
 * @description:
 * @date 2020/1/2 18:26
 */
public class JedisClusterZsetServiceImpl extends AbstractJedisCluster implements JedisClusterZsetService {

    @Override
    public String zSet(String key, double score, String value) {
        return jedisCluster.zadd(decorateKey(key),score, value).toString();
    }

    @Override
    public String zmSet(String key, Map<String,Double> map) {
        return jedisCluster.zadd(decorateKey(key),map).toString();
    }

    @Override
    public Long zCard(String key) {
        return jedisCluster.zcard(decorateKey(key));
    }

    @Override
    public Double zScore(String key,String value) {
        return jedisCluster.zscore(decorateKey(key),value);
    }

    @Override
    public String zRank(String key, String value) {
        return jedisCluster.zrank(decorateKey(key),value).toString();
    }

    @Override
    public Set<String> zGetAll(String key) {
        return jedisCluster.zrange(decorateKey(key),0,-1);
    }

    @Override
    public Set<String> zRange(String key,long start,long end) {
        return jedisCluster.zrange(decorateKey(key),start,end);
    }
}
