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
 * ����:��ѡ���Ժ�ɾ������
 * @author Boy Baby
 *
 */
@WebServlet("/ChooseDeleteMessage")
public class ChooseDeleteMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public ChooseDeleteMessage() {
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
		String type = jsonobject.getString("type");// ��ѡ/ɾ�����Ա�ʶ
		
		if(type.equals("1")){
			//����Ϊ��ѡ����
			String updatesql = "update messages set isCheck = 1 where p_id = "+id+"";
			
			int count = Tools.executeUpdate(updatesql);
			if(count>0){
				JSONObject jsonObject1=new JSONObject();
				jsonObject1.put("result", "1");
				jsonObject1.put("msg", "������Ϊ��ѡ����");
				String string = jsonObject1.toString();
				System.out.println(string);
				response.getWriter().write(string);
			}else{
				JSONObject jsonObject1=new JSONObject();
				jsonObject1.put("result", "0");
				jsonObject1.put("msg", "����Ϊ��ѡ����ʧ��");
				String string = jsonObject1.toString();
				System.out.println(string);
				response.getWriter().write(string);
			}
		}else if(type.equals("0")){
			//ɾ������
			String deletesql = "delete from messages where p_id = "+id+"";
			
			int count = Tools.executeUpdate(deletesql);
			if(count>0){
				String deletesql1 = "delete from zan where p_id = "+id+"";
				int count1 = Tools.executeUpdate(deletesql1);
				if(count1>=0){
					JSONObject jsonObject1=new JSONObject();
					jsonObject1.put("result", "1");
					jsonObject1.put("msg", "��ɾ��������");
					String string = jsonObject1.toString();
					System.out.println(string);
					response.getWriter().write(string);
				}else{
					JSONObject jsonObject1=new JSONObject();
					jsonObject1.put("result", "0");
					jsonObject1.put("msg", "ɾ��������ʧ��");
					String string = jsonObject1.toString();
					System.out.println(string);
					response.getWriter().write(string);
				}
			}else{
				JSONObject jsonObject1=new JSONObject();
				jsonObject1.put("result", "0");
				jsonObject1.put("msg", "ɾ��������ʧ��");
				String string = jsonObject1.toString();
				System.out.println(string);
				response.getWriter().write(string);
			}
		}else{
			//����Ϊ��ѡ����
			String updatesql = "update messages set isCheck = 0 where p_id = "+id+"";
			
			int count = Tools.executeUpdate(updatesql);
			if(count>0){
				JSONObject jsonObject1=new JSONObject();
				jsonObject1.put("result", "1");
				jsonObject1.put("msg", "��ȡ��Ϊ��ѡ����");
				String string = jsonObject1.toString();
				System.out.println(string);
				response.getWriter().write(string);
			}else{
				JSONObject jsonObject1=new JSONObject();
				jsonObject1.put("result", "0");
				jsonObject1.put("msg", "ȡ����ѡ����ʧ��");
				String string = jsonObject1.toString();
				System.out.println(string);
				response.getWriter().write(string);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
