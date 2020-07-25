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
 * ����:��ȡ���ں���Ϣ
 * @author Boy Baby
 *
 */
@WebServlet("/GongMessageServlet")
public class GongMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GongMessageServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//�������
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String selectGong = "select * from gonginfo";
		
		List list = Tools.executeQuary(selectGong);
		
		if(list.size()>0){
			JSONArray jsonArray = JSONArray.fromObject(list);
			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("result", "1");
			jsonObject2.put("content", jsonArray);
			String str = jsonObject2.toString();
			System.out.print("�ѻ�ȡ���ں���Ϣ"+str);
			response.getWriter().write(str);	
		}else{
			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("result", "0");
			String str = jsonObject2.toString();
			System.out.print("��ȡ���ں���Ϣʧ��"+str);
			response.getWriter().write(str);	
		}	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
