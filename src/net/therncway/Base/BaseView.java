package net.therncway.Base;

import net.therncway.Adapter.MyAdapter;
import net.therncway.UI.MainActivity;
import net.therncway.Util.DBHelper;
import net.therncway.Util.PreferenceUtil;
import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

public class BaseView {
	protected DBHelper dbHelper;
	protected Context context;
	protected PreferenceUtil preferenceUtil;
	public BaseView(Context context) {
		this.context = context;
		dbHelper = DBHelper.getInstance(context);
		preferenceUtil = PreferenceUtil.getInstance(context);
	}
	
	protected void setAdapterAndOnItemClick(ListView lv,MyAdapter adapter){
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(adapter);
	}
	
	protected void showToast(String str){
		Toast.makeText(context, str, Toast.LENGTH_LONG).show();
	}
}
