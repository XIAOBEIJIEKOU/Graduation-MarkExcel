package com.example.mark.dao;

import com.example.mark.bean.ExaminationBean;
import com.example.mark.bean.ExperimentBean;
import com.example.mark.bean.HomeworkBean;
import com.example.mark.bean.TestBean;
import org.springframework.stereotype.Component;

@Component
public interface MarkReportDao {

    int insertHomeWorkBean(HomeworkBean homeworkBean);

    int insertExperimentBean(ExperimentBean experimentBean);

    int insertTestBean(TestBean testBean);

    int insertExaminationBean(ExaminationBean examinationBean);
}
