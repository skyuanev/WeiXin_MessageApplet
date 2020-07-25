package com.withSoul.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.withSoul.dao.Tools;

import net.sf.json.JSONObject;

/**
 * ����:��ȡ����Ա��openid
 * @author Boy Baby
 *
 */
@WebServlet("/GetOperatorOpenidServlet")
public class GetOperatorOpenidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//�������
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
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
		String token = jsonobject.getString("token");
		String id = jsonobject.getString("id");
		String formid = jsonobject.getString("formid");
		String openid = jsonobject.getString("openid");
		
		String updateOpenid = "Update gonginfo set fromid = '"+formid+"',token='"+token+"',openid='"+openid+"' where id = "+id+"";
		
		int count = Tools.executeUpdate(updateOpenid);
		
		if(count>0){
			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("result", "1");
			String str = jsonObject2.toString();
			System.out.print("���ĳɹ���");
			response.getWriter().write(str);	
		}else{
			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("result", "0");
			String str = jsonObject2.toString();
			System.out.print("����ʧ�ܣ�");
			response.getWriter().write(str);	
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
