package edu.knjc.animalproject.inquiry;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import one.customutil.CustomAsyncTaskGson;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import edu.knjc.animalproject.MainActivity;
import edu.knjc.animalproject.R;
import edu.knjc.animalproject.Application.CommonApplication;
import edu.knjc.animalproject.Classification.Classification;
import edu.knjc.animalproject.Classification.Classification2;
import edu.knjc.animalproject.email.PhotoEmail;
import edu.knjc.animalproject.information.InformationActivity;
import edu.knjc.animalproject.model.JosnData;

public class InquiryActivity extends Activity {
	/*************************************************************************
	 * 常數數宣告區
	 *************************************************************************/

	/*************************************************************************
	 * 變數數宣告區
	 *************************************************************************/
	private String Userbreed;
	private String Userbody;
	private String Usercolour;
	private String Usergender;
	private String Userage;
	/**************************** View元件變數 *********************************/
	private Button btn_home;
	private Button btn_back;
	private Button btn_information;
	private Button btn_Classification;
	private Button btn_inquiry;
	private Button btn_photo;
	private ProgressDialog mDialog;
	// private Spinner sp_variety;
	private Button btn_yes;
	private Spinner sp_breed;
	private Spinner sp_body;
	private Spinner sp_colour;
	private Spinner sp_age;
	// private String[] mAllSpinnerArray_age;
	private Spinner sp_gender;
	// private String[] mAllSpinnerArray_gender;
	private TextView tv_title;
	
	/**************************** Adapter元件變數 ******************************/

	/**************************** Array元件變數 ********************************/

	/**************************** 資料集合變數 *********************************/

	/**************************** 物件變數 *************************************/
	private List<String> breedList;
	private List<String> bodyList;
	private List<String> colourList;
	private List<String> genderList;
	private List<String> ageList;
	/*************************************************************************
	 * Override 函式宣告 (覆寫)
	 *************************************************************************/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inquiry2);

		findViews();
		/* 初始化資料,包含從其他Activity傳來的Bundle資料 ,Preference資枓 */
		initData();
		/* 設置必要的系統服務元件如: Services、BroadcastReceiver */
		setSystemServices();
		/* 設置View元件對應的linstener事件,讓UI可以與用戶產生互動 */
		setLinstener();

	}

	@Override
	protected void onStart() {
		/* 查尋資料庫或是ContentProvider,取得所需資料 */
		queryDataBase();
		/* 設定各式Adapter元件,將UI與資料做整合程現 */
		setAdapter();
		super.onStart();
	}

	@Override
	protected void onResume() {
		/* 依據初始化得到的資料,若有些View元件需要重新繪制或填上預設資料,則在此處理 */
		renderUI();
		super.onResume();
	}

	@Override
	protected void onRestart() {
		// 處理於onStop中回收的資料,在此處需要重新還原
		restoreObject();
		super.onRestart();
	}

	@Override
	protected void onStop() {
		/* 做必要的資料儲存,如存放在SharePreference或是SQL DataBase */
		saveData();
		/* 釋放DataBase相關的物件,如Cursor */
		releaseObject();
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/*************************************************************************
	 * 自訂API函式宣告
	 *************************************************************************/

	/**
	 * 取得View元件記憶體位置
	 */
	private void findViews() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		btn_home = (Button) findViewById(R.id.btn_home);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_inquiry = (Button) findViewById(R.id.btn_inquiry);
		btn_information = (Button) findViewById(R.id.btn_information);
		btn_Classification = (Button) findViewById(R.id.btn_classification);
		btn_photo=(Button) findViewById(R.id.btn_photo);
		// sp_variety=(Spinner) findViewById(R.id.sp_variety);
		btn_yes =(Button) findViewById(R.id.btn_yes);
		sp_breed = (Spinner) findViewById(R.id.sp_breed);
		sp_body = (Spinner) findViewById(R.id.sp_body);
		sp_colour = (Spinner) findViewById(R.id.sp_colour);
		sp_age = (Spinner) findViewById(R.id.sp_age);
		sp_gender = (Spinner) findViewById(R.id.sp_gender);

	}

	/**
	 * 初始化物件資料
	 */
	private void initData() {
		btn_home.setVisibility(View.INVISIBLE);
		// btn_back.setText("");
		tv_title.setText(getResources().getString(R.string.inquiry));

//		downloadJsonData();
		if(CommonApplication.getPublic_Json_DataInfo(getApplicationContext())!=null){
			setData(CommonApplication.getPublic_Json_DataInfo(getApplicationContext()));
		}else{
			Toast.makeText(InquiryActivity.this, getResources().getString(R.string.an_error_occurred), Toast.LENGTH_SHORT).show();
		}
		
		// 先看懂我的Code 剩下兩個列表請自行參考我寫的 自行完成 加油!!!!!!

		// mAllSpinnerArray_age =new String []{"1","2","3","4","5"};
		// ArrayAdapter<String> mAllspinnerAdapter_age=new
		// ArrayAdapter<String>(this,
		// android.R.layout.simple_spinner_item,mAllSpinnerArray_age);
		// mAllspinnerAdapter_age.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// sp_age.setAdapter(mAllspinnerAdapter_age);
		//
		//
		// mAllSpinnerArray_gender =new String []{"雄性","雌性"};
		// ArrayAdapter<String> mAllspinnerAdapter_gender=new
		// ArrayAdapter<String>(this,
		// android.R.layout.simple_spinner_item,mAllSpinnerArray_gender);
		// mAllspinnerAdapter_gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// sp_gender.setAdapter(mAllspinnerAdapter_gender);

	}

	/**
	 * 查尋資料庫
	 */
	private void queryDataBase() {

	}

	/**
	 * 設定Adapter元件
	 */
	private void setAdapter() {

	}

	/**
	 * 設置系統元件,Services、BroadcastReceiver
	 */
	private void setSystemServices() {

	}

	/**
	 * 於View元件設置監聽Event.
	 */
	private void setLinstener() {
		btn_information.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(InquiryActivity.this, InformationActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});

		btn_Classification.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(InquiryActivity.this, Classification.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});

		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
//		btn_inquiry.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent();
//				intent.setClass(InquiryActivity.this, InquiryActivity.class);
//				startActivity(intent);
//			}
//		});
		btn_photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(InquiryActivity.this, PhotoEmail.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		
		btn_yes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				packUpData();
			}
		});
		
		
		sp_breed.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Userbreed = breedList.get(arg2);
//				Toast.makeText(InquiryActivity.this,"點選到"+Userbreed, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		sp_body.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				Userbody = bodyList.get(arg2);
//				Toast.makeText(InquiryActivity.this,"點選到"+Userbody, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		sp_colour.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				Usercolour = colourList.get(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		sp_age.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Userage = ageList.get(arg2);
				// TODO Auto-generated method stub
				// Toast.makeText(InquiryActivity.this,
				// "點選到"+mAllSpinnerArray_age[arg2], Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		sp_gender.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Usergender = genderList.get(arg2);
				// TODO Auto-generated method stub
				// Toast.makeText(InquiryActivity.this,
				// "點選到"+mAllSpinnerArray_gender[arg2],
				// Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	protected void packUpData() {
		List<JosnData> Data = new ArrayList<JosnData>();
//		private String Userbreed;
//		private String Userbody;
//		private String Usercolour;
//		private String Usergender;
//		private String Userage;
		
		if(CommonApplication.getPublic_Json_DataInfo(getApplicationContext())!=null){
			List<JosnData> myData = CommonApplication.getPublic_Json_DataInfo(getApplicationContext());
			
//			for (int i = 0; i < myData.size(); i++) {
//				if(myData.get(i).getType().equals(Userbreed) && myData.get(i).getBuild().equals(Userbody)&&myData.get(i).getHairType().equals(Usercolour)&&myData.get(i).getSex().equals(Usergender)&&myData.get(i).getAge().equals(Userage)){
//					Data.add(myData.get(i));
//				}
//			}	
			
			if(Userbreed.equals("全部")){
				System.out.println("等於全部的狀況");
				for (int i = 0; i < myData.size(); i++) {
					Data.add(myData.get(i));
				}
				
			}else{
				System.out.println("不等於全部的狀況");
				for (int i = 0; i < myData.size(); i++) {
					if(myData.get(i).getType().equals(Userbreed)){
						Data.add(myData.get(i));
					}
				}
			}
			
			if(!Userbody.equals("全部")){
				System.out.println("Userbody不等於全部的狀況");
				for (int i = 0; i < Data.size(); i++) {
					if(!Data.get(i).getBuild().equals(Userbody)){
						Data.remove(i);  
				        i=i-1;  
					}
				}
			}
			
			if(!Usercolour.equals("全部")){
				System.out.println("Usercolour不等於全部的狀況");
				for (int i = 0; i < Data.size(); i++) {
					if(!Data.get(i).getHairType().equals(Usercolour)){
						Data.remove(i);  
				        i=i-1;  
					}
				}
			}
			
			if(!Usergender.equals("全部")){
				System.out.println("Usercolour不等於全部的狀況");
				for (int i = 0; i < Data.size(); i++) {
					if(!Data.get(i).getSex().equals(Usergender)){
						Data.remove(i);  
				        i=i-1;  
					}
				}
			}
			
			if(!Userage.equals("全部")){
				System.out.println("Userage不等於全部的狀況");
				for (int i = 0; i < Data.size(); i++) {
					if(!Data.get(i).getAge().equals(Userage)){
						Data.remove(i);  
				        i=i-1;  
					}
				}
			}
			
		}
		
		if(Data==null){
			Toast.makeText(InquiryActivity.this, "資料發生異常", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if(Data.size()==0){
			Toast.makeText(InquiryActivity.this, getResources().getString(R.string.no_results),Toast.LENGTH_SHORT).show();
			return;
		}
		
		Intent intent = new Intent();
		Bundle bundel = new Bundle();
		bundel.putSerializable(CommonApplication.DataKey,(Serializable) Data);
		intent.setClass(InquiryActivity.this, Classification2.class);
		intent.putExtras(bundel);
		startActivity(intent);
		
	}

	/**
	 * 繪置UI
	 */
	private void renderUI() {

	}

	/**************************** 保存、釋放 、重啟 *************************************/

	/**
	 * 保存資料
	 */
	private void saveData() {

	}

	/**
	 * 釋放不必要的物件記憶體、Thread與Task
	 */
	private void releaseObject() {

	}

	/**
	 * 還原Thread、Task,與相關的物件
	 */
	private void restoreObject() {

	}

	/*************************************************************************
	 * Thread 、 AsycTask類別宣告
	 *************************************************************************/

	/*************************************************************************
	 * Handler類別宣告
	 *************************************************************************/

	/*************************************************************************
	 * Adapter類別宣告
	 *************************************************************************/

	/*************************************************************************
	 * 其它內部類別宣告
	 *************************************************************************/
//	private void downloadJsonData() {
//		Type keyType = new TypeToken<List<JosnData>>() {}.getType();
//		mDialog = new ProgressDialog(InquiryActivity.this);
//		mDialog.setMessage("資料處理中...");
//		mDialog.show();
//		CustomAsyncTaskGson TS = new CustomAsyncTaskGson(null, keyType,CustomAsyncTaskGson.HTTP_TYPE_GETRESPONSE,new CustomAsyncTaskGson.onPostExecute() {
////		CustomAsyncTaskGson Tg =new CustomAsyncTaskGson(null, keyType, CustomAsyncTaskGson.HTTP_TYPE_GETRESPONSE, new )
//					@Override
//					public void onPostExecute(Object result) {
//
//						if (result != null) {
//							List<JosnData> info = (List<JosnData>) result;
//							setData(info);
//						} else {
//							Toast.makeText(InquiryActivity.this, "資料下載錯誤",
//									Toast.LENGTH_SHORT).show();
//						}
//						mDialog.dismiss();
//					}
//				});
//		TS.run("http://163.29.39.183/GetAnimals.aspx");
//
//	}

	protected void setData(List<JosnData> info) {

		// 顯示品種列表
		breedList = getTypeType(info);
		ArrayAdapter<String> mAllspinnerAdapter_breed = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item,breedList);
		mAllspinnerAdapter_breed
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_breed.setAdapter(mAllspinnerAdapter_breed);

		// 顯示體型列表
		bodyList=getBuildType(info);
		ArrayAdapter<String> mAllspinnerAdapter_body = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, bodyList);
		mAllspinnerAdapter_body
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_body.setAdapter(mAllspinnerAdapter_body);

		// 顯示毛色列表
		colourList=getHairType(info);
		ArrayAdapter<String> mAllspinnerAdapter_colour = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, colourList);
		mAllspinnerAdapter_colour
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_colour.setAdapter(mAllspinnerAdapter_colour);

		// 顯示性別
		genderList=getSexType(info);
		ArrayAdapter<String> mAllspinnerAdapter_gender = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, genderList);
		mAllspinnerAdapter_gender
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_gender.setAdapter(mAllspinnerAdapter_gender);

//		顯示年齡
		ageList=getAgeType(info);
		ArrayAdapter<String> mAllspinnerAdapter_age = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, ageList);
		mAllspinnerAdapter_age
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_age.setAdapter(mAllspinnerAdapter_age);
		
		
		
	}

	private List<String> getHairType(List<JosnData> info) {
		List<String> mHairType = new ArrayList<String>();
		mHairType.add("全部");

		for (int i = 0; i < info.size(); i++) {
			String HairType = info.get(i).getHairType();
			boolean check = true;

			if (mHairType.size() == 0) {
				mHairType.add(HairType);
			} else {
				for (int j = 0; j < mHairType.size(); j++) {
					if (mHairType.get(j).equals(HairType)) {
						check = false;
					}
				}
				if (check == true) {
					mHairType.add(HairType);
				}
			}
		}
		return mHairType;
	}

	private List<String> getBuildType(List<JosnData> info) {
		List<String> mBuildType = new ArrayList<String>();
		mBuildType.add("全部");

		for (int i = 0; i < info.size(); i++) {
			String Build = info.get(i).getBuild();
			boolean check = true;

			if (mBuildType.size() == 0) {
				mBuildType.add(Build);
			} else {
				for (int j = 0; j < mBuildType.size(); j++) {
					if (mBuildType.get(j).equals(Build)) {
						check = false;
					}
				}
				if (check == true) {
					mBuildType.add(Build);
				}
			}
		}
		return mBuildType;
	}

	protected List<String> getTypeType(List<JosnData> info) {
		List<String> mTypeType = new ArrayList<String>();
		mTypeType.add("全部");

		for (int i = 0; i < info.size(); i++) {
			String Type = info.get(i).getType();
			boolean check = true;

			if (mTypeType.size() == 0) {
				mTypeType.add(Type);
			} else {
				for (int j = 0; j < mTypeType.size(); j++) {
					if (mTypeType.get(j).equals(Type)) {
						check = false;
					}
				}
				if (check == true) {
					mTypeType.add(Type);
				}
			}
		}
		
		return mTypeType;
	}

	protected List<String> getSexType(List<JosnData> info) {
		List<String> mSexType = new ArrayList<String>();
		mSexType.add("全部");

		for (int i = 0; i < info.size(); i++) {
			String Sex = info.get(i).getSex();
			boolean check = true;

			if (mSexType.size() == 0) {
				mSexType.add(Sex);
			} else {
				for (int j = 0; j < mSexType.size(); j++) {
					if (mSexType.get(j).equals(Sex)) {
						check = false;
					}
				}
				if (check == true) {
					mSexType.add(Sex);
				}
			}
		}
		return mSexType;
	}
	protected List<String> getAgeType(List<JosnData> info) {
		List<String> mAgeType = new ArrayList<String>();
		mAgeType.add("全部");

		for (int i = 0; i < info.size(); i++) {
			String Age = info.get(i).getAge();
			boolean check = true;

			if (mAgeType.size() == 0) {
				mAgeType.add(Age);
			} else {
				for (int j = 0; j < mAgeType.size(); j++) {
					if (mAgeType.get(j).equals(Age)) {
						check = false;
					}
				}
				if (check == true) {
					mAgeType.add(Age);
				}
			}
		}
		return mAgeType;
	}
}
