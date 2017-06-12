package com.appstud.parking.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PlaceModel implements Parcelable {

    private Double latitude;
    private Double longitude;
    private String name;
    private String type;
    private String content;
    private String picture;
    private String address;

    public PlaceModel(Double latitude, Double longitude, String name, String type, String content, String picture, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.type = type;
        this.content = content;
        this.picture = picture;
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    @Override
    public String toString() {
        return "PlaceModel{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", type='" + type + '\'' +
                ", picture='" + picture + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeString(this.content);
        dest.writeString(this.picture);
        dest.writeString(this.address);
    }

    protected PlaceModel(Parcel in) {
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.name = in.readString();
        this.type = in.readString();
        this.content = in.readString();
        this.picture = in.readString();
        this.address = in.readString();
    }

    public static final Creator<PlaceModel> CREATOR = new Creator<PlaceModel>() {
        @Override
        public PlaceModel createFromParcel(Parcel source) {
            return new PlaceModel(source);
        }

        @Override
        public PlaceModel[] newArray(int size) {
            return new PlaceModel[size];
        }
    };

    public String getAddress() {
        return address;
    }
}
