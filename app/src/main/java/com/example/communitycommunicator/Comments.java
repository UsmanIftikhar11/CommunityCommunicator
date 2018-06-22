package com.example.communitycommunicator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class Comments extends AppCompatActivity {

    private String userName ;
    private int postId ;

    private EditText et_comment ;
    private ImageButton btn_postComment ;

    private static String URL_COMMENT = "http://192.168.10.109/project/comment.php/";
    private static String URL_LOADCOMMENT = "http://192.168.10.109/project/loadcomment.php/";

    List<CommentVariables> productList;

    RecyclerView comment_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        userName = getIntent().getExtras().getString("userName") ;
        postId = getIntent().getExtras().getInt("id") ;

        et_comment = findViewById(R.id.et_comemnt);
        btn_postComment = findViewById(R.id.btn_postComment) ;
        
        btn_postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment  = et_comment.getText().toString();

                if(!comment.isEmpty()){

                    uploadComment(comment);
                }
                else {
                    et_comment.setError("Please enter a comment");
                }
            }
        });

        comment_list = findViewById(R.id.commentList);
        comment_list.setHasFixedSize(true);
        comment_list.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();

        loadComment();
    }

    private void loadComment() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOADCOMMENT,
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
                                productList.add(new CommentVariables(
                                        product.getInt("id"),
                                        product.getString("postId"),
                                        product.getString("userName"),
                                        product.getString("comment")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            commentAdapter adapter = new commentAdapter(Comments.this, productList);
                            comment_list.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {

                Map<String , String> params = new HashMap<>();
                params.put("id" , String.valueOf(postId));

                return params;
            }

        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void uploadComment(final String comment) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_COMMENT, new Response.Listener<String>() {
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
                params.put("postId" , String.valueOf(postId));
                params.put("comment" , comment);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
