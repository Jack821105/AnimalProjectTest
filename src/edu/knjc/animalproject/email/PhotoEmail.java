package edu.knjc.animalproject.email;

import java.util.ArrayList;

import org.w3c.dom.Text;

import edu.knjc.animalproject.MainActivity;
import edu.knjc.animalproject.R;
import edu.knjc.animalproject.Classification.Classification;
import edu.knjc.animalproject.information.InformationActivity;
import edu.knjc.animalproject.inquiry.InquiryActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PhotoEmail extends Activity {
	/*************************************************************************
	 * 常數數宣告區
	 *************************************************************************/

	/*************************************************************************
	 * 變數數宣告區
	 *************************************************************************/
	private String photoPath ;
	private String[] email = { "a90125209@gmail.com" };
	/**************************** View元件變數 *********************************/
	private TextView tv_title;
	private Button btn_home;
	private Button btn_back;
	private Button btn_information;
	private Button btn_Classification;
	private Button btn_inquiry;
	private ImageView img_photo;
	private EditText edt_title;
	private EditText edt_context;
	private Button btn_send_email;
	private TextView tv_title2;
	/**************************** Adapter元件變數 ******************************/

	/**************************** Array元件變數 ********************************/

	/**************************** 資料集合變數 *********************************/

	/**************************** 物件變數 *************************************/

	/*************************************************************************
	 * Override 函式宣告 (覆寫)
	 *************************************************************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.e_mail);

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
		if (requestCode == 9527 && resultCode == RESULT_OK && data != null ) {
			callFile(data);
		}
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
		btn_information = (Button) findViewById(R.id.btn_information);
		btn_Classification = (Button) findViewById(R.id.btn_classification);
		btn_inquiry = (Button) findViewById(R.id.btn_inquiry);
		img_photo = (ImageView) findViewById(R.id.img_email_photo);
		edt_title = (EditText) findViewById(R.id.edt_email_title);
		edt_context = (EditText) findViewById(R.id.edt_email_context);
		btn_send_email = (Button) findViewById(R.id.btn_send_email);
		tv_title2 = (TextView) findViewById(R.id.tv_title2);
	}

	/**
	 * 初始化物件資料
	 */
	private void initData() {
		tv_title.setText(getResources().getString(R.string.upload));
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
		btn_information.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(PhotoEmail.this, InformationActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});

		btn_Classification.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(PhotoEmail.this, Classification.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		btn_inquiry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(PhotoEmail.this, InquiryActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		
		img_photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
				intent.setType("image/*");
				startActivityForResult(intent, 9527);
				tv_title2.setVisibility(tv_title2.GONE);
			}
			
		});
		
		btn_send_email.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(edt_title.getText().toString().equals("") || edt_context.getText().toString().equals("")|| photoPath == null){
					backDialog();
					return;
				}
				sendEmail();
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
	protected void sendEmail() {
		
		Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.content) + edt_context.getText().toString());
        intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.subject) + edt_title.getText().toString());
        ArrayList<Uri> imageUris = null;
        
        if(photoPath!=null){
        	 imageUris = new ArrayList<Uri>();
             if(photoPath!=null){imageUris.add(Uri.parse("file://"+photoPath));}
             intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        }
        
        intent.setType("image/*");
        intent.setType("message/rfc882");
        startActivity(intent);
        finish();
		
	}
	
	private void callFile(Intent data) {
		
		Cursor cursor =null;
		photoPath = null;
		
		try {
			cursor = getContentResolver().query(data.getData(), null, null, null, null);
			if (cursor.moveToFirst()){
				int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			    String absoluteFilePath = cursor.getString(index);
			    
			    System.out.println(getResources().getString(R.string.path) + absoluteFilePath);
			    
			    if(absoluteFilePath!=null){
			    	photoPath = absoluteFilePath;
			    	//方法一
			    	Bitmap bitmap = BitmapFactory.decodeFile(photoPath); //讀取檔案路徑圖片
			    	img_photo.setImageBitmap(bitmap);
			    	//方法二 圖片太大時 使用
//			    	BitmapFactory.Options mOptions = new BitmapFactory.Options();
//					//Size=2為將原始圖片縮小1/2，Size=4為1/4，以此類推
//					mOptions.inSampleSize = 2; 
//					ContentResolver cr;
//					Bitmap bitmapd = BitmapFactory.decodeFile(photoPath,mOptions);
//					img_photo.setImageBitmap(bitmapd);
			    	
			    }
			    
			}
			
		} catch (Exception e) {
			System.out.println(getResources().getString(R.string.an_error_occurred));
		}finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		
	}
private void backDialog() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(PhotoEmail.this);
		builder.setTitle(getResources().getString(R.string.input_errors));
		builder.setMessage(getResources().getString(R.string.subject_and_content_or_pictures_can_not_be_empty));
		builder.setPositiveButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		
		builder.setNegativeButton(getResources().getString(R.string.confirm),new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			});
		
		builder.show();
}
}
