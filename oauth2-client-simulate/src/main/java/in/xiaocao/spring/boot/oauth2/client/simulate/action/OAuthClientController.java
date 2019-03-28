package in.xiaocao.spring.boot.oauth2.client.simulate.action;

import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class OAuthClientController {

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	private static String getLocalHost() throws UnknownHostException {
		// return InetAddress.getLocalHost().getHostAddress();
		return "localhost";
	}

	@RequestMapping("/leadToAuthorize")
	public void leadToAuthorize(HttpServletResponse response) throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("http://" + getLocalHost() + ":7000/authorize?");
		builder.append("response_type=code&");
		builder.append("client_id=10000032&");
		builder.append("redirect_uri=http://"+ getLocalHost() +":7001/index&");
		builder.append("scope=userinfo&");
		builder.append("state=hehe");
		response.sendRedirect(builder.toString());
	}

	@RequestMapping("/index")
	public String index(String code, HttpServletRequest request) throws Exception {
		RestTemplate restTemplate = new RestTemplateBuilder().build();
		/**
		 * （D）客户端收到授权码，附上早先的"重定向URI"，向认证服务器申请令牌。这一步是在客户端的后台的服务器上完成的，对用户不可见。
		 */
		String accessToken = restTemplate
				.getForObject("http://" + getLocalHost() + ":7000/getTokenByCode?" + "grant_type=authorization_code&"
						+ "code=xxx&" + "redirect_uri=http://" + getLocalHost() + ":7001/index", String.class);
		System.out.println("the access token is :" + accessToken);
		/**
		 * 发起通过token换用户信息的请求
		 */
		String username = restTemplate.getForObject(
				"http://" + getLocalHost() + ":7000/getUserinfoByToken?" + "access_token=yyy", String.class);
		request.getSession().setAttribute("username", username);
		return "index";
	}

	@RequestMapping("getUserInfo")
	@ResponseBody
	public String getUserInfo(HttpServletRequest request) throws Exception {
		Object username = request.getSession().getAttribute("username");
		return "Tom 18811311416";
	}

}
