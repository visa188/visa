package com.visa.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author bjyrwang
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

}
