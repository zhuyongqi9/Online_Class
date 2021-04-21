package pri.kirin.onlineclass.Exception;

import lombok.Data;

/**
 * 自定义异常类
 */
@Data
public class CommonException extends RuntimeException {
    private Integer code;
    private String msg;

    public CommonException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
