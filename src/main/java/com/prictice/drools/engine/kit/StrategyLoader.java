package com.prictice.drools.engine.kit;

import com.prictice.drools.engine.model.FieldVar;
import com.prictice.drools.engine.model.RuleRuntimeInfo;
import com.prictice.drools.engine.model.enums.DataType;
import com.prictice.drools.fintechervision.TmDmpStrategyVar;
import org.drools.KnowledgeBase;
import org.drools.builder.*;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.util.*;

public class StrategyLoader{
	
	public static KnowledgeBase loadKnowledgeBase(String drl){
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newReaderResource(new StringReader(drl)), ResourceType.DRL);
		
		System.out.println("---------规则信息---------");
		System.out.println();
		System.out.println(drl);
		System.out.println("--------------------------");
		if(kbuilder.hasErrors()){
			System.out.println("规则中存在错误，错误消息如下： ");
			KnowledgeBuilderErrors kbuidlerErrors=kbuilder.getErrors();
			for(Iterator<KnowledgeBuilderError> iter = kbuidlerErrors.iterator(); iter.hasNext();){
				KnowledgeBuilderError error = iter.next();
				System.out.println(error.toString());
				//System.out.println(error);
			}
		}
		
		return kbuilder.newKnowledgeBase();
	}
	
	public static void execute(KnowledgeBase knowledgeBase, Map<String, Object> fact){
		StatelessKnowledgeSession statelessKnowledgeSession = knowledgeBase.newStatelessKnowledgeSession();
		statelessKnowledgeSession.execute(fact);
	}

	
	/**
	 * 重新格式化输入数据
	 * 输入事实不存在key时，设置null,
	 * @param fact
	 * @param inputFieldVars
	 */
	public static void inputFactMerge(Map<String,Object> fact,Map<String, FieldVar> inputFieldVars){
		if(fact==null){
			fact = new HashMap<String, Object>();
		}
		fact.put("_rules", new ArrayList<RuleRuntimeInfo>());
		
		if(inputFieldVars!=null){
			for(Map.Entry<String, FieldVar> f:inputFieldVars.entrySet()){
				Object value = fact.get(f.getKey());
				DataType d = DataType.valueOf(f.getValue().getDataType());
				
				if(value==null|| (value!=null && "".equals(value.toString().trim())) ){
					switch (d) {
					case string:
						fact.put(f.getKey(), value);
						break;
					case decimal:
						fact.put(f.getKey(), null);
						break;
					case bool:
						fact.put(f.getKey(), null);
						break;
					case date:
						fact.put(f.getKey(), null);
						break;
					case e:
						fact.put(f.getKey(), null);
						break;
					default:
						fact.put(f.getKey(), null);
						break;
					}
				}else{
					switch (d) {
					case string:
						fact.put(f.getKey(), value);
						break;
					case decimal:
						fact.put(f.getKey(), value);
						break;
					case bool:
						if(value instanceof String){
							if("false".equalsIgnoreCase((String)value)){
								fact.put(f.getKey(), false);
							}else if("true".equalsIgnoreCase((String)value)){
								fact.put(f.getKey(), true);
							}else{
								throw new RuntimeException("bool 字符串不正确");
							}
						}
						break;
					case date:
						if(value instanceof String){
							fact.put(f.getKey(), FunctionKit.toDate((String)value));
						}else if(value instanceof Date){
							fact.put(f.getKey(), value);
						}else{
							throw new RuntimeException("时间日期格式不正确");
						}
						break;
					case e:
						fact.put(f.getKey(), value);
						break;
					default:
						fact.put(f.getKey(), value);
						break;
					}
				}
			}
		}
	}
}
