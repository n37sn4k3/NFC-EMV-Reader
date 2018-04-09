package com.viliyantrbr.nfcemvpayer.service;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;

public class PaymentService extends HostApduService {
    @Override
    public byte[] processCommandApdu(byte[] bytes, Bundle bundle) {
        return new byte[0];
    }

    @Override
    public void onDeactivated(int i) {

    }
}
