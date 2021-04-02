package org.jeecg.modules.demo.stu_test.service.impl;

import org.jeecg.modules.demo.stu_test.entity.StudentTest;
import org.jeecg.modules.demo.stu_test.mapper.StudentTestMapper;
import org.jeecg.modules.demo.stu_test.service.IStudentTestService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 学生测试表
 * @Author: jeecg-boot
 * @Date:   2021-03-26
 * @Version: V1.0
 */
@Service
public class StudentTestServiceImpl extends ServiceImpl<StudentTestMapper, StudentTest> implements IStudentTestService {

}
