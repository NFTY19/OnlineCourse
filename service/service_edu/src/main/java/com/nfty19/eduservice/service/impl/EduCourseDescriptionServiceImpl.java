package com.nfty19.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nfty19.eduservice.entity.EduChapter;
import com.nfty19.eduservice.entity.EduCourseDescription;
import com.nfty19.eduservice.mapper.EduCourseDescriptionMapper;
import com.nfty19.eduservice.service.EduCourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author nfty19
 * @since 2021-06-07
 */
@Service
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

    @Override
    public void removeByCourseId(String courseId) {
        QueryWrapper<EduCourseDescription> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("id",courseId);
        baseMapper.delete(queryWrapper);
    }
}
