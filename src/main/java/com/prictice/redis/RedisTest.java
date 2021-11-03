package com.prictice.redis;

import com.prictice.redis.service.JedisClusterHashService;
import com.prictice.redis.service.JedisClusterService;
import com.prictice.redis.service.JedisClusterZsetService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 苏博
 * @className: Test.java
 * @package com.prictice.redis
 * @description:
 * @date 2019/12/17 15:39
 */
public class RedisTest {

    private static JedisClusterService jedisImpl = new JedisClusterServiceImpl();
    private static JedisClusterHashService jedisHashImpl = new JedisClusterHashServiceImpl();
    private static JedisClusterZsetService jedisZsetImpl = new JedisClusterZsetServiceImpl();

    public static void main(String[] args) {

    }

    private static void strTest(){
        String key = "date";
        System.out.println("del: " + jedisImpl.del(key));
        System.out.println("set: " + jedisImpl.set("date", "1216"));
        System.out.println("get: " + jedisImpl.get(key));
        System.out.println("ttl: " + jedisImpl.ttl(key));
        System.out.println("setnx: " + jedisImpl.setnx(key,"1217"));
        System.out.println("type: " + jedisImpl.type(key));
    }

    private static void hashTest(){
        String key = "festival";
        Map<String,String> map = new HashMap<>(4);
        map.put("1月","春节");
        map.put("5月","端午");
        map.put("8月","中秋");
        map.put("12月","除夕");
        System.out.println(jedisHashImpl.hmSet(key, map));
        System.out.println(jedisHashImpl.hmGet(key,"5月","8月","6月"));//6月为null
        System.out.println(jedisHashImpl.hSet(key, "7月", "七夕"));
        System.out.println(jedisHashImpl.hGet(key,"7月"));
        System.out.println(jedisHashImpl.hGetAll(key));
    }

    private static void zsetTest(){
        String key = "rank";

        System.out.println(jedisZsetImpl.zSet(key, 53, "黄金"));
        System.out.println(jedisZsetImpl.zSet(key, 87, "大师"));
        System.out.println(jedisZsetImpl.zSet(key, 75, "钻石"));

        System.out.println(jedisZsetImpl.zCard(key));//计数
        System.out.println(jedisZsetImpl.zScore(key,"钻石"));//查询
        System.out.println(jedisZsetImpl.zRank(key,"钻石"));//查询
        System.out.println(jedisZsetImpl.zRank(key,"白银"));//查询



        Map<String,Double> map = new HashMap<>();
        map.put("白银",44.0);
        map.put("铂金",65.0);
        System.out.println(jedisZsetImpl.zmSet(key,map));
        System.out.println(jedisZsetImpl.zGetAll(key));
        System.out.println(jedisZsetImpl.zRange(key,0,0));
    }

}
