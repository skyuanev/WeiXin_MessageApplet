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
 * ���ղ�ͬ���ں� id ��ȡ�����б�
 * @author Boy Baby
 *
 */
@WebServlet("/ArticleListServlet")
public class ArticleListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ArticleListServlet() {
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
		String type = jsonobject.getString("type");//��������
		String id = jsonobject.getString("id");//���ں�id
		
		//����ɾ��
		if(type.equals("2")){
			String no = jsonobject.getString("no");//��������
			String deleteSql = "delete from articel where no = "+no+" and g_id = "+id+"";
			int count = Tools.executeUpdate(deleteSql);
			if(count>0){
				JSONObject jsonObject2 = new JSONObject();
				jsonObject2.put("result", "1");
				String str = jsonObject2.toString();
				System.out.print("ɾ���ɹ���");
				response.getWriter().write(str);	
			}else{
				JSONObject jsonObject2 = new JSONObject();
				jsonObject2.put("result", "2");
				String str = jsonObject2.toString();
				System.out.print("ɾ��ʧ�ܣ�");
				response.getWriter().write(str);	
			}
		}else{
			//���ݹ��ں� id ��ѯ�����б�
			String selectsql = "Select * from articel where g_id = "+ id +" order by no desc";
			List list = Tools.executeQuery(selectsql);
			
			if(list.size()>0){
				JSONObject jsonObject2 = new JSONObject();
				JSONArray ll=JSONArray.fromObject(list);
				jsonObject2.put("result", "1");
				jsonObject2.put("content", ll);
				String str = jsonObject2.toString();
				System.out.print("��ѯ�ɹ�"+str);
				response.getWriter().write(str);	
			}else if(list.size()== 0){
				JSONObject jsonObject2 = new JSONObject();
				JSONArray ll=JSONArray.fromObject(list);
				jsonObject2.put("content", ll);
				jsonObject2.put("result", "0");
				String str = jsonObject2.toString();
				System.out.print("�����б�Ϊ��"+str);
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
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
