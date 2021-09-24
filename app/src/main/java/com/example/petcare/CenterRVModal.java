package com.example.petcare;

import android.os.Parcel;
import android.os.Parcelable;

public class CenterRVModal implements Parcelable {
    private String centerName;
    private String centerDescription;
    private String centerFee;
    private String centerFor;
    private String centerImag;
    private String centerLink;
    private String centerID;

    public CenterRVModal(){

    }

    protected CenterRVModal(Parcel in) {
        centerName = in.readString();
        centerDescription = in.readString();
        centerFee = in.readString();
        centerFor = in.readString();
        centerImag = in.readString();
        centerLink = in.readString();
        centerID = in.readString();
    }

    public static final Creator<CenterRVModal> CREATOR = new Creator<CenterRVModal>() {
        @Override
        public CenterRVModal createFromParcel(Parcel in) {
            return new CenterRVModal(in);
        }

        @Override
        public CenterRVModal[] newArray(int size) {
            return new CenterRVModal[size];
        }
    };

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getCenterDescription() {
        return centerDescription;
    }

    public void setCenterDescription(String centerDescription) {
        this.centerDescription = centerDescription;
    }

    public String getCenterFee() {
        return centerFee;
    }

    public void setCenterFee(String centerFee) {
        this.centerFee = centerFee;
    }

    public String getCenterFor() {
        return centerFor;
    }

    public void setCenterFor(String centerFor) {
        this.centerFor = centerFor;
    }

    public String getCenterImag() {
        return centerImag;
    }

    public void setCenterImag(String centerImag) {
        this.centerImag = centerImag;
    }

    public String getCenterLink() {
        return centerLink;
    }

    public void setCenterLink(String centerLink) {
        this.centerLink = centerLink;
    }

    public String getCenterID() {
        return centerID;
    }

    public void setCenterID(String centerID) {
        this.centerID = centerID;
    }

    public CenterRVModal(String centerName, String centerDescription, String centerFee, String centerFor, String centerImag, String centerLink, String centerID) {
        this.centerName = centerName;
        this.centerDescription = centerDescription;
        this.centerFee = centerFee;
        this.centerFor = centerFor;
        this.centerImag = centerImag;
        this.centerLink = centerLink;
        this.centerID = centerID;


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(centerName);
        dest.writeString(centerDescription);
        dest.writeString(centerFee);
        dest.writeString(centerFor);
        dest.writeString(centerImag);
        dest.writeString(centerLink);
        dest.writeString(centerID);
    }
}
