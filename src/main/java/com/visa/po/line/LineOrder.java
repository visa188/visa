package com.visa.po.line;

import java.math.BigDecimal;
import java.util.Date;

/***********************************************************************
 * Module:  LineOrder.java
 * Author:  bjyrwang
 * Purpose: Defines the Class LineOrder
 ***********************************************************************/

/** @pdOid 943c7476-f8c1-4605-9d21-874d038f6ae1 */
public class LineOrder {
    /**
     * 主键
     * 
     * @pdOid 7dfcc1d9-f9e5-4332-90ba-13c3d1ccaa5a
     */
    public int orderId;
    /**
     * 下单日期
     * 
     * @pdOid f996a3a7-7ce4-44f2-bea0-048d3ddee967
     */
    public Date orderDate;
    /**
     * 订单类型（1单团2散拼）
     * 
     * @pdOid 6b7682d4-66c1-448c-a85f-b4526c4af2eb
     */
    public int type;
    /**
     * 客人数量
     * 
     * @pdOid 5e14ffc9-fda7-4aec-b56a-a9f0c9c6cf38
     */
    public int nameListSize;
    /**
     * 销售id
     * 
     * @pdOid 704cf255-fb37-4469-b283-ac94cabf63e9
     */
    public String salesmanId;
    /**
     * 线路操作员id
     * 
     * @pdOid cbd8e698-2da8-4317-b94e-7e85b1d0011e
     */
    public String lineOperatorId;
    /**
     * 签证操作员id
     * 
     * @pdOid 84c17522-1cd8-4b5f-8b20-1078a0546905
     */
    public String visaOperatorId;

    public Integer lineProductId;
    /**
     * 组团社
     * 
     * @pdOid 530eca3d-f958-4382-8824-8a30d9a2b6d8
     */
    public String travelAgency;
    /**
     * 联系人
     * 
     * @pdOid 2a405cdf-c8de-4cf3-8c03-f7b0d3381037
     */
    public String contact;
    /**
     * 联系方式
     * 
     * @pdOid 2d845ed1-fe06-411d-bb65-f45f828b5ec9
     */
    public String contactNo;
    /**
     * 线路国家id
     * 
     * @pdOid ed3abc93-1497-46e9-8a5a-3eb37b4a7eb4
     */
    public String lineCountryId;
    /**
     * 出发日期
     * 
     * @pdOid 28f2650b-3319-4875-99fa-e3e76ecf11ff
     */
    public Date startDate;
    /**
     * 归国日期
     * 
     * @pdOid d9e70c98-5862-492e-b79d-e72ecd617041
     */
    public Date endDate;
    /**
     * 行程简介
     * 
     * @pdOid 2520f59a-f0dd-4c19-bce5-6db5a7af337f
     */
    public String travelInfo;
    /**
     * 行程文件上传地址
     * 
     * @pdOid dd766152-10e2-4418-95f5-74c9f2d6cc04
     */
    public String travelInfoFileUrl;
    /**
     * 所属航空公司
     * 
     * @pdOid 875e476f-4bbe-40a6-be64-6b2a38ed7942
     */
    public int aircorpId;
    /**
     * 订单单价
     * 
     * @pdOid f8303ff7-81db-4111-900b-d21f5ec968d0
     */
    public BigDecimal price;
    /**
     * 线路订单状态
     * 
     * @pdOid 039a9580-614d-44ec-89e9-c2ffcc97d52e
     */
    public int status;
    public String orderSeq;
    public String specialComment;
    /** @pdOid ac5cabc4-f167-49b7-9590-f036f58a74df */
    public Date postTime;

    public String getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
    }

    public Integer getLineProductId() {
        return lineProductId;
    }

    public void setLineProductId(Integer lineProductId) {
        this.lineProductId = lineProductId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public java.util.Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(java.util.Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNameListSize() {
        return nameListSize;
    }

    public void setNameListSize(int nameListSize) {
        this.nameListSize = nameListSize;
    }

    public String getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(String salesmanId) {
        this.salesmanId = salesmanId;
    }

    public String getLineOperatorId() {
        return lineOperatorId;
    }

    public void setLineOperatorId(String lineOperatorId) {
        this.lineOperatorId = lineOperatorId;
    }

    public String getVisaOperatorId() {
        return visaOperatorId;
    }

    public void setVisaOperatorId(String visaOperatorId) {
        this.visaOperatorId = visaOperatorId;
    }

    public String getTravelAgency() {
        return travelAgency;
    }

    public void setTravelAgency(String travelAgency) {
        this.travelAgency = travelAgency;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getLineCountryId() {
        return lineCountryId;
    }

    public void setLineCountryId(String lineCountryId) {
        this.lineCountryId = lineCountryId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTravelInfo() {
        return travelInfo;
    }

    public void setTravelInfo(String travelInfo) {
        this.travelInfo = travelInfo;
    }

    public String getTravelInfoFileUrl() {
        return travelInfoFileUrl;
    }

    public void setTravelInfoFileUrl(String travelInfoFileUrl) {
        this.travelInfoFileUrl = travelInfoFileUrl;
    }

    public int getAircorpId() {
        return aircorpId;
    }

    public void setAircorpId(int aircorpId) {
        this.aircorpId = aircorpId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSpecialComment() {
        return specialComment;
    }

    public void setSpecialComment(String specialComment) {
        this.specialComment = specialComment;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

}
