package com.prictice.redis.bloomFilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @author 苏博
 * @className: GoogleBloomFilter.java
 * @package com.prictice.redis.bloomFilter
 * @description: guava包中带的布隆过滤器
 * @date 2020/1/2 14:09
 */
public class GoogleBloomFilter {


    // 创建布隆过滤器对象
    //目标存放 1500个整数的布隆过滤器，并且我们可以容忍误判的概率为0.01
    static BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(), 100, 0.01);


    public static void main(String[] args) {
        // 判断指定元素是否存在
        System.out.println(filter.mightContain(1));
        System.out.println(filter.mightContain(2));
        System.out.println(filter.mightContain(3));
        System.out.println(filter.mightContain(4));
        System.out.println(filter.mightContain(7));

        // 将元素添加进布隆过滤器
        filter.put(1);
        filter.put(2);
        filter.put(3);
        filter.put(4);
        System.out.println(filter.mightContain(1));
        System.out.println(filter.mightContain(2));
        System.out.println(filter.mightContain(3));
        System.out.println(filter.mightContain(4));
        System.out.println(filter.mightContain(7)); //容忍误判的概率较高时会返回true

    }
}
