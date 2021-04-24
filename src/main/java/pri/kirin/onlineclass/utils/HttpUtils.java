package pri.kirin.onlineclass.Utils;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HttpUtils {

    private static final Gson gson = new Gson();

    /**
     * 封装 Http Get 请求
     * @param url
     * @return
     */
    public static Map<String,Object> doGet(String url){
        Map<String,Object> map = new HashMap<>();

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(5000)
                .setConnectTimeout(5000)
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)
                .build();

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept","application/json");
        httpGet.setConfig(requestConfig);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();){
             HttpResponse response = httpClient.execute(httpGet);
             if(response.getStatusLine().getStatusCode() == 200){
                 String jsonResult = EntityUtils.toString(response.getEntity());
                 map = gson.fromJson(jsonResult,Map.class);
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 封装带header的头请求
     * @param url
     * @param headers
     * @return
     */
    public static Map<String,Object> doGetWithHeaders(String url,Map<String,String> headers){
        Map<String,Object> map = new HashMap<>();

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(5000)
                .setConnectTimeout(5000)
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)
                .build();

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept","application/json");

        Iterator<Map.Entry<String,String>> iterator = headers.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,String> entry = iterator.next();
            httpGet.setHeader(entry.getKey(),entry.getValue());
        }
        httpGet.setConfig(requestConfig);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();){
            HttpResponse response = httpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode() == 200){
                String jsonResult = EntityUtils.toString(response.getEntity());
                map = gson.fromJson(jsonResult,Map.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Http Post 请求
     * @return
     */
    public static String doPost(String url,Object data){
        //超时设置
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type","text/html;charset = utf-8");

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();
        httpPost.setConfig(requestConfig);

        if(data != null && data instanceof String){
            StringEntity stringEntity = new StringEntity(data.toString(),"utf-8");
            httpPost.setEntity(stringEntity);
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault();){
            HttpResponse response= httpClient.execute(httpPost);
            HttpEntity  httpEntity =response.getEntity();
            if(response.getStatusLine().getStatusCode() == 200){
                String result = EntityUtils.toString(httpEntity);
                return  result;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
