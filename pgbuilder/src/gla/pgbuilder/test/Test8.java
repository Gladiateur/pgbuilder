package gla.pgbuilder.test;

import org.junit.Test;

//pgb-1.0.jar tester
public class Test8 {
	@Test
	public void test(){
		//pgb-0.99.jar
		//测试第一种url
		gla.pgbuilder.builder.PgBuilder.setWsdPojo("gla.myapp.pojo");
		String wsd_url =
		  "wsd://com.isoft.common/w:web.action;s:ebi.ebo;d:dao.impl;";
		gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_url);
	}
	//pgb-0.99.jar 第二种url test pass ok
	@Test
	public void test0(){
		//pgb-0.99.jar
		//测试第二种url
		String wsd_pojo="gla.myapp.pojo";
		String wsd_url=
		  "wsd://com.isoft.common/w:controller;s:service.impl;d:mapper,dao.impl;";
		gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_pojo, wsd_url);
	}
	
	/////源版本中的代码先留着，优化后等稳定运行了再删
	@Test
	public void test1(){
		String wsd_pojo="gla.myapp.pojo";
		String wsd_url=
		  "wsd://com.isoft.common/w:controller,web.actions;";
		gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_pojo, wsd_url);
	}
	@Test
	public void test2(){
		String wsd_pojo="gla.myapp.pojo";
		String wsd_url=
		  "wsd://com.isoft.common/s:server.impl,service;";
		gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_pojo, wsd_url);
	}
	@Test
	public void test3(){
		String wsd_pojo="gla.myapp.pojo";
		String wsd_url=
		  "wsd://com.isoft.common/d:mapper,dao.impl;";
		gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_pojo, wsd_url);
	}
	@Test
	public void test4(){
		gla.pgbuilder.builder.PgBuilder.setWsdPojo("gla.myapp.pojo");
		String wsd_url = "wsd://com.isoft.apx/w:controll,web.action;";
		gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_url);
	}
	@Test
	public void test5(){
		gla.pgbuilder.builder.PgBuilder.setWsdPojo("gla.myapp.pojo");
		  String wsd_url = "wsd://com.isoft.ooxx/s:abc.def;";
		  gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_url);
	}
	@Test
	public void test6(){
		String wsd_pojo="gla.myapp.pojo";
		String wsd_url=
		  "wsd://com.ooxx/s:abc.jkk;";
		gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_pojo, wsd_url);
	}
	@Test
	public void test7(){
		gla.pgbuilder.builder.PgBuilder.setWsdPojo("gla.myapp.pojo");
		  String wsd_url = "wsd://com.isoft.so/d:dao.dao,mapper;";
		  gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_url);
	}
	@Test
	public void test8(){
		String wsd_pojo="gla.myapp.pojo";
		String wsd_url=
		  "wsd://com.isoft/d:mapper,dao.impl;";
		gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_pojo, wsd_url);
	}
	
	
	@Test
	public void test9(){
		gla.pgbuilder.builder.PgBuilder.setWsdPojo("gla.myapp.pojo");
		  String wsd_url = "wsd://com.isoft.gua/s:ebo;";
		  gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_url);
	}
	@Test
	public void test10(){
		String wsd_pojo="gla.myapp.pojo";
		String wsd_url=
		  "wsd://com.hua/s:ebi;";
		gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_pojo, wsd_url);
	}
	
	@Test
	public void test11(){
		gla.pgbuilder.builder.PgBuilder.setWsdPojo("gla.myapp.pojo");
		  String wsd_url = "wsd://com.test.web/w:action,my.controller;";
		  gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_url);
	}
	@Test
	public void test12(){
		String wsd_pojo="gla.myapp.pojo";
		String wsd_url=
		  "wsd://com.web/w:control,uui.action;";
		gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_pojo, wsd_url);
	}
	
	@Test
	//test dao contains dot and not contains dot
	public void test13(){
		gla.pgbuilder.builder.PgBuilder.setWsdPojo("gla.myapp.pojo");
		  String wsd_url = "wsd://com.testdao.demo/d:mapper;";
		  gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_url);
	}
	@Test
	public void test14(){
		String wsd_pojo="gla.myapp.pojo";
		String wsd_url=
		  "wsd://com.testdao/d:mapper;";
		gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_pojo, wsd_url);
	}
	@Test
	public void test15(){
		gla.pgbuilder.builder.PgBuilder.setWsdPojo("gla.myapp.pojo");
		  String wsd_url = "wsd://com.webtest.web/w:controller,haha.action;";
		  gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_url);
	}
}
