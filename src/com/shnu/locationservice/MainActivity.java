package com.shnu.locationservice;

import com.shnu.locationservice.mms.Change_password;
import com.shnu.locationservice.mms.Subject;
import com.shnu.locationservice.ui.MainTabActivity;
import com.shnu.locationservice.data.MD5;
import com.shnu.locationservice.data.Uri;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

 



/**
 * @company 上海师范大学
 * @author 郝彦淇
 * @class MainActivity
 * @time 2015-10-10
 * ALT + SHIFT +J 生成快捷键
 *
 */
public class MainActivity extends Activity {
	String name = "";
	String Type = "out";
	String pass = "";
	String ifsuccess = "false";
	private Button out , in , admin ,subject; //外网用户 内网用户 登陆 注册  按钮申明
	private EditText username , password; //用户名 密码   编辑框填写申明
	CheckBox remeber; //记住密码复选框
	TextView find ;  //找回密码文本按键
	LinearLayout linear ; //文本框线性布局

	//
	Handler handler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 0x123){
				Toast.makeText(MainActivity.this, ifsuccess, Toast.LENGTH_LONG).show();
			
				if(remeber.isChecked()){
					Save(name , pass);
				}
				Uri.name = name ;
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, MainTabActivity.class);
				startActivity(intent);
				MainActivity.this.finish();
			}
			if(msg.what == 0x124){
				Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_LONG).show();
				
			}
			if(msg.what == 0x125){
				Uri.name = name;
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, MainTabActivity.class);
				
			}
			
		}

	} ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		out = (Button) findViewById(R.id.button_out);
		in = (Button) findViewById(R.id.button_in);
		admin = (Button) findViewById(R.id.main_button_admin);
		subject = (Button) findViewById(R.id.main_button_subject); 
		remeber = (CheckBox) findViewById(R.id.main_checkbox1);
		linear = (LinearLayout) findViewById(R.id.LinearLayout);
		
		find = (TextView) findViewById(R.id.find);
		find.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);  //下划线
		find.getPaint().setAntiAlias(true); //抗锯齿
		find.setOnClickListener(new OnClickListener() {
			//找回密码按键监听事件
			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent();
				intent.setClass(MainActivity.this, Change_password.class);
				startActivity(intent);
				
			}
		});
		
		
		//用户名密码输入实例化
		username = (EditText) findViewById(R.id.main_editText1);
		password = (EditText) findViewById(R.id.main_editText2);
		
		//DisplayMetrics 类提供了一种关于显示的通用信息，如显示大小，分辨率和字体
	
		DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Uri.width = dm.widthPixels;
        Uri.height = dm.heightPixels;
        
        SharedPreferences sp = getSharedPreferences("data", Context.MODE_PRIVATE);
        name = sp.getString("name", "");
        pass = sp.getString("password","");
        
        if(name.length() > 0 && pass.length() > 0){
        	handler.sendEmptyMessage(0x125);
        }
		
        out.setOnClickListener(new OnClickListener() {
        	@SuppressWarnings("deprection")
			@Override
			public void onClick(View arg0) {
				in.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.in_btn));
				out.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.out_btn));
				
				Toast.makeText(MainActivity.this, "当前是外网登陆", Toast.LENGTH_LONG).show();
			}
		});
        in.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("deprection")
			@Override
			public void onClick(View arg0) {
				in.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.in_btn));
				out.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.out_btn));
				Toast.makeText(MainActivity.this, "待定义", Toast.LENGTH_LONG).show();
			}
		});
        admin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				name = username.getText().toString();
				pass = password.getText().toString();
				
				new Thread(){
					@Override
					public void run(){
						pass = MD5.GetMD5Code(pass);
						ifsuccess = GetPostUtil.sendPost(Uri.login, "name=" + name + "& password=" + pass  );
						Log.v("11111", ifsuccess);
						if("登陆成功".equals(ifsuccess)){
							handler.sendEmptyMessage(0x123);
						}else{
							handler.sendEmptyMessage(0x124);
						}
					}
				}.start();
			}
		});
        subject.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(MainActivity.this, Subject.class);
				startActivity(intent);
			}
		});
	}
	public void Save(String s1, String s2) {

		SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("name", s1);
		editor.putString("password", s2);
		editor.commit();
	}

	
}
