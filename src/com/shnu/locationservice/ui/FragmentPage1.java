package com.shnu.locationservice.ui;

import java.util.List;

import com.shnu.locationservice.R;
import com.shnu.locationservice.data.Uri;
import com.shnu.locationservice.utils.Log;

import android.R.color;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 * @ClassName: FragmentPage1 
 * @Description: TODO
 * @author yanqi
 * @company PING AN
 * @date 2016年5月22日 下午3:11:45 
 */
public class FragmentPage1 extends Fragment {
	private Button bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt10, bt11, bt12, bt13, bt14, bt15, bt16;
	private String w1, w2, w3, w4, w5, w6, w7, w8, w9, w10, w11, w12, w13, w14, w15, w16;
	private int size1 = Uri.width / 24 ;
	private int size2 = Uri.height / 33 ;
	
	private ViewPager viewpager; //android-support-v4中的滑动组件
	private List<ImageView> imageViews; //滑动的图片集合

	private int[] imageResId; //图片ID
	private List<View> dots; //图片标题的那些点
	
	@SuppressWarnings("unused")//  作用：用于抑制编译器产生警告信息。unused	to suppress warnings relative to unused code
	private int currentItem = 0;


	@Override //@Override是Java5的元数据，自动加上去的一个标志，告诉你说下面这个方法是从父类/接口 继承过来的，需要你重写一次，这样就可以方便你阅读，也不怕会忘记

	public void onDestroyView() {
		super.onDestroyView();
		Log.i("onDestory..");
	}
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.i("onCreteView");
		View v = inflater.inflate(R.layout.fragment_1, container, false);//R.layou.fragement_1不能加载删除import.Android.R;引入项目R文件。
		
		bt1 = (Button) v.findViewById(R.id.button1);
		w1 = getResources().getString(R.string.b101);
		Log.i("w1 == "+ w1);
		Spannable wordtoSpan1 = new SpannableString(w1);
		wordtoSpan1.setSpan(new AbsoluteSizeSpan(size1), 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		wordtoSpan1.setSpan(new AbsoluteSizeSpan(size2), 2, 13, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		wordtoSpan1.setSpan(new StyleSpan(Typeface.BOLD), 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		wordtoSpan1.setSpan(new ForegroundColorSpan(Color.DKGRAY), 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		wordtoSpan1.setSpan(new ForegroundColorSpan(Color.GRAY), 2, 13, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		Log.i("wordSpan1== "+ wordtoSpan1);
		bt1.setText(wordtoSpan1);
		bt1.setOnClickListener(btlistener);
		
		
		bt2 = (Button) v.findViewById(R.id.button2);
		w2 = getResources().getString(R.string.b102);
		Spannable wordtoSpan2 = new SpannableString(w2);
		wordtoSpan2.setSpan(new AbsoluteSizeSpan(size1), 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		wordtoSpan2.setSpan(new AbsoluteSizeSpan(size2), 2, 16, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		wordtoSpan2.setSpan(new StyleSpan(Typeface.BOLD), 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		wordtoSpan2.setSpan(new ForegroundColorSpan(Color.DKGRAY), 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		wordtoSpan2.setSpan(new ForegroundColorSpan(Color.GRAY), 2, 16, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		bt2.setText(wordtoSpan2);
		bt2.setOnClickListener(btlistener);
		
		
		bt3 = (Button) v.findViewById(R.id.button3);
		w3 = getResources().getString(R.string.b103);
		Spannable wordtoSpan3 = new SpannableString(w3);
		wordtoSpan3.setSpan(new AbsoluteSizeSpan(size1), 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		wordtoSpan3.setSpan(new AbsoluteSizeSpan(size2), 2, 11, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		wordtoSpan3.setSpan(new StyleSpan(Typeface.BOLD), 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		wordtoSpan3.setSpan(new ForegroundColorSpan(Color.DKGRAY), 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		wordtoSpan3.setSpan(new ForegroundColorSpan(Color.GRAY), 2, 11, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		bt3.setText(wordtoSpan3);
		bt3.setOnClickListener(btlistener);
		
		
	
		
		
		
		return v;
	}
	/**
	 * @method 按键监听事件
	 * @author yanqi
	 */
	 private OnClickListener btlistener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	};
}
