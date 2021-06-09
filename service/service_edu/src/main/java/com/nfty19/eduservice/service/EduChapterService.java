package com.nfty19.eduservice.service;

import com.nfty19.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nfty19.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author nfty19
 * @since 2021-06-07
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeByCourseId(String courseId);
}
