package pri.kirin.onlineclass.mapper;

import org.apache.ibatis.annotations.Param;
import pri.kirin.onlineclass.model.entity.User;


public interface UserMapper {

    int save(User user);

    User findByPhoneAndPwd(@Param("phone") String phone,@Param("pwd") String pwd);

    User findByUserId(@Param("user_id")int userId);
}
