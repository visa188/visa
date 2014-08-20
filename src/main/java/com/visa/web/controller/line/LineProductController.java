package com.visa.web.controller.line;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.visa.common.constant.Constant;
import com.visa.common.util.PagingUtil;
import com.visa.dao.line.AirlineDao;
import com.visa.dao.line.LineCountryDao;
import com.visa.dao.line.LineOrderDao;
import com.visa.dao.line.LineProductDao;
import com.visa.po.Airline;
import com.visa.po.Country;
import com.visa.po.line.LineOrder;
import com.visa.po.line.LineProduct;
import com.visa.vo.line.LineProductVo;

/**
 * @author user
 */
@Controller
@RequestMapping("/lineproduct/*")
public class LineProductController {
    @Resource
    private LineProductDao lineProductDao;
    @Resource
    private LineCountryDao lineCountryDao;
    @Resource
    private AirlineDao airlineDao;
    @Resource
    private LineOrderDao lineOrderDao;

    private final Log logger = LogFactory.getLog(getClass());

    /**
     * @param product product
     * @param page page
     * @param model model
     */
    @RequestMapping
    public void list(LineProduct product, Integer page, ModelMap model) {
        Map<String, Object> paraMap = new HashMap<String, Object>();

        String seachProductName = product.getLineProductName();
        Integer lineCountryId = product.getLineCountryId();
        paraMap.put("operator", "like");
        paraMap.put("lineProductName", StringUtils.isEmpty(seachProductName) ? null : "%"
                + seachProductName + "%");
        paraMap.put("lineCountryId", lineCountryId == 0 ? null : lineCountryId);

        Integer recordCount = lineProductDao.selectAllCount(paraMap);
        int[] recordRange = PagingUtil.addPagingSupport(Constant.LINE_PAGE_COUNT, recordCount,
                page, Constant.LINE_PAGE_OFFSET, model);

        paraMap.put("begin", recordRange[0]);
        paraMap.put("pageCount", Constant.LINE_PAGE_COUNT);

        List<LineProductVo> productList = lineProductDao.selectByPage(paraMap);
        model.put("productList", productList);
        List<Country> countryList = lineCountryDao.selectAllCountry();
        model.put("countryList", countryList);
    }

    /**
     * 增加一个用户
     * 
     * @param model model
     */
    @RequestMapping
    public void add(ModelMap model) {
        List<Airline> airlineList = airlineDao.selectAllAirline();
        model.put("airlineList", airlineList);
        List<Country> countryList = lineCountryDao.selectAllCountry();
        model.put("countryList", countryList);
        model.put("topNav", 11);
        model.put("secNav", 112);
        model.put("title", "新增产品信息");
        model.put("action", "insert");
    }

    /**
     * @param product product
     * @return String
     */
    @RequestMapping
    public String insert(LineProduct product) {
        List<LineOrder> lineOrderList = lineOrderDao.selectByProductId(product.getLineProductId());
        int count = 0;
        for (LineOrder order : lineOrderList) {
            count += order.getNameListSize();
        }
        int seatNum = product.getSeatNum();
        if (seatNum - count >= 0) {
            product.setLeftSeatNum(seatNum - count);
        }
        lineProductDao.insert(product);
        return "redirect:list.do";
    }

    /**
     * @param productId productId
     * @param page page
     * @param model model
     * @return String
     */
    @RequestMapping
    public String edit(Integer productId, Integer page, ModelMap model) {
        LineProduct product = lineProductDao.selectByPrimaryKey(productId);
        List<Country> countryList = lineCountryDao.selectAllCountry();
        List<Airline> airlineList = airlineDao.selectAllAirline();
        model.put("airlineList", airlineList);
        model.put("countryList", countryList);
        model.put("product", product);
        model.put("topNav", 11);
        model.put("secNav", 111);
        model.put("title", "修改产品信息");
        model.put("action", "update");
        model.put("page", page);
        return "lineproduct/add";
    }

    /**
     * @param product product
     * @param page page
     * @return String
     */
    @RequestMapping
    public String update(LineProduct product, Integer page, ModelMap model) {
        List<LineOrder> lineOrderList = lineOrderDao.selectByProductId(product.getLineProductId());
        int count = 0;
        for (LineOrder order : lineOrderList) {
            count += order.getNameListSize();
        }
        int seatNum = product.getSeatNum();
        if (seatNum - count >= 0) {
            product.setLeftSeatNum(seatNum - count);
            lineProductDao.updateByPrimaryKey(product);
            return "redirect:list.do?page=" + page;
        } else {
            model.put("msg", "机位数：" + seatNum + "小于该产品下所有订单的客人总数，修改失败！");
            model.put("code", 404);
            model.put("topNav", 10);
            model.put("secNav", 101);
            model.put("title", "产品信息修改");
            model.put("href", "/lineproduct/list.do");
            return "result";
        }
    }

    /**
     * 删除一个user
     * 
     * @param productId productId
     * @param page page
     * @param model model
     * @return String
     */
    @RequestMapping
    public String delete(Integer productId, Integer page, ModelMap model) {
        int count = lineOrderDao.selectByProductIdCount(productId);
        if (count == 0) {
            lineProductDao.deleteByPrimaryKey(productId);
            return "redirect:list.do?page=" + page;
        } else {
            model.put("msg", "订单中用到，此产品信息不能被删除！");
            model.put("code", 404);
            model.put("topNav", 11);
            model.put("secNav", 111);
            model.put("title", "产品信息删除");
            model.put("href", "/lineproduct/list.do?page=" + page);
            return "result";
        }
    }

    /**
     * @param productId productId
     * @return string
     */
    @RequestMapping
    @ResponseBody
    public LineProduct getProductInfo(String productId) {
        if (!StringUtils.isEmpty(productId)) {
            LineProduct product = lineProductDao.selectByPrimaryKey(Integer.parseInt(productId));
            return product;
        }
        return null;
    }

    /**
     * @param productId productId
     * @return string
     */
    @RequestMapping
    @ResponseBody
    public Integer getOrderCutomerSize(String productId) {
        int count = 0;
        if (!StringUtils.isEmpty(productId)) {
            List<LineOrder> lineOrderList = lineOrderDao.selectByProductId(Integer
                    .parseInt(productId));
            for (LineOrder order : lineOrderList) {
                count += order.getNameListSize();
            }
        }
        return count;
    }
}
