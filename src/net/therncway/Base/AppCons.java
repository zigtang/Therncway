package net.therncway.Base;

import net.therncway.R;
import android.net.Uri;

/**
 * Created by zig on 13-7-9.
 */
public class AppCons {
    /**MSG */
    public static final int MSG_SHOW_EVENT = 0x001;
    public static final int MSG_EXCEPTION_IO = 0x002;
    public static final int MSG_EXCEPTION_UNKNOWHOST = 0x003;
    public static final int MSG_CONNECTION_INTERUPTED = 0x004;


    public static final String END = "-END";
    public static final String KEY_MESSAGE = "message";
    
    /* DB */
    
     public static final String DB_NAME = "therncway";
     public static final String TABLE_NAME = "msg_table";
	 public static final String FILED_NAME_ID = "_id";
	 public static final String  FILED_NAME_SUBJECT= "subject";
	 public static final String  FILED_NAME_CONTENT= "content";
	 public static final String  FILED_NAME_TIME= "time";
	 	 
	 
	 /**SharedPreference*/
	 
	 
	 /**History Serch key*/
	 public static final String KEY_HISTORY_KEYWORD = "keyword";
	 public static final String KEY_HISTORY_STARTTIME = "startTime";
	 public static final String KEY_HISTORY_ENDTIME = "endtime";
	 
	 /**flag*/
	 public static final String FLAG_SET_ADDRESS = "setAddress";

	 
	 /**Setting preference*/
	 public static final String PREFERENS_NAME_SETTING = "setting_preference";
	 public static final String SETTING_SOUND = "isSoundOn";
	 public static final String SETTING_NOTIFICATION = "isNotification";
	 public static final String SETTING_MASKWORD = "maskWord";

	 public static final String DEFAULT_VALUE_ADDRESS = "www.therncway.net";
	 public static final  int DEFAULT_VALUE_PORT = 900;
	 public static final String SETTING_ADDRESS = "address";
	 public static final String SETTING_PORT = "port";
	 
	 public static final String SETTING_IS_FROM_TEST = "isFromTest";
	 public static final boolean DEFAULT_VALUE_IS_FROM_TEST = false;

	 
	 public static final  int REQUEST_CODE_MASKWORD = 0x005;
	 
}
