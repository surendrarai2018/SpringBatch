package com.diaspark.csvtomongo.config;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SecurityInterceptor extends HandlerInterceptorAdapter {
	Hashtable<String, String> validUsers = new Hashtable<>();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		validUsers.put("surendra", "authorized");
		String auth = request.getHeader("Authorization");
		if (!allowUser(auth)) {
			response.setHeader("WWW-Authenticate", "BASIC realm=\"data test\"");
			response.sendError(response.SC_UNAUTHORIZED);
		} else {
			// Allowed
			System.out.println("ALLOWED");
		}
		return true;
	}

	private boolean allowUser(String user) throws IOException {

		if (user == null) {
			System.out.println("No Auth");
			return false;
		}
		if (!user.toUpperCase().startsWith("BASIC ")) {
			System.out.println("Only Accept Basic Auth");
			return false;
		}

		String userpassEncoded = user.substring(6);
		// Decode
		sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
		String userpassDecoded = new String(dec.decodeBuffer(userpassEncoded));

		String account[] = userpassDecoded.split(":");
		System.out.println("User = " + account[0]);
		System.out.println("Pass = " + account[1]);

		if ("authorized".equals(validUsers.get(account[0]))) {
			return true;
		} else {
			return false;
		}
	}

}