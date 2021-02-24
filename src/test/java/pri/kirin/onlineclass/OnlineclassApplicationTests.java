package pri.kirin.onlineclass;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pri.kirin.onlineclass.model.entity.User;
import pri.kirin.onlineclass.utils.JWTUtils;

@SpringBootTest
class OnlineclassApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testGeneToken(){
		User user = new User();
		user.setId(66);
		user.setName("kirin");
		user.setHeadImg("head");
		String token = JWTUtils.geneJsonWebToken(user);

		try {
			Thread.sleep(4000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Claims claims = JWTUtils.checkJWT(token);
		System.out.println(token);
		System.out.println(claims);
	}

}
