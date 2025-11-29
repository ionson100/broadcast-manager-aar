package com.bitnic.test;

import android.os.Parcelable;

import androidx.annotation.NonNull;

public class TestParcelable implements Parcelable {

    public int age;
    public String name;

    @NonNull
    @Override
    public String toString() {
        return name+":"+age;
    }

    public TestParcelable() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeInt(this.age);
        dest.writeString(this.name);
    }

    protected TestParcelable(android.os.Parcel in) {
        this.age = in.readInt();
        this.name = in.readString();
    }

    public static final android.os.Parcelable.Creator<TestParcelable> CREATOR = new android.os.Parcelable.Creator<TestParcelable>() {
        @Override
        public TestParcelable createFromParcel(android.os.Parcel source) {
            return new TestParcelable(source);
        }

        @Override
        public TestParcelable[] newArray(int size) {
            return new TestParcelable[size];
        }
    };
}
