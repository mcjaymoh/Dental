package com.example.yia;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.os.Build;

public class Splashscreen extends ActionBarActivity {
	SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splashscreen);

		Thread background = new Thread() {
			public void run() {

				try {
					// Thread will sleep for 5 seconds
					sleep(2 * 1000);

					// After 5 seconds redirect to another intent
					if(checkInstallation()){
						Intent i = new Intent(getBaseContext(), Signin.class);
						startActivity(i);
					}
					else{
						Intent i = new Intent(getBaseContext(), Serverconfigration.class);
						startActivity(i);
					}
					// Remove activity
					finish();

				} catch (Exception e) {

				}
			}
		};

		// start thread
		background.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splashscreen, menu);
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

	@Override
	public void onBackPressed() {
	}
	
	public boolean checkInstallation() {
		boolean flag = false;
		try {
			db = openOrCreateDatabase("appointment", Context.MODE_PRIVATE, null);
			db.execSQL("CREATE TABLE IF NOT EXISTS serversettings(ID VARCHAR, IP VARCHAR, PORT VARCHAR);");
			Cursor c = db.rawQuery(
					"SELECT * FROM serversettings WHERE ID = '1'", null);
			if (c.moveToFirst()) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e) {
			
		}
		return flag;
	}
}
