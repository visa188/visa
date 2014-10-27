package com.visa.po.line;

import java.math.BigDecimal;
import java.util.Date;

/** @pdOid 8b37f208-abf9-4751-80d4-707c7101f3db */
public class LineProduct {
    /**
     * 主键
     * 
     * @pdOid 7946774b-6cce-47df-91dc-55411a29af5b
     */
    public int lineProductId;
    /**
     * 线路国家id
     * 
     * @pdOid fc293ed6-96f3-45fe-bc57-2a1009d0dac4
     */
    public int lineCountryId;
    /** @pdOid 65bd2c51-1cce-426b-bcef-9a2fb7978653 */
    public String lineProductName;
    /**
     * 领队
     * 
     * @pdOid 0cba1ce6-2d8d-4c09-a48c-6b564a9e7152
     */
    public String groupLeader;
    /**
     * 航空公司
     * 
     * @pdOid c1fdd8b0-08a6-42b1-aa38-171a9e82bd15
     */
    public int aircorpId;
    /**
     * 机票定金时限
     * 
     * @pdOid 8a30b529-2e7f-44cb-9084-709909c19fc7
     */
    public Date depositLimitDate;
    /**
     * 出票时限
     * 
     * @pdOid 3c507ad2-c077-49fb-a648-3d57f063aafb
     */
    public Date printTicketLimitDate;
    /**
     * 机位占位数
     * 
     * @pdOid 795dfd1b-4f13-4733-84b5-3a180cb1f6dd
     */
    public int seatNum;
    /**
     * 切位
     */
    public int qw;
    /**
     * 占位
     */
    public int zw;
    /**
     * 预报
     */
    public int yb;
    /**
     * 剩余机位数
     * 
     * @pdOid 1ee2a17b-5931-440d-b1e1-10c20db5d6a2
     */
    public int leftSeatNum;
    /**
     * 单价
     * 
     * @pdOid c4cfda10-67df-4a62-b98a-be22051af4f1
     */
    public BigDecimal price;
    /** @pdOid e26e1c3f-0ea9-4e73-829f-d7365ba5ac05 */
    public String des;

    public Date startDate;
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
    public String orderSeq;

    private String jpdjType;
    private BigDecimal jpdj;

    public String getJpdjType() {
        return jpdjType;
    }

    public void setJpdjType(String jpdjType) {
        this.jpdjType = jpdjType;
    }

    public BigDecimal getJpdj() {
        return jpdj;
    }

    public void setJpdj(BigDecimal jpdj) {
        this.jpdj = jpdj;
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

    public int getAircorpId() {
        return aircorpId;
    }

    public void setAircorpId(int aircorpId) {
        this.aircorpId = aircorpId;
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

    public int getLineProductId() {
        return lineProductId;
    }

    public void setLineProductId(int lineProductId) {
        this.lineProductId = lineProductId;
    }

    public int getLineCountryId() {
        return lineCountryId;
    }

    public void setLineCountryId(int lineCountryId) {
        this.lineCountryId = lineCountryId;
    }

    public String getLineProductName() {
        return lineProductName;
    }

    public void setLineProductName(String lineProductName) {
        this.lineProductName = lineProductName;
    }

    public String getGroupLeader() {
        return groupLeader;
    }

    public void setGroupLeader(String groupLeader) {
        this.groupLeader = groupLeader;
    }

    public int getAirCorpId() {
        return aircorpId;
    }

    public void setAirCorpId(int aircorpId) {
        this.aircorpId = aircorpId;
    }

    public Date getDepositLimitDate() {
        return depositLimitDate;
    }

    public void setDepositLimitDate(Date depositLimitDate) {
        this.depositLimitDate = depositLimitDate;
    }

    public Date getPrintTicketLimitDate() {
        return printTicketLimitDate;
    }

    public void setPrintTicketLimitDate(Date printTicketLimitDate) {
        this.printTicketLimitDate = printTicketLimitDate;
    }

    public int getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

    public int getLeftSeatNum() {
        return leftSeatNum;
    }

    public void setLeftSeatNum(int leftSeatNum) {
        this.leftSeatNum = leftSeatNum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
    }

    public int getQw() {
        return qw;
    }

    public void setQw(int qw) {
        this.qw = qw;
    }

    public int getZw() {
        return zw;
    }

    public void setZw(int zw) {
        this.zw = zw;
    }

    public int getYb() {
        return yb;
    }

    public void setYb(int yb) {
        this.yb = yb;
    }

}
