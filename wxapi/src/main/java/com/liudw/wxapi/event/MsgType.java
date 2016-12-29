package com.liudw.wxapi.event;
/**
 * 微信消息推送类型
 * @author site
 *
 */
public enum MsgType {
	
	Text("text"),  //文本消息
    Image("image"),  //图片消息
    Music("music"),  //媒体消息
    Video("video"),  //
    Voice("voice"),  
    Location("location"),  //定位消息
    Link("link"),  
	Event("event"); //事件消息
    private String msgType = "";  
  
    MsgType(String msgType) {  
        this.msgType = msgType;  
    }  
  
    /** 
     * @return the msgType 
     */  
    @Override  
    public String toString() {  
        return msgType;  
    }  
}
