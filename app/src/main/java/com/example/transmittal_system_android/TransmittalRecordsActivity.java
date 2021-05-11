package com.example.transmittal_system_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class TransmittalRecordsActivity extends AppCompatActivity implements View.OnClickListener {

    ClsCustomDataRecords clsCustomDataRecords;

    public static ListView listView;

    AutoCompleteTextView autoCompleteTxtSearch;

    ImageButton btnSearchRR, btnResetRR;

    AlertDialog alert;

    AlertDialog.Builder altdial;

    String RRNo, fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transmittal_records);

        fullname = getIntent().getExtras().getString("fullname");

        listView = (ListView) findViewById(R.id.listView);

        btnResetRR = (ImageButton) findViewById(R.id.btnResetRR);

        btnSearchRR = (ImageButton) findViewById(R.id.btnSearchRR);

        autoCompleteTxtSearch = (AutoCompleteTextView) findViewById(R.id.autoCompleteTxtSearch);


        GetAllRecordsForReceiving();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                RRNo = view.getTag().toString();

                altdial = new AlertDialog.Builder(TransmittalRecordsActivity.this);

                altdial.setMessage("Receive Document?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent io = new Intent(TransmittalRecordsActivity.this, MainActivity.class);
                                io.putExtra("RRNo", RRNo);
                                io.putExtra("fullname", fullname);
                                startActivity(io);
                                //Toast.makeText(TransmittalRecordsActivity.this, RRNo, Toast.LENGTH_LONG).show();
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
            }
        });

        btnSearchRR.setOnClickListener(this);

        btnResetRR.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent launchNextActivity;
        launchNextActivity = new Intent(TransmittalRecordsActivity.this, MainMenuActivity.class);
        launchNextActivity.putExtra("fullname", fullname);
        startActivity(launchNextActivity);
    }

    public void GetAllRecordsForReceiving() {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllRecordsForReceiving();

        clsCustomDataRecords = new ClsCustomDataRecords(this, dbAccess.clsDTDataRecords);

        listView.setAdapter(clsCustomDataRecords);

        clsCustomDataRecords.notifyDataSetChanged();
    }

    public void GetAllRecordsForReceivingByRRNo() {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllRecordsForReceivingByRRNo(autoCompleteTxtSearch.getText().toString());

        clsCustomDataRecords = new ClsCustomDataRecords(this, dbAccess.clsDTDataRecords);

        listView.setAdapter(clsCustomDataRecords);

        clsCustomDataRecords.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSearchRR:
                GetAllRecordsForReceivingByRRNo();
                break;
            case  R.id.btnResetRR:
                GetAllRecordsForReceiving();
                autoCompleteTxtSearch.setText("");
                break;
        }
    }
}