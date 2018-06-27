package com.viliyantrbr.nfcemvpayer.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;

import com.viliyantrbr.nfcemvpayer.R;
import com.viliyantrbr.nfcemvpayer.object.PaycardObject;
import com.viliyantrbr.nfcemvpayer.util.GpoUtil;
import com.viliyantrbr.nfcemvpayer.util.HexUtil;
import com.viliyantrbr.nfcemvpayer.util.KeyUtil;
import com.viliyantrbr.nfcemvpayer.util.LogUtil;

import java.util.Arrays;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.Sort;

public class PaymentHostApduService extends HostApduService {
    private static final String TAG = PaymentHostApduService.class.getSimpleName();

    private Realm mRealm = null;

    private PaycardObject mPaycardObject = null;

    public PaymentHostApduService() {
        // Required empty public constructor
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d(TAG, "\"" + TAG + "\": Service create");

        // Get encryption key
        byte[] getEncryptionKey = KeyUtil.getEncryptionKey(this);
        // - Get encryption key

        // Realm
        if (getEncryptionKey != null) {
            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                    .encryptionKey(getEncryptionKey)
                    .build();

            try {
                mRealm = Realm.getInstance(realmConfiguration);
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }
        } else {
            try {
                mRealm = Realm.getDefaultInstance();
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }
        }
        // - Realm

        byte[] applicationPan = null;
        String applicationPanHexadecimal = null;

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        applicationPanHexadecimal = sharedPreferences.getString(getString(R.string.pan_var_name), "N/A");

        if (!applicationPanHexadecimal.equals("N/A")) {
            applicationPan = HexUtil.hexadecimalToBytes(applicationPanHexadecimal);
        }

        if (mRealm != null) {
            if (applicationPan != null) {
                try {
                    mPaycardObject = mRealm.where(PaycardObject.class).equalTo(getString(R.string.pan_var_name), applicationPan).findFirst();
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage());
                    LogUtil.e(TAG, e.toString());

                    e.printStackTrace();
                }
            } else {
                try {
                    mPaycardObject = mRealm.where(PaycardObject.class).sort(getString(R.string.add_date_var_name), Sort.DESCENDING).findFirst();
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage());
                    LogUtil.e(TAG, e.toString());

                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, "\"" + TAG + "\": Service destroy");

        if (mRealm != null) {
            if (mPaycardObject != null) {
                mPaycardObject = null;
            }

            try {
                mRealm.close();
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }

            mRealm = null;
        }
    }

    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
        byte[] responseApdu = null;

        if (commandApdu != null) {
            LogUtil.d(TAG, "\"" + TAG + "\" EMV (C-APDU): Command APDU: " + Arrays.toString(commandApdu));

            String commandApduHexadecimal = HexUtil.bytesToHexadecimal(commandApdu);
            if (commandApduHexadecimal != null) {
                LogUtil.d(TAG, "\"" + TAG + "\" EMV (C-APDU): Command APDU Hexadecimal: " + commandApduHexadecimal);
            }

            // PSE (Payment System Environment)
            if (mPaycardObject.getCPse() != null && mPaycardObject.getRPse() != null) {
                if (Arrays.equals(commandApdu, mPaycardObject.getCPse()) /*&& Arrays.equals(commandApdu, PseUtil.selectPse(null))*/) {
                    responseApdu = mPaycardObject.getRPse();
                }
            }
            // - PSE (Payment System Environment)

            // PPSE (Proximity Payment System Environment)
            if (mPaycardObject.getCPpse() != null && mPaycardObject.getRPpse() != null) {
                if (Arrays.equals(commandApdu, mPaycardObject.getCPpse()) /*&& Arrays.equals(commandApdu, PseUtil.selectPpse(null))*/) {
                    responseApdu = mPaycardObject.getRPpse();
                }
            }
            // - PPSE (Proximity Payment System Environment)

            // FCI (File Control Information)
            if (mPaycardObject.getCFci() != null && mPaycardObject.getRFci() != null) {
                if (Arrays.equals(commandApdu, mPaycardObject.getCFci())) {
                    responseApdu = mPaycardObject.getRFci();
                }
            }
            // - FCI (File Control Information)

            // GPO (Get Processing Options)
            if (mPaycardObject.getCGpo() != null && mPaycardObject.getRGpo() != null) {
                if (Arrays.equals(commandApdu, mPaycardObject.getCGpo())) {
                    responseApdu = mPaycardObject.getRGpo();
                } else if (GpoUtil.isGpoCommand(commandApdu)) {
                    responseApdu = mPaycardObject.getRGpo();
                } // Command APDU may be filled with different data, no problem - return our GPO
            }
            // - GPO (Get Processing Options)

            // AFL (Application File Locator) Record(s)
            if (mPaycardObject.getCAflRecordsList() != null && !mPaycardObject.getCAflRecordsList().isEmpty()
                    &&
                    mPaycardObject.getRAflRecordsList() != null && !mPaycardObject.getRAflRecordsList().isEmpty()) {
                int listCSize = mPaycardObject.getCAflRecordsList().size();
                int listRSize = mPaycardObject.getRAflRecordsList().size();

                if (listCSize > 0 && listRSize > 0 && listCSize == listRSize) {
                    for (int i = 0; i < listCSize; i++) {
                        byte[] cReadRecord = null, rReadRecord = null;
                        try {
                            cReadRecord = mPaycardObject.getCAflRecordsList().get(i);
                        } catch (Exception e) {
                            LogUtil.e(TAG, e.getMessage());
                            LogUtil.e(TAG, e.toString());

                            e.printStackTrace();
                        }
                        try {
                            rReadRecord = mPaycardObject.getRAflRecordsList().get(i);
                        } catch (Exception e) {
                            LogUtil.e(TAG, e.getMessage());
                            LogUtil.e(TAG, e.toString());

                            e.printStackTrace();
                        }

                        if (cReadRecord != null && rReadRecord != null) {
                            if (Arrays.equals(commandApdu, cReadRecord)) {
                                responseApdu = rReadRecord;
                            }
                        }
                    }
                }
            }
            // - AFL (Application File Locator) Record(s)

            // Last Online ATC (Application Transaction Counter) Register
            if (mPaycardObject.getCLastOnlineAtcRegister() != null && mPaycardObject.getRLastOnlineAtcRegister() != null) {
                if (Arrays.equals(commandApdu, mPaycardObject.getCLastOnlineAtcRegister())) {
                    responseApdu = mPaycardObject.getRLastOnlineAtcRegister();
                }
            }
            // - Last Online ATC (Application Transaction Counter) Register

            // PIN (Personal Identification Number) Try Counter
            if (mPaycardObject.getCPinTryCounter() != null && mPaycardObject.getRPinTryCounter() != null) {
                if (Arrays.equals(commandApdu, mPaycardObject.getCPinTryCounter())) {
                    responseApdu = mPaycardObject.getRPinTryCounter();
                }
            }
            // - PIN (Personal Identification Number) Try Counter

            // ATC (Application Transaction Counter)
            if (mPaycardObject.getCAtc() != null && mPaycardObject.getRAtc() != null) {
                if (Arrays.equals(commandApdu, mPaycardObject.getCAtc())) {
                    responseApdu = mPaycardObject.getRAtc();
                }
            }
            // - ATC (Application Transaction Counter)

            // Log Format
            if (mPaycardObject.getCLogFormat() != null && mPaycardObject.getRLogFormat() != null) {
                if (Arrays.equals(commandApdu, mPaycardObject.getCLogFormat())) {
                    responseApdu = mPaycardObject.getRLogFormat();
                }
            }
            // - Log Format

            // Log Entry
            if (mPaycardObject.getCLogEntryList() != null && !mPaycardObject.getCLogEntryList().isEmpty()
                    &&
                    mPaycardObject.getRLogEntryList() != null && !mPaycardObject.getRLogEntryList().isEmpty()) {
                int listCSize = mPaycardObject.getCLogEntryList().size();
                int listRSize = mPaycardObject.getRLogEntryList().size();
                if (listCSize == listRSize) {
                    for (int i = 0; i < listCSize; i++) {
                        byte[] cReadRecord = null, rReadRecord = null;
                        try {
                            cReadRecord = mPaycardObject.getCLogEntryList().get(i);
                        } catch (Exception e) {
                            LogUtil.e(TAG, e.getMessage());
                            LogUtil.e(TAG, e.toString());

                            e.printStackTrace();
                        }
                        try {
                            rReadRecord = mPaycardObject.getRLogEntryList().get(i);
                        } catch (Exception e) {
                            LogUtil.e(TAG, e.getMessage());
                            LogUtil.e(TAG, e.toString());

                            e.printStackTrace();
                        }

                        if (cReadRecord != null && rReadRecord != null) {
                            if (Arrays.equals(commandApdu, cReadRecord)) {
                                responseApdu = rReadRecord;
                            }
                        }
                    }
                }
            }
            // - Log Entry

            if (responseApdu != null) {
                LogUtil.d(TAG, "\"" + TAG + "\" EMV (R-APDU): Command APDU: " + Arrays.toString(responseApdu));

                String responseApduHexadecimal = HexUtil.bytesToHexadecimal(responseApdu);
                if (responseApduHexadecimal != null) {
                    LogUtil.d(TAG, "\"" + TAG + "\" EMV (R-APDU): Response APDU Hexadecimal: " + responseApduHexadecimal);
                }

                return responseApdu;
            }
        }

        return new byte[0];
    }

    @Override
    public void onDeactivated(int reason) {
        switch (reason) {
            case DEACTIVATION_LINK_LOSS:
                LogUtil.w(TAG, "Deactivated: Link Loss");
                break;

            case DEACTIVATION_DESELECTED:
                LogUtil.w(TAG, "Deactivated: Deselected");
                break;
        }
    }
}
