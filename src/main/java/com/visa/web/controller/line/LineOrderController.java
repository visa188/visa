package com.visa.web.controller.line;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.visa.common.constant.PriceStatusEnum;
import com.visa.common.constant.YshkStatusEnum;
import com.visa.common.util.DateUtil;
import com.visa.common.util.PagingUtil;
import com.visa.common.util.StringUtil;
import com.visa.common.util.VisaUtil;
import com.visa.dao.CustomerDao;
import com.visa.dao.DepartmentDao;
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
import com.visa.po.Customer;
import com.visa.po.User;
import com.visa.po.line.LineNameList;
import com.visa.po.line.LineOrder;
import com.visa.po.line.LineProduct;
import com.visa.po.line.LinesSrvice;
import com.visa.po.line.OperateLog;
import com.visa.vo.Department;
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
    @Resource
    private CustomerDao customerDao;
    @Resource
    private DepartmentDao deptDao;

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
            ModelMap model, @ModelAttribute LineOrderSearchBean bean, Integer type,
            String lineProductOrderSeq) {
        Integer alarmCount = lineOrderDao.countAlarmOrders(type);
        if (StringUtils.isEmpty(lineProductOrderSeq)) {

            Map<String, Object> paraMap = new HashMap<String, Object>();

            String startDate = bean.getSearchStartDate();
            String endDate = bean.getSearchEndDate();
            String orderSeq = bean.getOrderSeq();
            Integer alarmOrders = bean.getAlarmOrders();

            String seachCountryName = bean.getSeachCountryName();
            String customerName = bean.getSeachCustomerName();
            String companyName = bean.getSeachCustomerCompany();
            String nameList = bean.getSeachNameList();
            String yfhkStatus = bean.getSeachYfhkStatus();
            String yshkStatus = bean.getSeachYshkStatus();
            String salesman = bean.getSalesman();
            String operator = bean.getOperator();
            String deptId = bean.getDeptId();

            if (StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
                // 如果未选择起止日期，默认为当日到本月30日
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                startDate = sdf.format(c.getTime());
                bean.setSearchStartDate(startDate);
                c.add(Calendar.MONTH, 3);
                c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
                endDate = sdf.format(c.getTime());
                bean.setSearchEndDate(endDate);
            }

            if (alarmOrders != null && alarmOrders == 1) {
                startDate = null;
                endDate = null;
                bean.setSearchEndDate(null);
                bean.setSearchStartDate(null);
            }

            paraMap.put("startDate", StringUtils.isEmpty(startDate) ? null : startDate);
            paraMap.put("endDate", StringUtils.isEmpty(endDate) ? null : endDate);
            paraMap.put("orderSeq", StringUtils.isEmpty(orderSeq) ? null : orderSeq);
            paraMap.put("alarmOrders", alarmOrders == null || alarmOrders == 0 ? null : alarmOrders);
            paraMap.put("operator", "like");
            paraMap.put("countryName", StringUtils.isEmpty(seachCountryName) ? null : "%"
                    + seachCountryName + "%");
            paraMap.put("customerName", StringUtils.isEmpty(customerName) ? null : "%"
                    + customerName + "%");
            paraMap.put("companyName", StringUtils.isEmpty(companyName) ? null : "%" + companyName
                    + "%");
            paraMap.put("nameList", StringUtils.isEmpty(nameList) ? null : "%" + nameList + "%");
            paraMap.put("yfhkStatus", StringUtils.isEmpty(yfhkStatus) ? null : yfhkStatus);
            paraMap.put("yshkStatus", StringUtils.isEmpty(yshkStatus) ? null : yshkStatus);
            paraMap.put("salesmanForSearch", StringUtils.isEmpty(salesman) ? null : salesman);
            paraMap.put("operatorForSearch", StringUtils.isEmpty(operator) ? null : operator);
            paraMap.put("deptId", StringUtils.isEmpty(deptId) ? null : deptId);

            if (LineRoleEnumType.SALESMAN.getId() == user.getLineRoleId()) {
                paraMap.put("salesmanId", user.getUserId());
            } else if (LineRoleEnumType.SALEMAN_MANAGER.getId() == user.getLineRoleId()) {
                paraMap.put("managerId", user.getUserId());
                paraMap.put("role", "salesmanId");
            } else if (LineRoleEnumType.OPERATOR_MANAGER.getId() == user.getLineRoleId()) {
                paraMap.put("operatorManagerId", user.getUserId());
            } else if (LineRoleEnumType.OPERATOR.getId() == user.getLineRoleId()) {
                paraMap.put("lineOperatorId", user.getUserId());
                paraMap.put("serviceOperatorId", user.getUserId());
            } else if (LineRoleEnumType.VISAOPER.getId() == user.getLineRoleId()) {
                paraMap.put("visaOperatorId", user.getUserId());
            } else if (LineRoleEnumType.SIGNOPERATOR.getId() == user.getLineRoleId()) {
                paraMap.put("signOperatorId", user.getUserId());
            }

            paraMap.put("userRoleId", user.getLineRoleId());

            paraMap.put("type", type);
            // 记录总条数
            int recordCount = lineOrderDao.count(paraMap);
            int[] recordRange = PagingUtil.addPagingSupport(Constant.LINE_PAGE_COUNT, recordCount,
                    page, Constant.LINE_PAGE_OFFSET, model);
            paraMap.put("begin", recordRange[0]);
            paraMap.put("pageCount", Constant.LINE_PAGE_COUNT);

            List<LineOrder> orderList = lineOrderDao.selectByPage(paraMap);

            for (LineOrder order : orderList) {
                if (!StringUtils.isEmpty(order.getLineOperatorId())) {
                    order.setLineOperatorName(userDao.selectByPrimaryKey(order.getLineOperatorId())
                            .getUserName());
                }
                if (!StringUtils.isEmpty(order.getVisaOperatorId())) {
                    order.setVisaOperatorName(userDao.selectByPrimaryKey(order.getVisaOperatorId())
                            .getUserName());
                }
                if (!StringUtils.isEmpty(order.getVisaOperatorId())) {
                    order.setSalesmanName(userDao.selectByPrimaryKey(order.getSalesmanId())
                            .getUserName());
                }
            }

            model.addAttribute("orderList", orderList);
            model.addAttribute("page", page);
            model.addAttribute("searchBean", bean);
            model.addAttribute("type", type);
            if (LineRoleEnumType.SALEMAN_MANAGER.getId() == user.getLineRoleId()
                    || LineRoleEnumType.SALESMAN.getId() == user.getLineRoleId()
                    || LineRoleEnumType.ADMIN.getId() == user.getLineRoleId()) {
                model.addAttribute("alarmCount", alarmCount);
            }
            model.addAttribute("alarmOrders", alarmOrders);

            // 总计应收和总计应付汇总
            Map<String, Object> sumPrice = lineOrderDao.sumPrice(paraMap);
            if (sumPrice != null) {
                DecimalFormat fmt = new DecimalFormat("#,####,####,####.00");
                BigDecimal sumzjys = (BigDecimal) sumPrice.get("sumzjys");
                BigDecimal sumzjyf = (BigDecimal) sumPrice.get("sumzjyf");
                BigDecimal sumyshk = (BigDecimal) sumPrice.get("sumyshk");
                if (sumzjys != null && sumzjyf != null && sumyshk != null) {
                    sumPrice.put("sumzjys", fmt.format(sumzjys));
                    sumPrice.put("sumzjyf", fmt.format(sumzjyf));
                    sumPrice.put("sumwshk", fmt.format(sumzjys.subtract(sumyshk)));
                    sumPrice.put("sumlr", fmt.format(sumzjys.subtract(sumzjyf)));
                }
            }
            model.addAttribute("sumPrice", sumPrice);
            List<User> salesmanList = userDao.selectByRoleId(LineRoleEnumType.SALESMAN.getId());
            List<User> tempSalesmanList = null;
            if (!StringUtils.isEmpty(deptId)) {
                tempSalesmanList = new ArrayList<User>();
                for (User man : salesmanList) {
                    if (deptId.equals(man.getDeptId())) {
                        tempSalesmanList.add(man);
                    }
                }
            } else {
                tempSalesmanList = salesmanList;
            }
            model.put("salesmanList", tempSalesmanList);
            List<User> operatorList = userDao.selectByRoleId(LineRoleEnumType.OPERATOR.getId());
            model.put("operatorList", operatorList);
            List<Department> deptList = deptDao.selectAll();
            model.put("deptList", deptList);
        } else {
            Map<String, Object> paraMap = new HashMap<String, Object>();
            paraMap.put("type", type);
            paraMap.put("userRoleId", user.getLineRoleId());

            if (LineRoleEnumType.SALESMAN.getId() == user.getLineRoleId()) {
                paraMap.put("salesmanId", user.getUserId());
            } else if (LineRoleEnumType.SALEMAN_MANAGER.getId() == user.getLineRoleId()) {
                paraMap.put("managerId", user.getUserId());
                paraMap.put("role", "salesmanId");
            } else if (LineRoleEnumType.OPERATOR.getId() == user.getLineRoleId()) {
                paraMap.put("lineOperatorId", user.getUserId());
                paraMap.put("serviceOperatorId", user.getUserId());
            } else if (LineRoleEnumType.VISAOPER.getId() == user.getLineRoleId()) {
                paraMap.put("visaOperatorId", user.getUserId());
            } else if (LineRoleEnumType.SIGNOPERATOR.getId() == user.getLineRoleId()) {
                paraMap.put("signOperatorId", user.getUserId());
            }

            paraMap.put("begin", 0);
            paraMap.put("pageCount", Constant.LINE_PAGE_COUNT * 1000);
            List<LineOrder> orderList = new ArrayList<LineOrder>();
            List<LineOrder> tempOrderList = lineOrderDao.selectByPage(paraMap);
            for (LineOrder lineOrder : tempOrderList) {
                if (lineProductOrderSeq.equals(lineOrder.getLineProductOrderSeq())) {

                    if (!StringUtils.isEmpty(lineOrder.getLineOperatorId())) {
                        lineOrder.setLineOperatorName(userDao.selectByPrimaryKey(
                                lineOrder.getLineOperatorId()).getUserName());
                    }
                    if (!StringUtils.isEmpty(lineOrder.getVisaOperatorId())) {
                        lineOrder.setVisaOperatorName(userDao.selectByPrimaryKey(
                                lineOrder.getVisaOperatorId()).getUserName());
                    }
                    if (!StringUtils.isEmpty(lineOrder.getVisaOperatorId())) {
                        lineOrder.setSalesmanName(userDao.selectByPrimaryKey(
                                lineOrder.getSalesmanId()).getUserName());
                    }

                    orderList.add(lineOrder);
                }
            }
            model.addAttribute("orderList", orderList);
            model.addAttribute("page", 1);
            model.addAttribute("hideSearch", "1");
            model.addAttribute("type", type);
        }
    }

    /**
     * 增加一个订单
     * 
     * @param model model
     */
    @RequestMapping
    public void add(Integer lineproductId, ModelMap model, int type) {
        if (lineproductId != null) {
            LineProduct product = lineProductDao.selectByPrimaryKey(lineproductId);
            model.put("product", product);
        }
        List<Country> countryList = lineCountryDao.selectAllCountry();
        List<LineProduct> lineProductList = lineProductDao.selectAllLineProduct();
        model.put("lineProductList", lineProductList);
        model.put("countryList", countryList);
        model.put("type", type);
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
        String prefix = "XL" + StringUtil.paddingZeroToLeft(String.valueOf(orderSeq), 6);
        lineOrder.setOrderSeq(prefix);
        lineOrder.setSalesmanId(user.getUserId());
        lineOrder.setStatus(0);
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
        if (LineRoleEnumType.SALESMAN.getId() == user.getLineRoleId()) {
            lineOrder.setYshkstatus(PriceStatusEnum.NOTYET.getId());
            lineOrder.setYfhkstatus(YshkStatusEnum.NOTYET.getId());
        }
        lineOrderDao.insert(lineOrder);

        LineProduct p = lineProductDao.selectByPrimaryKey(lineOrder.getLineProductId());
        if (lineOrder.type == 2) {
            if (lineOrder.nameListType == 1) {
                int qw = p.qw + lineOrder.nameListSize;
                p.setQw(qw);
            } else if (lineOrder.nameListType == 2) {
                int zw = p.zw + lineOrder.nameListSize;
                p.setZw(zw);
            } else if (lineOrder.nameListType == 3) {
                int yb = p.yb + lineOrder.nameListSize;
                p.setYb(yb);
            }
            p.setLeftSeatNum(p.seatNum - p.qw - p.zw - p.yb);
            lineProductDao.updateByPrimaryKey(p);
        }

        for (LinesSrvice srvice : lineOrder.getLineOrderService()) {
            linesServiceDao.insert("linessrvice", srvice);
        }
        for (LineNameList custom : lineOrder.getLineCustomList()) {
            lineNameListDao.insert(custom);
        }

        // 记录操作日志
        OperateLog operateLog = new OperateLog();
        operateLog.setUserId(user.getUserId());
        operateLog.setRoleId(user.getLineRoleId());
        operateLog.setOperateType(Constant.OPERATOR_TYPE_ADD);
        operateLog.setOperateDes(StringUtil.generateAddOperLog(lineOrder));
        operateLog.setOrderSeq(prefix);
        operateLogDao.insert(operateLog);
        return "redirect:list.do?page=1&type=" + lineOrder.getType();
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
            Integer currentPage, @ModelAttribute LineOrderSearchBean bean, ModelMap model) {
        LineOrder lineOrder = lineOrderDao.selectByPrimaryKey(orderId);
        List<LinesSrvice> lineServiceList = linesServiceDao.selectAllLinesSrvice("linessrvice",
                orderId);
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
        Customer customer = customerDao.selectByPrimaryKey(lineOrder.getCustomerId());
        model.put("customer", customer);
        List<Airline> airlineList = airlineDao.selectAllAirline();
        model.put("airlineList", airlineList);
        LineProduct product = lineProductDao.selectByPrimaryKey(lineOrder.getLineProductId());
        model.put("product", product);
        model.addAttribute("searchBean", bean);
    }

    /**
     * 编辑一个订单
     * 
     * @param user user
     * @param page page
     * @return String
     */
    @SuppressWarnings("unchecked")
    @RequestMapping
    public String update(@ModelAttribute(Constant.SESSION_USER) User user, LineOrderVo lineOrderVo,
            Integer currentPage, @ModelAttribute LineOrderSearchBean bean, ModelMap model) {
        LineOrder lineOrder = lineOrderDao.selectByPrimaryKey(lineOrderVo.getOrderId());
        List<LinesSrvice> tempServiceListDB = linesServiceDao.selectAllLinesSrvice("linessrvice",
                lineOrderVo.getOrderId());
        Map<Integer, LinesSrvice> serviceListDB = VisaUtil.dealServiceList(tempServiceListDB);
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
            User tempUser = userDao.selectByPrimaryKey(lineOrderVo.getSignOperatorId());
            if (tempUser != null) {
                lineOrderVo.setSignOperatorName(tempUser.getUserName());
            }
        }

        if (user.getLineRoleId() == LineRoleEnumType.SALEMAN_MANAGER.getId()
                || user.getLineRoleId() == LineRoleEnumType.ADMIN.getId()) {
            // 订单含有签证服务status=1，没有的话status=2
            if (lineOrderVo.getQz() != null && lineOrderVo.getQz().startsWith("1")) {
                lineOrderVo.setStatus(1);
            } else {
                lineOrderVo.setStatus(2);
            }
        }

        lineOrderDao.updateByPrimaryKey(lineOrderVo);

        if (lineOrder.type == 2) {
            if (lineOrder.getLineProductId() == lineOrderVo.getLineProductId()) {
                LineProduct p = lineProductDao.selectByPrimaryKey(lineOrderVo.getLineProductId());
                if (lineOrder.nameListType == lineOrderVo.nameListType) {
                    if (lineOrderVo.nameListType == 1) {
                        int qw = p.qw + (lineOrderVo.nameListSize - lineOrder.nameListSize);
                        p.setQw(qw);
                    } else if (lineOrderVo.nameListType == 2) {
                        int zw = p.zw + (lineOrderVo.nameListSize - lineOrder.nameListSize);
                        p.setZw(zw);
                    } else if (lineOrderVo.nameListType == 3) {
                        int yb = p.yb + (lineOrderVo.nameListSize - lineOrder.nameListSize);
                        p.setYb(yb);
                    }
                } else {
                    if (lineOrder.nameListType == 1) {
                        int qw = p.qw - lineOrder.nameListSize;
                        p.setQw(qw);
                    } else if (lineOrder.nameListType == 2) {
                        int zw = p.zw - lineOrder.nameListSize;
                        p.setZw(zw);
                    } else if (lineOrder.nameListType == 3) {
                        int yb = p.yb - lineOrder.nameListSize;
                        p.setYb(yb);
                    }

                    if (lineOrderVo.nameListType == 1) {
                        int qw = p.qw + lineOrderVo.nameListSize;
                        p.setQw(qw);
                    } else if (lineOrderVo.nameListType == 2) {
                        int zw = p.zw + lineOrderVo.nameListSize;
                        p.setZw(zw);
                    } else if (lineOrderVo.nameListType == 3) {
                        int yb = p.yb + lineOrderVo.nameListSize;
                        p.setYb(yb);
                    }
                }
                p.setLeftSeatNum(p.seatNum - p.qw - p.zw - p.yb);
                lineProductDao.updateByPrimaryKey(p);
            } else {
                LineProduct p = lineProductDao.selectByPrimaryKey(lineOrder.getLineProductId());
                if (lineOrder.nameListType == 1) {
                    int qw = p.qw - lineOrder.nameListSize;
                    p.setQw(qw);
                } else if (lineOrder.nameListType == 2) {
                    int zw = p.zw - lineOrder.nameListSize;
                    p.setZw(zw);
                } else if (lineOrder.nameListType == 3) {
                    int yb = p.yb - lineOrder.nameListSize;
                    p.setYb(yb);
                }
                p.setLeftSeatNum(p.seatNum - p.qw - p.zw - p.yb);
                lineProductDao.updateByPrimaryKey(p);

                p = lineProductDao.selectByPrimaryKey(lineOrderVo.getLineProductId());
                if (lineOrderVo.nameListType == 1) {
                    int qw = p.qw + lineOrderVo.nameListSize;
                    p.setQw(qw);
                } else if (lineOrderVo.nameListType == 2) {
                    int zw = p.zw + lineOrderVo.nameListSize;
                    p.setZw(zw);
                } else if (lineOrderVo.nameListType == 3) {
                    int yb = p.yb + lineOrderVo.nameListSize;
                    p.setYb(yb);
                }
                p.setLeftSeatNum(p.seatNum - p.qw - p.zw - p.yb);
                lineProductDao.updateByPrimaryKey(p);
            }
        }

        if (user.getLineRoleId() == LineRoleEnumType.OPERATOR.getId()
                || user.getLineRoleId() == LineRoleEnumType.OPERATOR_MANAGER.getId()) {
            List<Integer> serviceTypeList = new ArrayList<Integer>();
            serviceTypeList.add(LineSrviceEnumType.LD.getId());
            serviceTypeList.add(LineSrviceEnumType.JP.getId());
            serviceTypeList.add(LineSrviceEnumType.DJBS.getId());
            serviceTypeList.add(LineSrviceEnumType.DJSJDY.getId());
            serviceTypeList.add(LineSrviceEnumType.BX.getId());
            serviceTypeList.add(LineSrviceEnumType.QT.getId());

            for (LinesSrvice srvice : lineOrderVo.getLineOrderService()) {
                if (srvice.getServiceType() == LineSrviceEnumType.LD.getId()
                        || srvice.getServiceType() == LineSrviceEnumType.JP.getId()
                        || srvice.getServiceType() == LineSrviceEnumType.DJBS.getId()
                        || srvice.getServiceType() == LineSrviceEnumType.DJSJDY.getId()
                        || srvice.getServiceType() == LineSrviceEnumType.BX.getId()
                        || srvice.getServiceType() == LineSrviceEnumType.QT.getId()) {
                    dealService(tempServiceListDB, srvice);
                }
            }
        } else if (user.getLineRoleId() == LineRoleEnumType.VISAOPER.getId()
                || user.getLineRoleId() == LineRoleEnumType.VISAOPER_MANAGER.getId()) {
            List<Integer> serviceTypeList = new ArrayList<Integer>();
            serviceTypeList.add(LineSrviceEnumType.QZ.getId());
            serviceTypeList.add(LineSrviceEnumType.GGBZ.getId());

            for (LinesSrvice srvice : lineOrderVo.getLineOrderService()) {
                if (srvice.getServiceType() == LineSrviceEnumType.QZ.getId()
                        || srvice.getServiceType() == LineSrviceEnumType.GGBZ.getId()) {
                    dealService(tempServiceListDB, srvice);
                }
            }
        } else if (user.getLineRoleId() == LineRoleEnumType.FINANCE.getId()
                || user.getLineRoleId() == LineRoleEnumType.FINANCE_MANAGER.getId()) {
            for (LinesSrvice srvice : lineOrderVo.getLineOrderService()) {
                dealService(tempServiceListDB, srvice);
            }
        } else {
            for (LinesSrvice srvice : lineOrderVo.getLineOrderService()) {
                dealService(tempServiceListDB, srvice);
            }
        }

        lineNameListDao.deleteByOrderId(lineOrderVo.getOrderId());
        for (LineNameList custom : lineOrderVo.getLineCustomList()) {
            lineNameListDao.insert(custom);
        }

        // 记录操作日志
        Map<String, Object> temp = StringUtil.generateUpdateOperLog(lineOrderVo, lineOrder,
                serviceListDB, nameListDB);
        String log = (String) temp.get("log");
        if (!StringUtils.isEmpty(log)) {
            OperateLog operateLog = new OperateLog();
            operateLog.setUserId(user.getUserId());
            operateLog.setRoleId(user.getLineRoleId());
            operateLog.setOperateType(Constant.OPERATOR_TYPE_UPDATE);
            operateLog.setOperateDes(log);
            operateLog.setOrderSeq(lineOrder.getOrderSeq());
            operateLogDao.insert(operateLog);
        }

        serviceListDB = (Map<Integer, LinesSrvice>) temp.get("delService");
        if (serviceListDB != null) {
            for (Entry<Integer, LinesSrvice> entry : serviceListDB.entrySet()) {
                if (entry.getValue().getServiceType() != LineSrviceEnumType.BX.getId()) {
                    linesServiceDao.deleteByPrimaryKey("linessrvice", entry.getValue()
                            .getServiceId());
                }
            }
        }

        model.put("seachCountryName", bean.getSeachCountryName());
        model.put("salesman", bean.getSalesman());
        model.put("operator", bean.getOperator());
        model.put("seachYfhkStatus", bean.getSeachYfhkStatus());
        model.put("seachYshkStatus", bean.getSeachYshkStatus());
        model.put("searchStartDate", bean.getSearchStartDate());
        model.put("searchEndDate", bean.getSearchEndDate());
        model.put("seachCustomerName", bean.getSeachCustomerName());
        model.put("seachCustomerCompany", bean.getSeachCustomerCompany());
        model.put("seachNameList", bean.getSeachNameList());
        model.put("deptId", bean.getDeptId());

        return "redirect:list.do?page=" + currentPage + "&type=" + lineOrderVo.getType();
    }

    private void dealService(List<LinesSrvice> serviceListDB, LinesSrvice srvice) {
        for (LinesSrvice tempService : serviceListDB) {
            if (tempService.getServiceType() == srvice.getServiceType()) {
                linesServiceDao.updateByPrimaryKey("linessrvice", srvice);
                return;
            }
        }
        if (srvice.getServiceType() == LineSrviceEnumType.DJBS.getId()
                || srvice.getServiceType() == LineSrviceEnumType.DJSJDY.getId()) {
            linesServiceDao.deleteByPrimaryKey("linessrvice", srvice.getServiceId());
        }
        linesServiceDao.insert("linessrvice", srvice);
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

        LineProduct p = lineProductDao.selectByPrimaryKey(lineOrder.getLineProductId());
        if (lineOrder.type == 2) {
            if (lineOrder.nameListType == 1) {
                int qw = p.qw - lineOrder.nameListSize;
                p.setQw(qw);
            } else if (lineOrder.nameListType == 2) {
                int zw = p.zw - lineOrder.nameListSize;
                p.setZw(zw);
            } else if (lineOrder.nameListType == 3) {
                int yb = p.yb - lineOrder.nameListSize;
                p.setYb(yb);
            }
            p.setLeftSeatNum(p.seatNum - p.qw - p.zw - p.yb);
            lineProductDao.updateByPrimaryKey(p);
        }

        // 记录操作日志
        OperateLog operateLog = new OperateLog();
        operateLog.setUserId(user.getUserId());
        operateLog.setRoleId(user.getLineRoleId());
        operateLog.setOperateType(Constant.OPERATOR_TYPE_DELETE);
        operateLog.setOperateDes(StringUtil.generateDeleteOperLog(lineOrder));
        operateLog.setOrderSeq(lineOrder.getOrderSeq());
        operateLogDao.insert(operateLog);
        return "redirect:list.do?page=" + page + "&type=" + lineOrder.getType();
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
    public void exportSubmit(@ModelAttribute(Constant.SESSION_USER) User user,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            String type = request.getParameter("type");
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
            this.exportOrderData(toClient, type, year, month, salesman, yfhkStatus, yshkStatus,
                    customerId, company, operatorId, user);

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
    private void exportOrderData(OutputStream out, String type, String year, String month,
            String salesmanId, String yfhkStatus, String yshkStatus, String customerId,
            String company, String operatorId, User user) {
        String[] titles = { "团队编号", "单号", "线路名称", "出发日期", "结束日期", "操作员", "签证员", "销售", "客户", "客户公司",
                "报名人数", "应收账款", "已收账款", "未收账款", "毛利合计", "应付账款", "已付账款", "未付账款", "押金", "押金收退情况",
                "返佣", "返佣收退情况" };
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
        paraMap.put("type", type);
        paraMap.put("date", year + "-" + month + "%");
        paraMap.put("salesmanId", StringUtils.isEmpty(salesmanId) ? null : salesmanId);

        paraMap.put("customerId", StringUtils.isEmpty(customerId) ? null : customerId);
        paraMap.put("company", StringUtils.isEmpty(company) ? null : company);
        paraMap.put("operatorId", StringUtils.isEmpty(operatorId) ? null : operatorId);

        paraMap.put("yfhkStatus", StringUtils.isEmpty(yfhkStatus) ? null : yfhkStatus);
        paraMap.put("yshkStatus", StringUtils.isEmpty(yshkStatus) ? null : yshkStatus);

        if (LineRoleEnumType.SALESMAN.getId() == user.getLineRoleId()) {
            paraMap.put("salesmanId", user.getUserId());
        } else if (LineRoleEnumType.SALEMAN_MANAGER.getId() == user.getLineRoleId()) {
            paraMap.put("managerId", user.getUserId());
            paraMap.put("role", "salesmanId");
        } else if (LineRoleEnumType.OPERATOR_MANAGER.getId() == user.getLineRoleId()) {
            paraMap.put("operatorManagerId", user.getUserId());
        } else if (LineRoleEnumType.OPERATOR.getId() == user.getLineRoleId()) {
            paraMap.put("lineOperatorId", user.getUserId());
            paraMap.put("serviceOperatorId", user.getUserId());
        } else if (LineRoleEnumType.VISAOPER.getId() == user.getLineRoleId()) {
            paraMap.put("visaOperatorId", user.getUserId());
        } else if (LineRoleEnumType.SIGNOPERATOR.getId() == user.getLineRoleId()) {
            paraMap.put("signOperatorId", user.getUserId());
        }

        paraMap.put("userRoleId", user.getLineRoleId());

        List<LineOrder> ordersList = lineOrderDao.selectAllLineOrder(paraMap);

        if (ordersList != null && ordersList.size() > 0) {
            int i = 0;
            for (; i < ordersList.size(); i++) {
                LineOrder p = ordersList.get(i);
                Row row = s.createRow(i + 1);
                headerCell = row.createCell(0);
                headerCell.setCellValue(p.getLineProductOrderSeq());
                headerCell = row.createCell(1);
                headerCell.setCellValue(p.getOrderSeq());
                headerCell = row.createCell(2);
                headerCell.setCellValue(p.getLineProductName());
                headerCell = row.createCell(3);
                headerCell.setCellValue(DateUtil.format(p.getStartDate(), DateUtil.FORMAT_DATE));
                headerCell = row.createCell(4);
                headerCell.setCellValue(DateUtil.format(p.getEndDate(), DateUtil.FORMAT_DATE));
                headerCell = row.createCell(5);
                headerCell.setCellValue(p.getLineOperatorName());
                headerCell = row.createCell(6);
                headerCell.setCellValue(p.getSignOperatorName());
                headerCell = row.createCell(7);
                headerCell.setCellValue(p.getSalesmanName());
                headerCell = row.createCell(8);
                headerCell.setCellValue(p.getCustomerName());
                headerCell = row.createCell(9);
                headerCell.setCellValue(p.getCompany());
                headerCell = row.createCell(10);
                headerCell.setCellValue(p.getNameListSize());
                headerCell = row.createCell(11);
                headerCell.setCellValue(p.getPriceSum() == null ? "0" : p.getPriceSum().toString());
                headerCell = row.createCell(12);
                headerCell.setCellValue(p.getAlreadyGot() == null ? "0" : p.getAlreadyGot()
                        .toString());
                headerCell = row.createCell(13);
                headerCell.setCellValue(p.getNeedGot() == null ? "0" : p.getNeedGot().toString());
                headerCell = row.createCell(14);
                headerCell.setCellValue(p.getProfit() == null ? "0" : p.getProfit().toString());
                headerCell = row.createCell(15);
                headerCell.setCellValue(p.getPaidPriceSum() == null ? "0" : p.getPaidPriceSum()
                        .toString());
                headerCell = row.createCell(16);
                headerCell.setCellValue(p.getAlreadyPaidSum() == null ? "0" : p.getAlreadyPaidSum()
                        .toString());
                headerCell = row.createCell(17);
                headerCell.setCellValue(p.getNeedPaidSum() == null ? "0" : p.getNeedPaidSum()
                        .toString());
                headerCell = row.createCell(18);
                headerCell.setCellValue(p.getLineOrderDeposit() == null ? "0" : p
                        .getLineOrderDeposit().toString());
                headerCell = row.createCell(19);
                headerCell.setCellValue(VisaUtil.getLineOrderDepositStatusName(p
                        .getLineOrderDepositStatus() == null ? 0 : p.getLineOrderDepositStatus()));
                headerCell = row.createCell(20);
                headerCell.setCellValue(p.getCommission() == null ? "0" : p.getCommission()
                        .toString());
                headerCell = row.createCell(21);
                headerCell.setCellValue(VisaUtil.getLineOrderDepositStatusName(p
                        .getCommissionStatus() == null ? 0 : p.getCommissionStatus()));
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
