package pri.kirin.onlineclass.Model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 封装订单请求类
 */
@Data
public class VideoOrderRequest {

    @JsonProperty("video_id")
    private int videoId;


}
