package com.example.yia;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class Serverconfigration extends ActionBarActivity {

	Button btnext;
	TextView tvtitle, tvinfo;
	EditText edipaddress, edportnumber;
	String ip = "";
	String port = "";
	
	SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_serverconfigration);

		tvtitle = (TextView) findViewById(R.id.tvservicetime);
		//Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Neou-Bold_0.ttf");
		//tvtitle.setTypeface(tf);
		
		tvinfo = (TextView) findViewById(R.id.tvinfo);
		//Typeface tf2 = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/COMIC.TTF");
		//tvinfo.setTypeface(tf2);
		
		edipaddress = (EditText) findViewById(R.id.edipaddress);
		edportnumber = (EditText) findViewById(R.id.edportnumber);
		//Typeface tf3 = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/COURBD.TTF");
		//edipaddress.setTypeface(tf3);
		//edportnumber.setTypeface(tf3);
		
		btnext = (Button) findViewById(R.id.btnextserverconfiguration);
		btnext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				ip = edipaddress.getText().toString();
				port = edportnumber.getText().toString();
				
				clsGlobal.hostname = ip + ":" + port;
				if(saveSetting()){
					Intent intent = new Intent(Serverconfigration.this, Signin.class);
					startActivity(intent);
				}else{
					Toast.makeText(getApplicationContext(), "Configuration Not Set", Toast.LENGTH_LONG).show();
				}
				
			}
		});
	}

	public boolean saveSetting() {
		try {
			db = openOrCreateDatabase("appointment", Context.MODE_PRIVATE, null);
			db.execSQL("CREATE TABLE IF NOT EXISTS serversettings(ID VARCHAR, IP VARCHAR, PORT VARCHAR);");
			db.execSQL("INSERT INTO serversettings VALUES('1', '" + ip + "', '"
					+ port + "');");

			Toast.makeText(getApplicationContext(), "Configuration set to " + clsGlobal.hostname,
					Toast.LENGTH_LONG).show();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.serverconfigration, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
