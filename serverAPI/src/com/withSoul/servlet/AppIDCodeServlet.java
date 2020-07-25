package com.withSoul.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.withSoul.ThreadGetdata.TokenThread;
import com.withSoul.dao.Tools;
import com.withSoul.util.AccessTokenUtil;

import net.sf.json.JSONObject;


/**
 * ����:��ȡС�����AppID��Code
 * @author Boy Baby
 *
 */
@WebServlet("/AppIDCodeServlet")
public class AppIDCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String appID = ""; // ��д��С����� APPid
	public static final String appScret = "";// ��С����� AppScret��΢��С���������ȡ��
	public static String  openid = ""; 
	public static String  token = ""; 
       
    public AppIDCodeServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//�������
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		//������ʱ��ȡaccess_token���߳�
//		new Thread(new TokenThread()).start(); 
		
		//��������
		StringBuffer sbJson = new StringBuffer();
		String line = "";
		try{
			//��ȡ���紫�����������
			BufferedReader br = request.getReader();
			//�������ݲ�ƴ��
			while((line = br.readLine())!= null){
				//�����������,������ӵ��ַ�����  
				sbJson.append(line);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("client json data =" + sbJson);  
		
		 // ����:�Ѷ�ȡ�������ݷ�װ JSON
		System.out.println("JSON = "+ sbJson.toString());
		JSONObject jsonobject = JSONObject.fromObject(sbJson.toString());
		String code = jsonobject.getString("code");//js_code
		
		openid = AccessTokenUtil.getOpenid(appID, appScret,code); 
		
		token = TokenThread.access_token.getAccess_token();
		
		System.out.println("΢��API-openid:"+openid+ "token:" + token);
		
		if(!token.equals("")&&!openid.equals("")){
			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("result", "1");
			jsonObject2.put("openid", openid);
			jsonObject2.put("token", token);
			String str = jsonObject2.toString();
			System.out.print("openid"+openid);
			response.getWriter().write(str);	
		}else{
			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("result", "0");
			String str = jsonObject2.toString();
			response.getWriter().write(str);	
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
  
}
