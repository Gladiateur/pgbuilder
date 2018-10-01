/*
 * PgBuilder.java 18/9/30
 */
package versions;

import gla.pgbuilder.exception.WsdException;

import java.io.File;
import java.util.Set;

/**
 * Package Builder
 * 
 * @author Gladiateur
 * @since 1.0
 */
public class PgBuilder {

	//解析wsd_url
	private Wsd resolver(String wsd_url){
		if(wsd_url.length() < 6){
			throw new WsdException("wsd_url头长度非法");
		}
		String wsd_url_pg=null;
		String wsd_body = wsd_url.substring(6);
		String params_body = null;
		Wsd wsd = new Wsd() ;
		//第一步：解析wsd_url
		//1.去除wsd_url首尾空格。
		wsd_url = wsd_url.trim();
		//2.忽略大小写验证头部分：
		String head = wsd_url.substring(0, 6);
		boolean headFlag = head.equalsIgnoreCase("wsd://");
//		System.out.println("wsd头信息："+headFlag);
		if(!headFlag){
			//头信息错误
			throw new WsdException("wsd_url头部必须为：wsd://");
		}
		//头信息验证正确
		//3.证明在前缀后使用"/"符号后不再使用该符号：
		boolean formatFlag = wsd_body.split("/").length == 2;
//			System.out.println("wsd头后有且仅有一个/符号："+formatFlag);
		if(!formatFlag){
			throw new WsdException("wsd_body格式错误：wsd://perfix/x:xxx,...;");
		}
		//4.解析前缀包名：
		wsd_url_pg = wsd_body;
		wsd_url_pg = wsd_url_pg.substring(0,wsd_body.indexOf('/'));
//				System.out.println("wsd_url_pg="+wsd_url_pg);
		//这里必须验证包名的合法性,用正则校验
		boolean rgFlag = wsd_url_pg.trim().
			matches("([a-zA-Z_][a-zA-Z0-9_]*[.])*([a-zA-Z_][a-zA-Z0-9_]*)$");
//				System.out.println("正则校验包名结果："+rgFlag);
		if(!rgFlag){
			throw new WsdException("包名："+wsd_url_pg+"不合法");
		}	
		//校验通过，包名合法
		//5.解析参数部分：
//					wsd_body = wsd_body;
		//6.1 去除参数体首尾空格 params_body=params_body.trim()
		params_body = wsd_body.
			substring(wsd_body.indexOf('/')+1).trim();
		//6.1.1  正则判断wsd_body的/后面的格式是否是：x:aaa,bbb,...;y:cc.dd,ee,...;这种格式
		boolean bodyFlag = params_body
		  .matches("([a-zA-Z][:]([a-zA-Z_][a-zA-Z0-9_]*[.|,])*([a-zA-Z_][a-zA-Z0-9_]*)[;])*$");
		if(!bodyFlag){
			throw new WsdException("wsd://perfix/wsd_body格式错误！");
		}
//		System.out.println("params_body="+params_body);
		
		//6.2 获取数组：
		String[] paramsBodyArr = params_body.split(";");
		for (String string : paramsBodyArr) {
			String symbol = string.substring(0,1);
			if("w".equalsIgnoreCase(symbol)){
				if(wsd.getWeb() != null){
					throw new IllegalArgumentException("w");
				}
				wsd.setWeb(string.substring(2));
			}
			if("s".equalsIgnoreCase(symbol)){
				if(wsd.getService() != null){
					throw new IllegalArgumentException("s");
				}
				wsd.setService(string.substring(2));
			}
			if("d".equalsIgnoreCase(symbol)){
				if(wsd.getDao() != null){
					throw new IllegalArgumentException("d");
				}
				wsd.setDao(string.substring(2));
			}
		}
		wsd.setPerfix(wsd_url_pg);
		return wsd;
	}
	
	private void pgBuilder(Set<String> set){
		for (String path : set) {
			File f = new File("src/"+path);
			if(!f.exists()){
				f.mkdirs();
			}
		}
	}
	
	public static void pgBuild(String wsd_url){
		PgBuilder pgBuilder = new PgBuilder();
		Wsd wsd = pgBuilder.resolver(wsd_url);
		pgBuilder.pgBuilder(wsd.combiner());
	}
}