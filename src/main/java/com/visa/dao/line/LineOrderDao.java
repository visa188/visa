package com.visa.dao.line;

import java.util.List;
import java.util.Map;

import com.visa.po.line.LineOrder;
import com.visa.vo.line.LineOrderVo;

public interface LineOrderDao {

    int deleteByPrimaryKey(Integer orderId);

    int insert(LineOrderVo record);

    List<LineOrder> selectAllLineOrder(Map<String, Object> paraMap);

    LineOrder selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKey(LineOrderVo record);

    int count(Map<String, Object> paraMap);

    List<LineOrder> selectByPage(Map<String, Object> paraMap);

    int selectByProductIdCount(int lineProductId);

    List<LineOrder> selectByProductId(int lineProductId);

    List<String> selectOrderYears();

    List<String> selectOrderMonths();
}
