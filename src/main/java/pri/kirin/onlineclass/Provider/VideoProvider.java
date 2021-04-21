package pri.kirin.onlineclass.Provider;

import org.apache.ibatis.jdbc.SQL;
import pri.kirin.onlineclass.Model.entity.Video;

/**
 * 构建Video动态SQL语句
 */
public class VideoProvider {

    public String updateVideo(final Video video){
        return new SQL(){{
            UPDATE("video");

            if(video.getCoverImg() != null){
                SET("coverImg = #{coverImg}");
            }
            if(video.getCreateTime() != null){
                SET("create_time= #{createTime}");
            }
            if(video.getPoint() != null){
                SET("point= #{point");
            }
            if(video.getPrice() != null){
                SET("price = #{price}");
            }
            if(video.getSummary() != null){
                SET("summary= #{summary}");
            }
            if(video.getTitle() != null){
                SET("title= #{title}");
            }

            WHERE("id = #{id}");
        }}.toString();
    }
}
