package com.viliyantrbr.nfcemvpayer.util;

import android.support.annotation.Nullable;

import com.viliyantrbr.nfcemvpayer.helper.ReadPaycardConstsHelper;

import java.io.ByteArrayOutputStream;

public class PseUtil {
    private static final String TAG = PseUtil.class.getName();

    private static final byte[] PSE = "1PAY.SYS.DDF01".getBytes(); // PSE
    private static final byte[] PPSE = "2PAY.SYS.DDF01".getBytes(); // PPSE

    // PSE (Payment System Environment)
    @Nullable
    public static byte[] selectPse(@Nullable byte[] pse) {
        // Returning result
        byte[] result = null;
        // - Returning result

        ByteArrayOutputStream pseByteArrayOutputStream = null;
        try {
            pseByteArrayOutputStream = new ByteArrayOutputStream();
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
            LogUtil.e(TAG, e.toString());

            e.printStackTrace();
        }

        if (pseByteArrayOutputStream != null) {
            try {
                pseByteArrayOutputStream.write(ReadPaycardConstsHelper.SELECT); // Cla, Ins

                pseByteArrayOutputStream.write(new byte[]{
                        (byte) 0x04, // P1
                        (byte) 0x00 // P2
                });

                if (pse != null) {
                    pseByteArrayOutputStream.write(new byte[]{
                            (byte) pse.length // Lc
                    });

                    pseByteArrayOutputStream.write(pse); // Data
                } else {
                    pseByteArrayOutputStream.write(new byte[]{
                            (byte) PSE.length // Lc
                    });

                    pseByteArrayOutputStream.write(PSE); // Data
                }

                pseByteArrayOutputStream.write(new byte[]{
                        (byte) 0x00 // Le
                });

                pseByteArrayOutputStream.close();

                result = pseByteArrayOutputStream.toByteArray();
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }
        }

        return result;
    }
    // - PSE (Payment System Environment)

    // PPSE (Proximity Payment System Environment)
    @Nullable
    public static byte[] selectPpse(@Nullable byte[] ppse) {
        // Returning result
        byte[] result = null;
        // - Returning result

        ByteArrayOutputStream ppseByteArrayOutputStream = null;
        try {
            ppseByteArrayOutputStream = new ByteArrayOutputStream();
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
            LogUtil.e(TAG, e.toString());

            e.printStackTrace();
        }

        if (ppseByteArrayOutputStream != null) {
            try {
                ppseByteArrayOutputStream.write(ReadPaycardConstsHelper.SELECT); // Cla, Ins

                ppseByteArrayOutputStream.write(new byte[]{
                        (byte) 0x04, // P1
                        (byte) 0x00 // P2
                });

                if (ppse != null) {
                    ppseByteArrayOutputStream.write(new byte[]{
                            (byte) ppse.length // Lc
                    });

                    ppseByteArrayOutputStream.write(ppse); // Data
                } else {
                    ppseByteArrayOutputStream.write(new byte[]{
                            (byte) PPSE.length // Lc
                    });

                    ppseByteArrayOutputStream.write(PPSE); // Data
                }

                ppseByteArrayOutputStream.write(new byte[]{
                        (byte) 0x00 // Le
                });

                ppseByteArrayOutputStream.close();

                result = ppseByteArrayOutputStream.toByteArray();
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                LogUtil.e(TAG, e.toString());

                e.printStackTrace();
            }
        }

        return result;
    }
    // - PPSE (Proximity Payment System Environment)
}
