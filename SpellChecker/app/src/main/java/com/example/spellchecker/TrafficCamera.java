
package com.example.spellchecker;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class TrafficCamera implements Serializable {

    @SerializedName("ypos")
    @Expose
    private String ypos;
    @SerializedName("xpos")
    @Expose
    private String xpos;
    @SerializedName("cameralabel")
    @Expose
    private String cameralabel;
    @SerializedName("videourl")
    @Expose
    private String videourl;
    @SerializedName("weburl")
    @Expose
    private String weburl;
    @SerializedName("imageurl")
    @Expose
    private Imageurl imageurl;
    @SerializedName("ownershipcd")
    @Expose
    private String ownershipcd;
    @SerializedName("location")
    @Expose
    private Location location;


    public String getYpos() {
        return ypos;
    }

    public String getXpos() {
        return xpos;
    }

    public String getCameralabel() {
        return cameralabel;
    }

    public String getVideourl() {
        return videourl;
    }

    public String getWeburl() {
        return weburl;
    }

    public Imageurl getImageurl() {
        return imageurl;
    }

    public String getOwnershipcd() {
        return ownershipcd;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "TrafficCamera{" + "ypos='" + ypos + '\'' + ", xpos='" + xpos + '\'' + ", cameralabel='" + cameralabel + '\'' +
                ", videourl='" + videourl + '\'' + ", weburl='" + weburl + '\'' + ", imageurl=" + imageurl +
                ", ownershipcd='" + ownershipcd + '\'' + ", location=" + location + '}';
    }

    public class Imageurl implements Serializable {

        @SerializedName("url")
        @Expose
        private String url;
        public String getUrl() {
            return url;
        }

        @Override
        public String toString() {
            return "Imageurl{" + "url='" + url + '\'' + '}';
        }
    }

    public class Location implements Serializable {

        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("needs_recoding")
        @Expose
        private Boolean needsRecoding;
        @SerializedName("longitude")
        @Expose
        private String longitude;


        public String getLatitude() {
            return latitude;
        }

        public Boolean getNeedsRecoding() {
            return needsRecoding;
        }

        public String getLongitude() {
            return longitude;
        }

        @Override
        public String toString() {
            return "Location{" + "latitude='" + latitude + '\'' + ", needsRecoding=" + needsRecoding + ", longitude='" + longitude + '\'' + '}';
        }
    }

}
