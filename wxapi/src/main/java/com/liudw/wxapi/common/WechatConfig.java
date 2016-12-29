package com.liudw.wxapi.common;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @class com.liudw.wxapi.common.WechatConfig
 * @date 2016-12-5 上午10:25:56
 * @author Administrator
 * @description 微信配置类
 */
public class WechatConfig {
	
	private static Logger log = LoggerFactory.getLogger(WechatConfig.class);
	private static WechatConfig config = new WechatConfig();
	
	/**
	 * 应用ID
	 */
	private String appID;
	/**
	 * 应用密钥
	 */
	private String appSecret;
	/**
	 * 服务器地址
	 */
	private String url;
	/**
	 * 令牌
	 */
	private String token;
	/**
	 * 消息加解密密钥
	 */
	private String encodingAESKey;
	/**
	 * 统一微信重定向URI
	 */
	private String commonRedirectURI;
	
	/**
	 * 初始化配置文件，赋值
	 */
	private WechatConfig() {
		Properties prop = new Properties();
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("init.properties"));
			appID = prop.getProperty("wechat_appid", "wx8644c7a72a66564d").trim();
			appSecret = prop.getProperty("wechat_appSecret", "c3754524efe1d660bc435f3467e23b2e").trim();
			url = prop.getProperty("wechat_url", "http://23af5327.ngrok.natapp.cn/wechats/wx/signature.htm").trim();
			token = prop.getProperty("wechat_token", "seek").trim();
			encodingAESKey = prop.getProperty("wechat_encodingAESKey", "Y0MklUeYb8NLzy3CmR2ev7VzxCQrAYI1m58ThUfW888").trim();
			commonRedirectURI = prop.getProperty("commonRedirectURI", "").trim();
		} catch (IOException e) {
			log.error("初始化微信参数失败", e);
		}
	}
	
	public static WechatConfig getInstance() {
		return config;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEncodingAESKey() {
		return encodingAESKey;
	}

	public void setEncodingAESKey(String encodingAESKey) {
		this.encodingAESKey = encodingAESKey;
	}

	public String getCommonRedirectURI() {
		return commonRedirectURI;
	}

	public void setCommonRedirectURI(String commonRedirectURI) {
		this.commonRedirectURI = commonRedirectURI;
	}
	
}
