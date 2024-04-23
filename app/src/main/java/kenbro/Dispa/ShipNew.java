package com.example.kenbro.Dispa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.print.PrintManager;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kenbro.Home.Anologies;
import com.example.kenbro.Home.CartModel;
import com.example.kenbro.Home.DiverAda;
import com.example.kenbro.Home.Manage;
import com.example.kenbro.Home.PayModel;
import com.example.kenbro.Home.WordProm;
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

public class ShipNew extends AppCompatActivity {
    AlertDialog.Builder builder, alert, bio;
    Rect rect;
    Window window;
    LayoutInflater layoutInflater;
    AlertDialog alertDialog, alex, down;
    View layer;
    RequestQueue requestQueue;
    Spinner categor, typed;
    String myTpe;
    PayModel payModel;
    ArrayList<PayModel> payModelArrayList = new ArrayList<>();
    DiverAda payAda;
    ListView listView, listing;
    SearchView searchView;
    Toast toast;
    FrameLayout.LayoutParams params;
    FrameLayout frameLayout;
    JSONObject jsonObject;
    JSONArray jsonArray;
    Button button;
    ArrayList<CartModel> cartModArrayList = new ArrayList<>();
    WordProm cartAda;
    CartModel cartMod;
    ArrayList<String> artisans = new ArrayList<>();
    ArrayAdapter<String> artisanAdapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Shipment");
        setContentView(R.layout.activity_ship_new);
        listView = findViewById(R.id.listing);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.mySearch);
        getPay();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            payModel = (PayModel) parent.getItemAtPosition(position);
            builder = new AlertDialog.Builder(this);
            builder.setTitle(Html.fromHtml("<font color='#ff0000'><b><u>Shipment Details</u></b></font>"));
            builder.setMessage("CustomerID: " + payModel.getCust_id() + "\nName: " + payModel.getName() + "\nPhone: " + payModel.getPhone() + "\nLocation: " + payModel.getLocation() + " - " + payModel.getLandmark() + "\n\nFinanceStatus: " + payModel.getStatus() + "\nDate: " + payModel.getReg_date());
            builder.setNeutralButton(Html.fromHtml("<font color='#ff0000'>Cart</font>"), (dd, d) -> {
            });
            builder.setNegativeButton(Html.fromHtml("<font color='#ff0000'>Close</font>"), (dd, d) -> {
            });
            builder.setPositiveButton(Html.fromHtml("<font color='#ff0000'>Add_Driver</font>"), (dd, d) -> {
            });
            alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.origi);
            alertDialog.getWindow().setGravity(Gravity.CENTER);
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view1 -> {
                alertDialog.cancel();
            });
            alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL).setOnClickListener(view1 -> {
                requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(new StringRequest(Request.Method.POST, Manage.getRec,
                        response -> {
                            try {
                                jsonObject = new JSONObject(response);
                                int success = jsonObject.getInt("trust");
                                if (success == 1) {
                                    jsonArray = jsonObject.getJSONArray("victory");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        jsonObject = jsonArray.getJSONObject(i);
                                        cartMod = new CartModel(jsonObject.getString("reg"), jsonObject.getString("serial"), jsonObject.getString("cust_id"),
                                                jsonObject.getString("product"), jsonObject.getString("category"), jsonObject.getString("type"),
                                                jsonObject.getString("price"), jsonObject.getString("quantity"), Manage.img + jsonObject.getString("image"),
                                                jsonObject.getString("status"), jsonObject.getString("reg_date"));
                                        cartModArrayList.add(cartMod);
                                    }
                                    AlertDialog.Builder build = new AlertDialog.Builder(this);
                                    build.setTitle(Html.fromHtml("<font color='#7107DA'><b><u>Items Bought</u></b></font>"));
                                    rect = new Rect();
                                    window = this.getWindow();
                                    window.getDecorView().getWindowVisibleDisplayFrame(rect);
                                    layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    layer = layoutInflater.inflate(R.layout.show_me, null);
                                    layer.setMinimumWidth((int) (rect.width() * 1.0));
                                    layer.setMinimumHeight((int) (rect.height() * 0.01));
                                    listing = layer.findViewById(R.id.availableGrid);
                                    listing.setTextFilterEnabled(true);
                                    cartAda = new WordProm(ShipNew.this, R.layout.category, cartModArrayList);
                                    listing.setAdapter(cartAda);
                                    frameLayout = new FrameLayout(this);
                                    params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    params.leftMargin = getResources().getDimensionPixelSize(R.dimen.richer);
                                    params.rightMargin = getResources().getDimensionPixelSize(R.dimen.richer);
                                    layer.setLayoutParams(params);
                                    frameLayout.addView(layer);
                                    build.setView(frameLayout);
                                    build.setPositiveButton("Close", (tr, r) -> {
                                    });
                                    AlertDialog bon = build.create();
                                    bon.show();
                                    bon.setCancelable(false);
                                    bon.setCanceledOnTouchOutside(false);
                                    bon.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                                    bon.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view2 -> {
                                        if (!cartModArrayList.isEmpty()) {
                                            cartModArrayList.clear();
                                            cartAda.notifyDataSetChanged();
                                        }
                                        bon.cancel();
                                    });
                                    bon.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
                            }

                        }, error -> {
                    Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();

                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> para = new HashMap<>();
                        para.put("serial", payModel.getEntry());
                        return para;
                    }
                });
            });
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view1 -> {
                Toast.makeText(this, Html.fromHtml("<font color='green'>Please long press the Add_Driver Button</font>"), Toast.LENGTH_SHORT).show();
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
                                    requestQueue.add(new JsonObjectRequest(Request.Method.POST, Manage.driv, null,
                                            respons -> {
                                                try {
                                                    jsonArray = respons.getJSONArray("art");
                                                    for (int j = 0; j < jsonArray.length(); j++) {
                                                        jsonObject = jsonArray.getJSONObject(j);
                                                        artisans.add(jsonObject.optString("email"));
                                                    }
                                                    alert = new AlertDialog.Builder(this);
                                                    alert.setTitle("Select Driver Email Address");
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
                                                        requestQueue.add(new StringRequest(Request.Method.POST, Manage.getD,
                                                                respon -> {
                                                                    try {
                                                                        jsonObject = new JSONObject(respon);
                                                                        int st = jsonObject.getInt("success");
                                                                        if (st == 1) {
                                                                            jsonArray = jsonObject.getJSONArray("victory");
                                                                            for (int t = 0; t < jsonArray.length(); t++) {
                                                                                jsonObject = jsonArray.getJSONObject(t);
                                                                                String entry_no = jsonObject.getString("entry_no");
                                                                                String license = jsonObject.getString("license");
                                                                                String full = jsonObject.getString("fullname");
                                                                                String phon = jsonObject.getString("phone");
                                                                                bio = new AlertDialog.Builder(this);
                                                                                bio.setTitle("Confirm Driver");
                                                                                bio.setMessage("Driver Selected:-\nName: " + full + "\nPhone: " + phon + "\nEmail: " + email + "\nLicense: " + license + "\nCLick Submit to attach the driver.");
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
                                                                                    requestQueue.add(new StringRequest(Request.Method.POST, Manage.belowCapa,
                                                                                            responses -> {
                                                                                                try {
                                                                                                    JSONObject jsonObjec = new JSONObject(responses);
                                                                                                    String msg = jsonObjec.getString("message");
                                                                                                    int status = jsonObjec.getInt("success");
                                                                                                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                                                                                    if (status == 1) {
                                                                                                        startActivity(new Intent(getApplicationContext(), ShipNew.class));
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
                                                                                            para.put("driver_id", entry_no);
                                                                                            para.put("driver_name", full);
                                                                                            para.put("driver_phone", phon);//driver_id,driver_name,driver_phone,driver_email,payid,shipping,cust_id,cust_name,cust_phone
                                                                                            para.put("driver_email", email);//location,landmark,house,date
                                                                                            para.put("payid", payModel.getPayid());
                                                                                            para.put("license", license);
                                                                                            para.put("serial", payModel.getEntry());
                                                                                            para.put("shipping", "1");
                                                                                            para.put("cust_id", payModel.getCust_id());
                                                                                            para.put("cust_name", payModel.getName());
                                                                                            para.put("cust_phone", payModel.getPhone());
                                                                                            para.put("location", payModel.getLocation());
                                                                                            para.put("landmark", payModel.getLandmark());
                                                                                            para.put("house", payModel.getHouse());
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
                                                            artisanAdapt.notifyDataSetChanged();
                                                        }
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
        });
    }

    private void getPay() {
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(new StringRequest(Request.Method.POST, Manage.getSh,
                response -> {
                    try {
                        jsonObject = new JSONObject(response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                payModel = new PayModel(jsonObject.getString("payid"), jsonObject.getString("serial"), jsonObject.getString("mpesa"),
                                        jsonObject.getString("amount"), jsonObject.getString("orders"), jsonObject.getString("ship"),
                                        jsonObject.getString("cust_id"), jsonObject.getString("name"), jsonObject.getString("phone"),
                                        jsonObject.getString("location"), jsonObject.getString("landmark"), jsonObject.getString("house"),
                                        jsonObject.getString("status"), jsonObject.getString("comment"), jsonObject.getString("shipping"), jsonObject.getString("reg_date"));
                                payModelArrayList.add(payModel);
                            }
                            payAda = new DiverAda(ShipNew.this, R.layout.acc, payModelArrayList);
                            listView.setAdapter(payAda);
                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String text) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    payAda.getFilter().filter(newText);
                                    return false;
                                }
                            });
                            findViewById(R.id.btnPrint).setVisibility(View.VISIBLE);
                            findViewById(R.id.btnPrint).setOnClickListener(view -> {
                                PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
                                printManager.print(getString(R.string.app_name), new Anologies(this, findViewById(R.id.myHeader)), null);
                            });
                        } else if (success == 0) {
                            String msg = jsonObject.getString("mine");
                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
                    }

                }, error -> {
            Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();

        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> para = new HashMap<>();
                para.put("status", "1");
                para.put("shipping", "0");
                return para;
            }
        });
    }
}