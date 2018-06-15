package com.viliyantrbr.nfcemvpayer.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.viliyantrbr.nfcemvpayer.R;
import com.viliyantrbr.nfcemvpayer.thread.ReadPaycardThread;
import com.viliyantrbr.nfcemvpayer.util.LogUtil;

public class ReadPaycardActivity extends AppCompatActivity {
    private static final String TAG = ReadPaycardActivity.class.getSimpleName();

    private NfcAdapter mNfcAdapter = null;

    private AlertDialog mAlertDialog = null;

    // Receiver(s)
    // Broadcast intent action(s)
    public static final String ACTION_SUCCESS_READ_PAYCARD_BROADCAST = TAG + "SuccessBroadcastIntentAction";
    public static final String ACTION_CANNOT_READ_PAYCARD_BROADCAST = TAG + "CannotBroadcastIntentAction";
    // - Broadcast intent action(s)

    private BroadcastReceiver mSuccessReadPaycardCustomReceiver = null;
    private BroadcastReceiver mCannotReadPaycardCustomReceiver = null;
    // - Receiver(s)

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
        builder.setNeutralButton("Enable NFC", new DialogInterface.OnClickListener() {
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

    private NfcAdapter.ReaderCallback mReaderCallback = new NfcAdapter.ReaderCallback() {
        @Override
        public void onTagDiscovered(Tag tag) {
            runOnUiThread(new ReadPaycardThread(ReadPaycardActivity.this, tag));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, "\"" + TAG + "\": Activity create");

        setContentView(R.layout.activity_read_paycard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        try {
            mSuccessReadPaycardCustomReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    LogUtil.d(TAG, "\"" + TAG + "\": Receiver receive; " + ReadPaycardActivity.this.getString(R.string.success_read_paycard));

                    if (context == null || intent == null) {
                        if (context == null) {
                            LogUtil.w(TAG, "\"" + TAG + "\": Receiver context lack; " + ReadPaycardActivity.this.getString(R.string.success_read_paycard));
                        }

                        if (intent == null) {
                            LogUtil.w(TAG, "\"" + TAG + "\": Receiver intent lack; " + ReadPaycardActivity.this.getString(R.string.success_read_paycard));
                        }

                        return;
                    }

                    String intentAction = null;
                    try {
                        intentAction = intent.getAction();
                    } catch (Exception e) {
                        LogUtil.e(TAG, e.getMessage());
                        LogUtil.e(TAG, e.toString());

                        e.printStackTrace();
                    }

                    if (intentAction == null || !intentAction.equals(ACTION_SUCCESS_READ_PAYCARD_BROADCAST)) {
                        if (intentAction == null) {
                            LogUtil.w(TAG, "\"" + TAG + "\": Receiver intent action lack; " + ReadPaycardActivity.this.getString(R.string.success_read_paycard));
                        } else if (!intentAction.equals(ACTION_SUCCESS_READ_PAYCARD_BROADCAST)) {
                            LogUtil.w(TAG, "\"" + TAG + "\": Receiver intent action mismatch ; " + ReadPaycardActivity.this.getString(R.string.success_read_paycard));
                        }

                        return;
                    }

                    successReadPaycardReceiverOk(context, intent);
                }
            };
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            Log.e(TAG, e.toString());

            e.printStackTrace();
        }
        try {
            mCannotReadPaycardCustomReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    LogUtil.d(TAG, "\"" + TAG + "\": Receiver receive; " + ReadPaycardActivity.this.getString(R.string.cannot_read_paycard));

                    if (context == null || intent == null) {
                        if (context == null) {
                            LogUtil.w(TAG, "\"" + TAG + "\": Receiver context lack; " + ReadPaycardActivity.this.getString(R.string.cannot_read_paycard));
                        }

                        if (intent == null) {
                            LogUtil.w(TAG, "\"" + TAG + "\": Receiver intent lack; " + ReadPaycardActivity.this.getString(R.string.cannot_read_paycard));
                        }

                        return;
                    }

                    String intentAction = null;
                    try {
                        intentAction = intent.getAction();
                    } catch (Exception e) {
                        LogUtil.e(TAG, e.getMessage());
                        LogUtil.e(TAG, e.toString());

                        e.printStackTrace();
                    }

                    if (intentAction == null || !intentAction.equals(ACTION_CANNOT_READ_PAYCARD_BROADCAST)) {
                        if (intentAction == null) {
                            LogUtil.w(TAG, "\"" + TAG + "\": Receiver intent action lack; " + ReadPaycardActivity.this.getString(R.string.cannot_read_paycard));
                        } else if (!intentAction.equals(ACTION_CANNOT_READ_PAYCARD_BROADCAST)) {
                            LogUtil.w(TAG, "\"" + TAG + "\": Receiver intent action mismatch; " + ReadPaycardActivity.this.getString(R.string.cannot_read_paycard));
                        }

                        return;
                    }

                    cannotReadPaycardReceiverOk(context, intent);
                }
            };
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            Log.e(TAG, e.toString());

            e.printStackTrace();
        }
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
                Log.d(TAG, "Enabling reader mode...");

                // "Enable reader mode" (with NFC-A (ISO 14443-3A), NFC-B (ISO 14443-3B) reader flags (enumerated in ISO-DEP))
                mNfcAdapter.enableReaderMode(this, mReaderCallback, NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_NFC_B, null);
                // - "Enable reader mode" (with NFC-A (ISO 14443-3A), NFC-B (ISO 14443-3B) reader flags (enumerated in ISO-DEP))

                // Register read paycard receiver(s) from thread
                if (mSuccessReadPaycardCustomReceiver != null) {
                    LocalBroadcastManager.getInstance(this)
                            .registerReceiver(mSuccessReadPaycardCustomReceiver, new IntentFilter(ACTION_SUCCESS_READ_PAYCARD_BROADCAST)
                            ); // Success read paycard
                }
                if (mCannotReadPaycardCustomReceiver != null) {
                    LocalBroadcastManager.getInstance(this)
                            .registerReceiver(mCannotReadPaycardCustomReceiver, new IntentFilter(ACTION_CANNOT_READ_PAYCARD_BROADCAST)
                            ); // Cannot read paycard
                }
                // - Register read paycard receiver(s) from thread
                // - Activity relative
            }
        } else {
            nfcNotSupported();
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
                    Log.d(TAG, "Disabling reader mode...");

                    // Unregister (deregister) read paycard receiver(s) from thread
                    if (mCannotReadPaycardCustomReceiver != null) {
                        LocalBroadcastManager.getInstance(this)
                                .unregisterReceiver(mCannotReadPaycardCustomReceiver);
                    }
                    if (mSuccessReadPaycardCustomReceiver != null) {
                        LocalBroadcastManager.getInstance(this)
                                .unregisterReceiver(mSuccessReadPaycardCustomReceiver);
                    }
                    // - Unregister (deregister) read paycard receiver(s) from thread

                    // "Disable reader mode"
                    mNfcAdapter.disableReaderMode(this);
                    // - "Disable reader mode"
                    // - Activity relative
                }
            }
        }

        if (mAlertDialog != null) {
            if (mAlertDialog.isShowing()) {
                mAlertDialog.dismiss();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, "\"" + TAG + "\": Activity destroy");

        if (mCannotReadPaycardCustomReceiver != null) {
            mCannotReadPaycardCustomReceiver = null;
        }
        if (mSuccessReadPaycardCustomReceiver != null) {
            mSuccessReadPaycardCustomReceiver = null;
        }

        if (mAlertDialog != null) {
            mAlertDialog = null;
        }

        if (mNfcAdapter != null) {
            mNfcAdapter = null;
        }

        System.gc(); // All done, recycle unused objects (mainly because of thread)
    }

    public void successReadPaycardReceiverOk(@NonNull Context context, @NonNull Intent intent) {
        LogUtil.d(TAG, "\"" + TAG + "\": Receiver receive OK; " + getString(R.string.success_read_paycard));

        // Incoming context is from the receiver; using activity context is better option here

        // Receiver Relative
        finish(false);
        // - Receiver Relative
    }
    public void cannotReadPaycardReceiverOk(@NonNull Context context, @NonNull Intent intent) {
        LogUtil.d(TAG, "\"" + TAG + "\": Receiver receive OK; " + getString(R.string.cannot_read_paycard));

        // Incoming context is from the receiver; using activity context is better option here

        // Receiver Relative
        Toast.makeText(this, getString(R.string.cannot_read_paycard), Toast.LENGTH_LONG).show();

        finish(false);
        // - Receiver Relative
    }

    private void finish(boolean andRemoveTask) {
        if (andRemoveTask) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAndRemoveTask();
            }

            return;
        }

        finish();
    }
}
