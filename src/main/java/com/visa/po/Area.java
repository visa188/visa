package com.visa.po;

import java.util.Date;

public class Area {
    private Integer areaId;

    private String areaName;

    private Date postDt;

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Date getPostDt() {
        return postDt;
    }

    public void setPostDt(Date postDt) {
        this.postDt = postDt;
    }

}
