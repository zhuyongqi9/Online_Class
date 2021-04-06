package pri.kirin.onlineclass.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pri.kirin.onlineclass.utils.JWTUtils;
import pri.kirin.onlineclass.utils.JsonData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 进入Controller之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("token");
        if(accessToken == null) accessToken = request.getParameter("token");

        if(StringUtils.isNotBlank(accessToken)){
            Claims claims = JWTUtils.checkJWT(accessToken);
            if(claims != null) {
                Integer id = (Integer) claims.get("id");
                String name = (String) claims.get("name");

                request.setAttribute("user_id",id);
                request.setAttribute("user_name",name);
                return true;
            }else {
                ObjectMapper objectMapper = new ObjectMapper();
                response.setContentType("application/json;charset=utf-8");
                PrintWriter printWriter = response.getWriter();
                printWriter.write(objectMapper.writeValueAsString(JsonData.buildError("未登录，请重新登录")));
                printWriter.close();
                response.flushBuffer();
                return false;
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(objectMapper.writeValueAsString(JsonData.buildError("未登录，请重新登录")));
        printWriter.close();
        response.flushBuffer();
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
