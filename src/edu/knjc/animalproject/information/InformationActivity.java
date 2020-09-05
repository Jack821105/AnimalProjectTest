package edu.knjc.animalproject.information;

import edu.knjc.animalproject.MainActivity;
import edu.knjc.animalproject.R;
import edu.knjc.animalproject.Classification.Classification;
import edu.knjc.animalproject.email.PhotoEmail;
import edu.knjc.animalproject.inquiry.InquiryActivity;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class InformationActivity extends Activity implements OnClickListener {
	/*************************************************************************
	 * 常數數宣告區
	 *************************************************************************/

	/*************************************************************************
	 * 變數數宣告區
	 *************************************************************************/

	/**************************** View元件變數 *********************************/
	private TextView tv_title;
	private Button btn_home;
	private Button btn_back;
	private Button btn1;
	private Button btn2;
	private Button btn3;
	private Button btn4;
	private Button btn5;
	private Button btn6;
	private Button btn7;
	private Button btn8;
	private Button btn9;
	private Button btn10;
	private Button btn11;
	private Button btn12;
	private Button btn_inquiry;
	private Button btn_photo;
	private Button btn_Classification;

	/**************************** Adapter元件變數 ******************************/

	/**************************** Array元件變數 ********************************/

	/**************************** 資料集合變數 *********************************/

	/**************************** 物件變數 *************************************/

	/*************************************************************************
	 * Override 函式宣告 (覆寫)
	 *************************************************************************/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.information);

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
		btn_Classification = (Button) findViewById(R.id.btn_classification);
		btn_inquiry = (Button) findViewById(R.id.btn_inquiry);
		btn_photo = (Button) findViewById(R.id.btn_photo);
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button) findViewById(R.id.btn3);
		btn4 = (Button) findViewById(R.id.btn4);
		btn5 = (Button) findViewById(R.id.btn5);
		btn6 = (Button) findViewById(R.id.btn6);
		btn7 = (Button) findViewById(R.id.btn7);
		btn8 = (Button) findViewById(R.id.btn8);
		btn9 = (Button) findViewById(R.id.btn9);
		btn10 = (Button) findViewById(R.id.btn10);
		btn11 = (Button) findViewById(R.id.btn11);
		btn12 = (Button) findViewById(R.id.btn12);
	}

	/**
	 * 初始化物件資料
	 */
	private void initData() {
		tv_title.setText(getResources().getString(R.string.information));
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
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
		btn8.setOnClickListener(this);
		btn9.setOnClickListener(this);
		btn10.setOnClickListener(this);
		btn11.setOnClickListener(this);
		btn12.setOnClickListener(this);
		btn_Classification.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(InformationActivity.this, Classification.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		btn_inquiry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(InformationActivity.this, InquiryActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		btn_photo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(InformationActivity.this, PhotoEmail.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn1:
//			Uri weburi = Uri.parse("http://163.29.39.183/info.aspx?id=37");
//			Intent web = new Intent(Intent.ACTION_VIEW, weburi);
//			startActivity(web);
			Intent web = new Intent();
			web.setClass(InformationActivity.this, InformationActivityWebView.class);
			web.putExtra("WEB_URL", "http://163.29.39.183/info.aspx?id=37");
			startActivity(web);
			break;
		case R.id.btn2:
//			Uri weburi2 = Uri.parse("http://163.29.39.183/info.aspx?id=35");
//			Intent web2 = new Intent(Intent.ACTION_VIEW, weburi2);
//			startActivity(web2);
			Intent web2 = new Intent();
			web2.setClass(InformationActivity.this, InformationActivityWebView.class);
			web2.putExtra("WEB_URL", "http://163.29.39.183/info.aspx?id=35");
			startActivity(web2);
			break;
		case R.id.btn3:
//			Uri weburi3 = Uri.parse("http://163.29.39.183/info.aspx?id=34");
//			Intent web3 = new Intent(Intent.ACTION_VIEW, weburi3);
//			startActivity(web3);
			Intent web3 = new Intent();
			web3.setClass(InformationActivity.this, InformationActivityWebView.class);
			web3.putExtra("WEB_URL", "http://163.29.39.183/info.aspx?id=34");
			startActivity(web3);
			break;
		case R.id.btn4:
//			Uri weburi4 = Uri.parse("http://163.29.39.183/info.aspx?id=43");
//			Intent web4 = new Intent(Intent.ACTION_VIEW, weburi4);
//			startActivity(web4);
			Intent web4 = new Intent();
			web4.setClass(InformationActivity.this, InformationActivityWebView.class);
			web4.putExtra("WEB_URL", "http://163.29.39.183/info.aspx?id=43");
			startActivity(web4);
			break;
		case R.id.btn5:
//			Uri weburi5 = Uri.parse("http://www.tmiah.tcg.gov.tw/ct.asp?xItem=917509&ctNode=49400&mp=105031");
//			Intent web5 = new Intent(Intent.ACTION_VIEW, weburi5);
//			startActivity(web5);
			Intent web5 = new Intent();
			web5.setClass(InformationActivity.this, InformationActivityWebView.class);
			web5.putExtra("WEB_URL", "http://www.tmiah.tcg.gov.tw/ct.asp?xItem=917509&ctNode=49400&mp=105031");
			startActivity(web5);
			break;
		case R.id.btn6:
//			Uri weburi6 = Uri.parse("http://www.tmiah.tcg.gov.tw/ct.asp?xItem=917513&ctNode=49400&mp=105031");
//			Intent web6 = new Intent(Intent.ACTION_VIEW, weburi6);
//			startActivity(web6);
			Intent web6 = new Intent();
			web6.setClass(InformationActivity.this, InformationActivityWebView.class);
			web6.putExtra("WEB_URL", "http://www.tmiah.tcg.gov.tw/ct.asp?xItem=917513&ctNode=49400&mp=105031");
			startActivity(web6);
			break;
		case R.id.btn7:
//			Uri weburi7 = Uri.parse("http://www.tmiah.tcg.gov.tw/ct.asp?xItem=917518&ctNode=49400&mp=105031");
//			Intent web7 = new Intent(Intent.ACTION_VIEW, weburi7);
//			startActivity(web7);
			Intent web7 = new Intent();
			web7.setClass(InformationActivity.this, InformationActivityWebView.class);
			web7.putExtra("WEB_URL", "http://www.tmiah.tcg.gov.tw/ct.asp?xItem=917518&ctNode=49400&mp=105031");
			startActivity(web7);
			break;
		case R.id.btn8:
//			Uri weburi8 = Uri.parse("http://www.tmiah.tcg.gov.tw/ct.asp?xItem=917474&ctNode=49400&mp=105031");
//			Intent web8 = new Intent(Intent.ACTION_VIEW, weburi8);
//			startActivity(web8);
			Intent web8 = new Intent();
			web8.setClass(InformationActivity.this, InformationActivityWebView.class);
			web8.putExtra("WEB_URL", "http://www.tmiah.tcg.gov.tw/ct.asp?xItem=917474&ctNode=49400&mp=105031");
			startActivity(web8);
			break;
		case R.id.btn9:
//			Uri weburi9 = Uri.parse("http://www.tmiah.tcg.gov.tw/ct.asp?xItem=917476&ctNode=49400&mp=105031");
//			Intent web9 = new Intent(Intent.ACTION_VIEW, weburi9);
//			startActivity(web9);
			Intent web9 = new Intent();
			web9.setClass(InformationActivity.this, InformationActivityWebView.class);
			web9.putExtra("WEB_URL", "http://www.tmiah.tcg.gov.tw/ct.asp?xItem=917476&ctNode=49400&mp=105031");
			startActivity(web9);
			break;
		case R.id.btn10:
//			Uri weburi10 = Uri.parse("http://www.tmiah.tcg.gov.tw/ct.asp?xItem=1345504&ctNode=49400&mp=105031");
//			Intent web10 = new Intent(Intent.ACTION_VIEW, weburi10);
//			startActivity(web10);
			Intent web10 = new Intent();
			web10.setClass(InformationActivity.this, InformationActivityWebView.class);
			web10.putExtra("WEB_URL", "http://www.tmiah.tcg.gov.tw/ct.asp?xItem=1345504&ctNode=49400&mp=105031");
			startActivity(web10);
			break;
		case R.id.btn11:
//			Uri weburi11 = Uri.parse("http://www.tmiah.tcg.gov.tw/ct.asp?xItem=1345539&ctNode=49400&mp=105031");
//			Intent web11 = new Intent(Intent.ACTION_VIEW, weburi11);
//			startActivity(web11);
			Intent web11 = new Intent();
			web11.setClass(InformationActivity.this, InformationActivityWebView.class);
			web11.putExtra("WEB_URL", "http://www.tmiah.tcg.gov.tw/ct.asp?xItem=1345539&ctNode=49400&mp=105031");
			startActivity(web11);
			break;
		case R.id.btn12:
//			Uri weburi12 = Uri.parse("http://www.tmiah.tcg.gov.tw/ct.asp?xItem=1345543&ctNode=49400&mp=105031");
//			Intent web12 = new Intent(Intent.ACTION_VIEW, weburi12);
//			startActivity(web12);
			Intent web12 = new Intent();
			web12.setClass(InformationActivity.this, InformationActivityWebView.class);
			web12.putExtra("WEB_URL", "http://www.tmiah.tcg.gov.tw/ct.asp?xItem=1345543&ctNode=49400&mp=105031");
			startActivity(web12);
			break;
		default:
			break;
		}

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
