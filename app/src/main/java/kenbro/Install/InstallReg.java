package com.example.kenbro.Install;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kenbro.Home.Manage;
import com.example.kenbro.R;
import com.santalu.maskedittext.MaskEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InstallReg extends AppCompatActivity {
    EditText First, Last, Email, Password, Confirm, Username;
    MaskEditText Phone;
    Spinner role;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Register Installer");
        setContentView(R.layout.activity_install_reg);
        First = findViewById(R.id.fname);
        Last = findViewById(R.id.lname);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        Username = findViewById(R.id.username);
        Confirm = findViewById(R.id.passwordRetype);
        Phone = findViewById(R.id.phone);
        role = findViewById(R.id.role);
        btnRegister = findViewById(R.id.btnLogin);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Installation, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role.setAdapter(adapter1);
        btnRegister.setOnClickListener(v -> {
            final String myFirst = First.getText().toString().trim();
            final String myLast = Last.getText().toString().trim();
            final String myEmail = Email.getText().toString().trim();
            final String myUser = Username.getText().toString().trim();
            final String myPhone = Phone.getText().toString().trim();
            final String myPass = Password.getText().toString().trim();
            final String myConfirm = Confirm.getText().toString().trim();
            final String myRole = role.getSelectedItem().toString().trim();
            int countPhone = myPhone.length();
            int countPass = myPass.length();
            if (myFirst.isEmpty() || myLast.isEmpty() || myEmail.isEmpty() || myUser.isEmpty() || myPhone.isEmpty() || myPass.isEmpty() || myConfirm.isEmpty()) {
                Toast.makeText(this, "You an empty field", Toast.LENGTH_SHORT).show();
            } else if (!myPhone.matches(getString(R.string.phone1)) & !myPhone.matches(getString(R.string.phone7))) {
                Phone.setError("??");
                Phone.requestFocus();
                Toast.makeText(this, "Your phone number is wrong", Toast.LENGTH_SHORT).show();
            } else if (countPass < 8) {
                Toast.makeText(this, "Enter a Strong Password", Toast.LENGTH_SHORT).show();
            } else if (!myPass.equals(myConfirm)) {
                Toast.makeText(this, "Your passwords do not match", Toast.LENGTH_SHORT).show();
            } else if (!myEmail.matches(getString(R.string.valid_email)) & !myEmail.matches(getString(R.string.valid_email2))) {
                Email.setError("??");
                Email.requestFocus();
                Toast.makeText(this, "Your email is wrong", Toast.LENGTH_SHORT).show();
            } else {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Manage.rSta,
                        response -> {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Log.e("response ", response);
                                String msg = jsonObject.getString("message");
                                int status = jsonObject.getInt("success");
                                if (status == 1) {
                                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), InstallLog.class));

                                } else if (status == 0) {
                                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                            }

                        }, error -> {
                    Toast.makeText(this, "Could not create a connection route", Toast.LENGTH_SHORT).show();

                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("fname", myFirst);
                        params.put("lname", myLast);
                        params.put("username", myUser);
                        params.put("email", myEmail);
                        params.put("phone", myPhone);
                        params.put("role", myRole);
                        params.put("password", myPass);
                        return params;
                    }
                };//fullname,username,mobile,role,email,password
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            }
        });
    }
}