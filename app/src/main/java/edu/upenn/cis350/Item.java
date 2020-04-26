package edu.upenn.cis350;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.net.*;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Item implements Parcelable {
    public String name;
    public String quantity;
    public String date;
    public String id;

    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August",
            "September", "October", "November", "December"};

    public Item(String name, String quantity, String providedDate, String id) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.date = providedDate;
    }

    public Item(JSONObject item) {
        try {
            this.name = item.getString("name");
            this.quantity = String.valueOf(item.getInt("quantity"));
            this.id = item.getString("_id");

            String jsDate = item.getString("lastUpdated");
            String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX";
            DateFormat df = new SimpleDateFormat(pattern);
            Calendar calendar = Calendar.getInstance();
            Date date = df.parse(jsDate);
            calendar.setTime(date);


            this.date = months[calendar.get(calendar.MONTH)] + " " +
                            calendar.get(calendar.DAY_OF_MONTH) + ", " +
                            calendar.get(calendar.YEAR);

        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
    }

    protected Item(Parcel in) {
        name = in.readString();
        quantity = in.readString();
        date = in.readString();
        id = in.readString();
        months = in.createStringArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(quantity);
        dest.writeString(date);
        dest.writeString(id);
        dest.writeStringArray(months);
    }

    public static ArrayList<Item> fromJson(JSONArray jsonArray) {
        ArrayList<Item> items = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                items.add(new Item(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return items;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", quantity='" + quantity + '\'' +
                ", date='" + date + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
