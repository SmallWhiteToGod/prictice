package com.prictice.javabase.clone;

/**
 * @author 苏博
 * @company lhfinance.com
 * @className: Client.java
 * @package com.prictice.javabase.clone
 * @description:
 * @date 2020/2/17 10:16
 */
public class Client {


    public static void main(String[] args) throws CloneNotSupportedException{
        CloneSup B1 = new CloneSup("张三",18);
        System.out.println(B1);

        CloneTest A1 = new CloneTest(100,B1);

        CloneTest A2 = (CloneTest)A1.clone();

        System.out.println(A2);

        System.out.println(A1 == A2);
        System.out.println(B1 == A2.getCloneSup());

        B1.setName("李四");
        System.out.println(B1.getName() == A2.getCloneSup().getName());

        System.out.println(A1.getCloneSup().getName());
        System.out.println(A2.getCloneSup().getName());
    }
}
