package com.nfty19.eduservice.controller;


import com.nfty19.commonutils.R;
import com.nfty19.eduservice.entity.subject.FirstSubject;
import com.nfty19.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author nfty19
 * @since 2021-06-01
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;

    @PostMapping("addSubject")
    public R addSubject(@RequestBody MultipartFile file) {

        subjectService.saveSubject(file, subjectService);
        return R.ok();
    }

    @GetMapping("getAllSubject")
    public R getAllSubject() {
        List<FirstSubject> allSubject = subjectService.getAllSubject();
        return R.ok().data("list", allSubject);
    }
}

