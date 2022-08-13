package com.test.day01;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class FutureloanTest {
    @Test
    public void testRegister() {
        System.out.println("=============5、发post请求-json参数类型=================");
        //5、发post请求-json参数类型
        String jsonStr = "{\"mobile_phone\":\"13323231116\",\"pwd\":\"12345678\",\"type\":1}";
        given().
                //given配置参数，请求头、请求参数、请求数据
                        header("Content-Type", "application/json;charset=utf-8").
                header("X-Lemonban-Media-Type", "lemonban.v1").
                body(jsonStr).
                when().
                //when是用来发起请求（get/post）
                        post("http://api.lemonban.com/futureloan/member/register").
                then().
                log().body();
    }

    @Test
    public void testLogin() {
        String jsonStr = "{\"mobile_phone\":\"13323231116\",\"pwd\":\"12345678\"}";
        given().
                //given配置参数，请求头、请求参数、请求数据
                        header("Content-Type", "application/json;charset=utf-8").
                header("X-Lemonban-Media-Type", "lemonban.v1").
                body(jsonStr).
                when().
                //when是用来发起请求（get/post）
                        post("http://api.lemonban.com/futureloan/member/login").
                then().
                log().body();
    }
}
