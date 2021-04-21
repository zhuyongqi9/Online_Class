package pri.kirin.onlineclass.Model.request;

import lombok.Data;

@Data
/**
 * 封装登录请求类
 */
public class LoginRequest {
    private String phone;
    private String pwd;
}
