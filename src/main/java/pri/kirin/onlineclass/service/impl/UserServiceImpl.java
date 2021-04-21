package pri.kirin.onlineclass.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pri.kirin.onlineclass.Config.GithubConfig;
import pri.kirin.onlineclass.Model.entity.User;
import pri.kirin.onlineclass.Mapper.UserMapper;
import pri.kirin.onlineclass.Service.UserService;
import pri.kirin.onlineclass.Utils.CommonUtils;
import pri.kirin.onlineclass.Utils.HttpUtils;
import pri.kirin.onlineclass.Utils.JWTUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GithubConfig githubConfig;

    /**
     * 将对象保存到数据库中
     *
     * @param userInfo
     * @return
     */
    @Override
    public int save(Map<String, String> userInfo) {
        User user = parseToUser(userInfo);
        if (user != null) return userMapper.save(user);
        else return -1;
    }

    /**
     * 判断是否登录
     *
     * @param phone
     * @param pwd
     * @return 登录成功后生成的token
     */
    @Override
    public String findByPhoneAndPwd(String phone, String pwd) {
        User user = userMapper.findByPhoneAndPwd(phone, CommonUtils.geneMD5(pwd));
        if (user == null) return null;
        else {
            return JWTUtils.geneJsonWebToken(user);
        }
    }

    /**
     * 通过user_id 寻找到对应的user
     *
     * @param userId
     * @return
     */
    @Override
    public User findByUserId(int userId) {
        User user = userMapper.findByUserId(userId);
        return user;
    }

    private static final String[] headImg = {
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/12.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/11.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/13.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/14.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/15.jpeg"
    };

    /**
     * 将传入的map解析为user对象
     *
     * @param userInfo
     * @return
     */
    private User parseToUser(Map<String, String> userInfo) {
        if (userInfo.containsKey("phone") && userInfo.containsKey("pwd") && userInfo.containsKey("name")) {
            User user = new User();
            user.setName(userInfo.get("name"));
            user.setPhone(userInfo.get("phone"));
            user.setHeadImg(headImg[(int) (5 * Math.random())]);
            user.setCreateTime(new Date());
            String pwd = userInfo.get("pwd");
            //MD5加密
            pwd = CommonUtils.geneMD5(pwd);
            user.setPwd(pwd);
            return user;
        } else return null;
    }

    @Override
    public User saveGithubUser(String code,String state) {
        String accessTokenUrl = String.format(githubConfig.getGITHUB_ACCESS_TOKEN_URL(),
                githubConfig.getClientId(),
                githubConfig.getClientSecret(),
                code,
                githubConfig.getRedirectUrl());
        Map<String,Object> baseMap = HttpUtils.doGet(accessTokenUrl);
        if(baseMap == null || baseMap.isEmpty()) return null;
        String authToken = (String) baseMap.get("access_token");

        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization"," token " + authToken);

        Map<String,Object> map = HttpUtils.doGetWithHeaders(githubConfig.getGITHUB_USERINFO_URL(),headers);

        Double githubId = (Double) map.get("id");

        //如果用户已经登陆过
        User user = userMapper.findByGithubId(githubId);
        if(user != null) return user;

        String name = (String) map.get("name");
        String email = (String) map.get("email");
        String head_img = (String) map.get("avatar_url");

        user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setCreateTime(new Date());
        user.setHeadImg(head_img);
        user.setGithubId(githubId);

        userMapper.save(user);

        return user;
    }
}
