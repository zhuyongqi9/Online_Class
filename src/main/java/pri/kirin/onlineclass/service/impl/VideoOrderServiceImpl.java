package pri.kirin.onlineclass.Service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pri.kirin.onlineclass.Config.WechatConifg;
import pri.kirin.onlineclass.Dto.VideoOrderDto;
import pri.kirin.onlineclass.Exception.CommonException;
import pri.kirin.onlineclass.Mapper.*;
import pri.kirin.onlineclass.Model.entity.*;
import pri.kirin.onlineclass.Service.VideoOrderService;
import pri.kirin.onlineclass.Utils.CommonUtils;
import pri.kirin.onlineclass.Utils.HttpUtils;
import pri.kirin.onlineclass.Utils.WechatPayUtils;

import java.util.*;

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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WechatConifg wechatConifg;

    private Logger logger= LoggerFactory.getLogger(this.getClass());
    private Logger dataLogger = LoggerFactory.getLogger("dataLogger");

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


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public  String save(VideoOrderDto videoOrderDto) {

        dataLogger.info("module = video_order ` api = save ` user_id = {}",videoOrderDto.getUserId());

        Video video = videoMapper.findById(videoOrderDto.getVideoId());

        User user = userMapper.findById(videoOrderDto.getUserId());

        //生成订单
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setTotalFee(video.getPrice());
        videoOrder.setVideoTitle(video.getTitle());
        videoOrder.setUserId(user.getId());
        videoOrder.setVideoId(video.getId());
        videoOrder.setCreateTime(new Date());
        videoOrder.setState(0);
        videoOrder.setDel(0);
        videoOrder.setIp(videoOrderDto.getIp());
        videoOrder.setOutTradeNo(CommonUtils.geneUUID());

        videoOrderMapper.insert(videoOrder);

        String QRCodeUrl = unifiedOrder(videoOrder);

        return QRCodeUrl;
    }

    @Override
    public VideoOrder findByOutTradeNo(String outTradeNo) {
        return videoOrderMapper.findByOutTradeNo(outTradeNo);
    }

    @Override
    public int updateOrderByOutTradeNo(VideoOrder videoOrder) {
        return videoOrderMapper.updateVideoOrderByOutTradeNo(videoOrder);
    }

    private String unifiedOrder(VideoOrder videoOrder) {

        //生成签名
        SortedMap<String,String> parms = new TreeMap<>();

        parms.put("appid",wechatConifg.getAppId());
        parms.put("mch_id",wechatConifg.getMchId());
        parms.put("nonce_str",CommonUtils.geneUUID());
        parms.put("body",videoOrder.getVideoTitle());
        parms.put("out_trade_no",videoOrder.getOutTradeNo());
        parms.put("total_fee",videoOrder.getTotalFee().toString());
        parms.put("spbill_create_ip",videoOrder.getIp());
        parms.put("notify_url",wechatConifg.getCallbackUrl());
        parms.put("trade_type","NATIVE");

        String key = wechatConifg.getKey();
        String sign = WechatPayUtils.createSign(parms,key);

        parms.put("sign",sign);

        String payXml = null;
        try {
            payXml = WechatPayUtils.mapToXml(parms);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //下单
        String orderStr = HttpUtils.doPost(wechatConifg.getUNIFIED_ORDER_URL(),payXml);
        if(orderStr == null) return null;

        Map<String,String> map = new HashMap<>();

        try {
            map = WechatPayUtils.xmlToMap(orderStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return orderStr != null ? orderStr : null;

        String QRCodeUrl = map.get("code_url");
        return QRCodeUrl;
    }
}
