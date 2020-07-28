package com.withSoul.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.withSoul.ThreadGetdata.TokenThread;
import com.withSoul.dao.Tools;
import com.withSoul.util.HttpUtil;

import net.sf.json.JSONObject;

/**
 * ����:�û�����ģ������
 * @author Boy Baby
 *
 */
@WebServlet("/UserModelServlet")
public class UserModelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UserModelServlet() {
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
		
		String title = jsonobject.getString("title");//���±���
		String message = jsonobject.getString("messages");//��������
		String page = jsonobject.getString("page");//��תҳ��
		String g_id = jsonobject.getString("g_id");//���ں�ID
		String username = jsonobject.getString("username");//������


		String selectOpenid = "select formid,token,openid from gonginfo where id = "+g_id+"";
		List list = Tools.executeQuery(selectOpenid);
		
		if(list.size()>0){
			Map map = new HashMap();
			map = (Map) list.get(0);
			String formid = (String) map.get("formid");
			String token = (String) map.get("token");
			String openid = (String) map.get("openid");
			JSONObject jsonModel = new JSONObject();
			JSONObject jsonMes = new JSONObject();
			
			JSONObject json1 = new JSONObject();
			JSONObject json2 = new JSONObject();
			JSONObject json3 = new JSONObject();
			
			json1.put("value",message);//��������
			json2.put("value",username);//������
			json3.put("value",title);//���±���
			jsonMes.put("keyword1",json1);
			jsonMes.put("keyword2",json2);
			jsonMes.put("keyword3",json3);

			jsonModel.put("touser", openid);
			jsonModel.put("template_id", "XGyGSqLGyQ31OJWuE-zgzMYxB0Dqvt3HwQqgm6R02rc");
			jsonModel.put("page", page);
			jsonModel.put("form_id", formid);//1540196916854
			jsonModel.put("data", jsonMes);
			
			System.out.println("ģ������Ϊ : "+ jsonModel.toString());
			
			String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + token;
			
			String data = HttpUtil.getJsonData(jsonModel,url);
			
			System.out.println("����֪ͨ��������Ϊ : "+ data);	
		}else{
			System.out.println("��ѯʧ��");
		}	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
