package com.visa.dao.line;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.visa.po.line.FeeDetail;

public interface FeeDetailDao {

    int deleteByPrimaryKey(Integer id);

    int insert(FeeDetail feeDetail);

    Integer selectAllCount(FeeDetail feeDetail);

    List<FeeDetail> selectAll(@Param("start") Integer start, @Param("pageCount") Integer pageCount,
            @Param("feeDetail") FeeDetail feeDetail);

    List<FeeDetail> selectAllFeeDetail();

    FeeDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(FeeDetail feeDetail);

}
