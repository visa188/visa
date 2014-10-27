package com.visa.po.line;

import java.math.BigDecimal;
import java.util.Date;

import com.visa.common.util.FieldDes;

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
     * 占位数量
     * 
     * @pdOid 5e14ffc9-fda7-4aec-b56a-a9f0c9c6cf38
     */
    public int nameListSize;
    public int nameListType;
    public int nameListState;
    public Date nameListlimitDate;
    /**
     * 双人房数量
     */
    public int srf;
    /**
     * 单人房数量
     */
    public int drf;
    /**
     * 拼房数量
     */
    public int pf;
    /**
     * 销售id
     * 
     * @pdOid 704cf255-fb37-4469-b283-ac94cabf63e9
     */
    public String salesmanId;
    public String salesmanName;
    /**
     * 线路操作员id
     * 
     * @pdOid cbd8e698-2da8-4317-b94e-7e85b1d0011e
     */
    public String lineOperatorId;
    public String lineOperatorName;
    /**
     * 签证操作员id
     * 
     * @pdOid 84c17522-1cd8-4b5f-8b20-1078a0546905
     */
    public String visaOperatorId;
    public String visaOperatorName;

    public String signOperatorId;
    public String signOperatorName;

    public Integer lineProductId;
    public String lineProductName;
    public String lineProductOrderSeq;
    /**
     * 客人ID
     * 
     * @pdOid 530eca3d-f958-4382-8824-8a30d9a2b6d8
     */
    public int customerId;

    /**
     * 线路国家id
     * 
     * @pdOid ed3abc93-1497-46e9-8a5a-3eb37b4a7eb4
     */
    public String lineCountryId;
    public String lineCountryName;
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
    public BigDecimal priceSum;
    public BigDecimal alreadyGot;
    public BigDecimal needGot;
    public String gotBank;
    public Date gotDate;
    public BigDecimal paidPriceSum;
    /**
     * 线路订单状态
     * 
     * @pdOid 039a9580-614d-44ec-89e9-c2ffcc97d52e
     */
    public int status;

    public int jdstatus;
    public int busstatus;
    public int tuanstatus;

    public String orderSeq;
    public String specialComment;
    /** @pdOid ac5cabc4-f167-49b7-9590-f036f58a74df */
    public Date postTime;
    public BigDecimal profit;
    public BigDecimal qtys;

    public BigDecimal lineOrderDeposit;
    public BigDecimal commission;

    private String dyczb;
    private String deczb;

    public int yshkstatus;
    public int yfhkstatus;

    public String qtysRemark;

    public String garanteeFileUrl;

    public BigDecimal getLineOrderDeposit() {
        return lineOrderDeposit;
    }

    public void setLineOrderDeposit(BigDecimal lineOrderDeposit) {
        this.lineOrderDeposit = lineOrderDeposit;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public String getQtysRemark() {
        return qtysRemark;
    }

    public void setQtysRemark(String qtysRemark) {
        this.qtysRemark = qtysRemark;
    }

    public String getGaranteeFileUrl() {
        return garanteeFileUrl;
    }

    public void setGaranteeFileUrl(String garanteeFileUrl) {
        this.garanteeFileUrl = garanteeFileUrl;
    }

    public int getYshkstatus() {
        return yshkstatus;
    }

    public void setYshkstatus(int yshkstatus) {
        this.yshkstatus = yshkstatus;
    }

    public int getYfhkstatus() {
        return yfhkstatus;
    }

    public void setYfhkstatus(int yfhkstatus) {
        this.yfhkstatus = yfhkstatus;
    }

    @FieldDes(fieldDes = "占位类型#1&切位@2&占位@3&预报")
    public int getNameListType() {
        return nameListType;
    }

    public void setNameListType(int nameListType) {
        this.nameListType = nameListType;
    }

    @FieldDes(fieldDes = "占位状态#1&已收定金@2&已收资料")
    public int getNameListState() {
        return nameListState;
    }

    public void setNameListState(int nameListState) {
        this.nameListState = nameListState;
    }

    @FieldDes(fieldDes = "占位取消时限")
    public Date getNameListlimitDate() {
        return nameListlimitDate;
    }

    public void setNameListlimitDate(Date nameListlimitDate) {
        this.nameListlimitDate = nameListlimitDate;
    }

    @FieldDes(fieldDes = "其它应收")
    public BigDecimal getQtys() {
        return qtys;
    }

    public void setQtys(BigDecimal qtys) {
        this.qtys = qtys;
    }

    @FieldDes(fieldDes = "总计应付")
    public BigDecimal getPaidPriceSum() {
        return paidPriceSum;
    }

    public void setPaidPriceSum(BigDecimal paidPriceSum) {
        this.paidPriceSum = paidPriceSum;
    }

    @FieldDes(fieldDes = "毛利")
    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    @FieldDes(fieldDes = "团费总计")
    public BigDecimal getPriceSum() {
        return priceSum;
    }

    public void setPriceSum(BigDecimal priceSum) {
        this.priceSum = priceSum;
    }

    @FieldDes(fieldDes = "已收团费")
    public BigDecimal getAlreadyGot() {
        return alreadyGot;
    }

    public void setAlreadyGot(BigDecimal alreadyGot) {
        this.alreadyGot = alreadyGot;
    }

    @FieldDes(fieldDes = "未收团费")
    public BigDecimal getNeedGot() {
        return needGot;
    }

    public void setNeedGot(BigDecimal needGot) {
        this.needGot = needGot;
    }

    @FieldDes(fieldDes = "入账银行")
    public String getGotBank() {
        return gotBank;
    }

    public void setGotBank(String gotBank) {
        this.gotBank = gotBank;
    }

    @FieldDes(fieldDes = "入账日期")
    public Date getGotDate() {
        return gotDate;
    }

    public void setGotDate(Date gotDate) {
        this.gotDate = gotDate;
    }

    public String getLineProductName() {
        return lineProductName;
    }

    public void setLineProductName(String lineProductName) {
        this.lineProductName = lineProductName;
    }

    public String getLineCountryName() {
        return lineCountryName;
    }

    public void setLineCountryName(String lineCountryName) {
        this.lineCountryName = lineCountryName;
    }

    public String getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
    }

    @FieldDes(fieldDes = "线路产品")
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

    @FieldDes(fieldDes = "下单日期")
    public java.util.Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(java.util.Date orderDate) {
        this.orderDate = orderDate;
    }

    @FieldDes(fieldDes = "订单类型")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @FieldDes(fieldDes = "客人数量")
    public int getNameListSize() {
        return nameListSize;
    }

    @FieldDes(fieldDes = "双人房数量")
    public int getSrf() {
        return srf;
    }

    public void setSrf(int srf) {
        this.srf = srf;
    }

    @FieldDes(fieldDes = "单人房数量")
    public int getDrf() {
        return drf;
    }

    public void setDrf(int drf) {
        this.drf = drf;
    }

    @FieldDes(fieldDes = "拼房数量")
    public int getPf() {
        return pf;
    }

    public void setPf(int pf) {
        this.pf = pf;
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

    @FieldDes(fieldDes = "线路操作员")
    public String getLineOperatorId() {
        return lineOperatorId;
    }

    public void setLineOperatorId(String lineOperatorId) {
        this.lineOperatorId = lineOperatorId;
    }

    @FieldDes(fieldDes = "签证操作员")
    public String getVisaOperatorId() {
        return visaOperatorId;
    }

    public void setVisaOperatorId(String visaOperatorId) {
        this.visaOperatorId = visaOperatorId;
    }

    @FieldDes(fieldDes = "客人ID")
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @FieldDes(fieldDes = "线路国家")
    public String getLineCountryId() {
        return lineCountryId;
    }

    public void setLineCountryId(String lineCountryId) {
        this.lineCountryId = lineCountryId;
    }

    public Date getStartDate() {
        return startDate;
    }

    @FieldDes(fieldDes = "出发日期")
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    @FieldDes(fieldDes = "归国日期")
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTravelInfo() {
        return travelInfo;
    }

    @FieldDes(fieldDes = "行程简介")
    public void setTravelInfo(String travelInfo) {
        this.travelInfo = travelInfo;
    }

    public String getTravelInfoFileUrl() {
        return travelInfoFileUrl;
    }

    public void setTravelInfoFileUrl(String travelInfoFileUrl) {
        this.travelInfoFileUrl = travelInfoFileUrl;
    }

    @FieldDes(fieldDes = "航空公司")
    public int getAircorpId() {
        return aircorpId;
    }

    public void setAircorpId(int aircorpId) {
        this.aircorpId = aircorpId;
    }

    @FieldDes(fieldDes = "团费")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @FieldDes(fieldDes = "订单状态")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @FieldDes(fieldDes = "特殊说明")
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

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    @FieldDes(fieldDes = "线路操作员")
    public String getLineOperatorName() {
        return lineOperatorName;
    }

    public void setLineOperatorName(String lineOperatorName) {
        this.lineOperatorName = lineOperatorName;
    }

    @FieldDes(fieldDes = "签证操作员")
    public String getVisaOperatorName() {
        return visaOperatorName;
    }

    public void setVisaOperatorName(String visaOperatorName) {
        this.visaOperatorName = visaOperatorName;
    }

    public String getSignOperatorId() {
        return signOperatorId;
    }

    public void setSignOperatorId(String signOperatorId) {
        this.signOperatorId = signOperatorId;
    }

    @FieldDes(fieldDes = "送签员")
    public String getSignOperatorName() {
        return signOperatorName;
    }

    public void setSignOperatorName(String signOperatorName) {
        this.signOperatorName = signOperatorName;
    }

    public String getLineProductOrderSeq() {
        return lineProductOrderSeq;
    }

    public void setLineProductOrderSeq(String lineProductOrderSeq) {
        this.lineProductOrderSeq = lineProductOrderSeq;
    }

    public int getJdstatus() {
        return jdstatus;
    }

    public void setJdstatus(int jdstatus) {
        this.jdstatus = jdstatus;
    }

    public int getBusstatus() {
        return busstatus;
    }

    public void setBusstatus(int busstatus) {
        this.busstatus = busstatus;
    }

    public int getTuanstatus() {
        return tuanstatus;
    }

    public void setTuanstatus(int tuanstatus) {
        this.tuanstatus = tuanstatus;
    }

    public String getDyczb() {
        return dyczb;
    }

    public void setDyczb(String dyczb) {
        this.dyczb = dyczb;
    }

    public String getDeczb() {
        return deczb;
    }

    public void setDeczb(String deczb) {
        this.deczb = deczb;
    }

}
