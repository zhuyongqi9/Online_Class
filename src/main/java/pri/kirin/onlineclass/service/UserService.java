package pri.kirin.onlineclass.service;

import pri.kirin.onlineclass.model.entity.User;

import java.util.Map;

public interface UserService {

    int save(Map<String,String> userInfo);

    String findByPhoneAndPwd(String phone, String pwd);

    User findByUserId(int userId);
}
