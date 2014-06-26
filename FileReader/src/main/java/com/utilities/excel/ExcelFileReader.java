package com.utilities.excel;

import java.util.HashMap;
import java.util.List;

public interface ExcelFileReader {
	
	public List<String> getColumnData(String sheetName, int colNum);
	
	public List<String> getColumnData(String sheetName, String colName);
	
	public String getCellData(String sheetName, String colName, int rowNum);
	
	public String getCellData(String sheetName, int colNum, int rowNum);
	
	public HashMap<String, String> getRowData(String sheetName, int rowNum);
		
	public boolean isSheetExist(String sheetName);
	
	public int getColumnCount(String sheetName);
		
	public int getCellRowNum(String sheetName, String colName, String cellValue);
		
}
