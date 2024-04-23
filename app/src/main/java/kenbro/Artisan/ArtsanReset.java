package com.example.kenbro.Artisan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kenbro.Home.Manage;
import com.example.kenbro.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ArtsanReset extends AppCompatActivity {
    EditText Username, Password, reset;
    Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("ResetPassword");
        setContentView(R.layout.activity_artsan_reset);
        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        reset = findViewById(R.id.passwordRetype);
        btnReset = findViewById(R.id.btnLogin);
        btnReset.setOnClickListener(v -> {
            final String myUser = Username.getText().toString().trim();
            final String myPass = Password.getText().toString().trim();
            final String myReset = reset.getText().toString().trim();
            int count = myPass.length();
            if (myUser.isEmpty() || myPass.isEmpty() || myReset.isEmpty()) {
                Toast.makeText(this, "you have an empty field", Toast.LENGTH_SHORT).show();
            } else if (count < 8) {
                Toast.makeText(this, "Enter a strong password", Toast.LENGTH_SHORT).show();
            } else if (!myReset.equals(myPass)) {
                Toast.makeText(this, "Password do not match", Toast.LENGTH_SHORT).show();
            } else {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Manage.uArt,
                        response -> {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Log.e("response ", response);
                                String msg = jsonObject.getString("message");
                                int status = jsonObject.getInt("success");
                                if (status == 1) {
                                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), ArtisanLog.class));

                                } else if (status == 0) {
                                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(this, "A database error occurred", Toast.LENGTH_SHORT).show();
                            }

                        }, error -> {
                    Toast.makeText(this, "Connection was unsuccessful", Toast.LENGTH_SHORT).show();
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("username", myUser);
                        params.put("password", myPass);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });
    }
}