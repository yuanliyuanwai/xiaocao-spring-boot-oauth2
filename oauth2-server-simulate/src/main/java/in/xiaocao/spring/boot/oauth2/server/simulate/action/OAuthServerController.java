package in.xiaocao.spring.boot.oauth2.server.simulate.action;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class OAuthServerController {
	
	private static String getLocalHost() throws UnknownHostException {
		// return InetAddress.getLocalHost().getHostAddress();
		return "localhost";
	}

	private static String getRedirect_uri() throws UnknownHostException {
		return "http://" + getLocalHost() + ":7001/index";
	}

	/**
	 * （A）用户访问客户端，后者将前者导向认证服务器，即跳转到登录页面
	 */
	@RequestMapping("/authorize")
	public String authorize(String response_type, String client_id, String redirect_uri, String scope, String state) {
		return "login";
	}

	/**
	 * （C）假设用户给予授权，认证服务器将用户导向客户端事先指定的"重定向URI"（redirection URI），同时附上一个授权码。
	 */
	@RequestMapping("/login")
	public void login(String username, String password, HttpServletResponse response) throws IOException {
		// 验证用户名密码是否正确
		response.sendRedirect(getRedirect_uri() + "?code=xxx&state=hehe");
	}

	/**
	 * （E）认证服务器核对了授权码和重定向URI，确认无误后，向客户端发送访问令牌（access token）和更新令牌（refresh token）。
	 */
	@RequestMapping("/getTokenByCode")
	@ResponseBody
	public String getTokenByCode(String grant_type, String code, String client_id, String redirect_uri)
			throws IOException {
		// 判断client_id、redirect_uri、code是否正确
		// 返回token
		return "{access_token:yyy," + "token_type:bearer," + "expires_in:600," + "refresh_token:zzz}";
	}

	/**
	 * （F）资源服务器确认令牌无误，同意向客户端开放资源。。
	 */
	@RequestMapping("getUserinfoByToken")
	@ResponseBody
	public String getUserinfoByToken(String token) throws IOException {
		// 判断token是否正确
		return "{userGuid:j3jlk2jj32li43i," + "username:Tom," + "mobile:18811412324}";
	}

}

