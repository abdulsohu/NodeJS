package edu.upenn.cis350;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class DoctorHomepageActivity extends AppCompatActivity {

    public static final int BUTTON_CLICK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_homepage);
        //String msg = getIntent().getStringExtra("Doc");

    }

    public void onInventoryButtonClick(View v) {
        Intent i = new Intent(this, InventoryActivity.class);
        i.putExtra("MESSAGE", "HI");
        startActivityForResult(i, BUTTON_CLICK);
    }

    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onAppointmentButtonClick(View v) {
        Intent i = new Intent(DoctorHomepageActivity.this, AppointmentsActivity.class);
        //i.putExtra("MESSAGE", "HI");
        Toast.makeText(getApplicationContext(),"I get here!",Toast.LENGTH_SHORT).show();
        startActivity(i);
    }


}
