package com.lemon.testcases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lemon.base.BaseCase;
import com.lemon.pojo.CaseInfo;
import com.test.day03.GlobalEnvironment;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AddLoanTest extends BaseCase {
    List<CaseInfo> caseInfoList;

    @BeforeClass
    public void setup() {
        //从Excel读取用户信息接口模块所需要的用例数据
        caseInfoList = getCaseDataFromExcel(4);
        //参数化替换
        caseInfoList = paramsReplace(caseInfoList);
    }

    @Test(dataProvider = "getAddLoanDatas")
    public void testAddLoan(CaseInfo caseInfo) throws JsonProcessingException, FileNotFoundException {
        //请求头由json字符串转Map
        Map headersMap = fromJsonToMap(caseInfo.getRequestHeader());
        String logFilePath = addLogToFile(caseInfo.getInterfaceName(), caseInfo.getCaseId());

        //发起接口请求
        Response res =
                given().log().all().
                        headers(headersMap).
                        body(caseInfo.getInputParams()).
                        when().
                        post(caseInfo. getUrl()).
                        then().
                        extract().response();
        //接口请求结束之后把请求/响应的的信息添加到allure中（附件的形式）
        //第一个参数：附件的名字,第二个参数为文件留FileInputStream
        Allure.addAttachment("接口请求数据", new FileInputStream(logFilePath));
        //断言
        assertExpected(caseInfo, res);
        //获取项目id
        //保存到环境变量中
        if (res.path("data.id") != null) {
            GlobalEnvironment.envData.put("loan_id", res.path("data.id"));
        }
    }

    @DataProvider
    public Object[] getAddLoanDatas() {
        return caseInfoList.toArray();
    }
}
