package edu.upenn.cis350;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static android.app.PendingIntent.getActivity;


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

        Appointment ap1 = new Appointment("fad345", "Max", "REQUESTED",
                "782-453-2049", "chest pain", "1208 Walnut Street",
                "04/06/2020 4:00 PM");
        Appointment ap2 = new Appointment("fax345", "Max", "APPROVED",
                "782-453-2049", "auto-brewery syndrome", "1208 Walnut Street",
                "04/06/2020 4:00 PM");
        Appointment ap3 = new Appointment("fad345", "Marc", "APPROVED",
                "782-353-1349", "hypertension", "1208 Walnut Street",
                "04/06/2020 4:00 PM");
        Appointment ap4 = new Appointment("fax345", "Marc", "DONE",
                "782-353-1349", "deep vein thrombosis", "1208 Walnut Street",
                "04/04/2020 4:00 PM");

        final Appointment appArr[] = {ap1, ap2, ap3, ap4};

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

                if(userType.equals("Doctor") ){
                  if(checkCredentials(usernameInput.getText().toString(),
                          passwordInput.getText().toString(),"doctorData.json")){
                      Intent i = new Intent(MainActivity.this, DoctorHomepageActivity.class);
                      Doctor d = createDoctor(usernameInput.getText().toString(),
                              passwordInput.getText().toString(),"doctorData.json");
                      i.putExtra("doctor", d);
                      i.putExtra("appointments", appArr);
                      startActivity(i);
                  }
                  else{
                      wrongCredentials();
                  }
                }
                else{
                   if( checkCredentials(usernameInput.getText().toString(),
                            passwordInput.getText().toString(),"patientData.json")){
                       Intent i = new Intent(MainActivity.this, PatientHomepageActivity.class);
                       Patient p = createPatient(usernameInput.getText().toString(),
                               passwordInput.getText().toString(),"patientData.json");
                       if (p != null) {
                           i.putExtra("patient", p);
                           i.putExtra("appointments", appArr);
                           startActivity(i);
                       }
                   }
                   else{
                       wrongCredentials();
                   }


                }

            }
        });


        // createaccount button and onclick listener
        createAccount = (Button) findViewById(R.id.createAccount);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType = spinner.getSelectedItem().toString();

                if(userType.equals("Doctor")) {
                    startActivity(new Intent(MainActivity.this, doctorActivity.class));
                }
                else {
                    startActivity(new Intent(MainActivity.this, patientActivity.class));
                }

            }
        });
    }

    public Doctor createDoctor(String username, String password, String filename) {
        String json = this.loadJSONFromAsset(filename);
        try {
            final JSONObject obj = new JSONObject(json);
            final JSONArray patients =  obj.getJSONArray("doctors");
            for (int i =0 ; i<patients.length();i++){
                JSONObject objectInArray = patients.getJSONObject(i);
                if(objectInArray.get("username").equals(username) && objectInArray.get("password").equals(password)) {
                    return new Doctor(objectInArray.get("username").toString(), objectInArray.get("password").toString(),
                            objectInArray.get("email").toString(), objectInArray.get("telephone").toString(),
                            objectInArray.get("speciality").toString(), objectInArray.get("rate").toString());
                }
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    public Patient createPatient(String username, String password, String filename) {
        String json = this.loadJSONFromAsset(filename);
        try {
            final JSONObject obj = new JSONObject(json);
            final JSONArray patients =  obj.getJSONArray("patients");
            for (int i =0 ; i<patients.length();i++){
                JSONObject objectInArray = patients.getJSONObject(i);
                if(objectInArray.get("username").equals(username) && objectInArray.get("password").equals(password)) {
                    return new Patient(objectInArray.get("username").toString(), objectInArray.get("password").toString(),
                            objectInArray.get("email").toString(), objectInArray.get("telephone").toString(),
                            objectInArray.get("fullname").toString());
                }
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    public boolean checkCredentials(String username, String password, String filename)  {
        String json = this.loadJSONFromAsset(filename);

        try {
            final JSONObject obj = new JSONObject(json);
            if (filename.equals("doctorData.json")) {
                final JSONArray patients =  obj.getJSONArray("doctors");
                for (int i =0 ; i<patients.length();i++){
                    JSONObject objectInArray = patients.getJSONObject(i);
                    if(objectInArray.get("username").equals(username) && objectInArray.get("password").equals(password)){
                        return true;
                    }

                }
                return false;
            } else {
                final JSONArray patients =  obj.getJSONArray("patients");
                for (int i =0 ; i<patients.length();i++){
                    JSONObject objectInArray = patients.getJSONObject(i);
                    if(objectInArray.get("username").equals(username) && objectInArray.get("password").equals(password)){
                        return true;
                    }

                }
                return false;
            }

        } catch (JSONException e) {
            //e.printStackTrace();
            return false;
        }
    }
    public void wrongCredentials()
    {
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(MainActivity.this);
        builder.setMessage("You entered the wrong username and/or password");
        builder.setTitle("Alert !");
        builder.setCancelable(false);
        builder
                .setPositiveButton(
                        "Return to login",
                        new DialogInterface
                                .OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {
                                dialog.dismiss();
                            }
                        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = this.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            Log.d("size", "poop");
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}

