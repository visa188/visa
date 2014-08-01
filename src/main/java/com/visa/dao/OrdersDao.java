package com.visa.dao;

import java.util.List;
import java.util.Map;

import com.visa.po.Orders;

public interface OrdersDao {

    int deleteByPrimaryKey(Integer orderId);

    int insert(Orders record);

    List<Orders> selectByExample(Orders example);

    Orders selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKey(Orders record);

    int count(Map<String, Object> paraMap);

    List<Orders> selectByPage(Map<String, Object> paraMap);

    List<Orders> selectAll(Map<String, Object> paraMap);

    List<String> selectOrderYears();

    List<String> selectOrderMonths();

    int selectByProductIdCount(Integer productId);

    int selectByCustomerIdCount(Integer customerId);

    int selectByUserIdCount(String userId);
    
    Map<String, Object> sumPrice(Map<String, Object> paraMap);
    
}
