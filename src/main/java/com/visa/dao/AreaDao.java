package com.visa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.visa.po.Area;

public interface AreaDao {

    int deleteByPrimaryKey(Integer areaId);

    int insert(Area record);

    Integer selectAllCount(Area customer);

    List<Area> selectAll(@Param("start") Integer start, @Param("pageCount") Integer pageCount,
            @Param("area") Area area);

    List<Area> selectAllArea();

    Area selectByPrimaryKey(Integer areaId);

    int updateByPrimaryKey(Area record);

}
