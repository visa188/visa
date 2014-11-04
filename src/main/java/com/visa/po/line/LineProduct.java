package com.visa.po.line;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.visa.common.constant.LineSrviceEnumType;

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

    private String ld;
    private String qz;
    private String jp;
    private String dj;
    private String qt;

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

    public String getLd() {
        return ld;
    }

    public void setLd(String ld) {
        this.ld = ld;
    }

    public String getQz() {
        return qz;
    }

    public void setQz(String qz) {
        this.qz = qz;
    }

    public String getJp() {
        return jp;
    }

    public void setJp(String jp) {
        this.jp = jp;
    }

    public String getDj() {
        return dj;
    }

    public void setDj(String dj) {
        this.dj = dj;
    }

    public String getQt() {
        return qt;
    }

    public void setQt(String qt) {
        this.qt = qt;
    }

    public List<LinesSrvice> getLineOrderService() {
        List<LinesSrvice> serviceList = new ArrayList<LinesSrvice>();
        if (ld != null && ld.startsWith("1")) {
            ld = ld.replaceAll("undefined", "#");
            LinesSrvice linesSrvice = new LinesSrvice(lineProductId, LineSrviceEnumType.LD.getId());
            String[] items = ld.split("_");
            if (!"#".equals(items[1])) {
                linesSrvice.setServiceId(Integer.valueOf(items[1]));
            }
            // 组团社派 1/ 起航假期派 2
            linesSrvice.setServiceItem1(items[2]);
            if (!"#".equals(items[3])) {
                linesSrvice.setServiceOperator(items[3]);
            }
            if (!"#".equals(items[4])) {
                linesSrvice.setServiceItem2(items[4]);
            }
            if (!"#".equals(items[4])) {
                linesSrvice.setServiceItem2(items[4]);
            }
            serviceList.add(linesSrvice);
        }
        if (qz != null && qz.startsWith("1")) {
            qz = qz.replaceAll("undefined", "#");
            LinesSrvice linesSrvice = new LinesSrvice(lineProductId, LineSrviceEnumType.QZ.getId());
            String[] items = qz.split("_");
            if (!"#".equals(items[1])) {
                linesSrvice.setServiceId(Integer.valueOf(items[1]));
            }
            // 个签 1/ 团签 2
            linesSrvice.setServiceItem1(items[2]);
            if (!"#".equals(items[3])) {
                linesSrvice.setServicePrice(new BigDecimal(items[3])); // 单价
            }
            if (!"#".equals(items[4])) {
                linesSrvice.setPriceSum(new BigDecimal(items[4])); // 合计
            }
            if (!"#".equals(items[5])) {
                linesSrvice.setServiceOperator(items[5]);
            }
            serviceList.add(linesSrvice);
        }
        if (jp != null && jp.startsWith("1")) {
            jp = jp.replaceAll("undefined", "#");
            LinesSrvice linesSrvice = new LinesSrvice(lineProductId, LineSrviceEnumType.JP.getId());
            String[] items = jp.split("_");
            if (!"#".equals(items[1])) {
                linesSrvice.setServiceId(Integer.valueOf(items[1]));
            }
            // 团队机票 1/ 散客机票 2
            linesSrvice.setServiceItem1(items[2]);
            if (!"#".equals(items[3])) {
                linesSrvice.setServicePrice(new BigDecimal(items[3])); // 单价
            }
            if (!"#".equals(items[4])) {
                linesSrvice.setPriceSum(new BigDecimal(items[4])); // 合计
            }
            if (!"#".equals(items[5])) {
                linesSrvice.setServiceOperator(items[5]);
            }
            if (!"#".equals(items[6])) {
                linesSrvice.setServiceItem5(items[6]);
            }
            if (!"#".equals(items[7])) {
                linesSrvice.setServiceItem6(items[7]);
            }
            if (!"#".equals(items[8])) {
                linesSrvice.setServiceItem7(items[8]);
            }
            if (!"#".equals(items[9])) {
                linesSrvice.setServiceItem8(items[9]);
            }
            serviceList.add(linesSrvice);
        }
        if (dj != null && dj.startsWith("1")) {
            dj = dj.replaceAll("undefined", "#");
            String[] items = dj.split("_");
            // 41 地接 巴士 / 42 地接司机兼导游
            int serviceType = LineSrviceEnumType.DJBS.getId();
            if ("b".equals(items[6])) {
                serviceType = LineSrviceEnumType.DJSJDY.getId();
            }
            LinesSrvice linesSrvice = new LinesSrvice(lineProductId, serviceType);

            if (!"#".equals(items[1])) {
                linesSrvice.setServiceId(Integer.valueOf(items[1]));
            }

            if (!"#".equals(items[2])) {
                linesSrvice.setServiceItem1(items[2]); // 酒店星级
            }
            if (!"#".equals(items[3])) {
                linesSrvice.setServiceItem2(items[3]); // 房型
            }
            if (!"#".equals(items[4])) {
                linesSrvice.setServiceItem3(items[4]); // 备注
            }
            if (!"#".equals(items[5])) {
                linesSrvice.setServiceItem4(items[5]); // 特殊景点
            }
            if (!"#".equals(items[7])) {
                if (serviceType == LineSrviceEnumType.DJBS.getId()) {
                    linesSrvice.setServiceItem5(items[7]); // 41 巴士
                } else {
                    linesSrvice.setServiceItem12(items[7]); // 42 备注
                }
            }
            if (!"#".equals(items[8])) {
                linesSrvice.setServiceItem6(items[8]); // 41 的备注
            }
            if (!"#".equals(items[9])) {
                linesSrvice.setServiceItem7(items[9]); // 41 的导游
            }
            if (!"#".equals(items[10])) {
                linesSrvice.setServiceItem8(items[10]); // 41 的导游的备注
            }
            if (!"#".equals(items[11])) {
                linesSrvice.setServicePrice(new BigDecimal(items[11])); // 单价
            }
            if (!"#".equals(items[12])) {
                linesSrvice.setPriceSum(new BigDecimal(items[12])); // 合计
            }
            if (!"#".equals(items[13])) {
                linesSrvice.setServiceOperator(items[13]);
            }
            if (!"#".equals(items[14])) {
                linesSrvice.setServiceItem10(items[14]);
            }
            if (!"#".equals(items[15])) {
                linesSrvice.setServiceItem11(items[15]);
            }
            serviceList.add(linesSrvice);
        }
        if (qt != null && qt.startsWith("1")) {
            qt = qt.replaceAll("undefined", "#");
            LinesSrvice linesSrvice = new LinesSrvice(lineProductId, LineSrviceEnumType.QT.getId());
            String[] items = qt.split("_");
            if (!"#".equals(items[1])) {
                linesSrvice.setServiceId(Integer.valueOf(items[1]));
            }
            if (!"#".equals(items[2])) {
                linesSrvice.setServiceItem1(items[2]);
            }
            if (!"#".equals(items[3])) {
                linesSrvice.setServicePrice(new BigDecimal(items[3])); // 单价
            }
            if (!"#".equals(items[4])) {
                linesSrvice.setPriceSum(new BigDecimal(items[4])); // 合计
            }
            serviceList.add(linesSrvice);
        }
        
        return serviceList;
    }
}
