package com.nfty19.eduservice.controller;


import com.nfty19.commonutils.R;
import com.nfty19.eduservice.entity.EduVideo;
import com.nfty19.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author nfty19
 * @since 2021-06-07
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    EduVideoService videoService;

    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo video) {
        videoService.save(video);
        return R.ok().data("id",video.getId());
    }

    @DeleteMapping("{id}")
    public R deleteById(@PathVariable String id) {
        videoService.removeById(id);
        return R.ok();
    }

    @PostMapping("editVideo")
    public R editVideo(@RequestBody EduVideo video) {
        videoService.updateById(video);
        return R.ok().data("id",video.getId());
    }

    @GetMapping("getVideoInfo/{id}")
    public R getVideoInfo(@PathVariable String id) {
        EduVideo videoInfo = videoService.getById(id);
        return R.ok().data("videoInfo",videoInfo);
    }

}

