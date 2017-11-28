package com.example.yia;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class book extends ActionBarActivity {

    SQLiteDatabase db;
    AlertDialog alert2;
    EditText et_names, et_number, et_emali, et_reason;

    Button btsubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_book);

        if (!getServerConfiguration()) {
            finish();
        }

        et_names = (EditText) findViewById(R.id.et_names);
        et_number = (EditText) findViewById(R.id.et_number);
        et_emali = (EditText) findViewById(R.id.et_emali);
        et_reason = (EditText) findViewById(R.id.et_reason);

        btsubmit = (Button) findViewById(R.id.btsubmit);
        btsubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Submitappointment().execute();
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

    class Submitappointment extends AsyncTask<String, String, String> {

        ProgressDialog pDialog;

        JSONParser jsonParser = new JSONParser();

        private static final String TAG_SUCCESS = "success";

        public int flag = 0;

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(book.this);
            pDialog.setMessage("Booking Appointment for " + et_names.getText().toString() + ", please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
            String title = et_names.getText().toString();
            String category = et_number.getText().toString();
            String description = et_reason.getText().toString();
            String department = et_emali.getText().toString();


            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("title", title));
            params.add(new BasicNameValuePair("category", category));
            params.add(new BasicNameValuePair("description", description));
            params.add(new BasicNameValuePair("department", department));


            JSONObject json = jsonParser.makeHttpRequest(clsGlobal.protocal + clsGlobal.hostname + clsGlobal.url_appointment, "POST", params);

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
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            if (flag == 1) {

                Intent i = new Intent(getBaseContext(), userhome.class);
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), "Appointment not booked", Toast.LENGTH_SHORT).show();
            }
            pDialog.dismiss();
        }
    }
}