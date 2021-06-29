package drools;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * @author 苏博
 * @version V1.2.0
 * @company lhfinance.com
 * @className: drools.DroolsTest.java
 * @package PACKAGE_NAME
 * @description: Drools规则测试
 * @date 2019/4/8 16:04
 */
public class DroolsTest {

    /**
     * resource:规则文件
     * resource:MEAT-INF:知识库与会话的配置
     */
    @Test
    public void testHelloWorld() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.newKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("helloWorldSession");
        kieSession.fireAllRules();
        kieSession.dispose();
    }


}
