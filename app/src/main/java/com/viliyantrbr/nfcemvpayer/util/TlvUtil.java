package com.viliyantrbr.nfcemvpayer.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

public class TlvUtil {
    private static final String TAG = TlvUtil.class.getName();

    @Nullable
    public byte[] getTlvValue(@NonNull byte[] dataBytes, @NonNull byte[] tlvTag) {
        // Returning result
        byte[] result = null;
        // - Returning result

        ByteArrayInputStream byteArrayInputStream = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(dataBytes);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
            LogUtil.e(TAG, e.toString());

            e.printStackTrace();
        }

        if (byteArrayInputStream != null) {
            if (byteArrayInputStream.available() < 2) {
                try {
                    throw new Exception("Cannot preform TLV byte array stream actions, available bytes < 2; Length is " + byteArrayInputStream.available());
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage());
                    LogUtil.e(TAG, e.toString());

                    e.printStackTrace();
                }
            } else {
                int i = 0, resultSize;

                byte[] tlvTagLength = new byte[tlvTag.length];
                // let tlvTagLength: ByteArray? = ByteArray(tlvTag.size) // Kotlin

                while (byteArrayInputStream.read() != -1) {
                    i += 1;

                    if (i >= tlvTag.length) {
                        try {
                            tlvTagLength = Arrays.copyOfRange(dataBytes, i - tlvTag.length, i);
                        } catch (Exception e) {
                            LogUtil.e(TAG, e.getMessage());
                            LogUtil.e(TAG, e.toString());

                            e.printStackTrace();
                        }
                    }

                    if (Arrays.equals(tlvTag, tlvTagLength)) {
                        resultSize = byteArrayInputStream.read();

                        if (resultSize > byteArrayInputStream.available()) {
                            continue;
                        }

                        if (resultSize != -1) {
                            byte[] resultRes = new byte[resultSize];
                            // let resultRes: ByteArray? = ByteArray(resultSize) // Kotlin

                            if (byteArrayInputStream.read(resultRes, 0, resultSize) != 0) {
                                String checkResponse = HexUtil.bytesToHexadecimal(dataBytes), checkResult = HexUtil.bytesToHexadecimal(resultRes);

                                if (checkResponse != null && checkResult != null && checkResponse.contains(checkResult)) {
                                    result = resultRes;
                                }
                            }
                        }
                    }
                }
            }

            try {
                byteArrayInputStream.close();
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }
        }

        return result;
    }
}
