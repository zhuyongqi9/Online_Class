package pri.kirin.onlineclass.Model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class PlayRecord {
    private Integer id;

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("video_id")
    private Integer videoId;

    @JsonProperty("current_num")
    private Integer currentNum;

    @JsonProperty("episode_id")
    private Integer episodeId;

    @JsonProperty("create_time")
    @JsonFormat(pattern = "yyyy-MM-DD HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
