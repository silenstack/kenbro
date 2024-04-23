package com.example.kenbro.Customer;

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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
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
import com.example.kenbro.Home.Anologies;
import com.example.kenbro.Home.CartModel;
import com.example.kenbro.Home.CustModel;
import com.example.kenbro.Home.CustSession;
import com.example.kenbro.Home.Maksuudi;
import com.example.kenbro.Home.Manage;
import com.example.kenbro.Home.NilishaSema;
import com.example.kenbro.Home.PayModel;
import com.example.kenbro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Reports extends AppCompatActivity {
    CustModel custModel;
    CustSession custSession;
    AlertDialog.Builder builder, alert;
    AlertDialog alertDialog, dialog;
    View layer;
    Rect rect;
    Window window;
    EditText qty;
    LayoutInflater layoutInflater;
    RequestQueue requestQueue;
    PayModel payModel;
    ArrayList<PayModel> payModelArrayList = new ArrayList<>();
    NilishaSema payAda;
    ListView listView, listing;
    SearchView searchView;
    Toast toast;
    FrameLayout.LayoutParams params;
    FrameLayout frameLayout;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ArrayList<CartModel> SubjectList = new ArrayList<>();
    Maksuudi suppAda;
    CartModel productMode;
    TextView textView, cashed, details;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Receipts");
        setContentView(R.layout.activity_reports);
        custSession = new CustSession(getApplicationContext());
        custModel = custSession.getCustDetails();
        listView = findViewById(R.id.listing);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.mySearch);
        getPay();
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            payModel = (PayModel) adapterView.getItemAtPosition(i);
            requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(new StringRequest(Request.Method.POST, Manage.getCar,
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("trust");
                            if (success == 1) {
                                JSONArray jsonArray = jsonObject.getJSONArray("victory");
                                for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                                    jsonObject = jsonArray.getJSONObject(i1);
                                    String reg = jsonObject.getString("reg");
                                    String serial = jsonObject.getString("serial");
                                    String cust_id = jsonObject.getString("cust_id");
                                    String product = jsonObject.getString("product");
                                    String category = jsonObject.getString("category");
                                    String type = jsonObject.getString("type");
                                    String price = jsonObject.getString("price");
                                    String quantity = jsonObject.getString("quantity");
                                    String image = jsonObject.getString("image");
                                    String imagery = Manage.img + image;
                                    String status = jsonObject.getString("status");
                                    String reg_date = jsonObject.getString("reg_date");
                                    productMode = new CartModel(reg, serial, cust_id, product, category, type, price, quantity, imagery, status, reg_date);
                                    SubjectList.add(productMode);
                                }
                                builder = new AlertDialog.Builder(this);
                                rect = new Rect();
                                window = this.getWindow();
                                window.getDecorView().getWindowVisibleDisplayFrame(rect);
                                layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                layer = layoutInflater.inflate(R.layout.receipt, null);
                                layer.setMinimumWidth((int) (rect.width() * 0.9f));
                                layer.setMinimumHeight((int) (rect.height() * 0.08f));
                                textView = layer.findViewById(R.id.header);
                                details = layer.findViewById(R.id.myTexter);
                                cashed = layer.findViewById(R.id.shipper);
                                TextView texter = layer.findViewById(R.id.date);
                                texter.setText(Html.fromHtml("<font color='#C57602'><small><i>serial:</i></small><b>" + payModel.getEntry() + "</b></font>"));
                                listing = layer.findViewById(R.id.listerS);
                                listing.setTextFilterEnabled(true);
                                suppAda = new Maksuudi(Reports.this, R.layout.why_tell, SubjectList);
                                listing.setAdapter(suppAda);
                                builder.setView(layer);
                                textView.setText(Html.fromHtml("<font>KENBRO INDUSTRIES LTD<br>P.O. Box 41277 - 00100<br>PARKLANDS, NAIROBI<br>info@kenbro.co.ke<br><u>+254 (020) 242 2242/3</u></font>"));
                                cashed.setText("Orders KES" + payModel.getOrders() + "\nShipping: KES" + payModel.getShip() + "\nTotal KES" + payModel.getAmount());
                                details.setText("Customer: " + custModel.getFname() + " " + custModel.getLname() + "\nphone: " + payModel.getPhone() + "\nDate: " + payModel.getReg_date());
                                builder.setPositiveButton("print", (dialog, ids) -> {
                                });
                                builder.setNegativeButton("close", (dialog, ids) -> {
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.setCancelable(false);
                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.show();
                                alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                                alertDialog.getWindow().setGravity(Gravity.CENTER);
                                alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view1 -> {
                                    if (!SubjectList.isEmpty()) {
                                        SubjectList.clear();
                                        suppAda.notifyDataSetChanged();
                                    }
                                    alertDialog.cancel();
                                });
                                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view1 -> {
                                    PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
                                    printManager.print(getString(R.string.app_name), new Anologies(this, layer.findViewById(R.id.titled)), null);
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
                    para.put("serial", payModel.getEntry());
                    return para;
                }
            });
        });
    }

    private void getPay() {
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(new StringRequest(Request.Method.POST, Manage.getApp,
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
                            payAda = new NilishaSema(Reports.this, R.layout.aba, payModelArrayList);
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
                para.put("cust_id", custModel.getEntry_no());
                return para;
            }
        });
    }
}