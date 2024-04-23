package com.example.kenbro.Invent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;
import com.example.kenbro.Home.Manage;
import com.example.kenbro.Home.ProdAda;
import com.example.kenbro.Home.ProdMode;
import com.example.kenbro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ViewProd extends AppCompatActivity {
    String myCategory;
    RequestQueue requestQueue;
    ListView listView;
    SearchView searchView;
    ArrayList<ProdMode> SubjectList = new ArrayList<>();
    ProdAda suppAda;
    ProdMode productMode;
    AlertDialog.Builder builder;
    Rect rect;
    Window window;
    LayoutInflater layoutInflater;
    AlertDialog alertDialog;
    View veve;
    String encodedimage, myTyped;
    EditText des, qty, pri;
    ImageView imageView;
    Button sub, can, subb;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent inte = getIntent();
        if (inte != null) {
            myCategory = inte.getStringExtra("category");
        }
        Objects.requireNonNull(getSupportActionBar()).setTitle(myCategory);
        setContentView(R.layout.activity_view_prod);
        listView = findViewById(R.id.listing);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.mySearch);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            productMode = (ProdMode) parent.getItemAtPosition(position);
            builder = new AlertDialog.Builder(this);
            rect = new Rect();
            window = this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            layoutInflater = (LayoutInflater) (this.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
            veve = layoutInflater.inflate(R.layout.update_prod, null);
            veve.setMinimumHeight((int) (rect.height() * 0.02));
            veve.setMinimumWidth((int) (rect.width() * 0.9));
            textView = veve.findViewById(R.id.myText);
            des = veve.findViewById(R.id.editDesc);
            qty = veve.findViewById(R.id.edtQty);
            pri = veve.findViewById(R.id.edtPrice);
            imageView = veve.findViewById(R.id.imager);
            sub = veve.findViewById(R.id.btnUp);
            can = veve.findViewById(R.id.btnCancel);
            subb = veve.findViewById(R.id.btnDelete);
            textView.setText("TYPE: " + productMode.getType());
            des.setText(productMode.getDescription());
            qty.setText(productMode.getQuantity());
            pri.setText(productMode.getPrice());
            Glide.with(this).load(productMode.getImage()).into(imageView);
            builder.setView(veve);
            builder.setTitle(productMode.getCategory());
            alertDialog = builder.create();
            can.setOnClickListener(v1 -> {
                alertDialog.cancel();
            });
            sub.setOnClickListener(v -> {
                final String myDes = des.getText().toString().trim();
                final String myQty = qty.getText().toString().trim();
                final String myPrice = pri.getText().toString().trim();
                if (myDes.isEmpty()) {
                    des.setError("??");
                    des.requestFocus();
                } else if (myQty.isEmpty()) {
                    qty.setError("??");
                    qty.requestFocus();
                } else if (myPrice.isEmpty()) {
                    pri.setError("??");
                    pri.requestFocus();
                } else if (Float.parseFloat(myQty) < 1) {
                    qty.setError("why 0");
                    qty.requestFocus();
                } else if (Float.parseFloat(myPrice) < 10) {
                    pri.setError("low price");
                    pri.requestFocus();
                } else {
                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(new StringRequest(Request.Method.POST, Manage.updateProdu,
                            response -> {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    int st = jsonObject.getInt("status");
                                    String msg = jsonObject.getString("message");
                                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                    if (st == 1) {
                                        alertDialog.cancel();
                                        startActivity(new Intent(getApplicationContext(), InventDash.class));
                                        finish();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(this, "an error occurred", Toast.LENGTH_SHORT).show();
                                }
                            }, error -> {
                        Toast.makeText(this, "network error", Toast.LENGTH_SHORT).show();
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> para = new HashMap<>();
                            para.put("prod_id", productMode.getId());
                            para.put("description", myDes);
                            para.put("quantity", myQty);
                            para.put("price", myPrice);
                            return para;
                        }
                    });
                }
            });
            subb.setOnClickListener(v -> {
                requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(new StringRequest(Request.Method.POST, Manage.delete,
                        response -> {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int st = jsonObject.getInt("status");
                                String msg = jsonObject.getString("message");
                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                if (st == 1) {
                                    alertDialog.cancel();
                                    startActivity(new Intent(getApplicationContext(), InventDash.class));
                                    finish();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(this, "an error occurred", Toast.LENGTH_SHORT).show();
                            }
                        }, error -> {
                    Toast.makeText(this, "network error", Toast.LENGTH_SHORT).show();
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> para = new HashMap<>();
                        para.put("prod_id", productMode.getId());
                        return para;
                    }
                });
            });
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        });
        getProd();
    }

    private void getProd() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(new StringRequest(Request.Method.POST, Manage.viewProd,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("prod_id");
                                String category = jsonObject.getString("category");
                                String type = jsonObject.getString("type");
                                String description = jsonObject.getString("description");
                                String image = jsonObject.getString("image");
                                String imagery = Manage.img + image;
                                String qty = jsonObject.getString("qty");
                                String quantity = jsonObject.getString("quantity");
                                String price = jsonObject.getString("price");
                                String reg_date = jsonObject.getString("reg_date");
                                productMode = new ProdMode(id, category, type, description, imagery, qty, quantity, price, reg_date);
                                SubjectList.add(productMode);
                            }
                            suppAda = new ProdAda(ViewProd.this, R.layout.category, SubjectList);
                            listView.setAdapter(suppAda);
                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String text) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    suppAda.getFilter().filter(newText);
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

        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> para = new HashMap<>();
                para.put("category", myCategory);
                return para;
            }
        });
    }
}