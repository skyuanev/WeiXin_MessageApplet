package com.withSoul.util;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpEntity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Http ���󹤾���
 * @author Boy Baby
 *
 */
public class HttpUtil {

	public static String getJsonData(JSONObject jsonParam,String urls) {
		StringBuffer sb=new StringBuffer();
		try {
			// ����url��Դ
			URL url = new URL(urls);
			// ����http����
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// �����������
			conn.setDoOutput(true);
            // ������������
            conn.setDoInput(true);
            // ���ò��û���
            conn.setUseCaches(false);
            // ���ô��ݷ�ʽ
            conn.setRequestMethod("POST");
            // ����ά�ֳ�����
            conn.setRequestProperty("Connection", "Keep-Alive");
            // �����ļ��ַ���:
            conn.setRequestProperty("Charset", "UTF-8");
            // ת��Ϊ�ֽ�����
            byte[] data = (jsonParam.toString()).getBytes();
            // �����ļ�����
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));
            // �����ļ�����:
            conn.setRequestProperty("contentType", "application/json");
            // ��ʼ��������
            conn.connect();		
            OutputStream out = new DataOutputStream(conn.getOutputStream()) ;
			// д��������ַ���
			out.write((jsonParam.toString()).getBytes("UTF-8"));
			out.flush();
			out.close();
 
			System.out.println(conn.getResponseCode());
			// ���󷵻ص�״̬
			if (HttpURLConnection.HTTP_OK == conn.getResponseCode()){
				System.out.println("���ӳɹ�");
				// ���󷵻ص�����
				InputStream in1 = conn.getInputStream();
				try {
				      String readLine=new String();
				      BufferedReader responseReader=new BufferedReader(new InputStreamReader(in1,"UTF-8"));
				      while((readLine=responseReader.readLine())!=null){
				        sb.append(readLine).append("\n");
				      }
				      responseReader.close();
				      System.out.println(sb.toString());
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				System.out.println("error++");
			}
 
		} catch (Exception e) {
 
		}
		return sb.toString();
	}
}
