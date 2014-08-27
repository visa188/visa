package com.visa.common.util;

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
}
