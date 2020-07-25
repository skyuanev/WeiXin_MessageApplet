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
 * ���ܣ������û����Բ����뵽������еȴ��������
 * @author Boy Baby
 *
 */
@WebServlet("/SubmitMessage")
public class SubmitMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SubmitMessage() {
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

		String g_id = jsonobject.getString("g_id");//���ںű��
		String no = jsonobject.getString("no");//���±��
		String username = jsonobject.getString("username");//�û���
		String title = jsonobject.getString("title");//����
		String headimage = jsonobject.getString("avatarUrl");//�û�ͷ��
		String messages = jsonobject.getString("messages");//��������
		String openid = jsonobject.getString("openid");//����openid
		String token = jsonobject.getString("token");
		String ischeck = jsonobject.getString("ischeck");//�Ƿ񹫿�form_id
		String fromid = jsonobject.getString("form_id");//����form_id
		System.out.println(username);
		String insertsql = "insert into messages(no,title,username,headimage,userMesContent,isCheck,openid,g_id,fromid,token) values(?,?,?,?,?,?,?,?,?,?)";
		
		int count = Tools.executeUpdate(insertsql, no,title,username,headimage,messages,ischeck,openid,g_id,fromid,token);
		
		if(count>0){          
				JSONObject jsonObject1=new JSONObject();
				jsonObject1.put("result", "1");
				jsonObject1.put("message", messages);
				String string = jsonObject1.toString();
				System.out.println(string);
				response.getWriter().write(string);
		}else{
			JSONObject jsonObject2=new JSONObject();
			jsonObject2.put("result", "0");
			jsonObject2.put("msg", "�ύʧ��");
			String string2=jsonObject2.toString();
			response.getWriter().write(string2);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
