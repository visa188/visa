package com.visa.vo.line;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
    private BigDecimal sumServicePayPrice;
    private String datumLimitDate;
    private String datumIsready;
    private String garanteeType;
    private Integer garanteeServiceId;
    private BigDecimal garanteePrice;

    // 机票
    private String yfjpkType;
    private BigDecimal yfjpkdj;
    private BigDecimal yfjpkhj;
    private String hkgs;
    private String jpdjType;
    private BigDecimal jpdj;
    private String alreadyPaidJpdj;
    private String jpPaidJpdjDate;

    // 地接
    private BigDecimal yfdjhj;

    // 保险
    private String bxServicId;
    private BigDecimal yfbxdj;
    private BigDecimal yfbxhj;

    private BigDecimal garanteePriceSum;
    private BigDecimal garanteeAlreadyGot;
    private BigDecimal garanteeNeedGot;
    private String garanteeGotBank;
    private Date garanteeGotDate;
    private BigDecimal garanteeAlreadyPaid;
    private String garanteePaidBank;
    private Date garanteePaidDate;

    private BigDecimal djPriceSum;
    private BigDecimal djAlreadyPaid;
    private BigDecimal djNeedPaid;
    private String djPaidBank;
    private Date djPaidDate;

    private BigDecimal jpPriceSum;
    private BigDecimal jpAlreadyPaid;
    private BigDecimal jpNeedPaid;
    private String jpPaidBank;
    private Date jpPaidDate;

    private BigDecimal qzPriceSum;
    private BigDecimal qzAlreadyPaid;
    private BigDecimal qzNeedPaid;
    private String qzPaidBank;
    private Date qzPaidDate;

    private BigDecimal qtPriceSum;
    private BigDecimal qtAlreadyPaid;
    private BigDecimal qtNeedPaid;
    private String qtPaidBank;
    private Date qtPaidDate;

    private BigDecimal bxPriceSum;
    private BigDecimal bxAlreadyPaid;
    private BigDecimal bxNeedPaid;
    private String bxPaidBank;
    private Date bxPaidDate;

    public List<LinesSrvice> getLineOrderService() {
        List<LinesSrvice> serviceList = new ArrayList<LinesSrvice>();
        if (ld != null && ld.startsWith("1")) {
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
            if (!"#".equals(items[4])) {
                linesSrvice.setServiceItem2(items[4]);
            }
            if (!"#".equals(items[4])) {
                linesSrvice.setServiceItem2(items[4]);
            }
            serviceList.add(linesSrvice);
        }
        if (qz != null && qz.startsWith("1")) {
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

            if (this.getServicePayPrice() != null) {
                linesSrvice.setServicePayPrice(this.getServicePayPrice());
            }
            if (!StringUtils.isEmpty(this.getSignDate())) {
                linesSrvice.setServiceItem2(this.getSignDate());
            }
            if (!StringUtils.isEmpty(this.getDatumIsready())) {
                linesSrvice.setServiceItem4(this.getDatumIsready());
            }
            if (!StringUtils.isEmpty(this.getDatumLimitDate())) {
                linesSrvice.setServiceItem3(this.getDatumLimitDate());
            }
            if (this.getQzNeedPaid() != null) {
                linesSrvice.setNeedPaid(this.getQzNeedPaid());
            }
            if (this.getQzAlreadyPaid() != null) {
                linesSrvice.setAlreadyPaid(this.getQzAlreadyPaid());
            }
            if (!StringUtils.isEmpty(this.getQzPaidBank())) {
                linesSrvice.setPaidBank(this.getQzPaidBank());
            }
            if (this.getQzPaidDate() != null) {
                linesSrvice.setPaidDate(this.getQzPaidDate());
            }
            if (this.getSumServicePayPrice() != null) {
                linesSrvice.setYfhj(this.getSumServicePayPrice());
            }
            serviceList.add(linesSrvice);
        }
        if (jp != null && jp.startsWith("1")) {
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
            if (this.getJpNeedPaid() != null) {
                linesSrvice.setNeedPaid(this.getJpNeedPaid());
            }
            if (this.getJpAlreadyPaid() != null) {
                linesSrvice.setAlreadyPaid(this.getJpAlreadyPaid());
            }
            if (!StringUtils.isEmpty(this.getJpPaidBank())) {
                linesSrvice.setPaidBank(this.getJpPaidBank());
            }
            if (this.getJpPaidDate() != null) {
                linesSrvice.setPaidDate(this.getJpPaidDate());
            }

            linesSrvice.setServiceItem2(yfjpkType);
            linesSrvice.setServiceItem3(hkgs);
            linesSrvice.setServiceItem4(jpdjType);
            linesSrvice.setServiceItem5(alreadyPaidJpdj);
            linesSrvice.setServiceItem6(jpPaidJpdjDate);
            linesSrvice.setServicePayPrice(yfjpkdj);
            linesSrvice.setServiceItem9(jpdj);
            linesSrvice.setYfhj(yfjpkhj);

            serviceList.add(linesSrvice);
        }
        if (dj != null && dj.startsWith("1")) {
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
            if (!"#".equals(items[14])) {
                linesSrvice.setServiceItem10(items[14]);
            }
            if (!"#".equals(items[15])) {
                linesSrvice.setServiceItem11(items[15]);
            }
            if (this.getDjNeedPaid() != null) {
                linesSrvice.setNeedPaid(this.getDjNeedPaid());
            }
            if (this.getDjAlreadyPaid() != null) {
                linesSrvice.setAlreadyPaid(this.getDjAlreadyPaid());
            }
            if (!StringUtils.isEmpty(this.getDjPaidBank())) {
                linesSrvice.setPaidBank(this.getDjPaidBank());
            }
            if (this.getDjPaidDate() != null) {
                linesSrvice.setPaidDate(this.getDjPaidDate());
            }

            linesSrvice.setYfhj(yfdjhj);
            serviceList.add(linesSrvice);
        }
        if (qt != null && qt.startsWith("1")) {
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
            if (this.getQtPriceSum() != null) {
                linesSrvice.setYfhj(this.getQtPriceSum());
            }
            if (this.getQtNeedPaid() != null) {
                linesSrvice.setNeedPaid(this.getQtNeedPaid());
            }
            if (this.getQtAlreadyPaid() != null) {
                linesSrvice.setAlreadyPaid(this.getQtAlreadyPaid());
            }
            if (!StringUtils.isEmpty(this.getQtPaidBank())) {
                linesSrvice.setPaidBank(this.getQtPaidBank());
            }
            if (this.getQtPaidDate() != null) {
                linesSrvice.setPaidDate(this.getQtPaidDate());
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
            // 归国保证金或者是联保
            if ("2".equals(getGaranteeType()) || "3".equals(getGaranteeType())) {
                if (this.getGaranteeAlreadyPaid() != null) {
                    linesSrvice.setAlreadyPaid(this.getGaranteeAlreadyPaid());
                }
                if (!StringUtils.isEmpty(this.getGaranteePaidBank())) {
                    linesSrvice.setPaidBank(this.getGaranteePaidBank());
                }
                if (this.getGaranteePaidDate() != null) {
                    linesSrvice.setPaidDate(this.getGaranteePaidDate());
                }
                if (this.getGaranteeAlreadyGot() != null) {
                    linesSrvice.setAlreadyGot(this.getGaranteeAlreadyGot());
                }
                if (this.getGaranteeNeedGot() != null) {
                    linesSrvice.setNeedGot(this.getGaranteeNeedGot());
                }
                if (!StringUtils.isEmpty(this.getGaranteeGotBank())) {
                    linesSrvice.setGotBank(this.getGaranteeGotBank());
                }
                if (this.getGaranteeGotDate() != null) {
                    linesSrvice.setGotDate(this.getGaranteeGotDate());
                }
            }
            serviceList.add(linesSrvice);
        }

        LinesSrvice bxSrvice = new LinesSrvice(orderId, LineSrviceEnumType.BX.getId());
        bxSrvice.setYfhj(yfbxhj);
        bxSrvice.setServicePayPrice(yfbxdj);
        if (this.getBxNeedPaid() != null) {
            bxSrvice.setNeedPaid(this.getBxNeedPaid());
        }
        if (this.getBxAlreadyPaid() != null) {
            bxSrvice.setAlreadyPaid(this.getBxAlreadyPaid());
        }
        if (!StringUtils.isEmpty(this.getBxPaidBank())) {
            bxSrvice.setPaidBank(this.getBxPaidBank());
        }
        if (this.getBxPaidDate() != null) {
            bxSrvice.setPaidDate(this.getBxPaidDate());
        }
        int bxId = 0;
        try {
            bxId = Integer.valueOf(bxServicId);
        } catch (Exception e) {
            bxId = 0;
        }
        bxSrvice.setServiceId(bxId);
        serviceList.add(bxSrvice);
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
                nameListPo.setRoom(Integer.valueOf(items[5].replace("#", "0")));
                nameListPo.setComment(items[6].replace("#", ""));
                if (!"#".equals(items[7])) {
                    nameListPo.setId(Integer.valueOf(items[7]));
                }
                customList.add(nameListPo);
            }
        }
        return customList;
    }

    public String getAlreadyPaidJpdj() {
        return alreadyPaidJpdj;
    }

    public void setAlreadyPaidJpdj(String alreadyPaidJpdj) {
        this.alreadyPaidJpdj = alreadyPaidJpdj;
    }

    public String getJpPaidJpdjDate() {
        return jpPaidJpdjDate;
    }

    public void setJpPaidJpdjDate(String jpPaidJpdjDate) {
        this.jpPaidJpdjDate = jpPaidJpdjDate;
    }

    public BigDecimal getJpdj() {
        return jpdj;
    }

    public void setJpdj(BigDecimal jpdj) {
        this.jpdj = jpdj;
    }

    public String getJpdjType() {
        return jpdjType;
    }

    public void setJpdjType(String jpdjType) {
        this.jpdjType = jpdjType;
    }

    public BigDecimal getSumServicePayPrice() {
        return sumServicePayPrice;
    }

    public void setSumServicePayPrice(BigDecimal sumServicePayPrice) {
        this.sumServicePayPrice = sumServicePayPrice;
    }

    public BigDecimal getGaranteeAlreadyPaid() {
        return garanteeAlreadyPaid;
    }

    public void setGaranteeAlreadyPaid(BigDecimal garanteeAlreadyPaid) {
        this.garanteeAlreadyPaid = garanteeAlreadyPaid;
    }

    public String getGaranteePaidBank() {
        return garanteePaidBank;
    }

    public void setGaranteePaidBank(String garanteePaidBank) {
        this.garanteePaidBank = garanteePaidBank;
    }

    public Date getGaranteePaidDate() {
        return garanteePaidDate;
    }

    public void setGaranteePaidDate(Date garanteePaidDate) {
        this.garanteePaidDate = garanteePaidDate;
    }

    public BigDecimal getQzPriceSum() {
        return qzPriceSum;
    }

    public void setQzPriceSum(BigDecimal qzPriceSum) {
        this.qzPriceSum = qzPriceSum;
    }

    public BigDecimal getQzAlreadyPaid() {
        return qzAlreadyPaid;
    }

    public void setQzAlreadyPaid(BigDecimal qzAlreadyPaid) {
        this.qzAlreadyPaid = qzAlreadyPaid;
    }

    public BigDecimal getQzNeedPaid() {
        return qzNeedPaid;
    }

    public void setQzNeedPaid(BigDecimal qzNeedPaid) {
        this.qzNeedPaid = qzNeedPaid;
    }

    public String getQzPaidBank() {
        return qzPaidBank;
    }

    public void setQzPaidBank(String qzPaidBank) {
        this.qzPaidBank = qzPaidBank;
    }

    public Date getQzPaidDate() {
        return qzPaidDate;
    }

    public void setQzPaidDate(Date qzPaidDate) {
        this.qzPaidDate = qzPaidDate;
    }

    public BigDecimal getQtPriceSum() {
        return qtPriceSum;
    }

    public void setQtPriceSum(BigDecimal qtPriceSum) {
        this.qtPriceSum = qtPriceSum;
    }

    public BigDecimal getQtAlreadyPaid() {
        return qtAlreadyPaid;
    }

    public void setQtAlreadyPaid(BigDecimal qtAlreadyPaid) {
        this.qtAlreadyPaid = qtAlreadyPaid;
    }

    public BigDecimal getQtNeedPaid() {
        return qtNeedPaid;
    }

    public void setQtNeedPaid(BigDecimal qtNeedPaid) {
        this.qtNeedPaid = qtNeedPaid;
    }

    public String getQtPaidBank() {
        return qtPaidBank;
    }

    public void setQtPaidBank(String qtPaidBank) {
        this.qtPaidBank = qtPaidBank;
    }

    public Date getQtPaidDate() {
        return qtPaidDate;
    }

    public void setQtPaidDate(Date qtPaidDate) {
        this.qtPaidDate = qtPaidDate;
    }

    public BigDecimal getBxPriceSum() {
        return bxPriceSum;
    }

    public void setBxPriceSum(BigDecimal bxPriceSum) {
        this.bxPriceSum = bxPriceSum;
    }

    public BigDecimal getBxAlreadyPaid() {
        return bxAlreadyPaid;
    }

    public void setBxAlreadyPaid(BigDecimal bxAlreadyPaid) {
        this.bxAlreadyPaid = bxAlreadyPaid;
    }

    public BigDecimal getBxNeedPaid() {
        return bxNeedPaid;
    }

    public void setBxNeedPaid(BigDecimal bxNeedPaid) {
        this.bxNeedPaid = bxNeedPaid;
    }

    public String getBxPaidBank() {
        return bxPaidBank;
    }

    public void setBxPaidBank(String bxPaidBank) {
        this.bxPaidBank = bxPaidBank;
    }

    public Date getBxPaidDate() {
        return bxPaidDate;
    }

    public void setBxPaidDate(Date bxPaidDate) {
        this.bxPaidDate = bxPaidDate;
    }

    public BigDecimal getDjAlreadyPaid() {
        return djAlreadyPaid;
    }

    public void setDjAlreadyPaid(BigDecimal djAlreadyPaid) {
        this.djAlreadyPaid = djAlreadyPaid;
    }

    public BigDecimal getDjNeedPaid() {
        return djNeedPaid;
    }

    public void setDjNeedPaid(BigDecimal djNeedPaid) {
        this.djNeedPaid = djNeedPaid;
    }

    public String getDjPaidBank() {
        return djPaidBank;
    }

    public void setDjPaidBank(String djPaidBank) {
        this.djPaidBank = djPaidBank;
    }

    public Date getDjPaidDate() {
        return djPaidDate;
    }

    public void setDjPaidDate(Date djPaidDate) {
        this.djPaidDate = djPaidDate;
    }

    public BigDecimal getJpPriceSum() {
        return jpPriceSum;
    }

    public void setJpPriceSum(BigDecimal jpPriceSum) {
        this.jpPriceSum = jpPriceSum;
    }

    public BigDecimal getJpAlreadyPaid() {
        return jpAlreadyPaid;
    }

    public void setJpAlreadyPaid(BigDecimal jpAlreadyPaid) {
        this.jpAlreadyPaid = jpAlreadyPaid;
    }

    public BigDecimal getJpNeedPaid() {
        return jpNeedPaid;
    }

    public void setJpNeedPaid(BigDecimal jpNeedPaid) {
        this.jpNeedPaid = jpNeedPaid;
    }

    public String getJpPaidBank() {
        return jpPaidBank;
    }

    public void setJpPaidBank(String jpPaidBank) {
        this.jpPaidBank = jpPaidBank;
    }

    public Date getJpPaidDate() {
        return jpPaidDate;
    }

    public void setJpPaidDate(Date jpPaidDate) {
        this.jpPaidDate = jpPaidDate;
    }

    public BigDecimal getDjPriceSum() {
        return djPriceSum;
    }

    public void setDjPriceSum(BigDecimal djPriceSum) {
        this.djPriceSum = djPriceSum;
    }

    public BigDecimal getGaranteePriceSum() {
        return garanteePriceSum;
    }

    public void setGaranteePriceSum(BigDecimal garanteePriceSum) {
        this.garanteePriceSum = garanteePriceSum;
    }

    public BigDecimal getGaranteeAlreadyGot() {
        return garanteeAlreadyGot;
    }

    public void setGaranteeAlreadyGot(BigDecimal garanteeAlreadyGot) {
        this.garanteeAlreadyGot = garanteeAlreadyGot;
    }

    public BigDecimal getGaranteeNeedGot() {
        return garanteeNeedGot;
    }

    public void setGaranteeNeedGot(BigDecimal garanteeNeedGot) {
        this.garanteeNeedGot = garanteeNeedGot;
    }

    public String getGaranteeGotBank() {
        return garanteeGotBank;
    }

    public void setGaranteeGotBank(String garanteeGotBank) {
        this.garanteeGotBank = garanteeGotBank;
    }

    public Date getGaranteeGotDate() {
        return garanteeGotDate;
    }

    public void setGaranteeGotDate(Date garanteeGotDate) {
        this.garanteeGotDate = garanteeGotDate;
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

    public String getYfjpkType() {
        return yfjpkType;
    }

    public void setYfjpkType(String yfjpkType) {
        this.yfjpkType = yfjpkType;
    }

    public BigDecimal getYfjpkdj() {
        return yfjpkdj;
    }

    public void setYfjpkdj(BigDecimal yfjpkdj) {
        this.yfjpkdj = yfjpkdj;
    }

    public BigDecimal getYfjpkhj() {
        return yfjpkhj;
    }

    public void setYfjpkhj(BigDecimal yfjpkhj) {
        this.yfjpkhj = yfjpkhj;
    }

    public String getHkgs() {
        return hkgs;
    }

    public void setHkgs(String hkgs) {
        this.hkgs = hkgs;
    }

    public BigDecimal getYfdjhj() {
        return yfdjhj;
    }

    public void setYfdjhj(BigDecimal yfdjhj) {
        this.yfdjhj = yfdjhj;
    }

    public String getBxServicId() {
        return bxServicId;
    }

    public void setBxServicId(String bxServicId) {
        this.bxServicId = bxServicId;
    }

    public BigDecimal getYfbxdj() {
        return yfbxdj;
    }

    public void setYfbxdj(BigDecimal yfbxdj) {
        this.yfbxdj = yfbxdj;
    }

    public BigDecimal getYfbxhj() {
        return yfbxhj;
    }

    public void setYfbxhj(BigDecimal yfbxhj) {
        this.yfbxhj = yfbxhj;
    }

}
