package com.example.transmittal_system_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    DBAccess dbAccess;

    EditText txtEmpID, txtPassword;

    Button btnSignin;

    String empid, fullname,deptcode,sectcode;

    ClsUtils clsUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmpID = (EditText) findViewById(R.id.txtEmpID);

        txtPassword = (EditText) findViewById(R.id.txtPassword);

        btnSignin = (Button) findViewById(R.id.btnSignin);

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckLogin checkLogin = new CheckLogin();
                checkLogin.execute("");
            }
        });
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Click again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 1000);
    }

    public class CheckLogin extends AsyncTask<String, String, String> {
        String z = "";

        Boolean isSuccess = false;

        String _username = txtEmpID.getText().toString();

        String _userpass = txtPassword.getText().toString();

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != "") {
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
            }

            if (isSuccess) {
                Intent i = new Intent(LoginActivity.this, MainMenuActivity.class);
                i.putExtra("fullname", fullname);
                i.putExtra("sectcode", sectcode);
                i.putExtra("deptcode", deptcode);
                switch (deptcode){
                    case "I":
                        startActivity(i);
                        break;
                    case "F":
                        startActivity(i);
                        break;
                    default:
                        Toast.makeText(LoginActivity.this, "User is not authorize", Toast.LENGTH_SHORT).show();
                        break;
                }
                //Toast.makeText(LoginActivity.this, deptcode, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            if (_username.trim().equals("") || _userpass.trim().equals(""))
                z = "Username Cannot Be Empty. Username Required";
            else {
                dbAccess = new DBAccess();

                dbAccess.UserLogin(_username,_userpass);

                empid = dbAccess._empid;

                sectcode = dbAccess.sectioncode;

                deptcode = dbAccess.deptcode;

                fullname = dbAccess.FullName_FnameFirst;

                z = dbAccess.z;

                isSuccess = dbAccess.isSuccess;
            }

            return z;
        }
    }

}