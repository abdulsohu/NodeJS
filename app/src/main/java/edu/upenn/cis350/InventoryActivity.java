package edu.upenn.cis350;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class InventoryActivity extends AppCompatActivity {
    public static final int BUTTON_CLICK = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_inventory);
    }
}
