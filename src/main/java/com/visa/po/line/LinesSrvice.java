package com.visa.po.line;

import java.math.BigDecimal;
import java.util.Date;

import com.visa.common.constant.LineSrviceEnumType;
import com.visa.common.util.FieldDes;

/** @pdOid 7eb4a52d-c3f6-47d8-8256-7886c71d4421 */
public class LinesSrvice {
    public LinesSrvice(int orderId, int serviceType) {
        this.orderId = orderId;
        this.serviceType = serviceType;
        this.serviceName = LineSrviceEnumType.LINE_SRVICE_MAP.get(serviceType).getName();
    }

    public LinesSrvice() {
    }

    /**
     * 主键
     * 
     * @pdOid 8c34c129-f8f3-4d65-bf01-efb7653878fe
     */
    public int serviceId;
    /**
     * 订单id
     * 
     * @pdOid 998ba21f-0a7c-4055-ab23-65a4dbaab647
     */
    public int orderId;
    /**
     * 费用名称
     * 
     * @pdOid 7b470ab9-aab9-482f-a252-c4a4d28d88fa
     */
    public String serviceName;
    /**
     * 费用类型
     * 
     * @pdOid 0ae2448c-63fa-4f20-9575-6ac4d59daa21
     */
    public int serviceType;
    /**
     * 单价
     * 
     * @pdOid 026063bf-35d7-4e16-98d7-c12b0ce38d83
     */
    public BigDecimal servicePrice;
    /** @pdOid 3cdd0ad2-2585-421d-83e7-ffd0ced86acb */
    public String serviceOperator;
    /** @pdOid 0f5b9a45-8109-4205-8433-67e09ac2c675 */
    public String serviceItem1;
    /** @pdOid 3a7f2917-f525-4942-a300-5b9fe6751539 */
    public String serviceItem2;
    /** @pdOid d3bbc5fc-9114-49ee-b832-52d070a41a97 */
    public String serviceItem3;
    /** @pdOid 173f7193-8f85-456b-912a-fb286ff0057a */
    public String serviceItem4;
    /** @pdOid 8d565911-dabd-46c2-94ec-c21584719cf7 */
    public String serviceItem5;
    /** @pdOid 9d26ca88-29a9-4192-affd-c63fb97850c1 */
    public String serviceItem6;
    public String serviceItem7;
    public String serviceItem8;
    /**
     * 合计
     * 
     * @pdOid fe16c5de-f39c-49d9-a595-e2a169cdb0f2
     */
    public BigDecimal priceSum;
    /**
     * 已付
     * 
     * @pdOid d7841769-8bb3-47bd-9173-577e3d9bc5ad
     */
    public BigDecimal alreadyPaid;
    /**
     * 未付
     * 
     * @pdOid 5c030721-69dd-4f7e-965e-0e9201dafe27
     */
    public BigDecimal needPaid;
    /**
     * 付款银行
     * 
     * @pdOid f496f4a4-7dc4-4870-bf26-ba69cbfc8776
     */
    public String paidBank;
    /**
     * 付款日期
     * 
     * @pdOid 2eeaaae7-5b39-4b1e-8d31-9b45091aff0a
     */
    public Date paidDate;
    /**
     * 已收
     * 
     * @pdOid 2aac04a1-9747-43c5-bc00-e2d154c00f43
     */
    public BigDecimal alreadyGot;
    /**
     * 未收
     * 
     * @pdOid 3aa732d9-24d0-4d4b-a7bb-6ae2f18b9883
     */
    public BigDecimal needGot;
    /**
     * 收款银行
     * 
     * @pdOid 8f07c280-9e0e-4290-8594-83b3e20eb8c7
     */
    public String gotBank;
    /**
     * 收款日期
     * 
     * @pdOid cd10b7f9-f9df-46fa-9851-dfb4c38c6e1d
     */
    public Date gotDate;
    /**
     * 费用备注
     * 
     * @pdOid 9ff1a76d-ae73-41eb-8c05-5f0aee7b6e43
     */
    public String feeComment;
    /** @pdOid b9729eb6-c714-4e1d-8cf1-1b31ea3a939e */
    public Date postTime;

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public BigDecimal getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceOperator() {
        return serviceOperator;
    }

    public void setServiceOperator(String serviceOperator) {
        this.serviceOperator = serviceOperator;
    }

    public String getServiceItem1() {
        return serviceItem1;
    }

    public void setServiceItem1(String serviceItem1) {
        this.serviceItem1 = serviceItem1;
    }

    public String getServiceItem2() {
        return serviceItem2;
    }

    public void setServiceItem2(String serviceItem2) {
        this.serviceItem2 = serviceItem2;
    }

    public String getServiceItem3() {
        return serviceItem3;
    }

    public void setServiceItem3(String serviceItem3) {
        this.serviceItem3 = serviceItem3;
    }

    public String getServiceItem4() {
        return serviceItem4;
    }

    public void setServiceItem4(String serviceItem4) {
        this.serviceItem4 = serviceItem4;
    }

    public String getServiceItem5() {
        return serviceItem5;
    }

    public void setServiceItem5(String serviceItem5) {
        this.serviceItem5 = serviceItem5;
    }

    public String getServiceItem6() {
        return serviceItem6;
    }

    public void setServiceItem6(String serviceItem6) {
        this.serviceItem6 = serviceItem6;
    }

    public String getServiceItem7() {
        return serviceItem7;
    }

    public void setServiceItem7(String serviceItem7) {
        this.serviceItem7 = serviceItem7;
    }

    public String getServiceItem8() {
        return serviceItem8;
    }

    public void setServiceItem8(String serviceItem8) {
        this.serviceItem8 = serviceItem8;
    }

    @FieldDes(fieldDes = "合计")
    public BigDecimal getPriceSum() {
        return priceSum;
    }

    public void setPriceSum(BigDecimal priceSum) {
        this.priceSum = priceSum;
    }

    @FieldDes(fieldDes = "已付金额")
    public BigDecimal getAlreadyPaid() {
        return alreadyPaid;
    }

    public void setAlreadyPaid(BigDecimal alreadyPaid) {
        this.alreadyPaid = alreadyPaid;
    }

    @FieldDes(fieldDes = "未付金额")
    public BigDecimal getNeedPaid() {
        return needPaid;
    }

    public void setNeedPaid(BigDecimal needPaid) {
        this.needPaid = needPaid;
    }

    @FieldDes(fieldDes = "付款银行")
    public String getPaidBank() {
        return paidBank;
    }

    public void setPaidBank(String paidBank) {
        this.paidBank = paidBank;
    }

    @FieldDes(fieldDes = "付款日期")
    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    @FieldDes(fieldDes = "已收金额")
    public BigDecimal getAlreadyGot() {
        return alreadyGot;
    }

    public void setAlreadyGot(BigDecimal alreadyGot) {
        this.alreadyGot = alreadyGot;
    }

    @FieldDes(fieldDes = "未收金额")
    public BigDecimal getNeedGot() {
        return needGot;
    }

    public void setNeedGot(BigDecimal needGot) {
        this.needGot = needGot;
    }

    @FieldDes(fieldDes = "收款银行")
    public String getGotBank() {
        return gotBank;
    }

    public void setGotBank(String gotBank) {
        this.gotBank = gotBank;
    }

    @FieldDes(fieldDes = "收款日期")
    public Date getGotDate() {
        return gotDate;
    }

    public void setGotDate(Date gotDate) {
        this.gotDate = gotDate;
    }

    @FieldDes(fieldDes = "费用备注")
    public String getFeeComment() {
        return feeComment;
    }

    public void setFeeComment(String feeComment) {
        this.feeComment = feeComment;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

}
