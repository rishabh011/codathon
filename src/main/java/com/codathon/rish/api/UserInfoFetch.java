package com.codathon.rish.api;

import com.codathon.rish.proto.UserInfoPb;
import org.json.*;

/*public class TestClass {
	public static void main(String[] args) {
		
		UserInfoPb.Builder userBuilder = UserInfoPb.newBuilder();
	    userBuilder.clearId();
	    userBuilder.setId("25");
	    userBuilder.clearName();
	    userBuilder.setName("Rish");
	    userBuilder.clearGender();
	    userBuilder.setGender(1);
	    userBuilder.clearLocation();
	    userBuilder.getLocationBuilder().setLat("22.0");
	    userBuilder.getLocationBuilder().setLong("22.0");
	    System.out.println(userBuilder.build().toString());
	    
	}
}*/

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class UserInfoFetch extends HttpServlet{
 
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException{
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<h1>Hello Servlet Get</h1>");
		out.println("</body>");
		out.println("</html>");	
	}
}