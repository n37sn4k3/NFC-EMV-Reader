package com.viliyantrbr.nfcemvpayer.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.viliyantrbr.nfcemvpayer.helper.ReadPaycardConstsHelper;
import com.viliyantrbr.nfcemvpayer.object.AflObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class AflUtil {
    private static final String TAG = AflUtil.class.getName();

    @Nullable
    public ArrayList<AflObject> getAflDataRecords(@NonNull byte[] aflData) {
        // Returning result
        ArrayList<AflObject> result = null;
        // - Returning result

        LogUtil.d(TAG, "AFL Data Length: " + aflData.length);

        if (aflData.length < 4) { // At least 4 bytes length needed to go ahead
            try {
                throw new Exception("Cannot preform AFL data byte array actions, available bytes < 4; Length is " + aflData.length);
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }
        } else {
            result = new ArrayList<>();

            for (int i = 0; i < aflData.length / 4; i++) {
                int firstRecordNumber = aflData[4 * i + 1], lastRecordNumber = aflData[4 * i + 2]; // First record number & final record number

                while (firstRecordNumber <= lastRecordNumber) {
                    AflObject aflObject = new AflObject();
                    aflObject.setSfi(aflData[4 * i] >> 3); // SFI (Short File Identifier)
                    aflObject.setRecordNumber(firstRecordNumber);

                    byte[] cReadRecord = null;

                    ByteArrayOutputStream readRecordByteArrayOutputStream = null;
                    try {
                        readRecordByteArrayOutputStream = new ByteArrayOutputStream();
                    } catch (Exception e) {
                        LogUtil.e(TAG, e.getMessage());
                        LogUtil.e(TAG, e.toString());

                        e.printStackTrace();
                    }

                    if (readRecordByteArrayOutputStream != null) {
                        try {
                            readRecordByteArrayOutputStream.write(ReadPaycardConstsHelper.READ_RECORD);

                            readRecordByteArrayOutputStream.write(new byte[]{
                                    (byte) aflObject.getRecordNumber(),
                                    (byte) ((aflObject.getSfi() << 0x03) | 0x04),
                            });

                            readRecordByteArrayOutputStream.write(new byte[]{
                                    (byte) 0x00 // Le
                            });

                            readRecordByteArrayOutputStream.close();

                            cReadRecord = readRecordByteArrayOutputStream.toByteArray();
                        } catch (Exception e) {
                            LogUtil.e(TAG, e.getMessage());
                            LogUtil.e(TAG, e.toString());

                            e.printStackTrace();
                        }
                    }

                    if (cReadRecord != null) {
                        aflObject.setReadCommand(cReadRecord);
                    }

                    result.add(aflObject);

                    firstRecordNumber++;
                }
            }
        }

        return result;
    }
}
