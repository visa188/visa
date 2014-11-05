package com.visa.web.controller.line;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.visa.common.constant.Constant;
import com.visa.common.util.DateUtil;
import com.visa.dao.line.FeeDetailDao;
import com.visa.dao.line.LineOrderDao;
import com.visa.po.line.FeeDetail;
import com.visa.po.line.LineOrder;

/**
 * @author user
 */
@Controller
@RequestMapping("/feeDetail/*")
@SessionAttributes(Constant.SESSION_USER)
public class FeeDetailController {
    @Resource
    private FeeDetailDao feeDetailDao;
    @Resource
    private LineOrderDao lineOrderDao;

    /**
     * 列出所有的国家
     * 
     * @param Airline Airline
     * @param page page
     * @param model model
     */
    @RequestMapping
    public void list(Integer orderId, Integer feeType, ModelMap model) {
        LineOrder lineOrder = lineOrderDao.selectByPrimaryKey(orderId);
        FeeDetail feeDetail = new FeeDetail();
        feeDetail.setFeeType(feeType);
        feeDetail.setOrderId(orderId);
        List<FeeDetail> feeDetailList = feeDetailDao.selectAll(0, Constant.PAGE_COUNT * 1000,
                feeDetail);
        model.put("feeDetailList", feeDetailList);
        model.put("feeDetail", feeDetail);
        model.put("lineOrder", lineOrder);
    }

    /**
     * 增加一个国家
     * 
     * @param Airline Airline
     * @return String
     */
    @RequestMapping
    @ResponseBody
    public String insert(int orderId, int feeType, String feeAmount, String feeDate, String feeBank) {
        if (!StringUtils.isEmpty(feeAmount) && !StringUtils.isEmpty(feeDate)
                && !StringUtils.isEmpty(feeBank)) {
            FeeDetail feeDetail = new FeeDetail();
            feeDetail.setOrderId(orderId);
            feeDetail.setFeeType(feeType);
            feeDetail.setFeeAmount(new BigDecimal(feeAmount));
            feeDetail.setFeeDate(DateUtil.parse(feeDate, DateUtil.FORMAT_DATE));
            feeDetail.setFeeBank(feeBank);
            feeDetailDao.insert(feeDetail);
        }
        return null;
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
    public String edit(Integer id, Integer page, ModelMap model) {
        FeeDetail feeDetail = feeDetailDao.selectByPrimaryKey(id);
        model.put("feeDetail", feeDetail);
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
    public String update(FeeDetail feeDetail, Integer page) {
        feeDetailDao.updateByPrimaryKey(feeDetail);
        return "redirect:list.do?page=" + page;
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
    public String delete(Integer id, Integer orderId, Integer feeType, ModelMap model) {
        feeDetailDao.deleteByPrimaryKey(id);
        return "redirect:list.do?orderId=" + orderId + "&feeType=" + feeType;
    }

}
