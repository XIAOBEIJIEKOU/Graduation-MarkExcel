package com.example.mark;

import com.example.mark.bean.ExaminationBean;
import com.example.mark.bean.ExperimentBean;
import com.example.mark.bean.HomeworkBean;
import com.example.mark.bean.TestBean;
import com.example.mark.dao.MarkReportDao;
import com.example.mark.util.ExcelUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class HandleFourItems {

    @Autowired
    private MarkReportDao markReportDao;

    /**
     * 程序开始
     * @throws IOException
     */
    public static void handleStart() throws IOException {
        System.out.println("请输入excel地址：");
        //  /Users/salvatore/Desktop/salvatore/课程A成绩分析案例/成绩表.xlsx
        Scanner readFileDir = new Scanner(System.in);
        String readFilePath = readFileDir.next();
        Workbook workbook = ExcelUtil.getWorkBook(readFilePath);
        // 获取四个sheets分开处理
        Sheet sheetHomework =  workbook.getSheet("作业");
        Sheet sheetTest =  workbook.getSheet("测验");
        Sheet sheetExperiment =  workbook.getSheet("实验");
        Sheet sheetExamination =  workbook.getSheet("考试");
        HandleMarkReport.reportWrite(homeWorkHandle(sheetHomework),testHandle(sheetTest),examinationHandle(sheetExamination),exprimentHandle(sheetExperiment));
    }

    /**
     * 实验
     * @param sheet
     * @return
     */
    private static List<ExperimentBean> exprimentHandle(Sheet sheet){
        List<ExperimentBean> experimentBeanList = new ArrayList<ExperimentBean>();
        for (int i = 7; i < 10; i++) {
            String id = countingID(sheet,i,1);
            double goalFullMark = countingGoalFull(sheet,i,2);
            double goalMark = countingGoalAvg(sheet , i,3);
            ExperimentBean experimentBean = new ExperimentBean();
            experimentBean.setId(id);
            experimentBean.setFullMark(goalFullMark);
            experimentBean.setMark(goalMark);
            experimentBeanList.add(experimentBean);
            MarkApplication.ac.getBean(MarkReportDao.class).insertExperimentBean(experimentBean);
        }
        return experimentBeanList;
    }

    /**
     * 作业
     * @param sheet
     * @return
     */
    private static List<HomeworkBean> homeWorkHandle(Sheet sheet){
        List<HomeworkBean> homeworkBeanList = new ArrayList<HomeworkBean>();
        for (int i = 4; i < 7; i++) {
            HomeworkBean homeworkBean = new HomeworkBean();
            String id = countingID(sheet,i,1);
            double goalFullMark = countingGoalFull(sheet,i,2);
            double goalMark = countingGoalAvg(sheet , i,3);
            homeworkBean.setId(id);
            homeworkBean.setFullMark(goalFullMark);
            homeworkBean.setMark(goalMark);
            homeworkBeanList.add(homeworkBean);
            MarkApplication.ac.getBean(MarkReportDao.class).insertHomeWorkBean(homeworkBean);
        }
        return homeworkBeanList;
    }

    /**
     * 测验
     * @param sheet
     * @return
     */
    private static List<TestBean> testHandle(Sheet sheet){
        List<TestBean> testBeanList = new ArrayList<TestBean>();
        for (int i = 39; i < 42; i++) {
            String id = countingID(sheet,i,1);
            double goalFullMark = countingGoalFull(sheet,i,2);
            double goalMark = countingGoalAvg(sheet , i,3);
            TestBean testBean = new TestBean();
            testBean.setId(id);
            testBean.setFullMark(goalFullMark);
            testBean.setMark(goalMark);
            testBeanList.add(testBean);
            MarkApplication.ac.getBean(MarkReportDao.class).insertTestBean(testBean);
        }
        return testBeanList;
    }

    /**
     * 考试
     * @param sheet
     * @return
     */
    private static List<ExaminationBean> examinationHandle(Sheet sheet){
        List<ExaminationBean> examinationBeanList = new ArrayList<ExaminationBean>();
        for (int i = 39; i < 42; i++) {
            String id = countingID(sheet,i,1);
            double goalFullMark = countingGoalFull(sheet,i,2);
            double goalMark = countingGoalAvg(sheet , i,3);
            ExaminationBean examinationBean = new ExaminationBean();
            examinationBean.setId(id);
            examinationBean.setFullMark(goalFullMark);
            examinationBean.setMark(goalMark);
            examinationBeanList.add(examinationBean);
            MarkApplication.ac.getBean(MarkReportDao.class).insertExaminationBean(examinationBean);
        }
        return examinationBeanList;
    }


    /**
     * 计算目标平均分，double保留两位小数
     * @param sheet
     * @param cellNum
     * @return
     */
    private static double countingGoalAvg(Sheet sheet,int cellNum,int startRowNum) {
        if (sheet != null) {
            double goalTotal = 0;
            //  i从1开始，前两行为表头
            for (int i = startRowNum; i <= sheet.getLastRowNum(); i++) {
                goalTotal = goalTotal + Double.valueOf(ExcelUtil.getCellFormatValue(sheet.getRow(i).getCell(cellNum)));
            }
            int num = Integer.valueOf(ExcelUtil.getCellFormatValue(sheet.getRow(sheet.getLastRowNum()).getCell(0)));
            double goalAvg = goalTotal / num;
            return (double)Math.round(goalAvg*100)/100;
        } else {

        }
        return 0;
    }

    /**
     * 获取各目标满分
     * @param sheet
     * @param cellNum
     * @param rowNum
     * @return
     */
    private static double countingGoalFull(Sheet sheet,int cellNum,int rowNum){
        double goalFull = Double.valueOf(ExcelUtil.getCellFormatValue(sheet.getRow(rowNum).getCell(cellNum)));
        return goalFull;
    }

    /**
     * 获取各目标考察序号
     * @param sheet
     * @param cellNum
     * @param rowNum
     * @return
     */
    private static String countingID(Sheet sheet,int cellNum,int rowNum){
        String goalFull = ExcelUtil.getCellFormatValue(sheet.getRow(rowNum).getCell(cellNum));
        return goalFull;
    }


}
