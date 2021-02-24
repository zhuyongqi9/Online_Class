package pri.kirin.onlineclass.service;

import pri.kirin.onlineclass.model.entity.Video;
import pri.kirin.onlineclass.model.entity.VideoBanner;

import java.util.List;

public interface VideoService {

    List<Video> listVideo();

    List<VideoBanner> listVideoBanner();

    Video findDetailById(int videoId);
}
