package edu.upenn.cis350;

import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

                String json = loadJSONFromAsset("patientData.json");
                try {
                    final JSONObject obj = new JSONObject(json);
                    final JSONArray docs = (JSONArray) obj.getJSONArray("doctors");
                    final JSONObject obj2 = new JSONObject(json);
                    obj2.put("username", username.getText().toString());
                    obj2.put("password", password.getText().toString());
                    obj2.put("telephone", telephone.getText().toString());
                    obj2.put("email", email.getText().toString());
                    obj2.put("fullname", fullName.getText().toString());

                    docs.put(obj2);


                    try {
                        FileWriter file = new FileWriter("patientData.json");
                        file.write(docs.toString());
                        file.flush();
                        file.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (JSONException e) {
                    //e.printStackTrace();

                }





                startActivity(new Intent(patientActivity.this, PatientHomepageActivity.class));





            }
        });

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
