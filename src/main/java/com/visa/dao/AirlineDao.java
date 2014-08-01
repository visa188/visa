package com.visa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.visa.po.Airline;

public interface AirlineDao {

    int deleteByPrimaryKey(Integer airlineId);

    int insert(Airline record);

    Integer selectAllCount(Airline customer);

    List<Airline> selectAll(@Param("start") Integer start, @Param("pageCount") Integer pageCount,
            @Param("airline") Airline airline);

    List<Airline> selectAllAirline();

    Airline selectByPrimaryKey(Integer airlineId);

    int updateByPrimaryKey(Airline record);

    List<Airline> selectByContinentId(Integer continentId);

}
