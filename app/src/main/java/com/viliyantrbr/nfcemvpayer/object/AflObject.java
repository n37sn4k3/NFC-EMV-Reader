package com.viliyantrbr.nfcemvpayer.object;

public class AflObject {
    private int mSfi, mRecordNumber;
    private byte[] mReadCommand;

    public int getSfi() {
        return mSfi;
    }

    public void setSfi(int sfi) {
        mSfi = sfi;
    }

    public int getRecordNumber() {
        return mRecordNumber;
    }

    public void setRecordNumber(int recordNumber) {
        mRecordNumber = recordNumber;
    }

    public byte[] getReadCommand() {
        return mReadCommand;
    }

    public void setReadCommand(byte[] readCommand) {
        mReadCommand = readCommand;
    }
}
