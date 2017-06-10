package com.ekta.marvel.network.response.Comics;

/**
 * Created by Ekta on 11-06-2017.
 */

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Date implements Serializable, Parcelable
{

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("date")
    @Expose
    private String date;
    public final static Parcelable.Creator<Date> CREATOR = new Creator<Date>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Date createFromParcel(Parcel in) {
            Date instance = new Date();
            instance.type = ((String) in.readValue((String.class.getClassLoader())));
            instance.date = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Date[] newArray(int size) {
            return (new Date[size]);
        }

    }
            ;
    private final static long serialVersionUID = -8738644007043950696L;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeValue(date);
    }

    public int describeContents() {
        return 0;
    }

}