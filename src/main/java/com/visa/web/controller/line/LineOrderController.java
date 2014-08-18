package com.visa.web.controller.line;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.visa.common.constant.Constant;
import com.visa.common.util.PagingUtil;
import com.visa.dao.line.LineCountryDao;
import com.visa.dao.line.LineOrderDao;
import com.visa.dao.line.LineProductDao;
import com.visa.po.Country;
import com.visa.po.Orders;
import com.visa.po.User;
import com.visa.po.line.LineOrder;
import com.visa.po.line.LineProduct;
import com.visa.vo.OrderSearchBean;

/**
 * @author LineOrder
 */
@Controller
@RequestMapping("/lineOrder/*")
@SessionAttributes(Constant.SESSION_USER)
public class LineOrderController {
    @Resource
    private LineOrderDao lineOrderDao;
    @Resource
    private LineCountryDao lineCountryDao;
    @Resource
    private LineProductDao lineProductDao;

    /**
     * 列出所有的订单
     * 
     * @param user user
     * @param bean bean
     * @param page page
     * @param model model
     */
    @RequestMapping
    public void list(@ModelAttribute(Constant.SESSION_USER) User user, Integer page,
            ModelMap model, @ModelAttribute OrderSearchBean bean) {
        Map<String, Object> paraMap = new HashMap<String, Object>();
        // 记录总条数
        int recordCount = lineOrderDao.count(paraMap);
        int[] recordRange = PagingUtil.addPagingSupport(Constant.LINE_PAGE_COUNT, recordCount,
                page, Constant.LINE_PAGE_OFFSET, model);
        paraMap.put("begin", recordRange[0]);
        paraMap.put("pageCount", Constant.LINE_PAGE_COUNT);

        List<Orders> orderList = lineOrderDao.selectByPage(paraMap);
        model.addAttribute("orderList", orderList);
    }

    /**
     * 增加一个订单
     * 
     * @param model model
     */
    @RequestMapping
    public void add(ModelMap model) {
        List<Country> countryList = lineCountryDao.selectAllCountry();
        List<LineProduct> lineProductList = lineProductDao.selectAllLineProduct();
        model.put("lineProductList", lineProductList);
        model.put("countryList", countryList);
    }

    /**
     * 增加一个订单
     * 
     * @param user user
     * @param lineOrder lineOrder
     * @param model model
     * @return String
     */
    @RequestMapping
    public String addSubmit(@ModelAttribute(Constant.SESSION_USER) User user,
            @ModelAttribute("lineOrder") LineOrder lineOrder, ModelMap model) {
        lineOrderDao.insert(lineOrder);
        return "redirect:list.do?page=1";
    }

    /**
     * 编辑一个订单
     * 
     * @param userId userId
     * @param page page
     * @param model model
     * @return String
     */
    @RequestMapping
    public String edit(String userId, Integer page, ModelMap model) {
        return "redirect:list.do?page=" + page;
    }

    /**
     * 编辑一个订单
     * 
     * @param user user
     * @param page page
     * @return String
     */
    @RequestMapping
    public String update(User user, Integer page) {
        return "redirect:list.do?page=" + page;
    }

    /**
     * 删除一个订单
     * 
     * @param orderId orderId
     * @param page page
     * @return String
     */
    @RequestMapping
    public String delete(int orderId, Integer page) {
        lineOrderDao.deleteByPrimaryKey(orderId);
        return "redirect:list.do?page=" + page;
    }

}
