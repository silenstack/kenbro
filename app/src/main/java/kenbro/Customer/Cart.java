package com.example.kenbro.Customer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;
import com.example.kenbro.Home.CartAda;
import com.example.kenbro.Home.CartModel;
import com.example.kenbro.Home.CustModel;
import com.example.kenbro.Home.CustSession;
import com.example.kenbro.Home.Manage;
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

public class Cart extends AppCompatActivity {
    RequestQueue requestQueue;
    ListView listView;
    SearchView searchView;
    ArrayList<CartModel> SubjectList = new ArrayList<>();
    CartAda suppAda;
    CartModel productMode;
    AlertDialog.Builder builder;
    Rect rect;
    Window window;
    LayoutInflater layoutInflater;
    AlertDialog alertDialog;
    View veve;
    EditText qty, mpesa, land, house;
    Spinner spinner;
    ImageView imageView;
    Button payer, drop, reduce, add, saved, closer, yoo, can, ship, noship, quit, sub;
    TextView textView;
    CustModel custModel;
    CustSession custSess;
    FrameLayout frameLayout;
    FrameLayout.LayoutParams params;
    int summer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("My Cart");
        setContentView(R.layout.activity_cart);
        listView = findViewById(R.id.listing);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.mySearch);
        yoo = findViewById(R.id.btnMore);
        payer = findViewById(R.id.btnPay);
        yoo.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), CustDash.class));
            finish();
        });
        payer.setOnClickListener(v -> {
            Toast.makeText(this, Html.fromHtml("<font color='green'>Long Press the Pay Button</font>"), Toast.LENGTH_SHORT).show();
        });
        payer.setOnLongClickListener(view -> {
            getPay();
            return true;
        });
        custSess = new CustSession(getApplicationContext());
        custModel = custSess.getCustDetails();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            productMode = (CartModel) parent.getItemAtPosition(position);
            builder = new AlertDialog.Builder(this);
            rect = new Rect();
            window = this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            layoutInflater = (LayoutInflater) (this.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
            veve = layoutInflater.inflate(R.layout.edit_cart, null);
            veve.setMinimumHeight((int) (rect.height() * 0.02));
            veve.setMinimumWidth((int) (rect.width() * 0.9));
            textView = veve.findViewById(R.id.myText);
            imageView = veve.findViewById(R.id.imager);
            reduce = veve.findViewById(R.id.btnReduce);
            closer = veve.findViewById(R.id.btnClose);
            drop = veve.findViewById(R.id.btnDrop);
            add = veve.findViewById(R.id.btnAdd);
            saved = veve.findViewById(R.id.btnSave);
            qty = veve.findViewById(R.id.edtQty);
            textView.setText("Category: " + productMode.getCategory() + "\nTYPE: " + productMode.getType() + "\nQuantityAvailable: " + productMode.getQuantity() + "\nPriceTag: KES" + productMode.getPrice() + "\nQuantity is editable below:");
            Glide.with(this).load(productMode.getImage()).into(imageView);
            frameLayout = new FrameLayout(this);
            params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.rightMargin = getResources().getDimensionPixelSize(R.dimen.richer);
            params.leftMargin = getResources().getDimensionPixelSize(R.dimen.richer);
            veve.setLayoutParams(params);
            frameLayout.addView(veve);
            builder.setView(frameLayout);
            builder.setTitle("Cart Details");
            qty.setText(productMode.getQuantity());
            alertDialog = builder.create();
            closer.setOnClickListener(v1 -> {
                alertDialog.cancel();
            });
            if (qty.getText().toString().equals("1")) {
                reduce.setVisibility(View.GONE);
            } else {
                reduce.setVisibility(View.VISIBLE);
            }
            reduce.setOnClickListener(view1 -> {
                if (Float.parseFloat(qty.getText().toString()) == 1) {
                    Toast.makeText(this, Html.fromHtml("<font color='green'>Hello " + custModel.getFname() + "\nYou have Reached the end</font>"), Toast.LENGTH_SHORT).show();
                    reduce.setOnLongClickListener(view8 -> {
                        Toast.makeText(this, Html.fromHtml("<font color='green'>Hello " + custModel.getFname() + "\nYou have Reached the end</font>"), Toast.LENGTH_SHORT).show();
                        return true;
                    });
                } else {
                    Toast.makeText(this, Html.fromHtml("<font color='green'>Long Press\nReduce Button</font>"), Toast.LENGTH_SHORT).show();
                    reduce.setOnLongClickListener(view8 -> {
                        if (!qty.getText().toString().isEmpty()) {
                            if (qty.getText().toString().equals(productMode.getQuantity())) {
                                summer = Integer.parseInt(productMode.getQuantity());
                            } else {
                                summer = Integer.parseInt(qty.getText().toString());
                            }
                            if (summer <= 1) {
                                summer = 1;
                            } else {
                                summer--;
                            }
                            qty.setText("" + summer);
                        } else {
                            Toast.makeText(this, Html.fromHtml("<font color='#ff0000'>Your quantity is Zero</font>"), Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    });
                }
            });
            add.setOnClickListener(view1 -> {
                Toast.makeText(this, Html.fromHtml("<font color='green'>Long Press\nAdd Button</font>"), Toast.LENGTH_SHORT).show();
            });
            add.setOnLongClickListener(view8 -> {
                if (!qty.getText().toString().isEmpty()) {
                    if (qty.getText().toString().equals(productMode.getQuantity())) {
                        summer = Integer.parseInt(productMode.getQuantity());
                    } else {
                        summer = Integer.parseInt(qty.getText().toString());
                    }
                    summer++;
                    qty.setText("" + summer);
                } else {
                    Toast.makeText(this, Html.fromHtml("<font color='#ff0000'>Your quantity is Zero</font>"), Toast.LENGTH_SHORT).show();
                }
                return true;
            });
            drop.setOnClickListener(v -> {
                AlertDialog.Builder remover = new AlertDialog.Builder(this);
                remover.setTitle("Confirm Delete!");
                remover.setMessage("Hi " + custModel.getFname() + ",\nThis item will be deleted from your cart Permanently!!");
                remover.setPositiveButton("Yes_Proceed", (nn, m) -> {
                });
                remover.setNegativeButton("No_Quit", (nn, m) -> {
                });
                AlertDialog hold = remover.create();
                hold.show();
                hold.setCancelable(false);
                hold.setCanceledOnTouchOutside(false);
                hold.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                hold.getWindow().setGravity(17);
                hold.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                hold.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view1 -> {
                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(new StringRequest(Request.Method.POST, Manage.dropCart,
                            response -> {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    int st = jsonObject.getInt("status");
                                    String msg = jsonObject.getString("message");
                                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                    if (st == 1) {
                                        alertDialog.cancel();
                                        startActivity(new Intent(getApplicationContext(), Cart.class));
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
                            para.put("prod_id", productMode.getProduct());
                            para.put("quantity", productMode.getQuantity());
                            para.put("reg", productMode.getReg());
                            return para;
                        }
                    });
                });
                hold.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view1 -> {
                    hold.cancel();
                });
            });
            saved.setOnClickListener(view1 -> {
                final String myQ = qty.getText().toString().trim();
                if (myQ.isEmpty()) {
                    Toast.makeText(this, Html.fromHtml("<font color='#ff0000'>Quantity Cannot be Empty!!</font>"), Toast.LENGTH_SHORT).show();
                } else if (Float.parseFloat(myQ) == 0) {
                    Toast.makeText(this, Html.fromHtml("<font color='green'>Quantity Cannot be Zero!!!</font>"), Toast.LENGTH_SHORT).show();
                } else if (Float.parseFloat(myQ) == Float.parseFloat(productMode.getQuantity())) {
                    Toast.makeText(this, Html.fromHtml("<font color='blue'>You have edited Nothing so far</font>"), Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder remover = new AlertDialog.Builder(this);
                    remover.setTitle("Please Confirm");
                    remover.setMessage("Hi " + custModel.getFname() + ",\nCategory: " + productMode.getCategory() + "\nType: " + productMode.getType() + "\nPrice: KES" + productMode.getPrice() + "\nNewQuantity: " + myQ + "\n\nTotalCost: KES" + String.format("%.0f", (Float.parseFloat(myQ) * Float.parseFloat(productMode.getPrice()))) + "\nDo you want to Update Cart??");
                    remover.setPositiveButton("Yes_Proceed", (nn, m) -> {
                    });
                    remover.setNegativeButton("No_Quit", (nn, m) -> {
                    });
                    AlertDialog hold = remover.create();
                    hold.show();
                    hold.setCancelable(false);
                    hold.setCanceledOnTouchOutside(false);
                    hold.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                    hold.getWindow().setGravity(17);
                    hold.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    hold.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view6 -> {
                        requestQueue = Volley.newRequestQueue(getApplicationContext());
                        requestQueue.add(new StringRequest(Request.Method.POST, Manage.upya,
                                response -> {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        int stat = jsonObject.getInt("status");
                                        String msg = jsonObject.getString("message");
                                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                        if (stat == 1) {
                                            startActivity(new Intent(getApplicationContext(), Cart.class));
                                            finish();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Toast.makeText(this, "an error occurred", Toast.LENGTH_SHORT).show();
                                    }
                                }, error -> {
                            Toast.makeText(this, "connection not established", Toast.LENGTH_SHORT).show();
                        }) {
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> para = new HashMap<>();
                                para.put("myQ", myQ);
                                para.put("quantity", productMode.getQuantity());
                                para.put("product", productMode.getProduct());
                                para.put("reg", productMode.getReg());
                                return para;
                            }
                        });
                    });
                    hold.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view6 -> {
                        hold.cancel();
                    });
                }
            });
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            alertDialog.getWindow().

                    setBackgroundDrawableResource(R.drawable.origi);
        });
        getProd();
    }

    private void getPay() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Manage.getPay,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String orders = jsonObject.getString("orders");
                                String shipping = jsonObject.getString("shipping");
                                String total = jsonObject.getString("total");
                                builder = new AlertDialog.Builder(this);
                                builder.setTitle("Cart Cost");
                                rect = new Rect();
                                window = this.getWindow();
                                window.getDecorView().getWindowVisibleDisplayFrame(rect);
                                layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                veve = layoutInflater.inflate(R.layout.paying, null);
                                veve.setMinimumHeight((int) (rect.height() * 0.01));
                                veve.setMinimumWidth((int) (rect.width() * 0.9));
                                can = veve.findViewById(R.id.btnCancel);
                                sub = veve.findViewById(R.id.btnUp);
                                textView = veve.findViewById(R.id.myText);
                                textView.setText("Order cost: KES " + orders + "\n\nClick Next to make an Order?");
                                frameLayout = new FrameLayout(this);
                                params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                params.leftMargin = getResources().getDimensionPixelSize(R.dimen.richer);
                                params.rightMargin = getResources().getDimensionPixelSize(R.dimen.richer);
                                veve.setLayoutParams(params);
                                frameLayout.addView(veve);
                                builder.setView(frameLayout);
                                alertDialog = builder.create();
                                can.setOnClickListener(v -> {
                                    alertDialog.cancel();
                                });
                                sub.setOnClickListener(view -> {
                                    Toast.makeText(this, Html.fromHtml("<font color='green'>Long the Next Button</font>"), Toast.LENGTH_SHORT).show();
                                });
                                sub.setOnLongClickListener(view -> {
                                    AlertDialog.Builder paid = new AlertDialog.Builder(this);
                                    paid.setTitle("How do you want to Order?");
                                    rect = new Rect();
                                    window = this.getWindow();
                                    window.getDecorView().getWindowVisibleDisplayFrame(rect);
                                    layoutInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
                                    View ramper = layoutInflater.inflate(R.layout.payer, null);
                                    ramper.setMinimumHeight((int) (rect.height() * 0.01));
                                    ramper.setMinimumWidth((int) (rect.width() * 0.99));
                                    ship = ramper.findViewById(R.id.btnShip);
                                    noship = ramper.findViewById(R.id.btnNoShip);
                                    quit = ramper.findViewById(R.id.btnQuit);
                                    frameLayout = new FrameLayout(this);
                                    params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    params.rightMargin = getResources().getDimensionPixelSize(R.dimen.richer);
                                    params.leftMargin = getResources().getDimensionPixelSize(R.dimen.richer);
                                    ramper.setLayoutParams(params);
                                    frameLayout.addView(ramper);
                                    paid.setView(frameLayout);
                                    AlertDialog junior = paid.create();
                                    quit.setOnClickListener(view1 -> {
                                        junior.cancel();
                                    });
                                    noship.setOnClickListener(view1 -> {
                                        AlertDialog.Builder expen = new AlertDialog.Builder(this);
                                        expen.setTitle("No Shipping");
                                        expen.setMessage("Hi " + custModel.getFname() + ",\nPay Amount KES" + orders + "\nMPESA PayBill\n542542\nAccount\n5801217\nEnter the MPESA code in the next screen");
                                        expen.setPositiveButton("Next", (tr, r) -> {
                                        });
                                        expen.setNegativeButton("Close", (tr, r) -> {
                                        });
                                        AlertDialog mous = expen.create();
                                        mous.show();
                                        mous.setCancelable(false);
                                        mous.setCanceledOnTouchOutside(false);
                                        mous.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                                        mous.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view2 -> {
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
                                                    requestQueue.add(new StringRequest(Request.Method.POST, Manage.additive,
                                                            response1 -> {
                                                                try {
                                                                    JSONObject json = new JSONObject(response1);
                                                                    int st = json.getInt("success");
                                                                    String msg = json.getString("message");
                                                                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                                                    if (st == 1) {
                                                                        startActivity(new Intent(getApplicationContext(), Cart.class));
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
                                                            para.put("amount", orders);
                                                            para.put("orders", orders);
                                                            para.put("ship", "0");
                                                            para.put("cust_id", custModel.getEntry_no());
                                                            para.put("name", custModel.getFname() + " " + custModel.getLname());
                                                            para.put("phone", custModel.getPhone());
                                                            para.put("location", "");
                                                            para.put("landmark", "");
                                                            para.put("house", "");
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
                                        mous.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view2 -> {
                                            mous.cancel();
                                        });
                                    });
                                    ship.setOnClickListener(view1 -> {
                                        AlertDialog.Builder expen = new AlertDialog.Builder(this);
                                        expen.setTitle("Shipping");
                                        expen.setMessage("Hi " + custModel.getFname() + ",\nOrders KES" + orders + "\nShipping KES" + shipping + "\nTotal KES" + total + "\nTo Proceed first update your current location in the next Screen");
                                        expen.setPositiveButton("Next", (tr, r) -> {
                                        });
                                        expen.setNegativeButton("Close", (tr, r) -> {
                                        });
                                        AlertDialog mous = expen.create();
                                        mous.show();
                                        mous.setCancelable(false);
                                        mous.setCanceledOnTouchOutside(false);
                                        mous.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                                        mous.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view2 -> {
                                            AlertDialog.Builder key = new AlertDialog.Builder(this);
                                            key.setTitle("Current Location");
                                            rect = new Rect();
                                            window = this.getWindow();
                                            window.getDecorView().getWindowVisibleDisplayFrame(rect);
                                            layoutInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
                                            View some = layoutInflater.inflate(R.layout.router, null);
                                            spinner = some.findViewById(R.id.spinRoute);
                                            land = some.findViewById(R.id.edtLand);
                                            house = some.findViewById(R.id.edtHouse);
                                            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Location, android.R.layout.simple_spinner_item);
                                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            spinner.setAdapter(adapter);
                                            String myString = custModel.getResidence();
                                            ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter();
                                            int spinnerPosition = myAdap.getPosition(myString);
                                            spinner.setSelection(spinnerPosition);
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
                                            cool.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view4 -> {
                                                final String myPin = spinner.getSelectedItem().toString().trim();
                                                final String myLand = land.getText().toString().trim();
                                                final String myHouse = house.getText().toString().trim();
                                                if (myPin.equals("Select Location")) {
                                                    Toast.makeText(this, "Please select your current location", Toast.LENGTH_SHORT).show();
                                                } else if (myLand.isEmpty()) {
                                                    land.setError("Landmark cannot be empty");
                                                    land.requestFocus();
                                                } else if (myHouse.isEmpty()) {
                                                    house.setError("House or Additional route");
                                                    house.requestFocus();
                                                } else {
                                                    AlertDialog.Builder bean = new AlertDialog.Builder(this);
                                                    bean.setTitle("Shipping");
                                                    bean.setMessage("Hi " + custModel.getFname() + ",\nOrders KES" + orders + "\nShipping KES" + shipping + "\nPay Amount KES" + total + "\nMPESA PayBill\n542542\nAccount\n5801217\nEnter the MPESA code in the next screen");
                                                    bean.setPositiveButton("Next", (tr, r) -> {
                                                    });
                                                    bean.setNegativeButton("Close", (tr, r) -> {
                                                    });
                                                    AlertDialog adt = bean.create();
                                                    adt.show();
                                                    adt.setCancelable(false);
                                                    adt.setCanceledOnTouchOutside(false);
                                                    adt.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                                                    adt.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view3 -> {
                                                        AlertDialog.Builder open = new AlertDialog.Builder(this);
                                                        open.setTitle("Payment");
                                                        rect = new Rect();
                                                        window = this.getWindow();
                                                        window.getDecorView().getWindowVisibleDisplayFrame(rect);
                                                        layoutInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
                                                        View somer = layoutInflater.inflate(R.layout.mpesa, null);
                                                        mpesa = somer.findViewById(R.id.edtMpesa);
                                                        frameLayout = new FrameLayout(this);
                                                        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                                        params.leftMargin = getResources().getDimensionPixelSize(R.dimen.richer);
                                                        params.rightMargin = getResources().getDimensionPixelSize(R.dimen.richer);
                                                        somer.setLayoutParams(params);
                                                        frameLayout.addView(somer);
                                                        open.setView(frameLayout);
                                                        open.setPositiveButton("Submit", (rt, e) -> {
                                                        });
                                                        open.setNegativeButton("Close", (rt, e) -> {
                                                        });
                                                        AlertDialog rain = open.create();
                                                        rain.show();
                                                        rain.setCancelable(false);
                                                        rain.setCanceledOnTouchOutside(false);
                                                        rain.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                                                        rain.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view6 -> {
                                                            final String Mpe = mpesa.getText().toString().trim();
                                                            if (Mpe.isEmpty()) {
                                                                mpesa.setError("Mpesa Code required");
                                                                mpesa.requestFocus();
                                                            } else if (Mpe.length() < 10) {
                                                                mpesa.setError("Wrong mpesa code");
                                                                mpesa.requestFocus();
                                                            } else {
                                                                requestQueue = Volley.newRequestQueue(getApplicationContext());
                                                                requestQueue.add(new StringRequest(Request.Method.POST, Manage.additive,
                                                                        response1 -> {
                                                                            try {
                                                                                JSONObject json = new JSONObject(response1);
                                                                                int st = json.getInt("success");
                                                                                String msg = json.getString("message");
                                                                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                                                                if (st == 1) {
                                                                                    startActivity(new Intent(getApplicationContext(), Cart.class));
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
                                                                        para.put("amount", total);
                                                                        para.put("orders", orders);
                                                                        para.put("ship", shipping);
                                                                        para.put("cust_id", custModel.getEntry_no());
                                                                        para.put("name", custModel.getFname() + " " + custModel.getLname());
                                                                        para.put("phone", custModel.getPhone());
                                                                        para.put("location", myPin);
                                                                        para.put("landmark", myLand);
                                                                        para.put("house", myHouse);
                                                                        para.put("reg_date", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new Date()));
                                                                        return para;
                                                                    }
                                                                });
                                                            }
                                                        });
                                                        rain.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view6 -> {
                                                            rain.cancel();
                                                        });
                                                    });
                                                    adt.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view3 -> {
                                                        adt.cancel();
                                                    });
                                                }
                                            });
                                            cool.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view4 -> {
                                                cool.cancel();
                                            });
                                        });
                                        mous.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view2 -> {
                                            mous.cancel();
                                        });
                                    });
                                    junior.show();
                                    junior.setCancelable(false);
                                    junior.setCanceledOnTouchOutside(false);
                                    junior.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                                    return true;
                                });
                                alertDialog.setCancelable(false);
                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.show();
                                alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                                alertDialog.getWindow().setGravity(Gravity.CENTER);
                            }
                        } else if (success == 0) {
                            String msg = jsonObject.getString("mine");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> {


        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("cust_id", custModel.getEntry_no());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getProd() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(new StringRequest(Request.Method.POST, Manage.myCart,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
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
                            payer.setVisibility(View.VISIBLE);
                            suppAda = new CartAda(Cart.this, R.layout.category, SubjectList);
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
                para.put("cust_id", custModel.getEntry_no());
                return para;
            }
        });
    }
}