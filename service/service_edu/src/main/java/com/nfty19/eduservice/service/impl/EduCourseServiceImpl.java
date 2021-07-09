package com.nfty19.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nfty19.eduservice.entity.EduCourse;
import com.nfty19.eduservice.entity.EduCourseDescription;
import com.nfty19.eduservice.entity.EduVideo;
import com.nfty19.eduservice.entity.frontVo.CourseFrontVo;
import com.nfty19.eduservice.entity.frontVo.CourseWebVo;
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
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //1 条件查询带分页查询课程
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo) {
        //2 根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件值是否为空，不为空拼接
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) { //一级分类
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())) { //二级分类
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) { //关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { //最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {//价格
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageParam,wrapper);

        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();//下一页
        boolean hasPrevious = pageParam.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }

    //根据课程id，编写sql语句查询课程信息
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }


}
