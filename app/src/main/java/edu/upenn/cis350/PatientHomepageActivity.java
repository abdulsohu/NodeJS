package edu.upenn.cis350;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PatientHomepageActivity extends AppCompatActivity {
    Appointment appointment = null;
    Patient patient = null;
    Appointment[] appArr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_homepage);
        patient = (Patient) getIntent().getSerializableExtra("patient");
        appArr = (Appointment[]) getIntent().getSerializableExtra("appointments");

        for (int i = 0; i < appArr.length; i++) {
            System.out.println(appArr[i].dUsername);
        }

        int c = 0;
        for (int i = 0; i < appArr.length; i++) {
            if(appArr[i].pUsername.equals(patient.username)) {
                c++;
            }
        }
        Appointment[] appDates = new Appointment[c];
        int j = 0;
        for (int i = 0; i < appArr.length; i++) {
            if(appArr[i].pUsername.equals(patient.username)) {
                appDates[j] = appArr[i];
                j++;
            }
        }
        TextView nameView = (TextView) findViewById(R.id.patientName);
        nameView.setText("Hi " + patient.fullname);
        appointment = appArr[1];
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<Appointment> adapter = new ArrayAdapter<Appointment>
                (this, android.R.layout.simple_spinner_item,
                        appDates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void onStartButtonClick(View view) {
        Intent i = new Intent(this, AppoitmentsActivity.class);
        i.putExtra("appointment", appointment);
        i.putExtra("patient", patient);
        i.putExtra("appointments", appArr);
        startActivity(i);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        appointment = (Appointment) parent.getItemAtPosition(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }
}
