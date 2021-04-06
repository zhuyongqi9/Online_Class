package pri.kirin.onlineclass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pri.kirin.onlineclass.model.entity.VideoOrder;
import pri.kirin.onlineclass.model.request.VideoOrderRequest;
import pri.kirin.onlineclass.service.VideoOrderService;
import pri.kirin.onlineclass.utils.JsonData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/v1/pri/order")
public class VideoOrderController {

    @Autowired
    private VideoOrderService videoOrderService;

    /**
     * 下单接口
     * @return
     */
    @PostMapping("save")
    public JsonData saveOrder(@RequestBody VideoOrderRequest videoOrderRequest, HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("user_id");
        int rows = videoOrderService.save(userId,videoOrderRequest.getVideoId());

        return rows == 0 ? JsonData.buildError("下单失败") : JsonData.buildSuccess("下单成功");
    }

    @GetMapping("list")
    public JsonData list(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("user_id");
        List<VideoOrder> videoOrderList = videoOrderService.listOrderByUserId(userId);
        return JsonData.buildSuccess(videoOrderList);
    }
}
