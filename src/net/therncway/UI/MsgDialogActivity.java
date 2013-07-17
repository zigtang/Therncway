package net.therncway.UI;

import net.therncway.R;
import net.therncway.Base.AppCons;
import net.therncway.Base.BaseActivity;
import net.therncway.Util.Logger;
import net.therncway.Util.PreferenceUtil;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by zig on 13-7-9.
 */
public class MsgDialogActivity extends BaseActivity implements View.OnClickListener{

    String msgStr;
    TextView tvMsg;
    static WakeLock wk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWindow();
        
        setContentView(R.layout.activity_msg_dialog);
        setTitle("您有新的消息");
        tvMsg = (TextView)findViewById(R.id.tv_dialog_msg);
        showMessage(getIntent());
    }


    private void setWindow() {
    	Window win = getWindow();
    	WindowManager.LayoutParams winParams = win.getAttributes();
    	winParams.flags |= (WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
    			|WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
    			|WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
    			|WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
	}


	@Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_dialog_comfirm:
            	Intent intent = new Intent(this,MsgDetailActivity.class);
            	//intent.putExtra(AppCons.KEY_MESSAGE, msgStr);
            	PreferenceUtil.getInstance(this).saveMsg(msgStr);
            	startActivity(intent);
                break;
        }
        this.finish();
    }


    private void showMessage(Intent intent){
        Logger.i(TAG,"showMessage()");
        msgStr = intent.getExtras().getString(AppCons.KEY_MESSAGE);
        tvMsg.setText(msgStr);
    }


	@Override
	protected void onResume() {
		super.onResume();
		acquireWakeLock();
	}
	
	private void acquireWakeLock(){
		if(wk == null){
			PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
			wk = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, this.getClass().getCanonicalName());
			wk.acquire();
		}
	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
		releaseWakeLock();
	}


	private void releaseWakeLock(){
		if(wk != null && wk.isHeld()){
			wk.release();
			wk = null;
		}
	}
    
    



}
