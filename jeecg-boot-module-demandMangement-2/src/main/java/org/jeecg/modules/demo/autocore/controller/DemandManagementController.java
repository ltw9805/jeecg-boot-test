package org.jeecg.modules.demo.autocore.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.autocore.entity.DemandManagement;
import org.jeecg.modules.demo.autocore.service.IDemandManagementService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 需求管理
 * @Author: jeecg-boot
 * @Date:   2021-04-06
 * @Version: V1.0
 */
@Api(tags="需求管理")
@RestController
@RequestMapping("/autocore/demandManagement2")
@Slf4j
public class DemandManagementController extends JeecgController<DemandManagement, IDemandManagementService> {
	@Autowired
	private IDemandManagementService demandManagementService;
	
	/**
	 * 分页列表查询
	 *
	 * @param demandManagement
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "需求管理-分页列表查询")
	@ApiOperation(value="需求管理-分页列表查询", notes="需求管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DemandManagement demandManagement,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DemandManagement> queryWrapper = QueryGenerator.initQueryWrapper(demandManagement, req.getParameterMap());
		Page<DemandManagement> page = new Page<DemandManagement>(pageNo, pageSize);
		IPage<DemandManagement> pageList = demandManagementService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param demandManagement
	 * @return
	 */
	@AutoLog(value = "需求管理-添加")
	@ApiOperation(value="需求管理-添加", notes="需求管理-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DemandManagement demandManagement) {
		demandManagementService.save(demandManagement);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param demandManagement
	 * @return
	 */
	@AutoLog(value = "需求管理-编辑")
	@ApiOperation(value="需求管理-编辑", notes="需求管理-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DemandManagement demandManagement) {
		demandManagementService.updateById(demandManagement);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "需求管理-通过id删除")
	@ApiOperation(value="需求管理-通过id删除", notes="需求管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		demandManagementService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "需求管理-批量删除")
	@ApiOperation(value="需求管理-批量删除", notes="需求管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.demandManagementService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "需求管理-通过id查询")
	@ApiOperation(value="需求管理-通过id查询", notes="需求管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DemandManagement demandManagement = demandManagementService.getById(id);
		if(demandManagement==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(demandManagement);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param demandManagement
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DemandManagement demandManagement) {
        return super.exportXls(request, demandManagement, DemandManagement.class, "需求管理");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, DemandManagement.class);
    }

}
