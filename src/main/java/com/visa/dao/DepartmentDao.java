package com.visa.dao;

import java.util.List;

import com.visa.vo.Department;

public interface DepartmentDao {
    int insert(Department department);

    List<Department> selectAll();

    Department select(String name);

    int delete(String name);

    void update(Department department);
}
