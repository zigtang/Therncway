package net.therncway.Bussiness;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import net.therncway.R;
import net.therncway.Base.AppCons;
import net.therncway.UI.MainActivity;
import net.therncway.UI.MsgDetailActivity;
import net.therncway.UI.MsgDialogActivity;
import net.therncway.Util.DBHelper;
import net.therncway.Util.Logger;
import net.therncway.Util.PreferenceUtil;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by zig on 13-7-9.
 */
public class NetworkService extends Service {

	private final String TAG = "NetworkService";
	private  DBHelper dbHelper;
	private String desName;
	private int desPort;
	private PreferenceUtil preferenceUtil;
	public static boolean isConnected = false;
	private Socket socket = null;

	@Override
	public void onCreate() {
		Logger.d(TAG, "onCreate()");
		super.onCreate();
		dbHelper = DBHelper.getInstance(getApplication());
		preferenceUtil = PreferenceUtil.getInstance(getApplication());
		desName = preferenceUtil.getAddress();
		desPort = preferenceUtil.getPort();
		Logger.d(TAG, "desName:" + desName + "   desPort:" + desPort);
		connectServer();
//		showNotification("Subject;Conent");
	}

	/**
	 * 用后台线程来连接服务端，并保持与服务端的通讯
	 */
	private void connectServer() {
		Logger.d(TAG, "connectServer()");
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				Logger.d(TAG, "doInBackground()");
				try {
					socket = new Socket(desName, desPort);
					tellMeTestResult(socket);
					listenServer(socket);
				} catch (Exception e) {
					e.printStackTrace();
					setAddress();
				}
				
				return null;
			}

			// @Override
			// protected void onPostExecute(Void result) {
			// super.onPostExecute(result);
			// Logger.d(TAG, "onPostExecute");
			// /** 出现错误1分钟后重新连接 */
			// handler.postDelayed(new Runnable() {
			// @Override
			// public void run() {
			// connectServer();
			// }
			// }, 1000);
			// }
		}.execute();
	}

	protected void listenServer(Socket socket) {
		Logger.d(TAG, "listenServer");
		BufferedReader bf;
		try {
			bf = new BufferedReader(new InputStreamReader(
					socket.getInputStream(), "GB2312"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = bf.readLine()) != null) {
				buffer.append(line);
				if (line.endsWith(AppCons.END)) {
					Logger.d(TAG, buffer.toString());
					if (isMyMessage(buffer.toString())) {
						showNotification(buffer.toString());
						dbHelper.insert(buffer.toString());
					}
					buffer.setLength(0);// 清除buffer内容
				}
			}
		}  catch (Exception e) {
			e.printStackTrace();
			setAddress();
		}
		isConnected = false;
	}

	

	protected boolean isMyMessage(String msg) {
		Logger.d(TAG, "msg:" + msg);
		String[] masks = preferenceUtil.getMaskword().split("，");
		for (String str : masks) {
			Logger.d(TAG, "str:" + str);
			if (!"".equals(str) && msg.contains(str))
				return false;
		}
		return true;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private void showNotification(String msg) {
		Logger.d(TAG, "showNotification--msg:" + msg);
		if (preferenceUtil.isNotification()) {
			Notification notification = new Notification(R.drawable.ic_launcher, msg, System.currentTimeMillis());
			// notification.defaults |= Notification.DEFAULT_SOUND;
			if (preferenceUtil.isSoundOn()) {
				Uri uri = Uri.parse("android.resource://"+ getPackageName() + "/" + R.raw.notification);
				notification.sound = uri;
			}

			Intent intent = new Intent(this,MsgDetailActivity.class);
			preferenceUtil.saveMsg(msg);//无奈之举
//        	intent.putExtra(AppCons.KEY_MESSAGE, msg);
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0,intent, 0);
			notification.setLatestEventInfo(this, "hey~来消息啦～", msg,contentIntent);
			NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			nm.notify(R.string.app_name, notification);
		} else {
			Intent intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setClass(this, MsgDialogActivity.class);
			intent.putExtra(AppCons.KEY_MESSAGE, msg);
			startActivity(intent);
		}

	}

	private void setAddress() {
		Logger.d(TAG, "setAddress");
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra(AppCons.FLAG_SET_ADDRESS, true);
		startActivity(intent);
	}

	protected void tellMeTestResult(Socket socket) {
		isConnected = socket.isConnected();
		final String str = isConnected ? "网络正常，现在可以正常收取信息" : "网络不通，请检查设置";
		
		 new Thread(new Runnable() {    
             public void run() {    
                 Looper.prepare();    
                 Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
                 Looper.loop();    
             }    
         }).start();    
	}

	@Override
	public void onDestroy() {
		Logger.d(TAG, "onDestroy()");
		super.onDestroy();
		try {
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
		

}
