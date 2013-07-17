package net.therncway.Util;

import net.therncway.Base.AppCons;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;


public class PreferenceUtil {

	private Context context;
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	private static PreferenceUtil preferenceUtil;
	private PreferenceUtil(Context context) {
		this.context = context;
		preferences = context.getSharedPreferences(AppCons.PREFERENS_NAME_SETTING, context.MODE_PRIVATE);
		editor = preferences.edit();
	}
	
	public static PreferenceUtil getInstance(Context context){
		if(preferenceUtil == null){
			preferenceUtil = new PreferenceUtil(context);
		}
		return preferenceUtil;
	}
	
	public void saveSettings(ContentValues values){
		//set sound
		Boolean isSoundOn = values.getAsBoolean(AppCons.SETTING_SOUND);
		if(isSoundOn != null){
			editor.putBoolean(AppCons.SETTING_SOUND, isSoundOn);
		}
		
		//set notification style
		Boolean isNotification = values.getAsBoolean(AppCons.SETTING_NOTIFICATION);
		if(isNotification != null){
			editor.putBoolean(AppCons.SETTING_NOTIFICATION, isNotification);
		}
		
		//set mask word
		String maskWord = values.getAsString(AppCons.SETTING_MASKWORD);
		if(isNotification != null){
			editor.putString(AppCons.SETTING_MASKWORD, maskWord);
		}
		editor.commit();
	}
	
	public boolean isSoundOn(){
		return preferences.getBoolean(AppCons.SETTING_SOUND, true);
	}
	
	public boolean isNotification(){
		return preferences.getBoolean(AppCons.SETTING_NOTIFICATION, true);
	}
	
	public void saveAddress(String desName,int desPort){
		editor.putString(AppCons.SETTING_ADDRESS, desName);
		editor.putInt(AppCons.SETTING_PORT, desPort);
		editor.commit();
	}
	
	public String getMaskword(){
		return preferences.getString(AppCons.SETTING_MASKWORD,"");
	}
	
	public String getAddress(){
		return preferences.getString(AppCons.SETTING_ADDRESS,AppCons.DEFAULT_VALUE_ADDRESS);
	}
	
	public int getPort(){
		return preferences.getInt(AppCons.SETTING_PORT,AppCons.DEFAULT_VALUE_PORT);
	}
	
	
	/**测试网络用的开关*/
	public boolean isFromTest(){
		return preferences.getBoolean(AppCons.SETTING_IS_FROM_TEST, AppCons.DEFAULT_VALUE_IS_FROM_TEST);
	}
	public void saveIsTest(boolean isTest){
		editor.putBoolean(AppCons.SETTING_IS_FROM_TEST, isTest);
		editor.commit();
	}
	
	/** 传递notifiacation 的信息*/
	public void saveMsg(String msg){
		editor.putString(AppCons.KEY_MESSAGE, msg);
		editor.commit();
	}
	
	public String getMsg(){
		return preferences.getString(AppCons.KEY_MESSAGE, "Subject;Conent");
	}
}
