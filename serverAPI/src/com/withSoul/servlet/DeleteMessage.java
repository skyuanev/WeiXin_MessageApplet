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
 * ���ܣ�ɾ������
 * @author Boy Baby
 *
 */
@WebServlet("/DeleteMessage")
public class DeleteMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public DeleteMessage() {
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

		String no = jsonobject.getString("no");//���±��
		String username = jsonobject.getString("username");//�û���
		String title = jsonobject.getString("title");//�û���
		String messages = jsonobject.getString("messages");//��������

				
		String deletesql = "delete from messages where no = "+ no +" and username ='"+ username 
				+"' and title ='"+ title +"' and userMesContent = '"+ messages +"'";
		System.out.println(deletesql);
		int count = Tools.executeUpdate(deletesql);
		
		if(count>0){
			JSONObject jsonObject1=new JSONObject();
			jsonObject1.put("result", "1");
			jsonObject1.put("msg", "ɾ���ɹ�");
			String string = jsonObject1.toString();
			System.out.println(string);
			response.getWriter().write(string);
		}
		else{
			JSONObject jsonObject2=new JSONObject();
			jsonObject2.put("result", "0");
			jsonObject2.put("msg", "ɾ��ʧ��");
			String string2=jsonObject2.toString();
			response.getWriter().write(string2);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
