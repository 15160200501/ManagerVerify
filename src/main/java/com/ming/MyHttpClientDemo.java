package com.ming;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyHttpClientDemo {

    public static String doPost(String url, Map<String, String> params) {
        //创建一个httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //返回参数
        String result = "";
        //构建响应对象
        CloseableHttpResponse response = null;
        try {

            //构建post请求
            HttpPost post = new HttpPost(url);


            if (params != null){
                //构建参数
                List<NameValuePair> paramList = new ArrayList<>();
                for (Map.Entry<String,String> param: params.entrySet()){
                    paramList.add(new BasicNameValuePair(param.getKey(),param.getValue()));
                }
                //模拟表单对象
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramList, Charset.defaultCharset());
                post.setEntity(formEntity);
            }

            response = httpClient.execute(post);
            result = EntityUtils.toString(response.getEntity(), Charset.defaultCharset());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(httpClient,response);
        }

        return result;
    }


    private static void close(CloseableHttpClient httpClient, CloseableHttpResponse response){
        try {
            if (response != null){
                response.close();
            }

            if (httpClient != null){
                httpClient.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile","自己的手机号码");
        params.put("tpl_id","211907");
        params.put("tpl_value","%23code%23%3d5643");
        params.put("key","自己的AppKey");
        params.put("dtype","json");
        System.out.println(MyHttpClientDemo.doPost("http://v.juhe.cn/sms/send",params));
    }
}
