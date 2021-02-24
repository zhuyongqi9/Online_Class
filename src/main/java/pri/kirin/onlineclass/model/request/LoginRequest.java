package pri.kirin.onlineclass.model.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String phone;
    private String pwd;
}
