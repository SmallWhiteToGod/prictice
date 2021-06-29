import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author 苏博
 * @version V1.2.0
 * @company lhfinance.com
 * @className: PricticeBoot.java
 * @package PACKAGE_NAME
 * @description:
 * @date 2019/4/2 10:37
 */
public class PricticeBoot {

    private static ApplicationContext context = null;

    public static void main(String[] args) throws IOException, Exception {

        //context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context = new ClassPathXmlApplicationContext("springLifeCycle.xml");

        ((ClassPathXmlApplicationContext) context).afterPropertiesSet();

        System.out.println("======启动成功======");
        System.exit(0);
    }
}
