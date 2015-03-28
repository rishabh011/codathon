package com.codathon.rish.trashtools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

import com.codathon.rish.proto.UserInfoPb;

public class ExcelHelper {


	public static void main(String[] args) {
		List<CellMetaData> listData = new ArrayList<CellMetaData>();

		UserInfoPb.Builder to1 = UserInfoPb.newBuilder();
	    to1.clearId();
	    to1.setId("1");
	    to1.clearName();
	    to1.setName("r");
	    to1.clearGender();
	    to1.setGender(Integer.parseInt("1"));
	    to1.clearLocation();
	    to1.getLocationBuilder().setLat("123");
	    to1.getLocationBuilder().setLong("1231");
	    listData.add(ObjectConverterUpdater.updater(to1.build()));
	    

		UserInfoPb.Builder to2 = UserInfoPb.newBuilder();
	    to2.clearId();
	    to2.setId("2");
	    to2.clearName();
	    to2.setName("ru");
	    to2.clearGender();
	    to2.setGender(Integer.parseInt("2"));
	    to2.clearLocation();
	    to2.getLocationBuilder().setLat("1234");
	    to2.getLocationBuilder().setLong("12316");
	    listData.add(ObjectConverterUpdater.updater(to2.build()));
	    

		String outFileName = "/home/ilodge/Desktop/OutFile.xlsx";
	    Set<CellMetaData> cmdList = readExcelDataInSet(outFileName);
	    List<UserInfoPb> userList = new ArrayList<UserInfoPb>();
	    for(CellMetaData cmd : cmdList) {
	    	userList.add(ObjectConverterUpdater.converter(cmd));
	    }
	    System.out.println(userList.get(0).toString());
	}
	
	//prints headers in output file
	public static void writeHeaders(HSSFSheet sheet, int rowNum) {
		
		HSSFCellStyle style = sheet.getWorkbook().createCellStyle();
		style.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.ALIGN_CENTER);
		Font font = sheet.getWorkbook().createFont();
		font.setColor(IndexedColors. WHITE.getIndex());
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);
		
		Row row = sheet.createRow(rowNum);
        Cell cell0 = row.createCell(CellMetaData.idColNum);
        cell0.setCellValue((String) "id");
        cell0.setCellStyle(style);
        Cell cell1 = row.createCell(CellMetaData.nameColNum);
        cell1.setCellValue((String) "name");
        cell1.setCellStyle(style);
        Cell cell2 = row.createCell(CellMetaData.genderCellColNum);
        cell2.setCellValue((String) "gender");
        cell2.setCellStyle(style);
        Cell cell3 = row.createCell(CellMetaData.latitudeColNum);
        cell3.setCellValue((String) "latitude");
        cell3.setCellStyle(style);
        Cell cell4 = row.createCell(CellMetaData.longitudeColNum);
        cell4.setCellValue((String) "longitude");
        cell4.setCellStyle(style);
	}
	

	public static void writeListDataToFile(
			List<CellMetaData> listData, String outFileName, boolean append) {
		try {
			System.out.println("writing raw data to file ...");
			
			int rowNum = 0;
			File file = new File(outFileName);
			if(!file.isFile()||!append) {
				file.createNewFile();
				HSSFWorkbook workbook=new HSSFWorkbook();
				HSSFSheet sheet =  workbook.createSheet("All data");

				writeHeaders(sheet, rowNum++);
				FileOutputStream fileOut =  new FileOutputStream(file);
				workbook.write(fileOut);
				fileOut.close();
			}

			FileInputStream fileIS = new FileInputStream(file);

			HSSFWorkbook workbook = new HSSFWorkbook(fileIS);
			HSSFSheet sheet = workbook.getSheet("All data");

			Iterator<Row> rowIterator = sheet.iterator();
			if(append) {
				while (rowIterator.hasNext()) {
					HSSFRow row = (HSSFRow) rowIterator.next();
					rowNum = row.getRowNum();
				}
				rowNum++;
			} else {
				rowNum = 1;
			}	

			for(CellMetaData cmd : listData) {
				HSSFRow row = sheet.createRow(rowNum++);
				Cell cell0 = row.createCell(CellMetaData.idColNum);
				cell0.setCellValue((String) cmd.id);
				Cell cell1 = row.createCell(CellMetaData.nameColNum);
				cell1.setCellValue((String) cmd.name);
				Cell cell2 = row.createCell(CellMetaData.genderCellColNum);
				cell2.setCellValue((String) cmd.gender);
				Cell cell3 = row.createCell(CellMetaData.latitudeColNum);
				cell3.setCellValue((String) cmd.latitude);
				Cell cell4 = row.createCell(CellMetaData.longitudeColNum);
				cell4.setCellValue((String) (cmd.longitude));
			}
			try {
				FileOutputStream out = new FileOutputStream(new File(outFileName));
				workbook.write(out);
				out.close();
				System.out.println("File write successful");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch(Exception e) {
	        System.err.println("File write error");
	        e.printStackTrace();
		}
	}
	

	public static Set<CellMetaData> readExcelDataInSet(String fileName) {
		
		Set<CellMetaData> allDataSet = new HashSet<CellMetaData>();
		try {
			FileInputStream file = new FileInputStream(new File(fileName));

			HSSFWorkbook workbook = new HSSFWorkbook(file);
			
			HSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			
			//skip headers
			if(rowIterator.hasNext())
				rowIterator.next();
			
			while (rowIterator.hasNext())
			{
				HSSFRow row = (HSSFRow) rowIterator.next();
				CellMetaData cmd = new CellMetaData();

				HSSFCell cell = row.getCell(CellMetaData.idColNum);
				cmd.id = cell.getStringCellValue().trim();

				cell = row.getCell(CellMetaData.nameColNum);
				cmd.name = cell.getStringCellValue().trim();

				cell = row.getCell(CellMetaData.genderCellColNum);
				cmd.gender = cell.getStringCellValue().trim();

				cell = row.getCell(CellMetaData.latitudeColNum);
				cmd.latitude = cell.getStringCellValue().trim();

				cell = row.getCell(CellMetaData.longitudeColNum);
				cmd.longitude = cell.getStringCellValue().trim();
				
				allDataSet.add(cmd);
				
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allDataSet;
	}
}
