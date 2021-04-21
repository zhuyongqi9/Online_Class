package pri.kirin.onlineclass.Service;

import pri.kirin.onlineclass.Model.entity.Video;
import pri.kirin.onlineclass.Model.entity.VideoBanner;

import java.util.List;

public interface VideoService {

    List<Video> listVideo();

    List<VideoBanner> listVideoBanner();

    Video findDetailById(int videoId);
}
