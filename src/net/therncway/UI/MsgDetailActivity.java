package net.therncway.UI;

import net.therncway.R;
import net.therncway.Base.AppCons;
import net.therncway.Base.BaseActivity;
import net.therncway.Util.DateUtil;
import net.therncway.Util.PreferenceUtil;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by zig on 13-7-9.
 */
public class MsgDetailActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msg_detail);
		setUI();
	}
	
	private void setUI(){
			String msgStr = PreferenceUtil.getInstance(this).getMsg();
			((TextView) findViewById(R.id.tv_title)).setText(("信息详情"));
			String[] msgArray = msgStr.split(";");
			if(msgArray.length == 2){
				((TextView) findViewById(R.id.tv_detail_subject)).setText(msgArray[0]);
				((TextView) findViewById(R.id.tv_detail_content)).setText(msgArray[1]);
				((TextView) findViewById(R.id.tv_detail_time)).setText(DateUtil
						.second2Date(System.currentTimeMillis()));
			} else {
				showToast("消息格式错误，无法解析");
			}
			
		
		
	}
}
