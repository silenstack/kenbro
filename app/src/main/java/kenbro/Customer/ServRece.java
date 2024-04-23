package com.example.kenbro.Customer;

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
import com.example.kenbro.Home.DownLine;
import com.example.kenbro.Home.Manage;
import com.example.kenbro.Home.OnlyOne;
import com.example.kenbro.Home.UssrMode;
import com.example.kenbro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ServRece extends AppCompatActivity {
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
    ArrayList<UssrMode> ussrModeArrayList = new ArrayList<>();
    UssrMode ussrMode;
    DownLine ussrAda;
    ArrayList<OnlyOne> onlyOneArrayList = new ArrayList<>();
    OnlyOne onlyOne;
    ListView listView, listing;
    SearchView searchView;
    Toast toast;
    FrameLayout.LayoutParams params;
    FrameLayout frameLayout;
    JSONObject jsonObject;
    JSONArray jsonArray;
    CartModel productMode;
    TextView textView, cashed, details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Receipts");
        setContentView(R.layout.activity_serv_rece);
        custSession = new CustSession(getApplicationContext());
        custModel = custSession.getCustDetails();
        listView = findViewById(R.id.listing);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.mySearch);
        getPay();
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            ussrMode = (UssrMode) adapterView.getItemAtPosition(i);
            requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(new StringRequest(Request.Method.POST, Manage.getSerr,
                    response -> {
                        try {
                            jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("trust");
                            if (success == 1) {
                                jsonArray = jsonObject.getJSONArray("victory");
                                for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                                    jsonObject = jsonArray.getJSONObject(i1);
                                    onlyOne = new OnlyOne(jsonObject.getString("ser_id"), jsonObject.getString("category"), jsonObject.getString("checker"),
                                            Manage.img + jsonObject.getString("image"), jsonObject.getString("description"), jsonObject.getString("site_date"),
                                            jsonObject.getString("price"), jsonObject.getString("size"), jsonObject.getString("cust_id"), jsonObject.getString("fullname"),
                                            jsonObject.getString("phone"), jsonObject.getString("location"), jsonObject.getString("landmark"), jsonObject.getString("house"),
                                            jsonObject.getString("reg_date"), jsonObject.getString("status"), jsonObject.getString("pay"), jsonObject.getString("fina"),
                                            jsonObject.getString("insta"), jsonObject.getString("updated"));
                                    onlyOneArrayList.add(onlyOne);
                                }
                                builder = new AlertDialog.Builder(this);
                                rect = new Rect();
                                window = this.getWindow();
                                window.getDecorView().getWindowVisibleDisplayFrame(rect);
                                layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                layer = layoutInflater.inflate(R.layout.sweet, null);
                                layer.setMinimumWidth((int) (rect.width() * 0.9f));
                                layer.setMinimumHeight((int) (rect.height() * 0.08f));
                                textView = layer.findViewById(R.id.header);
                                details = layer.findViewById(R.id.myTexter);
                                cashed = layer.findViewById(R.id.shipper);
                                TextView texter = layer.findViewById(R.id.date);
                                texter.setText(Html.fromHtml("<font color='#C57602'><small><i>serial:</i></small><b>" + ussrMode.getPayid() + "</b></font>"));
                                TextView meme = layer.findViewById(R.id.myDetails);
                                builder.setView(layer);
                                meme.setText("Site: " + onlyOne.getLocation() + "-" + onlyOne.getLandmark() + "-" + onlyOne.getHouse() + "\n\nService: " + onlyOne.getCategory() + "\nDescription: " + onlyOne.getDescription() + "\nEstimatedSize: " + onlyOne.getSize() + "\nEstimatedStartDate: " + onlyOne.getSite_date());
                                textView.setText(Html.fromHtml("<font>KENBRO INDUSTRIES LTD<br>P.O. Box 41277 - 00100<br>PARKLANDS, NAIROBI<br>info@kenbro.co.ke<br><u>+254 (020) 242 2242/3</u></font>"));
                                cashed.setText("Cost KES" + ussrMode.getAmount());
                                details.setText("Customer: " + ussrMode.getFullname() + "\nphone: " + ussrMode.getPhone() + "\nDate: " + ussrMode.getReg_date());
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
                                    if (!onlyOneArrayList.isEmpty()) {
                                        onlyOneArrayList.clear();
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
                    para.put("ser_id", ussrMode.getSer_id());
                    return para;
                }
            });
        });
    }

    private void getPay() {
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(new StringRequest(Request.Method.POST, Manage.getOo,
                response -> {
                    try {
                        jsonObject = new JSONObject(response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                ussrMode = new UssrMode(jsonObject.getString("payid"), jsonObject.getString("ser_id"), jsonObject.getString("mpesa"),
                                        jsonObject.getString("amount"), jsonObject.getString("cust_id"), jsonObject.getString("fullname"),
                                        jsonObject.getString("phone"), jsonObject.getString("location"), jsonObject.getString("landmark"),
                                        jsonObject.getString("house"), jsonObject.getString("status"), jsonObject.getString("reg_date"));
                                ussrModeArrayList.add(ussrMode);
                            }
                            ussrAda = new DownLine(ServRece.this, R.layout.aba, ussrModeArrayList);
                            listView.setAdapter(ussrAda);
                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String text) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    ussrAda.getFilter().filter(newText);
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
                para.put("status", "1");
                return para;
            }
        });
    }
}