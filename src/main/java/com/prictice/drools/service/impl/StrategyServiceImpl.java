package com.prictice.drools.service.impl;

import com.prictice.drools.fintechervision.TmDmpStrategy;
import com.prictice.drools.service.StrategyService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service("strategyService")
public class StrategyServiceImpl implements StrategyService {

	@Override
	public TmDmpStrategy getDefaultStrategy(String stClass) {
		TmDmpStrategy tmDmpStrategy = new TmDmpStrategy();
		tmDmpStrategy.setStClass(stClass);
		tmDmpStrategy.setIfUsed("Y");
		//这里应该查数据库
		tmDmpStrategy.setStObject(getStObject());
		return tmDmpStrategy;
	}

	private String getStObject() {
		String a = "\n" +
				"package com.fintechervision._案件流转策略;\n" +
				"import java.util.Map;\n" +
				"import org.mvel2.MVEL;\n" +
				"import java.util.ArrayList;\n" +
				"import java.util.Calendar;\n" +
				"import java.util.Date;\n" +
				"import java.math.BigDecimal;\n" +
				"import com.prictice.drools.engine.kit.FunctionKit;\n" +
				"import com.prictice.drools.engine.model.RuleRuntimeInfo;\n" +
				"\n" +
				"dialect \"mvel\"\n" +
				"\n" +
				"//ST---start ruleset 案件流转策略\n" +
				"rule \"CTS_CASE_FLOW_案件流转策略_案件流转策略_流转策略13\"\n" +
				"  lock-on-active true\n" +
				"  enabled true\n" +
				"  activation-group \"案件流转策略\"\n" +
				"  salience 88\n" +
				"  when\n" +
				"    $t:Map( ( (acctOverdueDays > 361) ) )\n" +
				"  then\n" +
				"    RuleRuntimeInfo ruleRuntimeInfo = new RuleRuntimeInfo(\"CTS_CASE_FLOW\", \"案件流转策略\", \"流转策略13\" );\n" +
				"    if(!$t.containsKey(\"_rules\")){ $t.put(\"_rules\", new ArrayList());}\n" +
				"    ((ArrayList)$t.get(\"_rules\")).add(ruleRuntimeInfo); \n" +
				"\n" +
				"    Object newValue = \"DCA\" ;\n" +
				"    ruleRuntimeInfo.addOutput(\"currCollStage\", $t.get(\"currCollStage\"), newValue);\n" +
				"    $t.put(\"currCollStage\", newValue);\n" +
				"\n" +
				"    Object newValue = \"N\" ;\n" +
				"    ruleRuntimeInfo.addOutput(\"flowInd\", $t.get(\"flowInd\"), newValue);\n" +
				"    $t.put(\"flowInd\", newValue);\n" +
				"\n" +
				"    update($t);\n" +
				"end\n" +
				"\n" +
				"rule \"CTS_CASE_FLOW_案件流转策略_案件流转策略_流转策略12\"\n" +
				"  lock-on-active true\n" +
				"  enabled true\n" +
				"  activation-group \"案件流转策略\"\n" +
				"  salience 89\n" +
				"  when\n" +
				"    $t:Map( ( (acctOverdueDays  ==  361) )  &&  ( (daysInOperator >= 7) ) )\n" +
				"  then\n" +
				"    RuleRuntimeInfo ruleRuntimeInfo = new RuleRuntimeInfo(\"CTS_CASE_FLOW\", \"案件流转策略\", \"流转策略12\" );\n" +
				"    if(!$t.containsKey(\"_rules\")){ $t.put(\"_rules\", new ArrayList());}\n" +
				"    ((ArrayList)$t.get(\"_rules\")).add(ruleRuntimeInfo); \n" +
				"\n" +
				"    Object newValue = \"DCA\" ;\n" +
				"    ruleRuntimeInfo.addOutput(\"currCollStage\", $t.get(\"currCollStage\"), newValue);\n" +
				"    $t.put(\"currCollStage\", newValue);\n" +
				"\n" +
				"    Object newValue = \"Y\" ;\n" +
				"    ruleRuntimeInfo.addOutput(\"flowInd\", $t.get(\"flowInd\"), newValue);\n" +
				"    $t.put(\"flowInd\", newValue);\n" +
				"\n" +
				"    update($t);\n" +
				"end\n" +
				"\n" +
				"rule \"CTS_CASE_FLOW_案件流转策略_案件流转策略_流转策略11\"\n" +
				"  lock-on-active true\n" +
				"  enabled true\n" +
				"  activation-group \"案件流转策略\"\n" +
				"  salience 90\n" +
				"  when\n" +
				"    $t:Map( ( (acctOverdueDays > 181) )  &&  ( (acctOverdueDays <= 360) ) )\n" +
				"  then\n" +
				"    RuleRuntimeInfo ruleRuntimeInfo = new RuleRuntimeInfo(\"CTS_CASE_FLOW\", \"案件流转策略\", \"流转策略11\" );\n" +
				"    if(!$t.containsKey(\"_rules\")){ $t.put(\"_rules\", new ArrayList());}\n" +
				"    ((ArrayList)$t.get(\"_rules\")).add(ruleRuntimeInfo); \n" +
				"\n" +
				"    Object newValue = \"DCA\" ;\n" +
				"    ruleRuntimeInfo.addOutput(\"currCollStage\", $t.get(\"currCollStage\"), newValue);\n" +
				"    $t.put(\"currCollStage\", newValue);\n" +
				"\n" +
				"    Object newValue = \"N\" ;\n" +
				"    ruleRuntimeInfo.addOutput(\"flowInd\", $t.get(\"flowInd\"), newValue);\n" +
				"    $t.put(\"flowInd\", newValue);\n" +
				"\n" +
				"    update($t);\n" +
				"end\n" +
				"\n" +
				"rule \"CTS_CASE_FLOW_案件流转策略_案件流转策略_流转策略10\"\n" +
				"  lock-on-active true\n" +
				"  enabled true\n" +
				"  activation-group \"案件流转策略\"\n" +
				"  salience 91\n" +
				"  when\n" +
				"    $t:Map( ( (acctOverdueDays  ==  181) )  &&  ( (daysInOperator >= 7) ) )\n" +
				"  then\n" +
				"    RuleRuntimeInfo ruleRuntimeInfo = new RuleRuntimeInfo(\"CTS_CASE_FLOW\", \"案件流转策略\", \"流转策略10\" );\n" +
				"    if(!$t.containsKey(\"_rules\")){ $t.put(\"_rules\", new ArrayList());}\n" +
				"    ((ArrayList)$t.get(\"_rules\")).add(ruleRuntimeInfo); \n" +
				"\n" +
				"    Object newValue = \"DCA\" ;\n" +
				"    ruleRuntimeInfo.addOutput(\"currCollStage\", $t.get(\"currCollStage\"), newValue);\n" +
				"    $t.put(\"currCollStage\", newValue);\n" +
				"\n" +
				"    Object newValue = \"Y\" ;\n" +
				"    ruleRuntimeInfo.addOutput(\"flowInd\", $t.get(\"flowInd\"), newValue);\n" +
				"    $t.put(\"flowInd\", newValue);\n" +
				"\n" +
				"    update($t);\n" +
				"end\n" +
				"\n" +
				"rule \"CTS_CASE_FLOW_案件流转策略_案件流转策略_流转策略9\"\n" +
				"  lock-on-active true\n" +
				"  enabled true\n" +
				"  activation-group \"案件流转策略\"\n" +
				"  salience 92\n" +
				"  when\n" +
				"    $t:Map( ( (acctOverdueDays > 121) )  &&  ( (acctOverdueDays <= 180) ) )\n" +
				"  then\n" +
				"    RuleRuntimeInfo ruleRuntimeInfo = new RuleRuntimeInfo(\"CTS_CASE_FLOW\", \"案件流转策略\", \"流转策略9\" );\n" +
				"    if(!$t.containsKey(\"_rules\")){ $t.put(\"_rules\", new ArrayList());}\n" +
				"    ((ArrayList)$t.get(\"_rules\")).add(ruleRuntimeInfo); \n" +
				"\n" +
				"    Object newValue = \"DCA\" ;\n" +
				"    ruleRuntimeInfo.addOutput(\"currCollStage\", $t.get(\"currCollStage\"), newValue);\n" +
				"    $t.put(\"currCollStage\", newValue);\n" +
				"\n" +
				"    Object newValue = \"N\" ;\n" +
				"    ruleRuntimeInfo.addOutput(\"flowInd\", $t.get(\"flowInd\"), newValue);\n" +
				"    $t.put(\"flowInd\", newValue);\n" +
				"\n" +
				"    update($t);\n" +
				"end\n" +
				"\n" +
				"rule \"CTS_CASE_FLOW_案件流转策略_案件流转策略_流转策略8\"\n" +
				"  lock-on-active true\n" +
				"  enabled true\n" +
				"  activation-group \"案件流转策略\"\n" +
				"  salience 93\n" +
				"  when\n" +
				"    $t:Map( ( (acctOverdueDays  ==  121) )  &&  ( (daysInOperator >= 7) ) )\n" +
				"  then\n" +
				"    RuleRuntimeInfo ruleRuntimeInfo = new RuleRuntimeInfo(\"CTS_CASE_FLOW\", \"案件流转策略\", \"流转策略8\" );\n" +
				"    if(!$t.containsKey(\"_rules\")){ $t.put(\"_rules\", new ArrayList());}\n" +
				"    ((ArrayList)$t.get(\"_rules\")).add(ruleRuntimeInfo); \n" +
				"\n" +
				"    Object newValue = \"DCA\" ;\n" +
				"    ruleRuntimeInfo.addOutput(\"currCollStage\", $t.get(\"currCollStage\"), newValue);\n" +
				"    $t.put(\"currCollStage\", newValue);\n" +
				"\n" +
				"    Object newValue = \"Y\" ;\n" +
				"    ruleRuntimeInfo.addOutput(\"flowInd\", $t.get(\"flowInd\"), newValue);\n" +
				"    $t.put(\"flowInd\", newValue);\n" +
				"\n" +
				"    update($t);\n" +
				"end\n" +
				"\n" +
				"rule \"CTS_CASE_FLOW_案件流转策略_案件流转策略_流转策略7\"\n" +
				"  lock-on-active true\n" +
				"  enabled true\n" +
				"  activation-group \"案件流转策略\"\n" +
				"  salience 94\n" +
				"  when\n" +
				"    $t:Map( ( (acctOverdueDays > 61) )  &&  ( (acctOverdueDays <= 120) ) )\n" +
				"  then\n" +
				"    RuleRuntimeInfo ruleRuntimeInfo = new RuleRuntimeInfo(\"CTS_CASE_FLOW\", \"案件流转策略\", \"流转策略7\" );\n" +
				"    if(!$t.containsKey(\"_rules\")){ $t.put(\"_rules\", new ArrayList());}\n" +
				"    ((ArrayList)$t.get(\"_rules\")).add(ruleRuntimeInfo); \n" +
				"\n" +
				"    Object newValue = \"DCA\" ;\n" +
				"    ruleRuntimeInfo.addOutput(\"currCollStage\", $t.get(\"currCollStage\"), newValue);\n" +
				"    $t.put(\"currCollStage\", newValue);\n" +
				"\n" +
				"    Object newValue = \"N\" ;\n" +
				"    ruleRuntimeInfo.addOutput(\"flowInd\", $t.get(\"flowInd\"), newValue);\n" +
				"    $t.put(\"flowInd\", newValue);\n" +
				"\n" +
				"    update($t);\n" +
				"end\n" +
				"\n" +
				"rule \"CTS_CASE_FLOW_案件流转策略_案件流转策略_流转策略6\"\n" +
				"  lock-on-active true\n" +
				"  enabled true\n" +
				"  activation-group \"案件流转策略\"\n" +
				"  salience 95\n" +
				"  when\n" +
				"    $t:Map( ( (acctOverdueDays  ==  61) )  &&  ( (daysInOperator >= 7) ) )\n" +
				"  then\n" +
				"    RuleRuntimeInfo ruleRuntimeInfo = new RuleRuntimeInfo(\"CTS_CASE_FLOW\", \"案件流转策略\", \"流转策略6\" );\n" +
				"    if(!$t.containsKey(\"_rules\")){ $t.put(\"_rules\", new ArrayList());}\n" +
				"    ((ArrayList)$t.get(\"_rules\")).add(ruleRuntimeInfo); \n" +
				"\n" +
				"    Object newValue = \"DCA\" ;\n" +
				"    ruleRuntimeInfo.addOutput(\"currCollStage\", $t.get(\"currCollStage\"), newValue);\n" +
				"    $t.put(\"currCollStage\", newValue);\n" +
				"\n" +
				"    Object newValue = \"Y\" ;\n" +
				"    ruleRuntimeInfo.addOutput(\"flowInd\", $t.get(\"flowInd\"), newValue);\n" +
				"    $t.put(\"flowInd\", newValue);\n" +
				"\n" +
				"    update($t);\n" +
				"end\n" +
				"\n" +
				"rule \"CTS_CASE_FLOW_案件流转策略_案件流转策略_流转策略5\"\n" +
				"  lock-on-active true\n" +
				"  enabled true\n" +
				"  activation-group \"案件流转策略\"\n" +
				"  salience 96\n" +
				"  when\n" +
				"    $t:Map( ( (acctOverdueDays > 31) )  &&  ( (acctOverdueDays <= 60) ) )\n" +
				"  then\n" +
				"    RuleRuntimeInfo ruleRuntimeInfo = new RuleRuntimeInfo(\"CTS_CASE_FLOW\", \"案件流转策略\", \"流转策略5\" );\n" +
				"    if(!$t.containsKey(\"_rules\")){ $t.put(\"_rules\", new ArrayList());}\n" +
				"    ((ArrayList)$t.get(\"_rules\")).add(ruleRuntimeInfo); \n" +
				"\n" +
				"    Object newValue = \"TDC\" ;\n" +
				"    ruleRuntimeInfo.addOutput(\"currCollStage\", $t.get(\"currCollStage\"), newValue);\n" +
				"    $t.put(\"currCollStage\", newValue);\n" +
				"\n" +
				"    Object newValue = \"N\" ;\n" +
				"    ruleRuntimeInfo.addOutput(\"flowInd\", $t.get(\"flowInd\"), newValue);\n" +
				"    $t.put(\"flowInd\", newValue);\n" +
				"\n" +
				"    update($t);\n" +
				"end\n" +
				"\n" +
				"rule \"CTS_CASE_FLOW_案件流转策略_案件流转策略_流转策略4\"\n" +
				"  lock-on-active true\n" +
				"  enabled true\n" +
				"  activation-group \"案件流转策略\"\n" +
				"  salience 97\n" +
				"  when\n" +
				"    $t:Map( ( (acctOverdueDays  ==  31) )  &&  ( (daysInOperator >= 7) ) )\n" +
				"  then\n" +
				"    RuleRuntimeInfo ruleRuntimeInfo = new RuleRuntimeInfo(\"CTS_CASE_FLOW\", \"案件流转策略\", \"流转策略4\" );\n" +
				"    if(!$t.containsKey(\"_rules\")){ $t.put(\"_rules\", new ArrayList());}\n" +
				"    ((ArrayList)$t.get(\"_rules\")).add(ruleRuntimeInfo); \n" +
				"\n" +
				"    Object newValue = \"TDC\" ;\n" +
				"    ruleRuntimeInfo.addOutput(\"currCollStage\", $t.get(\"currCollStage\"), newValue);\n" +
				"    $t.put(\"currCollStage\", newValue);\n" +
				"\n" +
				"    Object newValue = \"Y\" ;\n" +
				"    ruleRuntimeInfo.addOutput(\"flowInd\", $t.get(\"flowInd\"), newValue);\n" +
				"    $t.put(\"flowInd\", newValue);\n" +
				"\n" +
				"    update($t);\n" +
				"end\n" +
				"\n" +
				"rule \"CTS_CASE_FLOW_案件流转策略_案件流转策略_流转策略3\"\n" +
				"  lock-on-active true\n" +
				"  enabled true\n" +
				"  activation-group \"案件流转策略\"\n" +
				"  salience 98\n" +
				"  when\n" +
				"    $t:Map( ( (acctOverdueDays > 15) )  &&  ( (acctOverdueDays <= 30) ) )\n" +
				"  then\n" +
				"    RuleRuntimeInfo ruleRuntimeInfo = new RuleRuntimeInfo(\"CTS_CASE_FLOW\", \"案件流转策略\", \"流转策略3\" );\n" +
				"    if(!$t.containsKey(\"_rules\")){ $t.put(\"_rules\", new ArrayList());}\n" +
				"    ((ArrayList)$t.get(\"_rules\")).add(ruleRuntimeInfo); \n" +
				"\n" +
				"    Object newValue = \"TDC\" ;\n" +
				"    ruleRuntimeInfo.addOutput(\"currCollStage\", $t.get(\"currCollStage\"), newValue);\n" +
				"    $t.put(\"currCollStage\", newValue);\n" +
				"\n" +
				"    Object newValue = \"N\" ;\n" +
				"    ruleRuntimeInfo.addOutput(\"flowInd\", $t.get(\"flowInd\"), newValue);\n" +
				"    $t.put(\"flowInd\", newValue);\n" +
				"\n" +
				"    update($t);\n" +
				"end\n" +
				"\n" +
				"rule \"CTS_CASE_FLOW_案件流转策略_案件流转策略_流转策略2\"\n" +
				"  lock-on-active true\n" +
				"  enabled true\n" +
				"  activation-group \"案件流转策略\"\n" +
				"  salience 99\n" +
				"  when\n" +
				"    $t:Map( ( (acctOverdueDays  ==  15) )  &&  ( (daysInOperator >= 7) ) )\n" +
				"  then\n" +
				"    RuleRuntimeInfo ruleRuntimeInfo = new RuleRuntimeInfo(\"CTS_CASE_FLOW\", \"案件流转策略\", \"流转策略2\" );\n" +
				"    if(!$t.containsKey(\"_rules\")){ $t.put(\"_rules\", new ArrayList());}\n" +
				"    ((ArrayList)$t.get(\"_rules\")).add(ruleRuntimeInfo); \n" +
				"\n" +
				"    Object newValue = \"TDC\" ;\n" +
				"    ruleRuntimeInfo.addOutput(\"currCollStage\", $t.get(\"currCollStage\"), newValue);\n" +
				"    $t.put(\"currCollStage\", newValue);\n" +
				"\n" +
				"    Object newValue = \"Y\" ;\n" +
				"    ruleRuntimeInfo.addOutput(\"flowInd\", $t.get(\"flowInd\"), newValue);\n" +
				"    $t.put(\"flowInd\", newValue);\n" +
				"\n" +
				"    update($t);\n" +
				"end\n" +
				"\n" +
				"rule \"CTS_CASE_FLOW_案件流转策略_案件流转策略_流转策略1\"\n" +
				"  lock-on-active true\n" +
				"  enabled true\n" +
				"  activation-group \"案件流转策略\"\n" +
				"  salience 100\n" +
				"  when\n" +
				"    $t:Map( ( (acctOverdueDays >= 1) )  &&  ( (acctOverdueDays < 15) ) )\n" +
				"  then\n" +
				"    RuleRuntimeInfo ruleRuntimeInfo = new RuleRuntimeInfo(\"CTS_CASE_FLOW\", \"案件流转策略\", \"流转策略1\" );\n" +
				"    if(!$t.containsKey(\"_rules\")){ $t.put(\"_rules\", new ArrayList());}\n" +
				"    ((ArrayList)$t.get(\"_rules\")).add(ruleRuntimeInfo); \n" +
				"\n" +
				"    Object newValue = \"TDC\" ;\n" +
				"    ruleRuntimeInfo.addOutput(\"currCollStage\", $t.get(\"currCollStage\"), newValue);\n" +
				"    $t.put(\"currCollStage\", newValue);\n" +
				"\n" +
				"    Object newValue = \"N\" ;\n" +
				"    ruleRuntimeInfo.addOutput(\"flowInd\", $t.get(\"flowInd\"), newValue);\n" +
				"    $t.put(\"flowInd\", newValue);\n" +
				"\n" +
				"    update($t);\n" +
				"end\n" +
				"\n" +
				"//ST---end ruleset 案件流转策略\n";
		return a;
	}


	@Override
	public TmDmpStrategy getTmDmpStrategy(Long stId){
		//return tmDmpStrategyDao.getByKey(stId);
		return null;
	}

}