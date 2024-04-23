package com.example.kenbro.Supplier;

import android.annotation.SuppressLint;
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
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
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
import com.example.kenbro.Home.Anologies;
import com.example.kenbro.Home.ForgiveAda;
import com.example.kenbro.Home.ForgiveMode;
import com.example.kenbro.Home.Manage;
import com.example.kenbro.Home.SupModel;
import com.example.kenbro.Home.SupSession;
import com.example.kenbro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SupPAy extends AppCompatActivity {
    SupSession supSession;
    SupModel custModel;
    AlertDialog.Builder builder;
    View layer;
    Rect rect;
    Window window;
    TextView dater, details;
    LayoutInflater layoutInflater, inflater;
    RequestQueue requestQueue;
    Spinner categor, typed;
    String myTpe;
    ForgiveMode payModel;
    ArrayList<ForgiveMode> payModelArrayList = new ArrayList<>();
    ForgiveAda payAda;
    ListView listView, listing;
    SearchView searchView;
    Toast toast;
    FrameLayout.LayoutParams params;
    FrameLayout frameLayout;
    JSONObject jsonObject;
    JSONArray jsonArray;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Payment");
        setContentView(R.layout.activity_sup_pay);
        supSession = new SupSession(getApplicationContext());
        custModel = supSession.getSupDetails();
        listView = findViewById(R.id.listing);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.mySearch);
        getPay();
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            payModel = (ForgiveMode) adapterView.getItemAtPosition(i);
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Payment Details");
            rect = new Rect();
            window = this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layer = layoutInflater.inflate(R.layout.rada_yako, null);
            layer.setMinimumWidth((int) (rect.width() * 1.0));
            layer.setMinimumHeight((int) (rect.height() * 0.01));
            dater = layer.findViewById(R.id.date);
            dater.setText(payModel.getDate());
            details = layer.findViewById(R.id.details);
            TextView textView = layer.findViewById(R.id.header);
            textView.setText(Html.fromHtml("<font>KENBRO INDUSTRIES LTD<br>P.O. Box 41277 - 00100<br>PARKLANDS, NAIROBI<br>info@kenbro.co.ke<br><u>+254 (020) 242 2242/3</u></font>"));
            details.setText(Html.fromHtml("<font><b><big><strong>supID</strong></big></b> :" + payModel.getSupplier() + "<br><b><big><strong>name</strong></big></b> :" + payModel.getFullname() + "<br><b><big><strong>phone</strong></big></b> :" + payModel.getPhone() + "<br><br><b><big><strong><i><u>MPESA</u></i></strong></big></b> :" + payModel.getMpesa() + "<br><b><big><strong>amount</strong></big></b> :Kes" + payModel.getAmount() + "</font>"));
            frameLayout = new FrameLayout(this);
            params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = getResources().getDimensionPixelSize(R.dimen.richer);
            params.rightMargin = getResources().getDimensionPixelSize(R.dimen.richer);
            layer.setLayoutParams(params);
            frameLayout.addView(layer);
            builder.setView(frameLayout);
            builder.setPositiveButton("Print", (dd, d) -> {
            });
            builder.setNegativeButton("Close", (dd, d) -> {
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.origi);
            alertDialog.getWindow().setGravity(Gravity.CENTER);
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view1 -> {
                alertDialog.cancel();
            });
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view1 -> {
                PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
                printManager.print(getString(R.string.app_name), new Anologies(this, layer.findViewById(R.id.Volvovov)), null);
            });
        });
    }

    private void getPay() {
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(new StringRequest(Request.Method.POST, Manage.oyaBana,
                response -> {
                    try {
                        jsonObject = new JSONObject(response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                payModel = new ForgiveMode(jsonObject.getString("mpesa"), jsonObject.getString("supplier"), jsonObject.getString("fullname"), jsonObject.getString("amount"),
                                        jsonObject.getString("phone"), jsonObject.getString("date"));
                                payModelArrayList.add(payModel);
                            }
                            payAda = new ForgiveAda(SupPAy.this, R.layout.aba, payModelArrayList);
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
                para.put("supplier", custModel.getEntry_no());
                return para;
            }
        });
    }
}