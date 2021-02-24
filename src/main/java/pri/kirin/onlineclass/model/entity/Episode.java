package pri.kirin.onlineclass.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Episode {
    private Integer id;
    private String title;
    private Integer num;
    private Integer ordered;

    @JsonProperty("play_url")
    private String playUrl;

    @JsonProperty("chapter_id")
    private Integer ChapterId;
    private Integer free;

    @JsonProperty("video_id")
    private Integer videoId;

    @JsonProperty("create_time")
    @JsonFormat(pattern = "yyyy-MM-DD HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
}
