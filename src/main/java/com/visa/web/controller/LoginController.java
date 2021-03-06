package com.visa.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.visa.common.constant.Constant;
import com.visa.dao.UserDao;
import com.visa.po.User;

/**
 * @author user
 */
@Controller
@RequestMapping("/*")
@SessionAttributes(Constant.SESSION_USER)
public class LoginController {
    private final Log logger = LogFactory.getLog(this.getClass());
    @Resource
    private UserDao userDao;

    /**
     * @param userId userId
     * @param password password
     * @param model model
     * @return string
     */
    @RequestMapping
    public String login(String userId, String password, ModelMap model) {
        User user = userDao.selectByPrimaryKey(userId);
        if (user != null && password != null && password.equals(user.getPwd())) {
            model.addAttribute(Constant.SESSION_USER, user);
            logger.info("Login successfully with userId: " + userId);
            if (user.getRoleId() != null && (user.getRoleId() <= 5 || user.getRoleId() == 8 || user.getRoleId() == 9)) {
                return "redirect:/orders/list.do?page=1";
            } else {
                return "redirect:/lineOrder/list.do?page=1&type=1";
            }
        } else {
            logger.info("Login failed with userId: " + userId);
            if (user.getRoleId() != null && user.getRoleId() <= 5) {
                return "redirect:/login.html";
            } else {
                return "redirect:/login.html";
            }
        }
    }

    /**
     * @param userId userId
     * @param password password
     * @param model model
     * @return string
     */
    @RequestMapping
    @ResponseBody
    public String validate(String userId, String password, ModelMap model) {
        User user = userDao.selectByPrimaryKey(userId);
        if (user == null) {
            return "1";
        } else if (password == null || !password.equals(user.getPwd())) {
            return "2";
        } else {
            return "true";
        }
    }

    /**
     * @param request request
     * @return logout
     */
    @RequestMapping
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute(Constant.SESSION_USER);
            logger.info("Logout with userId: " + user.getUserId());
            session.invalidate();
        }
        return "redirect:/login.html";
    }

}
