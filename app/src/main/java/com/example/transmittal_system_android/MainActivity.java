package com.example.transmittal_system_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ClsCustomDataRecords clsCustomDataRecords;

    public static EditText lblTransmitterResult, txtRemarks;

    TextView t_RRNumber;

    Button btnSave;

    CheckBox chkIsCompleted;

    String RRNo, fullname;

    public static ImageButton btnScanTransmitter;

    AlertDialog alert;

    AlertDialog.Builder altdial;

    DBAccess dbAccess;

    Boolean stat = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fullname = getIntent().getExtras().getString("fullname");

        RRNo = getIntent().getExtras().getString("RRNo");

        txtRemarks = (EditText) findViewById(R.id.txtRemarks);

        t_RRNumber = (TextView) findViewById(R.id.t_RRNumber);

        chkIsCompleted = (CheckBox) findViewById(R.id.chkIsCompleted);

        lblTransmitterResult = (EditText) findViewById(R.id.lblTransmitterResult);

        btnScanTransmitter = (ImageButton) findViewById(R.id.btnScanTransmitter);

        btnSave = (Button) findViewById(R.id.btnSave);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);

        t_RRNumber.setText(RRNo);

        btnSave.setOnClickListener(this);

        btnScanTransmitter.setOnClickListener(this);

        chkIsCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //Toast.makeText(DeliveryOutActivity.this, String.valueOf(b), Toast.LENGTH_LONG).show();
                stat = b;
            }
        });
    }

    public static void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;

            for (int idx = 0; idx < group.getChildCount(); idx++) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }

    public void SaveAccountingData() {
        if(lblTransmitterResult.getText().toString() == ""){
            Toast.makeText(MainActivity.this, "Must Scan Employee ID", Toast.LENGTH_LONG).show();
        }else{

        dbAccess = new DBAccess();

        dbAccess.SaveAccountingData(RRNo, lblTransmitterResult.getText().toString(), fullname, txtRemarks.getText().toString(), stat);

        Toast.makeText(MainActivity.this, dbAccess.z, Toast.LENGTH_LONG).show();

        GetAllRecordsForReceiving();

        Intent frm = new Intent(MainActivity.this, TransmittalRecordsActivity.class);

        frm.putExtra("fullname", fullname);

        startActivity(frm);}
    }

    public void GetAllRecordsForReceiving() {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllRecordsForReceiving();

        clsCustomDataRecords = new ClsCustomDataRecords(this, dbAccess.clsDTDataRecords);

        TransmittalRecordsActivity.listView.setAdapter(clsCustomDataRecords);

        clsCustomDataRecords.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnScanTransmitter:
                startActivity(new Intent(getApplicationContext(), ScanTransmitterActivity.class));
                break;
            case R.id.btnSave:
                altdial = new AlertDialog.Builder(MainActivity.this);

                altdial.setMessage("Do you want to continue?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SaveAccountingData();
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
    }
}
