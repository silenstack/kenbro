package com.example.kenbro.Install;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.print.PrintManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
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
import com.example.kenbro.Home.Manage;
import com.example.kenbro.Home.ServAda;
import com.example.kenbro.Home.ServMode;
import com.example.kenbro.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AssignHist extends AppCompatActivity {
    Button dater;
    TextView cate, siz, desc;
    AlertDialog.Builder builder;
    AlertDialog.Builder bio;
    Rect rect;
    Window window;
    LayoutInflater layoutInflater;
    AlertDialog alertDialog;
    AlertDialog down;
    View veve;
    RequestQueue requestQueue;
    ListView listView;
    SearchView searchView;
    ArrayList<ServMode> servModeArrayList = new ArrayList<>();
    ServMode servMode;
    ServAda servAda;
    JSONArray jsonArray;
    JSONObject jsonObject;
    FrameLayout frameLayout;
    FrameLayout.LayoutParams params;
    ImageView added;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Artisan Attachment History");
        setContentView(R.layout.activity_assign_hist);
        searchView = findViewById(R.id.mySearch);
        listView = findViewById(R.id.listing);
        listView.setTextFilterEnabled(true);
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
            builder.setMessage("Customer: " + servMode.getFullname() + "\n" + servMode.getPhone() + "\nSite: " + servMode.getLocation() + "-" + servMode.getLandmark() + "-" + servMode.getHouse() + "\nRequestDate: " + servMode.getReg_date() + "\nStatus: " + servMode.getStatus() + "\nFinanceStatus: " + servMode.getFina());
            builder.setTitle("Requested Service");
            builder.setNegativeButton("Close", (v1, ve) -> {
            });
            builder.setPositiveButton("Attached_Artisan", (v1, ve) -> {
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
                Toast.makeText(this, Html.fromHtml("<font color='green'>Please long press the Verify Button</font>"), Toast.LENGTH_SHORT).show();
            });
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnLongClickListener(viewer -> {
                requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(new StringRequest(Request.Method.POST, Manage.getRa,
                        respon -> {
                            try {
                                jsonObject = new JSONObject(respon);
                                int st = jsonObject.getInt("success");
                                if (st == 1) {
                                    jsonArray = jsonObject.getJSONArray("victory");
                                    for (int t = 0; t < jsonArray.length(); t++) {
                                        jsonObject = jsonArray.getJSONObject(t);
                                        String serial = jsonObject.getString("serial_no");
                                        String full = jsonObject.getString("fullname");
                                        String email = jsonObject.getString("email");
                                        String phon = jsonObject.getString("phone");
                                        bio = new AlertDialog.Builder(this);
                                        bio.setTitle("Confirmed Artisan");
                                        bio.setMessage("Attached Artisan:-\nSerial: " + serial + "\nName: " + full + "\nPhone: " + phon + "\nEmail: " + email);
                                        bio.setNegativeButton("Close", (dilo, o9) -> {
                                        });
                                        down = bio.create();
                                        down.show();
                                        down.setCanceledOnTouchOutside(false);
                                        down.setCancelable(false);
                                        down.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                                        down.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view12 -> {
                                            down.cancel();
                                        });
                                    }
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
                        para.put("ser_id", servMode.getSer_id());
                        return para;
                    }
                });
                return true;
            });
        }));
    }

    private void getServ() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(new StringRequest(Request.Method.POST, Manage.getPeD,
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
                            }
                                servAda = new ServAda(AssignHist.this, R.layout.acc, servModeArrayList);
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
                para.put("fina", "1");
                para.put("insta", "1");
                return para;
            }
        });
    }
}