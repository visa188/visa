package com.visa.vo.line;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.visa.common.constant.LineSrviceEnumType;
import com.visa.po.line.LineNameList;
import com.visa.po.line.LineOrder;
import com.visa.po.line.LinesSrvice;

public class LineOrderVo extends LineOrder {

    private String ld;
    private String qz;
    private String jp;
    private String dj;
    private String qt;

    private String nameList;

    private String signDate;
    private BigDecimal servicePayPrice;
    private String datumLimitDate;
    private String datumIsready;
    private String garanteeType;
    private Integer garanteeServiceId;
    private BigDecimal garanteePrice;

    public List<LinesSrvice> getLineOrderService() {
        List<LinesSrvice> serviceList = new ArrayList<LinesSrvice>();
        if (ld.startsWith("1")) {
            LinesSrvice linesSrvice = new LinesSrvice(orderId, LineSrviceEnumType.LD.getId());
            String[] items = ld.split("_");
            if (!"#".equals(items[1])) {
                linesSrvice.setServiceId(Integer.valueOf(items[1]));
            }
            // 组团社派 1/ 起航假期派 2
            linesSrvice.setServiceItem1(items[2]);
            if (!"#".equals(items[3])) {
                linesSrvice.setServiceOperator(items[3]);
            }
            serviceList.add(linesSrvice);
        }
        if (qz.startsWith("1")) {
            LinesSrvice linesSrvice = new LinesSrvice(orderId, LineSrviceEnumType.QZ.getId());
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
        if (jp.startsWith("1")) {
            LinesSrvice linesSrvice = new LinesSrvice(orderId, LineSrviceEnumType.JP.getId());
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
            serviceList.add(linesSrvice);
        }
        if (dj.startsWith("1")) {
            String[] items = dj.split("_");
            // 41 地接 巴士 / 42 地接司机兼导游
            int serviceType = LineSrviceEnumType.DJBS.getId();
            if ("b".equals(items[6])) {
                serviceType = LineSrviceEnumType.DJSJDY.getId();
            }
            LinesSrvice linesSrvice = new LinesSrvice(orderId, serviceType);

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
                linesSrvice.setServiceItem5(items[7]); // 41 巴士 / 42 备注
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
            serviceList.add(linesSrvice);
        }
        if (qt.startsWith("1")) {
            LinesSrvice linesSrvice = new LinesSrvice(orderId, LineSrviceEnumType.QT.getId());
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
        if (!StringUtils.isEmpty(getGaranteeType())) {
            LinesSrvice linesSrvice = new LinesSrvice(orderId, LineSrviceEnumType.GGBZ.getId());
            linesSrvice.setServiceId(getGaranteeServiceId());
            linesSrvice.setServiceItem1(getGaranteeType());
            if (getGaranteePrice() != null) {
                linesSrvice.setPriceSum(getGaranteePrice());
            }
            serviceList.add(linesSrvice);
        }
        return serviceList;
    }

    public List<LineNameList> getLineCustomList() {
        List<LineNameList> customList = new ArrayList<LineNameList>();
        if (nameList != null) {
            String[] customArray = nameList.split(",");
            for (String customStr : customArray) {
                String[] items = customStr.split("_");
                LineNameList nameListPo = new LineNameList();
                nameListPo.setLineOrderId(orderId);
                nameListPo.setName(items[0].replace("#", ""));
                nameListPo.setSex(Integer.valueOf(items[1].replace("#", "")));
                nameListPo.setAgeType(Integer.valueOf(items[2].replace("#", "")));
                nameListPo.setDeposit(items[3].replace("#", ""));
                nameListPo.setDatum(items[4].replace("#", ""));
                nameListPo.setRoom(Integer.valueOf(items[5].replace("#", "")));
                nameListPo.setComment(items[6].replace("#", ""));
                if (!"#".equals(items[7])) {
                    nameListPo.setId(Integer.valueOf(items[7]));
                }
                customList.add(nameListPo);
            }
        }
        return customList;
    }

    public Integer getGaranteeServiceId() {
        return garanteeServiceId;
    }

    public void setGaranteeServiceId(Integer garanteeServiceId) {
        this.garanteeServiceId = garanteeServiceId;
    }

    public String getGaranteeType() {
        return garanteeType;
    }

    public void setGaranteeType(String garanteeType) {
        this.garanteeType = garanteeType;
    }

    public BigDecimal getGaranteePrice() {
        return garanteePrice;
    }

    public void setGaranteePrice(BigDecimal garanteePrice) {
        this.garanteePrice = garanteePrice;
    }

    public String getDatumLimitDate() {
        return datumLimitDate;
    }

    public void setDatumLimitDate(String datumLimitDate) {
        this.datumLimitDate = datumLimitDate;
    }

    public String getDatumIsready() {
        return datumIsready;
    }

    public void setDatumIsready(String datumIsready) {
        this.datumIsready = datumIsready;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public BigDecimal getServicePayPrice() {
        return servicePayPrice;
    }

    public void setServicePayPrice(BigDecimal servicePayPrice) {
        this.servicePayPrice = servicePayPrice;
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

    public String getNameList() {
        return nameList;
    }

    public void setNameList(String nameList) {
        this.nameList = nameList;
    }

}
