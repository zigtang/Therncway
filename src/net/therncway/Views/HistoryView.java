package net.therncway.Views;

import java.util.Calendar;
import java.util.Date;

import net.therncway.R;
import net.therncway.Adapter.MyAdapter;
import net.therncway.Base.AppCons;
import net.therncway.Base.BaseView;
import net.therncway.UI.MainActivity;
import net.therncway.Util.DateUtil;
import net.therncway.interfaces.Observer;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

public class HistoryView extends BaseView implements View.OnClickListener, View.OnTouchListener,Observer {
	private LinearLayout layout;
	private ListView lv;
	private EditText etStartTime;
	private EditText etEndTime;
	private EditText etKeyword;
	private EditText etTouch;
	private DatePickerDialog dpd;

	public HistoryView(Context context) {
		super(context);
		layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.view_history, null);
		layout.findViewById(R.id.btn_history_search).setOnClickListener(this);
		etStartTime = (EditText) layout.findViewById(R.id.et_history_starttime);
		etEndTime = (EditText) layout.findViewById(R.id.et_history_endtime);
		etKeyword = (EditText) layout.findViewById(R.id.et_history_keyword);
		etStartTime.setOnTouchListener(this);
		etEndTime.setOnTouchListener(this);
		initDatePicker(context);
		lv = (ListView) layout.findViewById(R.id.lv_history);
		setAdapterAndOnItemClick(lv ,new MyAdapter(context, getCursor()));
		dbHelper.registerObserver(this);
	}

	private void initDatePicker(Context context) {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		int monthOfYear = c.get(Calendar.MONTH);
		int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

		dpd = new DatePickerDialog(context, new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				setEtTime(year, monthOfYear, dayOfMonth);
			}

		}, year, monthOfYear, dayOfMonth);

	}

	long startTime;
	long endTime;

	private void setEtTime(int year, int monthOfYear, int dayOfMonth) {

		final String startPostfix = " 00:00:00";
		final String endPostfix = " 23:59:59";

		String str = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
		etTouch.setText(str);

		str += (etTouch.getId() == R.id.et_history_starttime ? startPostfix : endPostfix);
		long time = 0;
		if ((time = DateUtil.date2second(str)) != 0) {
			if (etTouch.getId() == R.id.et_history_starttime) {
				startTime = time;
			} else {
				endTime = time;
			}
		}

	}

	public LinearLayout asView() {
		return layout;
	}

//	private CursorAdapter getApdater(Context context) {
//
//		CursorAdapter adapter = new CursorAdapter(context, getCursor(), true) {
//
//			@Override
//			public View newView(Context context, Cursor cursor, ViewGroup parent) {
//				return LayoutInflater.from(context).inflate(
//						R.layout.item_infomation, null);
//			}
//
//			@Override
//			public void bindView(View view, Context context, Cursor cursor) {
//				((TextView)(view.findViewById(R.id.tv_info_item))).setText(cursor.getString(cursor.getColumnIndex(AppCons.FILED_NAME_SUBJECT)));
//				((TextView)(view.findViewById(R.id.tv_info_item_index))).setText(cursor.getString(cursor.getColumnIndex(AppCons.FILED_NAME_ID)));
//				((TextView)(view.findViewById(R.id.tv_info_item_time))).setText(DateUtil.second2Date(cursor.getLong(cursor.getColumnIndex(AppCons.FILED_NAME_TIME))));
//				((ImageView)(view.findViewById(R.id.img_info_item))).setImageResource(ImageUtil.getImagId());
//			}
//		};
//
//		return adapter;
//	}

	private Cursor getCursor() {
		ContentValues values = new ContentValues();

		String keyword = etKeyword.getText().toString();
		if (!"".equals(keyword)) {
			values.put(AppCons.KEY_HISTORY_KEYWORD, keyword);
		}

		String startTimeStr = etStartTime.getText().toString();
		String endTimeStr = etEndTime.getText().toString();

		if (!"".equals(startTimeStr) && !"".equals(endTimeStr)) {
			values.put(AppCons.KEY_HISTORY_STARTTIME, startTime);
			values.put(AppCons.KEY_HISTORY_ENDTIME, endTime);
		}

		Cursor cursor = dbHelper.query(values);
		return cursor;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_history_search:
			search();
			break;
		default:
			break;
		}

	}

	private void search() {
		setAdapterAndOnItemClick(lv, new MyAdapter(context, getCursor()));
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			switch (v.getId()) {
			case R.id.et_history_starttime:
				etTouch = (EditText) v;
				dpd.show();
				break;
			case R.id.et_history_endtime:
				etTouch = (EditText) v;
				dpd.show();
				break;

			default:
				break;
			}
		}
		return false;
	}

	@Override
	public void onChange() {
		((MainActivity)context).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				setAdapterAndOnItemClick(lv ,new MyAdapter(context, getCursor()));
			}
			});
		
	}
}
