package com.visa.vo.line;

import java.io.Serializable;

/**
 * @author user yrwang
 */
public class LineOrderSearchBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String startDate = "";
    private String endDate = "";
    private String orderSeq;
    private Integer alarmOrders;

    public Integer getAlarmOrders() {
        return alarmOrders;
    }

    public void setAlarmOrders(Integer alarmOrders) {
        this.alarmOrders = alarmOrders;
    }

    public String getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
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

}
