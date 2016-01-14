package com.edgardeng.baseandroid.receiver;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.edgardeng.baseandroid.R;
import com.edgardeng.util.FileUtils;

/**
 * BroadcastReceiver for Update APK downloaded to install ;
 */
@SuppressLint("NewApi")
public class UpdateReceiver extends BroadcastReceiver {

	private DownloadManager downloadManager;
	private String path_apk = null;// apk file download path;

	@Override
	public void onReceive(Context context, Intent intent) {
  
		String action = intent.getAction();
		if(action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {

			long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
			Query query = new Query();
			query.setFilterById(id);
			downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
			Cursor cursor = downloadManager.query(query);			
   
			int columnCount = cursor.getColumnCount();

			while(cursor.moveToNext()) {
			    for (int j = 0; j < columnCount; j++) {
			    	String columnName = cursor.getColumnName(j);
			    	String string = cursor.getString(j);
			    	if(columnName.equals("local_filename")) {
			    		path_apk = string; // after   android 4.0
			    	}
			    	else if(columnName.equals("download_path")){
			    		path_apk = string;  //befor android 2.3
			    	}
			    }
			}
			cursor.close();

	  		// get apk  from  sdcard
	  		if(path_apk!=null && path_apk.startsWith("content:")) {
				cursor = context.getContentResolver().query(Uri.parse(path_apk), null, null, null, null);
				columnCount = cursor.getColumnCount();
				while(cursor.moveToNext()) {
	              for (int j = 0; j < columnCount; j++) {
	                    String columnName = cursor.getColumnName(j);
	                    String string = cursor.getString(j);
	                    if(string != null) {
	                        System.out.println(columnName+":  : "+ string);
	                    }else {
	                    	System.out.println(columnName+": null");
	                    }
	              }
				}
				cursor.close();
			}

	    if(path_apk != null){
	    	FileUtils.chmod("777", path_apk);
	    	Intent intent_apk = new Intent();
	    	intent_apk.setAction(android.content.Intent.ACTION_VIEW);
			intent_apk.setDataAndType(Uri.fromFile(new File(path_apk)), "application/vnd.android.package-archive");
	    	intent_apk.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    	context.startActivity(intent_apk);
	    }else
	    	Toast.makeText(context,context.getString(R.string.download_fail), Toast.LENGTH_SHORT).show();
	   }
		else if(action.equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
	  }
		
	}
	
	/**
	 *
	 * @param c
	 * @param filename
	 * @param url
	 * @date 2014-11-6
	 */
	public static void download_apk(Context c,String filename,String url){
		if(url == null) return;
//		String apk_name = update_hashmap.get(NAME)+".apk";
		try{
			String folder = Environment.getExternalStorageDirectory()+File.separator+Environment.DIRECTORY_DOWNLOADS;
			File folder_file = new File(folder);
			if(!folder_file.exists())
				folder_file.mkdirs();
			
			String path = folder+File.separator+filename;
			File apk = new File(path);
			if((apk.exists())){
				apk.delete();
			}
	
			DownloadManager downloadManager = (DownloadManager) c.getSystemService(Context.DOWNLOAD_SERVICE);		  
			Uri uri = Uri.parse(url);
			Request request = new Request(uri);
			request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE|DownloadManager.Request.NETWORK_WIFI); 
			request.setVisibleInDownloadsUi(false);
			request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,filename);
			long id = downloadManager.enqueue(request);
			Toast.makeText(c,c.getString(R.string.update_download), Toast.LENGTH_SHORT).show();

		}catch(Exception e){
			e.printStackTrace();
		}	
	}

}

