package com.example.kenbro.Finance;

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
import android.widget.EditText;
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
import com.example.kenbro.Home.CartModel;
import com.example.kenbro.Home.Manage;
import com.example.kenbro.Home.PayAda;
import com.example.kenbro.Home.PayModel;
import com.example.kenbro.Home.WordProm;
import com.example.kenbro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RejectP extends AppCompatActivity {
    AlertDialog.Builder builder, alert;
    AlertDialog alertDialog, dialog;
    View layer;
    Rect rect;
    Window window;
    EditText qty;
    LayoutInflater layoutInflater, inflater;
    RequestQueue requestQueue;
    Spinner categor, typed;
    String myTpe;
    PayModel payModel;
    ArrayList<PayModel> payModelArrayList = new ArrayList<>();
    PayAda payAda;
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
        Objects.requireNonNull(getSupportActionBar()).setTitle("Rejected Payment");
        setContentView(R.layout.activity_reject_p);
        listView = findViewById(R.id.listing);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.mySearch);
        getPay();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            payModel = (PayModel) parent.getItemAtPosition(position);
            builder = new AlertDialog.Builder(this);
            builder.setTitle(Html.fromHtml("<font color='#7107DA'><b><u>Payment Details</u></b></font>"));
            if (Float.parseFloat(payModel.getShip()) == 0) {
                builder.setMessage("CustomerID: " + payModel.getCust_id() + "\nName: " + payModel.getName() + "\nPhone: " + payModel.getPhone() + "\n\nMPESA: " + payModel.getMpesa() + "\nAmount: KES" + payModel.getAmount() + "\nStatus: " + payModel.getStatus() + "\nDate: " + payModel.getReg_date());
            } else {
                builder.setMessage("CustomerID: " + payModel.getCust_id() + "\nName: " + payModel.getName() + "\nPhone: " + payModel.getPhone() + "\nLocation: " + payModel.getLocation() + " - " + payModel.getLandmark() + "\n\nmpesa: " + payModel.getMpesa() + "\nOrder: KES" + payModel.getOrders() + "\nShip: KES" + payModel.getShip() + "\nAmount: KES" + payModel.getAmount() + "\nStatus: " + payModel.getStatus() + "\nDate: " + payModel.getReg_date());
            }
            builder.setNeutralButton(Html.fromHtml("<font color='#ff0000'>Cart</font>"), (dd, d) -> {
            });
            AlertDialog alertDialog = builder.create();
            //alertDialog.setCancelable(false);
            //alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.origi);
            alertDialog.getWindow().setGravity(Gravity.CENTER);
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
                                    cartAda = new WordProm(RejectP.this, R.layout.category, cartModArrayList);
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
                                            bon.cancel();
                                        }
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
        });
    }

    private void getPay() {
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(new StringRequest(Request.Method.POST, Manage.getMoney,
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
                            payAda = new PayAda(RejectP.this, R.layout.acc, payModelArrayList);
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
                para.put("status", "2");
                return para;
            }
        });
    }
}