package com.shnu.locationservice.data;

/*URI 是从虚拟根路径开始的
URL是整个链接
如URL http://zhidao.baidu.com/question/68016373.html  
URI 是/question/68016373.html在baidu那边服务器上把http://zhidao.baidu.com/制作成了虚拟的路径的根*/


public class Uri {
	//定义Uri总地址
	private static String url = "http://localhost:8080/Beta/";
	
	//定义JSP接口地址
	public static String login = url + "login.jsp";
	
	//定义字符类
	public static String problem = "";
	public static String data = "";
	public static String time = "";
	public static String name = "";
	
	
	public static int width = 30;
	public static int height = 100;
	public static int what;
	public static int if_mms = 0;
	
	

}
