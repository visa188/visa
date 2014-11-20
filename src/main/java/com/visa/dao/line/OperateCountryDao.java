package com.visa.dao.line;

import java.util.List;

import com.visa.po.line.OperateLog;
import com.visa.po.line.OperatorCountry;

public interface OperateCountryDao {

    int deleteByPrimaryKey(Integer id);

    int insert(OperatorCountry record);

    List<OperatorCountry> selectAllOperatorCountry();

    OperateLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(OperatorCountry record);

    int deleteByUserId(String userId);

}
