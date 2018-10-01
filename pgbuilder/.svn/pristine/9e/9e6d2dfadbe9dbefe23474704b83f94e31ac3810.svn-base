/*
 * Wsd.java 18/9/30
 */
package gla.pgbuilder.builder;

import gla.pgbuilder.exception.WsdException;

import java.util.HashSet;
import java.util.Set;

/**
 * W(web)S(Service)D(Dao)
 * 
 * @author Gladiateur
 */
public class Wsd{
	private String perfix = null;
	private String web = null;
	private String service = null ;
	private String dao = null;
	
	String getPerfix() {
		return perfix;
	}
	void setPerfix(String perfix) {
		this.perfix = perfix;
	}
	String getWeb() {
		return web;
	}
	void setWeb(String web) {
		if(!check(web)){
			throw new WsdException("w:格式非法");
		}
		this.web = web;
	}
	String getService() {
		return service;
	}
	void setService(String service) {
		if(!check(service)){
			throw new WsdException("s:格式非法");
		}
		this.service = service;
	}
	String getDao() {
		return dao;
	}
	void setDao(String dao) {
		if(!check(dao)){
			throw new WsdException("d:格式非法");
		}
		this.dao = dao;
	}

	//返回相对路径集合
	Set<String> combiner(){
		Set<String> set = new HashSet<String>();
		if(web != null){
			String[] webs = web.split(",");
			for (String string : webs) {
				set.add((perfix+"."+string).replaceAll("\\.", "/"));
			}
		}
		if(service != null){
			String[] services = service.split(",");
			for (String string : services) {
				set.add((perfix+"."+string).replaceAll("\\.", "/"));
			}
		}
		if(dao != null){
			String[] daos = dao.split(",");
			for (String string : daos) {
				set.add((perfix+"."+string).replaceAll("\\.", "/"));
			}
		}
		return set;
	}
	
	@Override
	public String toString() {
		return "Wsd [perfix=" + perfix + ", web=" + web + ", service="
				+ service + ", dao=" + dao + "]";
	}
	
	private static boolean check(String param){
		return param.trim().
		matches("([a-zA-Z_][a-zA-Z0-9_]*[.|,])*([a-zA-Z_][a-zA-Z0-9_]*)$");
	}
}