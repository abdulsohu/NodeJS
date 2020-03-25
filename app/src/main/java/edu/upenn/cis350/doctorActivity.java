package edu.upenn.cis350;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class doctorActivity extends AppCompatActivity {
    EditText username, password, telephone, email, speciality, hourly_rate;
    Button create_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        username = (EditText) findViewById(R.id.editText2);
        password= (EditText) findViewById(R.id.editText3);
        telephone = (EditText) findViewById(R.id.editText6);
        email = (EditText) findViewById(R.id.editText5);
        speciality = (EditText) findViewById(R.id.editText7);
        hourly_rate = (EditText) findViewById(R.id.editText8);

        create_account = (Button) findViewById(R.id.button);
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write to Json file
                // go To doctor homepage
                startActivity(new Intent(doctorActivity.this, DoctorHomepageActivity.class));





            }
        });




    }
}
