package com.visa.po;

import java.util.Date;

public class Airline {
    private Integer airlineId;

    private String airlineName;

    private Date postDt;

    public Integer getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(Integer airlineId) {
        this.airlineId = airlineId;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public Date getPostDt() {
        return postDt;
    }

    public void setPostDt(Date postDt) {
        this.postDt = postDt;
    }

}
