package edu.upenn.cis350;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class AccessWebTask extends AsyncTask<URL, String, String> {

    protected String doInBackground(URL... urls) {
        try {
            URL url = urls[0];

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            Scanner in = new Scanner(url.openStream());
            String msg = in.nextLine();
            Log.d("msg is this", msg);
            JSONObject jo = new JSONObject(msg);
            Log.d("Json object is:", jo.toString());
            String name = jo.getString("name");
            return name;
        } catch (Exception e) {
            return e.toString();
        }
    }

    protected void onPostExecute(String msg) {
        //Implement when necessary.
    }
}
