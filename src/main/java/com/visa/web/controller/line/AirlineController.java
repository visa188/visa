package com.visa.web.controller.line;

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
import com.visa.dao.ProductDao;
import com.visa.dao.line.AirlineDao;
import com.visa.po.Airline;

/**
 * @author user
 */
@Controller
@RequestMapping("/airline/*")
@SessionAttributes(Constant.SESSION_USER)
public class AirlineController {
    @Resource
    private AirlineDao airlineDao;
    @Resource
    private ProductDao productDao;

    /**
     * 列出所有的国家
     * 
     * @param Airline Airline
     * @param page page
     * @param model model
     */
    @RequestMapping
    public void list(Airline Airline, Integer page, ModelMap model) {
        List<Airline> airlineList = new ArrayList<Airline>();
        Integer recordCount = airlineDao.selectAllCount(Airline);
        int[] recordRange = PagingUtil.addPagingSupport(Constant.PAGE_COUNT, recordCount, page,
                Constant.PAGE_OFFSET, model);
        airlineList = airlineDao.selectAll(recordRange[0], Constant.PAGE_COUNT, Airline);
        model.put("airlineList", airlineList);
    }

    /**
     * 增加一个国家
     * 
     * @param model model
     */
    @RequestMapping
    public void add(ModelMap model) {
        model.put("topNav", 8);
        model.put("secNav", 82);
        model.put("title", "新增航空公司信息");
        model.put("action", "insert");
    }

    /**
     * 增加一个国家
     * 
     * @param Airline Airline
     * @return String
     */
    @RequestMapping
    public String insert(Airline Airline) {
        airlineDao.insert(Airline);
        return "redirect:airline/list.do";
    }

    /**
     * 编辑一个国家
     * 
     * @param airlineId airlineId
     * @param page page
     * @param model model
     * @return String
     */
    @RequestMapping
    public String edit(Integer airlineId, Integer page, ModelMap model) {
        Airline Airline = airlineDao.selectByPrimaryKey(airlineId);
        model.put("airline", Airline);
        model.put("topNav", 2);
        model.put("secNav", 22);
        model.put("title", "修改航空公司信息");
        model.put("action", "update");
        model.put("page", page);
        return "airline/add";
    }

    /**
     * 提交修改的country
     * 
     * @param Airline Airline
     * @param page page
     * @return String
     */
    @RequestMapping
    public String update(Airline Airline, Integer page) {
        airlineDao.updateByPrimaryKey(Airline);
        return "redirect:http://cloud.haoqianwang.com:81/airline/list.do?page=" + page;
    }

    /**
     * 删除一个country
     * 
     * @param airlineId airlineId
     * @param page page
     * @param model model
     * @return String
     */
    @RequestMapping
    public String delete(Integer airlineId, Integer page, ModelMap model) {
        int count = productDao.selectByCountryIdCount(airlineId);
        if (count == 0) {
            airlineDao.deleteByPrimaryKey(airlineId);
            return "redirect:http://cloud.haoqianwang.com:81/airline/list.do?page=" + page;
        } else {
            model.put("msg", "产品中用到此航空公司信息，不能删除！");
            model.put("code", 404);
            model.put("topNav", 8);
            model.put("secNav", 81);
            model.put("title", "航空公司信息删除");
            model.put("href", "/Airline/list.do?page=" + page);
            return "result";
        }
    }

    /**
     * 根据地区查询国家
     * 
     * @param continentId continentId
     * @return List<Airline>
     */
    @RequestMapping
    @ResponseBody
    public List<Airline> listByContinentId(Integer continentId) {
        return airlineDao.selectByContinentId(continentId);
    }
}
