package pri.kirin.onlineclass.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pri.kirin.onlineclass.Config.CacheKeyManager;
import pri.kirin.onlineclass.Model.entity.Video;
import pri.kirin.onlineclass.Model.entity.VideoBanner;
import pri.kirin.onlineclass.Mapper.VideoMapper;
import pri.kirin.onlineclass.Service.VideoService;
import pri.kirin.onlineclass.Utils.BaseCache;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private BaseCache baseCache;

    /**
     * 返回视频列表
     *
     * @return
     */
    @Override
    public List<Video> listVideo() {
        try {
            Object cacheObject = baseCache.getTenMinuteCache().get(CacheKeyManager.INDEX_VIDEO_LIST, () -> {
                List<Video> videoList = videoMapper.listVideo();
                System.out.println("数据库中寻找视频列表");
                return videoList;
            });

            if (cacheObject instanceof List) {
                return (List<Video>) cacheObject;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 在缓存中提取轮播图列表
     *
     * @return
     */
    @Override
    public List<VideoBanner> listVideoBanner() {

        try {
            Object cacheObject = baseCache.getTenMinuteCache().get(CacheKeyManager.INDEX_BANNER_KEY, () -> {
                List<VideoBanner> bannerList = videoMapper.listVideoBanner();
                System.out.println("数据库中寻找轮播图");
                return bannerList;
            });

            if (cacheObject instanceof List) {
                return (List<VideoBanner>) cacheObject;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 通过user_id 寻找视频详情
     *
     * @param videoId
     * @return
     */
    @Override
    public Video findDetailById(int videoId) {
        String cacheKey = String.format(CacheKeyManager.INDEX_VIDEO_DETAIL, videoId);
        try {
            Object cacheObject = baseCache.getOneHourCache().get(cacheKey, () -> {
                Video video = videoMapper.findDetailById(videoId);
                System.out.println("从数据库中查询视频详情");
                return video;
            });

            if (cacheObject instanceof Video)
                return (Video) cacheObject;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
