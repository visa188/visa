package com.visa.vo;

import com.visa.po.Customer;

public class CustomerVo extends Customer {

    private String salesmanName;

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

}
