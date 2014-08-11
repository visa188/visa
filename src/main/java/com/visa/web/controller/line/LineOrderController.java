package com.visa.web.controller.line;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.visa.common.constant.Constant;
import com.visa.dao.line.LineOrderDao;
import com.visa.po.User;

/**
 * @author LineOrder
 */
@Controller
@RequestMapping("/lineOrder/*")
@SessionAttributes(Constant.SESSION_USER)
public class LineOrderController {
    @Resource
    private LineOrderDao lineOrderDao;

    /**
     * 列出所有的用户
     * 
     * @param sessionUser sessionUser
     * @param searchUserName searchUserName
     * @param searchRoleId searchRoleId
     * @param page page
     * @param model model
     */
    @RequestMapping
    public void list(@ModelAttribute(Constant.SESSION_USER) User sessionUser,
            String searchUserName, Integer searchRoleId, Integer page, ModelMap model) {

    }

    /**
     * 增加一个用户
     * 
     * @param model model
     */
    @RequestMapping
    public void add(ModelMap model) {
        lineOrderDao.selectAllLineOrder();
    }

    /**
     * 增加一个用户
     * 
     * @param user user
     * @param model model
     * @return String
     */
    @RequestMapping
    public String insert(User user, ModelMap model) {
        return "redirect:list.do";
    }

    /**
     * 编辑一个user
     * 
     * @param userId userId
     * @param page page
     * @param model model
     * @return String
     */
    @RequestMapping
    public String edit(String userId, Integer page, ModelMap model) {
        return "redirect:list.do";
    }

    /**
     * 编辑一个user
     * 
     * @param user user
     * @param page page
     * @return String
     */
    @RequestMapping
    public String update(User user, Integer page) {
        return "redirect:list.do";
    }

    /**
     * 删除一个user
     * 
     * @param userId userId
     * @param page page
     * @param model model
     * @return String
     */
    @RequestMapping
    public String delete(String userId, Integer page, ModelMap model) {
        return "redirect:list.do";
    }

}
