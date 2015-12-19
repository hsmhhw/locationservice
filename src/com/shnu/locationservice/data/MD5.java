package com.shnu.locationservice.data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @company 上海师范大学
 * @author 郝彦淇
 * @time 2015-10-10
 * @method MD5
 */

//MD5 算法
public class MD5 {
	// 定义全局数组
	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5", 
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
	
	
	public MD5(){
		
	}
	//返回值形式为数字跟字符串
	private static String byteToArrayString(byte bByte){
		int iRet = bByte;
		
		if(iRet < 0){
			iRet += 256;
		}
		int iD1 = iRet / 16 ;
		int iD2 = iRet % 16 ;
		return strDigits[iD1] + strDigits[iD2];
		
	}
	//转化字节数组为16进制字符串
	private static String byteToString(byte[] bByte){
		StringBuffer sBuffer = new StringBuffer();
		for(int i = 0 ; i < bByte.length ; i++ ){
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
		
	}
	
	public static String GetMD5Code(String strObj){
		String resultString = null ;
		resultString = new String(strObj);
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
		
			//md.digest() 该函数返回值为存放哈希值结果的byte数组
			resultString = byteToString(md.digest(strObj.getBytes()));
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		//
		return resultString;
		
	}
	

	
}
