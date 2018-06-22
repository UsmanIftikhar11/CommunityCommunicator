package com.example.communitycommunicator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Circles extends AppCompatActivity {

    private ImageButton btn_createCircle;

    List<CircleVariables> productList;

    RecyclerView circle_list;

    private static final String URL_LOADCIRCLE = "http://192.168.10.109/project/loadcircle.php/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circles);

        btn_createCircle = findViewById(R.id.btn_createCircle);

        btn_createCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Circles.this , CreateCircle.class);
                startActivity(intent);
                finish();
            }
        });

        circle_list = findViewById(R.id.circleList);
        circle_list.setHasFixedSize(true);
        circle_list.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();

        loadCircles();
    }

    private void loadCircles() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_LOADCIRCLE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                //adding the product to product list
                                productList.add(new CircleVariables(
                                        product.getString("circlename"),
                                        product.getString("circledesc"),
                                        product.getString("circleimage"),
                                        product.getString("createdby"),
                                        product.getString("joinby"),
                                        product.getInt("id"),
                                        product.getInt("totalmembers")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            CircleAdapter adapter = new CircleAdapter(Circles.this, productList);
                            circle_list.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        /*{

            @Override
            public Map<String, String> getParams() throws AuthFailureError {

                Map<String , String> params = new HashMap<>();
                params.put("category" , "Education");

                return params;
            }
        };*/

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
