package com.codathon.rist.trashtools;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CellMetaData {

	public enum FieldType {
		BOOLEAN, TEXT, FORMULA, NUMBER, DATE, QTCSPARE;
	}
	
	/**
	 * update when adding or removing columns
	 */
	static final int totalColumnCount	= 5;
	
	static final int idColNum			= 0;
	static final int nameColNum			= 1;
	static final int genderCellColNum	= 2;
	static final int latitudeColNum		= 3;
	static final int longitudeColNum	= 4;

	String id = "";	
	String name = "";
	String gender = "";
	String latitude = "";
	String longitude = "";
}


class ComparatorCell implements Comparator<CellMetaData> {
    @Override
    public int compare(CellMetaData o1, CellMetaData o2) {
		String colRegex = "^[A-z]+";
		String rowRegex = "\\d+$";
    	
    	String col1 = new String();
    	int row1 = 0;
    	String col2 = new String();
    	int row2 = 0;
		
		Pattern scp = Pattern.compile(colRegex);
		Matcher scm = scp.matcher(o1.name);
		if(scm.find()) {
			col1 = scm.group();
		}
		
		Pattern ecp = Pattern.compile(colRegex);
		Matcher ecm = ecp.matcher(o2.name);
		if(ecm.find()) {
			col2 = ecm.group();
		}
		
		Pattern srp = Pattern.compile(rowRegex);
		Matcher srm = srp.matcher(o1.name);
		if(srm.find()) {
			row1 = Integer.parseInt(srm.group());
		}
		
		Pattern erp = Pattern.compile(rowRegex);
		Matcher erm = erp.matcher(o2.name);
		if(erm.find()) {
			row2 = Integer.parseInt(erm.group());
		}
		
		if(!o1.id.equals(o2.id)) {
			return o1.id.compareTo(o2.id);
		} else if(row1 == row2) {
			return (col1.length() == col2.length()) ? col1.compareTo(col2) : col1.length()-col2.length();
		} else {
			return row1 - row2;
		}
	
    }
}