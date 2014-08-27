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
import com.visa.common.util.StringUtil;
import com.visa.common.util.VisaUtil;
import com.visa.dao.SeqDao;
import com.visa.dao.line.LineCountryDao;
import com.visa.dao.line.LineNameListDao;
import com.visa.dao.line.LineOrderDao;
import com.visa.dao.line.LineProductDao;
import com.visa.dao.line.LinesServiceDao;
import com.visa.dao.line.OperateLogDao;
import com.visa.po.Country;
import com.visa.po.User;
import com.visa.po.line.LineNameList;
import com.visa.po.line.LineOrder;
import com.visa.po.line.LineProduct;
import com.visa.po.line.LinesSrvice;
import com.visa.po.line.OperateLog;
import com.visa.vo.OrderSearchBean;
import com.visa.vo.line.LineOrderVo;

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
    @Resource
    private LinesServiceDao linesServiceDao;
    @Resource
    private LineNameListDao lineNameListDao;
    @Resource
    private SeqDao seqDao;
    @Resource
    private OperateLogDao operateLogDao;

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

        List<LineOrder> orderList = lineOrderDao.selectByPage(paraMap);
        model.addAttribute("orderList", orderList);
        model.addAttribute("page", page);
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
            LineOrderVo lineOrder, ModelMap model) {
        int orderSeq = seqDao.select("lineOrder");
        String prefix = StringUtil.paddingZeroToLeft(String.valueOf(orderSeq), 6);
        lineOrder.setOrderSeq(prefix);
        lineOrder.setSalesmanId(user.getUserId());
        // 散拼团订单时，校验该产品下所有订单客人总量是否大于机位数
        if (lineOrder.getType() == 2) {
            LineProduct product = lineProductDao.selectByPrimaryKey(lineOrder.getLineProductId());
            List<LineOrder> lineOrderList = lineOrderDao.selectByProductId(lineOrder
                    .getLineProductId());
            int count = 0;
            for (LineOrder order : lineOrderList) {
                count += order.getNameListSize();
            }
            count += lineOrder.getNameListSize();
            int seatNum = product.getSeatNum();
            if (seatNum - count < 0) {
                model.put("msg", "新建订单失败，客人数量大于剩余机位数！");
                model.put("code", 404);
                model.put("topNav", 10);
                model.put("secNav", 101);
                model.put("title", "订单信息新增");
                model.put("href", "/lineproduct/list.do");
                return "result";
            }
        }
        lineOrderDao.insert(lineOrder);
        for (LinesSrvice srvice : lineOrder.getLineOrderService()) {
            linesServiceDao.insert(srvice);
        }
        for (LineNameList custom : lineOrder.getLineCustomList()) {
            lineNameListDao.insert(custom);
        }
        // 记录操作日志
        OperateLog operateLog = new OperateLog();
        operateLog.setUserId(user.getUserId());
        operateLog.setRoleId(user.getRoleId());
        operateLog.setOperateType(Constant.OPERATOR_TYPE_ADD);
        operateLog.setOperateDes(StringUtil.generateAddOperLog(lineOrder));
        operateLog.setOrderSeq(prefix);
        operateLogDao.insert(operateLog);
        return "redirect:list.do?page=1";
    }

    /**
     * 编辑一个订单
     * 
     * @param userId userId
     * @param page page
     * @param model model
     */
    @RequestMapping
    public void edit(@ModelAttribute(Constant.SESSION_USER) User user, Integer orderId,
            Integer currentPage, ModelMap model) {
        LineOrder lineOrder = lineOrderDao.selectByPrimaryKey(orderId);
        List<LinesSrvice> lineServiceList = linesServiceDao.selectAllLinesSrvice(orderId);
        Map<Integer, LinesSrvice> lineServiceMap = new HashMap<Integer, LinesSrvice>();
        for (LinesSrvice linesSrvice : lineServiceList) {
            int serviceType = linesSrvice.getServiceType();
            if (serviceType == 41 || serviceType == 42) {
                serviceType = 4;
            }
            lineServiceMap.put(serviceType, linesSrvice);
        }
        List<Country> countryList = lineCountryDao.selectAllCountry();
        List<LineProduct> lineProductList = lineProductDao.selectAllLineProduct();
        List<LineNameList> customList = lineNameListDao.selectAllLineNameList(orderId);
        model.put("customList", customList);
        model.put("lineOrder", lineOrder);
        model.put("lineServiceMap", lineServiceMap);
        model.put("lineProductList", lineProductList);
        model.put("countryList", countryList);
        model.put("currentPage", currentPage);
    }

    /**
     * 编辑一个订单
     * 
     * @param user user
     * @param page page
     * @return String
     */
    @RequestMapping
    public String update(@ModelAttribute(Constant.SESSION_USER) User user, LineOrderVo lineOrderVo,
            Integer currentPage) {
        LineOrder lineOrder = lineOrderDao.selectByPrimaryKey(lineOrderVo.getOrderId());
        Map<Integer, LinesSrvice> serviceListDB = VisaUtil.dealServiceList(linesServiceDao
                .selectAllLinesSrvice(lineOrderVo.getOrderId()));
        Map<Integer, LineNameList> nameListDB = VisaUtil.dealNameList(lineNameListDao
                .selectAllLineNameList(lineOrderVo.getOrderId()));

        lineOrderDao.updateByPrimaryKey(lineOrderVo);
        linesServiceDao.deleteByOrderId(lineOrderVo.getOrderId());
        for (LinesSrvice srvice : lineOrderVo.getLineOrderService()) {
            linesServiceDao.insert(srvice);
        }
        lineNameListDao.deleteByOrderId(lineOrderVo.getOrderId());
        for (LineNameList custom : lineOrderVo.getLineCustomList()) {
            lineNameListDao.insert(custom);
        }

        // 记录操作日志
        OperateLog operateLog = new OperateLog();
        operateLog.setUserId(user.getUserId());
        operateLog.setRoleId(user.getRoleId());
        operateLog.setOperateType(Constant.OPERATOR_TYPE_UPDATE);
        operateLog.setOperateDes(StringUtil.generateUpdateOperLog(lineOrderVo, lineOrder,
                serviceListDB, nameListDB));
        operateLog.setOrderSeq(lineOrder.getOrderSeq());
        operateLogDao.insert(operateLog);
        return "redirect:list.do?page=" + currentPage;
    }

    /**
     * 删除一个订单
     * 
     * @param orderId orderId
     * @param page page
     * @return String
     */
    @RequestMapping
    public String delete(@ModelAttribute(Constant.SESSION_USER) User user, int orderId, Integer page) {
        LineOrder lineOrder = lineOrderDao.selectByPrimaryKey(orderId);
        lineOrderDao.deleteByPrimaryKey(orderId);
        // 记录操作日志
        OperateLog operateLog = new OperateLog();
        operateLog.setUserId(user.getUserId());
        operateLog.setRoleId(user.getRoleId());
        operateLog.setOperateType(Constant.OPERATOR_TYPE_DELETE);
        operateLog.setOperateDes(StringUtil.generateDeleteOperLog(lineOrder));
        operateLog.setOrderSeq(lineOrder.getOrderSeq());
        operateLogDao.insert(operateLog);
        return "redirect:list.do?page=" + page;
    }

}
