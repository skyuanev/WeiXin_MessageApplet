package com.withSoul.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.withSoul.dao.Tools;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * ����:��̨�û���ȡ���ں�����
 * @author Boy Baby
 *
 */
@WebServlet("/IdentifyGetGidServlet")
public class IdentifyGetGidServlet extends HttpServlet {
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
		String password = jsonobject.getString("password");
		
		String querySql = "select * from gonginfo where password = '"+password+"'";
		
		List list = Tools.executeQuary(querySql);
		System.out.print("��ѯ�ɹ�"+list);
		if(list.size()>0){
			JSONObject jsonObject2 = new JSONObject();
			JSONArray ll=JSONArray.fromObject(list);
			jsonObject2.put("result", "1");
			jsonObject2.put("content", ll);
			String str = jsonObject2.toString();
			System.out.print("��ѯ�ɹ�"+str);
			response.getWriter().write(str);	
		}else{
			JSONObject jsonObject2 = new JSONObject();
			JSONArray ll=JSONArray.fromObject(list);
			jsonObject2.put("content", ll);
			jsonObject2.put("result", "0");
			String str = jsonObject2.toString();
			System.out.print("��ѯʧ��"+str);
			response.getWriter().write(str);	
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
