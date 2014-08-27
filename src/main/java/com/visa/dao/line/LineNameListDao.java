package com.visa.dao.line;

import java.util.List;

import com.visa.po.line.LineNameList;

public interface LineNameListDao {

    int deleteByPrimaryKey(Integer id);
    
    int deleteByOrderId(Integer lineOrderId);

    int insert(LineNameList record);

    List<LineNameList> selectAllLineNameList(Integer lineOrderId);

    LineNameList selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(LineNameList record);

}
