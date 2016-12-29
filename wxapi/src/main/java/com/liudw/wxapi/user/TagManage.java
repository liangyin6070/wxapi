package com.liudw.wxapi.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.liudw.wxapi.token.Token;
import com.liudw.wxapi.utils.HttpUtils;

/**
 * 
 * @class com.liudw.wxapi.user.TagManage
 * @date 2016-12-5 下午1:54:47
 * @author Administrator
 * @description 标签管理
 */
public class TagManage {
	private static Logger log = LoggerFactory.getLogger(TagManage.class);
	/**
	 *  创建标签
	 */
	private static String URL_POST_TAG = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token=%s";
	
	public static String createTag(JSONObject data) {
		String accessToken = Token.getAccessToken();
		return HttpUtils.sendForPOST(String.format(URL_POST_TAG, accessToken), data);
	}
}
