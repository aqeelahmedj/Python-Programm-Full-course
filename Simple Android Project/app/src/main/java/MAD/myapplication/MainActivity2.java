package MAD.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.loader.content.AsyncTaskLoader;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab_Fragment_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab_Fragment_1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText ed_url =;
    private EditText ed_urlcontent;
//    private Button btn_fetch;
//    private Button btn_fetchloader;
//    private Button btn_fetchvolley;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Tab_Fragment_1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab_Fragment_1.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab_Fragment_1 newInstance(String param1, String param2) {
        Tab_Fragment_1 fragment = new Tab_Fragment_1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        /// Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_tab__1, null);

        ed_urlcontent = root.findViewById(R.id.ed_urlcontent);
        ed_url = root.findViewById(R.id.ed_url);

        btn_fetch = root.findViewById(R.id.btn_fetch);
        btn_fetch.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RemoteContentTask RCT = new RemoteContentTask();
                        String url = ed_url.getText().toString();
                        RCT.execute(url);
                    }
                }
        );

        ed_url.addTextChangedListener(new TextWatcher()  {
                                          public void afterTextChanged(Editable s) {
                                          }

                                          public void beforeTextChanged(CharSequence s, int start, int count, int after)  { }
                                          public void onTextChanged(CharSequence s, int start, int before, int count)  {
                                              ed_urlcontent.setText("");
                                          }
                                      }
        );

        btn_fetchvolley = root.findViewById(R.id.btn_fetchvolley);
        btn_fetchvolley.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestQueue queue = Volley.newRequestQueue(Tab_Fragment_1.this.getContext());
                        String url = ed_url.getText().toString();

                        StringRequest stringRequest = new StringRequest
                                (Request.Method.GET, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        ed_urlcontent.setText(response);
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        ed_urlcontent.setText("That didn't work!");
                                    }
                                }
                                );
                        queue.add(stringRequest);
                    }
                }
        );

        return root;
    }

    private class RemoteContentTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            try {
                URL url = new URL(urls[0]);
                try {
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setDoOutput(false);
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    try {
                        InputStream inputStream;
                        int status = urlConnection.getResponseCode();

                        if (status != HttpURLConnection.HTTP_OK)  {
                            inputStream = urlConnection.getErrorStream();
                        } else  {
                            inputStream = urlConnection.getInputStream();
                        }
                        response = readStream(inputStream);
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (java.io.IOException ioex) {
                    response = ioex.toString();
                    Log.e("PlaceholderFragment", "Error ", ioex);
                }
            } catch (MalformedURLException muex) {
                response = muex.toString();
                Log.e("PlaceholderFragment", "Error ", muex);
            } catch (Exception e) {
                response = e.toString();
                Log.e("PlaceholderFragment", "Error ", e);
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println(result);
            ed_urlcontent.setText(result);
        }

        private String readStream(InputStream is) {
            try {
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                int i = is.read();
                while(i != -1) {
                    bo.write(i);
                    i = is.read();
                }
                return bo.toString();
            } catch (IOException e) {
                return "";
            }
        }
    }

}