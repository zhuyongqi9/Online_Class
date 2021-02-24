package pri.kirin.onlineclass.exception;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pri.kirin.onlineclass.utils.JsonData;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData handle(Exception e){

        if(e instanceof CommonException){
            CommonException commonException = (CommonException)e;
            return JsonData.buildError(commonException.getCode(),commonException.getMsg());
        }else {
            return JsonData.buildError("全局异常，未知错误");
        }

    }

}
