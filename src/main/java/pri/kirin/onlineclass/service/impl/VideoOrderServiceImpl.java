package pri.kirin.onlineclass.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pri.kirin.onlineclass.Exception.CommonException;
import pri.kirin.onlineclass.Mapper.*;
import pri.kirin.onlineclass.Model.entity.Episode;
import pri.kirin.onlineclass.Model.entity.PlayRecord;
import pri.kirin.onlineclass.Model.entity.Video;
import pri.kirin.onlineclass.Model.entity.VideoOrder;
import pri.kirin.onlineclass.Service.VideoOrderService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class VideoOrderServiceImpl implements VideoOrderService {

    @Autowired
    private VideoOrderMapper videoOrderMapper;

    @Autowired
    private EpisodeMapper episodeMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private PlayRecordMapper playRecordMapper;

    @Transactional
    @Override
    public int save(int userId, int videoId) {
        //判断是否重复购买
        VideoOrder videoOrder = videoOrderMapper.findByVideoIdAndOrderIdAndState(userId, videoId, 1);
        if (videoOrder != null) {
            return 0;
        }

        Video video = videoMapper.findById(videoId);
        VideoOrder newVideoOrder = new VideoOrder();
        newVideoOrder.setCreateTime(new Date());
        newVideoOrder.setOutTradeNo(UUID.randomUUID().toString());
        newVideoOrder.setVideoId(videoId);
        newVideoOrder.setUserId(userId);
        newVideoOrder.setVideoTitle(video.getTitle());
        newVideoOrder.setVideoImg(video.getCoverImg());
        newVideoOrder.setTotalFee(video.getPrice());
        newVideoOrder.setState(1);
        int rows = videoOrderMapper.saveOrder(newVideoOrder);

        if (rows == 1) {
            Episode episode = episodeMapper.findFirstEpisodeById(videoId);
            if (episode == null) throw new CommonException(-1, "视频下暂无内容");
            PlayRecord playRecord = new PlayRecord();

            playRecord.setCreateTime(new Date());
            playRecord.setCurrentNum(episode.getNum());
            playRecord.setEpisodeId(episode.getId());
            playRecord.setUserId(userId);
            playRecord.setVideoId(videoId);

            playRecordMapper.savePlayRecord(playRecord);
        }
        return rows;
    }

    @Override
    public List<VideoOrder> listOrderByUserId(int userId) {
        return videoOrderMapper.listOrderByUserId(userId);
    }
}
