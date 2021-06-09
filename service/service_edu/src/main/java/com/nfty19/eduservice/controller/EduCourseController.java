package com.nfty19.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nfty19.commonutils.R;
import com.nfty19.eduservice.entity.EduCourse;
import com.nfty19.eduservice.entity.EduTeacher;
import com.nfty19.eduservice.entity.vo.CourseInfoVo;
import com.nfty19.eduservice.entity.vo.CoursePublishVo;
import com.nfty19.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author nfty19
 * @since 2021-06-07
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    EduCourseService eduCourseService;

    //课程列表 基本实现
    //TODO  完善条件查询带分页
    @GetMapping
    public R getCourseList() {
        List<EduCourse> list = eduCourseService.list(null);
        return R.ok().data("list",list);
    }

    //添加课程基本信息的方法
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        //返回添加之后课程id，为了后面添加大纲使用
        String id = eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    @GetMapping("getCourseInfo/{id}")
    public R getCourseInfo(@PathVariable String id) {
        CourseInfoVo courseInfo= eduCourseService.getCourseInfo(id);
        return R.ok().data("courseInfoVo",courseInfo);
    }

    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfo) {
        String id = eduCourseService.updateCourseInfo(courseInfo);
        return R.ok().data("courseId",id);
    }

    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = eduCourseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }

    //课程最终发布
    //修改课程状态
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");//设置课程发布状态
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    //查询分页操作
    @GetMapping("pageCourse/{current}/{limit}")
    public R PageListTeacher(@PathVariable long current, @PathVariable long limit) {

        Page<EduCourse> pageParam = new Page<>(current, limit);
        //把分页数据全部封装到pageParam
        eduCourseService.page(pageParam, null);

        long total = pageParam.getTotal();
        List<EduCourse> records = pageParam.getRecords();

        return R.ok().data("total", total).data("rows", records);
    }

    //删除课程
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        eduCourseService.removeCourse(courseId);
        return R.ok();
    }
}

