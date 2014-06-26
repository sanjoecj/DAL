package com.utilities.excel;


public class FileReaderProducer {

	public static ExcelReader getExcelReader(String excelFilePath) {

		if (excelFilePath == null) {
			return null;
		} else {
			return ExcelReader.getInstance(excelFilePath);
		}
	}

}
