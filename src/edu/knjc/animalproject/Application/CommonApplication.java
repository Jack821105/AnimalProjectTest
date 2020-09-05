package edu.knjc.animalproject.Application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.knjc.animalproject.model.JosnData;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class CommonApplication extends Application {
	private static List<JosnData> public_Json_DataInfo;
	public static String DataKey = "KNJC_KEY";

	public static List<JosnData> getPublic_Json_DataInfo(Context context) {
		SharedPreferences prefs = context.getApplicationContext().getSharedPreferences("GsonData",context.getApplicationContext().MODE_PRIVATE);
		Gson gson = new Gson();
		
		try {
			public_Json_DataInfo = gson.fromJson(prefs.getString("GGSONDATA", null),new TypeToken<List<JosnData>>() {}.getType());  
		} catch (Exception e) {
			return null;
		}
		
		return public_Json_DataInfo;
	}

	public static long getNowDataTime() {
		// 抓現在日期
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String timestamp = sDateFormat.format(new java.util.Date());

		// 將現在日期字串轉成Date型
		try {
			Date dtNow = sDateFormat.parse(timestamp);
			// System.out.println("手機現在日期毫秒  = " + dtNow.getTime());
			return dtNow.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return (Long) null;
		}
	}

	public static boolean checkCompareTime(long nowTime, long CompareTime) {

		Date dtNow = new Date(nowTime);
		Date dtTime = new Date(CompareTime);

		if (dtNow.getTime() > dtTime.getTime()) {
			return true;
		} else {
			return false;
		}

	}
	
	public static long getRestrictionDataTime(){
		//抓現在日期
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date dataTime = new java.util.Date();
		dataTime.setHours(dataTime.getHours()+6); //正式
		String timestamp = sDateFormat.format(dataTime);
		
		//將現在日期字串轉成Date型
		try {
			Date dtNow = sDateFormat.parse(timestamp);
//			System.out.println("手機現在日期毫秒  = " + dtNow.getTime());
			return dtNow.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return (Long) null;
		}
	}
}
