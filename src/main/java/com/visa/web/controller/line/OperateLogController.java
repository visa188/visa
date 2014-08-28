package com.visa.web.controller.line;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.visa.common.constant.Constant;
import com.visa.common.util.PagingUtil;
import com.visa.dao.line.OperateLogDao;
import com.visa.po.line.OperateLog;
import com.visa.vo.line.LogSearchBean;

/**
 * @author user
 */
@Controller
@RequestMapping("/operatelog/*")
public class OperateLogController {
    @Resource
    private OperateLogDao operateLogDao;

    /**
     * @param product product
     * @param page page
     * @param model model
     */
    @RequestMapping
    public void list(LogSearchBean searchBean, Integer page, ModelMap model) {
        Map<String, Object> paraMap = new HashMap<String, Object>();

        String userName = searchBean.getUserName();
        String orderSeq = searchBean.getOrderSeq();
        String startDate = searchBean.getStartDate();
        String endDate = searchBean.getEndDate();

        if (StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
            // 如果未选择起止日期，默认为本月一号到当日
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            endDate = sdf.format(c.getTime());
            searchBean.setEndDate(endDate);
            c.set(Calendar.DAY_OF_MONTH, 1);
            startDate = sdf.format(c.getTime());
            searchBean.setStartDate(startDate);
        }
        startDate = searchBean.getStartDate();
        endDate = searchBean.getEndDate();

        paraMap.put("operator", "like");
        paraMap.put("orderSeq", StringUtils.isEmpty(orderSeq) ? null : orderSeq);
        paraMap.put("userName", StringUtils.isEmpty(userName) ? null : "%" + userName + "%");
        paraMap.put("startDate", StringUtils.isEmpty(startDate) ? null : startDate);
        paraMap.put("endDate", StringUtils.isEmpty(endDate) ? null : endDate);

        Integer recordCount = operateLogDao.selectAllCount(paraMap);
        int[] recordRange = PagingUtil.addPagingSupport(Constant.LINE_PAGE_COUNT, recordCount,
                page, Constant.LINE_PAGE_OFFSET, model);

        paraMap.put("begin", recordRange[0]);
        paraMap.put("pageCount", Constant.LINE_PAGE_COUNT);

        List<OperateLog> operateLogList = operateLogDao.selectByPage(paraMap);
        model.put("operateLogList", operateLogList);
        model.put("searchBean", searchBean);
    }
}
