package com.prictice.drools.service;

import com.prictice.drools.fintechervision.TmDmpStrategy;
import org.drools.KnowledgeBase;

import java.util.Map;

public interface StrategyExecute {

	void executeStClass(String stClass, Map<String, Object> fact);

	void executeStId(Long stId, Map<String, Object> fact);

	void executeStrategy(TmDmpStrategy tmStrategy, Map<String, Object> fact);
	
	public TmDmpStrategy loadStrategy(String stClass);
	
	public void runStrategy(Map<String, Object> fact, String strategyClass);
	
	KnowledgeBase loadKnowledgeBase(String stClass);
	
}
