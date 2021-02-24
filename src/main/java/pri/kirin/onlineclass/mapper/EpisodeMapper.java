package pri.kirin.onlineclass.mapper;

import org.apache.ibatis.annotations.Param;
import pri.kirin.onlineclass.model.entity.Episode;

public interface EpisodeMapper {

    Episode findFirstEpisodeById(@Param("video_id") int videoId);
}
