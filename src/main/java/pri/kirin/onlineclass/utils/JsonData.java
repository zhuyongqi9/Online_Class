package pri.kirin.onlineclass.Utils;

import lombok.Data;

@Data
public class JsonData {
    private int code;
    private Object Data;
    private String msg;

    private JsonData(int code, Object data, String msg) {
        this.code = code;
        Data = data;
        this.msg = msg;
    }


    public static JsonData buildSuccess(Object data) {
        return new JsonData(1, data, null);
    }

    public static JsonData buildError(String msg) {
        return new JsonData(-1, null, msg);
    }

    public static JsonData buildError(int code, String msg) {
        return new JsonData(code, null, msg);
    }

}
