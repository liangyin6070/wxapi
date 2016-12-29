package com.liudw.wxapi.user;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
/**
 * 微信拉取用户openid数据模型
 * @author site
 *
 */
@SuppressWarnings("serial")
public class UserOpenIdList implements Serializable {

	private Integer total;
	private Integer count;
	private String next_openid;
	private Map<String, List<String>> data;
	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getNext_openid() {
		return next_openid;
	}
	public void setNext_openid(String next_openid) {
		this.next_openid = next_openid;
	}
	public Map<String, List<String>> getData() {
		return data;
	}
	public void setData(Map<String, List<String>> data) {
		this.data = data;
	}
	
	
}
