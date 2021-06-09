package com.nfty19.eduservice.service;

import com.nfty19.eduservice.entity.EduCourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author nfty19
 * @since 2021-06-07
 */
public interface EduCourseDescriptionService extends IService<EduCourseDescription> {

    void removeByCourseId(String courseId);
}
