package vn.com.nst.employeemanagerpro;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import database.Database;
import vn.com.nst.model.Employee;

public class UpdateActivity extends Activity {
	ImageView imgAvarta;
	Button btnCapture, btnChoose, btnSave, btnCancel;
	EditText txtName, txtPhone;
	int id = -1;
	SQLiteDatabase db;
	String DATABASE_NAME = "manager.sqlite";
	int REQUEST_TAKE_PICTURE = 2016;
	int REQUEST_CHOOSE_PHOTO = 2017;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
		addControls();
		addEvents();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==RESULT_OK){
			if(requestCode==REQUEST_TAKE_PICTURE){
				Bitmap bm=(Bitmap)data.getExtras().get("data");
				imgAvarta.setImageBitmap(bm);
			}else if(requestCode==REQUEST_CHOOSE_PHOTO){
				Uri uri = data.getData();
				imgAvarta.setImageURI(uri);
			}			
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	private void addEvents() {
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				xuLyHuy();
			}

			private void xuLyHuy() {
				Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
		btnCapture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				xuLyChup();
			}

			private void xuLyChup() {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				if(intent.resolveActivity(getPackageManager())!=null){
					startActivityForResult(intent, REQUEST_TAKE_PICTURE);
				}
			}
		});
		btnChoose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				xuLyChon();
			}

			private void xuLyChon() {
				Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
				startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
				/*intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Select picture"), REQUEST_CHOOSE_PHOTO);*/				
			}
		});
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				xuLyLuu();
			}

			private void xuLyLuu() {
				String name;
				String phone;
				byte[] anh;
				name = txtName.getText().toString();
				phone = txtPhone.getText().toString();
				anh = tuHinhsangMangByte(imgAvarta);
				ContentValues row = new ContentValues();
				row.put("name", name);
				row.put("phoneNumber", phone);
				row.put("avarta", anh);
				db.update("employee", row, "id = ?", new String[] { id + "" });
				Toast.makeText(UpdateActivity.this, "Update successfully", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
	}

	private byte[] tuHinhsangMangByte(ImageView img) {
		byte[] anh = null;
		Bitmap bm = ((BitmapDrawable) (img.getDrawable())).getBitmap();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		anh = baos.toByteArray();
		return anh;
	}

	private void addControls() {
		imgAvarta = (ImageView) findViewById(R.id.imgAvartaUpdate);
		txtName = (EditText) findViewById(R.id.txtNameUpdate);
		txtPhone = (EditText) findViewById(R.id.txtPhoneUpdate);
		btnCancel = (Button) findViewById(R.id.btnCancelUpdate);
		btnCapture = (Button) findViewById(R.id.btnCaptureUpdate);
		btnChoose = (Button) findViewById(R.id.btnChooseUpdate);
		btnSave = (Button) findViewById(R.id.btnSaveUpdate);
		Intent intent = getIntent();
		id = intent.getIntExtra("ID", -1);
		Database.xuLiSaoChepCSDL(UpdateActivity.this);
		db = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
		Cursor cursor = db.rawQuery("SELECT * FROM employee WHERE id = ?", new String[] { "" + id });
		cursor.moveToNext();
		txtName.setText(cursor.getString(1));
		txtPhone.setText(cursor.getString(2));
		Bitmap bm = BitmapFactory.decodeByteArray(cursor.getBlob(3), 0, cursor.getBlob(3).length);
		imgAvarta.setImageBitmap(bm);
		/*
		 * txtName.setText(intent.getCharArrayExtra("NAME").toString());
		 * txtPhone.setText(intent.getCharArrayExtra("PHONE").toString());
		 * imgAvarta.setImageBitmap(tuHinhsangMangByte(intent.getByteArrayExtra(
		 * "ANH")));
		 */

	}

}
