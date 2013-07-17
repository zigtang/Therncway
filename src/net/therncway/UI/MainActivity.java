package net.therncway.UI;

import java.net.Socket;

import net.therncway.R;
import net.therncway.Base.AppCons;
import net.therncway.Base.BaseActivity;
import net.therncway.Bussiness.NetworkService;
import net.therncway.Util.DBHelper;
import net.therncway.Util.Logger;
import net.therncway.Util.NetUtil;
import net.therncway.Views.HistoryView;
import net.therncway.Views.InfomationView;
import net.therncway.Views.SettingView;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements OnClickListener{

	View infoView;
	View histView;
	View setView;
	ImageButton btnMsg;
	ImageButton btnHistory;
	ImageButton btnSetting;
	FrameLayout contentView;
	private DBHelper dbHelper;
	TextView tvTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Logger.d(TAG, "onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		contentView = (FrameLayout) findViewById(R.id.layout_main_content);
		dbHelper = DBHelper.getInstance(getApplication());
		startService(new Intent(this, NetworkService.class));
		btnMsg = (ImageButton)findViewById(R.id.btn_main_information);
		btnHistory = (ImageButton)findViewById(R.id.btn_main_history);
		btnSetting = (ImageButton)findViewById(R.id.btn_main_setting);
		tvTitle = (TextView)findViewById(R.id.tv_title);
		findViewById(R.id.tab_msg).performClick();
		if(NetworkService.isConnected){
			showToast("网络通路正常");
		}
	}


	
	

	@Override
	protected void onNewIntent(Intent intent) {
		Logger.d(TAG, "onNewIntent()");
		super.onNewIntent(intent);
		if(intent.getBooleanExtra(AppCons.FLAG_SET_ADDRESS, false)){
			findViewById(R.id.tab_setting).performClick();
			showToast("连接服务器出错，请确认您的设置");
		}
	}

	@Override
	public void onClick(View v) {
		resetButton();
		View view = null;
		int titleId = 0;
		switch (v.getId()) {
		case R.id.tab_msg:
			if (infoView == null) {
				infoView = new InfomationView(this).asView();
			}
			view = infoView;
			titleId = R.string.title_infomation;
			btnMsg.setEnabled(false);
			break;
		case R.id.tab_history:
			if (histView == null) {
				histView = new HistoryView(this).asView();
			}
			view = histView;
			titleId = R.string.title_history;
			btnHistory.setEnabled(false);
			break;
		case R.id.tab_setting:
			if (setView == null) {
				setView = new SettingView(this).asView();
			}
			view = setView;
			titleId = R.string.title_setting;
			btnSetting.setEnabled(false);
			break;
		default:
			break;
		}

		if (view != null) {
			showView(view);
			setTitle(titleId);
		}

	}

	private void resetButton() {
		btnMsg.setEnabled(true);
		btnHistory.setEnabled(true);
		btnSetting.setEnabled(true);
	}

	private void showView(View view) {
		contentView.removeAllViews();
		contentView.addView(view);
	}

	public DBHelper getDBHelp() {
		return dbHelper;
	}

	@Override
	public void setTitle(int titleId) {
		tvTitle.setText(getString(titleId));
	}
	
}
