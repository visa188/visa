package com.visa.po.line;

import java.util.Date;

/***********************************************************************
 * Module:  OperateLog.java
 * Author:  
 * Purpose: Defines the Class OperateLog
 ***********************************************************************/

/** @pdOid 647513ff-0857-452c-b7d5-548e18b4ae9b */
public class OperateLog {
    /** @pdOid 86165f8c-904b-4b67-b497-63d48c531d4a */
    public int logId;
    /** @pdOid 43843581-8219-4327-b92e-fe6ea6563912 */
    public String userId;
    /** @pdOid 60d7bb88-ad6d-4b0b-afe6-a6cbe9e6966c */
    public int roleId;
    /** @pdOid b0f39a33-2291-4220-9d09-7b18a41c1c8d */
    public int operateType;
    /** @pdOid 40032380-c287-47cb-aba7-efa9a39655a8 */
    public String operateDes;
    /** @pdOid 1bda69c8-ac86-4cc7-8e23-e367486ac363 */
    public Date operateTime;
    public String orderSeq;

    public String getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getOperateType() {
        return operateType;
    }

    public void setOperateType(int operateType) {
        this.operateType = operateType;
    }

    public String getOperateDes() {
        return operateDes;
    }

    public void setOperateDes(String operateDes) {
        this.operateDes = operateDes;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

}
