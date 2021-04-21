package pri.kirin.onlineclass.Model.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String phone;
    private String pwd;
}
