package edu.upenn.cis350;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppointmentsActivity extends AppCompatActivity {
    Map<String, String> appointments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appointments = new HashMap<String, String>();
        appointments.put("jack", "pending");
        appointments.put("penny", "accepted");
        appointments.put("jill", "accepted");
        appointments.put("mommy", "pending");
        appointments.put("daddy", "accepted");
        appointments.put("baby", "pending");
        Toast.makeText(this, "I can't do it man!", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appoitments);

//        String json = loadJSONFromAsset("appointmentData.json");
//        try {
//            final JSONObject obj = new JSONObject(json);
//            final JSONArray docs = (JSONArray) obj.getJSONArray("appointments");
//            final JSONObject obj2 = new JSONObject(json);
//            obj2.put("name", "jack string");
//            obj2.put("status", "pending");
//            docs.put(obj2);
//
//
//            try {
//                FileWriter file = new FileWriter("doctorData.json");
//                file.write(docs.toString());
//                file.flush();
//                file.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        } catch (JSONException e) {
//            //e.printStackTrace();
//
//        }
//
//        String json2 = loadJSONFromAsset("appointmentData.json");
//        try {
//            final JSONObject obj = new JSONObject(json2);
//            final JSONArray docs = (JSONArray) obj.getJSONArray("appointments");
//            final JSONObject obj2 = new JSONObject(json2);
//            obj2.put("name", "Max Strong");
//            obj2.put("status", "accepted");
//            docs.put(obj2);
//
//
//            try {
//                FileWriter file = new FileWriter("doctorData.json");
//                file.write(docs.toString());
//                file.flush();
//                file.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        } catch (JSONException e) {
//            //e.printStackTrace();
//
//        }


        updateSpinner();
    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        // On selecting a spinner item
//        String item = parent.getItemAtPosition(position).toString();
//
//        // Showing selected spinner item
//        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
//    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void updateSpinner() {
        // Spinner element
        Spinner upcomingSpinner = (Spinner) findViewById(R.id.upcoming_spinner);
        Spinner pendingSpinner = (Spinner) findViewById(R.id.pending_spinner);

        // Spinner click listener
        //upcomingSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        //pendingSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        // Spinner Drop down elements
        List<String> pendingAppointments = new ArrayList<String>();
        List<String> upcomingAppointment = new ArrayList<String>();

        pendingAppointments.add(" ");
        upcomingAppointment.add(" ");
        for (Map.Entry<String, String> one : appointments.entrySet()) {
            if (one.getValue().equals("pending")) {
                pendingAppointments.add(one.getKey());
            } else if (one.getValue().equals("accepted")) {
                upcomingAppointment.add(one.getKey());
            }
        }



        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterU = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, upcomingAppointment);
        ArrayAdapter<String> dataAdapterP = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pendingAppointments);

        // Drop down layout style - list view with radio button
        dataAdapterU.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        upcomingSpinner.setAdapter(dataAdapterU);
        pendingSpinner.setAdapter(dataAdapterP);
    }




//    public String loadJSONFromAsset(String filename) {
//        String json = null;
//        try {
//            InputStream is = this.getAssets().open(filename);
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        return json;
//    }

    public void onAcceptButtonClick(View v) {
        //Intent i = new Intent(this, InventoryActivity.class);
        Spinner spinner = (Spinner) findViewById(R.id.pending_spinner);
        String level = spinner.getSelectedItem().toString();
        if (!level.equals(" ")) {
            appointments.put(level, "accepted");
            updateSpinner();
        }
        //i.putExtra("MESSAGE", "HI");
        //startActivityForResult(i, 1);
    }

    public void onRejectButtonClick(View v) {
//        Intent i = new Intent(this, AppointmentsActivity.class);
//        i.putExtra("MESSAGE", "HI");
//        startActivityForResult(i, 1);

        Spinner spinner = (Spinner) findViewById(R.id.pending_spinner);
        String level = spinner.getSelectedItem().toString();
        if (!level.equals(" ")) {
            appointments.put(level, "rejected");
            updateSpinner();
        }
    }

    public void onEditButtonClick(View v) {
        //Intent i = new Intent(this, EditAppointment.class);
        //i.putExtra("MESSAGE", "HI");
        //startActivityForResult(i, 1);


    }

}
