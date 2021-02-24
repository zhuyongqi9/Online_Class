package pri.kirin.onlineclass.mapper;

import org.apache.ibatis.annotations.Param;
import pri.kirin.onlineclass.model.entity.Episode;
import pri.kirin.onlineclass.model.entity.PlayRecord;

public interface PlayRecordMapper {

    int savePlayRecord(PlayRecord playRecord);
}
