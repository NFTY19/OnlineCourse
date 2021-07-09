package com.nfty19.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nfty19.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nfty19.eduservice.entity.vo.TeacherQuery;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-05-12
 */
public interface EduTeacherService extends IService<EduTeacher> {
    void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery);

    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}
