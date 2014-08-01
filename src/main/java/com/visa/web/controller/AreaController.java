package com.visa.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.visa.common.constant.Constant;
import com.visa.common.util.PagingUtil;
import com.visa.dao.AreaDao;
import com.visa.dao.ProductDao;
import com.visa.po.Area;

/**
 * @author user
 */
@Controller
@RequestMapping("/area/*")
@SessionAttributes(Constant.SESSION_USER)
public class AreaController {
    @Resource
    private AreaDao areaDao;
    @Resource
    private ProductDao productDao;

    /**
     * 列出所有的领区
     * 
     * @param area area
     * @param page page
     * @param model model
     */
    @RequestMapping
    public void list(Area area, Integer page, ModelMap model) {
        List<Area> areaList = new ArrayList<Area>();
        Integer recordCount = areaDao.selectAllCount(area);
        int[] recordRange = PagingUtil.addPagingSupport(Constant.PAGE_COUNT, recordCount, page,
                Constant.PAGE_OFFSET, model);
        areaList = areaDao.selectAll(recordRange[0], Constant.PAGE_COUNT, area);
        model.put("areaList", areaList);
    }

    /**
     * 增加一个领区
     * 
     * @param model model
     */
    @RequestMapping
    public void add(ModelMap model) {
        model.put("topNav", 6);
        model.put("secNav", 62);
        model.put("title", "新增领区信息");
        model.put("action", "insert");
    }

    /**
     * 增加一个领区
     * 
     * @param area area
     * @return String
     */
    @RequestMapping
    public String insert(Area area) {
        areaDao.insert(area);
        return "redirect:list.do";
    }

    /**
     * 编辑一个领区
     * 
     * @param areaId areaId
     * @param page page
     * @param model model
     * @return String
     */
    @RequestMapping
    public String edit(Integer areaId, Integer page, ModelMap model) {
        Area area = areaDao.selectByPrimaryKey(areaId);
        model.put("area", area);
        model.put("topNav", 2);
        model.put("secNav", 22);
        model.put("title", "修改领区信息");
        model.put("action", "update");
        model.put("page", page);
        return "area/add";
    }

    /**
     * 提交修改的area
     * 
     * @param area area
     * @param page page
     * @return String
     */
    @RequestMapping
    public String update(Area area, Integer page) {
        areaDao.updateByPrimaryKey(area);
        return "redirect:list.do?page=" + page;
    }

    /**
     * 删除一个area
     * 
     * @param areaId areaId
     * @param page page
     * @param model model
     * @return String
     */
    @RequestMapping
    public String delete(Integer areaId, Integer page, ModelMap model) {
        int count = productDao.selectByAreaIdCount(areaId);
        if (count == 0) {
            areaDao.deleteByPrimaryKey(areaId);
            return "redirect:list.do?page=" + page;
        } else {
            model.put("msg", "产品中用到此领区信息，不能删除！");
            model.put("code", 404);
            model.put("topNav", 6);
            model.put("secNav", 61);
            model.put("title", "领区信息删除");
            model.put("href", "/area/list.do?page=" + page);
            return "result";
        }
    }

}
