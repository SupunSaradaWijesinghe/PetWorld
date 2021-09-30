package com.example.petcare;

import android.os.Parcel;
import android.os.Parcelable;

public class PetRVModal implements Parcelable {
    private String petBreed;
    private String petDescription;
    private String petPrice;
    private String petLocation;
    private String petImg;
    private String petLink;
    private String petID;

    public PetRVModal(){

    }

    protected PetRVModal(Parcel in) {
        petBreed = in.readString();
        petDescription = in.readString();
        petPrice = in.readString();
        petLocation = in.readString();
        petImg = in.readString();
        petLink = in.readString();
        petID = in.readString();
    }

    public static final Creator<PetRVModal> CREATOR = new Creator<PetRVModal>() {
        @Override
        public PetRVModal createFromParcel(Parcel in) {
            return new PetRVModal(in);
        }

        @Override
        public PetRVModal[] newArray(int size) {
            return new PetRVModal[size];
        }
    };

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public String getPetDescription() {
        return petDescription;
    }

    public void setPetDescription(String petDescription) {
        this.petDescription = petDescription;
    }

    public String getPetPrice() {
        return petPrice;
    }

    public void setPetPrice(String petPrice) {
        this.petPrice = petPrice;
    }

    public String getPetLocation() {
        return petLocation;
    }

    public void setPetLocation(String petLocation) {
        this.petLocation = petLocation;
    }

    public String getPetImg() {
        return petImg;
    }

    public void setPetImg(String petImg) {
        this.petImg = petImg;
    }

    public String getPetLink() {
        return petLink;
    }

    public void setPetLink(String petLink) {
        this.petLink = petLink;
    }

    public String getPetID() {
        return petID;
    }

    public void setPetID(String petID) {
        this.petID = petID;
    }

    public PetRVModal(String petBreed, String petDescription, String petPrice, String petLocation, String petImg, String petLink, String petID) {
        this.petBreed = petBreed;
        this.petDescription = petDescription;
        this.petPrice = petPrice;
        this.petLocation = petLocation;
        this.petImg = petImg;
        this.petLink = petLink;
        this.petID = petID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(petBreed);
        dest.writeString(petDescription);
        dest.writeString(petPrice);
        dest.writeString(petLocation);
        dest.writeString(petImg);
        dest.writeString(petLink);
        dest.writeString(petID);
    }
}
