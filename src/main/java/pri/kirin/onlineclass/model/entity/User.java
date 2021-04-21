package pri.kirin.onlineclass.Model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class User {
    private Integer id;
    private String name;

    @JsonIgnore
    private String pwd;

    @JsonProperty("head_img")
    private String headImg;
    private String phone;

    @JsonProperty("create_time")
    @JsonFormat(pattern = "yyyy-MM-DD HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
