package com.prictice.redis;

import com.prictice.redis.service.AbstractJedisCluster;
import com.prictice.redis.service.JedisClusterHashService;

import java.util.List;
import java.util.Map;

/**
 * @author 苏博
 * @className: JedisClusterMapServiceImpl.java
 * @package com.prictice.redis
 * @description:
 * @date 2020/1/2 17:03
 */
public class JedisClusterHashServiceImpl extends AbstractJedisCluster implements JedisClusterHashService {

    @Override
    public String hmSet(String key, Map map) {
        return jedisCluster.hmset(decorateKey(key),map);
    }

    @Override
    public List<String> hmGet(String key, String ... keyNames) {
        return jedisCluster.hmget(decorateKey(key),keyNames);
    }

    @Override
    public String hSet(String key, String keyName, String value) {
        return jedisCluster.hset(decorateKey(key),keyName,value).toString();
    }

    @Override
    public String hGet(String key, String keyName) {
        return jedisCluster.hget(decorateKey(key),keyName);
    }

    @Override
    public Map<String, String> hGetAll(String key) {
        return jedisCluster.hgetAll(decorateKey(key));
    }
}
