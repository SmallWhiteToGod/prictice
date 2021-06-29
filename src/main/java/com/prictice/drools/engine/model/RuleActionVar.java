package com.prictice.drools.engine.model;

import com.prictice.drools.engine.model.enums.ActionType;

import java.io.Serializable;

/**
 * 规则动作
 * @author daiwk
 *
 */
public class RuleActionVar {

	private String property;
	
	private ActionType actionType;
	
	private Serializable value;
	
	private String valueClassName;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	public Serializable getValue() {
		return value;
	}

	public void setValue(Serializable value) {
		this.value = value;
	}

	public String getValueClassName() {
		return valueClassName;
	}

	public void setValueClassName(String valueClassName) {
		this.valueClassName = valueClassName;
	} 
	
}
