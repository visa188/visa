package com.visa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.visa.po.Country;

public interface LineCountryDao {

    int deleteByPrimaryKey(Integer countryId);

    int insert(Country record);

    Integer selectAllCount(Country customer);

    List<Country> selectAll(@Param("start") Integer start, @Param("pageCount") Integer pageCount,
            @Param("country") Country country);

    List<Country> selectAllCountry();

    Country selectByPrimaryKey(Integer countryId);

    int updateByPrimaryKey(Country record);

    List<Country> selectByContinentId(Integer continentId);

}
