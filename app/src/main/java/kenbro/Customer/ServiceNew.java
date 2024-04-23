package com.example.kenbro.Customer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.kenbro.Home.CustModel;
import com.example.kenbro.Home.CustSession;
import com.example.kenbro.Home.Manage;
import com.example.kenbro.Home.OnlyOne;
import com.example.kenbro.Home.ServAda;
import com.example.kenbro.Home.ServMode;
import com.example.kenbro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ServiceNew extends AppCompatActivity {
    Button btn, dater, payer;
    CustModel custModel;
    CustSession custSession;
    Spinner divi, sizer, spinner;
    TextView cate, siz, desc;
    EditText description, land, house, mpesa;
    AlertDialog.Builder builder;
    Rect rect;
    Window window;
    LayoutInflater layoutInflater;
    AlertDialog alertDialog;
    View veve;
    RequestQueue requestQueue;
    ListView listView;
    SearchView searchView;
    ArrayList<ServMode> servModeArrayList = new ArrayList<>();
    ServMode servMode;
    ServAda servAda;
    ArrayList<OnlyOne> onlyOneArrayList = new ArrayList<>();
    OnlyOne onlyOne;
    String encodedimage;
    JSONArray jsonArray;
    JSONObject jsonObject;
    FrameLayout frameLayout;
    FrameLayout.LayoutParams params;
    CheckBox checkBox;
    ImageView added;
    Bitmap bitmap;
    DatePickerDialog datePickerDialog;
    RelativeLayout relativeLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Order Service");
        setContentView(R.layout.activity_service_new);
        custSession = new CustSession(getApplicationContext());
        custModel = custSession.getCustDetails();
        btn = findViewById(R.id.btnServ);
        findViewById(R.id.btnMore).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), FinishedProj.class));
        });
        findViewById(R.id.btnRec).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ServRece.class));
        });
        btn.setOnClickListener(view -> {
            builder = new AlertDialog.Builder(this);
            rect = new Rect();
            window = this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            layoutInflater = (LayoutInflater) (this.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
            veve = layoutInflater.inflate(R.layout.serv_re, null);
            veve.setMinimumHeight((int) (rect.height() * 0.02));
            veve.setMinimumWidth((int) (rect.width() * 0.9));
            divi = veve.findViewById(R.id.spindiv);
            checkBox = veve.findViewById(R.id.myChekTwo);
            description = veve.findViewById(R.id.editDesc);
            sizer = veve.findViewById(R.id.spinSize);
            added = veve.findViewById(R.id.addImage);
            dater = veve.findViewById(R.id.startDate);
            dater.setAnimation(AnimationUtils.loadAnimation(ServiceNew.this, R.anim.animated_blink));
            divi.setAnimation(AnimationUtils.loadAnimation(ServiceNew.this, R.anim.animated_blink));
            sizer.setAnimation(AnimationUtils.loadAnimation(ServiceNew.this, R.anim.animated_blink));
            description.setAnimation(AnimationUtils.loadAnimation(ServiceNew.this, R.anim.animated_blink));
            description.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String myDes = description.getText().toString().trim();
                    if (myDes.isEmpty()) {
                        description.setAnimation(AnimationUtils.loadAnimation(ServiceNew.this, R.anim.animated_blink));
                    } else {
                        description.clearAnimation();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
            dater.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    dater.clearAnimation();
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
            divi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String myDiv = divi.getSelectedItem().toString().trim();
                    if (myDiv.equals("Select Service")) {
                        divi.setAnimation(AnimationUtils.loadAnimation(ServiceNew.this, R.anim.animated_blink));
                    } else {
                        divi.clearAnimation();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            sizer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String myDiv = sizer.getSelectedItem().toString().trim();
                    if (myDiv.equals("Select Estimate")) {
                        sizer.setAnimation(AnimationUtils.loadAnimation(ServiceNew.this, R.anim.animated_blink));
                    } else {
                        sizer.clearAnimation();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            dater.setOnClickListener(this::openStartDate);
            initDatePicker();
            ArrayAdapter<CharSequence> ad = ArrayAdapter.createFromResource(this, R.array.Size, android.R.layout.simple_spinner_item);
            ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sizer.setAdapter(ad);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Service, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            divi.setAdapter(adapter);
            checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
                if (b) {
                    added.setVisibility(View.VISIBLE);
                } else {
                    added.setVisibility(View.GONE);
                    added.setImageBitmap(null);
                }
            });
            added.setOnClickListener(view1 -> {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        111
                );
            });
            frameLayout = new FrameLayout(this);
            params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = getResources().getDimensionPixelSize(R.dimen.richer);
            params.rightMargin = getResources().getDimensionPixelSize(R.dimen.richer);
            veve.setLayoutParams(params);
            frameLayout.addView(veve);
            builder.setView(frameLayout);
            builder.setTitle("Send Service");
            builder.setNegativeButton("Close", (v1, ve) -> {
            });
            builder.setPositiveButton(Html.fromHtml("<font color='#1E8103'>Next</font>"), (v1, ve) -> {
            });
            alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.origi);
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(viewer -> {
                alertDialog.cancel();
            });
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view1 -> {
                Toast.makeText(this, Html.fromHtml("<font color='green'>Hi " + custModel.getFname() + ", please long press the Next button</font>"), Toast.LENGTH_SHORT).show();
            });
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnLongClickListener(viewer -> {
                final String myDiv = divi.getSelectedItem().toString().trim();
                final String mySize = sizer.getSelectedItem().toString().trim();
                final String myDesc = description.getText().toString().trim();
                final String myDate = dater.getText().toString().trim();
                if (myDiv.equals("Select Service")) {
                    Toast.makeText(this, "Please Select Service", Toast.LENGTH_SHORT).show();
                } else if (myDesc.isEmpty()) {
                    description.setError("Add some description");
                    description.requestFocus();
                } else if (mySize.equals("Select Estimate")) {
                    Toast.makeText(this, "Please Select Estimate", Toast.LENGTH_SHORT).show();
                } else if (myDate.equals("Estimate Date")) {
                    Toast.makeText(this, Html.fromHtml("<font color='green'>Hi " + custModel.getFname() + ", Click Estimate Date"), Toast.LENGTH_SHORT).show();
                } else {
                    if (checkBox.isChecked()) {
                        Drawable drawable = added.getDrawable();
                        if (drawable == null) {
                            Toast.makeText(this, "If You choose to include image, Please add one", Toast.LENGTH_SHORT).show();
                        } else {
                            AlertDialog.Builder key = new AlertDialog.Builder(this);
                            key.setTitle("Site Location");
                            key.setMessage("Hi " + custModel.getFname() + ",\nPlease give directions to your site.");
                            rect = new Rect();
                            window = this.getWindow();
                            window.getDecorView().getWindowVisibleDisplayFrame(rect);
                            layoutInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
                            View some = layoutInflater.inflate(R.layout.router, null);
                            spinner = some.findViewById(R.id.spinRoute);
                            land = some.findViewById(R.id.edtLand);
                            house = some.findViewById(R.id.edtHouse);
                            ArrayAdapter<CharSequence> adept = ArrayAdapter.createFromResource(this, R.array.Location, android.R.layout.simple_spinner_item);
                            adept.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adept);
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
                            cool.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view1 -> {
                                Toast.makeText(this, Html.fromHtml("<font color='green'>Hi " + custModel.getFname() + ", please long press the submit button</font>"), Toast.LENGTH_SHORT).show();
                            });
                            cool.getButton(DialogInterface.BUTTON_POSITIVE).setOnLongClickListener(view1 -> {
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
                                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                                    requestQueue.add(new StringRequest(Request.Method.POST, Manage.gloom,
                                            response -> {
                                                try {
                                                    JSONObject jsonObject = new JSONObject(response);
                                                    int st = jsonObject.getInt("success");
                                                    String msg = jsonObject.getString("message");
                                                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                                    if (st == 1) {
                                                        alertDialog.dismiss();
                                                        startActivity(new Intent(getApplicationContext(), ServiceNew.class));
                                                        finish();
                                                    } else if (st == 5) {
                                                        AlertDialog.Builder nos = new AlertDialog.Builder(this);
                                                        nos.setTitle("Failed!!!");
                                                        nos.setMessage(msg);
                                                        nos.show();
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
                                            para.put("size", mySize);
                                            para.put("description", myDesc);
                                            para.put("category", myDiv);
                                            para.put("image", encodedimage);
                                            para.put("cust_id", custModel.getEntry_no());
                                            para.put("fullname", custModel.getFname() + " " + custModel.getLname());
                                            para.put("phone", custModel.getPhone());
                                            para.put("checked", "yes");
                                            para.put("location", myPin);
                                            para.put("landmark", myLand);
                                            para.put("house", myHouse);
                                            para.put("site_date", myDate);
                                            para.put("reg_date", new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date()));
                                            return para;
                                        }
                                    });
                                }
                                return true;
                            });
                            cool.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view1 -> {
                                cool.cancel();
                            });
                        }
                    } else {
                        AlertDialog.Builder key = new AlertDialog.Builder(this);
                        key.setTitle("Site Location");
                        key.setMessage("Hi " + custModel.getFname() + ",\nPlease give directions to your site.");
                        rect = new Rect();
                        window = this.getWindow();
                        window.getDecorView().getWindowVisibleDisplayFrame(rect);
                        layoutInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
                        View some = layoutInflater.inflate(R.layout.router, null);
                        spinner = some.findViewById(R.id.spinRoute);
                        land = some.findViewById(R.id.edtLand);
                        house = some.findViewById(R.id.edtHouse);
                        ArrayAdapter<CharSequence> adept = ArrayAdapter.createFromResource(this, R.array.Location, android.R.layout.simple_spinner_item);
                        adept.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adept);
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
                        cool.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view1 -> {
                            Toast.makeText(this, Html.fromHtml("<font color='green'>Hi " + custModel.getFname() + ", please long press the submit button</font>"), Toast.LENGTH_SHORT).show();
                        });
                        cool.getButton(DialogInterface.BUTTON_POSITIVE).setOnLongClickListener(view1 -> {
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
                                requestQueue = Volley.newRequestQueue(getApplicationContext());
                                requestQueue.add(new StringRequest(Request.Method.POST, Manage.gloom,
                                        response -> {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                int st = jsonObject.getInt("success");
                                                String msg = jsonObject.getString("message");
                                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                                if (st == 1) {
                                                    alertDialog.dismiss();
                                                    startActivity(new Intent(getApplicationContext(), ServiceNew.class));
                                                    finish();
                                                } else if (st == 5) {
                                                    AlertDialog.Builder nos = new AlertDialog.Builder(this);
                                                    nos.setTitle("Failed!!!");
                                                    nos.setMessage(msg);
                                                    nos.show();
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
                                        para.put("size", mySize);
                                        para.put("description", myDesc);
                                        para.put("category", myDiv);
                                        para.put("image", "");
                                        para.put("cust_id", custModel.getEntry_no());
                                        para.put("fullname", custModel.getFname() + " " + custModel.getLname());
                                        para.put("phone", custModel.getPhone());
                                        para.put("checked", "no");
                                        para.put("location", myPin);
                                        para.put("landmark", myLand);
                                        para.put("house", myHouse);
                                        para.put("site_date", myDate);
                                        para.put("reg_date", new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date()));
                                        return para;
                                    }
                                });
                            }
                            return true;
                        });
                        cool.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view1 -> {
                            cool.cancel();
                        });
                    }
                }
                return true;
            });
        });
        searchView = findViewById(R.id.mySearch);
        listView = findViewById(R.id.listing);
        listView.setTextFilterEnabled(true);
        payer = findViewById(R.id.btnPay);
        payer.setOnClickListener(view -> {
            Toast.makeText(this, Html.fromHtml("<font color='green'>Hi " + custModel.getFname() + ", Please long press the Pay button</font>"), Toast.LENGTH_SHORT).show();
        });
        payer.setOnLongClickListener(view -> {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(new StringRequest(Request.Method.POST, Manage.getRe,
                    response -> {
                        try {
                            jsonObject = new JSONObject(response);
                            int st = jsonObject.getInt("success");
                            if (st == 1) {
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
                                    builder = new AlertDialog.Builder(this);
                                    builder.setTitle(onlyOne.getFullname() + " Payment Form");
                                    builder.setMessage("Service: " + onlyOne.getCategory() + "\nDescription: " + onlyOne.getDescription() + "\nExpectedPay: KES" + onlyOne.getPrice() + "\nMPESA PayBill\n542542\nAccount\n5801217\nEnter the MPESA code in the next screen");
                                    builder.setPositiveButton("Next", (tr, r) -> {
                                    });
                                    builder.setNegativeButton("Close", (tr, r) -> {
                                    });
                                    alertDialog = builder.create();
                                    alertDialog.show();
                                    alertDialog.setCanceledOnTouchOutside(false);
                                    alertDialog.setCancelable(false);
                                    alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                                    alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view1 -> {
                                        Toast.makeText(this, Html.fromHtml("<font color='green'>Hi " + custModel.getFname() + ", Long press the Next button<font>"), Toast.LENGTH_SHORT).show();
                                    });
                                    alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnLongClickListener(view1 -> {
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
                                            Toast.makeText(this, Html.fromHtml("<font color='green'>Long Press the Submit Button</font>"), Toast.LENGTH_SHORT).show();
                                        });
                                        cool.getButton(DialogInterface.BUTTON_POSITIVE).setOnLongClickListener(view3 -> {
                                            final String Mpe = mpesa.getText().toString().trim();
                                            if (Mpe.isEmpty()) {
                                                mpesa.setError("Mpesa Code required");
                                                mpesa.requestFocus();
                                            } else if (Mpe.length() < 10) {
                                                mpesa.setError("Wrong mpesa code");
                                                mpesa.requestFocus();
                                            } else {
                                                requestQueue = Volley.newRequestQueue(getApplicationContext());
                                                requestQueue.add(new StringRequest(Request.Method.POST, Manage.wiMwega,
                                                        response1 -> {
                                                            try {
                                                                JSONObject json = new JSONObject(response1);
                                                                int sts = json.getInt("success");
                                                                String msg = json.getString("message");
                                                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                                                if (sts == 1) {
                                                                    startActivity(new Intent(getApplicationContext(), ServiceNew.class));
                                                                    finish();
                                                                } else if (sts == 10) {
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
                                                        para.put("amount", onlyOne.getPrice());
                                                        para.put("ser_id", onlyOne.getSer_id());
                                                        para.put("cust_id", custModel.getEntry_no());
                                                        para.put("name", custModel.getFname() + " " + custModel.getLname());
                                                        para.put("phone", custModel.getPhone());
                                                        para.put("location", onlyOne.getLocation());
                                                        para.put("landmark", onlyOne.getLandmark());
                                                        para.put("house", onlyOne.getHouse());
                                                        para.put("reg_date", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new Date()));
                                                        return para;
                                                    }
                                                });
                                            }
                                            return true;
                                        });
                                        cool.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view3 -> {
                                            cool.cancel();
                                        });
                                        return true;
                                    });
                                    alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view1 -> {
                                        alertDialog.cancel();
                                    });
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }, error -> {
                Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show();
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> para = new HashMap<>();
                    para.put("cust_id", custModel.getEntry_no());
                    return para;
                }
            });
            return true;
        });
        testAva();
        getServ();
        listView.setOnItemClickListener(((adapterView, view, i, l) -> {
            servMode = (ServMode) adapterView.getItemAtPosition(i);
            builder = new AlertDialog.Builder(this);
            rect = new Rect();
            window = this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            layoutInflater = (LayoutInflater) (this.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
            veve = layoutInflater.inflate(R.layout.sited, null);
            veve.setMinimumHeight((int) (rect.height() * 0.02));
            veve.setMinimumWidth((int) (rect.width() * 0.9));
            cate = veve.findViewById(R.id.txtServ);
            desc = veve.findViewById(R.id.txtDesc);
            siz = veve.findViewById(R.id.txtSize);
            added = veve.findViewById(R.id.addImage);
            dater = veve.findViewById(R.id.startDate);
            relativeLayout = veve.findViewById(R.id.myRel);
            desc.setText(servMode.getDescription());
            siz.setText(servMode.getSize());
            dater.setText(servMode.getSite_date());
            cate.setText(servMode.getCategory());
            if (servMode.getChecker().equals("1")) {
                relativeLayout.setVisibility(View.VISIBLE);
                Glide.with(this).load(servMode.getImage()).into(added);
            } else {
                relativeLayout.setVisibility(View.GONE);
            }
            frameLayout = new FrameLayout(this);
            params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = getResources().getDimensionPixelSize(R.dimen.richer);
            params.rightMargin = getResources().getDimensionPixelSize(R.dimen.richer);
            veve.setLayoutParams(params);
            frameLayout.addView(veve);
            builder.setView(frameLayout);
            if (Float.parseFloat(servMode.getPrice()) == 0)
                builder.setMessage("Customer: " + servMode.getFullname() + "\n" + servMode.getPhone() + "\nSite: " + servMode.getLocation() + "-" + servMode.getLandmark() + "-" + servMode.getHouse() + "\nRequestDate: " + servMode.getReg_date() + "\nStatus: " + servMode.getStatus());
            else
                builder.setMessage("Customer: " + servMode.getFullname() + "\n" + servMode.getPhone() + "\nSite: " + servMode.getLocation() + "-" + servMode.getLandmark() + "-" + servMode.getHouse() + "\nRequestDate: " + servMode.getReg_date() + "\nStatus: " + servMode.getStatus() + "\nEstimatedCost: KES" + servMode.getPrice());
            builder.setTitle("Requested Service");
            builder.setNegativeButton("Close", (v1, ve) -> {
            });
            alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.origi);
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(viewer -> {
                alertDialog.cancel();
            });
        }));
    }

    private void testAva() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(new StringRequest(Request.Method.POST, Manage.helpC,
                response -> {
                    try {
                        jsonObject = new JSONObject(response);
                        int st = jsonObject.getInt("success");
                        if (st == 1) {
                            payer.setVisibility(View.VISIBLE);
                        } else {
                            payer.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show();
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> para = new HashMap<>();
                para.put("cust_id", custModel.getEntry_no());
                return para;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.hist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Hist:
                startActivity(new Intent(getApplicationContext(), Services.class));
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void getServ() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(new StringRequest(Request.Method.POST, Manage.getSer,
                response -> {
                    try {
                        jsonObject = new JSONObject(response);
                        int st = jsonObject.getInt("success");
                        if (st == 1) {
                            jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                servMode = new ServMode(jsonObject.getString("ser_id"), jsonObject.getString("category"), jsonObject.getString("checker"),
                                        Manage.img + jsonObject.getString("image"), jsonObject.getString("description"), jsonObject.getString("site_date"),
                                        jsonObject.getString("price"), jsonObject.getString("size"), jsonObject.getString("cust_id"), jsonObject.getString("fullname"),
                                        jsonObject.getString("phone"), jsonObject.getString("location"), jsonObject.getString("landmark"), jsonObject.getString("house"),
                                        jsonObject.getString("reg_date"), jsonObject.getString("status"), jsonObject.getString("pay"), jsonObject.getString("fina"),
                                        jsonObject.getString("insta"), jsonObject.getString("updated"));
                                servModeArrayList.add(servMode);
                                servAda = new ServAda(ServiceNew.this, R.layout.acc, servModeArrayList);
                                listView.setAdapter(servAda);
                                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                    @Override
                                    public boolean onQueryTextSubmit(String s) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onQueryTextChange(String s) {
                                        servAda.getFilter().filter(s);
                                        return false;
                                    }
                                });
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show();
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

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String date = makeDateString(day, month, year);
            dater.setText(date);
        };
        Calendar calendar = Calendar.getInstance();
        Calendar call = Calendar.getInstance();
        Calendar makupa = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONDAY);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int style = android.app.AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        makupa.add(Calendar.DAY_OF_MONTH, +2);
        call.add(Calendar.MONTH, +11);
        datePickerDialog.getDatePicker().setMaxDate(call.getTimeInMillis());
        datePickerDialog.getDatePicker().setMinDate(makupa.getTimeInMillis());
    }

    private String makeDateString(int day, int month, int year) {
        return day + "/" + getMonthFormat(month) + "/" + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1) {
            return "01";
        }
        if (month == 2) {
            return "02";
        }
        if (month == 3) {
            return "03";
        }
        if (month == 4) {
            return "04";
        }
        if (month == 5) {
            return "05";
        }
        if (month == 6) {
            return "06";
        }
        if (month == 7) {
            return "07";
        }
        if (month == 8) {
            return "08";
        }
        if (month == 9) {
            return "09";
        }
        if (month == 10) {
            return "10";
        }
        if (month == 11) {
            return "11";
        }
        if (month == 12) {
            return "12";
        }
        return "01";
    }

    public void openStartDate(View view) {
        datePickerDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 111) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 111);
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 111 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                added.setImageBitmap(bitmap);
                encodedBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodedBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesofimages = byteArrayOutputStream.toByteArray();
        encodedimage = Base64.encodeToString(bytesofimages, Base64.DEFAULT);
    }
}