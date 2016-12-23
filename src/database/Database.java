package database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class Database {
	static String DATABASE_NAME="manager.sqlite";
	static String DB_PATH_SUFFIX="/databases/";
	public static void xuLiSaoChepCSDL(Activity context) {
		
		File dbFile = context.getDatabasePath(DATABASE_NAME);
		if(!dbFile.exists()){
			try{
			CopyDataBaseFromAsset(context);
			Toast.makeText(context, "Sao chep thanh cong", Toast.LENGTH_SHORT).show();
			}catch(Exception e){
				Log.e("LOI", e.toString());
			}
		}		
	}

	private static void CopyDataBaseFromAsset(Activity context) {
		try{
			InputStream myInput=context.getAssets().open(DATABASE_NAME);
			String outFileName=layDuongDanLuuTru(context);
			File f = new File(context.getApplicationInfo().dataDir+DB_PATH_SUFFIX);
			if(!f.exists()){
				f.mkdir();		
			}
			OutputStream myOutput = new FileOutputStream(outFileName);
			byte[] buffer = new byte[1024];
			int length;
			while((length=myInput.read(buffer))>0){
				myOutput.write(buffer);
			}
			myOutput.flush();
			myOutput.close();
			myInput.close();
		}catch(Exception e){
			Log.e("LOI_SAOCHEP", e.toString());
		}
	}
	private static String layDuongDanLuuTru(Activity context){
		return context.getApplicationInfo().dataDir+DB_PATH_SUFFIX+DATABASE_NAME;
	}
	public static String initDatabase(Activity context){
		final String DATABASE = "manager.sqlite";
		File file = context.getDatabasePath(DATABASE);
		if(!file.exists()){
			File folder = new File(context.getApplicationInfo().dataDir+"/databases/");
			if(!folder.exists()){
				folder.mkdir();
			}
			try {
				InputStream is = context.getResources().getAssets().open(DATABASE);
				String output = context.getApplicationInfo().dataDir+"/databases/"+DATABASE;
				byte[] buffer = new byte[1024];
				OutputStream os = context.openFileOutput(output, 0);
				while((is.read(buffer))>0){
					os.write(buffer);
				}
				os.flush();
				os.close();
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return context.getApplicationInfo().dataDir+"/databases/"+DATABASE;
	}
	
	
}
