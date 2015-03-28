package com.codathon.rish.api;

import com.codathon.rish.proto.LocationPb;
import com.codathon.rish.proto.UserInfoPb;
import com.codathon.rish.trashtools.DatabaseReaderWriter;
import org.json.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class UserInfoFetch extends HttpServlet{
 
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException{
		PrintWriter out = response.getWriter();
		List<UserInfoPb> userInfoList = DatabaseReaderWriter.reader();
		for(UserInfoPb user : userInfoList) {
			if(!isWithinRange(user.getLocation(), request.getParameter("distance")))
				userInfoList.remove(user);
		}
		out.write(userInfoList.toString());
	}

	private boolean isWithinRange(LocationPb location, String distance) {
		Location center;
		float distanceInMeters = center.distanceTo(location);
		boolean isWithin = distanceInMeters < Integer.parseInt(distance);
		return isWithin;
	}
}