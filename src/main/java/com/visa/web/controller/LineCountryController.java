package com.visa.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.visa.common.constant.Constant;
import com.visa.common.util.PagingUtil;
import com.visa.dao.LineCountryDao;
import com.visa.dao.ProductDao;
import com.visa.po.Country;

/**
 * @author user
 */
@Controller
@RequestMapping("/linecountry/*")
@SessionAttributes(Constant.SESSION_USER)
public class LineCountryController {
    @Resource
    private LineCountryDao lineCountryDao;
    @Resource
    private ProductDao productDao;

    /**
     * 列出所有的国家
     * 
     * @param country country
     * @param page page
     * @param model model
     */
    @RequestMapping
    public void list(Country country, Integer page, ModelMap model) {
        List<Country> countryList = new ArrayList<Country>();
        Integer recordCount = lineCountryDao.selectAllCount(country);
        int[] recordRange = PagingUtil.addPagingSupport(Constant.PAGE_COUNT, recordCount, page,
                Constant.PAGE_OFFSET, model);
        countryList = lineCountryDao.selectAll(recordRange[0], Constant.PAGE_COUNT, country);
        model.put("countryList", countryList);
    }

    /**
     * 增加一个国家
     * 
     * @param model model
     */
    @RequestMapping
    public void add(ModelMap model) {
        model.put("topNav", 5);
        model.put("secNav", 52);
        model.put("title", "新增国家信息");
        model.put("action", "insert");
    }

    /**
     * 增加一个国家
     * 
     * @param country country
     * @return String
     */
    @RequestMapping
    public String insert(Country country) {
        lineCountryDao.insert(country);
        return "redirect:list.do";
    }

    /**
     * 编辑一个国家
     * 
     * @param countryId countryId
     * @param page page
     * @param model model
     * @return String
     */
    @RequestMapping
    public String edit(Integer countryId, Integer page, ModelMap model) {
        Country country = lineCountryDao.selectByPrimaryKey(countryId);
        model.put("country", country);
        model.put("topNav", 2);
        model.put("secNav", 22);
        model.put("title", "修改国家信息");
        model.put("action", "update");
        model.put("page", page);
        return "country/add";
    }

    /**
     * 提交修改的country
     * 
     * @param country country
     * @param page page
     * @return String
     */
    @RequestMapping
    public String update(Country country, Integer page) {
        lineCountryDao.updateByPrimaryKey(country);
        return "redirect:list.do?page=" + page;
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
    public String delete(Integer countryId, Integer page, ModelMap model) {
        int count = productDao.selectByCountryIdCount(countryId);
        if (count == 0) {
            lineCountryDao.deleteByPrimaryKey(countryId);
            return "redirect:list.do?page=" + page;
        } else {
            model.put("msg", "产品中用到此国家信息，不能删除！");
            model.put("code", 404);
            model.put("topNav", 5);
            model.put("secNav", 51);
            model.put("title", "国家信息删除");
            model.put("href", "/country/list.do?page=" + page);
            return "result";
        }
    }

    /**
     * 根据地区查询国家
     * 
     * @param continentId continentId
     * @return List<Country>
     */
    @RequestMapping
    @ResponseBody
    public List<Country> listByContinentId(Integer continentId) {
        return lineCountryDao.selectByContinentId(continentId);
    }
}
