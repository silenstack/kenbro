package com.example.kenbro.Artisan;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.print.PrintManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
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
import com.example.kenbro.Home.ArtisanSession;
import com.example.kenbro.Home.Manage;
import com.example.kenbro.Home.ProjAda;
import com.example.kenbro.Home.ProjMode;
import com.example.kenbro.Home.ServMode;
import com.example.kenbro.Home.StaffModel;
import com.example.kenbro.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProjeHis extends AppCompatActivity {
    StaffModel custModel;
    ArtisanSession artisanSession;
    Button dater, complete, camera;
    TextView cate, siz, desc, text;
    AlertDialog.Builder builder, bio, alumini;
    Rect rect;
    Window window;
    LayoutInflater layoutInflater;
    AlertDialog alertDialog, down, meru;
    View veve;
    RequestQueue requestQueue;
    ListView listView;
    SearchView searchView;
    ArrayList<ProjMode> servModeArrayList = new ArrayList<>();
    ProjMode servMode;
    ProjAda servAda;
    ArrayList<ServMode> servModes = new ArrayList<>();
    ServMode mode;
    JSONArray jsonArray;
    JSONObject jsonObject;
    FrameLayout frameLayout;
    FrameLayout.LayoutParams params;
    ImageView added, sited, testify, dated;
    RelativeLayout relativeLayout;
    Bitmap bitmap;
    String encodedimage;
    DatePickerDialog datePickerDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Past Completed");
        setContentView(R.layout.activity_proje_his);
        artisanSession = new ArtisanSession(getApplicationContext());
        custModel = artisanSession.getArtDetails();
        searchView = findViewById(R.id.mySearch);
        listView = findViewById(R.id.listing);
        listView.setTextFilterEnabled(true);
        getServ();
        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            servMode = (ProjMode) adapterView.getItemAtPosition(i);
            builder = new AlertDialog.Builder(this);
            builder.setTitle(servMode.getCategory());
            builder.setMessage("Site: " + servMode.getLocation() + "\nStartDate: " + servMode.getSite_date() + "\nEntryDate: " + servMode.getReg_date() + "\n\nProjectStatus: " + servMode.getStatus() + "\nCompletionDate: " + servMode.getComplete() + "\nFinal SiteOutlook:-");
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
            return true;
        });
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Toast.makeText(this, Html.fromHtml("<font color='green'>Hi " + custModel.getFname() + ", Long Press</font>"), Toast.LENGTH_SHORT).show();
        });
    }

    private void getServ() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(new StringRequest(Request.Method.POST, Manage.artM,
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
                            servAda = new ProjAda(ProjeHis.this, R.layout.acc, servModeArrayList);
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
                para.put("art_serial", custModel.getSerial_no());
                return para;
            }
        });
    }
}