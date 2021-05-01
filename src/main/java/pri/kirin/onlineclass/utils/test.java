package pri.kirin.onlineclass.Utils;

import java.util.Map;
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



public class test {

    @Autowired
    private Gson gson;
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
    while(iterator.hasNext()){
      Map.Entry<String,String> entry = iterator.next();
      httpGet.setHeader(entry.getKey(),entry.getValue());

      try(CloseableHttpClient httpClient = HttpClients.createDefault();){
        HttpRespnse response = HttpClients.createDefault():

      }
    }

  }

  /**
   * HttpPost 请求
   *
   * */
  public static String doPost(String url,Object data){
    HttpPost httpPost = new HttpPost(url);
    httpPost.setHeader("Content-Type","text/html;charset = utf-8");

    //请求配置文件
    RequestConifg requestConfig = RequsetConfig.custom()
      .setSocketTimeOut(5000)
      .setConnetTimeout(5000)
      .setConnetionRequestTimeout(5000)
      .build();

    httpPost.setConfig(requestConfig);
    
    if(data != null && data instanceof String){
        StringEntity stringEnitty = new StringEntity(data.toString(),"utf-8");
        httpPost.setEntity(stringEntity);
    }

    try(CloseableHttpClient httpClient = HttpClients.createDefault()){
      HttpResponse response = httpClient.execute(httpPost);
      HttpEntity httpEntity = response.getEntity();
      if(response.getStatusLine().getStatusCode() == 200){
        String result = EntityUtils.toString(httpEntity);
        return result;
      }
    }catch(Exception e){
        e.printStackTrack();
    }

    //Post 为空 或者不是字符串
    return null;
  }

  /**
   *带header 的 GET 请求
   * */
    
  public static Map<String,Object> doGet(String url,Map<String,String> headers){
    Map<String,Object> res = new HashMap<>();
    HttpGet httpGet = new HttpGet(url);

    RequestConfig config = new RequestConfig.custom()
      .setConnectionRequestTimeout(5000)
      .setConnectTimeout(5000)
      .setSocketTimeout(5000)
      .setRedirectsEnabled()
      .build();

    httpGet.setConfig(config);
    
    Iterator<Map.Entry<String,String>> iterator = headers.entrySet().iterator();
    while(iterator.hasNext()){
      Map.Entry<String,String> entry = iterator.next();
      httpGet.setHeader(entry.getKey(),entry.getValue());
    }

    try(CloseableHttpClient httpClient = HttpClients.createDefault()){
        HttpResponse response = httpClient.execute(httpGet);
        if(response.getStatusLine().getStatusCode() == 200){
            String jsonResult = EntityUtils.toString(response.getEntity());
            res = gson.fromJson(jsonResult,Map.class);
        }

    }catch(Exception e){
        e.printStackTrace();
    }
    return res;
  } 

  publlc static doPost(String url,Object data){
      HttpPost httpPost = new HttpPost(url);
     httpPost.setHeader("content-Type","text/html;charset=utf-8");

     RequestConfig requestConfig = RequestConifg.custom()
         .setSocket


  }
      
    

    
}
