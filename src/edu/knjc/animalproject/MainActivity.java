package edu.knjc.animalproject;

import java.lang.reflect.Type;
import java.util.List;

import one.customutil.CustomAsyncTaskGson;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.knjc.animalproject.Application.CommonApplication;
import edu.knjc.animalproject.Classification.Classification;
import edu.knjc.animalproject.email.PhotoEmail;
import edu.knjc.animalproject.information.InformationActivity;
import edu.knjc.animalproject.inquiry.InquiryActivity;
import edu.knjc.animalproject.model.JosnData;

public class MainActivity extends Activity {
	/*************************************************************************
	 * 常數數宣告區
	 *************************************************************************/

	/*************************************************************************
	 * 變數數宣告區
	 *************************************************************************/

	/**************************** View元件變數 *********************************/
	private Button btn_information;
	private Button btn_Classification;
	private Button btn_home;
	private Button btn_back;
	private Button btn_inquiry;
	private Button btn_photo;
	private ProgressDialog mDialog;
	private ImageView iv_image;
	/**************************** Adapter元件變數 ******************************/

	/**************************** Array元件變數 ********************************/

	/**************************** 資料集合變數 *********************************/

	/**************************** 物件變數 *************************************/
	private SoundPool soundPool;
	private static final int SOUND_COUNT = 2;
	private int sneezeId;
	private int cost;
	private SharedPreferences prefs;
	private SharedPreferences.Editor editor ;
	/*************************************************************************
	 * Override 函式宣告 (覆寫)
	 *************************************************************************/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.soundPool = new SoundPool(SOUND_COUNT, AudioManager.STREAM_MUSIC,0);
		this.sneezeId = this.soundPool.load(this, R.raw.cat_01, 1);
		
		findViews();
		/* 初始化資料,包含從其他Activity傳來的Bundle資料 ,Preference資枓 */
		initData();
		/* 設置必要的系統服務元件如: Services、BroadcastReceiver */
		setSystemServices();
		/* 設置View元件對應的linstener事件,讓UI可以與用戶產生互動 */
		setLinstener();
		
		if(prefs.getLong("DATATIME", 0) == 0 || CommonApplication.checkCompareTime(CommonApplication.getNowDataTime(), prefs.getLong("DATATIME", 0))){
			downloadJsonData();
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
	
	@Override
	public void onBackPressed() {
		backDialog();
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
		btn_home = (Button) findViewById(R.id.btn_home);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_inquiry=(Button) findViewById(R.id.btn_inquiry);
		btn_photo=(Button) findViewById(R.id.btn_photo);
		iv_image = (ImageView) findViewById(R.id.imageView1);
	}
	
	/**
	 * 初始化物件資料
	 */
	private void initData() {
		btn_home.setVisibility(View.INVISIBLE);
		btn_back.setText(getResources().getString(R.string.Leave));
		cost = 0;
		prefs = getApplicationContext().getSharedPreferences("GsonData",getApplicationContext().MODE_PRIVATE);
		editor = prefs.edit();
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
				intent.setClass(MainActivity.this, InformationActivity.class);
				startActivity(intent);
//				overridePendingTransition(0, 0);
			}
		});
		
		btn_Classification.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, Classification.class);
				startActivity(intent);
//				overridePendingTransition(0, 0);
			}
		});
		
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				backDialog();
			}
		});
		btn_inquiry.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, InquiryActivity.class);
				startActivity(intent);
//				overridePendingTransition(0, 0);
			}
		});
		btn_photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, PhotoEmail.class);
				startActivity(intent);
//				overridePendingTransition(0, 0);
			}
		});
		iv_image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				cost++;
				if(cost == 5){
					Toast.makeText(MainActivity.this, "喵~喵~~", Toast.LENGTH_SHORT).show();
				}
				
				if(cost==15){
					Toast.makeText(MainActivity.this, "喵~喵~~你很愛點唷!!", Toast.LENGTH_SHORT).show();
					soundPool.play(sneezeId, 1, 1, 0, 0, 1);
					cost =0;
				}
				
				
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
	private void downloadJsonData() {
		Type keyType = new TypeToken<List<JosnData>>() {}.getType();
		mDialog = new ProgressDialog(MainActivity.this);
		mDialog.setCanceledOnTouchOutside(false);
		mDialog.setMessage(getResources().getString(R.string.data_processing));
		mDialog.show();
		CustomAsyncTaskGson TS = new CustomAsyncTaskGson(null, keyType,CustomAsyncTaskGson.HTTP_TYPE_GETRESPONSE,new CustomAsyncTaskGson.onPostExecute() {
//		CustomAsyncTaskGson Tg =new CustomAsyncTaskGson(null, keyType, CustomAsyncTaskGson.HTTP_TYPE_GETRESPONSE, new )
					@Override
					public void onPostExecute(Object result) {
						if (result != null) {
							List<JosnData> info = (List<JosnData>) result;
//							CommonApplication.setPublic_Json_DataInfo(info);
							Gson gson = new Gson();
							editor.putString("GGSONDATA", gson.toJson(info));
							editor.putLong("DATATIME", CommonApplication.getRestrictionDataTime());
							editor.commit();
							
						} else {
							Toast.makeText(MainActivity.this, getResources().getString(R.string.download_error),Toast.LENGTH_SHORT).show();
						}
						mDialog.dismiss();
					}
				});
		TS.run("http://163.29.39.183/GetAnimals.aspx");

	}

	/*************************************************************************
	 * Handler類別宣告
	 *************************************************************************/

	/*************************************************************************
	 * Adapter類別宣告
	 *************************************************************************/

	/*************************************************************************
	 * 其它內部類別宣告
	 *************************************************************************/
	private void backDialog() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle(getResources().getString(R.string.back_title_text));
		builder.setMessage(getResources().getString(R.string.back_msg_text));
		builder.setPositiveButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		
		builder.setNegativeButton(getResources().getString(R.string.confirm),new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
		
		builder.show();
		
	}

}
