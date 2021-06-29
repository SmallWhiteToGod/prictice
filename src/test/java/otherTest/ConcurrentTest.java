package otherTest;

import com.prictice.javabase.multiThread.SaveOrUnSave;

/**
 * @author 苏博
 * @version V1.2.0
 * @company lhfinance.com
 * @className: ConcurrentTest.java
 * @package otherTest
 * @description:
 * @date 2019/4/24 18:34
 */
public class ConcurrentTest {

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(()->{

            },String.valueOf(i)).start();
        }
    }

}