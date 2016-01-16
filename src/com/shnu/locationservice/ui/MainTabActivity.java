package com.shnu.locationservice.ui;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Random;

import com.shnu.locationservice.About_beta;
import com.shnu.locationservice.GetPostUtil;
import com.shnu.locationservice.R;
import com.shnu.locationservice.data.SendDate;
import com.shnu.locationservice.data.Uri;
import com.shnu.locationservice.services.RecordService;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract.Colors;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author 郝彥淇
 * @company 上海師範大學
 * @date 2015-10-10
 * @function 自定義TabHost
 *
 */
public class MainTabActivity extends FragmentActivity {
	// 定义FragmentTabHost对象
	private FragmentTabHost mTabHost;
	String version;
	public static MainTabActivity instance = null;

	// 定义一个布局
	private LayoutInflater layoutInflater;
	Handler handler  = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			
			if(msg.what == 0x123){
				new AlertDialog.Builder(MainTabActivity.this).setTitle("软件更新")//设置对话框标题
				.setMessage("服务器有新版本")//设置显示内容
				.setPositiveButton("更新", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {//确定按钮响应事件
						Intent i = new Intent();
						i.setClass(MainTabActivity.this, About_beta.class);
						SendDate.if_down = true;
						startActivity(i);
					}
				})
				.setNegativeButton("取消",new DialogInterface.OnClickListener() {//添加返回事件
					
					@Override
					public void onClick(DialogInterface dialog , int which) {//按钮事件
						// TODO Auto-generated method stub
						
					}
				}).show();
					
			
				
			}
			if(msg.what == 0x124){

				Toast.makeText(getApplication(),"连接失败", Toast.LENGTH_LONG).show();
			}
		}
	};
	String record = "" ;
	String file = "" ;
	String fileName = "" ;	
	//定义数组来存放Fragment界面
	@SuppressWarnings("rawtypes")
	private Class fragmentArray[] = {FragmentPage1.class , FragmentPage2.class , FragmentPage3.class , FragmentPage4.class};
	
	//定义数组来存放按钮图片
	private int mImageViewArray[] = {R.drawable.tab_home_btn, R.drawable.tab_message_btn, R.drawable.tab_selfinfo_btn , 
									R.drawable.tab_square_btn , R.drawable.tab_more_btn};
	//tab选项卡的文字
	private String mTextviewArrary[] = {"首页", "动态" , "关于我们" , "其他"};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_tab_layout);
		instance = this ; //和tabmainActivity有关
		initView();
		PackageManager manager ;
		PackageInfo info = null ;
		manager = this.getPackageManager();
		try {//在surround with添加
			info = manager.getPackageInfo(this.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		version = "" + info.versionName ;
		new Thread(){
			public void run() {
				String ifsuccess = GetPostUtil.sendPost("http://115.28.110.225:8080/location/update_apk.jsp", "version =" + version);
				Log.v("11111", ifsuccess);
				if("yes".equals(ifsuccess)){
					handler.sendEmptyMessage(0x123);
				}else if("no".equals(ifsuccess)){
					
				}else{
					handler.sendEmptyMessage(0x124);
				}
			};
		}.start();
		if(!Uri.start_service){
			Log.v("1111", "111111");
			final Intent ootStartIntent = new Intent(this , RecordService.class);
			ootStartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//把service放到一个新的task中启动。
			startService(ootStartIntent);
			Uri.start_service = true ;
		}
		
	}

	/*
	 * 初始化组件
	 * */
	private void initView() {
		//实例化布局对象
		layoutInflater = LayoutInflater.from(this);
		
		//实例化Tabhost对象，得到Tabhost
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this , getSupportFragmentManager() , R.id.realtabcontent);
		
		//得到fragment的个数
		int count = fragmentArray.length ;
		for(int i = 0 ; i < count ; i++){
			//为每个tab按钮设置图报，文字和内容
			TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArrary[i]).setIndicator(getTabItemView(i));
			//将tab按钮添加到tab选项卡中
			mTabHost.addTab(tabSpec , fragmentArray[i] , null);
			//设置Tab按钮的背景
			mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
		}
		
	}

	/*
	 * 给Tab按钮设置图标和文字
	 * */
	private View getTabItemView(int index) {
		View view = layoutInflater.inflate(R.layout.tab_item_view,null);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(mImageViewArray[index]);
		
		TextView textView = (TextView) view.findViewById(R.id.textview);
		textView.setText(mImageViewArray[index]);
		textView.setTextColor(Color.DKGRAY);
		return view;
	}
	//将字符串写入文本文件中
	public void writeTxtoFile(String strcontent , String filePath , String fileName ){
		//生成文件夹之后，再生成文件，不然会出错
		makeFilePath(filePath , fileName);
		String strFilePath = filePath + fileName ; 
		String strContent  = strcontent + "\r\n";
		try {
			File file = new File(strFilePath);
			if(!file.exists()){
				Log.d("TestFile", "Create the file : " + strFilePath);
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			RandomAccessFile raf = new RandomAccessFile(file, "rwd");
			raf.seek(file.length());
			raf.write(strContent.getBytes());
			raf.close();
		} catch (Exception e) {
			Log.e("TestFile", "Error on write File : " + e);
		}
	}

	//生成文件
	public File makeFilePath(String filePath, String fileName) {
		File file = null ;
		makeRootDirectory(filePath);
		try {
			file = new File(filePath + fileName);
			if(!file.exists()){
				file.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	public static void makeRootDirectory(String filePath) {

		File file = null;
		try {
			file = new File(filePath);
			if(!file.exists()){
				file.mkdir();
			}
		} catch (Exception e) {
			Log.i("error", e + "");
		}
		
	}

}
