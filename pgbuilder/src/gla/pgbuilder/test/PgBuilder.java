/*
 * PgBuilder.java 18/9/30
 */
package gla.pgbuilder.test;

import java.io.File;
import java.util.Set;

public class PgBuilder {

	//解析wsd_url
	private Wsd resolver(String wsd_url){
		String wsd_url_pg=null;
		String wsd_body = null;
		String params_body = null;
		Wsd wsd = new Wsd() ;
		//第一步：解析wsd_url
		//1.去除wsd_url首尾空格。
		wsd_url = wsd_url.trim();
		System.out.println(wsd_url);
		//2.忽略大小写验证头部分：
		boolean headFlag = wsd_url.substring(0, 6).equalsIgnoreCase("wsd://");
		System.out.println("wsd头信息："+headFlag);
		if(headFlag){
			//头信息验证正确
			//3.证明在前缀后使用"/"符号后不再使用该符号：
			boolean formatFlag = wsd_url.substring(6).split("/").length == 2;
			System.out.println("wsd头后有且仅有一个/符号："+formatFlag);
			if(formatFlag){
				//4.解析前缀包名：
				wsd_url_pg = wsd_url.substring(6);
				wsd_url_pg = wsd_url_pg.substring(0,wsd_url.substring(6).indexOf('/'));
				System.out.println("wsd_url_pg="+wsd_url_pg);
				
				//这里必须验证包名的合法性,用正则校验
				boolean rgFlag = wsd_url_pg.trim().
					matches("([a-zA-Z_][a-zA-Z0-9_]*[.])*([a-zA-Z_][a-zA-Z0-9_]*)$");
				System.out.println("正则校验包名结果："+rgFlag);
				if(rgFlag){
					//校验通过，包名合法
					//5.解析参数部分：
					wsd_body = wsd_url.substring(6);
					//6.1 去除参数体首尾空格 params_body=params_body.trim()
					params_body = wsd_body.
						substring(wsd_body.indexOf('/')+1).trim();
					System.out.println("params_body="+params_body);
					//6.2 判断末尾是否是分号,由于末尾是否有分号或连续几个分号并不影响结果因此该步骤省略。
					//6.3 获取数组：
					String[] paramsBodyArr = params_body.split(";");
//					String web = null,service = null , dao = null;
//					Wsd wsd = new Wsd();
					for (String string : paramsBodyArr) {
//						System.out.println(string.substring(0,1));
//						System.out.println(string.trim());
						if("w".equalsIgnoreCase(string.substring(0,1))){
							if(wsd.getWeb() != null){
								throw new IllegalArgumentException("w");
							}
							wsd.setWeb(string.substring(2));
						}
						if("s".equalsIgnoreCase(string.substring(0,1))){
							if(wsd.getService() != null){
								throw new IllegalArgumentException("s");
							}
							wsd.setService(string.substring(2));
						}
						if("d".equalsIgnoreCase(string.substring(0,1))){
							if(wsd.getDao() != null){
								throw new IllegalArgumentException("d");
							}
							wsd.setDao(string.substring(2));
						}
					}
					wsd.setPerfix(wsd_url_pg);
				}else{
					throw new RuntimeException("包名："+wsd_url_pg+"不合法");
				}
			}else{
				throw new RuntimeException("wsd体格式错误：wsd头后有且仅有一个/符号");
			}
			
		}else{
			//头信息错误
			throw new RuntimeException("head --> wsd://");
		}
		return wsd;
	}
	
	private void pgBuilder(Set<String> set){
		for (String path : set) {
			File f = new File("src/"+path);
			System.out.println(f.getAbsolutePath());
			if(!f.exists()){
				f.mkdirs();
			}
		}
	}
	
	public static void pgBuild(String wsd_url){
		Wsd wsd = new PgBuilder().resolver(wsd_url);
		Set<String> combiner = wsd.combiner();
		for (String string : combiner) {
			System.out.println(string);
		}
		PgBuilder pgBuilder = new PgBuilder();
		pgBuilder.pgBuilder(combiner);
	}
	
	public static void main(String[] args) {
		String wsd_url =
		  "wsd://gla.pgbuilder/w:builder";
		pgBuild(wsd_url);
		
		/*String wsd_url = 
		"wsd://gla.myapp.pgbuilder/w:controller,action;s:service.impl,ebi.ebo;d:mapper,dao.impl;";	
		Wsd wsd = new PgBuilder().resolver(wsd_url);
//		System.out.println(wsd);
		System.out.println(wsd.getPerfix());
		System.out.println(wsd.getWeb());
		System.out.println(wsd.getService());
		System.out.println(wsd.getDao());
		
		Set<String> combiner = wsd.combiner();
		for (String string : combiner) {
			System.out.println(string);
		}
		PgBuilder pgBuilder = new PgBuilder();
		pgBuilder.pgBuilder(combiner);*/
	}
}