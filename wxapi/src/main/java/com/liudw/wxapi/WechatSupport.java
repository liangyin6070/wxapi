package com.liudw.wxapi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.liudw.wxapi.common.SHA1;
import com.liudw.wxapi.common.WechatConfig;
import com.liudw.wxapi.oauth2.SnsapiBaseManage;
import com.liudw.wxapi.user.UserModel;
import com.liudw.wxapi.utils.ResponseUtils;

/**
 * 
 * @class com.liudw.wxapi.WechatSupport
 * @date 2016-12-5 下午4:10:34
 * @author Administrator
 * @description 微信业务入口类
 */
public abstract class WechatSupport {
	
	private static Logger log = LoggerFactory.getLogger(WechatSupport.class);
	
	/**
	 * 验证微信平台是否接入成功
	 * @param request
	 * @param response
	 */
	public void checkURL(HttpServletRequest request, HttpServletResponse response) {
		String signature = request.getParameter("signature"); 
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		String[] str = { WechatConfig.getInstance().getToken(), timestamp, nonce };
        Arrays.sort(str); // 字典序排序
        String bigStr = str[0] + str[1] + str[2];
        // SHA1加密
        String digest = new SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();
        if(digest.equals(signature)) {
        	ResponseUtils.renderText(response, echostr);
        } 
	}
	/**
	 * 微信菜单入口URL,
	 * ##http://testwx.smart2run.cn/tour/transfer/toHtml.htm?refer=Index
	 * 根据refer判断并从redirect.properties中读取相应的URL重定向到具体业务URL
	 * @param request
	 * @param response
	 */
	public void redirectToWechat(HttpServletRequest request, HttpServletResponse response) {
		//组装重定向到微信服务器的参数
		StringBuilder urlParams = new StringBuilder();
		urlParams.append(request.getHeader("Host")).append(WechatConfig.getInstance().getCommonRedirectURI());
		Enumeration enu = request.getParameterNames();
		int i = 0;
		while(enu.hasMoreElements()){  
			String paraName = (String)enu.nextElement();  
			String value = request.getParameter(paraName);
			if(i != 0) {
				urlParams.append("&");
			} else {
				urlParams.append("?");
			}
			urlParams.append(paraName).append("=").append(value);
			i++;
		}
		try {
			String redirect = SnsapiBaseManage.snsapiBase(urlParams.toString(), "transfer2016");
			response.sendRedirect(redirect);
		} catch (UnsupportedEncodingException e) {
			log.error("urlencode失败", e);
		} catch (IOException e) {
			log.error("重定向到页面", e);
		}
	}
	/**
	 * 接受微信重定向回来的数据，并判断用户关注与否，跳转到其他业务页面
	 * @param request
	 * @param response
	 * @param refer
	 */
	public void redirectToLocalUrl(HttpServletRequest request, HttpServletResponse response, String refer) {
		String code = request.getParameter("code");
		UserModel user = SnsapiBaseManage.getOpenIdForSnsapiBase(code);
		Properties prop = new Properties();
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("redirect.properties"));
			String url = null;	
			if(user.getSubscribe() == 0) {
				url= prop.getProperty("scanURL").trim();
				response.sendRedirect(url);
			} else {
				request.getSession(false).setAttribute("wechat_user", user);
				url = prop.getProperty(refer).trim();
				response.sendRedirect(editUrlParams(url, request));
			}
		} catch (IOException e) {
			log.error("", e);
		}
	}
	/**
	 * 创建重定向本地服务器的url
	 * @param url
	 * @param request
	 * @return
	 */
	public String editUrlParams(String url, HttpServletRequest request) {
		StringBuilder urlParams = new StringBuilder();
		urlParams.append(url);
		Enumeration enu = request.getParameterNames();
		int i = 0;
		while(enu.hasMoreElements()){  
			String paraName = (String)enu.nextElement();  
			String value = request.getParameter(paraName);
			if(!paraName.equals("refer") && !paraName.equals("state") && !paraName.equals("code")) {
				if(i != 0) {
					urlParams.append("&");
				} else {
					urlParams.append("?");
				}
				urlParams.append(paraName).append("=").append(value);
				i++;
			}
		}
		return urlParams.toString();
	}
}
