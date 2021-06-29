package com.prictice.drools.engine.kit;


import com.prictice.drools.engine.model.AbstractRuleVar;

import java.util.Comparator;

public class RuleComparator implements Comparator<AbstractRuleVar> {

	@Override
	public int compare(AbstractRuleVar o1, AbstractRuleVar o2) {
	
		if(o1.getPriority() > o2.getPriority()){
			return 1;
		}else if(o1.getPriority() < o2.getPriority()){
			return -1;
		}
		
		return 0;
	}

}
