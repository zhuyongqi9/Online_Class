package pri.kirin.onlineclass.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pri.kirin.onlineclass.Dto.VideoOrderDto;
import pri.kirin.onlineclass.Model.entity.VideoOrder;
import pri.kirin.onlineclass.Model.request.VideoOrderRequest;
import pri.kirin.onlineclass.Service.VideoOrderService;
import pri.kirin.onlineclass.Utils.IpUtils;
import pri.kirin.onlineclass.Utils.JsonData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
@RestController
@RequestMapping("api/v1/pri/order")
public class VideoOrderController {

    @Autowired
    private VideoOrderService videoOrderService;

    /**
     * 下单接口
     *
     * @return
     */
    @PostMapping("save")
    public void saveOrder(@RequestBody VideoOrderRequest videoOrderRequest
            ,HttpServletResponse servletResponse, HttpServletRequest servletRequest) throws Exception{
        Integer userId = (Integer) servletRequest.getAttribute("user_id");

        String ip = IpUtils.getIpAddr(servletRequest);
        // String ip = "120.25.1.43";
        // int userId = 2;
        VideoOrderDto videoOrderDto = new VideoOrderDto();
        videoOrderDto.setUserId(userId);
        videoOrderDto.setVideoId(videoOrderRequest.getVideoId());
        videoOrderDto.setIp(ip);

        String QRCodeUrl = videoOrderService.save(videoOrderDto);
        if(QRCodeUrl == null) throw new NullPointerException();

        //生成二维码
        Map<EncodeHintType,Object> hints = new HashMap<>();
        //设置纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hints.put(EncodeHintType.CHARACTER_SET,"utf-8");

        BitMatrix bitMatrix = new MultiFormatWriter().encode(QRCodeUrl, BarcodeFormat.QR_CODE
                ,400,400,hints);

        OutputStream outputStream = servletResponse.getOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix,"png",outputStream);

    }

    @GetMapping("list")
    public JsonData list(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("user_id");
        List<VideoOrder> videoOrderList = videoOrderService.listOrderByUserId(userId);
        return JsonData.buildSuccess(videoOrderList);
    }
}
