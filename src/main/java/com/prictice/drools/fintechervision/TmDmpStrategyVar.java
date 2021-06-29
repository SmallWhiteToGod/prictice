package com.prictice.drools.fintechervision;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 策略类别变量 TM_DMP_STRATEGY_VAR
 * @author zeus-maven-plugin
 */
public class TmDmpStrategyVar implements Serializable {
    private static final long serialVersionUID = 1L;

    private String org;

    private String stClass;

    private String varType;

    private String varCd;

    private String varName;

    private String isInput;

    private String isOutput;

    private String remark;

    private String ifUsed;

    private Date createDate;

    private String createBy;

    private Date updateDate;

    private String updateBy;

    private Long jpaVersion;

    /**
     * <p>ORG</p>
     */
    public String getOrg() {
        return org;
    }

    /**
     * <p>ORG</p>
     */
    public void setOrg(String org) {
        this.org = org;
    }

    /**
     * <p>策略方案JAVA类</p>
     */
    public String getStClass() {
        return stClass;
    }

    /**
     * <p>策略方案JAVA类</p>
     */
    public void setStClass(String stClass) {
        this.stClass = stClass;
    }

    /**
     * <p>变量类型</p>
     */
    public String getVarType() {
        return varType;
    }

    /**
     * <p>变量类型</p>
     */
    public void setVarType(String varType) {
        this.varType = varType;
    }

    /**
     * <p>变量代码</p>
     */
    public String getVarCd() {
        return varCd;
    }

    /**
     * <p>变量代码</p>
     */
    public void setVarCd(String varCd) {
        this.varCd = varCd;
    }

    /**
     * <p>变量名称</p>
     */
    public String getVarName() {
        return varName;
    }

    /**
     * <p>变量名称</p>
     */
    public void setVarName(String varName) {
        this.varName = varName;
    }

    /**
     * <p>输入变量</p>
     */
    public String getIsInput() {
        return isInput;
    }

    /**
     * <p>输入变量</p>
     */
    public void setIsInput(String isInput) {
        this.isInput = isInput;
    }

    /**
     * <p>输出变量</p>
     */
    public String getIsOutput() {
        return isOutput;
    }

    /**
     * <p>输出变量</p>
     */
    public void setIsOutput(String isOutput) {
        this.isOutput = isOutput;
    }

    /**
     * <p>说明</p>
     */
    public String getRemark() {
        return remark;
    }

    /**
     * <p>说明</p>
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * <p>是否启用</p>
     */
    public String getIfUsed() {
        return ifUsed;
    }

    /**
     * <p>是否启用</p>
     */
    public void setIfUsed(String ifUsed) {
        this.ifUsed = ifUsed;
    }

    /**
     * <p>创建时间</p>
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * <p>创建时间</p>
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * <p>创建用户</p>
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * <p>创建用户</p>
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * <p>修改时间</p>
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * <p>修改时间</p>
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * <p>修改用户</p>
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * <p>修改用户</p>
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * <p>版本</p>
     */
    public Long getJpaVersion() {
        return jpaVersion;
    }

    /**
     * <p>版本</p>
     */
    public void setJpaVersion(Long jpaVersion) {
        this.jpaVersion = jpaVersion;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("org", org);
        map.put("stClass", stClass);
        map.put("varType", varType);
        map.put("varCd", varCd);
        map.put("varName", varName);
        map.put("isInput", isInput);
        map.put("isOutput", isOutput);
        map.put("remark", remark);
        map.put("ifUsed", ifUsed);
        map.put("createDate", createDate);
        map.put("createBy", createBy);
        map.put("updateDate", updateDate);
        map.put("updateBy", updateBy);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        "org="+org+
        "stClass="+stClass+
        "varType="+varType+
        "varCd="+varCd+
        "varName="+varName+
        "isInput="+isInput+
        "isOutput="+isOutput+
        "remark="+remark+
        "ifUsed="+ifUsed+
        "createDate="+createDate+
        "createBy="+createBy+
        "updateDate="+updateDate+
        "updateBy="+updateBy+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (stClass == null) stClass = "";
        if (varType == null) varType = "";
        if (varCd == null) varCd = "";
        if (varName == null) varName = "";
        if (isInput == null) isInput = "";
        if (isOutput == null) isOutput = "";
        if (remark == null) remark = "";
        if (ifUsed == null) ifUsed = "";
        if (createDate == null) createDate = new Date();
        if (createBy == null) createBy = "";
        if (updateDate == null) updateDate = new Date();
        if (updateBy == null) updateBy = "";
        if (jpaVersion == null) jpaVersion = 0l;
    }
}