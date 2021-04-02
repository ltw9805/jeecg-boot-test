package org.jeecg.modules.demo.stu_test.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.demo.stu_test.entity.StudentTest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 学生测试表
 * @Author: jeecg-boot
 * @Date:   2021-03-26
 * @Version: V1.0
 */
@Mapper
public interface StudentTestMapper extends BaseMapper<StudentTest> {

}
