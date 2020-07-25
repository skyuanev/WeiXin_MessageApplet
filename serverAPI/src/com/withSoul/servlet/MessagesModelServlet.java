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
 * ����:�������Է���֪ͨ
 * @author Boy Baby
 *
 */

@WebServlet("/MessagesModelServlet")
public class MessagesModelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MessagesModelServlet() {
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
		
		
//		String token = TokenThread.access_token.getAccess_token();
		String p_id = jsonobject.getString("p_id");
		String title = jsonobject.getString("title");//���±���
		String message = jsonobject.getString("message");//��������
		String replyMes = jsonobject.getString("replyMes");//���߻ظ�����
		String page = jsonobject.getString("page");//��תҳ��
		String upformid = jsonobject.getString("formid");//ģ��id
		String g_id = jsonobject.getString("g_id");//���ں�id
//		System.out.println("����formid�ɹ���");
		String selectOpenid = "select formid,openid,token from messages where p_id = "+p_id+"";
		List list = Tools.executeQuary(selectOpenid);
		String updateFormid = "update gonginfo set formid= '"+upformid+"' where id="+g_id+"";
		int count = Tools.executeUpdate(updateFormid);
		if(count>0){
			System.out.println("����formid�ɹ���");
		}else{
			System.out.println("����formidʧ�ܣ�");
		}
		
		if(list.size()>0){
			
			Map map = new HashMap();
			map = (Map) list.get(0);
			String formid = (String) map.get("fromid");
			String touser = (String) map.get("openid");
			String token = (String) map.get("token");
			
			JSONObject jsonModel = new JSONObject();
			JSONObject jsonMes = new JSONObject();
			JSONObject json1 = new JSONObject();
			JSONObject json2 = new JSONObject();
			JSONObject json3 = new JSONObject();
			JSONObject json4 = new JSONObject();
			json1.put("value",replyMes);//���߻ظ�����
			json2.put("value","����");//��������
			json3.put("value",message);//��������
			json4.put("value",title);//���±���
			jsonMes.put("keyword1",json1);
			jsonMes.put("keyword2",json2);
			jsonMes.put("keyword3",json3);
			jsonMes.put("keyword4",json4);
			jsonModel.put("touser", touser);
			jsonModel.put("template_id", "rVHcW7WzUPQU2wcvmQobfq8BWHbtM0_6fuuzbFdATp0");
			jsonModel.put("page", page);
			jsonModel.put("form_id", formid);//1540196916854
			jsonModel.put("data", jsonMes);
			
			System.out.println("ģ������Ϊ : "+ jsonModel.toString());
			
			String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + token;
			
			String data = HttpUtil.getJsonData(jsonModel,url);
			
			System.out.println("����֪ͨ��������Ϊ : "+ data);
		}else{
			
		}
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
