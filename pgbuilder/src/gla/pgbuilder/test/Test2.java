package gla.pgbuilder.test;

import java.io.File;

//package api test
public class Test2 {
	//package   
	/*
	 * package api
	 * 	void create();
	 * 	Class<?>[] getClasses();
	 * 	boolean containsClasses();
	 * 	String[] getClassedSimpleName();
	 */
	public static void main(String[] args) {
//		 Package[] pgs = Package.getPackages();
//		for (Package package1 : pgs) {
//			System.out.println(package1);
//		}
		
		//test file api
		File f = new File("src/com/hahaha");
		System.out.println(f.getAbsolutePath());
		System.out.println(f.exists());
		if(!f.exists()){
			f.mkdirs();
		}
		
		
	}
}
