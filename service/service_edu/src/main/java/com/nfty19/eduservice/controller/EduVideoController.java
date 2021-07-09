package com.nfty19.eduservice.controller;


import com.nfty19.commonutils.R;
import com.nfty19.eduservice.client.VodClient;
import com.nfty19.eduservice.entity.EduVideo;
import com.nfty19.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo video) {
        videoService.save(video);
        return R.ok().data("id",video.getId());
    }

    @DeleteMapping("{id}")
    public R deleteById(@PathVariable String id) {
        //根据小节id获取视频id，调用方法实现视频删除
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        //判断小节里面是否有视频id
        if(!StringUtils.isEmpty(videoSourceId)) {
            //根据视频id，远程调用实现视频删除
            R result = vodClient.removeAlyVideo(videoSourceId);
            if(result.getCode() == 20001) {
                return R.error();
            }
        }
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

