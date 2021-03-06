package org.ds.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ds.health.constant.MessageConstant;
import org.ds.health.entity.Result;
import org.ds.health.service.MemberService;
import org.ds.health.service.ReportService;
import org.ds.health.service.SetmealService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("report")
public class ReportController {

    @Reference
    private MemberService memberService;

    @Reference
    private SetmealService setmealService;

    @Reference
    private ReportService reportService;

    @GetMapping("/getMemberReport")
    public Result getMemberReport(){
        //1. cntroller接收
        //2. 生成12个月的数据，2020-02, 获取日历对象
        Calendar car = Calendar.getInstance();
        // 过去1年
        car.add(Calendar.YEAR, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        //List<String> months(12个月)
        List<String> months = new ArrayList<String>();
        for (int i = 0; i < 12; i++) {
            car.add(Calendar.MONTH,1);
            // 添加月份 2020-02
            months.add(sdf.format(car.getTime()));
        }
        //3. 调用服务查询 List<String> months(12个月)
        List<Integer> memberCount = memberService.getMemberReport(months);
        // 返回给页面
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("months",months);
        map.put("memberCount",memberCount);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }

    /**
     * 套餐预约占比饼图
     * @return
     */
    @GetMapping("/getSetmealReport")
    public Result getSetmealReport(){
        //调用服务端方法查询套餐数量，
        List<Map<String,Object>> setmealCount = setmealService.getSetmealReport();
        // 抽取套餐名称集合
        List<String> setmealNames = setmealCount.stream().map(map -> (String)map.get("name")).collect(Collectors.toList());
        //List<String> setmealNames = new ArrayList<String>();
        //for (Map<String, Object> map : setmealCount) {
        //    // map{value:20,name:套餐名称}
        //    // 套餐名称
        //    String setmealName = ((String) map.get("name"));
        //    setmealNames.add(setmealName);
        //}
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("setmealNames",setmealNames);
        resultMap.put("setmealCount",setmealCount);
        return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,resultMap);
    }

    /**
     * 运营数据统计
     * @return
     */
    @GetMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        Map<String,Object> reportData = reportService.getBusinessReportData();
        return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS,reportData);
    }

    /**
     * 导出运营数据统计
     * @param req
     * @param res
     */
    @GetMapping("/exportBusinessReport")
    public void exportBusinessReport(HttpServletRequest req, HttpServletResponse res){
        //- 调用ReportService获取运营的数据
        Map<String, Object> reportData = reportService.getBusinessReportData();
        //- 获取模板的存放路径 .xlsx, getRealPath目录是webapp下
        String template = req.getSession().getServletContext().getRealPath("/template/report_template.xlsx");
        //- 创建XSSFWorkbook
        try(Workbook wk = new XSSFWorkbook(template);) {
            //- 获取工作表
            Sheet sht = wk.getSheetAt(0);
            //- 获取行对象
            //- 获取单格
            //- 填值
            //  - 报表日期
            Row row = sht.getRow(2);
            row.getCell(5).setCellValue((String)reportData.get("reportDate"));
            //  ============== 会员数量 ====================
            sht.getRow(4).getCell(5).setCellValue((int)reportData.get("todayNewMember"));
            sht.getRow(4).getCell(7).setCellValue((int)reportData.get("totalMember"));
            sht.getRow(5).getCell(5).setCellValue((int)reportData.get("thisWeekNewMember"));
            sht.getRow(5).getCell(7).setCellValue((int)reportData.get("thisMonthNewMember"));
            //  ============= 预约到诊 =====================
            sht.getRow(7).getCell(5).setCellValue((int)reportData.get("todayOrderNumber"));
            sht.getRow(7).getCell(7).setCellValue((int)reportData.get("todayVisitsNumber"));
            sht.getRow(8).getCell(5).setCellValue((int)reportData.get("thisWeekOrderNumber"));
            sht.getRow(8).getCell(7).setCellValue((int)reportData.get("thisWeekVisitsNumber"));
            sht.getRow(9).getCell(5).setCellValue((int)reportData.get("thisMonthOrderNumber"));
            sht.getRow(9).getCell(7).setCellValue((int)reportData.get("thisMonthVisitsNumber"));
            //  ================= 热门套餐 ===============
            List<Map<String,Object>> hotSetmeal = (List<Map<String,Object>>)reportData.get("hotSetmeal");
            if(null != hotSetmeal){
                int rowIndex = 12;
                for (Map<String, Object> setmealMap : hotSetmeal) {
                    sht.getRow(rowIndex).getCell(4).setCellValue((String)setmealMap.get("name"));
                    sht.getRow(rowIndex).getCell(5).setCellValue((long)setmealMap.get("setmeal_count"));
                    // 占比，类型BigDecimal
                    BigDecimal proportion = (BigDecimal) setmealMap.get("proportion");
                    sht.getRow(rowIndex).getCell(6).setCellValue(proportion.doubleValue());
                    sht.getRow(rowIndex).getCell(7).setCellValue((String)setmealMap.get("remark"));
                    rowIndex++;
                }
            }
            //- 实现下载
            //  - 设置响应内容体格式, 告诉浏览器，文件的类型为excel
            res.setContentType("application/vnd.ms-excel");
            //  - 调协响应头信息 告诉浏览器，响应的内容体是一个文件，文件名叫business.xlsx
            String filename="运营数据统计.xlsx";
            filename = new String(filename.getBytes(),"ISO-8859-1");// 数据不会丢失
            res.setHeader("Content-Disposition","attachment;filename=" + filename);
            //- workbook.write(response.getOutputStream)
            wk.write(res.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}