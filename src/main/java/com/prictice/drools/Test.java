package com.prictice.drools;

import com.prictice.drools.service.StrategyExecute;
import com.prictice.drools.service.impl.StrategyExecuteImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 苏博
 * @className: Test.java
 * @package com.prictice.drools
 * @description:
 * @date 2020/1/10 11:42
 */
public class Test {

    private static StrategyExecute strategyExecute = new StrategyExecuteImpl();

    public static void main(String[] args) {
        Map<String, Object> map = flowStratery(65,8);
        System.out.println(map.toString());
    }

    private static Map<String, Object> flowStratery(int acctOverdueDays,int daysInOperator){
        Map<String, Object> map = new HashMap<String, Object>();
        //入参:主借据逾期天数,催收员催收天数
        map.put("acctOverdueDays", acctOverdueDays);
        map.put("daysInOperator",daysInOperator);
        //出餐:催收阶段,案件是否流转(建议)
        map.put("flowInd", null);
        map.put("currCollStage", null);
        //执行"案件流转策略"
        strategyExecute.executeStClass("CTS_CASE_FLOW", map);
        String flowInd = (String) map.get("flowInd");
        String currCollStage = (String) map.get("currCollStage");
        return map;
    }
}
