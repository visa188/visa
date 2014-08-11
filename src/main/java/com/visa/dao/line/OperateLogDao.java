package com.visa.dao.line;

import java.util.List;

import com.visa.po.line.OperateLog;

public interface OperateLogDao {

    int deleteByPrimaryKey(Integer logId);

    int insert(OperateLog record);

    List<OperateLog> selectAllOperateLog();

    OperateLog selectByPrimaryKey(Integer logId);

    int updateByPrimaryKey(OperateLog record);

}
