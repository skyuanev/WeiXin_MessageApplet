package com.withSoul.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.withSoul.dao.Tools;

import net.sf.json.JSONObject;

/**
 * ����:�ϴ�������Ϣ
 * @author Boy Baby
 *
 */
@WebServlet("/MessageServlet")
public class ArticleMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ArticleMessageServlet() {
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

		String title = jsonobject.getString("title");//����
		String describ = jsonobject.getString("describe");//����
		String id = jsonobject.getString("id");//���ں�id
		String isheck = jsonobject.getString("isheck");
		System.out.println("���±�ʶΪ"+ isheck);
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dates = null;
		Date now = new Date();
		String nows = sdf3.format(now);
		try {
			dates=sdf3.parse(nows);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(isheck.equals("0")){
			//��������
			String sql="insert into article(title,describ,date,g_id) values(?,?,?,?)";
			
			int count = Tools.executeUpdate(sql,title,describ,dates,id);
			
			if(count>0){
				JSONObject jsonObject1=new JSONObject();
				jsonObject1.put("result", "1");
				jsonObject1.put("msg", "�ύ������Ϣ�ɹ�");
				String string = jsonObject1.toString();
				System.out.println(string);
				response.getWriter().write(string);
			}else{
				JSONObject jsonObject2=new JSONObject();
				jsonObject2.put("result", "0");
				jsonObject2.put("msg", "�ύ������Ϣʧ��");
				String string2=jsonObject2.toString();
				response.getWriter().write(string2);
			}
		}else{
			
			String selectSql= "select * from article where title = '"+title+"' and describ = '"+describ+"'and g_id='"+id+"'";
			List list = Tools.executeQuary(selectSql);
			
			if(list.size()>0){
				JSONObject jsonObject1=new JSONObject();
				jsonObject1.put("result", "1");
				jsonObject1.put("msg", "��ȡ������Ϣ�ɹ�");
				jsonObject1.put("content", list);
				String string = jsonObject1.toString();
				System.out.println(string);
				response.getWriter().write(string);
			}else{
				JSONObject jsonObject1=new JSONObject();
				jsonObject1.put("result", "1");
				jsonObject1.put("msg", "��ȡ������Ϣʧ��");
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
