package com.ekta.marvel.network.response.Comics;

/**
 * Created by Ekta on 11-06-2017.
 */

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Creators implements Serializable, Parcelable
{

    @SerializedName("available")
    @Expose
    private Integer available;
    @SerializedName("collectionURI")
    @Expose
    private String collectionURI;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    @SerializedName("returned")
    @Expose
    private Integer returned;
    public final static Parcelable.Creator<Creators> CREATOR = new Creator<Creators>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Creators createFromParcel(Parcel in) {
            Creators instance = new Creators();
            instance.available = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.collectionURI = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.items, (com.ekta.marvel.network.response.Comics.Item.class.getClassLoader()));
            instance.returned = ((Integer) in.readValue((Integer.class.getClassLoader())));
            return instance;
        }

        public Creators[] newArray(int size) {
            return (new Creators[size]);
        }

    }
            ;
    private final static long serialVersionUID = 9220120685761575931L;

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public String getCollectionURI() {
        return collectionURI;
    }

    public void setCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getReturned() {
        return returned;
    }

    public void setReturned(Integer returned) {
        this.returned = returned;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(available);
        dest.writeValue(collectionURI);
        dest.writeList(items);
        dest.writeValue(returned);
    }

    public int describeContents() {
        return 0;
    }

}
