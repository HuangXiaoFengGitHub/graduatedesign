package com.example.graduatedesign.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证验证码类，在后台注册信息之前加入此方法进行验证
 */
public class CodeUtil {
	public static boolean checkVerifyCode(HttpServletRequest request) {
		//实际的验证码
		String verifyCodeExpected = (String) request.getSession().getAttribute(
				com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		//用户输入的验证码
		String verifyCodeActual = HttpServletRequestUtil.getString(request,
				"verifyCodeActual");
		if (verifyCodeActual == null
				|| !verifyCodeActual.equalsIgnoreCase(verifyCodeExpected)) {
			return false;
		}
		return true;
	}
}
