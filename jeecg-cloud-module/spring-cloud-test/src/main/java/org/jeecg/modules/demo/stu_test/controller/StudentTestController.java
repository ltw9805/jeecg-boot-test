package org.jeecg.modules.demo.stu_test.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.PermissionData;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.stu_test.entity.StudentTest;
import org.jeecg.modules.demo.stu_test.service.IStudentTestService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 学生测试表
 * @Author: jeecg-boot
 * @Date:   2021-03-26
 * @Version: V1.0
 */
@Api(tags="学生测试表")
@RestController
@RequestMapping("/stu_test/studentTest")
@Slf4j
public class StudentTestController extends JeecgController<StudentTest, IStudentTestService> {
	@Autowired
	private IStudentTestService studentTestService;
	
	/**
	 * 分页列表查询
	 *
	 * @param studentTest
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "学生测试表-分页列表查询")
	@ApiOperation(value="学生测试表-分页列表查询", notes="学生测试表-分页列表查询")
	@GetMapping(value = "/list")
	@PermissionData(pageComponent = "stutest/StudentTestList")
	public Result<?> queryPageList(StudentTest studentTest,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<StudentTest> queryWrapper = QueryGenerator.initQueryWrapper(studentTest, req.getParameterMap());
		Page<StudentTest> page = new Page<StudentTest>(pageNo, pageSize);
		IPage<StudentTest> pageList = studentTestService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param studentTest
	 * @return
	 */
	@AutoLog(value = "学生测试表-添加")
	@ApiOperation(value="学生测试表-添加", notes="学生测试表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody StudentTest studentTest) {
		studentTestService.save(studentTest);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param studentTest
	 * @return
	 */
	@AutoLog(value = "学生测试表-编辑")
	@ApiOperation(value="学生测试表-编辑", notes="学生测试表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody StudentTest studentTest) {
		studentTestService.updateById(studentTest);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "学生测试表-通过id删除")
	@ApiOperation(value="学生测试表-通过id删除", notes="学生测试表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		studentTestService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "学生测试表-批量删除")
	@ApiOperation(value="学生测试表-批量删除", notes="学生测试表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.studentTestService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "学生测试表-通过id查询")
	@ApiOperation(value="学生测试表-通过id查询", notes="学生测试表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		StudentTest studentTest = studentTestService.getById(id);
		if(studentTest==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(studentTest);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param studentTest
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, StudentTest studentTest) {
        return super.exportXls(request, studentTest, StudentTest.class, "学生测试表");
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
        return super.importExcel(request, response, StudentTest.class);
    }

}
