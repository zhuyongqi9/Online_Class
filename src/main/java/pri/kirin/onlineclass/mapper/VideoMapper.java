package pri.kirin.onlineclass.Mapper;

import org.apache.ibatis.annotations.Param;
import pri.kirin.onlineclass.Model.entity.Video;
import pri.kirin.onlineclass.Model.entity.VideoBanner;

import java.util.List;

public interface VideoMapper {
    /**
     * 查询全部列表
     *
     * @return
     */
    List<Video> listVideo();

    /**
     * 查询全部视频的封面
     *
     * @return
     */
    List<VideoBanner> listVideoBanner();


    Video findDetailById(@Param("video_id") int videoId);

    Video findById(@Param("video_id") int videoId);
}