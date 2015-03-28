package com.codathon.rish.trashtools;

import com.codathon.rish.proto.UserInfoPb;
import com.google.protobuf.Message;
import com.googlecode.protobuf.format.JsonFormat;
import com.googlecode.protobuf.format.JsonFormat.ParseException;

public class JsonToUserInfo
{
	public static UserInfoPb deepConverter(String json)
	{
		
		Message.Builder builder = UserInfoPb.newBuilder();
		String jsonFormat = json;
		try {
			JsonFormat.merge(jsonFormat, builder);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		return (UserInfoPb) builder.build();
	}
}