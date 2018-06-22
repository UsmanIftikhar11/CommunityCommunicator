package com.example.communitycommunicator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
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

public class Replies extends AppCompatActivity {

    private String userName , comment ;
    private int commentId ;

    private EditText et_reply ;
    private TextView txt_comment , txt_userName ;
    private ImageButton btn_postreply ;

    private static String URL_REPLY = "http://192.168.10.109/project/reply.php/";
    private static String URL_LOADREPLY = "http://192.168.10.109/project/loadreply.php/";

    List<ReplyVariables> productList;

    RecyclerView reply_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replies);

        userName = getIntent().getExtras().getString("userName") ;
        comment = getIntent().getExtras().getString("comment");
        commentId = getIntent().getExtras().getInt("id") ;

        et_reply = findViewById(R.id.et_reply);
        txt_comment = findViewById(R.id.txt_singleComment);
        txt_userName = findViewById(R.id.txt_userName4);
        btn_postreply = findViewById(R.id.btn_postReply) ;

        txt_comment.setText(comment);
        txt_userName.setText(userName);

        btn_postreply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reply  = et_reply.getText().toString();

                if(!reply.isEmpty()){

                    uploadReply(reply);
                }
                else {
                    et_reply.setError("Please enter a comment");
                }
            }
        });

        reply_list = findViewById(R.id.repliesList);
        reply_list.setHasFixedSize(true);
        reply_list.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();

        loadComment();
    }

    private void loadComment() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_LOADREPLY,
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
                                productList.add(new ReplyVariables(
                                        product.getInt("id"),
                                        product.getString("userName"),
                                        product.getString("comment"),
                                        product.getString("commentId"),
                                        product.getString("reply")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            ReplyAdapter adapter = new ReplyAdapter(Replies.this, productList);
                            reply_list.setAdapter(adapter);
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

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void uploadReply(final String reply) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REPLY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    String success = jsonObject.getString("success");

                    if(success.equals("1")){

                        Toast.makeText(getApplicationContext() , "Register Successfully" , Toast.LENGTH_LONG).show();
                        recreate();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext() , "Register Error" + e.toString() , Toast.LENGTH_LONG).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext() , "Register fail" + error.toString() , Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {

                Map<String , String> params = new HashMap<>();
                params.put("userName" , userName);
                params.put("commentId" , String.valueOf(commentId));
                params.put("comment" , comment);
                params.put("reply" , reply);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
