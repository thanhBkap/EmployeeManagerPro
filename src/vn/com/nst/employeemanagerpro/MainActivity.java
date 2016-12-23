package vn.com.nst.employeemanagerpro;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import database.Database;
import vn.com.nst.adapter.EmployeeAdapter;
import vn.com.nst.model.Employee;

public class MainActivity extends Activity {
	Button btnAdd;
	ListView lvNhanVien;
	ArrayList<Employee> listNV=null;
	EmployeeAdapter eAdapter;
	static String DATABASE = "manager.sqlite";
	static SQLiteDatabase database;
	String path;
	Database db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addControls();
		addEvents();
	}
	@Override
	protected void onResume() {
		addControls();
		addEvents();
		super.onResume();
	}
	private void addEvents() {
		btnAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				xuLyAdd();
			}

			private void xuLyAdd() {
				Intent intent= new Intent(MainActivity.this, AddActivity.class);
				startActivity(intent);
			}
		});
	}

	private void addControls() {
		btnAdd = (Button) findViewById(R.id.btnAdd);
		lvNhanVien = (ListView) findViewById(R.id.lvNhanVien);
		if(listNV!=null){
			listNV.clear();
		}else{
			listNV = new ArrayList<Employee>();
		}
		/*
		 * listNV.add(new Employee(119, "Obama", "0988888888", null));
		 * listNV.add(new Employee(122, "Obama", "0976874523", null));
		 * listNV.add(new Employee(188, "Obama", "0988886666", null));
		 * listNV.add(new Employee(483, "DonaldTrump", "0164566666", null));
		 */
		/*
		 * path = Database.initDatabase(MainActivity.this); database =
		 * SQLiteDatabase.openOrCreateDatabase(path, null); Cursor cursor =
		 * database.rawQuery("SELECT * FROM employee", null);
		 * cursor.moveToFirst(); for(int i = 0;
		 * i<cursor.getExtras().size();i++){ int id = cursor.getInt(0); String
		 * name = cursor.getString(1); String phone = cursor.getString(2);
		 * byte[] img=(byte[]) cursor.getBlob(3); listNV.add(new Employee(id,
		 * name, phone, img)); }
		 */
		Database.xuLiSaoChepCSDL(MainActivity.this);
		// database = SQLiteDatabase.openOrCreateDatabase(DATABASE, null);
		database = openOrCreateDatabase(DATABASE, MODE_PRIVATE, null);

		Cursor cursor = database.rawQuery("SELECT * FROM employee", null);
		/*
		 * cursor.moveToFirst(); for(int i = 0;
		 * i<cursor.getExtras().size();i++){ int id = cursor.getInt(0); String
		 * name = cursor.getString(1); String phone = cursor.getString(2);
		 * byte[] img=(byte[]) cursor.getBlob(3); listNV.add(new Employee(id,
		 * name, phone, img));
		 */
		while (cursor.moveToNext()) {
			int id = cursor.getInt(0);
			String name = cursor.getString(1);
			String phone = cursor.getString(2);
			byte[] img = (byte[]) cursor.getBlob(3);
			listNV.add(new Employee(id, name, phone, img));
		}
		eAdapter = new EmployeeAdapter(MainActivity.this, R.layout.item, listNV);
		lvNhanVien.setAdapter(eAdapter);
	}

	
}
