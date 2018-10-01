package gla.pgbuilder.test;

public class Test6 {
	//web中可能不止一个：w:controller,action
	//service也是：s:service.impl,ebi.ebo
	//dao也是：mapper,dao.impl
	//测试多个包名时的情况
	public static void main(String[] args) {
		boolean rgFlag = "controller.impl,,service".trim().
		 matches("([a-zA-Z_][a-zA-Z0-9_]*[.|,])*([a-zA-Z_][a-zA-Z0-9_]*)$");
		System.out.println(rgFlag);
	}
}
