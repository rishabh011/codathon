package com.codathon.rish.trashtools;

import com.codathon.rish.proto.UserInfoPb;

public class ObjectConverterUpdater {
	public static UserInfoPb converter(CellMetaData from) {
		UserInfoPb.Builder to = UserInfoPb.newBuilder();
	    to.clearId();
	    to.setId(from.id);
	    to.clearName();
	    to.setName(from.name);
	    to.clearGender();
	    to.setGender(Integer.parseInt(from.gender));
	    to.clearLocation();
	    to.getLocationBuilder().setLat(from.latitude);
	    to.getLocationBuilder().setLong(from.longitude);
	    return to.build();
	}
	

	public static CellMetaData updater(UserInfoPb from) {
		CellMetaData to = new CellMetaData();
		to.id = from.getId();
		to.name = from.getName();
		to.gender = String.valueOf(from.getGender());
		to.latitude = from.getLocation().getLat();
		to.longitude = from.getLocation().getLong();
	    return to;
	}
}
