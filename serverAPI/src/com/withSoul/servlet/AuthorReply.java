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
 * ���ܣ����߻ظ�
 * @author Boy Baby
 */
@WebServlet("/AuthorReply")
public class AuthorReply extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AuthorReply() {
        super();
    }
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
				//�����������,�������ӵ��ַ�����  
				sbJson.append(line);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("client json data =" + sbJson);  
		
		 // ����:�Ѷ�ȡ�������ݷ�װ JSON
		System.out.println("JSON = "+ sbJson.toString());
		JSONObject jsonobject = JSONObject.fromObject(sbJson.toString());
		String id = jsonobject.getString("id");//�û�id
		String replyContent = jsonobject.getString("replyContent");//���߻ظ�����
		String isCheckChoess = jsonobject.getString("isCheckChoess");//�Ƿ�ɸѡ
		
		if(isCheckChoess.equals("0")){
			String insersql = "UPDATE messages SET authorMesContent='"+ replyContent +"' where p_id = "+id+"";
			int count = Tools.executeUpdate(insersql);
			if(count>0){
				JSONObject jsonObject1=new JSONObject();
				jsonObject1.put("result", "1");
				jsonObject1.put("msg", "�ظ��ɹ�");
				jsonObject1.put("replyContent", replyContent);
				String string = jsonObject1.toString();
				System.out.println(string);
				response.getWriter().write(string);
			}
			else{
				JSONObject jsonObject2=new JSONObject();
				jsonObject2.put("result", "0");
				jsonObject2.put("msg", "�ظ�ʧ��");
				jsonObject2.put("replyContent", replyContent);
				String string2=jsonObject2.toString();
				response.getWriter().write(string2);
			}
		}else{
			String updatesql = "UPDATE messages SET isCheck = 1 where p_id = "+id+"";
			int count = Tools.executeUpdate(updatesql);
			if(count>0){
				JSONObject jsonObject1=new JSONObject();
				jsonObject1.put("result", "1");
				jsonObject1.put("msg", "��ѡ�ɹ�");
				String string = jsonObject1.toString();
				System.out.println(string);
				response.getWriter().write(string);
			}
			else{
				JSONObject jsonObject2=new JSONObject();
				jsonObject2.put("result", "0");
				jsonObject2.put("msg", "��ѡʧ��");
				String string2=jsonObject2.toString();
				response.getWriter().write(string2);
			}
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}