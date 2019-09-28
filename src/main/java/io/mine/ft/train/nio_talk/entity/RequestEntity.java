package io.mine.ft.train.nio_talk.entity;

import java.io.Serializable;

/**
 * 2017/3/2 15:31.
 * <p>
 * Email: 978733153@qq.com
 * <p>
 * 包装请求实体
 */
public class RequestEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int isFirstRequest = 0;//是否是第一次请求 0不是  1是

    private String target;

    private String from;

    private String body;//请求消息

    public int getIsFirstRequest() {
        return isFirstRequest;
    }

    public void setIsFirstRequest(int isFirstRequest) {
        this.isFirstRequest = isFirstRequest;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
