package com.withSoul.bean;

/**
 * ���ܣ�access_token ʵ����
 * @author Boy Baby
 *
 */
public class Access_Token {
	
	//��ȡ����access_token
	private String access_token; 
	
	//��Чʱ�䣨����Сʱ��7200s��
	private int expires_in; 
	
	public String getAccess_token() {
		return access_token;
	}
	
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	
	public int getExpires_in() {
		return expires_in;
	}
	
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
}
