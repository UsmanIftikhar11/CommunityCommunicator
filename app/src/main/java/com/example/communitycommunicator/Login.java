package com.example.communitycommunicator;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private TextView txt_reg ;

    private EditText et_email , et_pass ;
    private ImageButton btn_login;

    private static String URL_REGIST = "http://192.168.10.109/project/login.php/";

    SessionManager sessionManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager =  new SessionManager(this);

        txt_reg = (TextView)findViewById(R.id.txt_register);

        et_email = (EditText)findViewById(R.id.et_email);
        et_pass = (EditText)findViewById(R.id.et_pass);

        btn_login = (ImageButton)findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email  = et_email.getText().toString();
                String pass  = et_pass.getText().toString();

                if(!email.isEmpty() || !pass.isEmpty()){

                    Login(email , pass);
                }
                else {
                    et_email.setError("Please enter a valid email");
                    et_pass.setError("Please enter a password");
                }


            }
        });

        txt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this ,SignUp.class);
                startActivity(intent);
            }
        });

    }

    private void Login(final String email , final String password){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("login");

                    if(success.equals("1")){

                        for(int i = 0; i<jsonArray.length(); i++ ){

                            JSONObject object = jsonArray.getJSONObject(i);

                            String name = object.getString("name").trim();
                            String email = object.getString("email").trim();

                            //Toast.makeText(getApplicationContext() , "login Successfully \n your name :" + name + "\nYour email : " + email , Toast.LENGTH_LONG).show();

                            sessionManager.createSession(name , email);

                            Intent intent = new Intent(Login.this , Home.class);
                            intent.putExtra("name",name);
                            intent.putExtra("email",email);
                            startActivity(intent);
                        }

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
                params.put("email" , email);
                params.put("password", password);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
