package com.visa.dao.line;

import java.util.List;
import java.util.Map;

import com.visa.po.line.LineProduct;

public interface LineProductDao {

    int deleteByPrimaryKey(Integer lineProductId);

    int insert(LineProduct record);

    List<LineProduct> selectAllLineProduct();

    LineProduct selectByPrimaryKey(Integer lineProductId);

    int updateByPrimaryKey(LineProduct record);

    Integer selectAllCount(LineProduct product);

    List<LineProduct> selectByPage(Map<String, Object> paraMap);
}
