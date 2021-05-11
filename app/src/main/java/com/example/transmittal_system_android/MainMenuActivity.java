package com.example.transmittal_system_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    AlertDialog alert;
    AlertDialog.Builder altdial;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    String empid, fullname,deptcode,sectcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_menu);


        this.setTitle("Transmittal System");

        setContentView(R.layout.activity_main_menu);

        fullname = getIntent().getExtras().getString("fullname");

        sectcode = getIntent().getExtras().getString("sectcode");

        deptcode = getIntent().getExtras().getString("deptcode");

        drawerLayout = findViewById(R.id.drawer_Layout);

        navigationView = findViewById(R.id.navMenuView);

        toolbar = findViewById(R.id.navToolbar);

        this.setTitleColor(R.color.black);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen((GravityCompat.START))) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent i;
        switch (menuItem.getItemId()){
            case R.id.nav_DataRecords:
                i = new Intent(MainMenuActivity.this, TransmittalRecordsActivity.class);
                i.putExtra("fullname", fullname);
                startActivity(i);
                break;
//            case R.id.nav_DeliveryPass:
//                i = new Intent(MainMenuActivity.this, MainActivity.class);
//                i.putExtra("fullname", fullname);
//                startActivity(i);
//                break;
            case R.id.nav_Signout:
                altdial = new AlertDialog.Builder(MainMenuActivity.this);

                altdial.setMessage("Do you want to signout?").setCancelable(false)
                        .setPositiveButton("Signout", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent launchNextActivity;
                                launchNextActivity = new Intent(MainMenuActivity.this, LoginActivity.class);
                                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(launchNextActivity);
                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                alert = altdial.create();

                alert.show();

                break;
        }

        return true;
    }
}