package com.test.day03;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.given;

public class LoginTest {
    List<CaseInfo> caseInfoList;

    @BeforeTest
    public void setup() {
        // 从Excel中读取登录接口模块所需要的测试用例
        caseInfoList = getCaseDataFromExcel(1);


    }


    @Test(dataProvider = "getLoginDatas02")
    public void testLogin02(CaseInfo caseInfo) throws JsonProcessingException {
        //非json格式，将字符串的请求头转换成Map,
        //实现思路：原始的字符串转换会比较麻烦，把原始的字符串通过json数据类型保存，通过ObjectMapper来转换Map
        // jackson json字符串-->字符串
        // 1、实例化objectMapper对象
        ObjectMapper objectMapper = new ObjectMapper();
        // readValue方法参数解释，第一个参数：json字符串，第二个参：转换的类型Map
        Map headersMap = objectMapper.readValue(caseInfo.getRequestHeader(), Map.class);
        System.out.println("map = " + headersMap);
        Response res =
                given().
                        headers(headersMap).
                        body(caseInfo.getInputParams()).
                        when().
                        post("http://api.lemonban.com/futureloan" + caseInfo.getUrl()).

                        then().
                        extract().response();
        // 断言
        // 1、把断言数据转换为map
        String expected = caseInfo.getExpected();
        // 2.实例化ObjectMapper,把数据转换成map
        ObjectMapper objectMapper2 = new ObjectMapper();
        Map expectedMap = objectMapper2.readValue(expected, Map.class);
        //3.循环遍历取到map里面的每一组键值对
        Set<Map.Entry<String, Object>> set = expectedMap.entrySet();
        for (Map.Entry<String, Object> map : set) {
            System.out.println(map.getKey() + " " + map.getValue());

            // 关键点做断言：通过Gpath获取实际接口响应对应字段的值
            // 我们在Excel里面写用例的期望结果，期望结果里面键名-->Gpath表达式
            // 期望结果里面键值-->期望值
            Assert.assertEquals(res.path(map.getKey()), map.getValue());
        }


    }

    @DataProvider
    public Object[] getLoginDatas02() {
        //读取Excel测试用例数据
        ImportParams importParams = new ImportParams();
        importParams.setStartSheetIndex(1);
        File file = new File("src/test/resources/api_testcases_futureloan_v1.xls");
        List<CaseInfo> list = ExcelImportUtil.importExcel(file, CaseInfo.class, importParams);
        // 要将list集合转换为Object[][] 或者Object[]
        Object[] datas = list.toArray();
        // datas一维数组保存其实就是所有的CaseInfo对象
        return datas;
    }

    /*
    从Excel中读取所需要的用例数据
    @param index sheet的索引，从0开始
    */
    public List<CaseInfo> getCaseDataFromExcel(int index) {
        ImportParams importParams = new ImportParams();
        importParams.setStartSheetIndex(index);
        File excelFile = new File("src/test/resources/api_testcases_futureloan_v1.xls");
        List<CaseInfo> list = ExcelImportUtil.importExcel(excelFile, CaseInfo.class, importParams);
        return list;
    }





    @Test(dataProvider = "getLoginDatas01")
    public void testLogin01(String url, String method, String header1, String header2, String jsonStr) {
        given().
                header("Content-Type", header1).
                header("X-Lemonban-Media-Type", header2).
                body(jsonStr).
                when().
                post("http://api.lemonban.com/futureloan/member/login").

                then().
                log().all();
    }


    @DataProvider
    public Object[][] getLoginDatas01() {
        //1、请求接口地址 2、请求方式 3、请求头 4、请求数据
        Object[][] datas = {{"http://api.lemonban.com/futureloan/member/login", "post", "application/json;charset=utf-8", "lemonban.v1", "{\"mobile_phone\":\"18251651343\",\"pwd\":\"12345678\"}"},
                {"http://api.lemonban.com/futureloan/member/login", "post", "application/json;charset=utf-8", "lemonban.v1", "{\"mobile_phone\":\"133232310112\",\"pwd\":\"12345678\"}"},
                {"http://api.lemonban.com/futureloan/member/login", "post", "application/json;charset=utf-8", "lemonban.v1", "{\"mobile_phone\":\"1332323101a\",\"pwd\":\"12345678\"}"},
                {"http://api.lemonban.com/futureloan/member/login", "post", "application/json;charset=utf-8", "lemonban.v1", "{\"mobile_phone\":\"11015541764\",\"pwd\":\"12345678\"}"}
        };
        return datas;

    }

    @Test
    public void testLogin() {

        String jsonStr = "{\"mobile_phone\":\"18251651343\",\"pwd\":\"12345678\"}";
        given().
                header("Content-Type", "application/json;charset=utf-8").
                header("X-Lemonban-Media-Type", "lemonban.v2").
                body(jsonStr).
                when().
                post("http://api.lemonban.com/futureloan/member/login").

                then().
                log().all().extract().response();
    }

    public static void main(String[] args) {
        //读取Excel测试用例数据？？poi麻烦/笨重
        //推荐读取Excel技术：EasyPOI
        //第一个参数：File对象 第二个参数：映射的实体类.class  第三个参数：读取配置对象
        ImportParams importParams = new ImportParams();
        //sheet索引 ，默认的起始值为0
        importParams.setStartSheetIndex(0);
        //要读取的sheet数量， 默认为1
        importParams.setSheetNum(2);
        File file = new File("src/test/resources/api_testcases_futureloan_v1.xls");
        List<CaseInfo> list = ExcelImportUtil.importExcel(file, CaseInfo.class, importParams);
        for (CaseInfo caseInfo : list) {
            System.out.println("caseInfo = " + caseInfo);

        }
    }


}
