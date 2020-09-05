package edu.knjc.animalproject.Classification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import edu.knjc.animalproject.R;
import edu.knjc.animalproject.Application.CommonApplication;
import edu.knjc.animalproject.email.PhotoEmail;
import edu.knjc.animalproject.information.InformationActivity;
import edu.knjc.animalproject.inquiry.InquiryActivity;
import edu.knjc.animalproject.model.JosnData;

public class Classification extends Activity {
	/*************************************************************************
	 * 常數數宣告區
	 *************************************************************************/

	/*************************************************************************
	 * 變數數宣告區
	 *************************************************************************/
	private Button btn_information;
	private Button btn_Classification;
	private Button btn_inquiry;
	private Button btn_photo;
	private TextView tv_title;
	private Button btn_cat;
	private Button btn_dog;
	private Button btn_other;
	private Button btn_home;
	private Button btn_back;
	/**************************** View元件變數 *********************************/

	/**************************** Adapter元件變數 ******************************/

	/**************************** Array元件變數 ********************************/
	private List<JosnData> DogListData;
	private List<JosnData> CatListData;
	private List<JosnData> OtherListData;
	/**************************** 資料集合變數 *********************************/

	/**************************** 物件變數 *************************************/
	
	/*************************************************************************
	 * Override 函式宣告 (覆寫)
	 *************************************************************************/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.classification);

		findViews();
		/* 初始化資料,包含從其他Activity傳來的Bundle資料 ,Preference資枓 */
		initData();
		/* 設置必要的系統服務元件如: Services、BroadcastReceiver */
		setSystemServices();
		/* 設置View元件對應的linstener事件,讓UI可以與用戶產生互動 */
		setLinstener();

	}

	private void packUpData() {
		
		List<JosnData> Data = CommonApplication.getPublic_Json_DataInfo(getApplicationContext());
		
		if(Data==null){
			Toast.makeText(Classification.this, "資料發生異常", Toast.LENGTH_SHORT).show();
			return;
		}
		
		DogListData = new ArrayList<JosnData>();
		CatListData = new ArrayList<JosnData>();
		OtherListData = new ArrayList<JosnData>();
		
		
		for (int i = 0; i < Data.size(); i++) {
			
			if(Data.get(i).getType().equals("犬")){
				DogListData.add(Data.get(i));
			}else if(Data.get(i).getType().equals("貓")){
				CatListData.add(Data.get(i));
			}else{
				OtherListData.add(Data.get(i));
			}
		}
		
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
		btn_information = (Button) findViewById(R.id.btn_information);
		btn_Classification = (Button) findViewById(R.id.btn_classification);
		btn_inquiry=(Button) findViewById(R.id.btn_inquiry);
		btn_photo=(Button) findViewById(R.id.btn_photo);
		tv_title = (TextView) findViewById(R.id.tv_title);
		btn_cat = (Button) findViewById(R.id.btn_cat);
		btn_dog = (Button) findViewById(R.id.btn_dog);
		btn_other = (Button) findViewById(R.id.btn_other);
		btn_home = (Button) findViewById(R.id.btn_home);
		btn_back = (Button) findViewById(R.id.btn_back);
		
	}

	/**
	 * 初始化物件資料
	 */
	private void initData() {
		tv_title.setText(getResources().getString(R.string.classfunction));
		packUpData();
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
				intent.setClass(Classification.this, InformationActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		
//		btn_Classification.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent();
//				intent.setClass(Classification.this, Classification.class);
//				startActivity(intent);
//			}
//		});
		
		btn_inquiry.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Classification.this, InquiryActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		
		btn_photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Classification.this, PhotoEmail.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		
		
		btn_cat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Bundle bundel = new Bundle();
				bundel.putSerializable(CommonApplication.DataKey,(Serializable) CatListData);
				intent.setClass(Classification.this, Classification2.class);
				intent.putExtras(bundel);
				startActivity(intent);
			}
		});

		btn_dog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Bundle bundel = new Bundle();
				bundel.putSerializable(CommonApplication.DataKey,(Serializable) DogListData);
				intent.setClass(Classification.this, Classification2.class);
				intent.putExtras(bundel);
				startActivity(intent);
			}
		});

		btn_other.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Bundle bundel = new Bundle();
				bundel.putSerializable(CommonApplication.DataKey,(Serializable) OtherListData);
				intent.setClass(Classification.this, Classification2.class);
				intent.putExtras(bundel);
				startActivity(intent);
			}
		});

		btn_home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		
		
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

}
