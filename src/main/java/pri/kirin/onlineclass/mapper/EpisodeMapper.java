package pri.kirin.onlineclass.Mapper;

import org.apache.ibatis.annotations.Param;
import pri.kirin.onlineclass.Model.entity.Episode;

public interface EpisodeMapper {

    Episode findFirstEpisodeById(@Param("video_id") int videoId);
}
