package com.prictice.javabase.collection;

import java.util.*;

/**
 * @author 苏博
 * @className: CollectionTest.java
 * @package com.prictice.javabase
 * @description:
 * @date 2019/8/25 16:34
 */
public class CollectionTest {

    public static void main(String[] args) {
        List list = new ArrayList<User>();
        Set<User> set = new HashSet<User>();

        User user1 = new User(111L,"张一",18,"辽宁");
        User user2 = new User(231L,"张二",34,"辽宁");
        User user3 = new User(567L,"张三",23,"辽宁");
        User user4 = new User(311L,"张四",19,"辽宁");
        User user5 = new User(311L,"张五",40,"辽宁");


        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);
        list.add(user5);

        set.add(user1);
        set.add(user2);
        set.add(user3);
        set.add(user4);
        set.add(user5);


        System.out.println("========遍历打印set==========");
        for (User user : set) {
            System.out.println(user.toString());
        }

        System.out.println("========遍历打印list==========");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).toString());
        }
        Collections.sort(list);
        System.out.println("========collections sort，默认调用compareTo（）排序后==========");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).toString());
        }

        list.sort(new Comparator<User>(){
            @Override
            public int compare(User o1, User o2) {
                return (int)(o1.getId()-o2.getId());
            }
        });
        System.out.println("========list sort排序后，制定compare（）排序后==========");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).toString());
        }

        list.remove(user1);
        System.out.println("========remove user1后==========");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).toString());
        }
    }
}
