package pri.kirin.onlineclass.Model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@NoArgsConstructor
public class VideoBanner {
    private Integer id;
    private String url;
    private String img;

    @JsonProperty("create_time")
    @JsonFormat(pattern = "yyyy-MM-DD HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Float weight;

}
