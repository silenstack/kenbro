package com.example.kenbro.Invent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.kenbro.Home.Manage;
import com.example.kenbro.Home.SupModel;
import com.example.kenbro.Home.SupSession;
import com.example.kenbro.Home.SuppliedAda;
import com.example.kenbro.Home.SuppliedMode;
import com.example.kenbro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class PastSup extends AppCompatActivity {
    SuppliedMode suppliedMode;
    ArrayList<SuppliedMode> suppliedModeArrayList = new ArrayList<>();
    SuppliedAda suppliedAda;
    ListView listView;
    SearchView searchView;
    EditText EditPri;
    Button Send;
    ImageView imageView;
    SupSession supSession;
    SupModel custModel;
    EditText des, qty, pri;
    AlertDialog.Builder builder;
    Rect rect;
    Window window;
    LayoutInflater layoutInflater;
    AlertDialog alertDialog;
    View veve;
    RequestQueue requestQueue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Supply History");
        setContentView(R.layout.activity_past_sup);
        listView = findViewById(R.id.listing);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.mySearch);
        getProd();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            suppliedMode = (SuppliedMode) parent.getItemAtPosition(position);
            builder = new AlertDialog.Builder(this);
            rect = new Rect();
            window = this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            layoutInflater = (LayoutInflater) (this.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
            veve = layoutInflater.inflate(R.layout.have_all, null);
            veve.setMinimumHeight((int) (rect.height() * 0.02));
            veve.setMinimumWidth((int) (rect.width() * 0.9));
            qty = veve.findViewById(R.id.edtQty);
            des = veve.findViewById(R.id.editDesc);
            qty = veve.findViewById(R.id.edtQty);
            pri = veve.findViewById(R.id.edtPrice);
            pri.setText(suppliedMode.getPrice());
            pri.setEnabled(false);
            imageView = veve.findViewById(R.id.imager);
            des.setText(suppliedMode.getDescription());
            des.setEnabled(false);
            qty.setText(suppliedMode.getQuantity());
            qty.setEnabled(false);
            Glide.with(this).load(suppliedMode.getImage()).into(imageView);
            builder.setView(veve);
            builder.setTitle(suppliedMode.getCategory());
            builder.setMessage("ID: " + suppliedMode.getSupplier() + "\n" + suppliedMode.getType() + "\nSupplied: " + suppliedMode.getQuantity() + "\nPrice: KES" + suppliedMode.getPrice() + "\nStatus: " + suppliedMode.getStatus() + "\nDate: " + suppliedMode.getReg_date());
            builder.setNegativeButton("Close", (v1, ve) -> {
            });
            alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.origi);
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(viewe -> {
                alertDialog.cancel();
            });
        });
    }

    private void getProd() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Manage.oldS,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String sup_id = jsonObject.getString("sup_id");
                                String pur_id = jsonObject.getString("pur_id");
                                String category = jsonObject.getString("category");
                                String type = jsonObject.getString("type");
                                String quantity = jsonObject.getString("quantity");
                                String price = jsonObject.getString("price");
                                String description = jsonObject.getString("description");
                                String image = jsonObject.getString("image");
                                String imagery = Manage.img + image;
                                String supplier = jsonObject.getString("supplier");
                                String status = jsonObject.getString("status");
                                String disburse = jsonObject.getString("disburse");
                                String reg_date = jsonObject.getString("reg_date");
                                suppliedMode = new SuppliedMode(sup_id, pur_id, category, type, quantity, price, description, imagery, supplier, status, disburse, reg_date);
                                suppliedModeArrayList.add(suppliedMode);
                            }
                            suppliedAda = new SuppliedAda(PastSup.this, R.layout.gatemene, suppliedModeArrayList);
                            listView.setAdapter(suppliedAda);
                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String text) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    suppliedAda.getFilter().filter(newText);
                                    return false;
                                }
                            });
                        } else if (success == 0) {
                            String msg = jsonObject.getString("mine");
                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();

        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}