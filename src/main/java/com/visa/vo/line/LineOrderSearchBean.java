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
    private String seachCustomerName = "";
    private String seachCustomerCompany = "";
    private String seachNameList = "";
    private String status = "";
    private String seachYfhkStatus = "";
    private String seachYshkStatus = "";
    private String salesman = "";
    private String operator = "";
    private String seachCountryName = "";
    private String deptId = "";

    public String getSeachCustomerName() {
        return seachCustomerName;
    }

    public void setSeachCustomerName(String seachCustomerName) {
        this.seachCustomerName = seachCustomerName;
    }

    public String getSeachCustomerCompany() {
        return seachCustomerCompany;
    }

    public void setSeachCustomerCompany(String seachCustomerCompany) {
        this.seachCustomerCompany = seachCustomerCompany;
    }

    public String getSeachNameList() {
        return seachNameList;
    }

    public void setSeachNameList(String seachNameList) {
        this.seachNameList = seachNameList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSeachYfhkStatus() {
        return seachYfhkStatus;
    }

    public void setSeachYfhkStatus(String seachYfhkStatus) {
        this.seachYfhkStatus = seachYfhkStatus;
    }

    public String getSeachYshkStatus() {
        return seachYshkStatus;
    }

    public void setSeachYshkStatus(String seachYshkStatus) {
        this.seachYshkStatus = seachYshkStatus;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getSeachCountryName() {
        return seachCountryName;
    }

    public void setSeachCountryName(String seachCountryName) {
        this.seachCountryName = seachCountryName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

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
