/*
 * Wsd.java 18/9/30
 */
package gla.pgbuilder.test;

import java.util.HashSet;
import java.util.Set;

/**
 * W(web)S(Service)D(Dao)
 * 
 * @author Gladiateur
 */
public class Wsd{
	private String perfix = null;
	//web中可能不止一个：w:controller,action
	//service也是：s:service.impl,ebi.ebo
	//dao也是：mapper,dao.impl
	private String web = null;
	private String service = null ;
	private String dao = null;
	
	public String getPerfix() {
		return perfix;
	}
	public void setPerfix(String perfix) {
		this.perfix = perfix;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		if(!check(web)){
			throw new RuntimeException("w:格式非法");
		}
		this.web = web;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		if(!check(service)){
			throw new RuntimeException("s:格式非法");
		}
		this.service = service;
	}
	public String getDao() {
		return dao;
	}
	public void setDao(String dao) {
		if(!check(dao)){
			throw new RuntimeException("d:格式非法");
		}
		this.dao = dao;
	}

	//组合wsd_body
	//返回相对路径集合
	public Set<String> combiner(){
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