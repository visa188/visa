package com.visa.dao.line;

import java.util.List;
import java.util.Map;

import com.visa.po.line.LineProduct;
import com.visa.vo.line.LineProductVo;

public interface LineProductDao {

    int deleteByPrimaryKey(Integer lineProductId);

    int insert(LineProduct record);

    List<LineProduct> selectAllLineProduct();

    LineProduct selectByPrimaryKey(Integer lineProductId);

    int updateByPrimaryKey(LineProduct record);

    Integer selectAllCount(Map<String, Object> paraMap);

    List<LineProductVo> selectByPage(Map<String, Object> paraMap);
}
