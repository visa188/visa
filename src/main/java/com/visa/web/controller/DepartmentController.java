package com.visa.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.visa.common.constant.Constant;
import com.visa.dao.DepartmentDao;
import com.visa.vo.Department;

/**
 * @author user
 */
@Controller
@RequestMapping("/department/*")
@SessionAttributes(Constant.SESSION_USER)
public class DepartmentController {
    @Resource
    private DepartmentDao departmentDao;

    /**
     * 列出所有的国家
     * 
     * @param country country
     * @param page page
     * @param model model
     */
    @RequestMapping
    public void list(ModelMap model) {
        List<Department> departmentList = new ArrayList<Department>();
        departmentList = departmentDao.selectAll();
        model.put("departmentList", departmentList);
    }

    /**
     * 增加一个国家
     * 
     * @param model model
     */
    @RequestMapping
    public void add(ModelMap model) {
        model.put("topNav", 9);
        model.put("secNav", 92);
        model.put("title", "新增部门信息");
        model.put("action", "insert");
    }

    /**
     * 增加一个国家
     * 
     * @param country country
     * @return String
     */
    @RequestMapping
    public String insert(Department department) {
        departmentDao.insert(department);
        return "redirect:list.do";
    }

    /**
     * 删除一个country
     * 
     * @param countryId countryId
     * @param page page
     * @param model model
     * @return String
     */
    @RequestMapping
    public String delete(String name, ModelMap model) {
        departmentDao.delete(name);
        return "redirect:list.do";
    }
}
