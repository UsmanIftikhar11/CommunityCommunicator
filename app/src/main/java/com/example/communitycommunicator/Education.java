package com.example.communitycommunicator;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class Education extends AppCompatActivity {

    private ImageView imageView;
    private EditText etCaption;
    private TextView txtCaption;
    private ImageButton btnUpload;
    private Bitmap bitmap;
    private int idd = 0 ;

    private static String URL_UPLOAD = "http://192.168.10.109/project/upload_image_post.php/";
    private static String URL_UPLOAD1 = "http://192.168.10.109/project/upload_post.php/";
    private static final String URL_POST = "http://192.168.10.109/project/retrieve_post.php/";
    private static final String URL_NOTIFY = "http://192.168.10.109/project/index.php/";

    List<Product> productList;

    RecyclerView education_list;

    private String userName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        userName = getIntent().getExtras().getString("userName");

        imageView = findViewById(R.id.image);
        etCaption = findViewById(R.id.etCaption);
        txtCaption = findViewById(R.id.txt_userName);
        btnUpload = findViewById(R.id.btnUpload);

        txtCaption.setText(userName);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "select picture"), 1);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(bitmap == null){
                    upoadPost();
                }
                else {
                    UploadPicture(getStringImage(bitmap));
                }
            }
        });

        education_list = findViewById(R.id.education_list);
        education_list.setHasFixedSize(true);
        education_list.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();

        loadProducts();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //tvPath.setText("Path: ". concat(getPath(filePath)));
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void upoadPost() {

        final String caption  = etCaption.getText().toString().trim();
        final int likes  = 0;
        final int dislike  = 0;
        final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        final String Category  = "Education";

        final PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, Home.class), PendingIntent.FLAG_UPDATE_CURRENT);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPLOAD1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    String success = jsonObject.getString("success");

                    if(success.equals("1")){

                        Toast.makeText(getApplicationContext() , "Success" , Toast.LENGTH_LONG).show();

                        NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(getApplicationContext())
                                        .setSmallIcon(R.mipmap.ic_launcher)
                                        .setContentTitle("New Post")
                                        .setContentText("A new post is added !!!")
                                        .setContentIntent(contentIntent);

                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(1, mBuilder.build());



                        recreate();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext() , "Error" + e.toString() , Toast.LENGTH_LONG).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext() , "fail" + error.toString() , Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {

                Map<String , String> params = new HashMap<>();
                params.put("userName" , userName);
                params.put("caption" , caption);
                params.put("likes", String.valueOf(likes));
                params.put("dislike" , String.valueOf(dislike));
                params.put("date" , date);
                params.put("category" , Category);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void UploadPicture(final String photo) {

        idd =  new Random().nextInt((1000 - 1) + 1) + 1 ;
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Posting...!");
        progressDialog.show();

        final String caption  = etCaption.getText().toString().trim();
        final int likes  = 0;
        final int dislike  = 0;
        final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        final String Category  = "Education";
        final String id = String.valueOf(idd);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPLOAD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    String success = jsonObject.getString("success");

                    if(success.equals("1")){

                        Toast.makeText(getApplicationContext() , "Success" , Toast.LENGTH_LONG).show();
                        recreate();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext() , "Error" + e.toString() , Toast.LENGTH_LONG).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext() , "fail" + error.toString() , Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {

                Map<String , String> params = new HashMap<>();
                params.put("photo" , photo);
                params.put("userName" , userName);
                params.put("caption" , caption);
                params.put("likes", String.valueOf(likes));
                params.put("dislike" , String.valueOf(dislike));
                params.put("date" , date);
                params.put("category" , Category);
                params.put("id" , id);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public String getStringImage(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG , 100 , byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodeImage = Base64.encodeToString(imageByteArray , Base64.DEFAULT);

        return encodeImage ;
    }

    private void loadProducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST,
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
                                productList.add(new Product(
                                        product.getInt("id"),
                                        product.getString("image"),
                                        product.getString("caption"),
                                        product.getString("category"),
                                        product.getString("userName"),
                                        product.getString("likes"),
                                        product.getString("dislike"),
                                        product.getString("date")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            ProductAdapter adapter = new ProductAdapter(Education.this, productList);
                            education_list.setAdapter(adapter);
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
                params.put("category" , "Education");

                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}