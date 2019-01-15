package org.wlgzs.xf_mall.util;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class AuthUtils {

    //应用的ID
    public static final String CLIENT_ID = "8c5284a3f97c73a66906";
    //应用秘钥
    public static final String CLIENT_SECRET="4097c6a6fd1d6b41e47b74b2036b1023585ada2e";

    //将请求返回的结果转化为json
    public static JSONObject doGetJson(String url) throws IOException{
        JSONObject jsonObject = null;
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response =client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if(entity !=null){
            String result = EntityUtils.toString(entity,"utf-8");
            jsonObject = JSONObject.fromObject(result);
        }
        httpGet.releaseConnection();
        return jsonObject;
    }

    //将请求返回的结果转化为String
    public static String doGetStr(String url) throws IOException {
        String result = null;
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response =client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if(entity !=null){
            result = EntityUtils.toString(entity,"utf-8");
        }
        httpGet.releaseConnection();
        return result;
    }


}
