package com.withSoul.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.withSoul.dao.Tools;

import net.sf.json.JSONObject;

/**
 * ���ܣ��������Ե�����
 * @author Boy Baby
 *
 */
@WebServlet("/GoodCountServlet")
public class GoodCountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public GoodCountServlet() {
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
		String status = jsonobject.getString("status");//ȡ�����ǵ���
		String p_id = jsonobject.getString("p_id");//����id
		String g_id = jsonobject.getString("g_id");//���ں�d
		String no = jsonobject.getString("no");//����id
		String openid = jsonobject.getString("openid");//�����û�openid
		
		if(status.equals("1")){
			//���һ����
	
			String insertzan = "insert into zan(g_id,no,p_id,openid,status) values(?,?,?,?,?)";
			int count = Tools.executeUpdate(insertzan,g_id,no,p_id,openid,1);
			
			if(count>0){
				String updateZanCounts = "update messages set zanCounts=zanCounts + 1 where g_id="+g_id+" and no="+no+" and p_id='"+p_id+"'";
				int count1 = Tools.executeUpdate(updateZanCounts);
				if(count1>0){
					JSONObject jsonObject1=new JSONObject();
					jsonObject1.put("result", "1");
					jsonObject1.put("msg", "���޳ɹ�");
					String string = jsonObject1.toString();
					System.out.println(string);
					response.getWriter().write(string);
				}else{
					JSONObject jsonObject1=new JSONObject();
					jsonObject1.put("result", "0");
					jsonObject1.put("msg", "����ʧ��");
					String string = jsonObject1.toString();
					System.out.println(string);
					response.getWriter().write(string);
				}
			}else{
				JSONObject jsonObject1=new JSONObject();
				jsonObject1.put("result", "0");
				jsonObject1.put("msg", "����ʧ��");
				String string = jsonObject1.toString();
				System.out.println(string);
				response.getWriter().write(string);
			}
					
		}else{
			//ȡ��һ����
			String updatesql = "delete from zan where g_id="+g_id+" and no="+no+" and p_id="+p_id+" and openid='"+openid+"'";

			int count = Tools.executeUpdate(updatesql);
			
			if(count>0){
				JSONObject jsonObject1=new JSONObject();
				jsonObject1.put("result", "1");
				jsonObject1.put("msg", "ȡ��״̬�ɹ�");
				String string = jsonObject1.toString();
				System.out.println(string);
				response.getWriter().write(string);
			}
			else{
				JSONObject jsonObject2=new JSONObject();
				jsonObject2.put("result", "0");
				jsonObject2.put("msg", "ȡ��ʧ��");
				String string2=jsonObject2.toString();
				response.getWriter().write(string2);
			}
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
