package edu.upenn.cis350;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AddItem extends AppCompatActivity {
    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August",
            "September", "October", "November", "December"};
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_page);
        queue = Volley.newRequestQueue(this);
    }

    public void onAddButtonClick(View view) {
        EditText name = findViewById(R.id.name);
        EditText quantity = findViewById(R.id.quantity);
        DatePicker datePicker = findViewById(R.id.datePicker);

        //Get item name and quantity:
        String itemName = name.getText().toString();
        String qty = quantity.getText().toString();

        //Get date:
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        String date = months[month] + " " + String.valueOf(day) + ", " + String.valueOf(year);

        ArrayList<String> result = new ArrayList<>();
        result.add(itemName);
        result.add(qty);
        result.add(date);

        post(result);

        Intent returnIntent = new Intent();
        returnIntent.putStringArrayListExtra("Result", result);
        setResult(Activity.RESULT_OK, returnIntent);
        Toast.makeText(getApplicationContext(), "Item added to inventory!",
                Toast.LENGTH_SHORT).show();
        finish();
    }

    private void post(ArrayList<String> result) {
        final JSONObject item = new JSONObject();

        try {
            item.put("name", result.get(0));
            item.put("quantity", result.get(1));
            item.put("lastUpdated", result.get(2));
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Can not add a negative quantity.",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        String url = "http://10.0.2.2:3000/items";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, item,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                "Can not add a negative quantity for an item.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(request);
    }

}
