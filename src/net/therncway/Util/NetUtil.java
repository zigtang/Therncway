package net.therncway.Util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.http.client.HttpClient;

import android.os.AsyncTask;

public class NetUtil {
	
	static boolean flag = false;
	public static boolean testDest(final String desName,final int desPort){

		Logger.d("NetUtil", "address:"+desName+" port:"+desPort);
		
		new AsyncTask<Void, Void, Void>(){

			@Override
			protected Void doInBackground(Void... params) {
				Socket socket = null;
				try {
					socket = new Socket(desName, desPort);
					socket.connect(new InetSocketAddress(desName, desPort),1000);
					flag = socket.isConnected();
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return null;
			}
		};
		
		
		
		
		Logger.d("NetUtil", "socket.isConnected():"+flag);
		
		return flag;
	}
}
