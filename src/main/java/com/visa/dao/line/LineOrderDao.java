package com.visa.dao.line;

import java.util.List;
import java.util.Map;

import com.visa.po.Orders;
import com.visa.po.line.LineOrder;

public interface LineOrderDao {

    int deleteByPrimaryKey(Integer orderId);

    int insert(LineOrder record);

    List<LineOrder> selectAllLineOrder();

    LineOrder selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKey(LineOrder record);

    int count(Map<String, Object> paraMap);

    List<Orders> selectByPage(Map<String, Object> paraMap);
}
