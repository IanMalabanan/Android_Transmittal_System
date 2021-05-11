package com.example.transmittal_system_android;

import androidx.appcompat.app.AppCompatActivity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;

public class ScanTransmitterActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView ScannerView;

    DBAccess dbAccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView =  new ZXingScannerView(this);
        setContentView(ScannerView);
        //setContentView(R.layout.activity_scan_transmitter);
    }

    @Override
    public void handleResult(Result result) {
        dbAccess = new DBAccess();

        dbAccess.SKPI_GetAllEmployeesByEmpID(result.getText());

        if(result.getText() == ""){
            Toast.makeText(this,"Record Not Found", Toast.LENGTH_SHORT).show();
            MainActivity.lblTransmitterResult.setText("");
            onBackPressed();
        }
        else{
            MainActivity.lblTransmitterResult.setText(dbAccess.transmitter);
            onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ScannerView.stopCamera();
    }
}
