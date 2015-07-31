package com.visa.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author
 */
public class Orders {
    private Integer orderId;

    private Date orderDate;

    private Integer customerId;

    private String customerName;

    private String customerCompany;

    private Integer productId;

    private String productName;

    private String nameList;

    private int nameListSize;

    private int status;

    private String salesmanId;

    private String salesmanName;

    private String operatorId;

    private String operatorName;

    private Date signDate;

    private String signOperatorName;

    private BigDecimal priceYsdj;

    private BigDecimal priceBxys;

    private BigDecimal priceBxyf;

    private BigDecimal priceQtys;

    private BigDecimal priceQtzc;

    private String priceQtysBz;

    private String priceQtzcBz;

    private BigDecimal priceZjys;

    private BigDecimal priceZjyf;

    private BigDecimal priceYfhk;

    private BigDecimal grossProfit;

    private int yfhkStatus;

    private String des;

    private String czdes;

    private String cwdes;

    private String operatorDes;

    private Date ptTime;

    private String countryName;

    private int yshkStatus;

    private BigDecimal priceYshk;

    private String orderSeq;

    private String operatorRemark;

    private String yshkRemark;

    private String yfhkRemark;

    private int type = 0;

    private String singleProduct;

    private String spStatus;

    private Date approvedTime_;

    private String fapiao;

    private String baoxian;

    private Integer baoxianDay;

    private Double baoxianPrice;

    private Double renzheng;

    private Double kuaidi;

    private Double tax;

    private Double fankuan;

    private Double qita;

    private String qitaComments;

    private String renzhengRemark;

    private String infostatus;

    private String finaceman;

    private String substatus;

    private String fkstatusdes;

    private String skstatusdes;

    public String getFkstatusdes() {
        return fkstatusdes;
    }

    public void setFkstatusdes(String fkstatusdes) {
        this.fkstatusdes = fkstatusdes;
    }

    public String getSkstatusdes() {
        return skstatusdes;
    }

    public void setSkstatusdes(String skstatusdes) {
        this.skstatusdes = skstatusdes;
    }

    public String getSubstatus() {
        return substatus;
    }

    public void setSubstatus(String substatus) {
        this.substatus = substatus;
    }

    public String getFinaceman() {
        return finaceman;
    }

    public void setFinaceman(String finaceman) {
        this.finaceman = finaceman;
    }

    public String getInfostatus() {
        return infostatus;
    }

    public void setInfostatus(String infostatus) {
        this.infostatus = infostatus;
    }

    public String getRenzhengRemark() {
        return renzhengRemark;
    }

    public void setRenzhengRemark(String renzhengRemark) {
        this.renzhengRemark = renzhengRemark;
    }

    public String getQitaComments() {
        return qitaComments;
    }

    public void setQitaComments(String qitaComments) {
        this.qitaComments = qitaComments;
    }

    public Double getBaoxianPrice() {
        return baoxianPrice;
    }

    public void setBaoxianPrice(Double baoxianPrice) {
        this.baoxianPrice = baoxianPrice;
    }

    public Double getRenzheng() {
        return renzheng;
    }

    public void setRenzheng(Double renzheng) {
        this.renzheng = renzheng;
    }

    public Double getKuaidi() {
        return kuaidi;
    }

    public void setKuaidi(Double kuaidi) {
        this.kuaidi = kuaidi;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getFankuan() {
        return fankuan;
    }

    public void setFankuan(Double fankuan) {
        this.fankuan = fankuan;
    }

    public Double getQita() {
        return qita;
    }

    public void setQita(Double qita) {
        this.qita = qita;
    }

    public Integer getBaoxianDay() {
        return baoxianDay;
    }

    public void setBaoxianDay(Integer baoxianDay) {
        this.baoxianDay = baoxianDay;
    }

    public String getBaoxian() {
        return baoxian;
    }

    public void setBaoxian(String baoxian) {
        this.baoxian = baoxian;
    }

    public String getFapiao() {
        return fapiao;
    }

    public void setFapiao(String fapiao) {
        this.fapiao = fapiao;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSingleProduct() {
        return singleProduct;
    }

    public void setSingleProduct(String singleProduct) {
        this.singleProduct = singleProduct;
    }

    public String getYshkRemark() {
        return yshkRemark;
    }

    public void setYshkRemark(String yshkRemark) {
        this.yshkRemark = yshkRemark;
    }

    public String getYfhkRemark() {
        return yfhkRemark;
    }

    public void setYfhkRemark(String yfhkRemark) {
        this.yfhkRemark = yfhkRemark;
    }

    public String getOperatorRemark() {
        return operatorRemark;
    }

    public void setOperatorRemark(String operatorRemark) {
        this.operatorRemark = operatorRemark;
    }

    public String getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
    }

    public String getCustomerCompany() {
        return customerCompany;
    }

    public void setCustomerCompany(String customerCompany) {
        this.customerCompany = customerCompany;
    }

    public int getYshkStatus() {
        return yshkStatus;
    }

    public void setYshkStatus(int yshkStatus) {
        this.yshkStatus = yshkStatus;
    }

    public BigDecimal getPriceYshk() {
        return priceYshk;
    }

    public void setPriceYshk(BigDecimal priceYshk) {
        this.priceYshk = priceYshk;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getNameListSize() {
        return nameListSize;
    }

    public void setNameListSize(int nameListSize) {
        this.nameListSize = nameListSize;
    }

    public Date getPtTime() {
        return ptTime;
    }

    public void setPtTime(Date ptTime) {
        this.ptTime = ptTime;
    }

    public String getOperatorDes() {
        return operatorDes;
    }

    public void setOperatorDes(String operatorDes) {
        this.operatorDes = operatorDes;
    }

    public BigDecimal getPriceYfhk() {
        return priceYfhk;
    }

    public void setPriceYfhk(BigDecimal priceYfhk) {
        this.priceYfhk = priceYfhk;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getNameList() {
        return nameList;
    }

    public void setNameList(String nameList) {
        this.nameList = nameList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(String salesmanId) {
        this.salesmanId = salesmanId;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public String getSignOperatorName() {
        return signOperatorName;
    }

    public void setSignOperatorName(String signOperatorName) {
        this.signOperatorName = signOperatorName;
    }

    public BigDecimal getPriceYsdj() {
        return priceYsdj;
    }

    public void setPriceYsdj(BigDecimal priceYsdj) {
        this.priceYsdj = priceYsdj;
    }

    public BigDecimal getPriceQtys() {
        return priceQtys;
    }

    public void setPriceQtys(BigDecimal priceQtys) {
        this.priceQtys = priceQtys;
    }

    public BigDecimal getPriceQtzc() {
        return priceQtzc;
    }

    public void setPriceQtzc(BigDecimal priceQtzc) {
        this.priceQtzc = priceQtzc;
    }

    public BigDecimal getPriceZjys() {
        return priceZjys;
    }

    public void setPriceZjys(BigDecimal priceZjys) {
        this.priceZjys = priceZjys;
    }

    public BigDecimal getPriceZjyf() {
        return priceZjyf;
    }

    public void setPriceZjyf(BigDecimal priceZjyf) {
        this.priceZjyf = priceZjyf;
    }

    public BigDecimal getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(BigDecimal grossProfit) {
        this.grossProfit = grossProfit;
    }

    public int getYfhkStatus() {
        return yfhkStatus;
    }

    public void setYfhkStatus(int yfhkStatus) {
        this.yfhkStatus = yfhkStatus;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public BigDecimal getPriceBxys() {
        return priceBxys;
    }

    public void setPriceBxys(BigDecimal priceBxys) {
        this.priceBxys = priceBxys;
    }

    public BigDecimal getPriceBxyf() {
        return priceBxyf;
    }

    public void setPriceBxyf(BigDecimal priceBxyf) {
        this.priceBxyf = priceBxyf;
    }

    public String getPriceQtysBz() {
        return priceQtysBz;
    }

    public void setPriceQtysBz(String priceQtysBz) {
        this.priceQtysBz = priceQtysBz;
    }

    public String getPriceQtzcBz() {
        return priceQtzcBz;
    }

    public void setPriceQtzcBz(String priceQtzcBz) {
        this.priceQtzcBz = priceQtzcBz;
    }

    public String getCzdes() {
        return czdes;
    }

    public void setCzdes(String czdes) {
        this.czdes = czdes;
    }

    public String getSpStatus() {
        return spStatus;
    }

    public void setSpStatus(String spStatus) {
        this.spStatus = spStatus;
    }

    public Date getApprovedTime_() {
        return approvedTime_;
    }

    public void setApprovedTime_(Date approvedTime_) {
        this.approvedTime_ = approvedTime_;
    }

    public String getCwdes() {
        return cwdes;
    }

    public void setCwdes(String cwdes) {
        this.cwdes = cwdes;
    }

}
