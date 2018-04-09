package com.viliyantrbr.nfcemvpayer.object;

import java.util.Date;

import io.realm.RealmObject;

public class PaymentObject extends RealmObject {
    // Additional data
    private Date mAddDate = null;
    // - Additional data

    // Additional data
    public Date getAddDate() {
        return mAddDate;
    }
    public void setAddDate(Date addDate) {
        this.mAddDate = addDate;
    }
    // - Additional data
}
