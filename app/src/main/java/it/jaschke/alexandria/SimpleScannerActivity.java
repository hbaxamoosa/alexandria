package it.jaschke.alexandria;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import timber.log.Timber;

public class SimpleScannerActivity extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
        Timber.v("inside onCreate(Bundle state)");
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
        Timber.v("inside onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
        Timber.v("inside onPause()");
    }

    @Override
    public void handleResult(Result rawResult) {
        Timber.v("inside handleResult(Result rawResult)");
        // Do something with the result here
        Timber.v(rawResult.getText()); // Prints scan results
        Timber.v(rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        Intent intent = new Intent();
        intent.putExtra("ISBN", rawResult.getText().toString());
        intent.putExtra("format", rawResult.getBarcodeFormat().toString());
        setResult(2, intent);
        finish(); //finishing activity

        // If you would like to resume scanning, call this method below:
        // mScannerView.resumeCameraPreview(this);
    }
}