package com.example.kenbro.Invent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.kenbro.Home.Manage;
import com.example.kenbro.Home.ModelAda;
import com.example.kenbro.Home.ModelBid;
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

public class ManageBid extends AppCompatActivity {
    Spinner divi, type;
    EditText qty, price;
    Button subb;
    AlertDialog.Builder builder;
    Rect rect;
    Window window;
    LayoutInflater layoutInflater;
    AlertDialog alertDialog;
    View veve;
    RequestQueue requestQueue;
    ListView listView;
    SearchView searchView;
    ArrayList<ModelBid> modelBidArrayList = new ArrayList<>();
    ModelAda modelAda;
    ModelBid modelBid;
    String myTyped;
    JSONArray jsonArray;
    JSONObject jsonObject;
    FrameLayout frameLayout;
    FrameLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Manage Purchases");
        setContentView(R.layout.activity_manage_bid);
        listView = findViewById(R.id.listing);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.mySearch);
        subb = findViewById(R.id.btnAdd);
        subb.setOnClickListener(v -> {
            builder = new AlertDialog.Builder(this);
            rect = new Rect();
            window = this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            layoutInflater = (LayoutInflater) (this.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
            veve = layoutInflater.inflate(R.layout.add_bid, null);
            veve.setMinimumHeight((int) (rect.height() * 0.02));
            veve.setMinimumWidth((int) (rect.width() * 0.9));
            divi = veve.findViewById(R.id.spindiv);
            type = veve.findViewById(R.id.spinType);
            qty = veve.findViewById(R.id.edtQty);
            price = veve.findViewById(R.id.edtPrice);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Category, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            divi.setAdapter(adapter);
            ArrayAdapter<CharSequence> data = ArrayAdapter.createFromResource(this, R.array.Man, android.R.layout.simple_spinner_item);
            data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ArrayAdapter<CharSequence> cons = ArrayAdapter.createFromResource(this, R.array.Adhesives, android.R.layout.simple_spinner_item);
            cons.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ArrayAdapter<CharSequence> expo = ArrayAdapter.createFromResource(this, R.array.Flooring, android.R.layout.simple_spinner_item);
            expo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ArrayAdapter<CharSequence> ch = ArrayAdapter.createFromResource(this, R.array.Chem, android.R.layout.simple_spinner_item);
            ch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ArrayAdapter<CharSequence> kn = ArrayAdapter.createFromResource(this, R.array.Kan, android.R.layout.simple_spinner_item);
            kn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            divi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String myDiv = divi.getSelectedItem().toString().trim();
                    if (myDiv.equals("Construction Chemical")) {
                        type.setVisibility(View.VISIBLE);
                        type.setAdapter(ch);
                    } else if (myDiv.equals("Floor & Walls")) {
                        type.setVisibility(View.VISIBLE);
                        type.setAdapter(expo);
                    } else if (myDiv.equals("Cabro")) {
                        type.setVisibility(View.VISIBLE);
                        type.setAdapter(kn);
                    } else if (myDiv.equals("ManHole")) {
                        type.setVisibility(View.VISIBLE);
                        type.setAdapter(data);
                    } else if (myDiv.equals("Adhesive")) {
                        type.setVisibility(View.VISIBLE);
                        type.setAdapter(cons);
                    } else {
                        type.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            frameLayout = new FrameLayout(this);
            params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = getResources().getDimensionPixelSize(R.dimen.richer);
            params.rightMargin = getResources().getDimensionPixelSize(R.dimen.richer);
            veve.setLayoutParams(params);
            frameLayout.addView(veve);
            builder.setView(frameLayout);
            builder.setTitle("Upload Bid");
            builder.setNegativeButton("Close", (v1, ve) -> {
            });
            builder.setPositiveButton(Html.fromHtml("<font color='#1E8103'>Submit</font>"), (v1, ve) -> {
            });
            alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.origi);
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view -> {
                alertDialog.cancel();
            });
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view -> {
                final String myDiv = divi.getSelectedItem().toString().trim();
                final String myQty = qty.getText().toString().trim();
                final String myPrice = price.getText().toString().trim();
                if (myDiv.equals("Select Category")) {
                    Toast.makeText(this, "Please select Category", Toast.LENGTH_SHORT).show();
                } else {
                    myTyped = type.getSelectedItem().toString().trim();
                    if (myTyped.equals("Select Type")) {
                        Toast.makeText(this, "select product Type", Toast.LENGTH_SHORT).show();
                    } else if (myQty.isEmpty()) {
                        qty.setError("required");
                        qty.requestFocus();
                    } else if (Float.parseFloat(myQty) < 1) {
                        qty.setError("required");
                        qty.requestFocus();
                    } else if (myPrice.isEmpty()) {
                        price.setError("required");
                        price.requestFocus();
                    } else if (Float.parseFloat(myPrice) < 100) {
                        price.setError("even less than 100???");
                        price.requestFocus();
                    } else {
                        requestQueue = Volley.newRequestQueue(getApplicationContext());
                        requestQueue.add(new StringRequest(Request.Method.POST, Manage.bida,
                                response -> {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        int st = jsonObject.getInt("success");
                                        String msg = jsonObject.getString("message");
                                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                        if (st == 1) {
                                            alertDialog.dismiss();
                                            startActivity(new Intent(getApplicationContext(), ManageBid.class));
                                            finish();
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
                                para.put("quantity", myQty);
                                para.put("category", myDiv);
                                para.put("price", myPrice);
                                para.put("type", myTyped);
                                para.put("date", new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date()));
                                return para;
                            }
                        });
                    }
                }
            });

        });
        listView.setOnItemClickListener((parent, view, position, id) -> {
            modelBid = (ModelBid) parent.getItemAtPosition(position);
            builder = new AlertDialog.Builder(this);
            rect = new Rect();
            window = this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            layoutInflater = (LayoutInflater) (this.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
            veve = layoutInflater.inflate(R.layout.get_bid, null);
            veve.setMinimumHeight((int) (rect.height() * 0.02));
            veve.setMinimumWidth((int) (rect.width() * 0.9));
            qty = veve.findViewById(R.id.edtQty);
            price = veve.findViewById(R.id.edtPrice);
            qty.setText(modelBid.getQuantity());
            price.setText(modelBid.getPrice());
            frameLayout = new FrameLayout(this);
            params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = getResources().getDimensionPixelSize(R.dimen.richer);
            params.rightMargin = getResources().getDimensionPixelSize(R.dimen.richer);
            veve.setLayoutParams(params);
            frameLayout.addView(veve);
            builder.setView(frameLayout);
            builder.setTitle(modelBid.getCategory());
            builder.setMessage(modelBid.getType() + "\nUploaded: " + modelBid.getQuantity() + "\nDate: " + modelBid.getReg_date());
            builder.setNegativeButton("Close", (v1, ve) -> {
            });
            builder.setPositiveButton(Html.fromHtml("<font color='#1E8103'>Update</font>"), (v1, ve) -> {
            });
            builder.setNeutralButton(Html.fromHtml("<font color='#ff0000'>Remove</font>"), (v1, ve) -> {
            });
            alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.origi);
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(viewe -> {
                alertDialog.cancel();
            });
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(viewe -> {
                final String myQty = qty.getText().toString().trim();
                final String myPri = price.getText().toString().trim();
                if (myPri.isEmpty()) {
                    price.setError("??");
                    price.requestFocus();
                } else if (Float.parseFloat(myPri) < 100) {
                    price.setError("??");
                    price.requestFocus();
                } else if (myQty.isEmpty()) {
                    qty.setError("??");
                    qty.requestFocus();
                } else if (Float.parseFloat(myQty) < 1) {
                    qty.setError("??");
                    qty.requestFocus();
                } else {
                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(new StringRequest(Request.Method.POST, Manage.remov,
                            response -> {
                                try {
                                    jsonObject = new JSONObject(response);
                                    int status = jsonObject.getInt("success");
                                    String msg = jsonObject.getString("message");
                                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                    if (status == 1) {
                                        startActivity(new Intent(getApplicationContext(), ManageBid.class));
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                                }
                            }, error -> {
                        Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show();
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> para = new HashMap<>();
                            para.put("id", modelBid.getId());
                            para.put("quantity", myQty);
                            para.put("price", myPri);
                            return para;
                        }
                    });
                }
            });
            alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL).setOnClickListener(viewe -> {
                if (modelBid.getQty().equals(modelBid.getQuantity())) {
                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(new StringRequest(Request.Method.POST, Manage.remove,
                            response -> {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    int status = jsonObject.getInt("success");
                                    String msg = jsonObject.getString("message");
                                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                    if (status == 1) {
                                        startActivity(new Intent(getApplicationContext(), ManageBid.class));
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(this, "Error Occurred", Toast.LENGTH_SHORT).show();
                                }
                            }, error -> {
                        Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show();
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> para = new HashMap<>();
                            para.put("id", modelBid.getId());
                            return para;
                        }
                    });
                } else {
                    Toast.makeText(this, "Could not Drop the Bid", Toast.LENGTH_SHORT).show();
                }
            });
        });
        getProd();
    }

    private void getProd() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(new StringRequest(Request.Method.POST, Manage.seeBid,
                response -> {
                    try {
                        jsonObject = new JSONObject(response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String category = jsonObject.getString("category");
                                String type = jsonObject.getString("type");
                                String pric = jsonObject.getString("price");
                                String qty = jsonObject.getString("qty");
                                String quantity = jsonObject.getString("quantity");
                                String reg_date = jsonObject.getString("reg_date");
                                modelBid = new ModelBid(id, category, type, pric, qty, quantity, reg_date);
                                modelBidArrayList.add(modelBid);
                            }
                            modelAda = new ModelAda(ManageBid.this, R.layout.see_b, modelBidArrayList);
                            listView.setAdapter(modelAda);
                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String text) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    modelAda.getFilter().filter(newText);
                                    return false;
                                }
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