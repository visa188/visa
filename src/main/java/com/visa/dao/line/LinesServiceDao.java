package com.visa.dao.line;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.visa.po.line.LinesSrvice;

public interface LinesServiceDao {

    int deleteByPrimaryKey(@Param("tableName") String tableName, @Param("serviceId") Integer serviceId);

    int deleteByOrderId(@Param("tableName") String tableName, @Param("orderId") Integer orderId, @Param("serviceTypeList") List<Integer> serviceType);

    int insert(@Param("tableName") String tableName, @Param("record") LinesSrvice record);

    List<LinesSrvice> selectAllLinesSrvice(@Param("tableName") String tableName, @Param("orderId") Integer orderId);

    LinesSrvice selectByPrimaryKey(@Param("tableName") String tableName, @Param("serviceId") Integer serviceId);

    int updateByPrimaryKey(@Param("tableName") String tableName, @Param("record") LinesSrvice record);

}
