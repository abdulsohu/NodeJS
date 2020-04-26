package edu.upenn.cis350;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditItems extends AppCompatActivity {

    private ItemsAdapter adapter;
    private RequestQueue queue;
    private ListView listView;
    ArrayList<Item> allItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_items_listview);
        allItems = new ArrayList<>();
        queue = Volley.newRequestQueue(this);
        listView = (ListView) findViewById(R.id.list);
        adapter = new ItemsAdapter(EditItems.this, allItems);
        listView.setAdapter(adapter);
        update();
        init();
    }

    public void init() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Item touched = (Item) adapter.getItem(i);
                openDialog(touched, i);
            }
        });
    }

    private void update() {
        allItems.clear();
        String url = "http://10.0.2.2:3000/items";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray items = response.getJSONArray("items");
                            for (int i = 0; i < items.length(); i++) {
                                allItems.add(new Item(items.getJSONObject(i)));
                                adapter.notifyDataSetChanged();
                                Log.d("item", allItems.get(i).toString());
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Something went wrong" +
                                            " while fetching items from database.",
                                    Toast.LENGTH_SHORT).show();
                            Log.e("Tag", "we arrive here", e);
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Something went wrong" +
                                " with the network connection.", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });
        queue.add(request);
        listView.invalidateViews();
    }


    public void openDialog(final Item touched, final int position) {
        final Dialog dialog = new Dialog(EditItems.this);

        dialog.setTitle("Edit Item");
        dialog.setContentView(R.layout.edit_dialog);

        final TextView name = (TextView) dialog.findViewById(R.id.edit_name);
        final TextView quantity = (TextView) dialog.findViewById(R.id.edit_quantity);
        Button edit = (Button) dialog.findViewById(R.id.edit);
        Button delete = (Button) dialog.findViewById(R.id.delete);

        name.setText(touched.name);
        quantity.setText(touched.quantity);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("(new) Name, Quantity", name.getText() + ", " + quantity.getText());
                patchItem(touched, (String) name.getText().toString(),
                        (String) quantity.getText().toString());

                update();
                init();
                listView.invalidateViews();
                Toast.makeText(getApplicationContext(),
                        "Item updated!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(touched, (String) name.getText().toString(),
                        (String) quantity.getText().toString());

                update();
                init();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void patchItem(Item touched, String name, String quantity) {
        StringBuffer url = new StringBuffer("http://10.0.2.2:3000/items/");
        url.append(touched.id);

        final JSONObject newItem = new JSONObject();

        try {
            newItem.put("name", name);
            newItem.put("quantity", quantity);
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Cannot edit item at the moment.",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PATCH, url.toString(),
                newItem,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                "Invalid parameters provided to change item.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(request);
    }

    private void deleteItem(Item touched, String name, String quantity) {
        StringBuffer url = new StringBuffer("http://10.0.2.2:3000/items/");
        url.append(touched.id);

        final JSONObject newItem = new JSONObject();

        try {
            newItem.put("name", name);
            newItem.put("quantity", quantity);
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Can not add a negative quantity.",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url.toString(),
                newItem,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Item deleted!",
                                Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                "Can not delete item.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(request);
    }
}
