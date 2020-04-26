package edu.upenn.cis350;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ItemsAdapter extends ArrayAdapter {

    public ItemsAdapter(Context context, ArrayList<Item> items) {
        super(context, R.layout.item_view, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Item item = (Item) getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.item_view, parent, false);
        }

        // Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView quantity = (TextView) convertView.findViewById(R.id.quantity);
        TextView updated = (TextView) convertView.findViewById(R.id.updated);

        // Populate the data into the template view using the data object
        name.setText(item.name);
        quantity.setText(item.quantity);
        updated.setText(item.date);

        return convertView;
    }

}
