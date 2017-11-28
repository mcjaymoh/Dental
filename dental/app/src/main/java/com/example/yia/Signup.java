package com.example.yia;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Build;

public class Signup extends ActionBarActivity {

	SQLiteDatabase db;
	AlertDialog alert2;
	EditText et_surname, et_middlename, et_lastname, et_mobile, et_email,
	et_idpasport, et_password, et_confirmpassword;
	
	Button btCreate, btTerms;
	
	RadioGroup radgender;
	
	String genderchoice = "Male";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_signup);
		
		if (!getServerConfiguration()) {
			finish();
		}
		
		et_surname = (EditText) findViewById(R.id.et_surname);
		et_middlename = (EditText) findViewById(R.id.et_middlename);
		et_lastname = (EditText) findViewById(R.id.et_lastname);
		et_mobile = (EditText) findViewById(R.id.et_mobile);
		et_email = (EditText) findViewById(R.id.et_email);
		et_idpasport = (EditText) findViewById(R.id.et_idpasport);
		et_password = (EditText) findViewById(R.id.et_password);
		et_confirmpassword = (EditText) findViewById(R.id.et_confirmpassword);

		btCreate = (Button) findViewById(R.id.btCreate);
		btCreate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(!et_password.getText().toString().equalsIgnoreCase(et_confirmpassword.getText().toString())){
					Toast.makeText(getApplicationContext(), "Password Miss Match", Toast.LENGTH_LONG).show();
				}else{
					new CreateAccount().execute();
				}
			}

		});
		


		radgender = (RadioGroup) findViewById(R.id.radgender);
		radgender.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(radgender.getCheckedRadioButtonId() == 2131034188){
					genderchoice = "Male";
				}else if(radgender.getCheckedRadioButtonId() == 2131034189){
					genderchoice = "Female";
				}
				Toast.makeText(getApplicationContext(), genderchoice, Toast.LENGTH_SHORT).show();
			}
		});
	}
	public boolean getServerConfiguration() {
		boolean flag = false;
		try {
			db = openOrCreateDatabase("appointment", Context.MODE_PRIVATE, null);
			db.execSQL("CREATE TABLE IF NOT EXISTS serversettings(ID VARCHAR, IP VARCHAR, PORT VARCHAR);");
			Cursor c = db.rawQuery(
					"SELECT * FROM serversettings WHERE ID = '1'", null);
			if (c.moveToFirst()) {
				clsGlobal.hostname = c.getString(1) + ":" + c.getString(2);
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e) {

		}
		return flag;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.signup, menu);
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
	
	public void createTermspopup() {
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View promptView = layoutInflater.inflate(R.layout.termsnconditionspopup, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setView(promptView);
		
		alertDialogBuilder.setCancelable(true).setPositiveButton("CLOSE",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						alert2.dismiss();
					}
				});
		
		alert2 = alertDialogBuilder.create();
		alert2.show();
		
	}
	
	class CreateAccount extends AsyncTask<String, String, String> {

		ProgressDialog pDialog;
		
		JSONParser jsonParser = new JSONParser();
		
		private static final String TAG_SUCCESS = "success";

		public int flag = 0;

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Signup.this);
			pDialog.setMessage("Creating Account for " + et_surname.getText().toString() + ", please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {

			String surname = et_surname.getText().toString();
			String middlename = et_middlename.getText().toString();
			String lastname = et_lastname.getText().toString();
			String email = et_email.getText().toString();
			String mobile = et_mobile.getText().toString();
			String password = et_password.getText().toString();
			String idpassport = et_idpasport.getText().toString();
			String gender = genderchoice;


			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			params.add(new BasicNameValuePair("surname", surname));
			params.add(new BasicNameValuePair("middlename", middlename));
			params.add(new BasicNameValuePair("lastname", lastname));
			params.add(new BasicNameValuePair("email", email));
			params.add(new BasicNameValuePair("password", password));
			params.add(new BasicNameValuePair("idpassport", idpassport));
			params.add(new BasicNameValuePair("mobile", mobile));
			params.add(new BasicNameValuePair("gender", gender));

			JSONObject json = jsonParser.makeHttpRequest(clsGlobal.protocal + clsGlobal.hostname + clsGlobal.url_createaccount, "POST", params);
			
			try {
				int success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					flag = 1;
				} else {
					flag = 0;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			if (flag == 1) {
				clsGlobal.email = et_email.getText().toString();
				Intent i = new Intent(getBaseContext(), Signin.class);
				startActivity(i);
			} else {
				Toast.makeText(getApplicationContext(), "Account NOT Created", Toast.LENGTH_SHORT).show();
			}
			pDialog.dismiss();
		}
	}


}
