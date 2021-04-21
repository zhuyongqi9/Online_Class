package pri.kirin.onlineclass.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pri.kirin.onlineclass.Interceptor.CorsInterceptor;
import pri.kirin.onlineclass.Interceptor.LoginInterceptor;

/**
 * 拦截器配置
 * <p>
 * 需要权限url      api/v1/pub/
 * 不需要权限url    api/v1/pri/
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Bean
    CorsInterceptor corsInterceptor() {
        return new CorsInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(corsInterceptor()).addPathPatterns("/**");

        //拦截器一定要加"/"
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/api/v1/pri/*/*/**")
                //不会被拦截
                .excludePathPatterns("/api/v1/pri/user/login", "/api/v1/pri/user/register");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
