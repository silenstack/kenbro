package com.example.kenbro.Finance;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.print.PrintManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kenbro.Home.Anologies;
import com.example.kenbro.Home.Manage;
import com.example.kenbro.Home.OnlyOne;
import com.example.kenbro.Home.UssrAda;
import com.example.kenbro.Home.UssrMode;
import com.example.kenbro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RejServ extends AppCompatActivity {
    Spinner divi, sizer, spinner;
    TextView cate, siz, desc;
    EditText description, land, house, mpesa;
    AlertDialog.Builder builder, alert;
    Rect rect;
    Window window;
    LayoutInflater layoutInflater;
    AlertDialog alertDialog;
    View veve;
    RequestQueue requestQueue;
    ListView listView;
    SearchView searchView;
    ArrayList<UssrMode> ussrModeArrayList = new ArrayList<>();
    UssrMode ussrMode;
    UssrAda ussrAda;
    ArrayList<OnlyOne> onlyOneArrayList = new ArrayList<>();
    OnlyOne onlyOne;
    JSONArray jsonArray;
    JSONObject jsonObject;
    FrameLayout frameLayout;
    FrameLayout.LayoutParams params;
    ImageView added;
    RelativeLayout relativeLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Rejected Service Payments");
        setContentView(R.layout.activity_rej_serv);
        listView = findViewById(R.id.listing);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.mySearch);
        getPay();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            ussrMode = (UssrMode) parent.getItemAtPosition(position);
            builder = new AlertDialog.Builder(this);
            builder.setTitle("ServicePayment Details");
            builder.setMessage("CustomerID: " + ussrMode.getCust_id() + "\nName: " + ussrMode.getFullname() + "\nPhone: " + ussrMode.getPhone() + "\nSiteLocation: " + ussrMode.getLocation() + " - " + ussrMode.getLandmark() + "-" + ussrMode.getHouse() + "\n\nmpesa: " + ussrMode.getMpesa() + "\nAmount: KES" + ussrMode.getAmount() + "\nStatus: " + ussrMode.getStatus() + "\nDate: " + ussrMode.getReg_date());
            builder.setPositiveButton("Service", (dd, d) -> {
            });
            builder.setNegativeButton("Close", (dd, d) -> {
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.origi);
            alertDialog.getWindow().setGravity(Gravity.CENTER);
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view1 -> {
                alertDialog.cancel();
            });
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view1 -> {
                requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(new StringRequest(Request.Method.POST, Manage.getSerr,
                        response -> {
                            try {
                                jsonObject = new JSONObject(response);
                                int success = jsonObject.getInt("trust");
                                if (success == 1) {
                                    jsonArray = jsonObject.getJSONArray("victory");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        jsonObject = jsonArray.getJSONObject(i);
                                        onlyOne = new OnlyOne(jsonObject.getString("ser_id"), jsonObject.getString("category"), jsonObject.getString("checker"),
                                                Manage.img + jsonObject.getString("image"), jsonObject.getString("description"), jsonObject.getString("site_date"),
                                                jsonObject.getString("price"), jsonObject.getString("size"), jsonObject.getString("cust_id"), jsonObject.getString("fullname"),
                                                jsonObject.getString("phone"), jsonObject.getString("location"), jsonObject.getString("landmark"), jsonObject.getString("house"),
                                                jsonObject.getString("reg_date"), jsonObject.getString("status"), jsonObject.getString("pay"), jsonObject.getString("fina"),
                                                jsonObject.getString("insta"), jsonObject.getString("updated"));
                                        onlyOneArrayList.add(onlyOne);
                                        alert = new AlertDialog.Builder(this);
                                        alert.setTitle(onlyOne.getFullname() + " Payment Form");
                                        alert.setMessage("Customer: " + onlyOne.getFullname() + "\n" + onlyOne.getPhone() + "\nSite: " + onlyOne.getLocation() + "-" + onlyOne.getLandmark() + "-" + onlyOne.getHouse() + "\n\nService: " + onlyOne.getCategory() + "\nDescription: " + onlyOne.getDescription() + "\nEstimatedSize: " + onlyOne.getSize() + "\nEstimatedStartDate: " + onlyOne.getSite_date() + "\nRequestDate: " + onlyOne.getReg_date() + "\nStatus: " + onlyOne.getStatus());
                                        alert.setNegativeButton("Close", (tr, r) -> {
                                        });
                                        AlertDialog dedd = alert.create();
                                        dedd.show();
                                        dedd.setCanceledOnTouchOutside(false);
                                        dedd.setCancelable(false);
                                        dedd.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                                        dedd.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view1r -> {
                                            dedd.cancel();
                                        });
                                    }
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
                        para.put("ser_id", ussrMode.getSer_id());
                        return para;
                    }
                });
            });
        });
    }

    private void getPay() {
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(new StringRequest(Request.Method.POST, Manage.getPaa,
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
                            ussrAda = new UssrAda(RejServ.this, R.layout.acc, ussrModeArrayList);
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