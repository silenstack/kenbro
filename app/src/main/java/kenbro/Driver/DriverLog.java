package com.example.kenbro.Driver;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kenbro.Home.DrivModel;
import com.example.kenbro.Home.DrivSession;
import com.example.kenbro.Home.Manage;
import com.example.kenbro.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DriverLog extends AppCompatActivity {
    DrivModel drivModel;
    DrivSession drivSession;
    EditText Username, Password;
    TextView ResetPass, CreateAccount;
    Button btnLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Driver Login");
        setContentView(R.layout.activity_driver_log);
        drivSession = new DrivSession(getApplicationContext());
        drivModel = drivSession.getDrivDetails();
        Password = findViewById(R.id.password);
        Username = findViewById(R.id.username);
        ResetPass = findViewById(R.id.resetPassword);
        CreateAccount = findViewById(R.id.createAccount);
        btnLog = findViewById(R.id.btnLogin);
        ResetPass.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), DriverReset.class));
        });
        CreateAccount.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), DriverReg.class));
        });
        btnLog.setOnClickListener(v -> {
            final String myUser = Username.getText().toString().trim();
            final String myPass = Password.getText().toString().trim();
            if (myUser.isEmpty() || myPass.isEmpty()) {
                Toast.makeText(this, "you have an empty field", Toast.LENGTH_SHORT).show();
            } else {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Manage.lDri,
                        response -> {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String msg = jsonObject.getString("message");
                                String status = jsonObject.getString("success");
                                if (status.equals("1")) {
                                    JSONArray dataArray = jsonObject.getJSONArray("login");
                                    for (int i = 0; i < dataArray.length(); i++) {
                                        JSONObject dataobj = dataArray.getJSONObject(i);
                                        String entry_no = dataobj.getString("entry_no");
                                        String license = dataobj.getString("license");
                                        String fname = dataobj.getString("fname");
                                        String lname = dataobj.getString("lname");
                                        String email = dataobj.getString("email");
                                        String phone = dataobj.getString("phone");
                                        String address = dataobj.getString("address");
                                        String username = dataobj.getString("username");
                                        String sstatus = dataobj.getString("status");
                                        String date = dataobj.getString("date");
                                        drivSession.loginDriv(entry_no, license, fname, lname, email, phone, address, username, sstatus, date);
                                    }
                                    startActivity(new Intent(getApplicationContext(), DriverDash.class));

                                } else if (status.equals("0")) {
                                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

                                } else if (status.equals("2")) {
                                    JSONArray dataArray = jsonObject.getJSONArray("login");
                                    for (int i = 0; i < dataArray.length(); i++) {
                                        JSONObject dataobj = dataArray.getJSONObject(i);
                                        String remarks = dataobj.getString("remarks").trim();
                                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                        Toast.makeText(getApplicationContext(), remarks, Toast.LENGTH_LONG).show();
                                    }
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