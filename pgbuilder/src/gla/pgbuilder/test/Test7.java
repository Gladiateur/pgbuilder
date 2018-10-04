package gla.pgbuilder.test;

import java.io.File;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Test;

public class Test7 {
	
	@Test
	public void test(){
		String wsd_url =
			  "wsd://com.apple.fantee/w:controller;s:service.impl;d:mapper,dao.impl;";
			gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_url);
	}
	
	//bug:wsd_url = "wsd://xx/xx";时校验通过但无结果
	@Test
	public void test0(){
		String wsd_url =
			  "wsd://xx/xx;";
			gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_url);
	}
	
	@Test
	public void test1(){
		//3.1 正则判断wsd_body的/后面的格式是否是：x:aaa,bbb,...;y:cc.dd,ee,...;这种格式
		//"w:aaa.bbb,c.d,ttttt;s:mmm,bbb;d:x.x;"
		System.out.println(
		  "w:action,controller;s:service,service.impl;d:mapper,dao,dao.impl;"
		  .matches("([a-zA-Z][:]([a-zA-Z_][a-zA-Z0-9_]*[.|,])*([a-zA-Z_][a-zA-Z0-9_]*)[;])*$"));
	} 
	
	//wsd://perfix/x:pg1.pg2.pg3,pg,...;末尾必须分号结尾
	
	//bug:wsd://perfix/x:x;校验通过，但运行无结果。
	@Test
	public void test2(){
		String wsd_url =
			  "wsd://com.gdsoft.isoft/x:x;";
			gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_url);
	}
	//pg: 表示不清楚属于哪一层。mvc之外的包。
	@Test
	public void test3(){
		//正则匹配参数
		System.out.println("pg".matches("[w|s|d]|pg"));
	}
	@Test
	public void test4(){
		//test pg
		String wsd_url =
			  "wsd://com.gdsoft.isoft/pg:exception;";
			gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_url);
	}
	
	@Test
	public void test5(){
		//一般测试
		String wsd_url =
			  "wsd://cn.itcast.moon/pg:controller,service.impl,dao.impl,mapper;";
			gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_url);
		
	}
	@Test
	public void test6(){
		String wsd_url =
			  "wsd://cn.itcast.suns/w:web,action;s:service,tx.impl;d:mapper;";
			gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_url);
	}
	//test ok
	@Test
	public void test7(){
		String wsd_url =
			  "wsd://cn.itcast.water/w:action;s:service.impl;d:mapper;pg:utils.interceptor,utils.filter,realm,exception;";
			gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_url);
	}
	
	//File API
	@Test
	public void test8(){
		File file = new File("src/gla/myapp/pojo");
		System.out.println(file.exists());
		System.out.println(file.isDirectory());
		String[] list = file.list();
		for (String string : list) {
			System.out.println(string);
		}
		System.out.println("==================");
		File[] listFiles = file.listFiles();
		for (File file2 : listFiles) {
			System.out.println(file2.getName());
		}
	}
	
	@Test
	public void test9(){
		//封装File API
		//通过包名获取该包下的所有java文件名，返回Set集合
		String perfix = "gla.myapp.pojo";
		String suffix = "java";
		Set<String> names = getNamesOfPackageAndFileSuffix(perfix, suffix);
		System.out.println("Set: "+names);
	}
	//通过包名获取该包下的所有java文件名，返回Set集合
	private Set<String> getNamesOfPackageAndFileSuffix(String perfix,
			String suffix) {
		if(perfix==null || suffix == null){
			throw new NullPointerException();
		}
		perfix = perfix.replaceAll("\\.", "/");
		File file = new File("src/"+perfix);
		System.out.println(file.exists());
		System.out.println(file.isDirectory());
		if(!file.exists()){
			throw new NoSuchElementException("目标:"+perfix+"不存在");
		}
		String[] list = file.list();
		Set<String> names = new HashSet<String>();
		for (String string : list) {
			int index = string.lastIndexOf('.');
			if(suffix.equalsIgnoreCase(string.substring(index+1))){
				names.add(string.substring(0,index).toLowerCase());
			}
		}
		return names;
	}
	//增强构建功能
	//增强后的pg参数有个小bug
	@Test
	public void test10(){
		String wsd_pojo="gla.myapp.pojo";
		String wsd_url =
		   "wsd://cn.itcast.gedeng/w:action;s:service.impl;d:mapper;pg:utils.interceptor,utils.filter,realm,exception;";
		 gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_pojo, wsd_url);
	}
	
	/*String path0 = conditionBean.getPath();
	File file = new File(path0);
	if(!file.exists()){
		file.mkdirs();
	}
	try {
		bos = new BufferedOutputStream(new FileOutputStream(
				new File(path0+"/"+Core.captureName(tableName)+".java")));
		bos.write(this.entityContent(tableName).toString().getBytes());
		bos.close();*/
	@Test
	public void test11(){
		//生成对应的类或接口
		/*String path0 = "/src/com/imooc/user/controller";
		File file = new File(path0);
		if(!file.exists()){
			file.mkdirs();
		}
		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(
					new File(path0+"/"+Core.captureName(tableName)+".java")));
			bos.write(this.entityContent(tableName).toString().getBytes());
			bos.close();
		}catch(Exception e){
		}*/
	}
	@Test
	public void test12(){
		gla.pgbuilder.builder.PgBuilder.setWsdPojo("safd..asfd");
		String wsdPojo = gla.pgbuilder.builder.PgBuilder.getWsdPojo();
		System.out.println(wsdPojo);
	}
	@Test
	public void test13(){
		String wsd_url =
			  "wsd://cn.itcast.water/w:action,web.controller;";
			gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_url);
	}
	@Test
	public void test14(){
		String wsd_pojo="gla.myapp.pojo";
		String wsd_url =
		   "wsd://cn.gd.app/w:web.action,web.controller;";
		 gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_pojo, wsd_url);
	}
	
	@Test
	public void test15(){
		/*
		 * 跟一般的方式构建跟复杂的包结构
		 * 
		 * 
		 */
	}
	
	@Test
	public void test16(){
		/*
		 * wsd默认生成策略：DefaultMVCTemplate
		 * 	w:	1.只在最后一个子包下生成类
		 * 		2.生成后缀默认为包名
		 *  s:	1.若存在子包则在及最后一个子包下生成类，在上一个子包下生成接口
		 *  	2.若不存在则在最后一个包下生成接口和类
		 *  d:	与s相同
		 *  
		 *  
		 */
	}
	@Test
	public void test17(){
		gla.pgbuilder.builder.PgBuilder.setWsdPojo("gla.myapp.pojo");
		String wsd_url =
			  "wsd://cn.mine.test/w:web.action,controller;s:service.impl,ebo;";
			gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_url);
	}
	
	@Test
	public void test18(){
		gla.pgbuilder.builder.PgBuilder.setWsdPojo("gla.myapp.pojo");
		String wsd_url =
			  "wsd://cn.mine.test/d:mapper,dao.impl;";
			gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_url);
	}
	@Test
	public void test19(){
		String wsd_pojo = "gla.myapp.pojo";
		String wsd_url =
			  "wsd://cn.mine/s:service.impl,ebo;";
			gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_pojo , wsd_url);
	}
	
	@Test
	public void test20(){
		String wsd_pojo = "gla.myapp.pojo";
		String wsd_url =
			  "wsd://cn.mpp/d:mapper,dao.impl;";
			gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_pojo , wsd_url);
	}
	
}
