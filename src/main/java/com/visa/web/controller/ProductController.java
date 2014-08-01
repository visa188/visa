package com.visa.web.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.visa.common.constant.Constant;
import com.visa.common.util.DateUtil;
import com.visa.common.util.PagingUtil;
import com.visa.common.util.VelocityToolbox;
import com.visa.dao.AreaDao;
import com.visa.dao.CountryDao;
import com.visa.dao.OrdersDao;
import com.visa.dao.ProductDao;
import com.visa.po.Area;
import com.visa.po.Country;
import com.visa.po.Product;
import com.visa.vo.ProductVo;

/**
 * @author zhangzp
 */
@Controller
@RequestMapping("/product/*")
public class ProductController {
	@Resource
	private ProductDao productDao;
	@Resource
	private OrdersDao ordersDao;
	@Resource
	private CountryDao countryDao;
	@Resource
	private AreaDao areaDao;

	private Log logger = LogFactory.getLog(getClass());

	/**
	 * @param product
	 *            product
	 * @param page
	 *            page
	 * @param model
	 *            model
	 */
	@RequestMapping
	public void list(Product product, Integer page, ModelMap model) {
		List<Area> areaList = areaDao.selectAllArea();
		model.put("areaList", areaList);
		List<Country> countryList = countryDao.selectByContinentId(product.getContinentId());
		model.put("countryList", countryList);
		List<ProductVo> productList = new ArrayList<ProductVo>();
		Integer recordCount = productDao.selectAllCount(product);
		int[] recordRange = PagingUtil.addPagingSupport(Constant.PAGE_COUNT, recordCount, page, Constant.PAGE_OFFSET, model);
		productList = productDao.selectAll(recordRange[0], Constant.PAGE_COUNT, product);
		model.put("productList", productList);
	}

	/**
	 * 增加一个用户
	 * 
	 * @param model
	 *            model
	 */
	@RequestMapping
	public void add(ModelMap model) {
		List<Area> areaList = areaDao.selectAllArea();
		model.put("areaList", areaList);
		// List<Country> countryList = countryDao.selectByContinentId(1);
		// model.put("countryList", countryList);
		model.put("topNav", 1);
		model.put("secNav", 12);
		model.put("title", "新增产品信息");
		model.put("action", "insert");
	}

	/**
	 * @param product
	 *            product
	 * @return String
	 */
	@RequestMapping
	public String insert(Product product) {
		productDao.insert(product);
		return "redirect:list.do";
	}

	/**
	 * @param productId
	 *            productId
	 * @param page
	 *            page
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping
	public String edit(Integer productId, Integer page, ModelMap model) {
		List<Area> areaList = areaDao.selectAllArea();
		model.put("areaList", areaList);
		Product product = productDao.selectByPrimaryKey(productId);
		List<Country> countryList = countryDao.selectByContinentId(product.getContinentId());
		model.put("countryList", countryList);
		model.put("product", product);
		model.put("topNav", 1);
		model.put("secNav", 11);
		model.put("title", "修改产品信息");
		model.put("action", "update");
		model.put("page", page);
		return "product/add";
	}

	/**
	 * @param product
	 *            product
	 * @param page
	 *            page
	 * @return String
	 */
	@RequestMapping
	public String update(Product product, Integer page) {
		productDao.updateByPrimaryKey(product);
		return "redirect:list.do?page=" + page;
	}

	/**
	 * 删除一个user
	 * 
	 * @param productId
	 *            productId
	 * @param page
	 *            page
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping
	public String delete(Integer productId, Integer page, ModelMap model) {
		int count = ordersDao.selectByProductIdCount(productId);
		if (count == 0) {
			productDao.deleteByPrimaryKey(productId);
			return "redirect:list.do?page=" + page;
		} else {
			model.put("msg", "订单中用到，此产品信息不能被删除！");
			model.put("code", 404);
			model.put("topNav", 1);
			model.put("secNav", 11);
			model.put("title", "产品信息删除");
			model.put("href", "/product/list.do?page=" + page);
			return "result";
		}
	}

	/**
	 * @param productId
	 *            productId
	 * @return string
	 */
	@RequestMapping
	@ResponseBody
	public String getProductPrice(String productId) {
		if (!StringUtils.isEmpty(productId)) {
			Product product = productDao.selectByPrimaryKey(Integer.parseInt(productId));
			if (product == null) {
				return "error";
			} else {
				return product.getPrice().toString();
			}
		}
		return "error";
	}

	/**
	 * @param productName
	 *            productName
	 * @return string
	 */
	@RequestMapping
	@ResponseBody
	public List<Product> searchProduct(int countryId) {
		return productDao.searchByCountryId(countryId);
	}

	/**
	 * 导出产品信息数据
	 * */
	@RequestMapping
	@ResponseBody
	public void export(Product product, HttpServletResponse rsp) {
		List<ProductVo> productList = new ArrayList<ProductVo>();
		Integer recordCount = productDao.selectAllCount(product);
		productList = productDao.selectAll(0, recordCount, product);

		try {
			rsp.reset();
			rsp.addHeader("Content-Disposition", "attachment;filename=product.xls");
			OutputStream toClient = new BufferedOutputStream(rsp.getOutputStream());
			rsp.setContentType("application/octet-stream");
			exportExcel(productList, toClient);
		} catch (Exception e) {
			logger.error("生成excel失败", e);
		}

	}

	private void exportExcel(List<ProductVo> productList, OutputStream os) {
		String[] titles = { "产品名称", "所属领区", "地区", "国家", "产品类型", "成本价格", "联系人", "联系电话", "联系QQ", "公司名称", "公司地址", "备注", "添加日期" };
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

		VelocityToolbox tool = new VelocityToolbox();
		if (productList != null && productList.size() > 0) {
			for (int i = 0; i < productList.size(); i++) {
				ProductVo p = productList.get(i);
				Row row = s.createRow(i + 1);
				int count = 0;
				headerCell = row.createCell(count++);
				headerCell.setCellValue(p.getProductName());
				headerCell = row.createCell(count++);
				headerCell.setCellValue(p.getAreaName());
				headerCell = row.createCell(count++);
				headerCell.setCellValue(tool.getContinent(p.getContinentId()));
				headerCell = row.createCell(count++);
				headerCell.setCellValue(p.getCountryName());
				headerCell = row.createCell(count++);
				headerCell.setCellValue(p.getProductType());
				headerCell = row.createCell(count++);
				headerCell.setCellValue(String.valueOf(p.getPrice()));
				headerCell = row.createCell(count++);
				headerCell.setCellValue(p.getRelatePeople());
				headerCell = row.createCell(count++);
				headerCell.setCellValue(p.getRelateTel());
				headerCell = row.createCell(count++);
				headerCell.setCellValue(p.getRelateQq());
				headerCell = row.createCell(count++);
				headerCell.setCellValue(p.getRelateCompany());
				headerCell = row.createCell(count++);
				headerCell.setCellValue(p.getRelateAddr());
				headerCell = row.createCell(count++);
				headerCell.setCellValue(p.getDes());
				headerCell = row.createCell(count++);
				headerCell.setCellValue(DateUtil.format(p.getPostDt(), DateUtil.FORMAT_DATE));
			}
		}
		try {
			wb.write(os);
			os.flush();
			os.close();
		} catch (IOException e) {
			logger.error("写出excel出错!", e);
		}
	}

}
