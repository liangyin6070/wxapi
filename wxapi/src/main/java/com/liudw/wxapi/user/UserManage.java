package com.liudw.wxapi.user;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import com.liudw.wxapi.token.Token;
import com.liudw.wxapi.utils.HttpUtils;
/**
 * 
 * @class com.liudw.wxapi.user.UserManage
 * @date 2016-12-5 下午1:33:10
 * @author Administrator
 * @description 微信用户管理
 */
public class UserManage {
	private static Logger log = LoggerFactory.getLogger(UserManage.class);
	/**
	 * 获取用户详情
	 */
	private static String URL_GET_USERINFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";
	/**
	 * 获取用户openid列表
	 */
	private static String URL_GETUSERLIST = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=%s&next_openid=%s";

	/**
	 * 获取微信用户详情
	 * @param openid
	 * @return
	 */
	public static UserModel getUserInfo(String openid) {
		String accessToken = Token.getAccessToken();
		String result = HttpUtils.sendForGET(String.format(URL_GET_USERINFO, accessToken, openid));
		log.info("微信用户详情"+result);
		JSONObject json = JSONObject.parseObject(result);
		if(json.containsKey("errcode")) {
			return null;
		} else {
			return JSONObject.toJavaObject(json, UserModel.class);
		}
	}
	/**
	 * 获取用户openid列表
	 * @param nextOpenid
	 * @return
	 */
	public static UserOpenIdList getOpenIds(String nextOpenid) {
		UserOpenIdList list = null;
		String accessToken = Token.getAccessToken();
		String url = "";
		if(StringUtils.isNotBlank(nextOpenid)) {
			url = String.format(URL_GETUSERLIST, accessToken, nextOpenid);
		} else {
			url = String.format(URL_GETUSERLIST, accessToken);
		}
		
		String result = HttpUtils.sendForGET(url);
		if(StringUtils.isNotBlank(result)) {
			JSONObject json = JSONObject.parseObject(result);
			if(!json.containsKey("errcode")) {
				list = JSONObject.toJavaObject(json, UserOpenIdList.class);
			}
		}
		return list;
	}
	
}
