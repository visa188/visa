package com.visa.po.line;

/***********************************************************************
 * Module:  OperatorCountry.java
 * Author:  bjyrwang
 * Purpose: Defines the Class OperatorCountry
 ***********************************************************************/


/** @pdOid cb8bdd2d-6855-4aaf-b47b-697994fc9169 */
public class OperatorCountry {
    /**
     * 主键
     * 
     * @pdOid b0d2e86a-3bdc-428d-9aee-df65b2074942
     */
    public int id;
    /**
     * 操作员id
     * 
     * @pdOid 374d76c8-1f70-4952-8ade-5ff2e405379b
     */
    public java.lang.String userid;
    /**
     * 线路国家id
     * 
     * @pdOid 0e214cdc-20a3-438a-81af-5001c15e6d73
     */
    public int lineCountryId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public java.lang.String getUserid() {
        return userid;
    }

    public void setUserid(java.lang.String userid) {
        this.userid = userid;
    }

    public int getLineCountryId() {
        return lineCountryId;
    }

    public void setLineCountryId(int lineCountryId) {
        this.lineCountryId = lineCountryId;
    }

}
