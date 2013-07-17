package net.therncway.Base;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by zig on 13-7-9.
 */
public class BaseActivity extends Activity{
    protected final String TAG = this.getClass().getName();

    public void showToast(String str){
        Toast.makeText(this,str,Toast.LENGTH_LONG).show();
    }
}
