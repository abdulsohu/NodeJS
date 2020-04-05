package edu.upenn.cis350;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

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

                String json = loadJSONFromAsset("doctorData.json");
                try {
                    final JSONObject obj = new JSONObject(json);
                    final JSONArray docs = (JSONArray) obj.getJSONArray("doctors");
                    final JSONObject obj2 = new JSONObject(json);
                    obj2.put("username", username.getText().toString());
                    obj2.put("password", password.getText().toString());
                    obj2.put("telephone", telephone.getText().toString());
                    obj2.put("email", email.getText().toString());
                    obj2.put("speciality", speciality.getText().toString());
                    obj2.put("hourly_rate",hourly_rate.getText().toString());
                    docs.put(obj2);


                    try {
                        FileWriter file = new FileWriter("doctorData.json");
                        file.write(docs.toString());
                        file.flush();
                        file.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (JSONException e) {
                    //e.printStackTrace();

                }
                startActivity(new Intent(doctorActivity.this,DoctorHomepageActivity.class));





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
