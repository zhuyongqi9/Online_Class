package pri.kirin.onlineclass.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pri.kirin.onlineclass.Config.WechatConifg;
import pri.kirin.onlineclass.Model.entity.VideoOrder;
import pri.kirin.onlineclass.Service.UserService;
import pri.kirin.onlineclass.Service.VideoOrderService;
import pri.kirin.onlineclass.Utils.WechatPayUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

@Controller
@RequestMapping("/api/v1/wechat")
public class WeChatController {

    @Autowired
    private WechatConifg wechatConifg;

    @Autowired
    private VideoOrderService videoOrderService;

    /**
     * 微信支付回调
     * @param request
     * @param response
     */
    @PostMapping("/order/callback")
    public void orderCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception{

        InputStream inputStream = request.getInputStream();
        BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
        StringBuffer stringBuffer = new StringBuffer();
        String line;
       //将xml内容读取到StringBuffer中
        while ((line = bufferedReader.readLine())  != null){
            stringBuffer.append(line);
        }

        bufferedReader.close();
        inputStream.close();

        Map<String,String> callbackMap = WechatPayUtils.xmlToMap(stringBuffer.toString());
        SortedMap<String,String> sortedMap = WechatPayUtils.getSortedMap(callbackMap);

        //判断签名是否正确
        if(WechatPayUtils.isCorrectedSign(sortedMap,wechatConifg.getKey())){
            if("SUCCESS".equals(sortedMap.get("result_code"))){
                String outTradeNo = sortedMap.get("out_trade_no");
                VideoOrder dbVideoOrder = videoOrderService.findByOutTradeNo(outTradeNo);
                if(dbVideoOrder.getState() == 0){
                    dbVideoOrder.setOpenid(sortedMap.get("openid"));
                    dbVideoOrder.setOutTradeNo(outTradeNo);
                    dbVideoOrder.setNotifyTime(new Date());
                    dbVideoOrder.setState(1);
                    int rows = videoOrderService.updateOrderByOutTradeNo(dbVideoOrder);
                    if(rows == 1){
                      response.setContentType("text/xml");
                      response.getWriter().println("SUCCESS");
                    }
                }
            }
        }else {
            response.setContentType("text/xml");
            response.getWriter().println("FAIL");
        }
    }
}
