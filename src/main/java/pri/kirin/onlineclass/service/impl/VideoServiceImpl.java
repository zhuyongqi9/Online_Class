package pri.kirin.onlineclass.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pri.kirin.onlineclass.config.CacheKeyManager;
import pri.kirin.onlineclass.model.entity.Video;
import pri.kirin.onlineclass.model.entity.VideoBanner;
import pri.kirin.onlineclass.mapper.VideoMapper;
import pri.kirin.onlineclass.service.VideoService;
import pri.kirin.onlineclass.utils.BaseCache;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private BaseCache baseCache;

    @Override
    public List<Video> listVideo() {
        try {
            Object cacheObject = baseCache.getTenMinuteCache().get(CacheKeyManager.INDEX_VIDEO_LIST,()->{
                List<Video> videoList = videoMapper.listVideo();
                System.out.println("数据库中寻找视频列表");
                return videoList;
            });

            if(cacheObject instanceof List){
                return (List<Video>) cacheObject;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<VideoBanner> listVideoBanner() {

        try {
            Object cacheObject = baseCache.getTenMinuteCache().get(CacheKeyManager.INDEX_BANNER_KEY,()->{
               List<VideoBanner> bannerList = videoMapper.listVideoBanner();
                System.out.println("数据库中寻找轮播图");
               return bannerList;
            });

            if(cacheObject instanceof List){
                List<VideoBanner> bannerList = (List<VideoBanner>) cacheObject;
                return bannerList;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Video findDetailById(int videoId) {
        String cacheKey = String.format(CacheKeyManager.INDEX_VIDEO_DETAIL,videoId);
        try {
            Object cacheObject = baseCache.getOneHourCache().get(cacheKey,()->{
                Video video = videoMapper.findDetailById(videoId);
                System.out.println("从数据库中查询视频详情");
                return video;
            });

            if(cacheObject instanceof Video)
                return (Video) cacheObject;
        }catch (ExecutionException e){
            e.printStackTrace();
        }
        return null;
    }
}
