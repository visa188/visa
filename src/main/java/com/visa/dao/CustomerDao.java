package com.visa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.visa.po.Customer;
import com.visa.vo.CustomerVo;

public interface CustomerDao {

    int deleteByPrimaryKey(Integer customerId);

    int insert(Customer record);

    Integer selectBySalesmanIdCount(@Param("salesmanId") String salesmanId, @Param("customer") Customer customer);

    List<CustomerVo> selectBySalesmanId(@Param("salesmanId") String salesmanId, @Param("start") Integer start,
            @Param("pageCount") Integer pageCount, @Param("customer") Customer customer);

    List<Customer> selectAllBySalesmanId(String salesmanId);

    Integer selectAllCount(Customer customer);

    List<CustomerVo> selectAll(@Param("start") Integer start, @Param("pageCount") Integer pageCount,
            @Param("customer") Customer customer);

    List<Customer> selectAllCustomer();

    Customer selectByPrimaryKey(Integer customerId);

    int updateByPrimaryKey(Customer record);

    int selectByTelephoneCount(@Param("telephone") String telephone, @Param("customerId") Integer customerId);

    List<Customer> searchCustomer(@Param("customerName") String customerName, @Param("userId") String userId);

    List<String> selectCompany();
}
