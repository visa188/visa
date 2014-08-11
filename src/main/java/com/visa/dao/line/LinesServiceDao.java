package com.visa.dao.line;

import java.util.List;

import com.visa.po.line.LinesSrvice;

public interface LinesServiceDao {

    int deleteByPrimaryKey(Integer serviceId);

    int insert(LinesSrvice record);

    List<LinesSrvice> selectAllLinesSrvice();

    LinesSrvice selectByPrimaryKey(Integer serviceId);

    int updateByPrimaryKey(LinesSrvice record);

}
