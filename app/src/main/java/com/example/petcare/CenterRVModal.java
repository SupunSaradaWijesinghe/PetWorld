package com.example.petcare;

import android.os.Parcel;
import android.os.Parcelable;

public class CenterRVModal implements Parcelable{
    private String centerName;
    private String centerDescription;
    private String centerFee;
    private String centerLocation;
    private String centerImg;
    private String centerLink;
    private String centerID;


    public CenterRVModal(){

    }


    protected CenterRVModal(Parcel in) {
        centerName = in.readString();
        centerDescription = in.readString();
        centerFee = in.readString();
        centerLocation= in.readString();
        centerImg = in.readString();
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

    public String getCenterLocation() {
        return centerLocation;
    }

    public void setCenterLocation(String centerLocation) {
        this.centerLocation = centerLocation;
    }

    public String getCenterImg() {
        return centerImg;
    }

    public void setCenterImg(String centerImg) {
        this.centerImg = centerImg;
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

    public CenterRVModal(String centerName, String centerDescription, String centerFee, String centerLocation, String centerImg, String centerLink, String centerID) {
        this.centerName = centerName;
        this.centerDescription = centerDescription;
        this.centerFee = centerFee;
        this.centerLocation = centerLocation;
        this.centerImg = centerImg;
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
        dest.writeString(centerLocation);
        dest.writeString(centerImg);
        dest.writeString(centerLink);
        dest.writeString(centerID);
    }
}
