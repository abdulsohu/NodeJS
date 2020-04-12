package edu.upenn.cis350;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class InventoryActivity extends AppCompatActivity {
    public static final int BUTTON_CLICK = 1;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<Long> quantities = new ArrayList<>();
    private ArrayList<Long> dates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_inventory);
    }

    public void onAddButtonClick(View view) {
        EditText name = findViewById(R.id.itemName);
        EditText quantity = findViewById(R.id.quantity);
        DatePicker date = findViewById(R.id.datePicker1);

        String itemName = name.getText().toString();
        Long qty = Long.parseLong(quantity.getText().toString());
    }

}
