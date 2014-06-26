package com.utilities.excel;

public class ThreadTest {

	
	public static void main(String[] args) {
		
		
		for(int i = 1; i <=1; i++){
			
			Thread t = new Thread(new UnitTest());
			t.start();
		}
		
		
	}

}
