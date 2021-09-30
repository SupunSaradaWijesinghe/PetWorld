package com.example.petcare;


import android.os.Parcel;
import android.os.Parcelable;

public class PetRequest implements Parcelable {
    private String petCategory;
    private String remarks;
    private String petBreed;
    private String qty;
    private String budget;
    private String contactNo;
    private String requestID;

    //constructor
    public PetRequest(){

    }

    //overloaded constructor
    public PetRequest(String petCategory, String remarks, String petBreed, String qty, String budget, String contactNo, String requestID) {
        this.petCategory = petCategory;
        this.remarks = remarks;
        this.petBreed = petBreed;
        this.qty = qty;
        this.budget = budget;
        this.contactNo = contactNo;
        this.requestID = requestID;
    }

    //all the getters and setters


    protected PetRequest(Parcel in) {
        petCategory = in.readString();
        remarks = in.readString();
        petBreed = in.readString();
        qty = in.readString();
        budget = in.readString();
        contactNo = in.readString();
        requestID = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(petCategory);
        dest.writeString(remarks);
        dest.writeString(petBreed);
        dest.writeString(qty);
        dest.writeString(budget);
        dest.writeString(contactNo);
        dest.writeString(requestID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PetRequest> CREATOR = new Creator<PetRequest>() {
        @Override
        public PetRequest createFromParcel(Parcel in) {
            return new PetRequest(in);
        }

        @Override
        public PetRequest[] newArray(int size) {
            return new PetRequest[size];
        }
    };

    public String getPetCategory() {
        return petCategory;
    }

    public void setPetCategory(String petCategory) {
        this.petCategory = petCategory;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }
}
