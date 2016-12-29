package com.liudw.wxapi.oauth2;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.liudw.wxapi.common.WechatConfig;
import com.liudw.wxapi.user.UserManage;
import com.liudw.wxapi.user.UserModel;
import com.liudw.wxapi.utils.HttpChartsetUtils;
import com.liudw.wxapi.utils.HttpUtils;
/**
 * 
 * @class com.liudw.wxapi.oauth2.SnsapiBaseManage
 * @date 2016-12-5 下午3:35:24
 * @author Administrator
 * @description 静默授权管理
 */
public class SnsapiBaseManage {
	private static Logger log = LoggerFactory.getLogger(SnsapiBaseManage.class);
	/**
	 * 静默授权
	 */
	private static String SNSAPI_BASE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=%s#wechat_redirect";
	/**
	 * 网页access_token
	 */
	private static String URL_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
	
	/**
	 * 获取静默授权URL
	 * @param redirectUri 返回URL
	 * @param state
	 * @return
	 */
	public static String snsapiBase(String redirectUri, String state) {
		String redirect = null;
		try {
			redirect = URLEncoder.encode(redirectUri, HttpChartsetUtils.CHARSET_UTF8);
		} catch (UnsupportedEncodingException e) {
			log.error("静默授权重定向页面转义异常", e);
		}
		return String.format(SNSAPI_BASE, WechatConfig.getInstance().getAppID(), redirect, state);
	}
	
	/**
	 * 通过code获取静默授权页面进入的微信用户信息
	 * @param code
	 * @return
	 */
	public static UserModel getOpenIdForSnsapiBase(String code) {
		String webTokenUrl = String.format(URL_TOKEN, WechatConfig.getInstance().getAppID(), WechatConfig.getInstance().getAppSecret(), code);
		String result = HttpUtils.sendForGET(webTokenUrl);
		JSONObject json = JSONObject.parseObject(result);
		if(json.containsKey("errcode")) {
			log.info("通过code换取网页授权access_token: "+json.toString());
			return null;
		} else {
			return UserManage.getUserInfo(json.getString("openid"));
		}
	}
}
