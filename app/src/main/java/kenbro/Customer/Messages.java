package com.example.kenbro.Customer;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kenbro.Home.CustModel;
import com.example.kenbro.Home.CustSession;
import com.example.kenbro.Home.Manage;
import com.example.kenbro.Home.MessaAda;
import com.example.kenbro.Home.MessaMode;
import com.example.kenbro.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Messages extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText editText;
    Button sender;
    RequestQueue requestQueue;
    JSONObject jsonObject;
    JSONArray jsonArray;
    private List<MessaMode> list;
    MessaMode messageMode;
    MessaAda messageAda;
    CustModel custModel;
    CustSession custSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Messages");
        setContentView(R.layout.activity_messages);
        custSession = new CustSession(getApplicationContext());
        custModel = custSession.getCustDetails();
        recyclerView = findViewById(R.id.recycle);
        editText = findViewById(R.id.messmess);
        sender = findViewById(R.id.btnSend);
        getMesso();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        editText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String messo = editText.getText().toString();
                if (messo.isEmpty()) {
                    sender.setVisibility(View.GONE);
                } else {
                    sender.setVisibility(View.VISIBLE);
                }
            }

            public void afterTextChanged(Editable s) {
            }
        });
        sender.setOnClickListener(view -> {
            final String mess = editText.getText().toString().trim();
            if (mess.isEmpty()) {
                editText.setError("Type text!!");
                editText.requestFocus();
            } else {
                AlertDialog.Builder build = new AlertDialog.Builder(this);
                Spinner spinner = new Spinner(this);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Sender, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                build.setView(spinner);
                build.setPositiveButton("SEND", (dialogInterface, i) -> {
                });
                build.setNegativeButton("Exit", (dialogInterface, i) -> {
                });
                AlertDialog alertDialog = build.create();
                alertDialog.show();
                alertDialog.setCancelable(false);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setGravity(Gravity.TOP);
                alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view1 -> {
                    final String mchois = spinner.getSelectedItem().toString().trim();
                    if (mchois.equals("Select Contact")) {
                        Toast.makeText(this, "Select Contact", Toast.LENGTH_SHORT).show();
                    } else {
                        requestQueue = Volley.newRequestQueue(getApplicationContext());
                        requestQueue.add(new StringRequest(Request.Method.POST, Manage.textME,
                                response -> {
                                    try {
                                        jsonObject = new JSONObject(response);
                                        int st = jsonObject.getInt("status");
                                        String msg = jsonObject.getString("message");
                                        if (st == 1) {
                                            alertDialog.cancel();
                                            editText.setText("");
                                            getMesso();
                                        } else {
                                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show();
                                    }
                                }, error -> {
                            Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show();
                        }) {
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> para = new HashMap<>();
                                para.put("message", mess);
                                para.put("reference", mchois);
                                para.put("phone", custModel.getPhone());
                                para.put("name", custModel.getLname());
                                para.put("user", custModel.getEntry_no());
                                para.put("date", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                                para.put("staged", "M");
                                para.put("time", new SimpleDateFormat("hh:mm a").format(new Date()));
                                return para;
                            }
                        });
                    }
                });
                alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view1 -> {
                    alertDialog.cancel();
                });
            }
        });
    }

    private void getMesso() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(new StringRequest(Request.Method.POST, Manage.texted,
                response -> {
                    try {
                        Log.e("RESPONSE ", response);
                        jsonObject = new JSONObject(response);
                        int status = jsonObject.getInt("trust");
                        if (status == 1) {
                            list.clear();
                            jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String user = jsonObject.getString("user");
                                String phone = jsonObject.getString("phone");
                                String name = jsonObject.getString("name");
                                String message = jsonObject.getString("message");
                                String date = jsonObject.getString("date");
                                String staged = jsonObject.getString("staged");
                                String time = jsonObject.getString("time");
                                messageMode = new MessaMode(jsonObject.getString("reference"), user, phone, name, message, date, staged, time);
                                list.add(messageMode);
                            }
                            messageAda = new MessaAda(getApplicationContext(), list);
                            recyclerView.setAdapter(messageAda);
                            recyclerView.scrollToPosition(list.size() - 1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, error -> {
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user", custModel.getEntry_no());
                return params;
            }
        });
    }
}