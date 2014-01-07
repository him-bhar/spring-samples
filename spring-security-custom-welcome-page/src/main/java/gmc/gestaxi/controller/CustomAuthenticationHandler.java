package gmc.gestaxi.controller;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

public class CustomAuthenticationHandler extends
		SimpleUrlAuthenticationSuccessHandler {
	private static Logger log = Logger.getLogger("InfoLogging");

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		log.info("Initializing CustomAuthenticationHandler");
		System.out.println("CustomAuthenticationHandler Controller");
		String userTargetUrl = "/menuUser";
		String adminTargetUrl = "/menuAdmin";
		String motTargetUrl = "/menuMot";
		Set<String> roles = AuthorityUtils.authorityListToSet(authentication
				.getAuthorities());
		log.info(roles.size());
		if (roles.contains("ROLE_ADMIN")) {
			getRedirectStrategy().sendRedirect(request, response,
					adminTargetUrl);
		} else if (roles.contains("ROLE_USER")) {
			getRedirectStrategy()
					.sendRedirect(request, response, userTargetUrl);
		} else if (roles.contains("ROLE_MOT")) {
			getRedirectStrategy().sendRedirect(request, response, motTargetUrl);
		} else {
			super.onAuthenticationSuccess(request, response, authentication);
			return;
		}
	}
}