package com.prictice.javabase.lamda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author 苏博
 * @company lhfinance.com
 * @className: LamdaTest.java
 * @package com.prictice.javabase.lamda
 * @description:
 * @date 2019/9/18 9:35
 */
public class LamdaTest {

    public static void test(LamdaInterFace a){
        System.out.println(a.reverse(a.convert(12345)));
    }

    public static void main(String[] args) {
        LamdaTest.test(String::valueOf);

        LamdaTest.sortTest();
    }

    private String first2UpperCase(String str){
        return str.substring(0,1).toUpperCase()+str.substring(1);
    }

    //sort
    private static void sortTest(){
        List<String> names = Arrays.asList("peter", "anna", "amike", "axenia","bob","adas");

        //遍历输出以a开头的
        names
                .stream()
                .filter(s -> s.startsWith("a"))
                .sorted((a, b) -> b.compareTo(a))
                .map(new LamdaTest()::first2UpperCase)
                .forEach(System.out::println);

    }

}