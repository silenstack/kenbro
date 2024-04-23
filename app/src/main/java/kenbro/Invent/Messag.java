package com.example.kenbro.Invent;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
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
import com.example.kenbro.Home.BabaYangu;
import com.example.kenbro.Home.Manage;
import com.example.kenbro.Home.MessaMode;
import com.example.kenbro.Home.Messing;
import com.example.kenbro.Home.StagedAda;
import com.example.kenbro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Messag extends AppCompatActivity {
    AlertDialog.Builder builder;
    Rect rect;
    Window window;
    LayoutInflater layoutInflater;
    AlertDialog alertDialog;
    View veve;
    RequestQueue requestQueue;
    ListView listView;
    SearchView searchView;
    ArrayList<StagedAda> messaModeArrayList = new ArrayList<>();
    Messing messing;
    StagedAda messaMode;
    RecyclerView recyclerView;
    EditText editText;
    Button sender;
    JSONArray jsonArray;
    JSONObject jsonObject;
    private List<MessaMode> list;
    MessaMode messageMode;
    BabaYangu messageAda;
    FrameLayout.LayoutParams params;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Messages");
        setContentView(R.layout.activity_messag);
        listView = findViewById(R.id.listing);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.mySearch);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            messaMode = (StagedAda) parent.getItemAtPosition(position);
            builder = new AlertDialog.Builder(this, R.style.Arap);
            rect = new Rect();
            window = this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            layoutInflater = (LayoutInflater) (this.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
            veve = layoutInflater.inflate(R.layout.activity_messages, null);
            veve.setMinimumHeight((int) (rect.height() * 0.02));
            veve.setMinimumWidth((int) (rect.width() * 0.9));
            recyclerView = veve.findViewById(R.id.recycle);
            editText = veve.findViewById(R.id.messmess);
            sender = veve.findViewById(R.id.btnSend);
            sender.setVisibility(View.GONE);
            getMesso();
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
            recyclerView.setLayoutManager(layoutManager);
            list = new ArrayList<>();
            frameLayout = new FrameLayout(this);
            params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = getResources().getDimensionPixelSize(R.dimen.richer);
            params.rightMargin = getResources().getDimensionPixelSize(R.dimen.richer);
            veve.setLayoutParams(params);
            frameLayout.addView(veve);
            builder.setView(frameLayout);
            builder.setNegativeButton("Close", (v1, ve) -> {
            });
            builder.setPositiveButton(Html.fromHtml("<font color='#1E8103'>Send</font>"), (v1, ve) -> {
            });
            builder.setTitle(Html.fromHtml("<font><u>" + messaMode.getPhone() + "~" + messaMode.getName() + "</u></font>"));
            alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.origi);
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view1 -> {
                alertDialog.cancel();
            });
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view1 -> {
                final String mess = editText.getText().toString().trim();
                if (mess.isEmpty()) {
                    editText.setError("Type text!!");
                    editText.requestFocus();
                } else {
                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(new StringRequest(Request.Method.POST, Manage.textME,
                            response -> {
                                try {
                                    jsonObject = new JSONObject(response);
                                    int st = jsonObject.getInt("status");
                                    String msg = jsonObject.getString("message");
                                    if (st == 1) {
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
                            para.put("reference", "Inventory Mgr");
                            para.put("phone", messaMode.getPhone());
                            para.put("name", messaMode.getName());
                            para.put("user", messaMode.getUser());
                            para.put("date", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                            para.put("staged", "R");
                            para.put("time", new SimpleDateFormat("hh:mm a").format(new Date()));
                            return para;
                        }
                    });
                }
            });
        });
        getDeal();
    }

    private void getMesso() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(new StringRequest(Request.Method.POST, Manage.sawaTu,
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
                            messageAda = new BabaYangu(getApplicationContext(), list);
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
                params.put("user", messaMode.getUser());
                params.put("reference", "Inventory Mgr");
                return params;
            }
        });
    }

    private void getDeal() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(new StringRequest(Request.Method.POST, Manage.chezaKama,
                response -> {
                    try {
                        jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String user = jsonObject.getString("user");
                                String phone = jsonObject.getString("phone");
                                String name = jsonObject.getString("name");
                                String message = jsonObject.getString("message");
                                String date = jsonObject.getString("date");
                                String time = jsonObject.getString("time");
                                messaMode = new StagedAda(jsonObject.getString("reference"), user, phone, name, message, date, time);
                                messaModeArrayList.add(messaMode);
                            }
                            messing = new Messing(Messag.this, R.layout.alien, messaModeArrayList);
                            listView.setAdapter(messing);
                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String text) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    messing.getFilter().filter(newText);
                                    return false;
                                }
                            });
                        } else if (success == 0) {
                            String msg = jsonObject.getString("mine");
                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();

        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> para = new HashMap<>();
                para.put("reference", "Inventory Mgr");
                return para;
            }
        });
    }
}