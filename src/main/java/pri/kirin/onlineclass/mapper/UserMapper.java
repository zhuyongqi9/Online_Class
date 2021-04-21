package pri.kirin.onlineclass.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pri.kirin.onlineclass.Model.entity.User;


public interface UserMapper {

    int save(User user);

    User findByPhoneAndPwd(@Param("phone") String phone, @Param("pwd") String pwd);

    User findByUserId(@Param("user_id") int userId);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id")int userId);

    @Select("select * from user where github_id = #{github_id}")
    User findByGithubId(@Param("github_id")double githubId);

    @Insert("INSERT INTO `user` (`openid`, `name`, `head_img`, `phone`, `sign`, `city`, `create_time`, `email`,`github_id`)" +
            "VALUES" +
            "(NULL, #{name}, #{headImg}, NULL, '全栈工程师',  NULL, #{createTime}, #{email},#{githubId});")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    int saveUser(User user);
}
