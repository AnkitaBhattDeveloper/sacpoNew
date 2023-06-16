package za.co.sacpo.grantportal.xCubeLib.model;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

public class PlaceInfo {

    private String name;
    private String address;
    private String phonenumber;
    private String id;
    private Uri WebsiteUri;
    private LatLng latLng;
    private float rating;
    private String attributions;


    public PlaceInfo(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Uri getWebsiteUri() {
        return WebsiteUri;
    }

    public void setWebsiteUri(Uri websiteUri) {
        WebsiteUri = websiteUri;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getAttributions() {
        return attributions;
    }

    public void setAttributions(String attributions) {
        this.attributions = attributions;
    }


    @Override
    public String toString() {
        return "PlaceInfo{" + "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phonenumber='" + phonenumber +
                '\'' + ", id='" + id + '\'' +
                ", WebsiteUri=" + WebsiteUri +
                ", latLng=" + latLng +
                ", rating=" + rating +
                ", attributions='" +
                attributions +
                '\'' +
                '}';
    }
}
