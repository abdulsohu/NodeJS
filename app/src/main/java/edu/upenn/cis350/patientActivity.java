package edu.upenn.cis350;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class patientActivity extends AppCompatActivity {

    EditText username,password,email,telephone,fullName;
    Button create_account;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        username = (EditText) findViewById(R.id.editText2);
        password = (EditText) findViewById(R.id.editText3);
        email = (EditText) findViewById(R.id.editText5);
        telephone = (EditText) findViewById(R.id.editText6);
        fullName = (EditText) findViewById(R.id.editText4);

        create_account = (Button) findViewById(R.id.button);
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write to Json file
                // go to patient homepage
                startActivity(new Intent(patientActivity.this, PatientHomepageActivity.class));




            }
        });

    }

}
