package com.visa.po;

import java.util.Date;

public class User {
    private String userId;

    private String userName;

    private Integer roleId;

    private Integer lineRoleId;

    private String pwd;

    private String managerId;

    private String lineManagerId;

    private Date postDt;

    private String deptId;

    private int enable;

    private String linecountryId;

    public String getLineManagerId() {
        return lineManagerId;
    }

    public void setLineManagerId(String lineManagerId) {
        this.lineManagerId = lineManagerId;
    }

    public String getLinecountryId() {
        return linecountryId;
    }

    public void setLinecountryId(String linecountryId) {
        this.linecountryId = linecountryId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getLineRoleId() {
        return lineRoleId;
    }

    public void setLineRoleId(Integer lineRoleId) {
        this.lineRoleId = lineRoleId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public Date getPostDt() {
        return postDt;
    }

    public void setPostDt(Date postDt) {
        this.postDt = postDt;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

}
