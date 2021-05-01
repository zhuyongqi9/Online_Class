import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;

public class GithubController_test{
    
    @Autowired
    private GithubConfig githubConfig;
    
    /**
     *GET 请求
     @return github登录地址
     * */
    @GetMapping("login")
    @RespnseBody
    public Object githubLogin(@RequestParam("state")String state){
        //获取重定向地址
        String url = githubConfig.getRedirectUrl(); 

        //获取github登录地址
        String githubLoginUrl = String.format(githubConfig.getGITHUB_LOGIN(),githubConfig.getClientId(),url,state);

        //将github登录授权地址发送给前端
        return JsonData.buildSuccess(githubLoginUrl);
   
    }

    /**
     *gitub 在验证成功之后会调用此接口，在数据库中新建此用户
     @return 重定向到homePage
     * */
    @GetMapping("/user/callback")
    public void githubCallback(@RequestParam("code"})String code,String state,HttpServletResponse){
        //TODO 用户已经注册过
       User user = userService.saveGihubUser(code,state);
       if(user != null){
            String token = JWTUtils.geneJsonWebToken(User user);
            String RedirectUrl = new StringBuilder("/api/v1/github/user/callback/home")
                .append("?token="+token)
                .append("&head_img="+user.getHeadImg())
                .append("&name="+URLEncoder.encode(user.getName(),"utf-8"))
                .toString();
            response.sendRedirect(RedirectUrl);

       }
    }

    @GetMapping("/user/callback/home")
    @RsponseBody
    public Object homepage(){
        return JsonData.buildSuccess("登录成功，正在跳转");
    }


}






 




