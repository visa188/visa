package com.visa.vo.line;

import java.io.Serializable;

/**
 * @author user yrwang
 */
public class LogSearchBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String startDate = "";
    private String endDate = "";
    private String userName = "";
    private String orderSeq = "";

    public LogSearchBean() {
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
    }

}
