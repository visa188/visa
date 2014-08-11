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

}
