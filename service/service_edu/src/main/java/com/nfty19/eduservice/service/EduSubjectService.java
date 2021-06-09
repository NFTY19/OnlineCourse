package com.nfty19.eduservice.service;

import com.nfty19.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nfty19.eduservice.entity.subject.FirstSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author nfty19
 * @since 2021-06-01
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService subjectService);

    List<FirstSubject> getAllSubject();
}
