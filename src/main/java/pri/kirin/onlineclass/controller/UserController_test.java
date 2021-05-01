public class UserController{

    @Autowired
    private UserService userService

    @PostMapping("register")
    public Object register(@RequestBody Map<String,String> userInfo){
        int rows = userService.save(userInfo);
        return rows == 1 ? JsonData.buildSuccess("注册成功") : JsonData.buildError("注册失败，请重试");
    }

    /**
     *登录校验
     @return 
     Null : 登录失败
     token : 登录成功
     * */
    @PostMapping("login")
    public Object login(@RequestBody LoginRequest loginRequest){
        String token = userService.findByPhoneAndPwd(loginRequest.getPhone(),loginReuest.getPwd());

        return token == null ? JsonData.buildError("账号名或密码错误，请重新登录") : JsonData.buildSuccess(token);   
    }

    public JsonData findUserByToken(HttpServletRequest request){
        Integer userId = (Integer)request.getAttribute("user_id");
        
        if(userId == null) return JsonData.buildError("查询失败");
        User user = userService.findByUserId(userId);
        return JsonData.buildSuccess(user);
    } 

}
