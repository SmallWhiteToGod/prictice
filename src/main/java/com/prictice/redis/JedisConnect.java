package com.prictice.redis;

import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.RedissonClient;
import org.redisson.SingleServerConfig;
import org.redisson.core.RBucket;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author 苏博
 * @version V1.2.0
 * @className: JedisConnect.java
 * @package com.prictice.redis
 * @description: redis连接测试
 * @date 2019/4/23 18:17
 */
public class JedisConnect {

    /**
     * redis是否连接
     */
    private static void connectTest(){
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("192.168.1.195");
        //jedis.select(1);
        System.out.println("Connection to server sucessfully");
        //查看服务是否运行
        System.out.println("Server is running: "+jedis.ping());
    }

    /**
     * 连接redis集群
     */
    private static void cluster(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 最大连接数
        poolConfig.setMaxTotal(5);
        // 最大空闲数
        poolConfig.setMaxIdle(1);
        // 最大允许等待时间，如果超过这个时间还未获取到连接，则会报JedisException异常：
        // Could not get a resource from the pool
        poolConfig.setMaxWaitMillis(1000);
        Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>();
        nodes.add(new HostAndPort("192.168.1.195", 6379));
        nodes.add(new HostAndPort("192.168.1.195", 6380));
        nodes.add(new HostAndPort("192.168.1.195", 6381));
        JedisCluster cluster = new JedisCluster(nodes, poolConfig);
        System.out.println(cluster.get("test:case1"));
        try {
            cluster.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用Redisson连接redis
     * @param ip
     * @param port
     */
    public static void getRedisson(String ip, String port){
        Config config = new Config();

        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress(ip+":"+port);

        RedissonClient redissonClient = Redisson.create(config);

        for (int i = 1; i <= 30; i++) {
            RBucket<String> bucket = redissonClient.getBucket("myBucket"+i);
            bucket.set("你好呀");

            bucket = redissonClient.getBucket("myBucket"+i);
            System.out.println(bucket.get());
        }


        redissonClient.shutdown();
    }

    public static void main(String[] args) {
        getRedisson("122.152.210.218","6379");
    }

}
