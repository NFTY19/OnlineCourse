package com.nfty19.eduservice.controller;


import com.nfty19.commonutils.R;
import com.nfty19.eduservice.entity.EduChapter;
import com.nfty19.eduservice.entity.chapter.ChapterVo;
import com.nfty19.eduservice.service.EduChapterService;
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
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    //课程大纲列表,根据课程id进行查询
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }

    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter chapter) {
        boolean save = chapterService.save(chapter);
        return R.ok();
    }

    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        EduChapter chapter = chapterService.getById(chapterId);
        return R.ok().data("chapter",chapter);
    }

    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter chapter) {
        chapterService.updateById(chapter);
        return R.ok();
    }

    @DeleteMapping("{chapterId}")
    public R delete(@PathVariable String chapterId) {
        boolean flag = chapterService.deleteChapter(chapterId);
        return R.ok();
    }
}

