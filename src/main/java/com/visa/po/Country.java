package com.visa.po;

import java.util.Date;

public class Country {
    private Integer countryId;

    private String countryName;

    private Integer continentId;

    private Date postDt;

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getContinentId() {
        return continentId;
    }

    public void setContinentId(Integer continentId) {
        this.continentId = continentId;
    }

    public Date getPostDt() {
        return postDt;
    }

    public void setPostDt(Date postDt) {
        this.postDt = postDt;
    }

}
