package com.test.day01;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;

public class RestApiTest {

    public static void main(String[] args) {
        System.out.println("==============1、简单get请求,可以连写，链式调用=================");
//        //1、简单get请求,可以连写，链式调用
//        given().
//                //given配置参数，请求头、请求参数、请求数据
//                        when().
//                //when是用来发起请求（get/post）
//                        get("http://httpbin.org/get").
//                then().
//                //对响应结果做什么事情
//                        log().all();

        System.out.println("==============2、带参数的get请求-1=================");
//        //2、带参数的get请求-1
//        given().
//                //given配置参数，请求头、请求参数、请求数据
//                        when().
//                //when是用来发起请求（get/post）
//                        get("http://httpbin.org/get?name=张三&age=20").
//                then().
//                //对响应结果做什么事情
//                        log().all();

        System.out.println("==============3.带参数的get请求-2=================");
//        //3.带参数的get请求-2
//        given().
//                //given配置参数，请求头、请求参数、请求数据
//                        queryParam("name", "张三").queryParam("age", "20").
//                when().
//                //when是用来发起请求（get/post）
//                        get("http://httpbin.org/get").
//
//                then().
//                log().all();


        System.out.println("==============4、带参数的get请求-3 多个的情况下=================");
        //4、带参数的get请求-3 多个的情况下
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "张三");
        map.put("age", "20");
        map.put("adress", "长沙");
        map.put("sex", "女");
        given().
                //given配置参数，请求头、请求参数、请求数据
                        queryParams(map).
                when().
                //when是用来发起请求（get/post）
                        get("http://httpbin.org/get").

                then().
                log().body();



//        //5、发post请求-form表单参数
//        //注意事项：如果form表单参数有中文的话，记得加charset=utf-8到content-type里面，否则会有乱码的问题
//        given().
//                //given配置参数，请求头、请求参数、请求数据
//                        formParam("name", "小红").
//                contentType("application/x-www-form-urlencoded;charset=utf-8").
//                when().
//                //when是用来发起请求（get/post）
//                        post("http://httpbin.org/get").
//
//                then().
//                log().body();


//        //5、发post请求-json参数类型
//        String jsonStr = "{\"mobile_phone\":\"13323231116\",\"pwd\":\"12345678\"}";
//        Map<String, String> map2 = new HashMap<String, String>();
//        map2.put("mobile_phone", "13323231116");
//        map2.put("pwd", "12345678");
//        given().
//                //given配置参数，请求头、请求参数、请求数据
//                        contentType("application/json;charset=utf-8").
//                body(map2).
//                when().
//                //when是用来发起请求（get/post）
//                        post("http://httpbin.org/post").
//                then().
//                log().body();

//        //5、发post请求-xml参数类型
//        String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
//                "<suite>\n" +
//                "    <class>测试xml</class>\n" +
//                "</suite>";
//        given().
//                contentType("text/xml;charset=utf-8").
//                body(xmlStr).
//                when().
//                post("http://httpbin.org/post").
//                then().
//                log().body();


//        //5、发post请求-多参数表单 上传文件
//        given().
//                contentType("multipart/form-data;charset=utf-8").
//                multiPart(new File("D:\\lemon.txt")).
//                when().
//                post("http://httpbin.org/post").
//                then().
//                log().body();
    }


}
