package edu.upenn.cis350;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class InventoryActivity extends AppCompatActivity {
    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August",
            "September", "October", "November", "December"};
    public static final int ADD_ITEM_CLICK = 1;
    public static final int EDIT_ITEMS_CLICK = 2;
    private RequestQueue queue;
    List<List<String>> allItems = new ArrayList<>();
    boolean fullTableShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_inventory);
        queue = Volley.newRequestQueue(this);
        parseJson();
    }

    private void parseJson() {
        //Get table and clear all rows
        TableLayout inventory = (TableLayout) findViewById(R.id.table);
        int count = inventory.getChildCount();
        for (int i = 1; i < count; i++) {
            View child = inventory.getChildAt(i);
            if (child instanceof TableRow) {
                ((ViewGroup) child).removeAllViews();
            }
        }

        String url = "http://10.0.2.2:3000/items";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray items = response.getJSONArray("items");

                            for (int i = 0; i < items.length(); i++) {
                                JSONObject item = items.getJSONObject(i);

                                String name = item.getString("name");
                                String quantity = String.valueOf(item.getInt("quantity"));
                                String jsDate = item.getString("lastUpdated");
                                String id = item.getString("_id");

                                String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX";
                                DateFormat df = new SimpleDateFormat(pattern);
                                Calendar calendar = Calendar.getInstance();
                                Date date = df.parse(jsDate);
                                calendar.setTime(date);

                                String dateToBeAdded =
                                        months[calendar.get(calendar.MONTH)] + " " +
                                                calendar.get(calendar.DAY_OF_MONTH) + ", " +
                                                calendar.get(calendar.YEAR);

                                List<String> list = new ArrayList<>();
                                list.add(name);
                                list.add(quantity);
                                list.add(dateToBeAdded);
                                list.add(id);

                                fillTable(list);
                                allItems.add(list);
                            }
                        } catch (JSONException | ParseException e) {
                            Toast.makeText(getApplicationContext(), "Something went wrong" +
                                    " with the fetching items from database.", Toast.LENGTH_SHORT);
                            Log.e("Tag", "we arrive here", e);
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Something went wrong" +
                                " with the network connection.", Toast.LENGTH_SHORT);
                        error.printStackTrace();
                    }
                });

        queue.add(request);
    }

    public void onDetailsClickButton(View view) {
        TableLayout table = findViewById(R.id.table);
        Button detailsButton = findViewById(R.id.detailsButton);
        table.setColumnCollapsed(1, fullTableShown);
        table.setColumnCollapsed(2, fullTableShown);

        if (fullTableShown) {
            fullTableShown = false;
            detailsButton.setText("Show Details");
        } else {
            fullTableShown = true;
            detailsButton.setText("Hide Details");
        }
    }

    public void onAddItemButton(View view) {
        Intent intent = new Intent(this, AddItem.class);
        startActivityForResult(intent, ADD_ITEM_CLICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ITEM_CLICK) {
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<String> result = data.getStringArrayListExtra("Result");
                allItems.add(result);
                fillTable(result);
            }

            if (resultCode == Activity.RESULT_CANCELED) {
                //Do nothing here, as of now
            }
        }

        if (requestCode == EDIT_ITEMS_CLICK) {
            parseJson();
        }
    }


    public void fillTable(List<String> item) {
        //Get table
        TableLayout inventory = (TableLayout) findViewById(R.id.table);

        //Get row format
        LayoutInflater inflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TableRow row = (TableRow) inflater.inflate(R.layout.tablerow, null);

        String itemName = item.get(0);
        String itemQuantity = item.get(1);
        String itemDate = item.get(2);

        //Get children in row and set values
        TextView name = (TextView) row.getVirtualChildAt(0);
        name.setText(itemName);
        TextView quantity = (TextView) row.getVirtualChildAt(1);
        quantity.setText(itemQuantity);
        TextView date = (TextView) row.getVirtualChildAt(2);
        date.setText(itemDate);

        //Add row to table
        inventory.addView(row);
    }

    public void onEditItemsButtonClick(View view) {
        Intent intent = new Intent(this, EditItems.class);
        startActivityForResult(intent, EDIT_ITEMS_CLICK);
    }

}
