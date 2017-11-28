package com.example.yia;

import java.util.ArrayList;
import java.util.List;

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class Investmentdetails extends ActionBarActivity {

	TextView tv_investmenttitle, tv_investmentcategory, tv_investmentdetails,
	tv_investmentideascount;
	LinearLayout llnodata, llfounddata;
	TextView tv_nodata;

	ListView ls_ideas_popup;
	
	AlertDialog alert, alert2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_investmentdetails);

		llnodata = (LinearLayout) findViewById(R.id.llnodata);
		llfounddata = (LinearLayout) findViewById(R.id.llfounddata);

		tv_nodata = (TextView) findViewById(R.id.tv_nodata);

		tv_investmenttitle = (TextView) findViewById(R.id.tv_investmenttitle);
		tv_investmentcategory = (TextView) findViewById(R.id.tv_investmentcategory);
		tv_investmentdetails = (TextView) findViewById(R.id.tv_investmentdetails);

		tv_investmentideascount = (TextView) findViewById(R.id.tv_investmentideascount);
		tv_investmentideascount.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(tv_investmentideascount.getText().toString().equalsIgnoreCase("There are (0 ideas) on this.")){
					Toast.makeText(getApplicationContext(), "O ideas in this.", Toast.LENGTH_LONG).show();
				}else{
					new Getinvestmentideas().execute();
					createPopup(tv_investmenttitle.getText().toString());
				}
			}
		});
		new Getinvestmentdetails().execute();
	}
	
	public void createPopup(String investmenttitle) {
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View promptView = layoutInflater.inflate(R.layout.popup_ideas, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setView(promptView);

		final TextView investmenttitle_popup = (TextView) promptView.findViewById(R.id.investmenttitle_popup);
		investmenttitle_popup.setText(investmenttitle);
		
		ls_ideas_popup = (ListView) promptView.findViewById(R.id.ls_ideas_popup);
		
		
		
		alertDialogBuilder.setCancelable(true).setPositiveButton("CLOSE",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						
					}
				});

		alert2 = alertDialogBuilder.create();
		alert2.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.investmentdetails, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.investmentdetails_refresh) {
			new Getinvestmentdetails().execute();
		}
		return super.onOptionsItemSelected(item);
	}

	class Getinvestmentdetails extends AsyncTask<String, String, String> {

		String phptext, ideascount;

		String SUCCESS = "success";
		String MESSAGE = "message";

		String DETAILS = "description";
		String IDEASCOUNT = "ideascount";

		ProgressDialog pDialog;
		JSONParser jParser = new JSONParser();
		int flag;

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Investmentdetails.this);
			pDialog.setMessage("Loading, Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			// pDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("investmentid",
					clsGlobal.investmentid));
			JSONObject json = jParser.makeHttpRequest(clsGlobal.protocal
					+ clsGlobal.hostname + clsGlobal.url_getinvestmentdetails,
					"GET", params);

			Log.d("Details: ", json.toString());

			try {
				int success = json.getInt(SUCCESS);
				if (success == 1) {
					flag = 1;
					clsGlobal.investmentdetails = json.getString(DETAILS);
					ideascount = json.getString(IDEASCOUNT);
				} else {
					flag = 0;
				}
				phptext = json.getString(MESSAGE);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// Toast.makeText(getApplicationContext(), ""+flag,
			// Toast.LENGTH_LONG).show();
			if (flag == 1) {
				tv_investmenttitle.setText(clsGlobal.investmenttitle);
				tv_investmentcategory.setText(clsGlobal.investmentcategory);
				tv_investmentdetails.setText(clsGlobal.investmentdetails);

				tv_investmentideascount.setText(ideascount);

				llfounddata.setVisibility(View.VISIBLE);
				llnodata.setVisibility(View.GONE);
			} else {
				llfounddata.setVisibility(View.GONE);
				llnodata.setVisibility(View.VISIBLE);
			}

			tv_nodata.setText(phptext);
		}
	}
	
	class Getinvestmentideas extends AsyncTask<String, String, String> {

		String phptext;
		String[] ideaids, ideatitles, ideadetails;
		int count = 0;

		JSONArray myIdeas;

		ProgressDialog pDialog;

		String SUCCESS = "success";
		String MESSAGE = "message";
		
		String IDEAS = "ideas";
		String IDEAID = "ideaid";
		String IDEATITLE = "title";
		String IDEADETAILS = "details";

		JSONParser jParser = new JSONParser();

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Investmentdetails.this);
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
			params.add(new BasicNameValuePair("investmentid", clsGlobal.investmentid));
			JSONObject json = jParser.makeHttpRequest(clsGlobal.protocal + clsGlobal.hostname + clsGlobal.url_getinvestmentideas, "GET", params);
			try {
				int success = json.getInt(SUCCESS);
				if (success == 1) {
					myIdeas = json.getJSONArray(IDEAS);

					ideaids = new String[myIdeas.length()];
					ideatitles = new String[myIdeas.length()];
					ideadetails = new String[myIdeas.length()];

					for (int i = 0; i < myIdeas.length(); i++) {
						JSONObject c = myIdeas.getJSONObject(i);
						ideaids[i] = c.getString(IDEAID);
						ideatitles[i] = c.getString(IDEATITLE);
						ideadetails[i] = c.getString(IDEADETAILS);
						
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
				IdeasAdapter adapter = new IdeasAdapter(Investmentdetails.this, ideaids, ideatitles, ideadetails);
				ls_ideas_popup.setAdapter(adapter);
			} else {
				Toast.makeText(getApplicationContext(), "O ideas in this.", Toast.LENGTH_LONG).show();
			}
			pDialog.dismiss();
		}
	}
}
