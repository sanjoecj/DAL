package com.utilities.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UnitTest implements Runnable {

	private static Properties configProp = new Properties();

	public void run() {

		System.setProperty("config_path", loadProps2());
		
		ExcelReader excelReader1 = FileReaderProducer.getExcelReader("D:\\test\\PersonalAdministration.xls");
		ExcelReader excelReader2 = FileReaderProducer.getExcelReader("D:\\test\\PersonalAdministration.xls");

		ExcelReader excelReader3 = FileReaderProducer.getExcelReader("D:\\test\\PersonalAdministration.xlsx");
		ExcelReader excelReader4 = FileReaderProducer.getExcelReader("D:\\test\\PersonalAdministration.xlsx");

		System.out.println("==========================");

		System.out.println(excelReader1);
		System.out.println(excelReader2);

		System.out.println(excelReader1.getCellData("Test Cases", 0, 2));

		System.out.println(excelReader3);
		System.out.println(excelReader4);

		System.out.println(excelReader3.getCellData("Test Cases", 0, 2));

	}

	public static String loadProps2() {
		InputStream in = UnitTest.class.getClassLoader().getResourceAsStream("logfile.properties");
		String configPath = "";
		try {
			configProp.load(in);
			configPath = configProp.getProperty("config_path");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return configPath;
	}

}
