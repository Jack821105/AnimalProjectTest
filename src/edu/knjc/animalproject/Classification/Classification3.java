package edu.knjc.animalproject.Classification;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.facebook.DialogError;
import com.android.facebook.Facebook;
import com.android.facebook.Facebook.DialogListener;
import com.android.facebook.FacebookError;

import edu.knjc.animalproject.R;
import edu.knjc.animalproject.Application.CommonApplication;
import edu.knjc.animalproject.model.JosnData;

public class Classification3 extends Activity {
	/*************************************************************************
	 * 常數數宣告區
	 *************************************************************************/

	/*************************************************************************
	 * 變數數宣告區
	 *************************************************************************/
	private Button btn_home;
	private Button btn_back;
	private TextView tv_title;
	private Button btn_fb;
	private Button btn_map;
	private Button btn_mail;
	private Button btn_phone;
	private ImageView ima_mPotho;
	/**************************** View元件變數 *********************************/
	private TextView tv_mContext;
	/**************************** Adapter元件變數 ******************************/

	/**************************** Array元件變數 ********************************/

	/**************************** 資料集合變數 *********************************/

	/**************************** 物件變數 *************************************/
	private JosnData mData;
	private Bitmap bit;
	private Facebook facebook;
	/*************************************************************************
	 * Override 函式宣告 (覆寫)
	 *************************************************************************/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.classification3);
		
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		facebook.authorizeCallback(requestCode, resultCode, data);
	}
	

	/*************************************************************************
	 * 自訂API函式宣告
	 *************************************************************************/
	
	/**
	 * 取得View元件記憶體位置
	 */
	private void findViews() {
		btn_home = (Button) findViewById(R.id.btn_home);
		btn_back = (Button) findViewById(R.id.btn_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		btn_fb = (Button) findViewById(R.id.btn_fb);
		btn_map = (Button) findViewById(R.id.btn_map);
		btn_mail = (Button) findViewById(R.id.btn_mail);
		btn_phone = (Button) findViewById(R.id.btn_phone);
		ima_mPotho = (ImageView) findViewById(R.id.ima_mPotho);
		tv_mContext = (TextView) findViewById(R.id.tv_context);
	}

	/**
	 * 初始化物件資料
	 */
	private void initData() {
		btn_home.setVisibility(View.INVISIBLE);
		tv_title.setText(getResources().getString(R.string.classfunction));
		if (getIntent() != null) {
			mData = (JosnData) getIntent().getSerializableExtra(CommonApplication.DataKey);
			setData();
			facebook = new Facebook("457516834343941");
		}
	}

	private void setData() {
		System.out.println(getResources().getString(R.string.test_name) + " = " + mData.getName());
		new Thread(GetHouseInfo).start();
		tv_mContext.setText(getResources().getString(R.string.name) + "：" + mData.getName() + "\n"
				+ getResources().getString(R.string.sex) + "：" + mData.getSex() + "\n" 
				+ getResources().getString(R.string.classfunction) + "：" + mData.getType() + "\n"
				+ getResources().getString(R.string.build) + "：" + mData.getBuild() + "\n"
				+ getResources().getString(R.string.age) + "：" + mData.getAge() + "\n"
				+ getResources().getString(R.string.variety) + "：" + mData.getVariety() + "\n"
				+ getResources().getString(R.string.reason) + "：" + mData.getReason() + "\n"
				+ getResources().getString(R.string.acceptNum) + "：" + mData.getAcceptNum() + "\n"
				+ getResources().getString(R.string.chipNum) + "：" + mData.getChipNum() + "\n"
				+ getResources().getString(R.string.issterilization) + "：" + mData.getIsSterilization() + "\n"
				+ getResources().getString(R.string.hairType) + "：" + mData.getHairType() + "\n"
				+ getResources().getString(R.string.resettlement) + "：" + mData.getResettlement() + "\n"
				+ getResources().getString(R.string.bodyweight) + "：" + mData.getBodyweight() + "\n"
				+ getResources().getString(R.string.phone) + "：" + mData.getPhone() + "\n"
				+ getResources().getString(R.string.email) + "：" + mData.getEmail() + "\n\n"
				+ getResources().getString(R.string.note) + "：" + mData.getNote() + "\n");
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
		
		btn_mail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sendEmail();
			}
		});
		
		btn_phone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Uri phoneUri = Uri.parse("tel:"+mData.getPhone());
				Intent phone = new Intent(Intent.ACTION_CALL, phoneUri);
				startActivity(phone);
			}
		});
		
		btn_map.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Uri mapUri = Uri.parse("https://maps.google.com/maps?ie=UTF-8&q=%E8%87%BA%E5%8C%97%E5%B8%82%E5%8B%95%E7%89%A9%E4%B9%8B%E5%AE%B6&fb=1&hq=%E5%8B%95%E7%89%A9%E4%B9%8B%E5%AE%B6&hnear=0x3442ac6b61dbbd9d:0xc0c243da98cba64b,%E5%8F%B0%E7%81%A3%E5%8F%B0%E5%8C%97%E5%B8%82&cid=0,0,6607676689702830895&ei=8i65UfWcO4uUkwXqloGACg&ved=0CHkQ_BIwAw");
				Intent map = new Intent(Intent.ACTION_VIEW, mapUri); 
				map.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
				startActivity(map); 
			}
		});
		
		btn_fb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(!facebook.isSessionValid()){
					faceBookLogin();
				}else{
					poMessageFaceBook();
				}
				
			}
		});
		
	}

	protected void poMessageFaceBook() {
		
		final Bundle params = new Bundle();
//		params.putString("message", "可以預寫使用者要發的黑體文字");
		params.putString("picture", mData.getImageName()); 
		params.putString("name", getResources().getString(R.string.my_name_is) + "：" + mData.getName() + getResources().getString(R.string.excellent) + mData.getVariety() + getResources().getString(R.string.variety) + getResources().getString(R.string.find_home));
//		params.putString("link",mData.getImageName());
		params.putString("caption", getResources().getString(R.string.sex) + "：" + mData.getSex()+"\n"
                                   + getResources().getString(R.string.build) + "：" +mData.getBuild() +"\n"
                                   + getResources().getString(R.string.age) + "：" + mData.getAge() +"\n" 
                                   + getResources().getString(R.string.issterilization) + "："+mData.getIsSterilization() +"\n" 
                                   + getResources().getString(R.string.acceptNum) + "：" +mData.getAcceptNum() +"\n"
                                   + getResources().getString(R.string.resettlement) + "：" +mData.getResettlement()+"\n"
                                   + getResources().getString(R.string.reason) + "：" + mData.getReason()
                                   + getResources().getString(R.string.take_me_home));
		params.putString("description",getResources().getString(R.string.phone) + "：" + mData.getPhone() +"\n" +getResources().getString(R.string.email) + "：" + mData.getEmail());
		
		facebook.dialog(Classification3.this, "feed", params, new DialogListener() {
			
			@Override
			public void onFacebookError(FacebookError e) {

			}
			
			@Override
			public void onError(DialogError e) {

			}
			
			@Override
			public void onComplete(Bundle values) {
				handler2.sendEmptyMessage(3);
			}
			
			@Override
			public void onCancel() {

			}
		});
		
	}

	protected void faceBookLogin() {
		
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				facebook.authorize(Classification3.this, new String[] {
						"publish_stream", "read_stream" },
						new DialogListener() {

							@Override
							public void onFacebookError(FacebookError e) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onError(DialogError e) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onComplete(Bundle values) {
								poMessageFaceBook();
							}

							@Override
							public void onCancel() {
								// TODO Auto-generated method stub

							}
						});

			}
		});

		thread.start();
		
	}

	protected void sendEmail() {
		String[] email = { mData.getEmail() };
		Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.query));
        intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.content));
        intent.setType("image/*");
        intent.setType("message/rfc882");
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
	Runnable GetHouseInfo = new Runnable() {
		
		@Override
		public void run() {
			try {
				bit = getUrlPic(mData.getImageName());
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
			ima_mPotho.setImageBitmap(bit);
		}

	};
	
	Handler handler2 = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 3:
				Toast.makeText(Classification3.this, "成功分享到facebook!", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}

	};
	
	/*************************************************************************
	 * Adapter類別宣告
	 *************************************************************************/

	/*************************************************************************
	 * 其它內部類別宣告
	 *************************************************************************/
	private synchronized Bitmap getUrlPic(String imgurl) {
		Bitmap webImg = null;
		try {
			URL url = new URL(imgurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStream is = conn.getInputStream();
			
			BitmapFactory.Options mOptions = new BitmapFactory.Options();
//			//Size=2為將原始圖片縮小1/2，Size=4為1/4，以此類推
			mOptions.inSampleSize = 2; 
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

