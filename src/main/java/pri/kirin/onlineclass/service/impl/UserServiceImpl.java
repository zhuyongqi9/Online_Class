package pri.kirin.onlineclass.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pri.kirin.onlineclass.model.entity.User;
import pri.kirin.onlineclass.mapper.UserMapper;
import pri.kirin.onlineclass.service.UserService;
import pri.kirin.onlineclass.utils.CommonUtils;
import pri.kirin.onlineclass.utils.JWTUtils;

import java.util.Date;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 将对象保存到数据库中
     * @param userInfo
     * @return
     */
    @Override
    public int save(Map<String, String> userInfo) {
        User user = parseToUser(userInfo);
        if(user != null) return userMapper.save(user);
        else return -1;
    }

    /**
     * 判断是否登录
     * @param phone
     * @param pwd
     * @return 登录成功后生成的token
     */
    @Override
    public String findByPhoneAndPwd(String phone, String pwd) {
        User user = userMapper.findByPhoneAndPwd(phone,CommonUtils.MD5(pwd));
        if(user == null) return null;
        else {
            return JWTUtils.geneJsonWebToken(user);
        }
    }

    /**
     * 通过user_id 寻找到对应的user
     * @param userId
     * @return
     */
    @Override
    public User findByUserId(int userId) {
        User user = userMapper.findByUserId(userId);
        return user;
    }

    private static final String [] headImg = {
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/12.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/11.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/13.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/14.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/15.jpeg"
    };

    /**
     * 将传入的map解析为user对象
     * @param userInfo
     * @return
     */
    private User parseToUser(Map<String, String> userInfo) {
        if(userInfo.containsKey("phone") && userInfo.containsKey("pwd") && userInfo.containsKey("name")){
            User user = new User();
            user.setName(userInfo.get("name"));
            user.setPhone(userInfo.get("phone"));
            user.setHeadImg(headImg[(int) (5*Math.random())]);
            user.setCreateTime(new Date());
            String pwd = userInfo.get("pwd");
            //MD5加密
            pwd = CommonUtils.MD5(pwd);
            user.setPwd(pwd);
            return user;
        }else return null;
    }
}
