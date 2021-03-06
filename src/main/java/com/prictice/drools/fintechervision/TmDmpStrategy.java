package com.prictice.drools.fintechervision;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 策略 TM_DMP_STRATEGY
 * @author zeus-maven-plugin
 */
public class TmDmpStrategy implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long stId;

    private String org;

    private String stName;

    private String remark;

    private String stClass;

    private String stKey;

    private String stObject;

    private String ifUsed;

    private Date createDate;

    private String createBy;

    private Date updateDate;

    private String updateBy;

    private Long jpaVersion;

    /**
     * <p>策略方案ID</p>
     */
    public Long getStId() {
        return stId;
    }

    /**
     * <p>策略方案ID</p>
     */
    public void setStId(Long stId) {
        this.stId = stId;
    }

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
     * <p>策略方案名称</p>
     */
    public String getStName() {
        return stName;
    }

    /**
     * <p>策略方案名称</p>
     */
    public void setStName(String stName) {
        this.stName = stName;
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
     * <p>关键值</p>
     */
    public String getStKey() {
        return stKey;
    }

    /**
     * <p>关键值</p>
     */
    public void setStKey(String stKey) {
        this.stKey = stKey;
    }

    /**
     * <p>对象</p>
     */
    public String getStObject() {
        return stObject;
    }

    /**
     * <p>对象</p>
     */
    public void setStObject(String stObject) {
        this.stObject = stObject;
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
        map.put("stId", stId);
        map.put("org", org);
        map.put("stName", stName);
        map.put("remark", remark);
        map.put("stClass", stClass);
        map.put("stKey", stKey);
        map.put("stObject", stObject);
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
        "stId="+stId+
        "org="+org+
        "stName="+stName+
        "remark="+remark+
        "stClass="+stClass+
        "stKey="+stKey+
        "stObject="+stObject+
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
        if (stName == null) stName = "";
        if (remark == null) remark = "";
        if (stClass == null) stClass = "";
        if (stKey == null) stKey = "";
        if (stObject == null) stObject = "";
        if (ifUsed == null) ifUsed = "";
        if (createDate == null) createDate = new Date();
        if (createBy == null) createBy = "";
        if (updateDate == null) updateDate = new Date();
        if (updateBy == null) updateBy = "";
        if (jpaVersion == null) jpaVersion = 0l;
    }
}