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
	
	@Test
	public void test16(){
		gla.pgbuilder.builder.PgBuilder.setWsdPojo("gla.myapp.pojo");
		  String wsd_url = "wsd://com.sss.test/s:service;";
		  gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_url);
	}
	@Test
	public void test17(){
		String wsd_pojo="gla.myapp.pojo";
		String wsd_url=
		  "wsd://com.sss/s:service;";
		gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_pojo, wsd_url);
	}
	
	@Test
	public void test18(){
		gla.pgbuilder.builder.PgBuilder.setWsdPojo("gla.myapp.pojo");
		  String wsd_url = "wsd://com.test.b/w:controller;";
		  gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_url);
	}
	@Test
	public void test19(){
		String wsd_pojo="gla.myapp.pojo";
		String wsd_url=
		  "wsd://com.test/w:controller;";
		gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_pojo, wsd_url);
	}
	
	@Test
	public void test20(){
		gla.pgbuilder.builder.PgBuilder.setWsdPojo("gla.myapp.pojo");
		String wsd_url=
		  "wsd://com.txt/s:service;d:mapper;";
		gla.pgbuilder.builder.PgBuilder.pgBuild( wsd_url);
	}
	//等待综合测试
	@Test
	public void test21(){
		//第一种url综合测试
		gla.pgbuilder.builder.PgBuilder.setWsdPojo("gla.myapp.pojo");
		String wsd_url=
		  "wsd://com.terento/w:controller;s:service,ebi.ebo;d:dao,idao.impl;";
		gla.pgbuilder.builder.PgBuilder.pgBuild( wsd_url);
	}
	@Test
	public void test22(){
		//第二种url综合测试
		String wsd_pojo="gla.myapp.pojo";
		String wsd_url=
		  "wsd://com.terento/w:controller;s:service,ebi.ebo;d:dao,idao.impl;";
		gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_pojo, wsd_url);
	}
	
	@Test
	public void test23(){
		gla.pgbuilder.builder.PgBuilder.setWsdPojo("gla.myapp.pojo");
		String wsd_url=
		  "wsd://com.ggg/w:action;s:service.impl;";
		gla.pgbuilder.builder.PgBuilder.pgBuild( wsd_url);
	}
	@Test
	public void test24(){
		String wsd_pojo="gla.myapp.pojo";
		String wsd_url=
		  "wsd://com.ggg/w:action;s:service.impl;";
		gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_pojo, wsd_url);
	}
	@Test
	public void test25(){
		gla.pgbuilder.builder.PgBuilder.setWsdPojo("gla.myapp.pojo");
		String wsd_url=
		  "wsd://com.ggg/w:action;s:service.impl;d:dao.impl;";
		gla.pgbuilder.builder.PgBuilder.pgBuild( wsd_url);
	}
	@Test
	public void test26(){
		gla.pgbuilder.builder.PgBuilder.setWsdPojo("gla.myapp.pojo");
		String wsd_url=
		  "wsd://com.ggg/w:action;s:service;d:mapper;";
		gla.pgbuilder.builder.PgBuilder.pgBuild( wsd_url);
	}
	//对于dao层：
	//如果有实现类，就在接口上写Repository注解
	//如果没有实现类，就啥也不写
	
	//测试：第一种url,s,d层包含点与不包含点的情况
	//预计结果：s层任何情况在实现类上写上service注解
	//d层包含点时在接口上写上repository注解
	//不包含点时只生成接口,接口上什么都不写。
	@Test
	public void test27(){
		gla.pgbuilder.builder.PgBuilder.setWsdPojo("gla.myapp.pojo");
		String wsd_url=
		  "wsd://com.kkk/w:action;s:service.impl,ebo;d:mapper,dao.impl;";
		gla.pgbuilder.builder.PgBuilder.pgBuild( wsd_url);
	}
	@Test
	public void test28(){
		String wsd_pojo="gla.myapp.pojo";
		String wsd_url=
		  "wsd://com.kkk/w:action;s:service.impl,ebo;d:mapper;";
		gla.pgbuilder.builder.PgBuilder.pgBuild(wsd_pojo, wsd_url);
	}
	//getSuffix,getFileSuffix就对了
	//发现无法注入，因为存在多个service,dao
	//因为wsd允许这样写：s:service.impl,ebi.ebo;
	//那么在进行注入时应该注入哪个包下的类?
	
	
}
