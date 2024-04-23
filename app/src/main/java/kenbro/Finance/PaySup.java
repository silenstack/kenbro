package com.example.kenbro.Finance;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.print.PrintManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kenbro.Home.Anologies;
import com.example.kenbro.Home.CashAda;
import com.example.kenbro.Home.CashMode;
import com.example.kenbro.Home.Manage;
import com.example.kenbro.Home.Monie;
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

public class PaySup extends AppCompatActivity {
    ListView listView;
    SearchView searchView;
    ArrayList<CashMode> cashModeArrayList = new ArrayList<>();
    CashAda cashAda;
    CashMode cashMode;
    View layer;
    EditText mpesa;
    ImageView imageView;
    TextView textView, amount;
    RequestQueue requestQueue;
    JSONArray jsonArray;
    JSONObject jsonObject;
    Button btn;
    FrameLayout frameLayout;
    FrameLayout.LayoutParams params;
    AlertDialog.Builder builder, alert;
    AlertDialog alertDialog, dialog;
    ArrayList<Monie> monieArrayList = new ArrayList<>();
    Monie monie;
    Rect rect;
    Window window;
    LayoutInflater layoutInflater;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Supply Paying");
        setContentView(R.layout.activity_pay_sup);
        listView = findViewById(R.id.listing);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.mySearch);
        getDeni();
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            cashMode = (CashMode) adapterView.getItemAtPosition(i);
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Basic Details");
            builder.setMessage("supplierID: " + cashMode.getSupplier() + "\nname: " + cashMode.getFullname() + "\ncash: Kes" + cashMode.getAmount() + "\ndisbursement: " + cashMode.getPaid());
            builder.setPositiveButton("Pay", (dialogInterface, i1) -> {
            });
            builder.setNegativeButton("Close", (dialogInterface, i1) -> {
            });
            alertDialog = builder.create();
            alertDialog.show();
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.origi);
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view1 -> {
                requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(new StringRequest(Request.Method.POST, Manage.getMeme,
                        response -> {
                            try {
                                jsonObject = new JSONObject(response);
                                int success = jsonObject.getInt("trust");
                                if (success == 1) {
                                    jsonArray = jsonObject.getJSONArray("victory");
                                    for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                                        jsonObject = jsonArray.getJSONObject(i1);
                                        monie = new Monie(jsonObject.getString("amount"), jsonObject.getString("orders"), jsonObject.getString("service"),
                                                jsonObject.getString("supplier"), jsonObject.getString("balance"), jsonObject.getString("regdate"),
                                                jsonObject.getString("updated"));
                                        monieArrayList.add(monie);
                                    }
                                    if (Float.parseFloat(monie.getBalance()) < Float.parseFloat(cashMode.getAmount())) {
                                        Toast.makeText(this, "There insufficient funds to render supplier payment", Toast.LENGTH_SHORT).show();
                                    } else {
                                        String pesa = String.format("%.0f", Float.parseFloat(monie.getBalance()) - Float.parseFloat(cashMode.getAmount()));
                                        AlertDialog.Builder build = new AlertDialog.Builder(this);
                                        build.setTitle("Funds Analysis");
                                        build.setMessage("Supplier: Kes" + cashMode.getAmount() + "\nAvailableCash: Kes" + monie.getBalance() + "\n\nnewBalance: Kes" + pesa + "\n\nDo you wish to continue?");
                                        build.setPositiveButton("Yes", (tr, r) -> {
                                        });
                                        build.setNegativeButton("Close", (tr, r) -> {
                                        });
                                        AlertDialog bon = build.create();
                                        bon.show();
                                        bon.setCancelable(false);
                                        bon.setCanceledOnTouchOutside(false);
                                        bon.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                                        bon.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view2 -> {
                                            AlertDialog.Builder expen = new AlertDialog.Builder(this);
                                            expen.setTitle("Supplier Payment");
                                            expen.setMessage("supplierID: " + cashMode.getSupplier() + "\nname: " + cashMode.getFullname() + "\nPad Via MPESA\ncash: Kes" + cashMode.getAmount() + "\nphone: " + cashMode.getPhone() + "\nEnter the MPESA code in the next screen");
                                            expen.setPositiveButton("Next", (tr, r) -> {
                                            });
                                            expen.setNegativeButton("Close", (tr, r) -> {
                                            });
                                            AlertDialog mous = expen.create();
                                            mous.show();
                                            mous.setCancelable(false);
                                            mous.setCanceledOnTouchOutside(false);
                                            mous.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                                            mous.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view6 -> {
                                                AlertDialog.Builder key = new AlertDialog.Builder(this);
                                                key.setTitle("Payment");
                                                rect = new Rect();
                                                window = this.getWindow();
                                                window.getDecorView().getWindowVisibleDisplayFrame(rect);
                                                layoutInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
                                                View some = layoutInflater.inflate(R.layout.mpesa, null);
                                                mpesa = some.findViewById(R.id.edtMpesa);
                                                frameLayout = new FrameLayout(this);
                                                params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                                params.leftMargin = getResources().getDimensionPixelSize(R.dimen.richer);
                                                params.rightMargin = getResources().getDimensionPixelSize(R.dimen.richer);
                                                some.setLayoutParams(params);
                                                frameLayout.addView(some);
                                                key.setView(frameLayout);
                                                key.setPositiveButton("Submit", (rt, e) -> {
                                                });
                                                key.setNegativeButton("Close", (rt, e) -> {
                                                });
                                                AlertDialog cool = key.create();
                                                cool.show();
                                                cool.setCancelable(false);
                                                cool.setCanceledOnTouchOutside(false);
                                                cool.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                                                cool.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view3 -> {
                                                    final String Mpe = mpesa.getText().toString().trim();
                                                    if (Mpe.isEmpty()) {
                                                        mpesa.setError("Mpesa Code required");
                                                        mpesa.requestFocus();
                                                    } else if (Mpe.length() < 10) {
                                                        mpesa.setError("Wrong mpesa code");
                                                        mpesa.requestFocus();
                                                    } else {
                                                        requestQueue = Volley.newRequestQueue(getApplicationContext());
                                                        requestQueue.add(new StringRequest(Request.Method.POST, Manage.huhuu,
                                                                response1 -> {
                                                                    try {
                                                                        JSONObject json = new JSONObject(response1);
                                                                        int st = json.getInt("success");
                                                                        String msg = json.getString("message");
                                                                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                                                        if (st == 1) {
                                                                            startActivity(new Intent(getApplicationContext(), PaySup.class));
                                                                            finish();
                                                                        } else if (st == 10) {
                                                                            mpesa.setError(msg);
                                                                            mpesa.requestFocus();
                                                                        }
                                                                    } catch (JSONException e) {
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
                                                                para.put("mpesa", Mpe);
                                                                para.put("amount", cashMode.getAmount());
                                                                para.put("remainder", pesa);
                                                                para.put("supplier", cashMode.getSupplier());
                                                                para.put("fullname", cashMode.getFullname());
                                                                para.put("phone", cashMode.getPhone());
                                                                para.put("reg_date", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new Date()));
                                                                return para;
                                                            }
                                                        });
                                                    }
                                                });
                                                cool.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view3 -> {
                                                    cool.cancel();
                                                });
                                            });
                                            mous.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view6 -> {
                                                mous.cancel();
                                            });
                                        });
                                        bon.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view2 -> {
                                            if (!monieArrayList.isEmpty()) {
                                                monieArrayList.clear();
                                            }
                                            bon.cancel();
                                        });
                                        bon.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    }
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
            });
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view1 -> {
                alertDialog.cancel();
            });
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.lengo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Hist:
                startActivity(new Intent(getApplicationContext(), HistPay.class));
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDeni() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(new StringRequest(Request.Method.POST, Manage.getDeni,
                response -> {
                    try {
                        jsonObject = new JSONObject(response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                cashMode = new CashMode(jsonObject.getString("supplier"), jsonObject.getString("amount"), jsonObject.getString("paid"), jsonObject.getString("fullname"),
                                        jsonObject.getString("phone"));
                                cashModeArrayList.add(cashMode);
                            }
                            cashAda = new CashAda(PaySup.this, R.layout.paster, cashModeArrayList);
                            listView.setAdapter(cashAda);
                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String text) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    cashAda.getFilter().filter(newText);
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
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();

        }));
    }
}