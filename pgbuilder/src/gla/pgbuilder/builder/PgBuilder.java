/*
 * PgBuilder.java 18/9/30
 */
package gla.pgbuilder.builder;

import gla.pgbuilder.exception.WsdException;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * Package Builder
 * 
 * @author Gladiateur
 * @since 1.0
 */
public class PgBuilder {
	
	private Logger logger = Logger.getLogger(PgBuilder.class);
	
	String wsd_url = null;
	String wsd_url_pg = null;
	String wsd_body = null;
	String params_body = null;
	Wsd wsd = new Wsd() ;
	
	//解析wsd_url
	private Wsd resolver(String wsd_url){
		urlChecker(wsd_url);
		logger.debug("wsd_url="+wsd_url);
		logger.debug("params_body="+params_body);
		String[] paramsBodyArr = params_body.split(";");
		for (String string : paramsBodyArr) {
			String symbol = string.split(":")[0];
			if(!(symbol.matches("[w|s|d]|pg"))){
				throw new WsdException("参数不合法。参数列表：w:web,s:service,d:dao,pg:any");
			}
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
			if("pg".equalsIgnoreCase(symbol)){
				if(wsd.getPg() != null){
					throw new IllegalArgumentException("pg");
				}
				wsd.setPg(string.substring(3));
			}
		}
		wsd.setPerfix(wsd_url_pg);
		return wsd;
	}

	//wsd_url校验器
	private void urlChecker(String wsd_url) {
		if(wsd_url.length() < 6){
			throw new WsdException("wsd_url头长度非法");
		}
		//1.去除wsd_url首尾空格
		wsd_url = wsd_url.trim();
		//2.忽略大小写验证头部分：
		String head = wsd_url.substring(0, 6);
		boolean headFlag = head.equalsIgnoreCase("wsd://");
		logger.debug("wsd头信息："+headFlag);
		if(!headFlag){
			//头信息错误
			throw new WsdException("wsd_url头部必须为：wsd://");
		}
		//头信息验证正确
		//3.证明在前缀后使用"/"符号后不再使用该符号：
		wsd_body = wsd_url.substring(6);
		boolean formatFlag = wsd_body.split("/").length == 2;
		logger.debug("wsd_body校验,formatFlag="+formatFlag);
		if(!formatFlag){
			throw new WsdException("wsd_body格式错误：wsd://perfix/x:xxx,...;");
		}
		//4.解析前缀包名：
		wsd_url_pg = wsd_body;
		wsd_url_pg = wsd_url_pg.substring(0,wsd_body.indexOf('/'));
		logger.debug("wsd_url_pg="+wsd_url_pg);
		//这里必须验证包名的合法性,用正则校验
		boolean rgFlag = wsd_url_pg.trim().
			matches("([a-zA-Z_][a-zA-Z0-9_]*[.])*([a-zA-Z_][a-zA-Z0-9_]*)$");
		logger.debug("正则校验包名结果："+rgFlag);
		if(!rgFlag){
			throw new WsdException("包名："+wsd_url_pg+"不合法");
		}	
		//校验通过，包名合法
		//6.1 去除参数体首尾空格 params_body=params_body.trim()
		params_body = wsd_body.
			substring(wsd_body.indexOf('/')+1).trim();
		//6.1.1  正则判断wsd_body的/后面的格式是否是：x:aaa,bbb,...;y:cc.dd,ee,...;这种格式
		boolean bodyFlag = params_body
		  .matches("([a-zA-Z]*[:]([a-zA-Z_][a-zA-Z0-9_]*[.|,])*([a-zA-Z_][a-zA-Z0-9_]*)[;])*$");
		if(!bodyFlag){
			throw new WsdException("wsd://perfix/wsd_body格式错误！");
		}
		this.wsd_url = wsd_url;
	}
	
	private void pgBuilder(Set<String> set){
		for (String path : set) {
			File f = new File("src/"+path);
			if(!f.exists()){
				f.mkdirs();
			}
			logger.info("package: "+path);
		}
		
		logger.debug("pojo list:"+names);
		logger.debug(set);
		String perfix = wsd.getPerfix();
		logger.debug("perfix = "+perfix);
		logger.debug("wsd_pojo="+wsd_pojo);
		
		String webs = wsd.getWeb();
		String services = wsd.getService();
		String daos = wsd.getDao();
		if(names.size() == 0){
			if(wsd_pojo!=null){
				//当wsd_pojo不为空时，根据它来生成类
				names = getNamesOfPackageAndFileSuffix(wsd_pojo, "java");
				logger.debug("java_files:"+names);
				//每层都包含全部的实体类相关的类
				if(webs != null){
					webBox(perfix,webs);
				}
				if(services != null){
					//service.impl   service(接口)  impl(类)
					//ebi.ebo		ebi(接口)		ebo(类)
					//service		service(接口前缀I,类后缀为service)
					//impl			impl(接口前缀I，类后缀为impl)
					serviceBox(perfix, services);
				}
				if(daos != null){
					//与service类似
					//存在点时aa.bb.cc在bb生成接口在cc生成实现类
					//不存在点时只生成接口
					daoBox(perfix, daos);
				}
			}
		}else{
			//已实体类为模块生成对应各层的类
			for (String moduleName : names) {
				logger.debug("moduleName >> "+moduleName);
				if(webs != null){
//					webBox(perfix, moduleName, webs);
					webBox2(perfix, webs);
				}
				if(services != null){
					logger.debug("services box2");
//					serviceBox(perfix, moduleName, services);
					serviceBox2(perfix,services);
				}
				if(daos != null){
					logger.debug("dao box...");
					//a.b.c	b包生成接口，c包生成实现类
					//aaa  仅生成接口
//					daoBox(perfix, moduleName, daos);
					daoBox2(perfix,daos);
				}
			}
		}
	}

	private void daoBox2(String perfix,String daos) {
		String[] dao = daos.split(",");
		for (String d : dao) {
			if(d.contains(".")){
				//...a.b.c
				logger.debug("d>>contains dot="+d);
				daoContainsDot2(perfix,d);
			}else{
				//aaa
				logger.debug("d>>not contains dot="+d);
				daoNotContainsDot2(perfix,d);
			}
		}
	}

	private void daoNotContainsDot2(String perfix,String d) {
		String sPath = (perfix+"."+d).replaceAll("\\.", "/");
		String moduleName = perfix.substring(perfix.lastIndexOf(".")+1);
		
		String interfaceName = captureName(moduleName)+captureName(d);
		String interfacePath = sPath+"/"+interfaceName+".java";
		StringBuilder sb = new StringBuilder();
		sb.append("package "+perfix+"."+d+";\n\n");
		sb.append("public interface "+interfaceName+"{\n\n");
		sb.append("\t//TODO \n\n");
		sb.append("}\n");
		output(interfacePath, sb);
	}

	private void daoContainsDot2(String perfix,String d) {
		String suffix = d.substring(0,d.lastIndexOf('.'));
		String package0 = perfix+"."+suffix;
		String moduleName = perfix.substring(perfix.lastIndexOf(".")+1);
		String interfaceName = captureName(moduleName)+captureName(suffix);
		String fileName = package0.replaceAll("\\.", "/") + "/" 
				+ interfaceName + ".java";
		logger.debug("d:fileName="+fileName);
		
		String impl = d.substring(d.lastIndexOf(".")+1);
		logger.debug("d:impl="+impl);
		
		String implSuffix = captureName(suffix) + captureName(impl);
		logger.debug("implSuffix="+implSuffix);
		logger.debug("d >>> "+d);
		String implPath = (perfix +"." +d).replaceAll("\\.", "/");
		logger.debug("implPath="+implPath);
		String implFileName = implPath + "/" 
				+ captureName(moduleName) + implSuffix + ".java";
		
		StringBuilder sb = new StringBuilder();
		sb.append("package "+package0+";\n\n");
		sb.append("public interface "+interfaceName+"{\n\n");
		sb.append("\t//TODO \n\n");
		sb.append("}\n");
		output(fileName, sb);
		
		StringBuilder sb2 = new StringBuilder();
		sb2.append("package "+perfix +"." +d+";\n\n");
		sb2.append("import "+package0+"."+interfaceName+";\n");
		sb2.append("\npublic class "+captureName(moduleName)+implSuffix);
		sb2.append(" implements "+captureName(moduleName)+captureName(suffix)+" {\n\n");
		sb2.append("\t//TODO \n\n");
		sb2.append("}\n");
		output(implFileName, sb2);
	}

//	private void serviceBox(String perfix, String moduleName, String services) {
	private void serviceBox2(String perfix,String services) {
		String[] service = services.split(",");
		for (String s : service) {
			if(s.contains(".")){
				//包括点的情况
				Config config = config(perfix, s);
				String moduleName = perfix.substring(perfix.lastIndexOf(".")+1);
				serviceHandler(perfix, s, config, moduleName);
			}else{
				//不包括点的情况
//				serviceNotContainsDot(perfix, moduleName, s);
				Config config = new Config();
				config.setFileSuffix(s);
				String moduleName = perfix.substring(perfix.lastIndexOf(".")+1);
				outputService2(perfix, s, config, moduleName);
			}
		}
	}

	private void outputService2(String perfix,String s,Config config,String moduleName){
		String sPath = (perfix+"."+s).replaceAll("\\.", "/");
		String interfaceName = config.getFilePerfix() + captureName(moduleName);
		String interfacePath = sPath+"/"+interfaceName+".java";
		StringBuilder sb = new StringBuilder();
		sb.append("package "+perfix+"."+s+";\n\n");
		sb.append("public interface "+interfaceName+"{\n\n");
		sb.append("\t//TODO \n\n");
		sb.append("}\n");
		output(interfacePath, sb);
		
		String implName = captureName(moduleName) + captureName(config.getFileSuffix());
		String implPath =  sPath+"/"+implName+".java";
		StringBuilder sb2 = new StringBuilder();
		sb2.append("package "+perfix +"." +s+";\n\n");
		sb2.append("public class "+implName);
		sb2.append(" implements "+interfaceName+" {\n\n");
		sb2.append("\t//TODO \n\n");
		sb2.append("}\n");
		output(implPath, sb2);
	}

	private Config config(String perfix,String s){
		Config config = new Config();
		String suffix = s.substring(0,s.lastIndexOf('.'));
		config.setSuffix(suffix);
		String package0 = perfix+"."+suffix;
		config.setPackage0(package0);
		String impl = s.substring(s.lastIndexOf(".")+1);
		config.setImpl(impl); 
		config.setImplSuffix(captureName(suffix) + captureName(impl));
		config.setImplPath((perfix +"." +s).replaceAll("\\.", "/"));
		config.setServicePath(package0.replaceAll("\\.", "/"));
		return config;
	}
	
//	private void webBox(String perfix, String moduleName, String webs) {
	private void webBox2(String perfix, String webs) {
		String[] web = webs.split(",");
		for (String w : web) {
			String webSuffix = w.substring(w.lastIndexOf(".")+1);
			webSuffix = captureName(webSuffix);
			logger.debug("webSuffix="+webSuffix);
			String package0 = perfix+"."+w;
			logger.debug(">>>>>>>>>>>>>perfix="+perfix);
			String moduleName = perfix.substring(perfix.lastIndexOf(".")+1);
			String webModulePath =package0 .replaceAll("\\.", "/");
			
			String fileName = captureName(moduleName) + webSuffix;
			String fp = "/" + fileName + ".java";
			String webPath = webModulePath + fp;
			logger.debug("webPath="+webPath);
			
			StringBuilder sb = new StringBuilder();
			sb.append("package "+package0+";\n\n");
			sb.append("public class "+fileName+"{\n\n");
			sb.append("\t//TODO \n\n");
			sb.append("}\n");
			output(webPath, sb);
		}
	}

	private void daoBox(String perfix, String daos) {
		String[] dao = daos.split(",");
		for (String d : dao) {
			logger.debug("d="+d);
			if(d.contains(".")){
				//包含点的情况：
				//aa.bb.cc在bb生成接口，在cc生成实现类
				logger.debug("d>> contains dot :"+d);
				daoContainsDot(perfix, d);
			}else{
				//不包含点的情况
				//mapper  仅生成接口不生成实现类
				logger.debug("d>> not contains dot :"+d);
				daoNotContainsDot(perfix, d);
			}
		}
	}

	private void daoNotContainsDot(String perfix, String d) {
		String dPath = (perfix+"."+d).replaceAll("\\.", "/");
		String interfaceSuffixPerfix = captureName(d);	//default
		for (String file : names) {
			String interfaceName = captureName(file)+interfaceSuffixPerfix;
			String interfacePath = dPath+"/"+interfaceName+".java";
			StringBuilder sb = new StringBuilder();
			sb.append("package "+perfix+"."+d+";\n\n");
			sb.append("public interface "+interfaceName+"{\n\n");
			sb.append("\t//TODO \n\n");
			sb.append("}\n");
			output(interfacePath, sb);
		}
	}

	private void daoContainsDot(String perfix, String d) {
		String suffix = d.substring(0,d.lastIndexOf('.'));
		logger.debug("d>>suffix = "+suffix);
		String package0 = (perfix+"." + suffix);
		String daoPath = package0.replaceAll("\\.", "/");
		logger.debug("daoPath="+daoPath);
		for (String file : names) {
			String fileName = daoPath + "/" 
					+ captureName(file) + captureName(suffix) + ".java";
			logger.debug("d:fileName="+fileName);
			String impl = d.substring(d.lastIndexOf(".")+1);
			logger.debug("d:impl="+impl);
			
			String implSuffix = captureName(suffix) + captureName(impl);
			logger.debug("implSuffix="+implSuffix);
			logger.debug("d >>> "+d);
			String implPath = (perfix+"." +d).replaceAll("\\.", "/");
			logger.debug("implPath="+implPath);
			String implFileName = implPath + "/" 
					+ captureName(file) + implSuffix + ".java";
			
			String interfaceName = captureName(file)+captureName(suffix);
			StringBuilder sb = new StringBuilder();
			sb.append("package "+package0+";\n\n");
			sb.append("public interface "+interfaceName+"{\n\n");
			sb.append("\t//TODO \n\n");
			sb.append("}\n");
			output(fileName, sb);
			
			StringBuilder sb2 = new StringBuilder();
			sb2.append("package "+perfix +"." +d+";\n\n");
			sb2.append("import "+package0+"."+interfaceName+";\n");
			sb2.append("\npublic class "+captureName(file)+implSuffix);
			sb2.append(" implements "+captureName(file)+captureName(suffix)+" {\n\n");
			sb2.append("\t//TODO \n\n");
			sb2.append("}\n");
			output(implFileName, sb2);
		}
	}

	private void serviceBox(String perfix, String services) {
		String[] service = services.split(",");
		for (String s : service) {
			logger.debug("s="+s);
			if(s.contains(".")){
				//包含点的情况：aa.bb.cc在bb生成接口，在cc生成实现类
				logger.debug("contains dot :"+s);
				Config config = config(perfix, s);
				for (String moduleName : names) {
					serviceHandler(perfix, s, config, moduleName);
				}
			}else{
				//不包含点的情况
				//abc  即生成接口又生成实现类
				logger.debug("not contains dot :"+s);
				Config config = new Config();
				config.setFileSuffix(s);
				for (String moduleName : names) {
					outputService2(perfix, s, config, moduleName);
				}
			}
		}
	}

	private void serviceHandler(String perfix, String s, Config config,
			String moduleName) {
		String interfaceName = captureName(moduleName)+captureName(config.getSuffix());
		String filePath = config.getServicePath() + "/" 
				+ interfaceName + ".java";
		config.setInterfaceName(interfaceName);
		config.setFilePath(filePath);
		outputService(perfix, s, config, moduleName);
	}
	private void outputService(String perfix, String s,Config config,String moduleName){
		String implFileName = config.getImplPath() + "/" 
				+ captureName(moduleName) + config.getImplSuffix() + ".java";
		StringBuilder sb = new StringBuilder();
		sb.append("package "+config.getPackage0()+";\n\n");
		sb.append("public interface "+config.getInterfaceName()+"{\n\n");
		sb.append("\t//TODO \n\n");
		sb.append("}\n");
		output(config.getFilePath(), sb);
		StringBuilder sb2 = new StringBuilder();
		sb2.append("package "+perfix +"." +s+";\n\n");
		sb2.append("import "+config.getPackage0()+"."+config.getInterfaceName()+";\n");
		sb2.append("\npublic class "+captureName(moduleName)+config.getImplSuffix());
		sb2.append(" implements "+captureName(moduleName)+captureName(config.getSuffix())+" {\n\n");
		sb2.append("\t//TODO \n\n");
		sb2.append("}\n");
		output(implFileName, sb2);
	}

//	private void webBox( String webs) {
	private void webBox(String perfix, String webs) {
		String[] web = webs.split(",");
		for (String w : web) {
			logger.debug("w="+w);
			String package0 = perfix+"."+w;
			logger.debug("web_package:"+package0);
			String path0 = package0.replaceAll("\\.", "/");
			logger.debug("path0="+path0);
			
			for (String file : names) {
				String suffix = path0.substring(path0.lastIndexOf('/')+1);
				String fileName = path0+"/"+captureName(file)+captureName(suffix)+".java";
				logger.debug("fileName="+fileName);
//							File f = new File("src/"+fileName);
				
				String name = captureName(file)+captureName(suffix);
				StringBuilder sb = new StringBuilder();
				sb.append("package "+package0+";\n\n");
				sb.append("public class "+name+"{\n\n");
				sb.append("\t//TODO \n\n");
				sb.append("}\n");
				output(fileName, sb);
			}
		}
	}
	
	//outputClass , outputInterface
	private void output(String path,StringBuilder sb){
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(
					new File("src/"+path)));
		} catch (FileNotFoundException e) {
		}
		try {
			bos.write(sb.toString().getBytes());
			bos.close();
		} catch (IOException e) {
		}
	}

	public final static String captureName(String name) {
		if (isLowwer(name) == true) {
			char[] cs = name.toCharArray();
			cs[0] -= 32;
			return String.valueOf(cs);
		} else {
			return name;
		}

	}
	public final static boolean isLowwer(String table) {
		byte[] b = table.getBytes();
		return (b[0] >= 97 && b[0] <= 122) == true ? true : false;
	}
	
	private static String wsd_pojo = null;
	
	public static void setWsdPojo(String pojo){
		if(pojo == null){
			throw new NullPointerException();
		}
		boolean pojoFlag = pojo
		 .matches("([a-zA-Z_][a-zA-Z0-9_]*[.])*([a-zA-Z_][a-zA-Z0-9_]*)$");
		if(!pojoFlag){
			throw new WsdException("包名不合法");
		}
		wsd_pojo = pojo;
	}
	public static String getWsdPojo(){
		return wsd_pojo;
	}
	
	private List<String> names = new ArrayList<String>();
	//通过包名获取该包下的所有java文件名，返回Set集合
	private List<String> getNamesOfPackageAndFileSuffix(String perfix,
			String suffix) {
		if(perfix==null || suffix == null){
			throw new NullPointerException();
		}
		perfix = perfix.replaceAll("\\.", "/");
		File file = new File("src/"+perfix);
		if(!file.exists()){
			throw new NoSuchElementException("目标:"+perfix+"不存在");
		}
		String[] list = file.list();
		for (String string : list) {
			int index = string.lastIndexOf('.');
			if(suffix.equalsIgnoreCase(string.substring(index+1))){
				names.add(string.substring(0,index).toLowerCase());
			}
		}
		return names;
	}
	
	public static void pgBuild(String wsd_url){
		PgBuilder pgBuilder = new PgBuilder();
		Wsd wsd = pgBuilder.resolver(wsd_url);
		pgBuilder.pgBuilder(wsd.combiner());
	}
	public static void pgBuild(String wsd_pojo,String wsd_url){
		boolean pojoFlag = wsd_pojo
		 .matches("([a-zA-Z_][a-zA-Z0-9_]*[.])*([a-zA-Z_][a-zA-Z0-9_]*)$");
		if(!pojoFlag){
			throw new WsdException("包名不合法："+wsd_pojo);
		}
		PgBuilder pgBuilder = new PgBuilder();
		List<String> names = pgBuilder.
			getNamesOfPackageAndFileSuffix(wsd_pojo,"java");
		Wsd wsd = pgBuilder.resolver(wsd_url);
		String perfix = wsd.getPerfix();
		for (String perfixAppend : names) {
			wsd.setPerfix(perfix+"."+perfixAppend);
			pgBuilder.pgBuilder(wsd.combiner());
		}
	}
}