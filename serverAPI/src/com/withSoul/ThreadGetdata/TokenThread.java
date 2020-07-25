package com.withSoul.ThreadGetdata;

import com.withSoul.bean.Access_Token;
import com.withSoul.util.AccessTokenUtil;

public class TokenThread implements Runnable{
	//΢�Ź��ںŵ�ƾ֤����Կ
	public static final String appID = "";// ���΢��С���� AppId
	public static final String appScret = ""; // ��С����ĵ� appScret�������鿴��
	public static Access_Token access_token=null; 

	@Override
	public void run() {
		while(true){
			try {
				//���ù������ȡaccess_token(ÿ������ȡ100000�Σ�ÿ�λ�ȡ����Ч��Ϊ7200��)
				access_token = AccessTokenUtil.getAccessToken(appID, appScret); 
				if(null!=access_token){
				System.out.println("accessToken��ȡ�ɹ���"+access_token.getExpires_in());
				//7000��֮�����½��л�ȡ
				Thread.sleep((access_token.getExpires_in()-1000)*1000);
				}else{
					//��ȡʧ��ʱ��60��֮�������»�ȡ
					Thread.sleep(30*1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

