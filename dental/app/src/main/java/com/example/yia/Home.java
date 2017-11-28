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
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class Home extends ActionBarActivity {

	TextView tv_clientfullnames, tv_clientemail;
	Spinner spinnerOne;
	ListView listViewOne;
	TextView tv_nodata;
	LinearLayout llnodata, llfounddata;
	Button btn_search;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		llnodata = (LinearLayout) findViewById(R.id.llnodata);
		llfounddata = (LinearLayout) findViewById(R.id.llfounddata);

		tv_nodata = (TextView) findViewById(R.id.tv_nodata);
		
		tv_clientfullnames = (TextView) findViewById(R.id.tv_clientfullnames);
		tv_clientfullnames.setText(clsGlobal.firstname + ", " + clsGlobal.middlename + " " + clsGlobal.lastname);
		
		tv_clientemail = (TextView) findViewById(R.id.tv_clientemail);
		tv_clientemail.setText(clsGlobal.email);
		
		spinnerOne = (Spinner) findViewById(R.id.spinnerOne);
		
		new Loadinvestmentdepartments().execute();
		
		listViewOne = (ListView) findViewById(R.id.listViewOne);
		listViewOne.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				clsGlobal.investmenttitle = ((TextView) view.findViewById(R.id.tv_investmenttitle)).getText().toString();
				clsGlobal.investmentcategory = ((TextView) view.findViewById(R.id.tv_investmentcategory)).getText().toString();
				clsGlobal.investmentid = ((TextView) view.findViewById(R.id.tv_investmentid)).getText().toString();
				clsGlobal.investmentid = ((TextView) view.findViewById(R.id.tv_investmentid)).getText().toString();
				//finish();
				Intent intent = new Intent(Home.this, Investmentdetails.class);
				startActivity(intent);	
			}
		});
		new Gettopinvestments().execute();

		btn_search = (Button) findViewById(R.id.btn_search);
		btn_search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new Getspecificinvestments().execute();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
	
		if (id == R.id.home_logout) {
			closePrompt();
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setTitle("Alert")
				.setMessage("Logout?")
				.setPositiveButton("YES",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								System.exit(0);
							}

						}).setNegativeButton("NO", null).show();
	}
	
	public void closePrompt() {
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setTitle("Alert")
				.setMessage("Logout?")
				.setPositiveButton("YES",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								System.exit(0);
							}

						}).setNegativeButton("NO", null).show();
	}
	
	class Loadinvestmentdepartments extends AsyncTask<String, String, String> {

		Stack<String> allDepartments = new Stack<String>();
		int count = 0;

		JSONArray myDepartments;
		
		ProgressDialog pDialog;
		
		String TAG_SUCCESS = "success";
		String DEPARTMENTS = "departmentdetails";
		String DEPARTMENTNAME = "department";

		JSONParser jParser = new JSONParser();

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Home.this);
			pDialog.setMessage("Loading, Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * getting All Updates from url
		 * */
		protected String doInBackground(String... args) {
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject json = jParser.makeHttpRequest(clsGlobal.protocal + clsGlobal.hostname + clsGlobal.url_getinvestimentdepartments, "GET", params);

			Log.d("All Departments: ", json.toString());

			try {
				int success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					myDepartments = json.getJSONArray(DEPARTMENTS);
					for (int i = 0; i < myDepartments.length(); i++) {
						JSONObject c = myDepartments.getJSONObject(i);
						String country_name = c.getString(DEPARTMENTNAME);
						//String location_id = c.getString(TAG_LOCATIONID);
						allDepartments.push(country_name);
						count++;
					}
				} else {
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();

			if (count > 0) {
				ArrayAdapter<String> dtpAdapter = new ArrayAdapter<String>(Home.this, android.R.layout.simple_spinner_item, allDepartments);
				dtpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerOne.setAdapter(dtpAdapter);

			} else {
				
			}
		}
	}
	
	class Gettopinvestments extends AsyncTask<String, String, String> {

		String phptext;
		String[] investmenttitles, investmentcategories, investmentids, investmentdetails;
		int count = 0;

		JSONArray myInvestments;

		ProgressDialog pDialog;

		String SUCCESS = "success";
		String MESSAGE = "message";
		
		String INVESTMESNTS = "investments";
		String INVESTMESNTID = "id";
		String INVESTMESNTTITLE = "title";
		String INVESTMESNTCATEGORY = "category";
		String INVESTMESNTDETAILS = "description";

		JSONParser jParser = new JSONParser();

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Home.this);
			pDialog.setMessage("Loading, Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * getting All Updates from url
		 * */
		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject json = jParser.makeHttpRequest(clsGlobal.protocal + clsGlobal.hostname + clsGlobal.url_gettopinvestiments, "GET", params);
			try {
				int success = json.getInt(SUCCESS);
				if (success == 1) {
					myInvestments = json.getJSONArray(INVESTMESNTS);

					investmenttitles = new String[myInvestments.length()];
					investmentcategories = new String[myInvestments.length()];
					investmentids = new String[myInvestments.length()];
					investmentdetails = new String[myInvestments.length()];

					for (int i = 0; i < myInvestments.length(); i++) {
						JSONObject c = myInvestments.getJSONObject(i);
						investmenttitles[i] = c.getString(INVESTMESNTTITLE);
						investmentcategories[i] = c.getString(INVESTMESNTCATEGORY);
						investmentids[i] = c.getString(INVESTMESNTID);
						investmentdetails[i] = c.getString(INVESTMESNTDETAILS);
						
						count++;
					}
				} else {
					count = 0;
				}
				phptext = json.getString(MESSAGE);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			if (count > 0) {
				InvestmentAdapter adapter = new InvestmentAdapter(getApplicationContext(), investmenttitles, investmentcategories, investmentids, investmentdetails);
				listViewOne.setAdapter(adapter);
				
				llfounddata.setVisibility(View.VISIBLE);
				llnodata.setVisibility(View.GONE);
			} else {
				llfounddata.setVisibility(View.GONE);
				llnodata.setVisibility(View.VISIBLE);
			}
			tv_nodata.setText(phptext);
			pDialog.dismiss();
		}
	}
	
	class Getspecificinvestments extends AsyncTask<String, String, String> {

		String phptext;
		String[] investmenttitles, investmentcategories, investmentids, investmentdetails;
		int count = 0;

		JSONArray myInvestments;

		ProgressDialog pDialog;

		String SUCCESS = "success";
		String MESSAGE = "message";
		
		String INVESTMESNTS = "investments";
		String INVESTMESNTID = "id";
		String INVESTMESNTTITLE = "title";
		String INVESTMESNTCATEGORY = "category";
		String INVESTMESNTDETAILS = "description";

		JSONParser jParser = new JSONParser();

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Home.this);
			pDialog.setMessage("Loading, Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * getting All Updates from url
		 * */
		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("department", spinnerOne.getSelectedItem().toString()));
			JSONObject json = jParser.makeHttpRequest(clsGlobal.protocal + clsGlobal.hostname + clsGlobal.url_getspecifiinvestiments, "GET", params);
			try {
				int success = json.getInt(SUCCESS);
				if (success == 1) {
					myInvestments = json.getJSONArray(INVESTMESNTS);

					investmenttitles = new String[myInvestments.length()];
					investmentcategories = new String[myInvestments.length()];
					investmentids = new String[myInvestments.length()];
					investmentdetails = new String[myInvestments.length()];

					for (int i = 0; i < myInvestments.length(); i++) {
						JSONObject c = myInvestments.getJSONObject(i);
						investmenttitles[i] = c.getString(INVESTMESNTTITLE);
						investmentcategories[i] = c.getString(INVESTMESNTCATEGORY);
						investmentids[i] = c.getString(INVESTMESNTID);
						investmentdetails[i] = c.getString(INVESTMESNTDETAILS);
						
						count++;
					}
				} else {
					count = 0;
				}
				phptext = json.getString(MESSAGE);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			if (count > 0) {
				InvestmentAdapter adapter = new InvestmentAdapter(getApplicationContext(), investmenttitles, investmentcategories, investmentids, investmentdetails);
				listViewOne.setAdapter(adapter);
				
				llfounddata.setVisibility(View.VISIBLE);
				llnodata.setVisibility(View.GONE);
			} else {
				llfounddata.setVisibility(View.GONE);
				llnodata.setVisibility(View.VISIBLE);
			}
			tv_nodata.setText(phptext);
			pDialog.dismiss();
		}
	}
}
