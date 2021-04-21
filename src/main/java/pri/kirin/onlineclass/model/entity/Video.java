package pri.kirin.onlineclass.Model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class Video {
    private Integer id;
    private String title;
    private String summary;

    @JsonProperty("cover_img")
    private String coverImg;
    private Integer price;

    @JsonProperty("create_time")
    @JsonFormat(pattern = "yyyy-MM-DD HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Double point;

    @JsonProperty("chapter_list")
    private List<Chapter> chapterList;

}
