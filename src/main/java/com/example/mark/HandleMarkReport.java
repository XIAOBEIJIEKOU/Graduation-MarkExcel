package com.example.mark;

import com.example.mark.bean.ExaminationBean;
import com.example.mark.bean.ExperimentBean;
import com.example.mark.bean.HomeworkBean;
import com.example.mark.bean.TestBean;
import com.example.mark.util.ExcelUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class HandleMarkReport {

    private static String readFilePath = "";

    /**
     * 接收数据写入报告excel
     * @param homeworkBeanList
     * @param testBeanList
     * @param examinationBeanList
     * @param experimentBeanList
     * @throws IOException
     */
    public static void reportWrite(List<HomeworkBean> homeworkBeanList, List<TestBean> testBeanList, List<ExaminationBean> examinationBeanList, List<ExperimentBean> experimentBeanList) throws IOException {
        Workbook workbook = ExcelUtil.getWorkBook(getReportExcelPath());
        Sheet sheet = workbook.getSheetAt(0);
        for (int i = 10; i < 13; i++) {
            sheet.getRow(i).getCell(2).setCellValue(homeworkBeanList.get(i-10).getId());
            sheet.getRow(i).getCell(3).setCellValue(homeworkBeanList.get(i-10).getFullMark());
            sheet.getRow(i).getCell(4).setCellValue(homeworkBeanList.get(i-10).getMark());
            sheet.getRow(i).getCell(5).setCellValue(testBeanList.get(i-10).getId());
            sheet.getRow(i).getCell(6).setCellValue(testBeanList.get(i-10).getFullMark());
            sheet.getRow(i).getCell(7).setCellValue(testBeanList.get(i-10).getMark());
            sheet.getRow(i).getCell(8).setCellValue(experimentBeanList.get(i-10).getId());
            sheet.getRow(i).getCell(9).setCellValue(experimentBeanList.get(i-10).getFullMark());
            sheet.getRow(i).getCell(10).setCellValue(experimentBeanList.get(i-10).getMark());
            sheet.getRow(i).getCell(11).setCellValue(examinationBeanList.get(i-10).getId());
            sheet.getRow(i).getCell(12).setCellValue(examinationBeanList.get(i-10).getFullMark());
            sheet.getRow(i).getCell(13).setCellValue(examinationBeanList.get(i-10).getMark());
        }
        //  四个模块得分
        Double[] avgDoubles = countingModuleTotal(homeworkBeanList,testBeanList,examinationBeanList,experimentBeanList);
        sheet.getRow(13).getCell(2).setCellValue(avgDoubles[0]);
        sheet.getRow(13).getCell(5).setCellValue(avgDoubles[1]);
        sheet.getRow(13).getCell(8).setCellValue(avgDoubles[2]);
        sheet.getRow(13).getCell(11).setCellValue(avgDoubles[3]);
        //  权重
        Double[] weightDoubles = countingWeight();
        sheet.getRow(16).getCell(2).setCellValue(weightDoubles[0]);
        sheet.getRow(16).getCell(5).setCellValue(weightDoubles[1]);
        sheet.getRow(16).getCell(8).setCellValue(weightDoubles[2]);
        sheet.getRow(16).getCell(11).setCellValue(weightDoubles[3]);
        //  环节法
        String s = countingTotal(avgDoubles,weightDoubles);
        sheet.getRow(18).getCell(1).setCellValue(s);
        FileOutputStream readFileInputStream = new FileOutputStream(readFilePath);
        workbook.write(readFileInputStream);
        workbook.close();
    }

    /**
     * 环节法计算
     * @param doubles1
     * @param doubles2
     * @return
     */
    private static String countingTotal(Double[] doubles1,Double[] doubles2){
        double total = 0;
        String s = "环节法计算达成度：";
        for (int i = 0; i < doubles1.length; i++) {
            total += doubles1[i]*doubles2[1];
            if (i == doubles1.length-1){
                s += doubles1[i] + "x" + doubles2[i] + "=" + total;
            } else {
                s += doubles1[i] + "x" + doubles2[i] + "+";
            }
        }
        return s;
    }

    /**
     * 设定权重
     * @return
     */
    private static Double[] countingWeight(){
        System.out.println("请输入四大模块占比(要求：加总为1，输入时以逗号隔开)：");
        Scanner readFileDir = new Scanner(System.in);
        String weightString = readFileDir.next();
        String[] strings = weightString.split(",");
        Double[] doubles = new Double[strings.length];
        double weight = 0;
        if (strings.length!=4){
            System.out.println("您输入的格式有误");
            countingWeight();
        } else {
            for (int i = 0; i < strings.length; i++) {
                weight += Double.valueOf(strings[i]);
                doubles[i] = Double.valueOf(strings[i]);
            }
            if (weight == 1){
                return doubles;
            } else {
                System.out.println("您输入的格式或数值有误");
                countingWeight();
            }
        }
        return null;
    }

    /**
     * 计算模块得分
     * @param homeworkBeanList
     * @param testBeanList
     * @param examinationBeanList
     * @param experimentBeanList
     * @return
     */
    private static Double[] countingModuleTotal(List<HomeworkBean> homeworkBeanList, List<TestBean> testBeanList, List<ExaminationBean> examinationBeanList, List<ExperimentBean> experimentBeanList){
        Double[] doubles = new Double[4];
        double homeworkTotal = 0.0;
        double testTotal = 0.0;
        double exprimentTotal = 0.0;
        double examinationTotal = 0.0;
        for (int i = 0; i < homeworkBeanList.size(); i++) {
            homeworkTotal += homeworkBeanList.get(i).getMark();
            testTotal += testBeanList.get(i).getMark();
            exprimentTotal += experimentBeanList.get(i).getMark();
            examinationTotal += examinationBeanList.get(i).getMark();
        }
        doubles[0] = (double) Math.round(homeworkTotal*100)/100;
        doubles[1] = (double) Math.round(testTotal*100)/100;
        doubles[2] = (double) Math.round(exprimentTotal*100)/100;
        doubles[3] = (double) Math.round(examinationTotal*100)/100;
        return doubles;
    }

    /**
     * 读取报告路径
     * @return
     */
    private static String getReportExcelPath(){
        System.out.println("请输入报告文件路径：");
        Scanner readFileDir = new Scanner(System.in);
        // /Users/salvatore/Desktop/salvatore/课程A成绩分析案例/西安工程大学课程成绩分析报告.xlsx
        readFilePath = readFileDir.next();
        return readFilePath;
    }
}