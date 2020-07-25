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
 * ���ܣ������ö�����
 * @author Boy Baby
 *
 */
@WebServlet("/TopMessagesServlet")
public class TopMessagesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TopMessagesServlet() {
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
		String id = jsonobject.getString("id");//�û�id
		System.out.println("�ö�����id:"+ id);
		String type = jsonobject.getString("type");//�ö����Ա�ʶ
		
		if(type.equals("1")){
			String updatesql = "update messages set istop = 1 where p_id = "+id+"";
			
			int count = Tools.executeUpdate(updatesql);
			
			if(count > 0){
				JSONObject jsonObject1=new JSONObject();
				jsonObject1.put("result", "1");
				jsonObject1.put("msg", "�ö��ɹ�");
				String string = jsonObject1.toString();
				System.out.println(string);
				response.getWriter().write(string);
			}else{
				JSONObject jsonObject2=new JSONObject();
				jsonObject2.put("result", "0");
				jsonObject2.put("msg", "�ö�ʧ��");
				String string2=jsonObject2.toString();
				response.getWriter().write(string2);
			}
		}else{
			String updatesql = "update messages set istop = 0 where p_id = "+id+"";
			
			int count = Tools.executeUpdate(updatesql);
			
			if(count > 0){
				JSONObject jsonObject1=new JSONObject();
				jsonObject1.put("result", "1");
				jsonObject1.put("msg", "�ö��ɹ�");
				String string = jsonObject1.toString();
				System.out.println(string);
				response.getWriter().write(string);
			}else{
				JSONObject jsonObject2=new JSONObject();
				jsonObject2.put("result", "0");
				jsonObject2.put("msg", "�ö�ʧ��");
				String string2=jsonObject2.toString();
				response.getWriter().write(string2);
			}
		}	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
