package com.codathon.rish.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.codathon.rish.proto.UserInfoPb;
import com.codathon.rish.trashtools.DatabaseReaderWriter;
import com.codathon.rish.trashtools.JsonToUserInfo;
 
public class UserInfoStore extends HttpServlet{
 
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException{
		String userInfo = request.getParameter("info");
		UserInfoPb user = JsonToUserInfo.deepConverter(userInfo);
		DatabaseReaderWriter.writer(user);
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("wrote to db : "+user.toString());
		out.println("</body>");
		out.println("</html>");	
	}
}