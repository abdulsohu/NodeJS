package edu.upenn.cis350;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

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
                      i.putExtra("Doc", usernameInput.getText().toString());
                      //startActivity(new Intent(MainActivity.this,
                      //        DoctorHomepageActivity.class));
                      startActivity(i);
                  }
                  else{
                      wrongCredentials();
                  }



                }
                else{
                   if( checkCredentials(usernameInput.getText().toString(),
                            passwordInput.getText().toString(),"patientData.json")){

                       startActivity(new Intent(MainActivity.this,
                               PatientHomepageActivity.class));
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

                if(userType.equals("Doctor")){
                    startActivity(new Intent(MainActivity.this, doctorActivity.class));

                }
                else{
                    startActivity(new Intent(MainActivity.this, patientActivity.class));

                }

            }
        });
    }

    public boolean checkCredentials(String username, String password, String filename)  {
        String json = this.loadJSONFromAsset(filename);

        try {
            final JSONObject obj = new JSONObject(json);
            final JSONArray patients =  obj.getJSONArray("patients");
            for (int i =0 ; i<patients.length();i++){
                JSONObject objectInArray = patients.getJSONObject(i);
                if(objectInArray.get("username").equals(username) && objectInArray.get("password").equals(password)){
                    return true;
                }

            }
            return false;

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


                                finish();
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
            ex.printStackTrace();
            return null;
        }
        return json;
    }



}

