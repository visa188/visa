package com.visa.web.controller.line;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.visa.common.constant.LineSrviceEnumType;
import com.visa.common.util.DateUtil;
import com.visa.common.util.PagingUtil;
import com.visa.common.util.StringUtil;
import com.visa.common.util.VisaUtil;
import com.visa.dao.SeqDao;
import com.visa.dao.UserDao;
import com.visa.dao.line.AirlineDao;
import com.visa.dao.line.LineCountryDao;
import com.visa.dao.line.LineNameListDao;
import com.visa.dao.line.LineOrderDao;
import com.visa.dao.line.LineProductDao;
import com.visa.dao.line.LinesServiceDao;
import com.visa.dao.line.OperateLogDao;
import com.visa.po.Airline;
import com.visa.po.Country;
import com.visa.po.User;
import com.visa.po.line.LineNameList;
import com.visa.po.line.LineOrder;
import com.visa.po.line.LineProduct;
import com.visa.po.line.LinesSrvice;
import com.visa.po.line.OperateLog;
import com.visa.vo.line.LineOrderSearchBean;
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
    @Resource
    private AirlineDao airlineDao;

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
            ModelMap model, @ModelAttribute LineOrderSearchBean bean) {
        Map<String, Object> paraMap = new HashMap<String, Object>();

        String startDate = bean.getStartDate();
        String endDate = bean.getEndDate();
        String orderSeq = bean.getOrderSeq();

        if (StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
            // 如果未选择起止日期，默认为本月一号到当日
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            endDate = sdf.format(c.getTime());
            bean.setEndDate(endDate);
            c.set(Calendar.DAY_OF_MONTH, 1);
            startDate = sdf.format(c.getTime());
            bean.setStartDate(startDate);
        }

        paraMap.put("startDate", StringUtils.isEmpty(startDate) ? null : startDate);
        paraMap.put("endDate", StringUtils.isEmpty(endDate) ? null : endDate);
        paraMap.put("orderSeq", StringUtils.isEmpty(orderSeq) ? null : orderSeq);

        if (LineRoleEnumType.SALESMAN.getId() == user.getRoleId()) {
            paraMap.put("salesmanId", user.getUserId());
        } else if (LineRoleEnumType.SALEMAN_MANAGER.getId() == user.getRoleId()) {
            paraMap.put("managerId", user.getUserId());
            paraMap.put("role", "salesmanId");
        } else if (LineRoleEnumType.OPERATOR.getId() == user.getRoleId()) {
            paraMap.put("lineOperatorId", user.getUserId());
        } else if (LineRoleEnumType.VISAOPER.getId() == user.getRoleId()) {
            paraMap.put("visaOperatorId", user.getUserId());
        } else if (LineRoleEnumType.SIGNOPERATOR.getId() == user.getRoleId()) {
            paraMap.put("signOperatorId", user.getUserId());
        }

        // 记录总条数
        int recordCount = lineOrderDao.count(paraMap);
        int[] recordRange = PagingUtil.addPagingSupport(Constant.LINE_PAGE_COUNT, recordCount,
                page, Constant.LINE_PAGE_OFFSET, model);
        paraMap.put("begin", recordRange[0]);
        paraMap.put("pageCount", Constant.LINE_PAGE_COUNT);

        List<LineOrder> orderList = lineOrderDao.selectByPage(paraMap);
        model.addAttribute("orderList", orderList);
        model.addAttribute("page", page);
        model.addAttribute("searchBean", bean);
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
        lineOrder.setSalesmanName(userDao.selectByPrimaryKey(lineOrder.getSalesmanId())
                .getUserName());
        if (!StringUtils.isEmpty(lineOrder.getLineOperatorId())) {
            lineOrder.setLineOperatorName(userDao.selectByPrimaryKey(lineOrder.getLineOperatorId())
                    .getUserName());
        }
        if (!StringUtils.isEmpty(lineOrder.getVisaOperatorId())) {
            lineOrder.setVisaOperatorName(userDao.selectByPrimaryKey(lineOrder.getVisaOperatorId())
                    .getUserName());
        }
        if (!StringUtils.isEmpty(lineOrder.getSignOperatorId())) {
            lineOrder.setSignOperatorName(userDao.selectByPrimaryKey(lineOrder.getSignOperatorId())
                    .getUserName());
        }
        if (lineOrder.getPrice() != null) {
            lineOrder.setPriceSum(lineOrder.getPrice().pow(lineOrder.getNameListSize()));
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
        List<LineProduct> lineProductList = lineProductDao.selectAllLineProduct();
        List<User> signOperList = userDao.selectByRoleId(LineRoleEnumType.SIGNOPERATOR.getId());
        model.put("signOperList", signOperList);
        model.put("lineOrder", lineOrder);
        model.put("lineServiceMap", lineServiceMap);
        model.put("lineProductList", lineProductList);
        model.put("currentPage", currentPage);
        List<Country> countryList = lineCountryDao.selectAllCountry();
        model.put("countryList", countryList);
        List<LineNameList> customList = lineNameListDao.selectAllLineNameList(orderId);
        model.put("customList", customList);
        List<Airline> airlineList = airlineDao.selectAllAirline();
        model.put("airlineList", airlineList);
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

        lineOrderVo.setSalesmanName(userDao.selectByPrimaryKey(lineOrderVo.getSalesmanId())
                .getUserName());
        if (!StringUtils.isEmpty(lineOrderVo.getLineOperatorId())) {
            lineOrderVo.setLineOperatorName(userDao.selectByPrimaryKey(
                    lineOrderVo.getLineOperatorId()).getUserName());
        }
        if (!StringUtils.isEmpty(lineOrderVo.getVisaOperatorId())) {
            lineOrderVo.setVisaOperatorName(userDao.selectByPrimaryKey(
                    lineOrderVo.getVisaOperatorId()).getUserName());
        }
        if (!StringUtils.isEmpty(lineOrderVo.getSignOperatorId())) {
            lineOrderVo.setSignOperatorName(userDao.selectByPrimaryKey(
                    lineOrderVo.getSignOperatorId()).getUserName());
        }

        lineOrderDao.updateByPrimaryKey(lineOrderVo);
        if (user.getRoleId() == LineRoleEnumType.OPERATOR.getId()
                || user.getRoleId() == LineRoleEnumType.OPERATOR_MANAGER.getId()) {
            List<Integer> serviceTypeList = new ArrayList<Integer>();
            serviceTypeList.add(LineSrviceEnumType.LD.getId());
            serviceTypeList.add(LineSrviceEnumType.JP.getId());
            serviceTypeList.add(LineSrviceEnumType.DJBS.getId());
            serviceTypeList.add(LineSrviceEnumType.DJSJDY.getId());
            serviceTypeList.add(LineSrviceEnumType.BX.getId());
            serviceTypeList.add(LineSrviceEnumType.QT.getId());
            linesServiceDao.deleteByOrderId(lineOrderVo.getOrderId(), serviceTypeList);

            for (LinesSrvice srvice : lineOrderVo.getLineOrderService()) {
                if (srvice.getServiceType() == LineSrviceEnumType.LD.getId()
                        || srvice.getServiceType() == LineSrviceEnumType.JP.getId()
                        || srvice.getServiceType() == LineSrviceEnumType.DJBS.getId()
                        || srvice.getServiceType() == LineSrviceEnumType.DJSJDY.getId()
                        || srvice.getServiceType() == LineSrviceEnumType.BX.getId()
                        || srvice.getServiceType() == LineSrviceEnumType.QT.getId()) {
                    linesServiceDao.insert(srvice);
                }
            }
        } else if (user.getRoleId() == LineRoleEnumType.VISAOPER.getId()
                || user.getRoleId() == LineRoleEnumType.VISAOPER_MANAGER.getId()) {
            List<Integer> serviceTypeList = new ArrayList<Integer>();
            serviceTypeList.add(LineSrviceEnumType.QZ.getId());
            serviceTypeList.add(LineSrviceEnumType.GGBZ.getId());
            linesServiceDao.deleteByOrderId(lineOrderVo.getOrderId(), serviceTypeList);

            for (LinesSrvice srvice : lineOrderVo.getLineOrderService()) {
                if (srvice.getServiceType() == LineSrviceEnumType.QZ.getId()
                        || srvice.getServiceType() == LineSrviceEnumType.GGBZ.getId()) {
                    linesServiceDao.insert(srvice);
                }
            }
        } else if (user.getRoleId() == LineRoleEnumType.FINANCE.getId()
                || user.getRoleId() == LineRoleEnumType.FINANCE_MANAGER.getId()) {
            List<LinesSrvice> lineServiceListDb = linesServiceDao.selectAllLinesSrvice(lineOrderVo
                    .getOrderId());
            linesServiceDao.deleteByOrderId(lineOrderVo.getOrderId(), null);

            List<LinesSrvice> lineServiceList = lineOrderVo.getLineOrderService();
            Map<Integer, LinesSrvice> lineServiceMap = new HashMap<Integer, LinesSrvice>();
            for (LinesSrvice linesSrvice : lineServiceList) {
                int serviceType = linesSrvice.getServiceType();
                if (serviceType == 41 || serviceType == 42) {
                    serviceType = 4;
                }
                lineServiceMap.put(serviceType, linesSrvice);
            }

            LinesSrvice srvice;
            for (LinesSrvice srvicedb : lineServiceListDb) {
                int serviceType = srvicedb.getServiceType();
                if (serviceType == 41 || serviceType == 42) {
                    serviceType = 4;
                }
                srvice = lineServiceMap.get(serviceType);
                if (srvice != null) {
                    srvicedb.setAlreadyGot(srvice.getAlreadyGot());
                    srvicedb.setAlreadyPaid(srvice.getAlreadyPaid());
                    srvicedb.setNeedGot(srvice.getNeedGot());
                    srvicedb.setNeedPaid(srvice.getNeedPaid());
                    srvicedb.setPaidBank(srvice.getPaidBank());
                    srvicedb.setPaidDate(srvice.getPaidDate());
                    srvicedb.setGotBank(srvice.getGotBank());
                    srvicedb.setGotDate(srvice.getGotDate());
                    linesServiceDao.insert(srvicedb);
                } else {
                    System.out.println(serviceType);
                }
            }
        } else {
            linesServiceDao.deleteByOrderId(lineOrderVo.getOrderId(), null);
            for (LinesSrvice srvice : lineOrderVo.getLineOrderService()) {
                linesServiceDao.insert(srvice);
            }
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
        String[] titles = { "订单序号", "销售员", "产品名称", "订单类型", "线路国家", "下单日期", "客人数量", "操作员", "送签员",
                "组团社", "联系人", "联系方式", "备注" };
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
            int i = 0;
            for (; i < ordersList.size(); i++) {
                LineOrder p = ordersList.get(i);
                Row row = s.createRow(i + 1);
                headerCell = row.createCell(0);
                headerCell.setCellValue(p.getOrderSeq());
                headerCell = row.createCell(1);
                headerCell.setCellValue(p.getSalesmanName());
                headerCell = row.createCell(2);
                headerCell.setCellValue(p.getLineProductName());
                headerCell = row.createCell(3);
                headerCell.setCellValue(p.getType() == 1 ? "单团" : "散拼");
                headerCell = row.createCell(4);
                headerCell.setCellValue(p.getLineCountryName());
                headerCell = row.createCell(5);
                headerCell.setCellValue(DateUtil.format(p.getOrderDate(), DateUtil.FORMAT_DATE));
                headerCell = row.createCell(6);
                headerCell.setCellValue(p.getNameListSize());
                headerCell = row.createCell(7);
                headerCell.setCellValue(p.getLineOperatorName());
                headerCell = row.createCell(8);
                headerCell.setCellValue(p.getSignOperatorName());
                headerCell = row.createCell(9);
                headerCell.setCellValue(p.getTravelAgency());
                headerCell = row.createCell(10);
                headerCell.setCellValue(p.getContact());
                headerCell = row.createCell(11);
                headerCell.setCellValue(p.getContactNo());
                headerCell = row.createCell(12);
                headerCell.setCellValue(p.getSpecialComment());
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
