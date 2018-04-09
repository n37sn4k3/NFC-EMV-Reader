package com.viliyantrbr.nfcemvpayer.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.viliyantrbr.nfcemvpayer.R;
import com.viliyantrbr.nfcemvpayer.util.LogUtil;

public class HostPaycardActivity extends AppCompatActivity {
    private static final String TAG = HostPaycardActivity.class.getSimpleName();

    private NfcAdapter mNfcAdapter = null;

    private AlertDialog mAlertDialog = null;

    private void nfcNotSupported() {
        Log.w(TAG, "NFC Not Supported");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("NFC Not Supported");
        builder.setMessage("Your device does not support NFC feature.");
        builder.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                finish();
            }
        });
        builder.setCancelable(false);

        mAlertDialog = builder.create();

        mAlertDialog.show();
    }

    private void nfcNotEnabled() {
        Log.w(TAG, "NFC Not Enabled");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("NFC Not Enabled");
        builder.setMessage("Enable NFC feature and come back again.");
        builder.setNeutralButton("Enable in Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
            }
        });
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                finish();
            }
        });
        builder.setCancelable(false);

        mAlertDialog = builder.create();

        mAlertDialog.show();
    }

    private void nfcHceNotSupported() {
        Log.w(TAG, "NFC HCE Not Supported");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("NFC HCE Not Supported");
        builder.setMessage("Your device does not support NFC host card emulation feature.");
        builder.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                finish();
            }
        });
        builder.setCancelable(false);

        mAlertDialog = builder.create();

        mAlertDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, "\"" + TAG + "\": Activity create");

        setContentView(R.layout.activity_host_paycard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d(TAG, "\"" + TAG + "\": Activity start");

        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC)) {
            // Try to get the NFC adapter directly
            try {
                mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                Log.e(TAG, e.toString());

                e.printStackTrace();
            }

            if (mNfcAdapter == null) {
                NfcManager nfcManager = null;
                try {
                    nfcManager = (NfcManager) getSystemService(NFC_SERVICE);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    Log.e(TAG, e.toString());

                    e.printStackTrace();
                }

                if (nfcManager != null) {
                    // Try to get the NFC adapter alternatively
                    try {
                        mNfcAdapter = nfcManager.getDefaultAdapter();
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        Log.e(TAG, e.toString());

                        e.printStackTrace();
                    }
                }
            }

            if (mNfcAdapter == null) {
                nfcNotSupported();
            } else if (!mNfcAdapter.isEnabled()) {
                nfcNotEnabled();
            } else if (mNfcAdapter.isEnabled()) {
                // Activity relative
                // - Activity relative
            }
        } else {
            nfcNotSupported();
        }

        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC_HOST_CARD_EMULATION)) {
            // Activity relative
            // - Activity relative
        } else {
            nfcHceNotSupported();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d(TAG, "\"" + TAG + "\": Activity stop");

        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC)) {
            if (mNfcAdapter != null) {
                if (mNfcAdapter.isEnabled()) {
                    // Activity relative
                    // - Activity relative
                }

                mNfcAdapter = null;
            }
        }

        if (mAlertDialog != null) {
            if (mAlertDialog.isShowing()) mAlertDialog.dismiss();

            mAlertDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, "\"" + TAG + "\": Activity destroy");
    }
}
