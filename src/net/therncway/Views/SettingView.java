package net.therncway.Views;

import java.net.Socket;

import net.therncway.R;
import net.therncway.Base.AppCons;
import net.therncway.Base.BaseView;
import net.therncway.Bussiness.NetworkService;
import net.therncway.UI.MainActivity;
import net.therncway.Util.Logger;
import net.therncway.Util.NetUtil;
import net.therncway.Util.PreferenceUtil;
import android.R.anim;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Toast;

public class SettingView extends BaseView implements View.OnClickListener{
	private ScrollView layout;
	
	private EditText etAddress;
	private EditText etPort;
	private EditText etMaskWord;
	
	private RadioButton rbSoundOn;
	private RadioButton rbSoundOff;
	private RadioButton rbIsNotification;
	private RadioButton rbIsPopupWidnow;
	
	public SettingView(Context context) {
		super(context);
		initUI();
	}
	
	private void initUI() {
		layout = (ScrollView) LayoutInflater.from(context).inflate(R.layout.view_setting, null);
		
		layout.findViewById(R.id.btn_setting_test).setOnClickListener(this);
		layout.findViewById(R.id.btn_setting_exit).setOnClickListener(this);
		layout.findViewById(R.id.btn_setting_clean).setOnClickListener(this);
		layout.findViewById(R.id.btn_setting_save).setOnClickListener(this);
		
		etAddress = (EditText) layout.findViewById(R.id.et_setting_adderss);
		etPort = (EditText) layout.findViewById(R.id.et_setting_port);
		etMaskWord = (EditText) layout.findViewById(R.id.et_setting_maskword);
		
		etAddress.setText(preferenceUtil.getAddress());
		etPort.setText(""+preferenceUtil.getPort());
		etMaskWord.setText(preferenceUtil.getMaskword());
		etMaskWord.setOnClickListener(this);
		
		rbSoundOn = (RadioButton)(layout.findViewById(R.id.rb_setting_soundon));
		rbSoundOff = (RadioButton)(layout.findViewById(R.id.rb_setting_soundoff));
		rbIsNotification = (RadioButton)(layout.findViewById(R.id.rb_setting_notification));
		rbIsPopupWidnow = (RadioButton)(layout.findViewById(R.id.rb_setting_popupwindow));
		
		RadioButton rbSound = preferenceUtil.isSoundOn() ? rbSoundOn :rbSoundOff;
		RadioButton rbNotify = preferenceUtil.isNotification() ? rbIsNotification :rbIsPopupWidnow;
		rbSound.performClick();
		rbNotify.performClick();
		
	}

	public View asView(){
		return layout;
	}

	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_setting_test:
			testAddress();
			break;
		case R.id.btn_setting_exit:
			exitApp();
			break;
		case R.id.btn_setting_clean:
			cleanData();
			break;
		case R.id.btn_setting_save:
			saveSettings();
			break;
		case R.id.et_setting_maskword:
//			Intent intent = new Intent(context, MaskwordActivity.class);
//			((MainActivity)context).startActivityForResult(intent, AppCons.REQUEST_CODE_MASKWORD);
			break;
		default:
			break;
		}
		
	}

	private void cleanData() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("警告");
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setMessage("你确定要清空所有的消息？以后可就没得看了哦～");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dbHelper.deleDB();
				showToast("已清空所有数据！");
			}
		});
		builder.setNegativeButton("不了", null);
		builder.create().show();
	}

	private void testAddress() {
		String desName = etAddress.getText().toString();
		String desPort = etPort.getText().toString();
		if(!"".equals(desName) && !"".equals(desPort)){
			Logger.i("", "NetworkService.isConnected:"+NetworkService.isConnected);
			if(NetworkService.isConnected){
				showToast("当前网络连接正常，无需测试～");
			} else {
				Intent intent = new Intent(context, NetworkService.class);
				context.stopService(intent);
				context.startService(intent);
				preferenceUtil.saveAddress(desName, Integer.parseInt(desPort));
			}
		} else {
			showToast("信息填写不完整");
		}
		
		
	}

	private void exitApp() {
		context.stopService(new Intent(context, NetworkService.class));
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	private void saveSettings() {
		ContentValues values = new ContentValues();
		String strMaskword = etMaskWord.getText().toString();
		boolean isSoundon = rbSoundOn.isChecked();
		boolean isNotification = rbIsNotification.isChecked();
		
		if(!"".equals(strMaskword)){
			values.put(AppCons.SETTING_MASKWORD, strMaskword);
		}
		values.put(AppCons.SETTING_SOUND, isSoundon);
		values.put(AppCons.SETTING_NOTIFICATION, isNotification);
		preferenceUtil.saveSettings(values);
		showToast("保存成功~");
	}
	
	
}
