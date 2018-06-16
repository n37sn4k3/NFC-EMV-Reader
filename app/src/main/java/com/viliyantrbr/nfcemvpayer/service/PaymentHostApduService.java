package com.viliyantrbr.nfcemvpayer.service;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;

import com.viliyantrbr.nfcemvpayer.object.PaycardObject;
import com.viliyantrbr.nfcemvpayer.util.GpoUtil;
import com.viliyantrbr.nfcemvpayer.util.HexUtil;
import com.viliyantrbr.nfcemvpayer.util.LogUtil;
import com.viliyantrbr.nfcemvpayer.util.PseUtil;

import java.util.Arrays;

public class PaymentHostApduService extends HostApduService {
    private static final String TAG = PaymentHostApduService.class.getSimpleName();

    private PaycardObject mPaycardObject;

    public PaymentHostApduService() {
        // Required empty public constructor
    }

    public PaymentHostApduService(@NonNull PaycardObject paycardObject) {
        mPaycardObject = paycardObject;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d(TAG, "\"" + TAG + "\": Service create");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, "\"" + TAG + "\": Service destroy");
    }

    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
        byte[] responseApdu = null;

        if (commandApdu != null) {
            LogUtil.d(TAG, "\"" + TAG + "\": Command APDU: " + Arrays.toString(commandApdu));

            String commandApduHexadecimal = HexUtil.bytesToHexadecimal(commandApdu);
            if (commandApduHexadecimal != null) {
                LogUtil.d(TAG, "\"" + TAG + "\": Command APDU Hexadecimal: " + commandApduHexadecimal);
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
                } // Command APDU may be filled with different data, no problem - return out GPO
            }
            // - GPO (Get Processing Options)

            if (responseApdu != null) {
                return responseApdu;
            }
        }

        return new byte[0];
    }

    @Override
    public void onDeactivated(int reason) {
        try {
            stopSelf();
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
            LogUtil.e(TAG, e.toString());

            e.printStackTrace();
        }
    }
}
