package com.prictice.drools.engine.model;

import java.io.Serializable;

public abstract class AbstractRuleVar implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 数据库ruleId
	 */
	private Long ruleId;
	
	private int priority;
	
	private String enabled = "Y";
	
	private String name;
	
	private String desc;

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
