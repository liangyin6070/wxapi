package com.liudw.wxapi.jsapi;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Formatter;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liudw.wxapi.common.WechatConfig;
import com.liudw.wxapi.token.Token;
import com.liudw.wxapi.utils.HttpChartsetUtils;

/**
 * 
 * @class com.liudw.wxapi.jsapi.JsApiConfig
 * @date 2016-12-5 上午10:45:47
 * @author Administrator
 * @description 微信JSSDK参数配置类
 */
public class JsApiConfig {

	private static Logger log = LoggerFactory.getLogger(JsApiConfig.class);
	/**
	 * 是否开启debug，默认false
	 */
	private Boolean debug = false;
	/**
	 * 应用appid
	 */
	private String appid;
	/**
	 * 时间戳，单位秒
	 */
	private String timestamp;
	/**
	 * 随机字符串
	 */
	private String nonceStr;
	/**
	 * 签名
	 */
	private String signature;
	/**
	 * @param debug 是否调试, 默认否
	 * @param url 当前URL
	 */
	private JsApiConfig(Boolean debug, String url) {
		this.timestamp = Long.toString(Calendar.getInstance().getTimeInMillis() / 1000);
		this.nonceStr = createNonceStr();
		if(null != debug) {
			this.debug = debug;
		}
		this.appid = WechatConfig.getInstance().getAppID();
		this.signature = createSignature(this.nonceStr, Token.getJsapiTicket(), this.appid, url);
	}
	/**
	 * 实例化jsapi ticket对象
	 * @param debug
	 * @param url
	 * @return
	 */
	public JsApiConfig getInstance(Boolean debug, String url) {
		return new JsApiConfig(debug, url);
	}
	
	public Boolean getDebug() {
		return debug;
	}
	public void setDebug(Boolean debug) {
		this.debug = debug;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	/**
	 * 创建jsapi-ticket签名
	 * @param noncestr
	 * @param jsapi_ticket
	 * @param timestamp
	 * @param url
	 * @return
	 */
	private static String createSignature(String noncestr, String jsapi_ticket, String timestamp, String url) {
		StringBuilder jsSignature = new StringBuilder();
		jsSignature.append("jsapi_ticket=").append(jsapi_ticket)
					.append("&noncestr=").append(noncestr)
					.append("&timestamp=").append(timestamp)
					.append("&url=").append(url);
        // SHA1加密
        String digest = "";    
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(jsSignature.toString().getBytes(HttpChartsetUtils.CHARSET_UTF8));
            digest = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            log.error("", e);
        } catch (UnsupportedEncodingException e) {
        	log.error("字符集异常", e);
        }
        return digest;
	}
	/**
	 * 加密
	 * @param hash
	 * @return
	 */
	private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash){
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
	/**
	 * 创建随机字符串
	 * @return
	 */
	private static String createNonceStr() {
		return RandomStringUtils.randomAlphabetic(16);
	}
	
	public static void main(String[] args) {
		System.out.println(createNonceStr());
	}
}
