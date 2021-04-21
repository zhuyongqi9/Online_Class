package pri.kirin.onlineclass.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pri.kirin.onlineclass.Service.VideoService;
import pri.kirin.onlineclass.Utils.JsonData;


@RestController
@RequestMapping("/api/v1/pub/video")
public class VideoController {
    @Autowired
    private VideoService videoService;


    @GetMapping("list_banner")
    public JsonData listVideoBanner() {
        return JsonData.buildSuccess(videoService.listVideoBanner());
    }

    @RequestMapping("list")
    public JsonData listVideo() {
        return JsonData.buildSuccess(videoService.listVideo());
    }

    @GetMapping("find_detail_by_id")
    public JsonData findDetailById(@RequestParam(value = "video_id", required = true) int videoId) {
        return JsonData.buildSuccess(videoService.findDetailById(videoId));
    }
}
