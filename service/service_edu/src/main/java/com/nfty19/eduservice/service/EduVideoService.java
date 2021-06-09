package com.nfty19.eduservice.service;

import com.nfty19.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author nfty19
 * @since 2021-06-07
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeByCourseId(String courseId);
}
