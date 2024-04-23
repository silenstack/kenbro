package com.example.kenbro.Install;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.print.PrintManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.kenbro.Home.Anologies;
import com.example.kenbro.Home.Manage;
import com.example.kenbro.Home.ServAda;
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

public class AssignArt extends AppCompatActivity {
    Button dater;
    TextView cate, siz, desc;
    AlertDialog.Builder builder, alert, bio;
    Rect rect;
    Window window;
    LayoutInflater layoutInflater;
    AlertDialog alertDialog, alex, down;
    View veve;
    RequestQueue requestQueue;
    ListView listView;
    SearchView searchView;
    ArrayList<ServMode> servModeArrayList = new ArrayList<>();
    ServMode servMode;
    ServAda servAda;
    JSONArray jsonArray;
    JSONObject jsonObject;
    FrameLayout frameLayout;
    FrameLayout.LayoutParams params;
    ImageView added;
    RelativeLayout relativeLayout;
    ArrayList<String> artisans = new ArrayList<>();
    ArrayAdapter<String> artisanAdapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Attach Artisan");
        setContentView(R.layout.activity_assign_art);
        searchView = findViewById(R.id.mySearch);
        listView = findViewById(R.id.listing);
        listView.setTextFilterEnabled(true);
        getServ();
        listView.setOnItemClickListener(((adapterView, view, i, l) -> {
            servMode = (ServMode) adapterView.getItemAtPosition(i);
            builder = new AlertDialog.Builder(this);
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
            desc.setText(servMode.getDescription());
            siz.setText(servMode.getSize());
            dater.setText(servMode.getSite_date());
            cate.setText(servMode.getCategory());
            if (servMode.getChecker().equals("1")) {
                relativeLayout.setVisibility(View.VISIBLE);
                Glide.with(this).load(servMode.getImage()).into(added);
            } else {
                relativeLayout.setVisibility(View.GONE);
            }
            frameLayout = new FrameLayout(this);
            params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = getResources().getDimensionPixelSize(R.dimen.richer);
            params.rightMargin = getResources().getDimensionPixelSize(R.dimen.richer);
            veve.setLayoutParams(params);
            frameLayout.addView(veve);
            builder.setView(frameLayout);
            builder.setMessage("Customer: " + servMode.getFullname() + "\n" + servMode.getPhone() + "\nSite: " + servMode.getLocation() + "-" + servMode.getLandmark() + "-" + servMode.getHouse() + "\nRequestDate: " + servMode.getReg_date() + "\nStatus: " + servMode.getStatus() + "\nFinanceStatus: " + servMode.getFina());
            builder.setTitle("Requested Service");
            builder.setNegativeButton("Close", (v1, ve) -> {
            });
            builder.setPositiveButton("Artisan", (v1, ve) -> {
            });
            alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.origi);
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(viewer -> {
                alertDialog.cancel();
            });
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view1 -> {
                Toast.makeText(this, Html.fromHtml("<font color='green'>Please long press the Verify Button</font>"), Toast.LENGTH_SHORT).show();
            });
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnLongClickListener(viewer -> {
                requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(new StringRequest(Request.Method.POST, Manage.chek,
                        response -> {
                            try {
                                jsonObject = new JSONObject(response);
                                int gt = jsonObject.getInt("success");
                                if (gt == 1) {
                                    requestQueue = Volley.newRequestQueue(this);
                                    requestQueue.add(new JsonObjectRequest(Request.Method.POST, Manage.noo, null,
                                            respons -> {
                                                try {
                                                    jsonArray = respons.getJSONArray("art");
                                                    for (int j = 0; j < jsonArray.length(); j++) {
                                                        jsonObject = jsonArray.getJSONObject(j);
                                                        artisans.add(jsonObject.optString("email"));
                                                    }
                                                    alert = new AlertDialog.Builder(this);
                                                    alert.setTitle("Select Artisan Email Address");
                                                    final Spinner spinned = new Spinner(this);
                                                    artisanAdapt = new ArrayAdapter<>(this,
                                                            android.R.layout.simple_spinner_item, artisans);
                                                    artisanAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                    spinned.setAdapter(artisanAdapt);
                                                    frameLayout = new FrameLayout(this);
                                                    params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                                    params.rightMargin = getResources().getDimensionPixelSize(R.dimen.richer);
                                                    params.leftMargin = getResources().getDimensionPixelSize(R.dimen.richer);
                                                    spinned.setLayoutParams(params);
                                                    frameLayout.addView(spinned);
                                                    alert.setView(frameLayout);
                                                    alert.setPositiveButton("Next", (dilo, di) -> {
                                                    });
                                                    alert.setNegativeButton("Close", (dilo, o9) -> {
                                                    });
                                                    alex = alert.create();
                                                    alex.show();
                                                    alex.setCanceledOnTouchOutside(false);
                                                    alex.setCancelable(false);
                                                    alex.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                                                    alex.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view1 -> {
                                                        final String email = spinned.getSelectedItem().toString().trim();
                                                        requestQueue = Volley.newRequestQueue(getApplicationContext());
                                                        requestQueue.add(new StringRequest(Request.Method.POST, Manage.getAr,
                                                                respon -> {
                                                                    try {
                                                                        jsonObject = new JSONObject(respon);
                                                                        int st = jsonObject.getInt("success");
                                                                        if (st == 1) {
                                                                            jsonArray = jsonObject.getJSONArray("victory");
                                                                            for (int t = 0; t < jsonArray.length(); t++) {
                                                                                jsonObject = jsonArray.getJSONObject(t);
                                                                                String serial = jsonObject.getString("serial_no");
                                                                                String full = jsonObject.getString("fullname");
                                                                                String phon = jsonObject.getString("phone");
                                                                                bio = new AlertDialog.Builder(this);
                                                                                bio.setTitle("Confirm Artisan");
                                                                                bio.setMessage("Artisan Selected:-\nName: " + full + "\nPhone: " + phon + "\nEmail: " + email + "\nCLick Submit to attach the artisan.");
                                                                                bio.setPositiveButton("Submit", (dilo, di) -> {
                                                                                });
                                                                                bio.setNegativeButton("Close", (dilo, o9) -> {
                                                                                });
                                                                                down = bio.create();
                                                                                down.show();
                                                                                down.setCanceledOnTouchOutside(false);
                                                                                down.setCancelable(false);
                                                                                down.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                                                                                down.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view12 -> {
                                                                                    down.cancel();
                                                                                });
                                                                                down.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view12 -> {
                                                                                    Toast.makeText(this, Html.fromHtml("<font color='green'>Long press the Submit button</font>"), Toast.LENGTH_SHORT).show();
                                                                                });
                                                                                down.getButton(DialogInterface.BUTTON_POSITIVE).setOnLongClickListener(view12 -> {
                                                                                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                                                                                    requestQueue.add(new StringRequest(Request.Method.POST, Manage.truth,
                                                                                            responses -> {
                                                                                                try {
                                                                                                    JSONObject jsonObjec = new JSONObject(responses);
                                                                                                    String msg = jsonObjec.getString("message");
                                                                                                    int status = jsonObjec.getInt("success");
                                                                                                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                                                                                    if (status == 1) {
                                                                                                        startActivity(new Intent(getApplicationContext(), AssignArt.class));
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
                                                                                            para.put("serial", serial);
                                                                                            para.put("name", full);
                                                                                            para.put("phone", phon);
                                                                                            para.put("email", email);
                                                                                            para.put("ser_id", servMode.getSer_id());
                                                                                            para.put("insta", "1");
                                                                                            para.put("category", servMode.getCategory());
                                                                                            para.put("location", servMode.getLocation());
                                                                                            para.put("site_date", servMode.getSite_date());
                                                                                            para.put("cust_id", servMode.getCust_id());
                                                                                            para.put("date", new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date()));
                                                                                            return para;
                                                                                        }
                                                                                    });
                                                                                    return true;
                                                                                });
                                                                            }
                                                                        }
                                                                    } catch (Exception e) {
                                                                        e.printStackTrace();
                                                                        Toast.makeText(this, "an error occurred", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }, error -> {
                                                            Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show();
                                                        }) {
                                                            @Nullable
                                                            @Override
                                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                                Map<String, String> para = new HashMap<>();
                                                                para.put("email", email);
                                                                return para;
                                                            }
                                                        });
                                                    });
                                                    alex.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view1 -> {
                                                        if (!artisans.isEmpty()) {
                                                            artisans.clear();
                                                        }
                                                        artisanAdapt.notifyDataSetChanged();
                                                        alex.cancel();
                                                    });
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                    Toast.makeText(this, "an error occurred", Toast.LENGTH_SHORT).show();
                                                }
                                            }, error -> {
                                        Toast.makeText(this, "connection error", Toast.LENGTH_SHORT).show();
                                    }));
                                } else {
                                    String mes = jsonObject.getString("message");
                                    Toast.makeText(this, mes, Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(this, "an error occurred", Toast.LENGTH_SHORT).show();
                            }
                        }, error -> {
                    Toast.makeText(this, "connection error", Toast.LENGTH_SHORT).show();
                }));
                return true;
            });
        }));
    }

    private void getServ() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(new StringRequest(Request.Method.POST, Manage.getPeD,
                response -> {
                    try {
                        jsonObject = new JSONObject(response);
                        int st = jsonObject.getInt("success");
                        if (st == 1) {
                            jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                servMode = new ServMode(jsonObject.getString("ser_id"), jsonObject.getString("category"), jsonObject.getString("checker"),
                                        Manage.img + jsonObject.getString("image"), jsonObject.getString("description"), jsonObject.getString("site_date"),
                                        jsonObject.getString("price"), jsonObject.getString("size"), jsonObject.getString("cust_id"), jsonObject.getString("fullname"),
                                        jsonObject.getString("phone"), jsonObject.getString("location"), jsonObject.getString("landmark"), jsonObject.getString("house"),
                                        jsonObject.getString("reg_date"), jsonObject.getString("status"), jsonObject.getString("pay"), jsonObject.getString("fina"),
                                        jsonObject.getString("insta"), jsonObject.getString("updated"));
                                servModeArrayList.add(servMode);
                            }
                            servAda = new ServAda(AssignArt.this, R.layout.acc, servModeArrayList);
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
                para.put("fina", "1");
                para.put("insta", "0");
                return para;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.hist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Hist:
                startActivity(new Intent(getApplicationContext(), AssignHist.class));
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}