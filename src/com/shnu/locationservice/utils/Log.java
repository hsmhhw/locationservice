package com.shnu.locationservice.utils;

import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;



/**
 * @ClassName: Log 
 * @Description: TODO
 * @author yanqi
 * @company PING AN
 * @date 2016年5月22日 下午3:10:18 
 */
public class Log {

	private Log(){
		//Utility class
	}
	
	private static String getTag(){
		/*获取当前方法堆栈，我们一般用
 		StackTraceElement[] stes = Thread.currentThread().getStackTrace();
		想要获取当前方法，切记不够灵活，使用数组stes的固定index
		 */
		/*返回一个堆栈轨迹元素的数组，代表了这个线程的堆栈情况。
	　	如果：1.这个线程没有被开启；
		2.这个线程被开启了但是没有被系统运行过（因为线程运行是需要根据一定规律轮换的）；
		3.这个线程结束了。
		 */
		StackTraceElement[] stackTraceElements = Thread.currentThread()
				.getStackTrace();
		String fullClassName = stackTraceElements[4].getClassName();
		String className = fullClassName.substring(fullClassName
				.lastIndexOf(".") + 1);//lastIndexOf()方法返回从右向左出现某个字符或字符串的首个字符索引值
										/*var src="images/off_1.png";
										alert(src.lastIndexOf('/'));
										alert(src.lastIndexOf('g'));
										弹出值依次为：6,15 
										*/
		int lineNumber = stackTraceElements[4].getLineNumber();
		return "TAG" + className + ":" + lineNumber ;
	}
	
	private static void toast(Context context, String message){
		Toast.makeText(context, message, 0).show();
	}
	
	public static void v (String message){
		android.util.Log.v(getTag(), message);
	}
	
	public static void v(String message, Throwable e){//Throwable 类是 Java 语言中所有错误或异常的超类。只有当对象是此类（或其子类之一）的实例时，才能通过 Java 虚拟机或者 Java throw 语句抛出。类似地，只有此类或其子类之一才可以是 catch 子句中的参数类型。
		android.util.Log.v(getTag(), message, e);
	}
	
	public static void v(Context context, String message){
		toast(context, message);
		android.util.Log.v(getTag(), message);
	}
	
	public static void v(Context context, String message, Throwable e){
		toast(context, message);
		android.util.Log.v(getTag(), message, e);
	}
	
	public static void e(Throwable e){
		android.util.Log.e(getTag(), "Error", e);
	}
	
	public static void e(String message){
		android.util.Log.e(getTag(), message);
	}
	
	public static void e(String message, Throwable e){
		android.util.Log.e(getTag(), message, e);
	}
	
	public static void e(Context context, String message){
		toast(context, message);
		android.util.Log.e(getTag(), message);
	}
	public static void e(Context context, String message, Throwable e){
		toast(context, message);
		android.util.Log.e(getTag(), message, e);
	} 
	
	public static void w(Throwable e){
		android.util.Log.w(getTag(), "warning", e);
	}
	
	public static void w(String message){
		android.util.Log.w(getTag(), message);
	}
	
	public static void w(String message, Throwable e){
		android.util.Log.w(getTag(), e);
	}
	
	public static void w(Context context,String message){
		toast(context, message);
		android.util.Log.w(getTag(), message);
	}
	
	public static void w(Context context, String message, Throwable e){
		toast(context, message);
		android.util.Log.w(getTag(), message, e);
	}
	
	public static void d(String message){
		android.util.Log.d(getTag(), message);
	}
	
	public static void d(String message, Throwable e){
		android.util.Log.d(getTag(), message, e);
	}
	
	public static void d(Context context, String message){
		android.util.Log.d(getTag(), message);
	}
	
	public static void d(Context context, String message,Throwable e){
		toast(context, message);
		android.util.Log.d(getTag(), message, e);
	}
	
	public static void i(String message){
		android.util.Log.i(getTag(), message);
	}
	
	public static void i(String message, Throwable e){
		android.util.Log.i(getTag(), message, e);
	}
	
	public static void i(Context context, String message){
		toast(context, message);
		android.util.Log.i(getTag(), message);
	}
	
	public static void i(Context context, String message, Throwable e){
		toast(context, message);
		android.util.Log.i(getTag(), message, e);
	}
	

	/**
	 * 
	 * @Title: 
	 * @Description:开始log记录，线程操作 
	 * @param shell 命令 ：| findstr
	 * @return 
	 * @throws
	 */
	static Thread LogThread = null;
	public static void startLog(final String tag){
		LogThread = new Thread(new Runnable() {
			
			@Override
			public void run() {

				String shell = "logcat -v time";
				if(tag != null && !"".equals(tag)){
					shell += " | findstr " + tag;
				}
				try {
					Process process =Runtime.getRuntime().exec(shell);
					InputStream inputStream = process.getInputStream();
					//Android中判断SD卡是否存在，并且可以进行写操作
					boolean sdCardExist = Environment.getExternalStorageState()
							.equals(Environment.MEDIA_MOUNTED);
					File dir = null;
					if(sdCardExist){
						dir = new File(Environment.getExternalStorageDirectory().toString()//获取SDCard目录,来获取sdcard路径
										+ File.separator + "log.txt");
						if(!dir.exists()){
							dir.createNewFile();
						}
					}
					byte[] buffer = new byte[1024];
					int bytesLeft = 5 * 1024 * 1024 ;
					try {
						FileOutputStream fos = new FileOutputStream(dir);
						try {
							while (bytesLeft > 0) {
								int read = inputStream.read(buffer, 0, 
										Math.min(bytesLeft, buffer.length));//java.lang.Math.min(int a, int b) 返回两个int值中较小的一个。
								if(read == -1){
									throw new EOFException(
											"Unexpected end of data");
								}
								fos.write(buffer, 0, read);
								bytesLeft -= read;
							}
						} finally {
							fos.close();
						}
					} finally{
						inputStream.close();
					}
					android.util.Log.d("", "LOGCAT = ok");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		LogThread.start();
	}
	
	/**
	 * 
	* @Title: stopLog()
	* @Description: 停止线程记录
	* @param 
	* @return 
	* @throws
	 */
	public static void stopLog(){
		if (LogThread != null && LogThread.isAlive()) {//线程中调用isAlive()方法，它的一般形式如下：final boolean isAlive()；如果调用他的线程仍在运行，返回true，否则，返回false；
			LogThread.interrupt();//原方法：LogThread.stop();
			LogThread = null;
		}
	}
}













