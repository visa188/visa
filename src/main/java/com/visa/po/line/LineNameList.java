package com.visa.po.line;

import java.util.Date;

/***********************************************************************
 * Module:  LineNameList.java
 * Author:  bjyrwang
 * Purpose: Defines the Class LineNameList
 ***********************************************************************/

/** @pdOid 0ff2f7d2-6a06-415c-a065-47f150c728aa */
public class LineNameList {
    /**
     * 主键
     * 
     * @pdOid 7a6408d4-54d9-4445-8eb7-d7d3802c268b
     */
    public int id;

    public int lineOrderId;
    /**
     * 客人名字
     * 
     * @pdOid dcbd5c10-9fbc-4c61-bb5e-190a264adfed
     */
    public String name;
    /**
     * 性别1男2女
     * 
     * @pdOid 807b792f-52a5-4cf4-8dd0-2dfa1eac864a
     */
    public int sex;
    /**
     * 1成人2儿童(占床)3儿童(不占床)
     * 
     * @pdOid c2c84452-f6a9-40b3-a3cc-1cb4ace53654
     */
    public int ageType;
    /**
     * 押金
     * 
     * @pdOid 1ff3a2d5-04e5-44a2-af4f-c97b2fcb6938
     */
    public String deposit;
    /**
     * 资料
     * 
     * @pdOid 365b987b-6f46-4835-810c-852ff83ddb44
     */
    public String datum;
    /**
     * 房间数
     * 
     * @pdOid f0441c6b-abed-4121-9d72-911b0d683eb0
     */
    public int room;
    /**
     * 备注（说明哪些资料不全之类）
     * 
     * @pdOid d5e46e30-262a-4d70-b28e-c55e123f04f5
     */
    public String comment;
    /** @pdOid 1ed4bc3e-9d97-4bd8-9d5d-07a80b3c55b7 */
    public Date postTime;

    public int getLineOrderId() {
        return lineOrderId;
    }

    public void setLineOrderId(int lineOrderId) {
        this.lineOrderId = lineOrderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAgeType() {
        return ageType;
    }

    public void setAgeType(int ageType) {
        this.ageType = ageType;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

}
