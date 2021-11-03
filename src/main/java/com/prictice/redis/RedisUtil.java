package com.prictice.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 苏博
 * @className: RedisUtil.java
 * @package com.prictice.redis
 * @description:
 * @date 2019/12/11 17:33
 */
public class RedisUtil {

    private final static String REDIS_ADRESS = "192.168.1.201:6379,192.168.1.202:6379,192.168.1.203:6379";
    public final static String REDIS_PREFIX = "prictice:";

    private final static Integer TIMEOUT = 300000;
    private final static Integer MAX_REDIRECTIONS = 3;


    public static JedisCluster initJedisCluster() throws Exception{
        return new JedisCluster(parseHostAndPort(), TIMEOUT, MAX_REDIRECTIONS, getPoolConfig());
    }

    private static Set<HostAndPort> parseHostAndPort() throws Exception {
        try {
            Set<HostAndPort> haps = new HashSet<HostAndPort>();

            String[] address = REDIS_ADRESS.split(",");
            for (int i = 0; i < address.length; i++) {
                String[] ipAndPort = address[i].split(":");
                HostAndPort hap = new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
                haps.add(hap);
            }

            return haps;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("jedis 配置失败", ex);
        }
    }

    private static GenericObjectPoolConfig getPoolConfig(){
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxWaitMillis(-1);
        poolConfig.setMaxTotal(1000);
        poolConfig.setMinIdle(50);
        poolConfig.setMaxIdle(500);
        return poolConfig;
    }


}
