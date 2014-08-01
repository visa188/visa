package com.visa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.visa.po.Product;
import com.visa.vo.ProductVo;

public interface ProductDao {

    int deleteByPrimaryKey(Integer productId);

    int insert(Product record);

    Integer selectAllCount(Product product);

    List<ProductVo> selectAll(@Param("start") Integer start, @Param("pageCount") Integer pageCount,
            @Param("product") Product product);

    List<Product> selectAllProduct();

    Product selectByPrimaryKey(Integer productId);

    int updateByPrimaryKey(Product record);

    List<Product> searchByCountryId(@Param("countryId") int countryId);

    int selectByAreaIdCount(Integer areaId);

    int selectByCountryIdCount(Integer countryId);
}
