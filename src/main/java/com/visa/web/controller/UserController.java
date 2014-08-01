package com.visa.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.visa.common.constant.Constant;
import com.visa.common.constant.RoleEnumType;
import com.visa.common.util.PagingUtil;
import com.visa.dao.CustomerDao;
import com.visa.dao.OrdersDao;
import com.visa.dao.UserDao;
import com.visa.po.Customer;
import com.visa.po.User;
import com.visa.vo.UserVo;

/**
 * @author user
 */
@Controller
@RequestMapping("/user/*")
@SessionAttributes(Constant.SESSION_USER)
public class UserController {
    @Resource
    private UserDao userDao;
    @Resource
    private OrdersDao ordersDao;
    @Resource
    private CustomerDao customerDao;

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
    public void list(@ModelAttribute(Constant.SESSION_USER) User sessionUser, String searchUserName,
            Integer searchRoleId, Integer page, ModelMap model) {
        List<UserVo> userList = new ArrayList<UserVo>();
        User user = new User();
        user.setUserName(searchUserName);
        user.setRoleId(searchRoleId);
        if (sessionUser.getRoleId() == RoleEnumType.ADMIN.getId()) {
            Integer recordCount = userDao.selectAllCount(user);
            int[] recordRange = PagingUtil.addPagingSupport(Constant.PAGE_COUNT, recordCount, page,
                    Constant.PAGE_OFFSET, model);
            userList = userDao.selectAll(recordRange[0], Constant.PAGE_COUNT, user);
        } else {
            Integer recordCount = userDao.selectByManagerIdCount(sessionUser.getUserId(), user);
            int[] recordRange = PagingUtil.addPagingSupport(Constant.PAGE_COUNT, recordCount, page,
                    Constant.PAGE_OFFSET, model);
            userList = userDao.selectByManagerId(sessionUser.getUserId(), recordRange[0], Constant.PAGE_COUNT, user);
        }
        model.put("userList", userList);
        model.put("user", user);
    }

    /**
     * 增加一个用户
     * 
     * @param model model
     */
    @RequestMapping
    public void add(ModelMap model) {
        List<User> managerList = userDao.selectByRoleId(RoleEnumType.MANAGER.getId());
        model.put("managerList", managerList);
        model.put("topNav", 4);
        model.put("secNav", 42);
        model.put("title", "新增用户信息");
        model.put("action", "insert");
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
        User userInDb = userDao.selectByPrimaryKey(user.getUserId());
        if (userInDb != null) {
            model.put("topNav", 4);
            model.put("secNav", 42);
            model.put("title", "新增用户信息");
            model.put("msg", "此用户名已经存在，请换其他用户名试试");
            model.put("code", 404);
            return "result";
        } else {
            userDao.insert(user);
            return "redirect:list.do";
        }
    }

    /**
     * 密码修改
     */
    @RequestMapping
    public void changepwd() {

    }

    /**
     * 密码修改 提交
     * 
     * @param sessionUser sessionUser
     * @param oldpwd oldpwd
     * @param newpwd newpwd
     * @param model model
     * @return result
     */
    @RequestMapping
    public String change(@ModelAttribute(Constant.SESSION_USER) User sessionUser, String oldpwd, String newpwd,
            ModelMap model) {
        User userInDb = userDao.selectByPrimaryKey(sessionUser.getUserId());
        if (userInDb.getPwd() != null && userInDb.getPwd().equals(oldpwd)) {
            userDao.updatePwdByUserId(newpwd, sessionUser.getUserId());
            model.put("msg", "密码修改成功");
            model.put("code", 200);
        } else {
            model.put("msg", "旧密码输入错误");
            model.put("code", 404);
        }
        model.put("topNav", 4);
        model.put("secNav", 43);
        model.put("title", "用户密码修改");
        return "result";
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
        User user = userDao.selectByPrimaryKey(userId);
        model.put("user", user);
        List<User> managerList = userDao.selectByRoleId(RoleEnumType.MANAGER.getId());
        model.put("managerList", managerList);
        model.put("topNav", 4);
        model.put("secNav", 41);
        model.put("title", "修改用户信息");
        model.put("action", "update");
        model.put("page", page);
        return "user/add";
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
        userDao.updateByPrimaryKey(user);
        return "redirect:list.do?page=" + page;
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
        int count = ordersDao.selectByUserIdCount(userId);
        if (count > 0) {
            model.put("msg", "订单中用到，此用户信息不能被删除！");
            model.put("code", 404);
            model.put("topNav", 4);
            model.put("secNav", 41);
            model.put("title", "用户信息删除");
            model.put("href", "/user/list.do?page=" + page);
            return "result";
        }
        Customer customer = new Customer();
        count = customerDao.selectBySalesmanIdCount(userId, customer);
        if (count > 0) {
            model.put("msg", "还有此用户的客户，信息不能被删除！");
            model.put("code", 404);
            model.put("topNav", 4);
            model.put("secNav", 41);
            model.put("title", "用户信息删除");
            model.put("href", "/user/list.do?page=" + page);
            return "result";
        }
        userDao.deleteByPrimaryKey(userId);
        return "redirect:list.do?page=" + page;
    }
}
