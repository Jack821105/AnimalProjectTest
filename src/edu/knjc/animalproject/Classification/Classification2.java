package edu.knjc.animalproject.Classification;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import edu.knjc.animalproject.MainActivity;
import edu.knjc.animalproject.R;
import edu.knjc.animalproject.Application.CommonApplication;
import edu.knjc.animalproject.email.PhotoEmail;
import edu.knjc.animalproject.information.InformationActivity;
import edu.knjc.animalproject.inquiry.InquiryActivity;
import edu.knjc.animalproject.model.JosnData;

public class Classification2 extends Activity {
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
	private Button btn_home;
	private Button btn_back;
	private GridView gv_mGridView;
	private List<Map<String, Object>> listData;
	/**************************** View元件變數 *********************************/
	private ProgressDialog mDialog;
	/**************************** Adapter元件變數 ******************************/

	/**************************** Array元件變數 ********************************/

	/**************************** 資料集合變數 *********************************/

	/**************************** 物件變數 *************************************/
	private List<JosnData> ListData;
	/*************************************************************************
	 * Override 函式宣告 (覆寫)
	 *************************************************************************/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.classification2);

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
		btn_information = (Button) findViewById(R.id.btn_information);
		btn_Classification = (Button) findViewById(R.id.btn_classification);
		btn_inquiry = (Button) findViewById(R.id.btn_inquiry);
		btn_photo = (Button) findViewById(R.id.btn_photo);
		tv_title = (TextView) findViewById(R.id.tv_title);
		btn_home = (Button) findViewById(R.id.btn_home);
		btn_back = (Button) findViewById(R.id.btn_back);
		gv_mGridView = (GridView) findViewById(R.id.gridView1);
	}

	/**
	 * 初始化物件資料
	 */
	private void initData() {
		tv_title.setText(getResources().getString(R.string.classfunction));
		if (getIntent() != null) {
			ListData = (List<JosnData>) getIntent().getSerializableExtra(CommonApplication.DataKey);
			System.out.println("幹你老師   " + ListData.get(0).getName());
			setData();
		}
	}

	private void setData() {
		listData = null;
		listData = new ArrayList<Map<String,Object>>();
		mDialog = new ProgressDialog(Classification2.this);
		mDialog.setCanceledOnTouchOutside(false);
		mDialog.setMessage(getResources().getString(R.string.data_processing));
		mDialog.show();
		new Thread(GetHouseInfo).start();
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
				intent.setClass(Classification2.this, InformationActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});

		btn_Classification.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.setClass(Classification2.this, Classification.class);
				startActivity(intent);
			}
		});

		btn_inquiry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Classification2.this, InquiryActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});

		btn_photo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Classification2.this, PhotoEmail.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
		
		gv_mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();
				Bundle bundel = new Bundle();
				bundel.putSerializable(CommonApplication.DataKey,ListData.get(arg2));
				intent.setClass(Classification2.this, Classification3.class);
				intent.putExtras(bundel);
				startActivity(intent);
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
	Runnable GetHouseInfo = new Runnable() {

		@Override
		public void run() {
			try {
				
				for (int i = 0; i < ListData.size(); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("name", ListData.get(i).getName());
					// map.put("image", R.drawable.ic_launcher);
					Bitmap bitmap;
					bitmap = getUrlPic(ListData.get(i).getImageName());
					map.put("image", bitmap);
					listData.add(map);
				}
				handler.sendEmptyMessage(0);
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	};

	/*************************************************************************
	 * Handler類別宣告
	 *************************************************************************/
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			SimpleAdapter sim = new SimpleAdapter(Classification2.this, listData,R.layout.classfunction_item, new String[] { "name", "image" },new int[] { R.id.item_textview, R.id.item_image });
			posee(sim);
			gv_mGridView.setAdapter(sim);
			mDialog.dismiss();
		}

	};

	/*************************************************************************
	 * Adapter類別宣告
	 *************************************************************************/

	/*************************************************************************
	 * 其它內部類別宣告
	 *************************************************************************/
	private void posee(SimpleAdapter adapter) {
		adapter.setViewBinder(new ViewBinder() {

			@Override
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				if (view instanceof ImageView && data instanceof Bitmap) {
					ImageView iv = (ImageView) view;
					iv.setImageBitmap((Bitmap) data);
					return true;
				} else
					return false;
			}
		});

	}

	private synchronized Bitmap getUrlPic(String imgurl) {
		Bitmap webImg = null;
		try {
			URL url = new URL(imgurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStream is = conn.getInputStream();
			
			BitmapFactory.Options mOptions = new BitmapFactory.Options();
//			//Size=2為將原始圖片縮小1/2，Size=4為1/4，以此類推
			mOptions.inSampleSize = 4; 
//			ContentResolver cr;
//			Bitmap bitmapd = BitmapFactory.decodeFile(photoPath,mOptions);
//			img.setImageBitmap(bitmapd);
			
			
			webImg = BitmapFactory.decodeStream(is,null, mOptions);
		} catch (IOException e) {
			Log.e("MCU_House", "getUrlPic Error Msg : " + e.toString());
		}
		return webImg;
	}
}
