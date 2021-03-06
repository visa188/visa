package com.visa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.visa.po.User;
import com.visa.vo.UserVo;

public interface UserDao {

    Integer deleteByPrimaryKey(String userId);

    Integer activeByPrimaryKey(String userId);

    Integer insert(User record);

    Integer selectAllCount(User user);

    List<UserVo> selectAll(@Param("start") Integer start, @Param("pageCount") Integer pageCount,
            @Param("user") User user);

    List<User> selectByRoleId(Integer roleId);

    User selectByPrimaryKey(String userId);

    Integer updateByPrimaryKey(User record);

    Integer updatePwdByUserId(@Param("pwd") String pwd, @Param("userId") String userId);

    Integer selectVisaAllCount(User user);

    List<UserVo> selectVisaAll(@Param("start") Integer start,
            @Param("pageCount") Integer pageCount, @Param("user") User user);

    Integer selectLineAllCount(User user);

    List<UserVo> selectLineAll(@Param("start") Integer start,
            @Param("pageCount") Integer pageCount, @Param("user") User user);

    List<User> selectByLineCountryId(@Param("lineCountryId") Integer lineCountryId,
            @Param("managerId") String managerId, @Param("roleId") Integer roleId);

    List<User> selectVisaOperators(@Param("managerId") String managerId,
            @Param("roleId") Integer roleId);

}
