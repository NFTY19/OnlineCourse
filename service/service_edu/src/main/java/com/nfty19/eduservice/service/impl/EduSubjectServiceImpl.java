package com.nfty19.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nfty19.eduservice.entity.EduSubject;
import com.nfty19.eduservice.entity.excel.SubjectData;
import com.nfty19.eduservice.entity.subject.FirstSubject;
import com.nfty19.eduservice.entity.subject.SecondSubject;
import com.nfty19.eduservice.listener.SubjectExcelListener;
import com.nfty19.eduservice.mapper.EduSubjectMapper;
import com.nfty19.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author nfty19
 * @since 2021-06-01
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<FirstSubject> getAllSubject() {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", 0);
        List<EduSubject> First = baseMapper.selectList(wrapper);

        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        wrapper.ne("parent_id", 0);
        List<EduSubject> Second = baseMapper.selectList(wrapper2);

        ArrayList<FirstSubject> ans = new ArrayList<>();

        for (EduSubject es : First) {
            FirstSubject firstSubject = new FirstSubject();
            BeanUtils.copyProperties(es, firstSubject);

            ArrayList<SecondSubject> secondSubjects = new ArrayList<>();

            for (EduSubject es2 : Second) {
                if (es2.getParentId().equals(es.getId())) {
                    SecondSubject ss = new SecondSubject();
                    BeanUtils.copyProperties(es2, ss);
                    secondSubjects.add(ss);
                }
            }
            firstSubject.setChildren(secondSubjects);
            ans.add(firstSubject);
        }
        return ans;
    }
}
