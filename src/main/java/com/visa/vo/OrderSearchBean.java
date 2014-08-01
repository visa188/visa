package com.visa.vo;

import java.io.Serializable;

/**
 * @author user yrwang
 */
public class OrderSearchBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String startDate = "";
    private String endDate = "";
    private String seachCustomerName = "";
    private String seachCustomerCompany = "";
    private String seachNameList = "";
    private String status = "";
    private String seachYfhkStatus = "";
    private String seachYshkStatus = "";
    private String salesman = "";
    private String operator = "";
    private String seachCountryName = "";

    public OrderSearchBean() {
    }

    public String getSeachYshkStatus() {
        return seachYshkStatus;
    }

    public void setSeachYshkStatus(String seachYshkStatus) {
        this.seachYshkStatus = seachYshkStatus;
    }

    public String getSeachCountryName() {
        return seachCountryName;
    }

    public void setSeachCountryName(String seachCountryName) {
        this.seachCountryName = seachCountryName;
    }

    public String getSeachCustomerCompany() {
        return seachCustomerCompany;
    }

    public void setSeachCustomerCompany(String seachCustomerCompany) {
        this.seachCustomerCompany = seachCustomerCompany;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public String getSeachYfhkStatus() {
        return seachYfhkStatus;
    }

    public void setSeachYfhkStatus(String seachYfhkStatus) {
        this.seachYfhkStatus = seachYfhkStatus;
    }

    public String getSeachCustomerName() {
        return seachCustomerName;
    }

    public void setSeachCustomerName(String seachCustomerName) {
        this.seachCustomerName = seachCustomerName;
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
