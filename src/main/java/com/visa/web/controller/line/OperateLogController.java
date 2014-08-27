package com.visa.web.controller.line;

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
import com.visa.vo.line.OperateLogVo;

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
    public void list(OperateLogVo operateLog, Integer page, ModelMap model) {
        Map<String, Object> paraMap = new HashMap<String, Object>();

        String userName = operateLog.getUserName();
        String orderSeq = operateLog.getOrderSeq();
        paraMap.put("operator", "like");
        paraMap.put("orderSeq", StringUtils.isEmpty(orderSeq) ? null : "%" + orderSeq + "%");
        paraMap.put("userName", StringUtils.isEmpty(userName) ? null : "%" + userName + "%");

        Integer recordCount = operateLogDao.selectAllCount(paraMap);
        int[] recordRange = PagingUtil.addPagingSupport(Constant.LINE_PAGE_COUNT, recordCount,
                page, Constant.LINE_PAGE_OFFSET, model);

        paraMap.put("begin", recordRange[0]);
        paraMap.put("pageCount", Constant.LINE_PAGE_COUNT);

        List<OperateLog> operateLogList = operateLogDao.selectByPage(paraMap);
        model.put("operateLogList", operateLogList);
        model.put("operateLog", operateLog);
    }
}
