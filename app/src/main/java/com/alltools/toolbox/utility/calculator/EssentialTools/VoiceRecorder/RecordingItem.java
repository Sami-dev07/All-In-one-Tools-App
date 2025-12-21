package com.alltools.toolbox.utility.calculator.EssentialTools.VoiceRecorder;

import android.os.Parcel;
import android.os.Parcelable;

public class RecordingItem implements Parcelable {
    public static final Parcelable.Creator<RecordingItem> CREATOR = new Parcelable.Creator<RecordingItem>() {
        @Override
        public RecordingItem createFromParcel(Parcel parcel) {
            return new RecordingItem(parcel);
        }

        @Override
        public RecordingItem[] newArray(int i) {
            return new RecordingItem[i];
        }
    };
    private String mFilePath;
    private int mId;
    private int mLength;
    private String mName;
    private long mTime;

    @Override
    public int describeContents() {
        return 0;
    }

    public RecordingItem() {
    }

    public RecordingItem(Parcel parcel) {
        this.mName = parcel.readString();
        this.mFilePath = parcel.readString();
        this.mId = parcel.readInt();
        this.mLength = parcel.readInt();
        this.mTime = parcel.readLong();
    }

    public String getFilePath() {
        return this.mFilePath;
    }

    public void setFilePath(String str) {
        this.mFilePath = str;
    }

    public int getLength() {
        return this.mLength;
    }

    public void setLength(int i) {
        this.mLength = i;
    }

    public int getId() {
        return this.mId;
    }

    public void setId(int i) {
        this.mId = i;
    }

    public String getName() {
        return this.mName;
    }

    public void setName(String str) {
        this.mName = str;
    }

    public long getTime() {
        return this.mTime;
    }

    public void setTime(long j) {
        this.mTime = j;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mLength);
        parcel.writeLong(this.mTime);
        parcel.writeString(this.mFilePath);
        parcel.writeString(this.mName);
    }
}