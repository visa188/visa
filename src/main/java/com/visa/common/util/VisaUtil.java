package com.visa.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.visa.po.line.LineNameList;
import com.visa.po.line.LinesSrvice;

/**
 * Bean Util
 * 
 * @author haosun
 */
public final class VisaUtil {

    private VisaUtil() {
    }

    public static Map<Integer, LinesSrvice> dealServiceList(List<LinesSrvice> serviceList) {
        Map<Integer, LinesSrvice> map = new HashMap<Integer, LinesSrvice>();
        for (LinesSrvice service : serviceList) {
            map.put(service.getServiceId(), service);
        }
        return map;
    }

    public static Map<Integer, LineNameList> dealNameList(List<LineNameList> nameList) {
        Map<Integer, LineNameList> map = new HashMap<Integer, LineNameList>();
        for (LineNameList service : nameList) {
            map.put(service.getId(), service);
        }
        return map;
    }

    List<Integer> flow = new ArrayList<Integer>();

    /**
     * @param currentFlow
     * @param isParallel 0 只有操作 1 只有采购 2 操作主采购次 采购主操作次
     * @return
     */
    public static int nextFlow(int currentFlow, int isParallel, String deptId) {
        switch (currentFlow) {
        case 0:
            return 71;
        case 71: {
            switch (isParallel) {
            case 0:
                return 81;
            case 1:
                return -81;
            case 2:
                return -1;
            default:
                break;
            }
        }
        case 81:
            return 8;
        case 8:
            return 101;
        case 101:
            return 10;
        case 10:
            return 9;
        case 9:
            return 91;
        case -1: {
            if ("北京操作部".equals(deptId)) {
                return -11;
            } else if ("北京采购部".equals(deptId)) {
                return -111;
            }
        }
        case -11:
            if ("北京采购部".equals(deptId)) {
                return 101;
            }
        case -111:
            if ("北京操作部".equals(deptId)) {
                return 101;
            }
        default:
            return 71;
        }

    }
}
