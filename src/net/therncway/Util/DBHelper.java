package net.therncway.Util;

import java.util.ArrayList;

import net.therncway.Base.AppCons;
import net.therncway.interfaces.Observer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

	private SQLiteDatabase db;
	private Context context;
	private final String TAG = "DBHelper";
	private static DBHelper dbHelper;
	private DBHelper(Context context) {
		super(context, AppCons.DB_NAME,null,1);
		this.context = context;
		db = getWritableDatabase();
	}

	public static DBHelper getInstance(Context context){
		if(dbHelper == null){
			dbHelper = new DBHelper(context);
		}
		return dbHelper;
	}
	 
	 
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table "+AppCons.TABLE_NAME+" ("+
				AppCons.FILED_NAME_ID+" integer primary key autoincrement,"+
				AppCons.FILED_NAME_SUBJECT+" text,"+
				AppCons.FILED_NAME_CONTENT+" text,"+
				AppCons.FILED_NAME_TIME+" text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	public void insert(String string) {
    	ContentValues values = new ContentValues();
    	String[] str = string.split(";");
    	values.put(AppCons.FILED_NAME_SUBJECT, str[0]);
    	values.put(AppCons.FILED_NAME_CONTENT, str[1]);
    	values.put(AppCons.FILED_NAME_TIME, System.currentTimeMillis());	
    	db.insert(AppCons.TABLE_NAME, null, values);
    	notifyChange();
	}
	
	public Cursor getAll(){
		Cursor c = db.query(
				AppCons.TABLE_NAME, 
				new String[]{AppCons.FILED_NAME_ID,AppCons.FILED_NAME_SUBJECT,AppCons.FILED_NAME_TIME,AppCons.FILED_NAME_CONTENT}, 
				null, 
				null, 
				null, 
				null, 
				AppCons.FILED_NAME_ID + " desc");
		return c;
	}



	public Cursor query(ContentValues values) {
		StringBuffer WHERE = new StringBuffer();
		Object keyword =  values.get(AppCons.KEY_HISTORY_KEYWORD);
		Object startTime =  values.get(AppCons.KEY_HISTORY_STARTTIME);
		Object endTime =  values.get(AppCons.KEY_HISTORY_ENDTIME);
		if(keyword != null){
			WHERE.append("("+AppCons.FILED_NAME_SUBJECT+" like '%"+(String)keyword+"%'");
			WHERE.append(" or "+AppCons.FILED_NAME_CONTENT+" like '%"+(String)keyword+"%')");
		}
		
		if(startTime != null && endTime != null){
			if(keyword != null){
				WHERE.append(" and ");
			}
			WHERE.append("("+AppCons.FILED_NAME_TIME +" > " +((Long)startTime).longValue());
			WHERE.append(" and "+AppCons.FILED_NAME_TIME +" < " +((Long)endTime).longValue()+")");
		}
		
		Cursor c = db.query(
				AppCons.TABLE_NAME, 
				new String[]{AppCons.FILED_NAME_ID,AppCons.FILED_NAME_SUBJECT,AppCons.FILED_NAME_TIME,AppCons.FILED_NAME_CONTENT}, 
				WHERE.toString(), 
				null, 
				null, 
				null, 
				AppCons.FILED_NAME_TIME + " desc");
		return c;
	}
	
	public void deleDB(){
		context.deleteDatabase(AppCons.DB_NAME);
		notifyChange();
	}
	
	private ArrayList<Observer> obList = new ArrayList<Observer>();
	public void registerObserver(Observer ob){
		obList.add(ob);
	}
	
	public void unRegisterObserver(Observer ob){
		obList.remove(ob);
	}
	
	private void notifyChange(){
		Logger.i(TAG, "notifyChange()");
		for(Observer ob: obList){
			ob.onChange();
		}
	}

}
