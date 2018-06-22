package com.example.communitycommunicator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CreateCircle extends AppCompatActivity {

    private ImageView btnAddCircleImage ;
    private EditText etCircleName , etCircleDesc;
    private ImageButton btnUpload;
    private Bitmap bitmap;
    int idd = 0 ;

    private static String URL_CIRCLE = "http://192.168.10.109/project/create_circle.php/";
    private static String URL_CIRCLE1 = "http://192.168.10.109/project/create_circle_picture.php/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_circle);

        btnAddCircleImage = findViewById(R.id.btnAddCircleImage);
        etCircleName = findViewById(R.id.etCircleName);
        etCircleDesc = findViewById(R.id.etCircleDesc);
        btnUpload = findViewById(R.id.btnCreateCircles);

        btnAddCircleImage.setOnClickListener(new View.OnClickListener() {
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
                    createCircle();
                }
                else {
                    createPictureCircle(getStringImage(bitmap));
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //tvPath.setText("Path: ". concat(getPath(filePath)));
                btnAddCircleImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createCircle() {

        final String circleName  = etCircleName.getText().toString().trim();
        final String circleDesc  = etCircleDesc.getText().toString().trim();
        final String createdBy  = "User Name";
        final String joinBy  = "User Name";
        final int totalMembers  = 1;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CIRCLE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    String success = jsonObject.getString("success");

                    if(success.equals("1")){

                        Toast.makeText(getApplicationContext() , "Success" , Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(CreateCircle.this , Circles.class);
                        startActivity(intent);
                        finish();
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
                params.put("circlename" , circleName);
                params.put("circledesc" , circleDesc);
                params.put("createdby" , createdBy);
                params.put("joinby" , joinBy);
                params.put("totalmembers" , String.valueOf(totalMembers));


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void createPictureCircle(final String photo) {

        idd = idd+1 ;
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Posting...!");
        progressDialog.show();

        final String circleName  = etCircleName.getText().toString().trim();
        final String circleDesc  = etCircleDesc.getText().toString().trim();
        final String createdBy  = "User Name";
        final String joinBy  = "User Name";
        final int totalMembers  = 1;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CIRCLE1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    String success = jsonObject.getString("success");

                    if(success.equals("1")){

                        Toast.makeText(getApplicationContext() , "Success" , Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(CreateCircle.this , Circles.class);
                        startActivity(intent);
                        finish();
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
                params.put("id" , String.valueOf(idd));
                params.put("circlename" , circleName);
                params.put("circledesc" , circleDesc);
                params.put("circleimage" , photo);
                params.put("createdby" , createdBy);
                params.put("joinby" , joinBy);
                params.put("totalmembers" , String.valueOf(totalMembers));


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
}
