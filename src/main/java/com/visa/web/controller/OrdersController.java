package com.visa.web.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.visa.common.constant.Constant;
import com.visa.common.constant.PriceStatusEnum;
import com.visa.common.constant.RoleEnumType;
import com.visa.common.constant.YshkStatusEnum;
import com.visa.common.util.DateUtil;
import com.visa.common.util.PagingUtil;
import com.visa.common.util.StringUtil;
import com.visa.dao.AreaDao;
import com.visa.dao.CountryDao;
import com.visa.dao.CustomerDao;
import com.visa.dao.DepartmentDao;
import com.visa.dao.NoteDao;
import com.visa.dao.OrdersDao;
import com.visa.dao.ProductDao;
import com.visa.dao.SeqDao;
import com.visa.dao.UserDao;
import com.visa.po.Area;
import com.visa.po.Customer;
import com.visa.po.Note;
import com.visa.po.Orders;
import com.visa.po.Product;
import com.visa.po.User;
import com.visa.vo.Department;
import com.visa.vo.OrderSearchBean;
import com.visa.vo.SaleDto;

/**
 * @author user
 */
@Controller
@RequestMapping("/orders/*")
@SessionAttributes(Constant.SESSION_USER)
public class OrdersController {
    private final Log logger = LogFactory.getLog(this.getClass());

    @Resource
    private OrdersDao ordersDao;
    @Resource
    private CustomerDao customerDao;
    @Resource
    private ProductDao productDao;
    @Resource
    private UserDao userDao;
    @Resource
    private AreaDao areaDao;
    @Resource
    private CountryDao countryDao;
    @Resource
    private SeqDao seqDao;
    @Resource
    private DepartmentDao deptDao;
    @Resource
    private NoteDao noteDao;

    /**
     * @param user user
     * @param page page
     * @param model model
     * @param bean bean
     */
    @RequestMapping
    public void salecount(@ModelAttribute(Constant.SESSION_USER) User user, Integer page,
            ModelMap model, @ModelAttribute OrderSearchBean bean, HttpServletRequest request) {
    	
    	List<User> users = userDao.selectByRoleId(RoleEnumType.SALESMAN.getId());
    	
    	List<String> deptnames = new ArrayList<String>();
    	deptnames.add("广州部");
    	deptnames.add("沈阳部");
    	
    	String deptname = "";
        HttpSession session = request.getSession(false);
        if (session != null) {
	       	User loginUser = userDao.selectByPrimaryKey(user.getUserId());
	       	if(null != loginUser){
	       		deptname = loginUser.getDeptId();
	       	}
        }
        List<User> tempSalesmanList = null;
    	
        if(deptnames.contains(deptname)){
    		
    		tempSalesmanList = new ArrayList<User>();
    		for (User man : users) {
    			if (deptname.equals(man.getDeptId())) {
    				tempSalesmanList.add(man);
    			}
    		}
        	model.put("salesmanList", tempSalesmanList);
    		
        }else{
        	tempSalesmanList = users;
        }
    	
    	List<SaleDto> dtos = new ArrayList<SaleDto>();
    	
    	for(User u : tempSalesmanList){
    		
    		SaleDto dto = new SaleDto();
            Map<String, Object> paraMap = new HashMap<String, Object>();
            paraMap.put("salesmanId", u.getUserId());
            
    		dto.setUserId(u.getUserId());
    		dto.setUsername(u.getUserName());
    		
            String startDate = bean.getStartDate();
            String endDate = bean.getEndDate();
            String yfhkStatus = bean.getSeachYfhkStatus();
            String yshkStatus = bean.getSeachYshkStatus();

            if (StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
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
            paraMap.put("yfhkStatus", StringUtils.isEmpty(yfhkStatus) ? null : yfhkStatus);
            paraMap.put("yshkStatus", StringUtils.isEmpty(yshkStatus) ? null : yshkStatus);

            // 总计应收和总计应付汇总
            Map<String, Object> sumPrice = ordersDao.sumPrice(paraMap);
            if (sumPrice != null) {
                DecimalFormat fmt = new DecimalFormat("#,####,####,####.00");
                BigDecimal sumzjys = (BigDecimal) sumPrice.get("sumzjys");
                BigDecimal sumzjyf = (BigDecimal) sumPrice.get("sumzjyf");
                BigDecimal sumyshk = (BigDecimal) sumPrice.get("sumyshk");
                BigDecimal sumyfhk = (BigDecimal) sumPrice.get("sumyfhk");
                BigDecimal sumNameListSize = (BigDecimal) sumPrice.get("sumNameListSize");
                
                if (sumzjys != null && sumzjyf != null && sumyshk != null && sumyfhk != null) {
                    sumPrice.put("sumzjys", fmt.format(sumzjys));
                    sumPrice.put("sumzjyf", fmt.format(sumzjyf));
                    sumPrice.put("sumwshk", fmt.format(sumzjys.subtract(sumyshk)));
                    sumPrice.put("sumlr", fmt.format(sumzjys.subtract(sumzjyf)));
                    sumPrice.put("sumNameListSize", sumNameListSize.toString());
                    sumPrice.put("summaoli",  fmt.format(sumyshk.subtract(sumyfhk)));
                    
                    dto.setSumzjys(fmt.format(sumzjys));
                    dto.setSumzjyf(fmt.format(sumzjyf));
                    dto.setPromaoli(fmt.format(sumzjys.subtract(sumzjyf)));
                    dto.setActmaoli(fmt.format(sumyshk.subtract(sumyfhk)));
                    dto.setNums(sumNameListSize.toString());
                    dto.setSumyshk(fmt.format(sumyshk));
                    dto.setSumyfhk(fmt.format(sumyfhk));
                    
                    int flags1 = sumzjys.compareTo(sumyshk);
                    int flags2 = sumzjyf.compareTo(sumyfhk);
                    
                    if(flags1 == 0){
                    	dto.setShoustatus("已收全款");
                    }else{
                    	
                    	BigDecimal bs = new BigDecimal("0.00");
                    	if(bs.compareTo(sumyshk) == 0){
                    		dto.setShoustatus("未收款");
                    	}else{
                    		dto.setShoustatus("部分收款");
                    	}
                    	
                    }
                    if(flags2 == 0){
                    	dto.setFustatus("已付全款");
                    }else{
                    	
                    	BigDecimal bs = new BigDecimal("0.00");
                    	if(bs.compareTo(sumyfhk) == 0){
                    		dto.setFustatus("未付款");
                    	}else{
                    		dto.setFustatus("部分付款");
                    	}
                    }
                }
                dtos.add(dto);
            }
    	}
    	
    	model.addAttribute("dto", dtos);
    	model.addAttribute("searchBean", bean);
    }
    @RequestMapping
    public void list(@ModelAttribute(Constant.SESSION_USER) User user, Integer page,
    		ModelMap model, @ModelAttribute OrderSearchBean bean, HttpServletRequest request) {
    	
    	List<String> deptnames = new ArrayList<String>();
    	deptnames.add("广州部");
    	deptnames.add("沈阳部");
    	boolean flag = false;
    	String sessionDeptname = "";
    	
    	int recordCount = 0;
    	// 每页数据数
    	Map<String, Object> paraMap = new HashMap<String, Object>();
    	if (RoleEnumType.SALESMAN.getId() == user.getRoleId()) {
    		paraMap.put("salesmanId", user.getUserId());
    	} else if (RoleEnumType.MANAGER.getId() == user.getRoleId()) {
    		paraMap.put("managerId", user.getUserId());
    	} else if (RoleEnumType.OPERATOR.getId() == user.getRoleId()) {
    		paraMap.put("operatorId", user.getUserId());
    	} else if(RoleEnumType.FINANCE.getId() == user.getRoleId()){
            
            if (user.getUserId() != null) {
            	 User loginUser = userDao.selectByPrimaryKey(user.getUserId());
            	 if(null != loginUser){
            		 sessionDeptname = loginUser.getDeptId();
            	 }
            }
            
            if(null == user.getDeptId() || "".equals(user.getDeptId())){
            	user.setDeptId(sessionDeptname);
            }
    		
    		String deptname = user.getDeptId();
    		if(null != deptname && !"".equals(deptname)){
    			if(deptnames.contains(deptname)){
    				bean.setDeptId(deptname);
    				flag = true;
    			}
    		}
    		
    	}
    	
    	String seachCountryName = bean.getSeachCountryName();
    	String customerName = bean.getSeachCustomerName();
    	String companyName = bean.getSeachCustomerCompany();
    	String nameList = bean.getSeachNameList();
    	String startDate = bean.getStartDate();
    	String endDate = bean.getEndDate();
    	String yfhkStatus = bean.getSeachYfhkStatus();
    	String yshkStatus = bean.getSeachYshkStatus();
    	String salesman = bean.getSalesman();
    	String operator = bean.getOperator();
    	String orderSeq = bean.getOrderSeq();
    	String deptId = bean.getDeptId();
    	String orderType = bean.getOrderType();
    	String operatorDes = bean.getSeachOperatorDes();
    	String fapiao = bean.getFapiao();
    	String baoxian = bean.getBaoxian();
    	
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
    	
    	paraMap.put("operator", "like");
    	paraMap.put("countryName", StringUtils.isEmpty(seachCountryName) ? null : "%"
    			+ seachCountryName + "%");
    	paraMap.put("customerName", StringUtils.isEmpty(customerName) ? null : "%" + customerName
    			+ "%");
    	paraMap.put("companyName", StringUtils.isEmpty(companyName) ? null : "%" + companyName
    			+ "%");
    	paraMap.put("nameList", StringUtils.isEmpty(nameList) ? null : "%" + nameList + "%");
    	paraMap.put("startDate", StringUtils.isEmpty(startDate) ? null : startDate);
    	paraMap.put("endDate", StringUtils.isEmpty(endDate) ? null : endDate);
    	paraMap.put("yfhkStatus", StringUtils.isEmpty(yfhkStatus) ? null : yfhkStatus);
    	paraMap.put("yshkStatus", StringUtils.isEmpty(yshkStatus) ? null : yshkStatus);
    	paraMap.put("salesmanForSearch", StringUtils.isEmpty(salesman) ? null : salesman);
    	paraMap.put("operatorForSearch", StringUtils.isEmpty(operator) ? null : operator);
    	paraMap.put("orderSeq", StringUtils.isEmpty(orderSeq) ? null : orderSeq);
    	paraMap.put("deptId", StringUtils.isEmpty(deptId) ? null : deptId);
    	paraMap.put("orderType", StringUtils.isEmpty(orderType) ? null : orderType);
    	paraMap.put("operatorDes", StringUtils.isEmpty(operatorDes) ? null : operatorDes);
    	paraMap.put("fapiao", StringUtils.isEmpty(fapiao) ? null : fapiao);
    	paraMap.put("baoxian", StringUtils.isEmpty(baoxian) ? null : baoxian);
    	
    	// 记录总条数
    	recordCount = ordersDao.count(paraMap);
    	
    	// 总计应收和总计应付汇总
    	Map<String, Object> sumPrice = ordersDao.sumPrice(paraMap);
    	if (sumPrice != null) {
    		DecimalFormat fmt = new DecimalFormat("#,####,####,####.00");
    		BigDecimal sumzjys = (BigDecimal) sumPrice.get("sumzjys");
    		BigDecimal sumzjyf = (BigDecimal) sumPrice.get("sumzjyf");
    		BigDecimal sumyshk = (BigDecimal) sumPrice.get("sumyshk");
    		BigDecimal sumyfhk = (BigDecimal) sumPrice.get("sumyfhk");
    		BigDecimal sumNameListSize = (BigDecimal) sumPrice.get("sumNameListSize");
     		BigDecimal baoxians = (BigDecimal) sumPrice.get("baoxiansum");
    		sumPrice.put("baoxian", fmt.format(baoxians));
    		
    		if (sumzjys != null && sumzjyf != null && sumyshk != null && sumyfhk != null) {
    			sumPrice.put("sumzjys", fmt.format(sumzjys));
    			sumPrice.put("sumzjyf", fmt.format(sumzjyf));
    			sumPrice.put("sumwshk", fmt.format(sumzjys.subtract(sumyshk)));
    			sumPrice.put("sumlr", fmt.format(sumzjys.subtract(sumzjyf)));
    			sumPrice.put("sumNameListSize", sumNameListSize.toString());
    			sumPrice.put("summaoli",  fmt.format(sumyshk.subtract(sumyfhk)));
    		}
    	}
    	
    	int[] recordRange = PagingUtil.addPagingSupport(Constant.PAGE_COUNT, recordCount, page,
    			Constant.PAGE_OFFSET, model);
    	paraMap.put("begin", recordRange[0]);
    	paraMap.put("pageCount", Constant.PAGE_COUNT);
    	
    	List<Orders> orderList = ordersDao.selectByPage(paraMap);
    	
    	model.addAttribute("orderList", orderList);
    	List<User> salesmanList = userDao.selectByRoleId(RoleEnumType.SALESMAN.getId());
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
    	List<User> operatorList = userDao.selectByRoleId(RoleEnumType.OPERATOR.getId());
    	
    	List<User> tempOperatorList = null;
    	
    	if(flag){
    		
        	if (!StringUtils.isEmpty(deptId)) {
        		tempOperatorList = new ArrayList<User>();
        		for (User man : operatorList) {
        			if (deptId.equals(man.getDeptId())) {
        				tempOperatorList.add(man);
        			}
        		}
        	} 
    	}else{
    		tempOperatorList = operatorList;
    	}
    	
    	model.put("operatorList", tempOperatorList);
    	List<Department> deptList = null;
    	if(flag){
    		deptList = new ArrayList<Department>();
    		Department dment = new Department();
    		dment.setName(deptId);
    		deptList.add(dment);
    	}else{
    		deptList = deptDao.selectAll();
    	}
    	model.put("deptList", deptList);
    	model.addAttribute("role", user.getRoleId());
     	model.addAttribute("searchBean", bean);
    	model.addAttribute("sumPrice", sumPrice);
    }
    
    @RequestMapping
    public void listmanager(@ModelAttribute(Constant.SESSION_USER) User user, Integer page,
    		ModelMap model, @ModelAttribute OrderSearchBean bean) {
    	int recordCount = 0;
    	// 每页数据数
    	Map<String, Object> paraMap = new HashMap<String, Object>();
    	if (RoleEnumType.SALESMAN.getId() == user.getRoleId()) {
    		paraMap.put("salesmanId", user.getUserId());
    	} else if (RoleEnumType.MANAGER.getId() == user.getRoleId()) {
    		paraMap.put("managerId", user.getUserId());
    	} else if (RoleEnumType.OPERATOR.getId() == user.getRoleId()) {
    		paraMap.put("operatorId", user.getUserId());
    	}
    	
    	String seachCountryName = bean.getSeachCountryName();
    	String customerName = bean.getSeachCustomerName();
    	String companyName = bean.getSeachCustomerCompany();
    	String nameList = bean.getSeachNameList();
    	String startDate = "2015-01-01";
    	bean.setStartDate(startDate);
    	String endDate = bean.getEndDate();
    	String yfhkStatus =  "3" ;//bean.getSeachYfhkStatus();
    	bean.setSeachYfhkStatus("3");
    	String yshkStatus =  "3" ;//bean.getSeachYshkStatus();
    	bean.setSeachYshkStatus("3");
    	String salesman = bean.getSalesman();
    	String operator = bean.getOperator();
    	String orderSeq = bean.getOrderSeq();
    	String deptId = bean.getDeptId();
    	String orderType = bean.getOrderType();
    	String operatorDes = bean.getSeachOperatorDes();
    	String fapiao = bean.getFapiao();
    	String baoxian = bean.getBaoxian();
    	
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		endDate = sdf.format(c.getTime());
		bean.setEndDate(endDate);
/*    	
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
    	*/
    	paraMap.put("operator", "like");
    	paraMap.put("countryName", StringUtils.isEmpty(seachCountryName) ? null : "%"
    			+ seachCountryName + "%");
    	paraMap.put("customerName", StringUtils.isEmpty(customerName) ? null : "%" + customerName
    			+ "%");
    	paraMap.put("companyName", StringUtils.isEmpty(companyName) ? null : "%" + companyName
    			+ "%");
    	paraMap.put("nameList", StringUtils.isEmpty(nameList) ? null : "%" + nameList + "%");
    	paraMap.put("startDate", StringUtils.isEmpty(startDate) ? null : startDate);
    	paraMap.put("endDate", StringUtils.isEmpty(endDate) ? null : endDate);
    	paraMap.put("yfhkStatus", StringUtils.isEmpty(yfhkStatus) ? null : yfhkStatus);
    	paraMap.put("yshkStatus", StringUtils.isEmpty(yshkStatus) ? null : yshkStatus);
    	paraMap.put("salesmanForSearch", StringUtils.isEmpty(salesman) ? null : salesman);
    	paraMap.put("operatorForSearch", StringUtils.isEmpty(operator) ? null : operator);
    	paraMap.put("orderSeq", StringUtils.isEmpty(orderSeq) ? null : orderSeq);
    	paraMap.put("deptId", StringUtils.isEmpty(deptId) ? null : deptId);
    	paraMap.put("orderType", StringUtils.isEmpty(orderType) ? null : orderType);
    	paraMap.put("operatorDes", StringUtils.isEmpty(operatorDes) ? null : operatorDes);
    	paraMap.put("fapiao", StringUtils.isEmpty(fapiao) ? null : fapiao);
    	paraMap.put("baoxian", StringUtils.isEmpty(baoxian) ? null : baoxian);
    	paraMap.put("spStatus", 0);
    	
    	// 记录总条数
    	recordCount = ordersDao.count(paraMap);
    	
    	// 总计应收和总计应付汇总
    	Map<String, Object> sumPrice = ordersDao.sumPrice(paraMap);
    	if (sumPrice != null) {
    		DecimalFormat fmt = new DecimalFormat("#,####,####,####.00");
    		BigDecimal sumzjys = (BigDecimal) sumPrice.get("sumzjys");
    		BigDecimal sumzjyf = (BigDecimal) sumPrice.get("sumzjyf");
    		BigDecimal sumyshk = (BigDecimal) sumPrice.get("sumyshk");
    		BigDecimal sumyfhk = (BigDecimal) sumPrice.get("sumyfhk");
    		BigDecimal sumNameListSize = (BigDecimal) sumPrice.get("sumNameListSize");
    		if (sumzjys != null && sumzjyf != null && sumyshk != null) {
    			sumPrice.put("sumzjys", fmt.format(sumzjys));
    			sumPrice.put("sumzjyf", fmt.format(sumzjyf));
    			sumPrice.put("sumwshk", fmt.format(sumzjys.subtract(sumyshk)));
    			sumPrice.put("sumlr", fmt.format(sumzjys.subtract(sumzjyf)));
    			sumPrice.put("sumNameListSize", sumNameListSize.toString());
    			sumPrice.put("summaoli",  fmt.format(sumyshk.subtract(sumyfhk)));
    		}
    	}
    	
    	int[] recordRange = PagingUtil.addPagingSupport(Constant.PAGE_COUNT, recordCount, page,
    			Constant.PAGE_OFFSET, model);
    	paraMap.put("begin", recordRange[0]);
    	paraMap.put("pageCount", Constant.PAGE_COUNT);
    	
    	List<Orders> orderList = ordersDao.selectByPage(paraMap);
    	
    	model.addAttribute("orderList", orderList);
    	List<User> salesmanList = userDao.selectByRoleId(RoleEnumType.SALESMAN.getId());
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
    	List<User> operatorList = userDao.selectByRoleId(RoleEnumType.OPERATOR.getId());
    	model.put("operatorList", operatorList);
    	List<Department> deptList = deptDao.selectAll();
    	model.put("deptList", deptList);
    	model.addAttribute("role", user.getRoleId());
    	model.addAttribute("searchBean", bean);
    	model.addAttribute("sumPrice", sumPrice);
    }
    
    @RequestMapping
    public void listapproved(@ModelAttribute(Constant.SESSION_USER) User user, Integer page,
    		ModelMap model, @ModelAttribute OrderSearchBean bean) {
    	int recordCount = 0;
    	// 每页数据数
    	Map<String, Object> paraMap = new HashMap<String, Object>();
    	if (RoleEnumType.SALESMAN.getId() == user.getRoleId()) {
    		paraMap.put("salesmanId", user.getUserId());
    	} else if (RoleEnumType.MANAGER.getId() == user.getRoleId()) {
    		paraMap.put("managerId", user.getUserId());
    	} else if (RoleEnumType.OPERATOR.getId() == user.getRoleId()) {
    		paraMap.put("operatorId", user.getUserId());
    	}
    	
    	String seachCountryName = bean.getSeachCountryName();
    	String customerName = bean.getSeachCustomerName();
    	String companyName = bean.getSeachCustomerCompany();
    	String nameList = bean.getSeachNameList();
    	String startDate = "2015-01-01";
    	bean.setStartDate(startDate);
    	String endDate = bean.getEndDate();
    	String yfhkStatus =  "3" ;//bean.getSeachYfhkStatus();
    	bean.setSeachYfhkStatus("3");
    	String yshkStatus =  "3" ;//bean.getSeachYshkStatus();
    	bean.setSeachYshkStatus("3");
    	String salesman = bean.getSalesman();
    	String operator = bean.getOperator();
    	String orderSeq = bean.getOrderSeq();
    	String deptId = bean.getDeptId();
    	String orderType = bean.getOrderType();
    	String operatorDes = bean.getSeachOperatorDes();
    	String fapiao = bean.getFapiao();
    	
    	Calendar c = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	endDate = sdf.format(c.getTime());
    	bean.setEndDate(endDate);
    	/*    	
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
    	 */
    	paraMap.put("operator", "like");
    	paraMap.put("countryName", StringUtils.isEmpty(seachCountryName) ? null : "%"
    			+ seachCountryName + "%");
    	paraMap.put("customerName", StringUtils.isEmpty(customerName) ? null : "%" + customerName
    			+ "%");
    	paraMap.put("companyName", StringUtils.isEmpty(companyName) ? null : "%" + companyName
    			+ "%");
    	paraMap.put("nameList", StringUtils.isEmpty(nameList) ? null : "%" + nameList + "%");
    	paraMap.put("startDate", StringUtils.isEmpty(startDate) ? null : startDate);
    	paraMap.put("endDate", StringUtils.isEmpty(endDate) ? null : endDate);
    	paraMap.put("yfhkStatus", StringUtils.isEmpty(yfhkStatus) ? null : yfhkStatus);
    	paraMap.put("yshkStatus", StringUtils.isEmpty(yshkStatus) ? null : yshkStatus);
    	paraMap.put("salesmanForSearch", StringUtils.isEmpty(salesman) ? null : salesman);
    	paraMap.put("operatorForSearch", StringUtils.isEmpty(operator) ? null : operator);
    	paraMap.put("orderSeq", StringUtils.isEmpty(orderSeq) ? null : orderSeq);
    	paraMap.put("deptId", StringUtils.isEmpty(deptId) ? null : deptId);
    	paraMap.put("orderType", StringUtils.isEmpty(orderType) ? null : orderType);
    	paraMap.put("operatorDes", StringUtils.isEmpty(operatorDes) ? null : operatorDes);
    	paraMap.put("fapiao", StringUtils.isEmpty(fapiao) ? null : fapiao);
    	paraMap.put("spStatus", 1);
    	
    	// 记录总条数
    	recordCount = ordersDao.count(paraMap);
    	
    	// 总计应收和总计应付汇总
    	Map<String, Object> sumPrice = ordersDao.sumPrice(paraMap);
    	if (sumPrice != null) {
    		DecimalFormat fmt = new DecimalFormat("#,####,####,####.00");
    		BigDecimal sumzjys = (BigDecimal) sumPrice.get("sumzjys");
    		BigDecimal sumzjyf = (BigDecimal) sumPrice.get("sumzjyf");
    		BigDecimal sumyshk = (BigDecimal) sumPrice.get("sumyshk");
    		BigDecimal sumyfhk = (BigDecimal) sumPrice.get("sumyfhk");
    		BigDecimal sumNameListSize = (BigDecimal) sumPrice.get("sumNameListSize");
    		if (sumzjys != null && sumzjyf != null && sumyshk != null) {
    			sumPrice.put("sumzjys", fmt.format(sumzjys));
    			sumPrice.put("sumzjyf", fmt.format(sumzjyf));
    			sumPrice.put("sumwshk", fmt.format(sumzjys.subtract(sumyshk)));
    			sumPrice.put("sumlr", fmt.format(sumzjys.subtract(sumzjyf)));
    			sumPrice.put("sumNameListSize", sumNameListSize.toString());
    			sumPrice.put("summaoli",  fmt.format(sumyshk.subtract(sumyfhk)));
    		}
    	}
    	
    	int[] recordRange = PagingUtil.addPagingSupport(Constant.PAGE_COUNT, recordCount, page,
    			Constant.PAGE_OFFSET, model);
    	paraMap.put("begin", recordRange[0]);
    	paraMap.put("pageCount", Constant.PAGE_COUNT);
    	
    	List<Orders> orderList = ordersDao.selectByPage(paraMap);
    	
    	model.addAttribute("orderList", orderList);
    	List<User> salesmanList = userDao.selectByRoleId(RoleEnumType.SALESMAN.getId());
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
    	List<User> operatorList = userDao.selectByRoleId(RoleEnumType.OPERATOR.getId());
    	model.put("operatorList", operatorList);
    	List<Department> deptList = deptDao.selectAll();
    	model.put("deptList", deptList);
    	model.addAttribute("role", user.getRoleId());
    	model.addAttribute("searchBean", bean);
    	model.addAttribute("sumPrice", sumPrice);
    }

    @RequestMapping
    @ResponseBody
    public String ajaxSubmit(@RequestParam("vstring") String vstring, HttpServletResponse response)throws Exception{
    	
        if("".equals(vstring)){
        	return "no";
        }else{
        	
        	String[] orderSeq = vstring.split(",");
        	
        	for(String seq : orderSeq){
        		
        		Orders order = ordersDao.selectByOrderSeq(seq);
        		order.setSpStatus("1");
        		ordersDao.updateByOrderSeq(order);
        	}
        	return "yes";
        }
    	
    }
    
    /**
     * @param user user
     * @param model model
     */
    @RequestMapping
    public void note(@ModelAttribute(Constant.SESSION_USER) User user,String salename ,int page, ModelMap model) {

    	List<Note> notes = null;
    	int recordCount = 0;
    	
        Map<String, Object> paraMap = new HashMap<String, Object>();
    	recordCount = noteDao.count();
    	
    	int[] recordRange = PagingUtil.addPagingSupport(Constant.PAGE_COUNT, recordCount, page,
    			Constant.PAGE_OFFSET, model);
    	paraMap.put("begin", recordRange[0]);
    	paraMap.put("pageCount", Constant.PAGE_COUNT);
    	
        List<User> salesmanList = new ArrayList<User>();
        if(null == salename || "".equals(salename)){
        	notes = noteDao.selectAll(paraMap);
        }else{
        	paraMap.put("salename", salename);
        	notes = noteDao.selectBySaleId(paraMap);
        }
        
        salesmanList.addAll(userDao.selectByRoleId(RoleEnumType.SALESMAN.getId()));
        model.put("salesmanList", salesmanList);
    	model.put("notes", notes);
    	model.put("salename", salename);
    }
    /**
     * @param user user
     * @param model model
     */
    @RequestMapping
    public void add(@ModelAttribute(Constant.SESSION_USER) User user, Integer type, ModelMap model) {
    	String userId = user.getUserId();
    	List<Customer> customerList = customerDao.selectAllBySalesmanId(userId);
    	List<Area> areaList = areaDao.selectAllArea();
    	model.put("areaList", areaList);
    	model.put("customerList", customerList);
    	if (type == null) {
    		model.put("type", 0);
    	} else {
    		model.put("type", type);
    	}
    }

    /**
     * @param user user
     * @param orders orders
     * @param model model
     * @return logout
     */
    @RequestMapping
    public String addSubmit(@ModelAttribute(Constant.SESSION_USER) User user,
            @ModelAttribute("orders") Orders orders, ModelMap model) {
        String userId = user.getUserId();
        String userName = user.getUserName();
        if (RoleEnumType.SALESMAN.getId() == user.getRoleId()) {
            // Product product =
            // productDao.selectByPrimaryKey(orders.getProductId());
            orders.setSalesmanId(userId);
            orders.setSalesmanName(userName);
            orders.setOrderDate(new Date());
            // 总计应付款=应付单价(产品价格)*客人数量+其他支出款
            // orders.setPriceZjyf(product.getPrice()
            // .multiply(new
            // BigDecimal(orders.getNameListSize())).add(orders.getPriceQtzc()));
            // 总计应收款=应收单价*客人数量+其他应收款
            // orders.setPriceZjys(orders.getPriceYsdj()
            // .multiply(new
            // BigDecimal(orders.getNameListSize())).add(orders.getPriceQtys()));
            // 毛利=总计应收-其他支出款
            // orders.setGrossProfit(orders.getPriceZjys().subtract(orders.getPriceQtzc()));
            orders.setYfhkStatus(PriceStatusEnum.NOTYET.getId());
            orders.setYshkStatus(YshkStatusEnum.NOTYET.getId());
        }
        Customer customer = customerDao.selectByPrimaryKey(orders.getCustomerId());
        if (customer != null) {
            orders.setCustomerId(customer.getCustomerId());
            orders.setCustomerName(customer.getCustomerName());
        }
        Product product = productDao.selectByPrimaryKey(orders.getProductId());
        if (product != null) {
            orders.setProductId(product.getProductId());
            orders.setProductName(product.getProductName());
        }
        orders.setStatus(0);
        orders.setPtTime(new Date());
        int orderSeq = seqDao.select("order");
        orders.setSpStatus("0");
        orders.setFapiao("2");
        String prefix = StringUtil.paddingZeroToLeft(String.valueOf(orderSeq), 6);
        orders.setOrderSeq(prefix);
        ordersDao.insert(orders);

        return "redirect:list.do?page=1";
    }

    /**
     * @param user user
     * @param orderId orderId
     * @param model model
     */
    @RequestMapping
    public void edit(@ModelAttribute(Constant.SESSION_USER) User user, int orderId, ModelMap model,
            Integer currentPage, @ModelAttribute OrderSearchBean bean) {
        Orders orders = ordersDao.selectByPrimaryKey(orderId);
        String userId = user.getUserId();
        List<Customer> customerList = customerDao.selectAllBySalesmanId(userId);
        Product product = null;
        if (orders.getProductId() != null) {
            product = productDao.selectByPrimaryKey(orders.getProductId());
        }
        List<User> operatorList = userDao.selectByRoleId(RoleEnumType.OPERATOR.getId());
        if (user.getRoleId() == RoleEnumType.ADMIN.getId()) {
            customerList = customerDao.selectAllCustomer();
        } else {
            customerList = customerDao.selectAllBySalesmanId(userId);
        }
        model.put("areaList", areaDao.selectAllArea());
        if (product != null) {
            model.put("countryList", countryDao.selectByContinentId(product.getContinentId()));
            model.put("productList", productDao.searchByCountryId(product.getCountryId()));
        }
        model.put("operatorList", operatorList);
        model.put("customerList", customerList);
        model.put("product", product);
        if (orders.getNameList() != null) {
            List<Map<String, String>> customList = Lists.newArrayList();
            String[] customArray = orders.getNameList().split(",");
            for (String customStr : customArray) {
                Iterator<String> it = Splitter.on("_").split(customStr).iterator();
                if (!it.hasNext()) {
                    continue;
                }
                Map<String, String> custom = Maps.newHashMap();
                if (it.hasNext()) {
                    custom.put("name", it.next());
                }
                if (it.hasNext()) {
                    custom.put("contact", it.next());
                }
                if (it.hasNext()) {
                    custom.put("addr", it.next());
                }
                customList.add(custom);
            }
            model.put("customList", customList);
            model.put("nameList", orders.getNameList());
        }
        model.put("page", currentPage);
        model.addAttribute("orders", orders);
        
        BigDecimal fukuang =  orders.getPriceYfhk();
        BigDecimal yikuang = orders.getPriceYshk();
        
        if(null != fukuang && null != yikuang){
            DecimalFormat df = new DecimalFormat("#.00");
            double maoli = yikuang.subtract(fukuang).doubleValue();
            model.addAttribute("maoli", df.format(maoli));
        }else{
        	model.addAttribute("maoli", 0);
        }
        
        model.addAttribute("searchBean", bean);
    }

    /**
     * 订单修改功能，销售、经理、操作员、财务共用
     * 
     * @param user user
     * @param updateOrders updateOrders
     * @param model model
     * @return logout
     */
    @RequestMapping
    public String update(@ModelAttribute(Constant.SESSION_USER) User user,
            @ModelAttribute("orders") Orders updateOrders, ModelMap model, Integer page,
            @ModelAttribute OrderSearchBean bean) {
        Orders orders = ordersDao.selectByPrimaryKey(updateOrders.getOrderId());

        if (RoleEnumType.SALESMAN.getId() == user.getRoleId()
                || RoleEnumType.ADMIN.getId() == user.getRoleId() || RoleEnumType.FINANCE.getId() == user.getRoleId()) {
            Product product = null;
            if (updateOrders.getProductId() != null) {
                product = productDao.selectByPrimaryKey(updateOrders.getProductId());
            }
            // orders.setPriceYsdj(updateOrders.getPriceYsdj());
            // orders.setPriceQtzc(updateOrders.getPriceQtzc());
            // orders.setPriceQtys(updateOrders.getPriceQtys());
            // orders.setPriceZjyf(updateOrders.getPriceZjyf());
            // orders.setPriceZjys(updateOrders.getPriceZjys());
            // orders.setGrossProfit(updateOrders.getGrossProfit());
            Customer customer = customerDao.selectByPrimaryKey(updateOrders.getCustomerId());
            if (customer != null) {
                orders.setCustomerId(customer.getCustomerId());
                orders.setCustomerName(customer.getCustomerName());
            }
            if (product != null) {
                orders.setProductId(product.getProductId());
                orders.setProductName(product.getProductName());
            }
            orders.setNameList(updateOrders.getNameList());
            orders.setNameListSize(updateOrders.getNameListSize());
        }
        // 经理指定操作员
        if (RoleEnumType.MANAGER.getId() == user.getRoleId()
                || RoleEnumType.ADMIN.getId() == user.getRoleId()) {
            orders.setOperatorId(updateOrders.getOperatorId());
        }
        // 操作员录入：送签日期、送签员、订单状态
        // if (RoleEnumType.OPERATOR.getId() == user.getRoleId() ||
        // RoleEnumType.ADMIN.getId() == user.getRoleId()) {
        orders.setSignDate(updateOrders.getSignDate());
        orders.setSignOperatorName(updateOrders.getSignOperatorName());
        orders.setOperatorDes(updateOrders.getOperatorDes());
        orders.setOperatorRemark(updateOrders.getOperatorRemark());
        // }

        if (RoleEnumType.FINANCE.getId() == user.getRoleId()
                || RoleEnumType.ADMIN.getId() == user.getRoleId()) {
            // 财务确认订单全部完款后将订单状态设置为已完成状态
            if (updateOrders.getYfhkStatus() == PriceStatusEnum.DONE.getId()) {
                orders.setStatus(1);
            }
            
            Note note = new Note();
            String content = user.getUserName() + "把";
            Map<Integer ,String> map = new HashMap<Integer, String>();
            map.put(1, "未付款");
            map.put(2, "部分付款");
            map.put(3, "已付款");
            
            
            //修改的日记信息
            if(orders.getYfhkStatus() != updateOrders.getYfhkStatus()){
            	content += "收款状态从‘" + map.get(orders.getYfhkStatus()) + "’改成了‘" + map.get(updateOrders.getYfhkStatus()) + ", ";
            }
            
            if(orders.getPriceYfhk() == null || !orders.getPriceYfhk().equals(updateOrders.getPriceYfhk())){
            	content += "已收货款从" + (orders.getPriceYfhk() == null?0:orders.getPriceYfhk().doubleValue()) + "改成了" + updateOrders.getPriceYfhk().doubleValue() + ",  ";
            }
            if(orders.getYshkStatus() != updateOrders.getYshkStatus()){
            	content += "付款状态从‘" + map.get(orders.getYshkStatus()) + "’改成了‘" + map.get(updateOrders.getYshkStatus()) + ", ";
            }
            
            if(orders.getPriceYshk() == null || !orders.getPriceYshk().equals(updateOrders.getPriceYshk())){
            	content += "已付货款从" + (orders.getPriceYshk() == null?0:orders.getPriceYshk().doubleValue()) + "改成了" + updateOrders.getPriceYshk().doubleValue();
            }
            
            if((user.getUserName() + "把").equals(content)){
            	note = null;
            }else{
            	
            	note.setContent(content);
            	note.setOp(user.getUserName());
            	note.setOrderSeq(orders.getOrderSeq());
            	noteDao.insert(note);
            }
            
            // 财务人员录入：收款状态和已收货款
            orders.setYfhkStatus(updateOrders.getYfhkStatus());
            orders.setPriceYfhk(updateOrders.getPriceYfhk());
            orders.setYfhkRemark(updateOrders.getYfhkRemark());
            // 财务人员录入：付款状态和已付货款
            orders.setYshkStatus(updateOrders.getYshkStatus());
            orders.setPriceYshk(updateOrders.getPriceYshk());
            orders.setYshkRemark(updateOrders.getYshkRemark());
            
            // 财务人员可以修改其他应收、其他应付
            orders.setPriceYsdj(updateOrders.getPriceYsdj());
            orders.setPriceQtzc(updateOrders.getPriceQtzc());
            orders.setPriceQtys(updateOrders.getPriceQtys());
            orders.setPriceQtysBz(updateOrders.getPriceQtysBz());
            orders.setPriceQtzcBz(updateOrders.getPriceQtzcBz());

            // MODIFY：财务允许修改总计应收
            orders.setPriceZjys(updateOrders.getPriceZjys());

            orders.setPriceZjyf(updateOrders.getPriceZjyf());
            orders.setGrossProfit(updateOrders.getGrossProfit());
        }

        if (RoleEnumType.OPERATOR.getId() == user.getRoleId()) {
            orders.setPriceBxys(updateOrders.getPriceBxys());
            orders.setPriceBxyf(updateOrders.getPriceBxyf());
            
            orders.setPriceQtzc(updateOrders.getPriceQtzc());

            // MODIFY：财务允许修改总计应收
            orders.setPriceZjys(updateOrders.getPriceZjys());
            orders.setPriceZjyf(updateOrders.getPriceZjyf());
            orders.setGrossProfit(updateOrders.getGrossProfit());
            // orders.setPriceZjys(orders.getPriceZjys().add(updateOrders.getPriceBxys()));
            // orders.setPriceZjyf(orders.getPriceZjyf().add(updateOrders.getPriceBxyf()));
        }

        orders.setDes(updateOrders.getDes());
        orders.setCzdes(updateOrders.getCzdes());
        orders.setCwdes(updateOrders.getCwdes());
        orders.setFapiao(updateOrders.getFapiao());
        orders.setBaoxian(updateOrders.getBaoxian());
        orders.setBaoxianDay(updateOrders.getBaoxianDay());
        orders.setBaoxianPrice(updateOrders.getBaoxianPrice());
        orders.setRenzheng(updateOrders.getRenzheng());
        orders.setKuaidi(updateOrders.getKuaidi());
        orders.setTax(updateOrders.getTax());
        orders.setFankuan(updateOrders.getFankuan());
        orders.setQita(updateOrders.getQita());
        orders.setQitaComments(updateOrders.getQitaComments());
        orders.setRenzhengRemark(updateOrders.getRenzhengRemark());
        orders.setPtTime(new Date());
        orders.setFkstatusdes(updateOrders.getFkstatusdes());
        orders.setSkstatusdes(updateOrders.getSkstatusdes());
        
        String infostatus = updateOrders.getInfostatus();
        String substatus = updateOrders.getSubstatus();
        
        if(null != infostatus && !"1".equals(infostatus)){
        	
        	if("5".equals(substatus) || "6".equals(substatus)){
        		orders.setFinaceman(user.getUserId());
        	}
        	orders.setInfostatus(infostatus);
        	orders.setSubstatus(substatus);
        }else{
        	orders.setInfostatus(null);
        	orders.setSubstatus(null);
        }
        
        ordersDao.updateByPrimaryKey(orders);

        model.put("seachCountryName", bean.getSeachCountryName());
        model.put("salesman", bean.getSalesman());
        model.put("operator", bean.getOperator());
        model.put("seachYfhkStatus", bean.getSeachYfhkStatus());
        model.put("seachYshkStatus", bean.getSeachYshkStatus());
        model.put("startDate", bean.getStartDate());
        model.put("endDate", bean.getEndDate());
        model.put("seachCustomerName", bean.getSeachCustomerName());
        model.put("seachCustomerCompany", bean.getSeachCustomerCompany());
        model.put("seachNameList", bean.getSeachNameList());
        return "redirect:list.do?page=" + page;
    }

    /**
     * @param orderId orderId
     * @return String
     */
    @RequestMapping
    public String delete(int orderId, Integer page) {
        ordersDao.deleteByPrimaryKey(orderId);
        return "redirect:list.do?page=" + page;
    }

    /**
     */
    @RequestMapping
    public void export(ModelMap model,HttpServletRequest request) {
    	
    	List<String> deptnames = new ArrayList<String>();
    	deptnames.add("广州部");
    	deptnames.add("沈阳部");
    	
    	String deptname = "";
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute(Constant.SESSION_USER);
	       	User loginUser = userDao.selectByPrimaryKey(user.getUserId());
	       	if(null != loginUser){
	       		deptname = loginUser.getDeptId();
	       	}
        }
        List<Department> deptList = null;
        List<User> salesmanList = userDao.selectByRoleId(RoleEnumType.SALESMAN.getId());
        List<User> operatorList = userDao.selectByRoleId(RoleEnumType.OPERATOR.getId());
        List<User> tempSalesmanList = null;
    	List<User> tempOperatorList = null;
    	
        if(deptnames.contains(deptname)){
        	deptList = new ArrayList<Department>();
    		Department dment = new Department();
    		dment.setName(deptname);
    		deptList.add(dment);
    		model.put("departmentList", deptList);
    		
    		tempSalesmanList = new ArrayList<User>();
    		for (User man : salesmanList) {
    			if (deptname.equals(man.getDeptId())) {
    				tempSalesmanList.add(man);
    			}
    		}
        	model.put("salesmanList", tempSalesmanList);
    		
    		tempOperatorList = new ArrayList<User>();
    		for (User man : operatorList) {
    			if (deptname.equals(man.getDeptId())) {
    				tempOperatorList.add(man);
    			}
    		}
    		model.put("operatorList", tempOperatorList);
    		
        }else{
        	model.put("departmentList", deptDao.selectAll());
            model.put("salesmanList", salesmanList);
            model.put("operatorList", operatorList);
        }
        model.put("customerList", customerDao.selectAllCustomer());
        model.put("companyList", customerDao.selectCompany());
        model.put("yearList", ordersDao.selectOrderYears());
        model.put("monthList", ordersDao.selectOrderMonths());
    }

    /**
     * @param request request
     * @param response response
     */
    @RequestMapping
    public void exportSubmit(HttpServletRequest request, HttpServletResponse response){
        try {
         /* String year = request.getParameter("year");
            String month = request.getParameter("month");*/
            String salesman = request.getParameter("salesman");
            String customerId = request.getParameter("customerId");
            String company = request.getParameter("company");
            String operatorId = request.getParameter("operatorId");
            String yfhkStatus = request.getParameter("yfhkStatus");
            String yshkStatus = request.getParameter("yshkStatus");
            String deptName = request.getParameter("dpsName");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            
/*          NumberFormat formatter = NumberFormat.getNumberInstance();
            formatter.setMinimumIntegerDigits(2);
            formatter.setGroupingUsed(false);
            month = formatter.format(Integer.parseInt(month));
*/
            
            // 这里还应增加一个报表时间，精确到月即可
            String fileName = startDate.substring(5) + "|" + endDate.substring(5);
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            this.exportOrderData(toClient, startDate, endDate, salesman, yfhkStatus, yshkStatus,
                    customerId, company, operatorId, deptName);

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
    private void exportOrderData(OutputStream out, String startDate, String endDate, String salesmanId,
            String yfhkStatus, String yshkStatus, String customerId, String company,
            String operatorId, String deptName) {
        String[] titles = { "客户名称", "客户公司", "下单日期", "产品名称", "客人名单", "客人数量", "销售员", "操作员", "送签日期",
                "送签员", "应收单价", "其它应收款", "其它应付款", "总计应收款", "总计应付款", "毛利润", "付款状态", "已付货款", "收款状态",
                "已收货款", "备注","订单号" };
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
     /*   paraMap.put("date", year + "-" + month + "%");*/
        paraMap.put("salesmanId", StringUtils.isEmpty(salesmanId) ? null : salesmanId);

        paraMap.put("customerId", StringUtils.isEmpty(customerId) ? null : customerId);
        paraMap.put("company", StringUtils.isEmpty(company) ? null : company);
        paraMap.put("operatorId", StringUtils.isEmpty(operatorId) ? null : operatorId);

        paraMap.put("yfhkStatus", StringUtils.isEmpty(yfhkStatus) ? null : yfhkStatus);
        paraMap.put("yshkStatus", StringUtils.isEmpty(yshkStatus) ? null : yshkStatus);
        paraMap.put("deptName", StringUtils.isEmpty(deptName) ? null : deptName);
        paraMap.put("startDate", StringUtils.isEmpty(startDate) ? null : startDate);
        paraMap.put("endDate", StringUtils.isEmpty(endDate) ? null : endDate);
        List<Orders> ordersList = ordersDao.selectAll(paraMap);

        if (ordersList != null && ordersList.size() > 0) {
            BigDecimal zjys = new BigDecimal(0);
            BigDecimal zjyf = new BigDecimal(0);
            BigDecimal zjProfit = new BigDecimal(0);
            int i = 0;
            for (; i < ordersList.size(); i++) {
                Orders p = ordersList.get(i);
                Row row = s.createRow(i + 1);
                headerCell = row.createCell(0);
                headerCell.setCellValue(p.getCustomerName());
                headerCell = row.createCell(1);
                headerCell.setCellValue(p.getCustomerCompany());
                headerCell = row.createCell(2);
                headerCell.setCellValue(DateUtil.format(p.getOrderDate(), DateUtil.FORMAT_DATE));
                headerCell = row.createCell(3);
                if (p.getType() == 1) {
                    headerCell.setCellValue(p.getSingleProduct());
                } else {
                    headerCell.setCellValue(p.getProductName());
                }
                headerCell = row.createCell(4);
                headerCell.setCellValue(p.getNameList());
                headerCell = row.createCell(5);
                headerCell.setCellValue(p.getNameListSize());
                headerCell = row.createCell(6);
                headerCell.setCellValue(p.getSalesmanName());
                headerCell = row.createCell(7);
                headerCell.setCellValue(p.getOperatorName());
                headerCell = row.createCell(8);
                headerCell.setCellValue(DateUtil.format(p.getSignDate(), DateUtil.FORMAT_DATE));
                headerCell = row.createCell(9);
                headerCell.setCellValue(p.getSignOperatorName());
                headerCell = row.createCell(10);
                headerCell.setCellValue(p.getPriceYsdj() == null ? "0" : p.getPriceYsdj()
                        .toString());
                headerCell = row.createCell(11);
                headerCell.setCellValue(p.getPriceQtys() == null ? "0" : p.getPriceQtys()
                        .toString());
                headerCell = row.createCell(12);
                headerCell.setCellValue(p.getPriceQtzc() == null ? "0" : p.getPriceQtzc()
                        .toString());
                headerCell = row.createCell(13);
                headerCell.setCellValue(p.getPriceZjys() == null ? "0" : p.getPriceZjys()
                        .toString());
                headerCell = row.createCell(14);

                headerCell.setCellValue(p.getPriceZjyf() == null ? "0" : p.getPriceZjyf()
                        .toString());
                
                headerCell = row.createCell(15);
               
                BigDecimal zjys1 = null;
                BigDecimal zjyf1 = null;
                if(null == p.getPriceZjys()){
                	zjys1 = new BigDecimal(0);
                }else{
                    zjys1 = p.getPriceZjys();	
                }
                
                if(null == p.getPriceZjyf()){
                	zjyf1 = new BigDecimal(0);
                }else{
                	zjyf1 = p.getPriceZjyf();	
                }
                headerCell.setCellValue(zjys1.subtract(zjyf1).toString());

                headerCell = row.createCell(16);
                if (PriceStatusEnum.PRICESTATUS_MAP.get(p.getYfhkStatus()) != null) {
                    headerCell.setCellValue(PriceStatusEnum.PRICESTATUS_MAP.get(p.getYfhkStatus())
                            .getName());
                } else {
                    headerCell.setCellValue("");
                }
                headerCell = row.createCell(17);
                headerCell.setCellValue(p.getPriceYfhk() == null ? "0" : p.getPriceYfhk()
                        .toString());
                headerCell = row.createCell(18);
                if (PriceStatusEnum.PRICESTATUS_MAP.get(p.getYshkStatus()) != null) {
                    headerCell.setCellValue(YshkStatusEnum.YSHKSTATUS_MAP.get(p.getYshkStatus())
                            .getName());
                } else {
                    headerCell.setCellValue("");
                }
                headerCell = row.createCell(19);
                headerCell.setCellValue(p.getPriceYshk() == null ? "0" : p.getPriceYshk()
                        .toString());
                headerCell = row.createCell(20);
                headerCell.setCellValue(p.getDes());

                if (p.getPriceZjys() != null) {
                    zjys = zjys.add(p.getPriceZjys());
                }
                if (p.getPriceZjyf() != null) {
                    zjyf = zjyf.add(p.getPriceZjyf());
                }
                if (p.getGrossProfit() != null) {
                    zjProfit = zjProfit.add(zjys1.subtract(zjyf1));
                }
                headerCell = row.createCell(21);
                headerCell.setCellValue(p.getOrderSeq());
            }
            Row row1 = s.createRow(i + 1);
            headerCell = row1.createCell(0);
            headerCell.setCellValue("本月总计应收：" + zjys.toString());
            Row row2 = s.createRow(i + 2);
            headerCell = row2.createCell(0);
            headerCell.setCellValue("本月总计应付：" + zjyf.toString());
            Row row3 = s.createRow(i + 3);
            headerCell = row3.createCell(0);
            headerCell.setCellValue("本月总计毛利：" + zjProfit.toString());
        }
        try {
            wb.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.error("写出excel出错!", e);
        }

    }
    

    @RequestMapping
    public void export2(ModelMap model) {
        model.put("salesmanList", userDao.selectByRoleId(RoleEnumType.SALESMAN.getId()));
        model.put("operatorList", userDao.selectByRoleId(RoleEnumType.OPERATOR.getId()));
        model.put("customerList", customerDao.selectAllCustomer());
        model.put("companyList", customerDao.selectCompany());
        model.put("yearList", ordersDao.selectOrderYears());
        model.put("monthList", ordersDao.selectOrderMonths());
        model.put("departmentList", deptDao.selectAll());
    }
    
    @RequestMapping
    public void exportSubmit2(HttpServletRequest request, HttpServletResponse response){
        try {
         /* String year = request.getParameter("year");
            String month = request.getParameter("month");*/
            String salesman = request.getParameter("salesman");
            String customerId = request.getParameter("customerId");
            String company = request.getParameter("company");
            String operatorId = request.getParameter("operatorId");
            String yfhkStatus = request.getParameter("yfhkStatus");
            String yshkStatus = request.getParameter("yshkStatus");
            String deptName = request.getParameter("dpsName");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            
/*          NumberFormat formatter = NumberFormat.getNumberInstance();
            formatter.setMinimumIntegerDigits(2);
            formatter.setGroupingUsed(false);
            month = formatter.format(Integer.parseInt(month));
*/
            
            // 这里还应增加一个报表时间，精确到月即可
            String fileName = startDate.substring(5) + "|" + endDate.substring(5);
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            this.exportOrderData(toClient, startDate, endDate, salesman, yfhkStatus, yshkStatus,
                    customerId, company, operatorId, deptName);

        } catch (IOException e) {
            logger.error(e, e);
        }
    }
    
    private void exportOrderData2(OutputStream out, String startDate, String endDate, String salesmanId,
    		String yfhkStatus, String yshkStatus, String customerId, String company,
    		String operatorId, String deptName) {
    	String[] titles = { "客户名称", "客户公司", "下单日期", "产品名称", "客人名单", "客人数量", "销售员", "操作员", "送签日期",
    			"送签员", "应收单价", "其它应收款", "其它应付款", "总计应收款", "总计应付款", "毛利润", "付款状态", "已付货款", "收款状态",
    			"已收货款", "备注" };
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
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	/*   paraMap.put("date", year + "-" + month + "%");*/
    	paraMap.put("salesmanId", StringUtils.isEmpty(salesmanId) ? null : salesmanId);
    	
    	paraMap.put("customerId", StringUtils.isEmpty(customerId) ? null : customerId);
    	paraMap.put("company", StringUtils.isEmpty(company) ? null : company);
    	paraMap.put("operatorId", StringUtils.isEmpty(operatorId) ? null : operatorId);
    	
    	paraMap.put("yfhkStatus", StringUtils.isEmpty(yfhkStatus) ? null : yfhkStatus);
    	paraMap.put("yshkStatus", StringUtils.isEmpty(yshkStatus) ? null : yshkStatus);
    	paraMap.put("deptName", StringUtils.isEmpty(deptName) ? null : deptName);
    	paraMap.put("startDate", StringUtils.isEmpty(startDate) ? null : startDate);
    	paraMap.put("endDate", StringUtils.isEmpty(endDate) ? null : endDate);
    	List<Orders> ordersList = ordersDao.selectAll(paraMap);
    	
    	if (ordersList != null && ordersList.size() > 0) {
    		BigDecimal zjys = new BigDecimal(0);
    		BigDecimal zjyf = new BigDecimal(0);
    		BigDecimal zjProfit = new BigDecimal(0);
    		int i = 0;
    		for (; i < ordersList.size(); i++) {
    			Orders p = ordersList.get(i);
    			Row row = s.createRow(i + 1);
    			headerCell = row.createCell(0);
    			headerCell.setCellValue(p.getCustomerName());
    			headerCell = row.createCell(1);
    			headerCell.setCellValue(p.getCustomerCompany());
    			headerCell = row.createCell(2);
    			headerCell.setCellValue(DateUtil.format(p.getOrderDate(), DateUtil.FORMAT_DATE));
    			headerCell = row.createCell(3);
    			if (p.getType() == 1) {
    				headerCell.setCellValue(p.getSingleProduct());
    			} else {
    				headerCell.setCellValue(p.getProductName());
    			}
    			headerCell = row.createCell(4);
    			headerCell.setCellValue(p.getNameList());
    			headerCell = row.createCell(5);
    			headerCell.setCellValue(p.getNameListSize());
    			headerCell = row.createCell(6);
    			headerCell.setCellValue(p.getSalesmanName());
    			headerCell = row.createCell(7);
    			headerCell.setCellValue(p.getOperatorName());
    			headerCell = row.createCell(8);
    			headerCell.setCellValue(DateUtil.format(p.getSignDate(), DateUtil.FORMAT_DATE));
    			headerCell = row.createCell(9);
    			headerCell.setCellValue(p.getSignOperatorName());
    			headerCell = row.createCell(10);
    			headerCell.setCellValue(p.getPriceYsdj() == null ? "0" : p.getPriceYsdj()
    					.toString());
    			headerCell = row.createCell(11);
    			headerCell.setCellValue(p.getPriceQtys() == null ? "0" : p.getPriceQtys()
    					.toString());
    			headerCell = row.createCell(12);
    			headerCell.setCellValue(p.getPriceQtzc() == null ? "0" : p.getPriceQtzc()
    					.toString());
    			headerCell = row.createCell(13);
    			headerCell.setCellValue(p.getPriceZjys() == null ? "0" : p.getPriceZjys()
    					.toString());
    			headerCell = row.createCell(14);
    			headerCell.setCellValue(p.getPriceZjyf() == null ? "0" : p.getPriceZjyf()
    					.toString());
    			headerCell = row.createCell(15);
    			headerCell.setCellValue(p.getGrossProfit() == null ? "0" : p.getGrossProfit()
    					.toString());
    			headerCell = row.createCell(16);
    			if (PriceStatusEnum.PRICESTATUS_MAP.get(p.getYfhkStatus()) != null) {
    				headerCell.setCellValue(PriceStatusEnum.PRICESTATUS_MAP.get(p.getYfhkStatus())
    						.getName());
    			} else {
    				headerCell.setCellValue("");
    			}
    			headerCell = row.createCell(17);
    			headerCell.setCellValue(p.getPriceYfhk() == null ? "0" : p.getPriceYfhk()
    					.toString());
    			headerCell = row.createCell(18);
    			if (PriceStatusEnum.PRICESTATUS_MAP.get(p.getYshkStatus()) != null) {
    				headerCell.setCellValue(YshkStatusEnum.YSHKSTATUS_MAP.get(p.getYshkStatus())
    						.getName());
    			} else {
    				headerCell.setCellValue("");
    			}
    			headerCell = row.createCell(19);
    			headerCell.setCellValue(p.getPriceYshk() == null ? "0" : p.getPriceYshk()
    					.toString());
    			headerCell = row.createCell(20);
    			headerCell.setCellValue(p.getDes());
    			
    			if (p.getPriceZjys() != null) {
    				zjys = zjys.add(p.getPriceZjys());
    			}
    			if (p.getPriceZjyf() != null) {
    				zjyf = zjyf.add(p.getPriceZjyf());
    			}
    			if (p.getGrossProfit() != null) {
    				zjProfit = zjProfit.add(p.getGrossProfit());
    			}
    		}
    		Row row1 = s.createRow(i + 1);
    		headerCell = row1.createCell(0);
    		headerCell.setCellValue("本月总计应收：" + zjys.toString());
    		Row row2 = s.createRow(i + 2);
    		headerCell = row2.createCell(0);
    		headerCell.setCellValue("本月总计应付：" + zjyf.toString());
    		Row row3 = s.createRow(i + 3);
    		headerCell = row3.createCell(0);
    		headerCell.setCellValue("本月总计毛利：" + zjProfit.toString());
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
