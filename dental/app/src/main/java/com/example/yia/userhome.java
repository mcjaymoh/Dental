package com.example.yia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class userhome extends ActionBarActivity {

    Button btview, btbook, btnLogout;
    TextView tv_clientnames, tv_clientmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_userhome);

        tv_clientnames = (TextView) findViewById(R.id.tv_clientnames);
        tv_clientnames.setText(clsGlobal.firstname + ", " + clsGlobal.middlename + " " + clsGlobal.lastname);

        tv_clientmail = (TextView) findViewById(R.id.tv_clientmail);
        tv_clientmail.setText(clsGlobal.email);


        btview = (Button) findViewById(R.id.btview);
        btview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), appointments.class);
                startActivity(intent);

            }
        });

        btbook = (Button) findViewById(R.id.btbook);
        btbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), book.class);
                startActivity(intent1);

            }

        });
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View view) {
                 System.exit(0);
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


}
