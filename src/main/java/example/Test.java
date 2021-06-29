package example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: 苏博
 * @Date: 2020/7/13 15:56
 * @Description:
 */
public class Test {


    public static void main(String[] args) {
        List<Long> list = new ArrayList<>();

        list.add(1L);
        list.add(2L);
        list.add(3L);


        System.out.println(Arrays.toString(list.toArray()));
    }
}
