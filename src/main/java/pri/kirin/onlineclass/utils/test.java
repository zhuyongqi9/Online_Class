package pri.kirin.onlineclass.Utils;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class test {

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

        Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,String> entry = iterator.next();
            httpGet.setHeader(entry.getKey(),entry.getValue());
        }

        httpGet.setConfig(requestConfig);
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpResponse response = httpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode() == 200){
                String jsonResult = EntityUtils.toString(response.getEntity());
                map = new Gson().fromJson(jsonResult,Map.class);
            }
        }catch (Exception e){

        }

    }
}
