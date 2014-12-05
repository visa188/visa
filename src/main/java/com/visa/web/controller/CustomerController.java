package com.visa.web.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.visa.common.constant.Constant;
import com.visa.common.constant.LineRoleEnumType;
import com.visa.common.constant.RoleEnumType;
import com.visa.common.util.DateUtil;
import com.visa.common.util.PagingUtil;
import com.visa.dao.CustomerDao;
import com.visa.dao.OrdersDao;
import com.visa.dao.UserDao;
import com.visa.po.Customer;
import com.visa.po.User;
import com.visa.vo.CustomerVo;

/**
 * @author user
 */
@Controller
@RequestMapping("/customer/*")
@SessionAttributes(Constant.SESSION_USER)
public class CustomerController {
    @Resource
    private CustomerDao customerDao;
    @Resource
    private UserDao userDao;
    @Resource
    private OrdersDao ordersDao;

    private final Log logger = LogFactory.getLog(getClass());

    /**
     * 列出所有的用户
     * 
     * @param sessionUser sessionUser
     * @param customer customer
     * @param page page
     * @param model model
     */
    @RequestMapping
    public void list(@ModelAttribute(Constant.SESSION_USER) User sessionUser, Customer customer,
            Integer page, ModelMap model) {
        List<CustomerVo> customerList = new ArrayList<CustomerVo>();
        if ((sessionUser.getRoleId() != null && sessionUser.getRoleId() == RoleEnumType.ADMIN
                .getId())
                || (sessionUser.getLineRoleId() != null && sessionUser.getLineRoleId() == LineRoleEnumType.ADMIN
                        .getId())) {
            Integer recordCount = customerDao.selectAllCount(customer);
            int[] recordRange = PagingUtil.addPagingSupport(Constant.PAGE_COUNT, recordCount, page,
                    Constant.PAGE_OFFSET, model);
            customerList = customerDao.selectAll(recordRange[0], Constant.PAGE_COUNT, customer);
        } else {
            Integer recordCount = customerDao.selectBySalesmanIdCount(sessionUser.getUserId(),
                    customer);
            int[] recordRange = PagingUtil.addPagingSupport(Constant.PAGE_COUNT, recordCount, page,
                    Constant.PAGE_OFFSET, model);
            customerList = customerDao.selectBySalesmanId(sessionUser.getUserId(), recordRange[0],
                    Constant.PAGE_COUNT, customer);
        }
        model.put("customerList", customerList);
        List<User> salesmanList = userDao.selectByRoleId(RoleEnumType.SALESMAN.getId());
        model.put("salesmanList", salesmanList);
    }

    /**
     * 增加一个用户
     * 
     * @param sessionUser sessionUser
     * @param model model
     */
    @RequestMapping
    public void add(@ModelAttribute(Constant.SESSION_USER) User sessionUser, ModelMap model) {
        if (sessionUser.getRoleId() != null
                && sessionUser.getRoleId() == RoleEnumType.ADMIN.getId()) {
            List<User> salesmanList = userDao.selectByRoleId(RoleEnumType.SALESMAN.getId());
            model.put("salesmanList", salesmanList);
        } else if (sessionUser.getLineRoleId() != null
                && sessionUser.getLineRoleId() == LineRoleEnumType.ADMIN.getId()) {
            List<User> salesmanList = userDao.selectByRoleId(LineRoleEnumType.SALESMAN.getId());
            model.put("salesmanList", salesmanList);
        }
        model.put("topNav", 2);
        model.put("secNav", 22);
        model.put("title", "新增客户信息");
        model.put("action", "insert");
    }

    /**
     * 增加一个用户
     * 
     * @param sessionUser sessionUser
     * @param customer customer
     * @return String
     */
    @RequestMapping
    public String insert(@ModelAttribute(Constant.SESSION_USER) User sessionUser, Customer customer) {
        if (customer.getSalesmanId() == null) {
            customer.setSalesmanId(sessionUser.getUserId());
        }
        customerDao.insert(customer);
        return "redirect:list.do";
    }

    /**
     * 编辑一个user
     * 
     * @param sessionUser sessionUser
     * @param customerId customerId
     * @param page page
     * @param model model
     * @return String
     */
    @RequestMapping
    public String edit(@ModelAttribute(Constant.SESSION_USER) User sessionUser, Integer customerId,
            Integer page, ModelMap model) {
        Customer customer = customerDao.selectByPrimaryKey(customerId);
        model.put("customer", customer);
        if (sessionUser.getRoleId() != null
                && sessionUser.getRoleId() == RoleEnumType.ADMIN.getId()) {
            List<User> salesmanList = userDao.selectByRoleId(RoleEnumType.SALESMAN.getId());
            model.put("salesmanList", salesmanList);
        } else if (sessionUser.getLineRoleId() != null
                && sessionUser.getLineRoleId() == LineRoleEnumType.ADMIN.getId()) {
            List<User> salesmanList = userDao.selectByRoleId(LineRoleEnumType.SALESMAN.getId());
            model.put("salesmanList", salesmanList);
        }
        model.put("topNav", 2);
        model.put("secNav", 22);
        model.put("title", "修改客户信息");
        model.put("action", "update");
        model.put("page", page);
        return "customer/add";
    }

    /**
     * 提交修改的customer
     * 
     * @param sessionUser sessionUser
     * @param customer customer
     * @param page page
     * @return String
     */
    @RequestMapping
    public String update(@ModelAttribute(Constant.SESSION_USER) User sessionUser,
            Customer customer, Integer page) {
        if (customer.getSalesmanId() == null) {
            customer.setSalesmanId(sessionUser.getUserId());
        }
        customerDao.updateByPrimaryKey(customer);
        return "redirect:list.do?page=" + page;
    }

    /**
     * 删除一个user
     * 
     * @param customerId customerId
     * @param page page
     * @param model model
     * @return String
     */
    @RequestMapping
    public String delete(Integer customerId, Integer page, ModelMap model) {
        int count = ordersDao.selectByCustomerIdCount(customerId);
        if (count == 0) {
            customerDao.deleteByPrimaryKey(customerId);
            return "redirect:list.do?page=" + page;
        } else {
            model.put("msg", "订单中用到，此客户信息不能被删除！");
            model.put("code", 404);
            model.put("topNav", 2);
            model.put("secNav", 21);
            model.put("title", "客户信息删除");
            model.put("href", "/customer/list.do?page=" + page);
            return "result";
        }
    }

    /**
     * @param telephone telephone
     * @param customerId customerId
     * @return count
     */
    @RequestMapping
    @ResponseBody
    public int hasThisCustomer(String telephone, Integer customerId) {
        return customerDao.selectByTelephoneCount(telephone, customerId);
    }

    /**
     * @param telephone telephone
     * @param customerId customerId
     * @return count
     */
    @RequestMapping
    @ResponseBody
    public List<Customer> searchCustomer(@ModelAttribute(Constant.SESSION_USER) User user,
            String customerName) {
        String userId = user.getUserId();
        if ((user.getRoleId() != null && user.getRoleId() == RoleEnumType.ADMIN.getId())
                || (user.getLineRoleId() != null && user.getLineRoleId() == LineRoleEnumType.ADMIN
                        .getId())) {
            userId = null;
        }

        return customerDao.searchCustomer(customerName, userId);
    }

    /**
     * 导出客户信息数据
     */
    @RequestMapping
    @ResponseBody
    public void export(@ModelAttribute(Constant.SESSION_USER) User sessionUser, Customer customer,
            HttpServletResponse rsp) {
        List<CustomerVo> customerList = new ArrayList<CustomerVo>();
        if ((sessionUser.getRoleId() != null && sessionUser.getRoleId() == RoleEnumType.ADMIN
                .getId())
                || (sessionUser.getLineRoleId() != null && sessionUser.getLineRoleId() == LineRoleEnumType.ADMIN
                        .getId())) {
            Integer recordCount = customerDao.selectAllCount(customer);
            customerList = customerDao.selectAll(0, recordCount, customer);
        } else {
            Integer recordCount = customerDao.selectBySalesmanIdCount(sessionUser.getUserId(),
                    customer);
            customerList = customerDao.selectBySalesmanId(sessionUser.getUserId(), 0, recordCount,
                    customer);
        }
        try {
            rsp.reset();
            rsp.addHeader("Content-Disposition", "attachment;filename=customer.xls");
            OutputStream toClient = new BufferedOutputStream(rsp.getOutputStream());
            rsp.setContentType("application/octet-stream");
            exportExcel(customerList, toClient);
        } catch (Exception e) {
            logger.error("生成excel失败", e);
        }

    }

    private void exportExcel(List<CustomerVo> customerList, OutputStream os) {
        String[] titles = { "客户名称", "公司", "电话", "QQ", "地址", "所属销售", "备注", "添加日期" };
        HSSFWorkbook wb = new HSSFWorkbook();
        Sheet s = wb.createSheet();
        // header row
        Row headerRow = s.createRow(0);
        headerRow.setHeightInPoints(40);
        Cell headerCell;
        for (int i = 0; i < titles.length; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(titles[i]);
        }

        if (customerList != null && customerList.size() > 0) {
            for (int i = 0; i < customerList.size(); i++) {
                CustomerVo p = customerList.get(i);
                Row row = s.createRow(i + 1);
                int count = 0;
                headerCell = row.createCell(count++);
                headerCell.setCellValue(p.getCustomerName());
                headerCell = row.createCell(count++);
                headerCell.setCellValue(p.getCompany());
                headerCell = row.createCell(count++);
                headerCell.setCellValue(p.getTelephone());
                headerCell = row.createCell(count++);
                headerCell.setCellValue(p.getQq());
                headerCell = row.createCell(count++);
                headerCell.setCellValue(p.getAddress());
                headerCell = row.createCell(count++);
                headerCell.setCellValue(p.getSalesmanName());
                headerCell = row.createCell(count++);
                headerCell.setCellValue(p.getDes());
                headerCell = row.createCell(count++);
                headerCell.setCellValue(DateUtil.format(p.getPostDt(), DateUtil.FORMAT_DATE));
            }
        }
        try {
            wb.write(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            logger.error("写出excel出错!", e);
        }

    }
}
