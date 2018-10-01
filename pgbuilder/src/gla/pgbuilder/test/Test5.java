package gla.pgbuilder.test;

public class Test5 {

	public static void main(String[] args) {
		String wsd_url = 
		"wsd://gla.myapp.pgbuilder/w:controller;s:service.impl;d:mapper;";	
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
		
		/*
		 * 		
		 * 		
		 * 		6.内部类：参数体对象
		 * 			例如：w:controller;s:service.impl;d:mapper;
		 * 			
		 * 			现在的思路是定义三个变量web,service,dao,且初始化均为null,
		 * 			循环遍历途中判断首个字符，并赋值给对应变量，
		 * 			若该变量不为null(说明已被赋值，同时也说明本次循环收个字符又出现一次，所以抛出参数异常)
		 * 			若该变量为null则进行赋值。
		 * 					
		 * 		7.至此，表达式解析完成。原表达式：
		 * 		 wsd_url = "wsd://gla.myapp.pgbuilder/w:controller;s:service.impl;d:mapper;";	
		 * 		在经过格式校验之后，已被解析为：
		 * 			perfix:gla.myapp.pgbuilder
		 * 			web:controller
		 * 			service:service.impl
		 * 			dao:mapper
		 * 		8.整理一下：
		 * 			wsd_pojo = "gla.myapp.pojo"
		 * 			wsd_url已被解析。
		 * 			perfix = gla.myapp.pgbuilder
		 * 			web = controller
		 * 			service = service.impl
		 * 			dao = mapper
		 * 			
		 * 			扫描器：扫描gla.myapp.pojo包下的实体类。
		 * 			扫描后结果：
		 * 				User,Order。
		 * 			根据上述条件可得：
		 * 				gla.myapp.pgbuilder.controller.UserController
		 * 				gla.myapp.pgbuilder.controller.OrderController
		 * 				gla.myapp.pgbuilder.service.UserService
		 * 				gla.myapp.pgbuilder.service.impl.UserServiceImpl
		 * 				gla.myapp.pgbuilder.service.OrderService
		 * 				gla.myapp.pgbuilder.service.impl.OrderServiceImpl
		 * 				gla.myapp.pgbuilder.mapper
		 * 
		 * 		即可完成构建项目包结构。
		 * 		之后便可根据实体类构建各层的类，接口，并实现依赖注入。
		 * 	
		 * 		java package API 
		 */
		System.out.println("===========");
		System.out.println("perfix="+wsd.getPerfix());
		System.out.println("web="+wsd.getWeb());
		System.out.println("service="+wsd.getService());
		System.out.println("dao="+wsd.getDao());
	}
}