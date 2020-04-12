package edu.upenn.cis350;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class InventoryActivity extends AppCompatActivity {
    public static final int BUTTON_CLICK = 1;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<Long> quantities = new ArrayList<>();
    private ArrayList<String> dates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_inventory);
    }

    public void onAddButtonClick(View view) {
        EditText name = findViewById(R.id.itemName);
        EditText quantity = findViewById(R.id.quantity);
        DatePicker datePicker = findViewById(R.id.datePicker);

        String itemName = name.getText().toString();
        Long qty = Long.parseLong(quantity.getText().toString());

        int day  = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = sdf.format(calendar.getTime());

        names.add(itemName);
        quantities.add(qty);
        dates.add(formattedDate);

        TableLayout table = (TableLayout) findViewById(R.id.table);
        TableRow header = (TableRow) findViewById(R.id.headerRow);

    }

}
