package com.example.petcare;

import android.os.Parcel;
import android.os.Parcelable;

public class FoodRVModal implements Parcelable {
    private String sellerName;
    private String brandName;
    private String foodType;
    private String weight;
    private String price;
    private String contactNumber;
    private String description;
    private String image;
    private String sellerId;

    public FoodRVModal(){

    }

    protected FoodRVModal(Parcel in) {
        sellerName = in.readString();
        brandName = in.readString();
        foodType = in.readString();
        weight = in.readString();
        price = in.readString();
        contactNumber = in.readString();
        description = in.readString();
        image = in.readString();
        sellerId = in.readString();
    }

    public static final Creator<FoodRVModal> CREATOR = new Creator<FoodRVModal>() {
        @Override
        public FoodRVModal createFromParcel(Parcel in) {
            return new FoodRVModal(in);
        }

        @Override
        public FoodRVModal[] newArray(int size) {
            return new FoodRVModal[size];
        }
    };

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public FoodRVModal(String sellerName, String brandName, String foodType, String weight, String price, String contactNumber, String description, String image, String sellerId) {
        this.sellerName = sellerName;
        this.brandName = brandName;
        this.foodType = foodType;
        this.weight = weight;
        this.price = price;
        this.contactNumber = contactNumber;
        this.description = description;
        this.image = image;
        this.sellerId = sellerId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sellerName);
        dest.writeString(brandName);
        dest.writeString(foodType);
        dest.writeString(weight);
        dest.writeString(price);
        dest.writeString(contactNumber);
        dest.writeString(description);
        dest.writeString(image);
        dest.writeString(sellerId);
    }
}
