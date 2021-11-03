package com.prictice.redis;

import redis.clients.jedis.JedisCluster;

/**
 * @author 苏博
 * @className: Test.java
 * @package com.prictice.redis
 * @description:
 * @date 2020/1/7 17:15
 */
public class Test {

    private final static String UP_VERSION_KEY = "org:bmp:upVersion:sysApp";


    public static void main(String[] args) throws Exception{

        int times = 1000;
        JedisCluster jedisCluster = RedisUtil.initJedisCluster();
        String upVersionKey = UP_VERSION_KEY.replace("org","000064540000").replace("sysApp","APS");

        String value = null;

        while (times-->0){
            value = jedisCluster.get(upVersionKey);
            if (value!= null){
                System.out.println(value);
            }
            Thread.sleep(100);
        }
    }

}
