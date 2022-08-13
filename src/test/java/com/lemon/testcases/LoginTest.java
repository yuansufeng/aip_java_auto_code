package com.lemon.testcases;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lemon.base.BaseCase;
import com.lemon.data.GlobalEnvironment;
import com.lemon.pojo.CaseInfo;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class LoginTest extends BaseCase {

    List<CaseInfo> caseInfoList;

    @BeforeClass
    public void setup() {
        //从Excel读取登录接口模块所需要的用例数据
        caseInfoList = getCaseDataFromExcel(1);
        //参数化替换
        caseInfoList = paramsReplace(caseInfoList);
    }

    @Test(dataProvider = "getLoginDatas")
    public void testLogin(CaseInfo caseInfo) throws JsonProcessingException, FileNotFoundException {
        //String jsonStr = "{\"mobile_phone\":\"13323231011\",\"pwd\":\"12345678\"}";
        //字符串请求头转换成Map --
        //实现思路：原始的字符串转换会比较麻烦，把原始的字符串通过json数据类型保存，通过ObjectMapper来去转换为Map
        //jackson json字符串-->Map
        //1、实例化objectMapper对象，
        Map headersMap = fromJsonToMap(caseInfo.getRequestHeader());
        String logFilePath = addLogToFile(caseInfo.getInterfaceName(), caseInfo.getCaseId());

        Response res =
                given().log().all().
                        headers(headersMap).
                        body(caseInfo.getInputParams()).
                        when().
                        post(caseInfo.getUrl()).
                        then().
                        log().all().
                        extract().response();

        //接口请求结束之后把请求/响应的的信息添加到allure中（附件的形式）
        //第一个参数：附件的名字,第二个参数为文件留FileInputStream
        Allure.addAttachment("接口请求数据", new FileInputStream(logFilePath));
        //断言
        assertExpected(caseInfo, res);
        //在登录模块用例执行结束之后将token保存到环境变量中
        if (caseInfo.getCaseId() == 1) {
            //拿到正常用例返回信息里面的token
            GlobalEnvironment.envData.put("token1", res.path("data.token_info.token"));
        } else if (caseInfo.getCaseId() == 2) {
            //拿到正常用例返回信息里面的token
            GlobalEnvironment.envData.put("token2", res.path("data.token_info.token"));
        } else if (caseInfo.getCaseId() == 3) {
            //拿到正常用例返回信息里面的token
            GlobalEnvironment.envData.put("token3", res.path("data.token_info.token"));
        }
    }

    @DataProvider
    public Object[] getLoginDatas() {
        //dataprovider数据提供者返回值类型可以是Object[] 也可以是Object[][]
        //怎么list集合转换为Object[][]或者Object[]？？？
        return caseInfoList.toArray();
        //datas一维数组里面保存其实就是所有的CaseInfo对象

    }

    /**
     * 从Excel读取所需的用例数据
     *
     * @param index sheet的索引，从0开始的
     */
    public List<CaseInfo> getCaseDataFromExcel(int index) {
        ImportParams importParams = new ImportParams();
        importParams.setStartSheetIndex(index);
        File excelFile = new File("src\\test\\resources\\api_testcases_futureloan_v41.xls");
        List<CaseInfo> list = ExcelImportUtil.importExcel(excelFile, CaseInfo.class, importParams);
        return list;
    }

}
