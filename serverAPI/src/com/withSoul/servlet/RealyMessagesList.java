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

import com.withSoul.bean.MessagesBean;
import com.withSoul.dao.Tools;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * ����:���������������Ϣ��
 * @author Boy Baby
 *
 */

@WebServlet("/RealyMessagesList")
public class RealyMessagesList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RealyMessagesList() {
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
		String ischeckmessage = jsonobject.getString("ischeckmessage");
		
		
		//�ѹ�������
		if(ischeckmessage.equals("1")){
			String g_id = jsonobject.getString("g_id");//���ںű��
			String no = jsonobject.getString("no");//���±��
			String openid = jsonobject.getString("openid"); //�û�openid
			System.out.println("���±��Ϊ:  "+ no);
			String queueSql = "select * from messages where no = "+no+" and isCheck = 1 and g_id = "+g_id+" ORDER BY messages.istop DESC ";
			List list = Tools.executeQuary(queueSql);

			if(list!=null){
				//�洢����С����������ϢList
				List<MessagesBean> messlist = new ArrayList();
				Map map = new HashMap();
				for(int i = 0;i<list.size();i++){
					map = (Map) list.get(i);
					MessagesBean messagebean = new MessagesBean();
					messagebean.setP_id((int) map.get("p_id"));
					messagebean.setG_id((int) map.get("g_id"));
					messagebean.setNo((int) map.get("no"));
					messagebean.setTitle((String) map.get("title"));
					messagebean.setUsername((String) map.get("username"));
					messagebean.setOpenid((String) map.get("openid"));
					messagebean.setHeadimage((String) map.get("headimage"));
					messagebean.setUserMesContent((String) map.get("userMesContent"));
					messagebean.setAuthorMesCount((String) map.get("authorMesContent"));
					messagebean.setIsCheck((int) map.get("isCheck"));
					messagebean.setIstop((int) map.get("istop"));
					messagebean.setZanCounts((int) map.get("zanCounts"));
					messagebean.setIszan(0);
					messlist.add(messagebean);
				}
				
				String querezan = "select p_id from zan where no = "+no+" and g_id = "+g_id+" and openid='"+openid+"'";
				List zanlist = Tools.executeQuary(querezan);
				System.out.print("zan��"+zanlist);
				if(zanlist.size()>0){
					Map zanmap = new HashMap();
					for(int j = 0;j<zanlist.size();j++){
						zanmap = (Map) zanlist.get(j);
						for(int n = 0;n<messlist.size();n++){
							if(((int)zanmap.get("p_id"))==((int)messlist.get(n).getP_id())){
								messlist.get(n).setIszan(1);
							}
						}
					}
					JSONArray jsonArray = JSONArray.fromObject(messlist);
					JSONObject jsonObject2 = new JSONObject();
					jsonObject2.put("result", "1");
					jsonObject2.put("content", jsonArray);
					String str = jsonObject2.toString();
					System.out.print("��ɸѡ����"+str);
					response.getWriter().write(str);
					
				}else{
					
					JSONArray jsonArray = JSONArray.fromObject(messlist);
					JSONObject jsonObject2 = new JSONObject();
					jsonObject2.put("result", "1");
					jsonObject2.put("content", jsonArray);
					String str = jsonObject2.toString();
					System.out.print("��ɸѡ����"+str);
					response.getWriter().write(str);
				}
			}else{
				JSONObject jsonObject2 = new JSONObject();
				jsonObject2.put("result", "0");
				String str = jsonObject2.toString();
				System.out.print("��ɸѡ����"+str);
				response.getWriter().write(str);	
			}
		}else{
			//δ��������
			String g_id = jsonobject.getString("id");
			String no = jsonobject.getString("no");//���±��
			String queueSql = "select * from messages where no = '"+no+"' and g_id ='"+g_id+"'";
			String countsql = "select COUNT(*) from messages where no = '"+no+"'";
			List list = Tools.executeQuary(queueSql);
			List countlist = Tools.executeQuary(countsql);
			System.out.println("countlist"+list);
			if(list.size()>0){
				Map m = (Map) countlist.get(0);
				String count = m.toString().substring(m.toString().indexOf("=")+1,m.toString().lastIndexOf("}"));
				System.out.println("countlist"+m.toString().substring(m.toString().indexOf("=")+1,m.toString().lastIndexOf("}")));
				JSONArray jsonArray = JSONArray.fromObject(list);
				JSONObject jsonObject2 = new JSONObject();
				jsonObject2.put("result", "1");	//�ɹ�
				jsonObject2.put("count", Integer.parseInt(count));//δ�������
				jsonObject2.put("content", jsonArray);//δ��������
				String str = jsonObject2.toString();
				System.out.print("δ��������"+str);
				response.getWriter().write(str);	
			}else{
				JSONObject jsonObject2 = new JSONObject();
				jsonObject2.put("result", "0");
				String str = jsonObject2.toString();
				System.out.print("δ��������"+str);
				response.getWriter().write(str);	
			}
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
