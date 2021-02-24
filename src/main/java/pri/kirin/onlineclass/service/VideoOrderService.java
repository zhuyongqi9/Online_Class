package pri.kirin.onlineclass.service;

import pri.kirin.onlineclass.model.entity.VideoOrder;

import java.util.List;

public interface VideoOrderService {

    int save(int userId,int videoId);

    List<VideoOrder> listOrderByUserId(int userId);
}
