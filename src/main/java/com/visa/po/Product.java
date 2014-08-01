package com.visa.po;

import java.math.BigDecimal;
import java.util.Date;

public class Product {
	private Integer productId;

	private String productName;

	private String productType;

	private String districtPlace;

	private BigDecimal price;

	private String des;

	private Date postDt;

	private Integer areaId;

	private Integer continentId;

	private Integer countryId;

	private String countryName;

	private String relatePeople;

	private String relateTel;

	private String relateQq;

	private String relateAddr;

	private String relateCompany;

	public String getRelatePeople() {
		return relatePeople;
	}

	public void setRelatePeople(String relatePeople) {
		this.relatePeople = relatePeople;
	}

	public String getRelateTel() {
		return relateTel;
	}

	public void setRelateTel(String relateTel) {
		this.relateTel = relateTel;
	}

	public String getRelateQq() {
		return relateQq;
	}

	public void setRelateQq(String relateQq) {
		this.relateQq = relateQq;
	}

	public String getRelateAddr() {
		return relateAddr;
	}

	public void setRelateAddr(String relateAddr) {
		this.relateAddr = relateAddr;
	}

	public String getRelateCompany() {
		return relateCompany;
	}

	public void setRelateCompany(String relateCompany) {
		this.relateCompany = relateCompany;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
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

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getDistrictPlace() {
		return districtPlace;
	}

	public void setDistrictPlace(String districtPlace) {
		this.districtPlace = districtPlace;
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

	public Date getPostDt() {
		return postDt;
	}

	public void setPostDt(Date postDt) {
		this.postDt = postDt;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getContinentId() {
		return continentId;
	}

	public void setContinentId(Integer continentId) {
		this.continentId = continentId;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

}
