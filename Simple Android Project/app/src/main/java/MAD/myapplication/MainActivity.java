package MAD.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.text);

// ...
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
       //      String server_url ="http://mad.mywork.gr/authenticate.php?t=429720";
                //     String server_url ="https://projects.vishnusivadas.com/testing/read.php";
               //      String server_url = "https://www.google.com";
                    // Request a string response from the provided URL.
             //   String server_url1 = server_url.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://mad.mywork.gr/authenticate.php?t=429720",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                     //   processDataUsingXMLReader(reponse);
                        textView.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    }