package com.example.kenbro.Artisan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.print.PrintManager;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.example.kenbro.Home.Anologies;
import com.example.kenbro.Home.ArtisanSession;
import com.example.kenbro.Home.Manage;
import com.example.kenbro.Home.ProjAda;
import com.example.kenbro.Home.ProjMode;
import com.example.kenbro.Home.ServMode;
import com.example.kenbro.Home.StaffModel;
import com.example.kenbro.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NewProj extends AppCompatActivity {
    StaffModel custModel;
    ArtisanSession artisanSession;
    Button dater, complete, camera;
    TextView cate, siz, desc, text;
    AlertDialog.Builder builder, bio, alumini;
    Rect rect;
    Window window;
    LayoutInflater layoutInflater;
    AlertDialog alertDialog, down, meru;
    View veve;
    RequestQueue requestQueue;
    ListView listView;
    SearchView searchView;
    ArrayList<ProjMode> servModeArrayList = new ArrayList<>();
    ProjMode servMode;
    ProjAda servAda;
    ArrayList<ServMode> servModes = new ArrayList<>();
    ServMode mode;
    JSONArray jsonArray;
    JSONObject jsonObject;
    FrameLayout frameLayout;
    FrameLayout.LayoutParams params;
    ImageView added, sited, changer, dated;
    RelativeLayout relativeLayout;
    Bitmap bitmap;
    String encodedimage;
    DatePickerDialog datePickerDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("New Projects");
        setContentView(R.layout.activity_new_proj);
        artisanSession = new ArtisanSession(getApplicationContext());
        custModel = artisanSession.getArtDetails();
        searchView = findViewById(R.id.mySearch);
        listView = findViewById(R.id.listing);
        listView.setTextFilterEnabled(true);
        getServ();
        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            servMode = (ProjMode) adapterView.getItemAtPosition(i);
            builder = new AlertDialog.Builder(this);
            builder.setTitle(servMode.getCategory());
            builder.setMessage("Site: " + servMode.getLocation() + "\nStartDate: " + servMode.getSite_date() + "\nEntryDate: " + servMode.getReg_date() + "\n\nProjectStart: " + servMode.getStatus() + "\nClick Confirm below:");
            builder.setPositiveButton("Confirm", (rt, r) -> {
            });
            builder.setNegativeButton("Close", (rt, r) -> {
            });
            builder.setNeutralButton("More", (rt, r) -> {
            });
            alertDialog = builder.create();
            alertDialog.show();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.origi);
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view1 -> {
                alertDialog.cancel();
            });
            alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL).setOnClickListener(view1 -> {
                requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(new StringRequest(Request.Method.POST, Manage.getDet,
                        response -> {
                            try {
                                jsonObject = new JSONObject(response);
                                int st = jsonObject.getInt("success");
                                if (st == 1) {
                                    jsonArray = jsonObject.getJSONArray("victory");
                                    for (int r = 0; r < jsonArray.length(); r++) {
                                        jsonObject = jsonArray.getJSONObject(r);
                                        mode = new ServMode(jsonObject.getString("ser_id"), jsonObject.getString("category"), jsonObject.getString("checker"),
                                                Manage.img + jsonObject.getString("image"), jsonObject.getString("description"), jsonObject.getString("site_date"),
                                                jsonObject.getString("price"), jsonObject.getString("size"), jsonObject.getString("cust_id"), jsonObject.getString("fullname"),
                                                jsonObject.getString("phone"), jsonObject.getString("location"), jsonObject.getString("landmark"), jsonObject.getString("house"),
                                                jsonObject.getString("reg_date"), jsonObject.getString("status"), jsonObject.getString("pay"), jsonObject.getString("fina"),
                                                jsonObject.getString("insta"), jsonObject.getString("updated"));
                                        servModes.add(mode);
                                    }
                                    bio = new AlertDialog.Builder(this);
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
                                    desc.setText(mode.getDescription());
                                    siz.setText(mode.getSize());
                                    dater.setText(mode.getSite_date());
                                    cate.setText(mode.getCategory());
                                    if (mode.getChecker().equals("1")) {
                                        relativeLayout.setVisibility(View.VISIBLE);
                                        Glide.with(this).load(mode.getImage()).into(added);
                                    } else {
                                        relativeLayout.setVisibility(View.GONE);
                                    }
                                    frameLayout = new FrameLayout(this);
                                    params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    params.leftMargin = getResources().getDimensionPixelSize(R.dimen.richer);
                                    params.rightMargin = getResources().getDimensionPixelSize(R.dimen.richer);
                                    veve.setLayoutParams(params);
                                    frameLayout.addView(veve);
                                    bio.setView(frameLayout);
                                    bio.setMessage("Customer: " + mode.getFullname() + "\n" + mode.getPhone() + "\nSite: " + mode.getLocation() + "-" + mode.getLandmark() + "-" + mode.getHouse() + "\nRequestDate: " + mode.getReg_date());
                                    bio.setTitle("Site Details");
                                    bio.setNegativeButton("Close", (v1, ve) -> {
                                    });
                                    down = bio.create();
                                    down.setCancelable(false);
                                    down.setCanceledOnTouchOutside(false);
                                    down.show();
                                    down.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                                    down.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(viewer -> {
                                        down.cancel();
                                    });
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
                        para.put("ser_id", servMode.getSer_id());
                        return para;
                    }
                });
            });
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view1 -> {
                alumini = new AlertDialog.Builder(this);
                rect = new Rect();
                window = this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rect);
                layoutInflater = (LayoutInflater) (this.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
                veve = layoutInflater.inflate(R.layout.beba, null);
                complete = veve.findViewById(R.id.completeDate);
                camera = veve.findViewById(R.id.btnCame);
                sited = veve.findViewById(R.id.adobe);
                dated = veve.findViewById(R.id.dateChecker);
                text = veve.findViewById(R.id.exa);
                changer = veve.findViewById(R.id.myImg);
                complete.setOnClickListener(this::openStartDate);
                initDatePicker();
                camera.setOnClickListener(vev -> {
                    Dexter.withActivity(this)
                            .withPermission(Manifest.permission.CAMERA)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse response) {
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(intent, 111);
                                }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse response) {

                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken permissionToken) {
                                    permissionToken.continuePermissionRequest();
                                }
                            }).check();
                });
                complete.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        final String myComp = complete.getText().toString().trim();
                        if (myComp.equals("Complete Date")) {
                            dated.setVisibility(View.GONE);
                            text.setVisibility(View.GONE);
                        } else {
                            Date date1 = null;
                            Date date2 = null;
                            try {
                                date1 = new SimpleDateFormat("dd/MM/yyyy").parse(servMode.getSite_date());
                                date2 = new SimpleDateFormat("dd/MM/yyyy").parse(myComp);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            assert date1 != null;
                            assert date2 != null;
                            if (date1.after(date2)) {
                                dated.setImageResource(R.drawable.why);
                                dated.setVisibility(View.VISIBLE);
                                text.setVisibility(View.VISIBLE);
                            } else {
                                dated.setImageResource(R.drawable.correct);
                                dated.setVisibility(View.VISIBLE);
                                text.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });
                frameLayout = new FrameLayout(this);
                params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = getResources().getDimensionPixelSize(R.dimen.richer);
                params.rightMargin = getResources().getDimensionPixelSize(R.dimen.richer);
                veve.setLayoutParams(params);
                frameLayout.addView(veve);
                alumini.setView(frameLayout);
                alumini.setTitle("Update Site Completion");
                alumini.setNegativeButton("Close", (v1, ve) -> {
                });
                alumini.setPositiveButton("Submit", (v1, ve) -> {
                });
                meru = alumini.create();
                meru.setCancelable(false);
                meru.setCanceledOnTouchOutside(false);
                meru.show();
                meru.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                meru.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(viewer -> {
                    meru.cancel();
                });
                meru.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(viewer -> {
                    Toast.makeText(this, Html.fromHtml("<font color='green'>Hi " + custModel.getFname() + ", Long Press Submit Button</font>"), Toast.LENGTH_SHORT).show();
                });
                meru.getButton(DialogInterface.BUTTON_POSITIVE).setOnLongClickListener(viewer -> {
                    final String myComplete = complete.getText().toString().trim();
                    Drawable drawable = sited.getDrawable();
                    if (myComplete.equals("Complete Date")) {
                        Toast.makeText(this, "Completion Date not set", Toast.LENGTH_SHORT).show();
                    } else if (drawable == null) {
                        Toast.makeText(this, "Please Click the camera", Toast.LENGTH_SHORT).show();
                    } else {
                        Date date1 = null;
                        Date date2 = null;
                        try {
                            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(servMode.getSite_date());
                            date2 = new SimpleDateFormat("dd/MM/yyyy").parse(myComplete);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        assert date1 != null;
                        assert date2 != null;
                        if (date1.after(date2)) {
                            Toast.makeText(this, Html.fromHtml("<font color='green'>Site StartDate: " + servMode.getSite_date() + "<br>Your CompletionDate: " + myComplete + "<br>looming date mismatch!!!</font>"), Toast.LENGTH_LONG).show();
                        } else {
                            requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(new StringRequest(Request.Method.POST, Manage.upd,
                                    responses -> {
                                        try {
                                            JSONObject jsonObjec = new JSONObject(responses);
                                            String msg = jsonObjec.getString("message");
                                            int status = jsonObjec.getInt("success");
                                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                            if (status == 1) {
                                                startActivity(new Intent(getApplicationContext(), NewProj.class));
                                                finish();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                                        }
                                    }, error -> {
                                Toast.makeText(this, "There was a connection error", Toast.LENGTH_SHORT).show();
                            }) {
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> para = new HashMap<>();
                                    para.put("reg_id", servMode.getReg_id());
                                    para.put("complete", myComplete);
                                    para.put("status", "1");
                                    para.put("image", encodedimage);
                                    para.put("reg_date", new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date()));
                                    return para;
                                }
                            });
                        }
                    }
                    return true;
                });
            });
            return true;
        });
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Toast.makeText(this, Html.fromHtml("<font color='green'>Hi " + custModel.getFname() + ", Long Press</font>"), Toast.LENGTH_SHORT).show();
        });
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String date = makeDateString(day, month, year);
            complete.setText(date);
        };
        Calendar calendar = Calendar.getInstance();
        Calendar call = Calendar.getInstance();
        Calendar makupa = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONDAY);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int style = android.app.AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        makupa.add(Calendar.DAY_OF_MONTH, +0);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 111 && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            sited.setVisibility(View.VISIBLE);
            changer.setImageResource(R.drawable.correct);
            sited.setImageBitmap(bitmap);
            encodedBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodedBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesofimages = byteArrayOutputStream.toByteArray();
        encodedimage = Base64.encodeToString(bytesofimages, Base64.DEFAULT);
    }

    private void getServ() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(new StringRequest(Request.Method.POST, Manage.artM,
                response -> {
                    try {
                        jsonObject = new JSONObject(response);
                        int st = jsonObject.getInt("success");
                        if (st == 1) {
                            jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                servMode = new ProjMode(jsonObject.getString("reg_id"), jsonObject.getString("ser_id"), jsonObject.getString("category"), jsonObject.getString("location"),
                                        jsonObject.getString("site_date"), jsonObject.getString("cust_id"), jsonObject.getString("art_name"),
                                        jsonObject.getString("art_phone"), jsonObject.getString("art_email"), jsonObject.getString("art_serial"),
                                        jsonObject.getString("status"), jsonObject.getString("complete"), Manage.img + jsonObject.getString("image"),
                                        jsonObject.getString("install"), jsonObject.getString("custom"), jsonObject.getString("reg_date"), jsonObject.getString("updated"));
                                servModeArrayList.add(servMode);
                            }
                            servAda = new ProjAda(NewProj.this, R.layout.acc, servModeArrayList);
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
                            findViewById(R.id.btnPrint).setVisibility(View.VISIBLE);
                            findViewById(R.id.btnPrint).setOnClickListener(view -> {
                                PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
                                printManager.print(getString(R.string.app_name), new Anologies(this, findViewById(R.id.myHeader)), null);
                            });
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
                para.put("status", "0");
                para.put("art_serial", custModel.getSerial_no());
                return para;
            }
        });
    }
}