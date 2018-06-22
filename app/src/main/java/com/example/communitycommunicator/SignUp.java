package com.example.communitycommunicator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private EditText et_Name , et_Phone , et_Email , et_Password ;
    private ImageButton btn_register ;

    private static String URL_REGIST = "http://192.168.10.109/project/register.php/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        et_Name = (EditText)findViewById(R.id.et_name1);
        et_Phone = (EditText)findViewById(R.id.et_phone1);
        et_Email = (EditText)findViewById(R.id.et_email1);
        et_Password = (EditText)findViewById(R.id.et_pass1);

        btn_register = (ImageButton)findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Register();
            }
        });
    }

    private void Register() {

        final String name  = et_Name.getText().toString().trim();
        final String password  = et_Password.getText().toString().trim();
        final String email  = et_Email.getText().toString().trim();
        final String phone  = et_Phone.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                    try {
                        JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                        String success = jsonObject.getString("success");

                        if(success.equals("1")){

                            Toast.makeText(getApplicationContext() , "Register Successfully" + "\n" + "now you can login to application" , Toast.LENGTH_LONG).show();
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
                params.put("name" , name);
                params.put("email" , email);
                params.put("phone" , phone);
                params.put("password", password);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}