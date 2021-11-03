package otherTest;

import com.prictice.enums.InOut;
import org.junit.Test;

import java.util.HashMap;

/**
 * @author 苏博
 * @version V1.2.0
 * @className: EnumTest.java
 * @package otherTest
 * @description:
 * @date 2019/4/9 10:09
 */
public class EnumTest {


    @Test
    public void test(){
        System.out.println("I".equals(InOut.I.name()));
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("I","I");
        InOut i = null;
        try {
            i = (InOut)map.get("I");
            System.out.println(i);
        } catch (ClassCastException e) {
            e.printStackTrace();
            System.out.println("会报类型转换错误");
        }
    }


}
