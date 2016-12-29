package com.liudw.wxapi.token;

import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.liudw.wxapi.common.WechatConfig;
import com.liudw.wxapi.utils.HttpUtils;
/**
 * 
 * @class com.liudw.wxapi.token.Token
 * @date 2016-12-5 上午11:26:12
 * @author Administrator
 * @description 应用token中继器刷新
 */
public class Token {
	/**
	 * 微信应用access_token
	 */
	private static String accessToken = null;
	/**
	 * 刷新token时的时间戳
	 */
	private static Long tokenTime = null;
	/**
	 * token持续时间
	 */
	private static Long tokenExpiresIn = null;
	/**
	 * 应用jssdk ticket
	 */
	private static String jsapiTicket = null;
//	/**
//	 * 刷新ticket时的时间戳
//	 */
//	private static Long jsapiTime = null;
//	/**
//	 * ticket持续时间
//	 */
//	private static Long jsapiExpiresIn = null;
	/**
	 * 获取access token URL，有效期7200秒
	 */
	private static String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
	/**
	 * jssdk刷新jsapi_ticket，有效期7200秒
	 */
	private static String URL_JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
	/**
	 * 获取access_token
	 * @return
	 */
	public static String getAccessToken() {
		Long nowTimestamp = Calendar.getInstance().getTimeInMillis();
		if(null == accessToken || tokenTime + tokenExpiresIn <= nowTimestamp) {
			String appid = WechatConfig.getInstance().getAppID();
			String appSecret = WechatConfig.getInstance().getAppSecret();
			String result = HttpUtils.sendForGET(String.format(URL_ACCESS_TOKEN, appid, appSecret));
			if(StringUtils.isNotBlank(result)) {
				JSONObject json = JSONObject.parseObject(result);
				if(!json.containsKey("errcode")) {
					accessToken = json.getString("access_token");
					tokenExpiresIn = json.getLong("expires_in");
					tokenTime = nowTimestamp;
				}
			}
		}
		return accessToken;
	}
	/**
	 * 获取jssdk ticket
	 * @return
	 */
	public static String getJsapiTicket() {
		String dist = accessToken;
		if(null == jsapiTicket || !dist.equals(getAccessToken())) {
			String result = HttpUtils.sendForGET(String.format(URL_JSAPI_TICKET, accessToken));
			JSONObject json = JSONObject.parseObject(result);
			if(json.getInteger("errcode") == 0) {
				jsapiTicket = json.getString("ticket");
			}
		}
		return jsapiTicket;
	}
}
