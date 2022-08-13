package com.test.day01;

import io.restassured.response.Response;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;

public class HomeWok {
    @Test
    public void testRegister() {
        //1.注册


        given().

                when().

                then().
                log().all();

    }

    @Test
    public void testLogin() {
        //2.登录
        String jsonStr = "{\"mobile_phone\":\"13323231116\",\"pwd\":\"12345678\"}";
        Response res =
                given().
                        //given配置参数，请求头、请求参数、请求数据
                                header("Content-Type", "application/json;charset=utf-8").
                        header("X-Lemonban-Media-Type", "lemonban.v2").
                        body(jsonStr).
                        when().
                        //when是用来发起请求（get/post）
                                post("http://api.lemonban.com/futureloan/member/login").
                        then().
                        extract().response();

        //获取响应信息中的全部内容：响应头+响应体
        System.out.println("响应体的内容为：" + res.asString());

        //提取响应状态码
        System.out.println("响应体的状态码为：" + res.statusCode());

        //提取响应头
        System.out.println("响应头为：" + res.header("Content-Type"));

        //获取响应时间
        System.out.println("响应时间为：" + res.time());

        //提取响应体，paht方法-->使用Gpath路径表达式语法来提取
        //提取响应结果对应字段值token
        String tokenValue = res.path("data.token_info.token");
        System.out.println("提取的token：" + tokenValue);

        //提取响应结果的会员id
        Integer member_id = res.path("data.id");
        System.out.println("提取的member_id：" + member_id);
    }

//    @Test
//    public void testRecharge() {
//        //3.充值
//        //把充值请求数据放到map
//        //String jsonStr2 = "{\"member_id\":\"member_id\",\"amount\":\"105.50\"}";
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("member_id", member_id);
//        map.put("amount", 100);
//        given().
//                header("Content-Type", "application/json;charset=utf-8").
//                header("X-Lemonban-Media-Type", "lemonban.v2").
//                //按照接口文档规定，v2版本需要添加token发请求
//                        header("Authorization", "Bearer " + tokenValue).
//                body(map).
//                when().
//                post("http://api.lemonban.com/futureloan/member/recharge").
//                then().
//                log().body();
//
//    }

    @Test
    public void testAdd() {
        //4.新增项目

        given().

                when().

                then().
                log().all();

    }

    @Test
    public void testWihtdraw() {
        //5.投资

        given().

                when().

                then().
                log().all();

    }
}
