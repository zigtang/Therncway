package net.therncway.Util;

import android.util.Log;

/**
 * Created by zig on 13-7-9.
 */
public class Logger {
    private static boolean isDebug = true;

    public static void i(String tag,String msg){
        if(isDebug){
            Log.i(tag,msg);
        }
    }

    public static void d(String tag,String msg){
        if(isDebug){
            Log.d(tag,msg);
        }
    }

    public static void w(String tag,String msg){
        if(isDebug){
            Log.w(tag,msg);
        }
    }

    public static void e(String tag,String msg){
        if(isDebug){
            Log.e(tag,msg);
        }
    }

}
