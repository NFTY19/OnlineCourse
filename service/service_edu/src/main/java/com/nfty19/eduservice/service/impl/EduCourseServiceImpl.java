package com.nfty19.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nfty19.eduservice.entity.EduCourse;
import com.nfty19.eduservice.entity.EduCourseDescription;
import com.nfty19.eduservice.entity.EduVideo;
import com.nfty19.eduservice.entity.vo.CourseInfoVo;
import com.nfty19.eduservice.entity.vo.CoursePublishVo;
import com.nfty19.eduservice.mapper.EduCourseMapper;
import com.nfty19.eduservice.service.EduChapterService;
import com.nfty19.eduservice.service.EduCourseDescriptionService;
import com.nfty19.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nfty19.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author nfty19
 * @since 2021-06-07
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    EduVideoService videoService;

    @Autowired
    EduChapterService chapterService;


    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, course);
        int insert = baseMapper.insert(course);

        if(insert <= 0) {
            return null;
        }else {
            String cid = course.getId();
            EduCourseDescription eduCourseDescription = new EduCourseDescription();
            eduCourseDescription.setId(cid);
            eduCourseDescription.setDescription(courseInfoVo.getDescription());
            eduCourseDescriptionService.save(eduCourseDescription);
            return cid;
        }
    }

    @Override
    public CourseInfoVo getCourseInfo(String id) {
        EduCourse eduCourse = baseMapper.selectById(id);
        CourseInfoVo courseInfo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfo);

        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(id);
        courseInfo.setDescription(courseDescription.getDescription());

        return courseInfo;
    }

    @Override
    public String updateCourseInfo(CourseInfoVo courseInfo) {
        EduCourse course = baseMapper.selectById(courseInfo.getId());
        BeanUtils.copyProperties(courseInfo, course);
        baseMapper.updateById(course);

        String cid = course.getId();
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(cid);
        eduCourseDescription.setDescription(courseInfo.getDescription());
        eduCourseDescriptionService.updateById(eduCourseDescription);
        return cid;
    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        return baseMapper.getPublishCourseInfo(id);
    }

    @Override
    public void removeCourse(String courseId) {
        videoService.removeByCourseId(courseId);

        chapterService.removeByCourseId(courseId);

        eduCourseDescriptionService.removeByCourseId(courseId);

        baseMapper.deleteById(courseId);
    }


}
