package com.example.kenbro.Customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.print.PrintManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.kenbro.Home.Anologies;
import com.example.kenbro.Home.CustModel;
import com.example.kenbro.Home.CustSession;
import com.example.kenbro.Home.Manage;
import com.example.kenbro.Home.ProjAda;
import com.example.kenbro.Home.ProjMode;
import com.example.kenbro.Home.ServMode;
import com.example.kenbro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FinishedProj extends AppCompatActivity {
    Button dater;
    TextView cate, siz, desc;
    AlertDialog.Builder builder;
    AlertDialog.Builder bio;
    Rect rect;
    Window window;
    LayoutInflater layoutInflater;
    AlertDialog alertDialog;
    AlertDialog down;
    View veve;
    RequestQueue requestQueue;
    ListView listView;
    SearchView searchView;
    ArrayList<ProjMode> servModeArrayList = new ArrayList<>();
    ProjMode servMode;
    ProjAda servAda;
    JSONArray jsonArray;
    JSONObject jsonObject;
    FrameLayout frameLayout;
    FrameLayout.LayoutParams params;
    ImageView added, testify;
    RelativeLayout relativeLayout;
    ArrayList<ServMode> servModes = new ArrayList<>();
    ServMode mode;
    CustModel custModel;
    CustSession custSession;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Past Completed Services");
        setContentView(R.layout.activity_finished_proj);
        custSession = new CustSession(getApplicationContext());
        custModel = custSession.getCustDetails();
        searchView = findViewById(R.id.mySearch);
        listView = findViewById(R.id.listing);
        listView.setTextFilterEnabled(true);
        getServ();
        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            servMode = (ProjMode) adapterView.getItemAtPosition(i);
            builder = new AlertDialog.Builder(this);
            builder.setTitle(servMode.getCategory());
            builder.setMessage("Site: " + servMode.getLocation() + "\nStartDate: " + servMode.getSite_date() + "\nEntryDate: " + servMode.getReg_date() + "\n\nProjectStatus: " + servMode.getStatus() + "\nCompletionDate: " + servMode.getComplete() + "\n\nCustomerStatus: " + servMode.getCustom() + "\nFinal SiteOutlook:-");
            rect = new Rect();
            window = this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            layoutInflater = (LayoutInflater) (this.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
            veve = layoutInflater.inflate(R.layout.levelled, null);
            testify = veve.findViewById(R.id.adobe);
            Glide.with(this).load(servMode.getImage()).into(testify);
            frameLayout = new FrameLayout(this);
            params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = getResources().getDimensionPixelSize(R.dimen.richer);
            params.rightMargin = getResources().getDimensionPixelSize(R.dimen.richer);
            veve.setLayoutParams(params);
            frameLayout.addView(veve);
            builder.setView(frameLayout);
            if (servMode.getCustom().equals("Pending")) {
                builder.setPositiveButton("Confirm", (rt, r) -> {
                });
            }
            builder.setNegativeButton("Close", (rt, r) -> {
            });
            builder.setNeutralButton("More", (rt, r) -> {
            });
            alertDialog = builder.create();
            alertDialog.show();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.origi);
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view1 -> {
                alertDialog.cancel();
            });
            alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL).setOnClickListener(view1 -> {
                requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(new StringRequest(Request.Method.POST, Manage.getDet,
                        response -> {
                            try {
                                jsonObject = new JSONObject(response);
                                int st = jsonObject.getInt("success");
                                if (st == 1) {
                                    jsonArray = jsonObject.getJSONArray("victory");
                                    for (int r = 0; r < jsonArray.length(); r++) {
                                        jsonObject = jsonArray.getJSONObject(r);
                                        mode = new ServMode(jsonObject.getString("ser_id"), jsonObject.getString("category"), jsonObject.getString("checker"),
                                                Manage.img + jsonObject.getString("image"), jsonObject.getString("description"), jsonObject.getString("site_date"),
                                                jsonObject.getString("price"), jsonObject.getString("size"), jsonObject.getString("cust_id"), jsonObject.getString("fullname"),
                                                jsonObject.getString("phone"), jsonObject.getString("location"), jsonObject.getString("landmark"), jsonObject.getString("house"),
                                                jsonObject.getString("reg_date"), jsonObject.getString("status"), jsonObject.getString("pay"), jsonObject.getString("fina"),
                                                jsonObject.getString("insta"), jsonObject.getString("updated"));
                                        servModes.add(mode);
                                    }
                                    bio = new AlertDialog.Builder(this);
                                    rect = new Rect();
                                    window = this.getWindow();
                                    window.getDecorView().getWindowVisibleDisplayFrame(rect);
                                    layoutInflater = (LayoutInflater) (this.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
                                    veve = layoutInflater.inflate(R.layout.sited, null);
                                    veve.setMinimumHeight((int) (rect.height() * 0.02));
                                    veve.setMinimumWidth((int) (rect.width() * 0.9));
                                    cate = veve.findViewById(R.id.txtServ);
                                    desc = veve.findViewById(R.id.txtDesc);
                                    siz = veve.findViewById(R.id.txtSize);
                                    added = veve.findViewById(R.id.addImage);
                                    dater = veve.findViewById(R.id.startDate);
                                    relativeLayout = veve.findViewById(R.id.myRel);
                                    desc.setText(mode.getDescription());
                                    siz.setText(mode.getSize());
                                    dater.setText(mode.getSite_date());
                                    cate.setText(mode.getCategory());
                                    if (mode.getChecker().equals("1")) {
                                        relativeLayout.setVisibility(View.VISIBLE);
                                        Glide.with(this).load(mode.getImage()).into(added);
                                    } else {
                                        relativeLayout.setVisibility(View.GONE);
                                    }
                                    frameLayout = new FrameLayout(this);
                                    params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    params.leftMargin = getResources().getDimensionPixelSize(R.dimen.richer);
                                    params.rightMargin = getResources().getDimensionPixelSize(R.dimen.richer);
                                    veve.setLayoutParams(params);
                                    frameLayout.addView(veve);
                                    bio.setView(frameLayout);
                                    bio.setMessage("Customer: " + mode.getFullname() + "\n" + mode.getPhone() + "\nSite: " + mode.getLocation() + "-" + mode.getLandmark() + "-" + mode.getHouse() + "\nRequestDate: " + mode.getReg_date());
                                    bio.setTitle("Site Details");
                                    bio.setNegativeButton("Close", (v1, ve) -> {
                                    });
                                    down = bio.create();
                                    down.setCancelable(false);
                                    down.setCanceledOnTouchOutside(false);
                                    down.show();
                                    down.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                                    down.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(viewer -> {
                                        down.cancel();
                                    });
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }, error -> {
                    Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show();
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> para = new HashMap<>();
                        para.put("ser_id", servMode.getSer_id());
                        return para;
                    }
                });
            });
            if (servMode.getCustom().equals("Pending")) {
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(viewer -> {
                    Toast.makeText(this, Html.fromHtml("<font color='green'>Long Press Confirm Button</font>"), Toast.LENGTH_SHORT).show();
                });
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnLongClickListener(viewer -> {
                    AlertDialog.Builder sender = new AlertDialog.Builder(this);
                    sender.setTitle("Some Feedback");
                    final EditText editText = new EditText(this);
                    editText.setHint("Type Message here");
                    frameLayout = new FrameLayout(this);
                    params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.leftMargin = getResources().getDimensionPixelSize(R.dimen.richer);
                    params.rightMargin = getResources().getDimensionPixelSize(R.dimen.richer);
                    editText.setLayoutParams(params);
                    frameLayout.addView(editText);
                    sender.setView(frameLayout);
                    sender.setPositiveButton("Submit", (dre, er) -> {
                    });
                    sender.setNegativeButton("Close", (dre, er) -> {
                    });
                    AlertDialog rece = sender.create();
                    rece.show();
                    rece.setCancelable(false);
                    rece.setCanceledOnTouchOutside(false);
                    rece.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                    rece.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view1 -> {
                        final String messs = editText.getText().toString().trim();
                        if (messs.isEmpty()) {
                            editText.setError("Message??");
                            editText.requestFocus();
                        } else {
                            requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(new StringRequest(Request.Method.POST, Manage.getYou,
                                    responses -> {
                                        try {
                                            JSONObject jsonObjec = new JSONObject(responses);
                                            String msg = jsonObjec.getString("message");
                                            int status = jsonObjec.getInt("success");
                                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                            if (status == 1) {
                                                startActivity(new Intent(getApplicationContext(), FinishedProj.class));
                                                finish();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                                        }
                                    }, error -> {
                                Toast.makeText(this, "There was a connection error", Toast.LENGTH_SHORT).show();
                            }) {
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> para = new HashMap<>();
                                    para.put("message", messs);
                                    para.put("reference", "Installation Mgr");
                                    para.put("phone", custModel.getPhone());
                                    para.put("name", custModel.getLname());
                                    para.put("user", custModel.getEntry_no());
                                    para.put("staged", "M");
                                    para.put("date", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                                    para.put("time", new SimpleDateFormat("hh:mm a").format(new Date()));
                                    para.put("reg_id", servMode.getReg_id());
                                    para.put("custom", "1");
                                    para.put("reg_date", new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date()));
                                    return para;
                                }
                            });
                        }
                    });
                    rece.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view1 -> {
                        rece.cancel();
                    });
                    return true;
                });
            }
            return true;
        });
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Toast.makeText(this, Html.fromHtml("<font color='green'>Long Press</font>"), Toast.LENGTH_SHORT).show();
        });
    }

    private void getServ() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(new StringRequest(Request.Method.POST, Manage.finalfinal,
                response -> {
                    try {
                        jsonObject = new JSONObject(response);
                        int st = jsonObject.getInt("success");
                        if (st == 1) {
                            jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                servMode = new ProjMode(jsonObject.getString("reg_id"), jsonObject.getString("ser_id"), jsonObject.getString("category"), jsonObject.getString("location"),
                                        jsonObject.getString("site_date"), jsonObject.getString("cust_id"), jsonObject.getString("art_name"),
                                        jsonObject.getString("art_phone"), jsonObject.getString("art_email"), jsonObject.getString("art_serial"),
                                        jsonObject.getString("status"), jsonObject.getString("complete"), Manage.img + jsonObject.getString("image"),
                                        jsonObject.getString("install"), jsonObject.getString("custom"), jsonObject.getString("reg_date"), jsonObject.getString("updated"));
                                servModeArrayList.add(servMode);
                            }
                            servAda = new ProjAda(FinishedProj.this, R.layout.acc, servModeArrayList);
                            listView.setAdapter(servAda);
                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String s) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String s) {
                                    servAda.getFilter().filter(s);
                                    return false;
                                }
                            });
                            findViewById(R.id.btnPrint).setVisibility(View.VISIBLE);
                            findViewById(R.id.btnPrint).setOnClickListener(view -> {
                                PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
                                printManager.print(getString(R.string.app_name), new Anologies(this, findViewById(R.id.myHeader)), null);
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show();
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> para = new HashMap<>();
                para.put("status", "1");
                para.put("cust_id", custModel.getEntry_no());
                return para;
            }
        });
    }
}