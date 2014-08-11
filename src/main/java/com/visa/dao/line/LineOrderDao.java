package com.visa.dao.line;

import java.util.List;

import com.visa.po.line.LineOrder;

public interface LineOrderDao {

    int deleteByPrimaryKey(Integer orderId);

    int insert(LineOrder record);

    List<LineOrder> selectAllLineOrder();

    LineOrder selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKey(LineOrder record);

}
