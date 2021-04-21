package pri.kirin.onlineclass.Service;

import pri.kirin.onlineclass.Model.entity.VideoOrder;

import java.util.List;

public interface VideoOrderService {

    int save(int userId, int videoId);

    List<VideoOrder> listOrderByUserId(int userId);
}
