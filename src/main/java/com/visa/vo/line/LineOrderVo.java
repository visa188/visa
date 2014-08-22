package com.visa.vo.line;

import java.util.ArrayList;
import java.util.List;

import com.visa.po.line.LineOrder;
import com.visa.po.line.LinesSrvice;

public class LineOrderVo extends LineOrder {

    private String ld;
    private String qz;
    private String jp;
    private String dj;

    public List<LinesSrvice> getLineOrderService() {
        List<LinesSrvice> serviceList = new ArrayList<LinesSrvice>();
        if (ld.startsWith("1")) {
            LinesSrvice linesSrvice = new LinesSrvice(orderId, 1);
            // 组团社派 1/ 起航假期派 2
            linesSrvice.setServiceItem1(ld.split("_")[1]);
            serviceList.add(linesSrvice);
        }
        if (qz.startsWith("1")) {
            LinesSrvice linesSrvice = new LinesSrvice(orderId, 2);
            // 个签 1/ 团签 2
            linesSrvice.setServiceItem1(qz.split("_")[1]);
            serviceList.add(linesSrvice);
        }
        if (jp.startsWith("1")) {
            LinesSrvice linesSrvice = new LinesSrvice(orderId, 3);
            // 团队机票 1/ 散客机票 2
            linesSrvice.setServiceItem1(ld.split("_")[1]);
            serviceList.add(linesSrvice);
        }
        if (dj.startsWith("1")) {
            String[] items = dj.split("_");
            // 41 地接 巴士 / 42 地接司机兼导游
            int serviceType = 41;
            if ("b".equals(items[5])) {
                serviceType = 42;
            }
            LinesSrvice linesSrvice = new LinesSrvice(orderId, serviceType);
            if (!"#".equals(items[1])) {
                linesSrvice.setServiceItem1(items[1]); // 酒店星级
            }
            if (!"#".equals(items[2])) {
                linesSrvice.setServiceItem2(items[2]); // 房型
            }
            if (!"#".equals(items[3])) {
                linesSrvice.setServiceItem3(items[3]); // 备注
            }
            if (!"#".equals(items[4])) {
                linesSrvice.setServiceItem4(items[4]); // 特殊景点
            }
            if (!"#".equals(items[6])) {
                linesSrvice.setServiceItem5(items[6]); // 41 巴士 / 42 备注
            }
            if (!"#".equals(items[7])) {
                linesSrvice.setServiceItem6(items[7]); // 41 的备注
            }
            if (!"#".equals(items[8])) {
                linesSrvice.setServiceItem7(items[8]); // 41 的导游
            }
            if (!"#".equals(items[9])) {
                linesSrvice.setServiceItem8(items[9]); // 41 的导游的备注
            }
            serviceList.add(linesSrvice);
        }
        return serviceList;
    }

    public String getLd() {
        return ld;
    }

    public void setLd(String ld) {
        this.ld = ld;
    }

    public String getQz() {
        return qz;
    }

    public void setQz(String qz) {
        this.qz = qz;
    }

    public String getJp() {
        return jp;
    }

    public void setJp(String jp) {
        this.jp = jp;
    }

    public String getDj() {
        return dj;
    }

    public void setDj(String dj) {
        this.dj = dj;
    }

}
