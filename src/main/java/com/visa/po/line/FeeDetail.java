package com.visa.po.line;

import java.math.BigDecimal;
import java.util.Date;

/***********************************************************************
 * Author:
 ***********************************************************************/

public class FeeDetail {
    public int id;
    public int orderId;
    public int feeType;
    public BigDecimal feeAmount;
    public Date feeDate;
    public String feeBank;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFeeType() {
        return feeType;
    }

    public void setFeeType(int feeType) {
        this.feeType = feeType;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public Date getFeeDate() {
        return feeDate;
    }

    public void setFeeDate(Date feeDate) {
        this.feeDate = feeDate;
    }

    public String getFeeBank() {
        return feeBank;
    }

    public void setFeeBank(String feeBank) {
        this.feeBank = feeBank;
    }

}
