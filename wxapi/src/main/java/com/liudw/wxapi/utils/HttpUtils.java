package com.liudw.wxapi.utils;

import java.io.IOException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
/**
 * 
 * @class com.liudw.wxapi.utils.HttpUtils
 * @date 2016-12-5 上午11:13:06
 * @author Administrator
 * @description
 */
public class HttpUtils {
	private static Logger log = LoggerFactory.getLogger(HttpUtils.class);
	/**
	 * get提交
	 * 
	 * @param url
	 * @return
	 */
	public static String sendForGET(String url) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		String result = null;
		HttpEntity entity = null;
		try {
			CloseableHttpResponse resp = client.execute(get);
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				entity = resp.getEntity();
				result = EntityUtils.toString(entity, HttpChartsetUtils.CHARSET_UTF8);
			}
		} catch (ClientProtocolException e) {
			log.error("", e);
		} catch (IOException e) {
			log.error("", e);
		} finally {
			get.abort();
			if (entity != null) {
				EntityUtils.consumeQuietly(entity);
			}
		}
		return result;
	}
	/**
	 * post提交string字符串
	 * @param url
	 * @param data
	 * @return
	 */
	public static String postString(String url, String data) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		String result = null;
		HttpEntity entity = null;
		try {
			if (data != null) {
				StringEntity se = new StringEntity(data, HttpChartsetUtils.CHARSET_UTF8);
				se.setContentEncoding(HttpChartsetUtils.CHARSET_UTF8);
				//se.setContentType(HttpServletUtils.TYPE_JSON);
				post.setEntity(se);
			}
			CloseableHttpResponse resp = client.execute(post);
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				entity = resp.getEntity();
				result = EntityUtils.toString(entity, HttpChartsetUtils.CHARSET_UTF8);
			}
		} catch (ClientProtocolException e) {
			log.error("", e);
		} catch (IOException e) {
			log.error("", e);
		} finally {
			post.abort();
			if (entity != null) {
				EntityUtils.consumeQuietly(entity);
			}
		}
		return result;
	}
	
	/**
	 * post提交
	 * 
	 * @param url
	 * @param data
	 * @return
	 */
	public static String sendForPOST(String url, JSONObject data) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		String result = null;
		HttpEntity entity = null;
		try {
			if (data != null) {
				StringEntity se = new StringEntity(data.toString(), HttpChartsetUtils.CHARSET_UTF8);
				se.setContentEncoding(HttpChartsetUtils.CHARSET_UTF8);
				se.setContentType("application/json");
				post.setEntity(se);
			}
			CloseableHttpResponse resp = client.execute(post);
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				entity = resp.getEntity();
				result = EntityUtils.toString(entity, HttpChartsetUtils.CHARSET_UTF8);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.abort();
			if (entity != null) {
				EntityUtils.consumeQuietly(entity);
			}
		}
		return result;
	}
}
