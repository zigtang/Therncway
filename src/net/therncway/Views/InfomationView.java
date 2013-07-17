package net.therncway.Views;


import net.therncway.R;
import net.therncway.Adapter.MyAdapter;
import net.therncway.Base.BaseView;
import net.therncway.UI.MainActivity;
import net.therncway.Util.DBHelper;
import net.therncway.interfaces.Observer;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.widget.ListView;

public class InfomationView extends BaseView implements Observer{
	private ListView listView;
	
	public InfomationView(Context context) {
		super(context);
		listView = (ListView) LayoutInflater.from(context).inflate(R.layout.view_information, null);
		setAdapterAndOnItemClick(listView ,new MyAdapter(context, getCursor()));
		dbHelper.registerObserver(this);
	}
	
	
	
	
	
//	private CursorAdapter getApdater(Context context) {
//		
//		CursorAdapter adapter = new CursorAdapter(context, getCursor(),true) {
//			
//			@Override
//			public View newView(Context context, Cursor cursor, ViewGroup parent) {
//				return LayoutInflater.from(context).inflate(R.layout.item_infomation, null);
//			}
//			
//			@Override
//			public void bindView(View view, Context context, Cursor cursor) {
//				((TextView)(view.findViewById(R.id.tv_info_item))).setText(cursor.getString(cursor.getColumnIndex(AppCons.FILED_NAME_SUBJECT)));
//				((TextView)(view.findViewById(R.id.tv_info_item_index))).setText(cursor.getString(cursor.getColumnIndex(AppCons.FILED_NAME_ID)));
//				((TextView)(view.findViewById(R.id.tv_info_item_time))).setText(DateUtil.second2Date(cursor.getLong(cursor.getColumnIndex(AppCons.FILED_NAME_TIME))));
//				((ImageView)(view.findViewById(R.id.img_info_item))).setImageResource(ImageUtil.getImagId());
//				
//			}
//		};
//		
//		return adapter;
//		
//	}


	private Cursor getCursor() {
		return dbHelper.getAll();
	}


	public ListView asView(){
		return listView;
	}

	@Override
	public void onChange() {
		((MainActivity)context).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				setAdapterAndOnItemClick(listView ,new MyAdapter(context, getCursor()));
			}
		});
	}


}
