package pri.kirin.onlineclass.Config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource(value = "classpath:application.properties")
public class GithubConfig {

    @Value("${github.client_id}")
    private String clientId;

    @Value("${github.redirect_uri}")
    private String redirectUrl;

    @Value("${github.client_secret}")
    private String clientSecret;

    private final String GITHUB_LOGIN = "https://github.com/login/oauth/authorize?client_id=%s&redirect_uri=%s&state=%s";

    private String GITHUB_ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token?client_id=%s&client_secret=%s&code=%s&redirect_uri=%s";

    private String GITHUB_USERINFO_URL = "https://api.github.com/user?";

}
