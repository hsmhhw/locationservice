package com.shnu.locationservice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class About_beta extends Activity {
	ImageView image;
	TextView tv, tv4;
	Button bt1, bt2, bt3;
	String version;
	Bitmap bitmap;
	boolean if_success = true, if_click = false;
	private int DownedFileLength = 0, Length;
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0x123) {
				tv4.setVisibility(View.VISIBLE);
			}
			if (msg.what == 0124) {
				if (if_success) {
					Toast.makeText(getApplication(), "下载完成", Toast.LENGTH_LONG).show();
					openFiles();
				}else{
					Toast.makeText(getApplication(), "下载失败",Toast.LENGTH_LONG).show();
				}
				tv4.setVisibility(View.GONE);
				if_click = false ;
			}
			if(msg.what == 0x125){
				image.setVisibility(View.GONE);
				Toast.makeText(getApplication(), "你的APK已经是最新版本",
						Toast.LENGTH_LONG).show();
				if_click = false;
			}
			if(msg.what == 0x126){
				image.setVisibility(View.GONE);
				new AlertDialog.Builder(About_beta.this)
				.setTitle("locationservice更新")//设置对话框标题
				.setMessage("服务器有更新版本  /n 第一版发布  /n 功能完善中")//设置显示内容
				.setPositiveButton("更新", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {

						MyTask mytask = new MyTask();
						mytask.execute();
					}
				})
				.setNegativeButton("取消",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {

						if_click = false;
					}
				}).show();
			}
			if(msg.what == 0x127){
				image.setVisibility(View.GONE);
				//Toast.makeText();
			}
		}

		private void openFiles() {

			final String fileName = "locationservice.apk";
			File file = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath()+ "/" + fileName);
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setAction(android.content.Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file) 
					, "application/vnd.android.package-archive");
			startActivity(intent);
			
		};
	};

	private class MyTask extends AsyncTask<String, Integer, String>{

		
		@Override
		protected String doInBackground(String... arg0) {
			final String fileName = "locationservice" ;
			File tmpFile = new File("/sdcard/update");
			if(!tmpFile.exists()){
				tmpFile.mkdir();//创建文件夹，如果没有
			}
			File file = new File("/" + fileName);
			boolean sdCardExist = Environment.getExternalStorageState()
					.equals(android.os.Environment.MEDIA_MOUNTED);//判断SD卡是否存在
			if(sdCardExist){
				file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() 
						+ "/" + fileName);
				/* 
				 * getExternalStorageDirectory()
				 *  获取到外部存储的目录 一般指SDcard（/storage/sdcard0）
				 *  getAbsolutePath():返回抽象路径名的绝对路径名字符串。
				 *   确认sdcard的存在
　　				 * android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)
　　				 *  获取扩展存储设备的文件目录
　　				 * android.os.Environment.getExternalStorageDirectory();
				 */
			}
			if(file.exists()){
				file.delete();
			}
			try {
				URL url = new URL("http://225.");
				try {
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					 // 此处的urlConnection对象实际上是根据URL的   
			          // 请求协议(此处是http)生成的URLConnection类   
			          // 的子类HttpURLConnection,故此处最好将其转化   
			          // 为HttpURLConnection类型的对象,以便用到   
					InputStream is = conn.getInputStream(); // <===注意，实际发送请求的代码段就在这里
					// 调用HttpURLConnection连接对象的getInputStream()函数,   
					// 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。
					
					FileOutputStream fileOutput = new FileOutputStream(file);//写数据
					
					byte[] buf = new byte[1024];//分配byte
					Length = conn.getContentLength();
					conn.connect();
					int numRead = 0 ;
					if(conn.getResponseCode() >= 4000){
						Toast.makeText(About_beta.this, "连接超时", Toast.LENGTH_LONG).show();
					}else{
						while((numRead = is.read(buf)) != -1){
							if(numRead <= 0){
								Log.v("numRead_error", "" +numRead);
							}else{
								fileOutput.write(buf,0,numRead);
								DownedFileLength+=numRead;
								Log.v("22222", "" +DownedFileLength);
								handler.sendEmptyMessage(0x128);
							}
						}
					}
					conn.disconnect();//关闭连接
					fileOutput.close();//关闭文件流
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
					if_success = false;
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
				if_success = false;
			}
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			handler.sendEmptyMessage(0x124);
		}
		@Override
		protected void onPreExecute() {
			handler.sendEmptyMessage(0x123);
		}
	}
}
