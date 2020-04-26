package edu.upenn.cis350;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AppoitmentsActivity extends AppCompatActivity {
    Appointment appointment = null;
    Patient patient = null;
    Appointment[] appArr = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoitments);
        patient = (Patient) getIntent().getSerializableExtra("patient");
        appArr = (Appointment[]) getIntent().getSerializableExtra("appointments");
        appointment = (Appointment) getIntent().getSerializableExtra("appointment");

    }

    public void onStartButtonClick(View view) {
        Intent i = new Intent(this, PatientHomepageActivity.class);
        i.putExtra("appointments", appArr);
        i.putExtra("patient", patient);
        startActivity(i);
    }
}
