package pri.kirin.onlineclass.Service;

import pri.kirin.onlineclass.Model.entity.User;

import java.util.Map;

public interface UserService {

    int save(Map<String, String> userInfo);

    String findByPhoneAndPwd(String phone, String pwd);

    User findByUserId(int userId);

    User saveGithubUser(String code,String state);
}
