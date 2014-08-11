package com.visa.dao.line;

import java.util.List;

import com.visa.po.line.LineProduct;

public interface LineProductDao {

    int deleteByPrimaryKey(Integer lineProductId);

    int insert(LineProduct record);

    List<LineProduct> selectAllLineProduct();

    LineProduct selectByPrimaryKey(Integer lineProductId);

    int updateByPrimaryKey(LineProduct record);

}
