package com.codathon.rish.trashtools;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.codathon.rish.proto.UserInfoPb;

public class DatabaseReaderWriter {

	public static String outFileName = "/home/ilodge/Desktop/database.xlsx";

	public static void writer(UserInfoPb userInfo) {
		List<CellMetaData> listCmd = new ArrayList<CellMetaData>();
	    listCmd.add(ObjectConverterUpdater.updater(userInfo));
	    ExcelHelper.writeListDataToFile(listCmd, outFileName, true);
	}
	
	public static List<UserInfoPb> reader() {
	    Set<CellMetaData> cmdList = ExcelHelper.readExcelDataInSet(outFileName);
	    List<UserInfoPb> userList = new ArrayList<UserInfoPb>();
	    for(CellMetaData cmd : cmdList) {
	    	userList.add(ObjectConverterUpdater.converter(cmd));
	    }
	    return userList;
	}
}
