package com.example.kenbro.Dispa;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.print.PrintManager;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kenbro.Home.Anologies;
import com.example.kenbro.Home.BenaAda;
import com.example.kenbro.Home.BenaMode;
import com.example.kenbro.Home.CartModel;
import com.example.kenbro.Home.Manage;
import com.example.kenbro.Home.WordProm;
import com.example.kenbro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OldShip extends AppCompatActivity {
    AlertDialog.Builder builder, alert, bio;
    Rect rect;
    Window window;
    LayoutInflater layoutInflater;
    AlertDialog alertDialog, alex, down;
    View layer;
    RequestQueue requestQueue;
    Spinner categor, typed;
    String myTpe;
    BenaMode payModel;
    ArrayList<BenaMode> payModelArrayList = new ArrayList<>();
    BenaAda payAda;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Shipment History");
        setContentView(R.layout.activity_old_ship);
        listView = findViewById(R.id.listing);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.mySearch);
        getPay();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            payModel = (BenaMode) parent.getItemAtPosition(position);
            builder = new AlertDialog.Builder(this);
            builder.setTitle(Html.fromHtml("<font color='#ff0000'><b><u>Shipment Details</u></b></font>"));
            builder.setMessage("Customer:\nCustomerID: " + payModel.getCust_id() + "\nName: " + payModel.getCust_name() + "\nPhone: " + payModel.getCust_phone() + "\nLocation: " + payModel.getLocation() + " - " + payModel.getLandmark() + " - " + payModel.getHouse() + "\n\nDriver:\nDriverID: " + payModel.getDriver_id() + "\nName: " + payModel.getDriver_name() + "\nPhone: " + payModel.getDriver_phone() + "\nEmail: " + payModel.getDriver_email() + "\nLicense: " + payModel.getLicense() + "\n\nEntryDate: " + payModel.getEntry_date() + "\nDriverStatus: " + payModel.getDrive() + "\nCustomerStatus: " + payModel.getCustom() + "\nLastUpdate: " + payModel.getUpdate_date());
            builder.setNeutralButton(Html.fromHtml("<font color='#ff0000'>Cart</font>"), (dd, d) -> {
            });
            builder.setNegativeButton(Html.fromHtml("<font color='#ff0000'>Close</font>"), (dd, d) -> {
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
                                    cartAda = new WordProm(OldShip.this, R.layout.category, cartModArrayList);
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
                        para.put("serial", payModel.getPayid());
                        return para;
                    }
                });
            });
        });
    }

    private void getPay() {
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(new StringRequest(Request.Method.POST, Manage.kensilver,
                response -> {
                    try {
                        jsonObject = new JSONObject(response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                //id,payid,driver_id,driver_name,driver_phone,driver_email,cust_id,cust_name,cust_phone
//location,landmark,house,drive,custom,entry_date,update_date
                                payModel = new BenaMode(jsonObject.getString("id"), jsonObject.getString("payid"), jsonObject.getString("driver_id"),
                                        jsonObject.getString("driver_name"), jsonObject.getString("driver_phone"), jsonObject.getString("driver_email"),
                                        jsonObject.getString("license"), jsonObject.getString("cust_id"), jsonObject.getString("cust_name"), jsonObject.getString("cust_phone"),
                                        jsonObject.getString("location"), jsonObject.getString("landmark"), jsonObject.getString("house"),
                                        jsonObject.getString("drive"), jsonObject.getString("custom"), jsonObject.getString("entry_date"), jsonObject.getString("update_date"));
                                payModelArrayList.add(payModel);
                            }
                            payAda = new BenaAda(OldShip.this, R.layout.acc, payModelArrayList);
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

        }));
    }
}