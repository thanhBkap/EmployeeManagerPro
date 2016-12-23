package vn.com.nst.adapter;

import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import database.Database;
import vn.com.nst.employeemanagerpro.MainActivity;
import vn.com.nst.employeemanagerpro.R;
import vn.com.nst.employeemanagerpro.UpdateActivity;
import vn.com.nst.model.Employee;

@SuppressLint("ViewHolder")
public class EmployeeAdapter extends ArrayAdapter<Employee> {
	Activity context;
	int resource;
	List<Employee> objects;
	String DATABASE = "manager.sqlite";

	public EmployeeAdapter(Activity context, int resource, List<Employee> objects) {
		super(context, resource, objects);
		this.context = context;
		this.resource = resource;
		this.objects = objects;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		TextView txtName, txtPhone;
		Button btnDelete, btnUpdate;
		ImageView imgAvarta;
		final Employee e = objects.get(position);
		// e=objects.get(position);
		EmployeeHolder employeeHolder = new EmployeeHolder(context);
		LayoutInflater inflate = context.getLayoutInflater();
		View row = inflate.inflate(resource, null);
		btnDelete = (Button) row.findViewById(R.id.btnDelete);
		btnUpdate = (Button) row.findViewById(R.id.btnUpdate);
		imgAvarta = (ImageView) row.findViewById(R.id.imgAvarta);
		txtName = (TextView) row.findViewById(R.id.txtName);
		txtPhone = (TextView) row.findViewById(R.id.txtPhone);
		btnDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				xuLyXoa();
			}

			private void xuLyXoa() {
				objects.remove(position);
				notifyDataSetChanged();
				Database.xuLiSaoChepCSDL(context);
				SQLiteDatabase db = context.openOrCreateDatabase(DATABASE, context.MODE_PRIVATE, null);
				db.delete("employee", "id = ?", new String[]{""+e.getId()});
			}
		});

		btnUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				xuLyUpdate();
			}

			private void xuLyUpdate() {
				Intent intent = new Intent(context, UpdateActivity.class);
				intent.putExtra("ID", e.getId());
				intent.putExtra("NAME", e.getName());
				intent.putExtra("PHONE", e.getPhoneNumber());
				intent.putExtra("ANH", e.getAvarta());
				context.startActivity(intent);
			}

		});
		txtName.setText(e.getName().toString());
		txtPhone.setText(e.getPhoneNumber());
		imgAvarta.setImageBitmap(tuHinhsangMangByte(e.getAvarta()));
		;
		return row;
	}

	private Bitmap tuHinhsangMangByte(byte[] img) {
		Bitmap anh = null;
		anh = BitmapFactory.decodeByteArray(img, 0, img.length);
		return anh;
	}

	class EmployeeHolder extends ViewGroup {
		public EmployeeHolder(Activity context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		TextView txtName, txtPhone;
		Button btnDelete, btnUpdate;
		ImageView imgAvarta;

		@Override
		protected void onLayout(boolean changed, int l, int t, int r, int b) {

		}

	}
}
