package pri.kirin.onlineclass.Mapper;

import org.apache.ibatis.annotations.Param;
import pri.kirin.onlineclass.Model.entity.VideoOrder;

import java.util.List;

public interface VideoOrderMapper {

    /**
     * 查询用户是否购买过此商品
     *
     * @param userId
     * @param videoId
     * @param state
     * @return
     */
    VideoOrder findByVideoIdAndOrderIdAndState(@Param("user_id") int userId, @Param("video_id") int videoId
            , @Param("state") int state);

    int saveOrder(VideoOrder videoOrder);

    List<VideoOrder> listOrderByUserId(@Param("user_id") int userId);
}
