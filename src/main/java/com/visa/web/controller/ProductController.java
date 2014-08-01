package com.visa.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.visa.common.constant.Constant;
import com.visa.common.util.PagingUtil;
import com.visa.dao.AreaDao;
import com.visa.dao.CountryDao;
import com.visa.dao.OrdersDao;
import com.visa.dao.ProductDao;
import com.visa.po.Area;
import com.visa.po.Country;
import com.visa.po.Product;
import com.visa.vo.ProductVo;

/**
 * @author user
 */
@Controller
@RequestMapping("/product/*")
public class ProductController {
    @Resource
    private ProductDao productDao;
    @Resource
    private OrdersDao ordersDao;
    @Resource
    private CountryDao countryDao;
    @Resource
    private AreaDao areaDao;

    /**
     * @param product product
     * @param page page
     * @param model model
     */
    @RequestMapping
    public void list(Product product, Integer page, ModelMap model) {
        List<Area> areaList = areaDao.selectAllArea();
        model.put("areaList", areaList);
        List<Country> countryList = countryDao.selectByContinentId(product.getContinentId());
        model.put("countryList", countryList);
        List<ProductVo> productList = new ArrayList<ProductVo>();
        Integer recordCount = productDao.selectAllCount(product);
        int[] recordRange = PagingUtil.addPagingSupport(Constant.PAGE_COUNT, recordCount, page,
                Constant.PAGE_OFFSET, model);
        productList = productDao.selectAll(recordRange[0], Constant.PAGE_COUNT, product);
        model.put("productList", productList);
    }

    /**
     * 增加一个用户
     * 
     * @param model model
     */
    @RequestMapping
    public void add(ModelMap model) {
        List<Area> areaList = areaDao.selectAllArea();
        model.put("areaList", areaList);
        // List<Country> countryList = countryDao.selectByContinentId(1);
        // model.put("countryList", countryList);
        model.put("topNav", 1);
        model.put("secNav", 12);
        model.put("title", "新增产品信息");
        model.put("action", "insert");
    }

    /**
     * @param product product
     * @return String
     */
    @RequestMapping
    public String insert(Product product) {
        productDao.insert(product);
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
        List<Area> areaList = areaDao.selectAllArea();
        model.put("areaList", areaList);
        Product product = productDao.selectByPrimaryKey(productId);
        List<Country> countryList = countryDao.selectByContinentId(product.getContinentId());
        model.put("countryList", countryList);
        model.put("product", product);
        model.put("topNav", 1);
        model.put("secNav", 11);
        model.put("title", "修改产品信息");
        model.put("action", "update");
        model.put("page", page);
        return "product/add";
    }

    /**
     * @param product product
     * @param page page
     * @return String
     */
    @RequestMapping
    public String update(Product product, Integer page) {
        productDao.updateByPrimaryKey(product);
        return "redirect:list.do?page=" + page;
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
        int count = ordersDao.selectByProductIdCount(productId);
        if (count == 0) {
            productDao.deleteByPrimaryKey(productId);
            return "redirect:list.do?page=" + page;
        } else {
            model.put("msg", "订单中用到，此产品信息不能被删除！");
            model.put("code", 404);
            model.put("topNav", 1);
            model.put("secNav", 11);
            model.put("title", "产品信息删除");
            model.put("href", "/product/list.do?page=" + page);
            return "result";
        }
    }

    /**
     * @param productId productId
     * @return string
     */
    @RequestMapping
    @ResponseBody
    public String getProductPrice(String productId) {
        if (!StringUtils.isEmpty(productId)) {
            Product product = productDao.selectByPrimaryKey(Integer.parseInt(productId));
            if (product == null) {
                return "error";
            } else {
                return product.getPrice().toString();
            }
        }
        return "error";
    }

    /**
     * @param productName productName
     * @return string
     */
    @RequestMapping
    @ResponseBody
    public List<Product> searchProduct(int countryId) {
        return productDao.searchByCountryId(countryId);
    }
}
