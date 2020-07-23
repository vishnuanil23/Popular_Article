package com.example.sampletest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Adapter adapter;
    String imageurl;
    ArrayList<String> array_image = new ArrayList<String>();
    ArrayList<String> array_title = new ArrayList<String>();
    ArrayList<String> array_subtitle = new ArrayList<String>();
    ArrayList<String> array_detailview = new ArrayList<String>();
    ArrayList<String> array_publishedby = new ArrayList<String>();
    ArrayList<String> array_published_date = new ArrayList<String>();
    private  static String json_url="http://api.nytimes.com/svc/mostpopular/v2/mostviewed/all-sections/7.json?api-key=9XwGAupuA4rKZWjTge3gwpiWtTXq1CDj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recview);
        extractJson();

    }

    private void extractJson() {
        RequestQueue queue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonobjRequest= new JsonObjectRequest(Request.Method.GET, json_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jObject = null;
                try {
                    jObject = new JSONObject(response.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                 if(jObject.getString("status").equalsIgnoreCase("ok")){
                     JSONArray jArray = jObject.getJSONArray("results");
                        Log.e("JSONArrayLENGTH", ""+jArray.length());
                     for (int i = 0; i < jArray.length(); i++) {
                         try {
                             JSONObject newsObject = jArray.getJSONObject(i);

                             array_title.add(newsObject.getString("title").toString());
                             array_subtitle.add(newsObject.getString("abstract").toString());
                             array_publishedby.add(newsObject.getString("byline").toString());
                             array_published_date.add(newsObject.getString("published_date").toString());
                             array_detailview.add(newsObject.getString("url"));

                             JSONArray jArray1 = newsObject.getJSONArray("media");
                             for (int j = 0; j < jArray1.length(); j++){
                                 JSONObject newsObject1 = jArray1.getJSONObject(j);
                                 JSONArray jArray2 = newsObject1.getJSONArray("media-metadata");

                                 for (int k = 0; k < jArray2.length(); k++){
                                     JSONObject newsObject2 = jArray2.getJSONObject(k);
                                     imageurl= newsObject2.getString("url");
                                 }
                                 array_image.add(imageurl);
                             }

                         } catch (JSONException e) {
                             e.printStackTrace();
                         }
                     }

                 }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter= new Adapter(getApplicationContext(),array_image,array_publishedby,array_published_date,array_title,array_subtitle,array_detailview);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR RESPONSE","OnErrorResponse");
            }
        });
                queue.add(jsonobjRequest);

    }



}