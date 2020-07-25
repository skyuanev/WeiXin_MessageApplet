package com.withSoul.servlet;

import java.io.File;
import java.io.IOException;
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
 * Servlet implementation class PhotoServlet
 */

@WebServlet("/PhotoServlet")
public class PhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//�������
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		//����·��
		String savePath = request.getSession().getServletContext().getRealPath("/")+"photo/";
		System.out.println("·��:"+savePath);
		//ͨ��������·�����ַ���ת��Ϊ����·����������һ���� File ʵ����
		File saveDir=new File(savePath);
		//���Ŀ¼�����ھʹ���Ŀ¼
		if(!saveDir.exists()){
			saveDir.mkdir();
		}
		//�����ļ��ϴ�������,���������ļ�����
		DiskFileItemFactory factory=new DiskFileItemFactory();
		//�����������ļ��ϴ�������
		ServletFileUpload sfu=new ServletFileUpload(factory);
		
		//���ý��������ļ��������ʽ 
		sfu.setHeaderEncoding("utf-8");
		
		try {
			//���������,����requestΪһ�����ϣ�Ԫ��ΪFileItem
			List<FileItem>itemList=sfu.parseRequest(request);
			for(FileItem fileItem:itemList){
				//��Ӧ���пؼ���name
				String filedName=fileItem.getFieldName();
				System.out.println("�ؼ�����"+filedName);
				//�ж��Ƿ��ļ������Ϊ��true��������ͨ�������Ϊ��false�������ļ�
				if(fileItem.isFormField()){
					//�ؼ�ֵ
					String value=fileItem.getString();
					//���±���,���fileupload����
					value=new String(value.getBytes("iso-8859-1"),"utf-8");
					System.out.println("��ͨ"+filedName+"="+value);
					//�ϴ��ļ�
				}
				else{
					//����ļ���С
					Long size=fileItem.getSize();
					//����ʱ�����֤�ϴ��ļ���Ψһ��
					String filetype=".jpg";
					Date date=new Date(System.currentTimeMillis());
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					
					//����ϴ��ļ����ļ���
					String fileName=fileItem.getName();
					System.out.println("�ļ���:"+fileName+"\t��С"+size+"byte");
					//���ò������ϴ����ļ���ʽ
					if(fileName.endsWith(".exe")){
						request.setAttribute("msg", "�������ϴ�������");
						JSONObject jsonObject1=new JSONObject();
						jsonObject1.put("result", "0");
						jsonObject1.put("msg", "�ϴ�ʧ��");
						String string1=jsonObject1.toString();
						response.getWriter().write(string1);
					}
					else{
						//���ļ����浽ָ��·��
						File file=new File(savePath,fileName);
						try {
							fileItem.write(file);
							//����·��  �����磺https://�������/Message_board/photo/��
							String basepath="��ķ������Ĵ洢��Ƭ��·��"+fileName;
							String paths = basepath.replace("\\", "\\\\");
							System.out.println("����·��"+paths);
							String updatesql="UPDATE article set imageTitle='"+paths+"' order by `no` desc limit 1";
							int executeUpdate = Tools.executeUpdate(updatesql);
							if(executeUpdate>0)
							{
								JSONObject jsonObject1=new JSONObject();
								jsonObject1.put("result", "1");
								jsonObject1.put("msg", "����ɹ�");
								String string=jsonObject1.toString();
								System.out.println(string);
								response.getWriter().write(string);
							}
							else
							{
								JSONObject jsonObject2=new JSONObject();
								jsonObject2.put("result", "0");
								jsonObject2.put("msg", "����ʧ��");
								String string2=jsonObject2.toString();
								response.getWriter().write(string2);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}catch(FileSizeLimitExceededException e){
			request.setAttribute("msg", "�ļ�̫��");
		}
		catch (FileUploadException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
