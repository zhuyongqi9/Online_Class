package pri.kirin.onlineclass.Mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pri.kirin.onlineclass.Model.entity.User;


public interface UserMapper {

    int save(User user);

    User findByPhoneAndPwd(@Param("phone") String phone, @Param("pwd") String pwd);

    User findByUserId(@Param("user_id") int userId);

    @Select("select * from user where github_id = #{github_id}")
    User findByGithubId(@Param("github_id")double githubId);
}
