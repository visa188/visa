package com.visa.web.controller.line;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.visa.common.constant.Constant;
import com.visa.common.constant.LineRoleEnumType;
import com.visa.common.util.PagingUtil;
import com.visa.common.util.StringUtil;
import com.visa.common.util.VisaUtil;
import com.visa.dao.SeqDao;
import com.visa.dao.UserDao;
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
    private final Log logger = LogFactory.getLog(this.getClass());

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
    @Resource
    private UserDao userDao;

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

    /**
     */
    @RequestMapping
    public void export(ModelMap model) {
        model.put("salesmanList", userDao.selectByRoleId(LineRoleEnumType.SALESMAN.getId()));
        model.put("yearList", lineOrderDao.selectOrderYears());
        model.put("monthList", lineOrderDao.selectOrderMonths());
    }

    /**
     * @param request request
     * @param response response
     */
    @RequestMapping
    public void exportSubmit(HttpServletRequest request, HttpServletResponse response) {
        try {
            String year = request.getParameter("year");
            String month = request.getParameter("month");
            String salesman = request.getParameter("salesman");
            String customerId = request.getParameter("customerId");
            String company = request.getParameter("company");
            String operatorId = request.getParameter("operatorId");
            String yfhkStatus = request.getParameter("yfhkStatus");
            String yshkStatus = request.getParameter("yshkStatus");

            NumberFormat formatter = NumberFormat.getNumberInstance();
            formatter.setMinimumIntegerDigits(2);
            formatter.setGroupingUsed(false);
            month = formatter.format(Integer.parseInt(month));

            // 这里还应增加一个报表时间，精确到月即可
            String fileName = year + "-" + month;
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            this.exportOrderData(toClient, year, month, salesman, yfhkStatus, yshkStatus,
                    customerId, company, operatorId);

        } catch (IOException e) {
            logger.error(e, e);
        }
    }

    /**
     * 订单报表
     * 
     * @param type type
     * @param out out
     */
    private void exportOrderData(OutputStream out, String year, String month, String salesmanId,
            String yfhkStatus, String yshkStatus, String customerId, String company,
            String operatorId) {
        String[] titles = { "客户名称", "下单日期", "产品名称", "客人名单", "客人数量", "销售员", "操作员", "送签日期", "送签员",
                "应收单价", "其它应收款", "其它应付款", "总计应收款", "总计应付款", "毛利润", "付款状态", "已付货款", "收款状态", "已收货款",
                "备注" };
        HSSFWorkbook wb = new HSSFWorkbook();
        Sheet s = wb.createSheet();
        // header row
        Row headerRow = s.createRow(0);
        headerRow.setHeightInPoints(40);
        Cell headerCell;
        for (int i = 0; i < titles.length; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(titles[i]);
        }
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("date", year + "-" + month + "%");
        paraMap.put("salesmanId", StringUtils.isEmpty(salesmanId) ? null : salesmanId);

        paraMap.put("customerId", StringUtils.isEmpty(customerId) ? null : customerId);
        paraMap.put("company", StringUtils.isEmpty(company) ? null : company);
        paraMap.put("operatorId", StringUtils.isEmpty(operatorId) ? null : operatorId);

        paraMap.put("yfhkStatus", StringUtils.isEmpty(yfhkStatus) ? null : yfhkStatus);
        paraMap.put("yshkStatus", StringUtils.isEmpty(yshkStatus) ? null : yshkStatus);
        List<LineOrder> ordersList = lineOrderDao.selectAllLineOrder(paraMap);

        if (ordersList != null && ordersList.size() > 0) {
            new BigDecimal(0);
            new BigDecimal(0);
            new BigDecimal(0);
            int i = 0;
            for (; i < ordersList.size(); i++) {
                ordersList.get(i);
                s.createRow(i + 1);
            }
        }
        try {
            wb.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.error("写出excel出错!", e);
        }

    }
}
