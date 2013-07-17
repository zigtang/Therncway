package net.therncway.Adapter;

import net.therncway.R;
import net.therncway.Base.AppCons;
import net.therncway.UI.MsgDetailActivity;
import net.therncway.Util.DateUtil;
import net.therncway.Util.ImageUtil;
import net.therncway.Util.PreferenceUtil;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends CursorAdapter implements OnItemClickListener{
	private Cursor mCursor;
	private Context context;
	public MyAdapter(Context context, Cursor c) {
		super(context, c, true);
		this.mCursor = c;
		this.context = context;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return LayoutInflater.from(context).inflate(R.layout.item_infomation, null);
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		if(cursor != null && cursor.getCount() > 0){
			((TextView)(view.findViewById(R.id.tv_info_item_subject))).setText(cursor.getString(cursor.getColumnIndex(AppCons.FILED_NAME_SUBJECT)));
			((TextView)(view.findViewById(R.id.tv_info_item_index))).setText(cursor.getString(cursor.getColumnIndex(AppCons.FILED_NAME_ID)));
			((TextView)(view.findViewById(R.id.tv_info_item_time))).setText(DateUtil.second2Date(cursor.getLong(cursor.getColumnIndex(AppCons.FILED_NAME_TIME))));
			((ImageView)(view.findViewById(R.id.img_info_item))).setImageResource(ImageUtil.getImagId());
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		StringBuffer sb = new StringBuffer();
		if(mCursor != null && position<mCursor.getCount()){
			sb.append(mCursor.getString(mCursor.getColumnIndex(AppCons.FILED_NAME_SUBJECT)));
			sb.append(";");
			sb.append(mCursor.getString(mCursor.getColumnIndex(AppCons.FILED_NAME_CONTENT)));
		}
		
		if(sb.length() > 0){
			Intent intent = new Intent(context,MsgDetailActivity.class);
//			intent.putExtra(AppCons.KEY_MESSAGE, sb.toString());
			PreferenceUtil.getInstance(context).saveMsg(sb.toString());
			context.startActivity(intent);
		}
	}
	
}
