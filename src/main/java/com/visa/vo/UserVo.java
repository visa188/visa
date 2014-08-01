package com.visa.vo;

import com.visa.po.User;

public class UserVo extends User {

    private String managerName;

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

}
