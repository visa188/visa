package com.visa.vo.line;

import com.visa.po.line.LineProduct;

public class LineProductVo extends LineProduct {

    private String airLineName;
    private String lineCountryName;

    public String getAirLineName() {
        return airLineName;
    }

    public void setAirLineName(String airLineName) {
        this.airLineName = airLineName;
    }

    public String getLineCountryName() {
        return lineCountryName;
    }

    public void setLineCountryName(String lineCountryName) {
        this.lineCountryName = lineCountryName;
    }

}
