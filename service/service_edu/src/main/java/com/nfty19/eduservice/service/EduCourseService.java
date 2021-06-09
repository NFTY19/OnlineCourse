package com.nfty19.eduservice.service;

import com.nfty19.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nfty19.eduservice.entity.vo.CourseInfoVo;
import com.nfty19.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author nfty19
 * @since 2021-06-07
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String id);

    String updateCourseInfo(CourseInfoVo courseInfo);

    CoursePublishVo publishCourseInfo(String id);

    void removeCourse(String courseId);
}
