package com.nfty19.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nfty19.commonutils.R;
import com.nfty19.eduservice.entity.EduTeacher;
import com.nfty19.eduservice.entity.vo.TeacherQuery;
import com.nfty19.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-05-12
 */
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    //1. 查询所有讲师模块
    @GetMapping("findAll")
    public R findAllTeacher() {
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }

    //2. 逻辑删除功能
    @DeleteMapping("{id}")
    public R removeTeacher(@PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //3. 分页操作
    @GetMapping("pageTeacher/{current}/{limit}")
    public R PageListTeacher(@PathVariable long current, @PathVariable long limit) {

        Page<EduTeacher> pageParam = new Page<>(current, limit);
        //把分页数据全部封装到pageParam
        teacherService.page(pageParam, null);

        long total = pageParam.getTotal();
        List<EduTeacher> records = pageParam.getRecords();

        return R.ok().data("total", total).data("rows", records);
    }

    //4. 条件查询
    @PostMapping("pageQuery/{current}/{limit}")
    public R pageQuery(@PathVariable long current, @PathVariable long limit,
                       @RequestBody(required = false)TeacherQuery teacherQuery) {
        Page<EduTeacher> pageParam = new Page<>(current, limit);

        teacherService.pageQuery(pageParam, teacherQuery);
        List<EduTeacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();

        return R.ok().data("total", total).data("rows", records);
    }

    //5. 讲师添加
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher teacher) {
        boolean save = teacherService.save(teacher);
        return save ? R.ok() : R.error();
    }

    //6. 根据id查询教师
    @GetMapping("getTeacher/{id}")
    public R addTeacher(@PathVariable long id) {
        EduTeacher teacher = teacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }

    //7. 讲师修改功能
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher teacher) {
        return teacherService.updateById(teacher) ? R.ok() : R.error();
    }

    //8. 测试异常处理
    @ApiOperation(value = "测试全局异常处理(除法运算)")
    @GetMapping("testException/{first}/{second}")
    public R testException(@PathVariable int first, @PathVariable int second) {
        int ans = first / second;
        return R.ok().data("answer",ans);
    }

}

