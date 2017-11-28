package com.example.yia;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class docsignin extends ActionBarActivity {

    SQLiteDatabase db;
    EditText docemail, docpasscode;
    Button btdoclogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_docsignin);

        if (!getServerConfiguration()) {
            finish();
        }

        docemail = (EditText) findViewById(R.id.docemail);
        docemail.setText(clsGlobal.email);

        docpasscode = (EditText) findViewById(R.id.docpasscode);

        btdoclogin = (Button) findViewById(R.id.btdoclogin);
        btdoclogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clsGlobal.email = docemail.getText().toString();
                clsGlobal.passcode = docpasscode.getText().toString();
                new Login().execute();
                //Toast.makeText(getApplicationContext(), "Email: " + clsGlobal.email + " Passcode: " + clsGlobal.passcode , Toast.LENGTH_LONG).show();
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

    class Login extends AsyncTask<String, String, String> {

        String TAG_SUCCESS = "success";
        String TAG_DETAILS = "userdetails";
        String TAG_FIRSTNAME = "firstname";
        String TAG_MIDDLENAME = "middlename";
        String TAG_LASTNAME = "lastname";
        String TAG_EMAIL = "email";
        String TAG_IDPASSPORT = "idpassport";
        String TAG_MOBILE = "mobile";
        String TAG_PASSWORD = "password";

        ProgressDialog pDialog;

        JSONArray userdtails = null;
        JSONParser jParser = new JSONParser();
        public int flag = 0;

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(docsignin.this);
            pDialog.setMessage("Login, Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", clsGlobal.email));
            params.add(new BasicNameValuePair("password", clsGlobal.passcode));

            JSONObject json = jParser.makeHttpRequest(clsGlobal.protocal + clsGlobal.hostname + clsGlobal.url_doclogin, "GET", params);

            Log.d("Client: Details", json.toString());

            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    clsGlobal.firstname = json.getString(TAG_FIRSTNAME);
                    clsGlobal.middlename = json.getString(TAG_MIDDLENAME);
                    clsGlobal.lastname = json.getString(TAG_LASTNAME);
                    clsGlobal.idpassport = json.getString(TAG_IDPASSPORT);
                    clsGlobal.mobile = json.getString(TAG_MOBILE);
                    flag = 1;
                }
            }catch (JSONException e) {
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
                finish();
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Wrong Details", Toast.LENGTH_SHORT).show();
            }
            pDialog.dismiss();
        }
    }
}