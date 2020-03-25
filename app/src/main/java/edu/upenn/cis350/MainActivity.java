package edu.upenn.cis350;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity {
    String name, email;
    EditText usernameInput, passwordInput;
    Button login, createAccount;
    String userType = "Doctor";
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner spinner = (Spinner) findViewById(R.id.users_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.users_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);


        //user enters username and password
        usernameInput= (EditText) findViewById(R.id.usernameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);


        // login button and onclick listener
        login= (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if userName and password are correct

                userType = spinner.getSelectedItem().toString();

                if(userType.equals("Doctor")){
                    startActivity(new Intent(MainActivity.this, DoctorHomepageActivity.class));

                }
                else{
                    startActivity(new Intent(MainActivity.this, PatientHomepageActivity.class));

                }




            }
        });


        // createaccount button and onclick listener
        createAccount = (Button) findViewById(R.id.createAccount);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType = spinner.getSelectedItem().toString();

                if(userType.equals("Doctor")){
                    startActivity(new Intent(MainActivity.this, doctorActivity.class));

                }
                else{
                    startActivity(new Intent(MainActivity.this, patientActivity.class));

                }

            }
        });













    }
}

