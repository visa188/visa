package com.visa.dao.line;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.visa.po.line.LinesSrvice;

public interface LinesServiceDao {

    int deleteByPrimaryKey(Integer serviceId);

    int deleteByOrderId(@Param("orderId") Integer orderId, @Param("serviceTypeList") List<Integer> serviceType);

    int insert(LinesSrvice record);

    List<LinesSrvice> selectAllLinesSrvice(Integer orderId);

    LinesSrvice selectByPrimaryKey(Integer serviceId);

    int updateByPrimaryKey(LinesSrvice record);

}
