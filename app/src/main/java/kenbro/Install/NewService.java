package com.example.kenbro.Install;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.print.PrintManager;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
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
import android.widget.RelativeLayout;
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
import com.bumptech.glide.Glide;
import com.example.kenbro.Home.Anologies;
import com.example.kenbro.Home.Manage;
import com.example.kenbro.Home.ServAda;
import com.example.kenbro.Home.ServMode;
import com.example.kenbro.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NewService extends AppCompatActivity {
    Button dater;
    TextView cate, siz, desc;
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
        Objects.requireNonNull(getSupportActionBar()).setTitle("New Service");
        setContentView(R.layout.activity_new_service);
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
            builder.setMessage("Customer: " + servMode.getFullname() + "\n" + servMode.getPhone() + "\nSite: " + servMode.getLocation() + "-" + servMode.getLandmark() + "-" + servMode.getHouse() + "\nRequestDate: " + servMode.getReg_date() + "\nStatus: " + servMode.getStatus());
            builder.setTitle("Requested Service");
            builder.setNegativeButton("Close", (v1, ve) -> {
            });
            builder.setPositiveButton("Verify", (v1, ve) -> {
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
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setPositiveButton("Approve", (dr, e) -> {
                });
                alert.setNegativeButton("Close", (dr, e) -> {
                });
                alert.setNeutralButton("Decline", (dr, e) -> {
                });
                AlertDialog dede = alert.create();
                dede.show();
                dede.setCancelable(false);
                dede.setCanceledOnTouchOutside(false);
                dede.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                dede.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view1 -> {
                    dede.cancel();
                });
                dede.getButton(DialogInterface.BUTTON_NEUTRAL).setOnClickListener(view1 -> {
                    Toast.makeText(this, Html.fromHtml("<font color='green'>Please long press the Decline Button</font>"), Toast.LENGTH_SHORT).show();
                });
                dede.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view1 -> {
                    Toast.makeText(this, Html.fromHtml("<font color='green'>Please long press the Approve Button</font>"), Toast.LENGTH_SHORT).show();
                });
                dede.getButton(DialogInterface.BUTTON_NEUTRAL).setOnLongClickListener(view1 -> {
                    AlertDialog.Builder maze = new AlertDialog.Builder(this);
                    maze.setTitle("Confirm!");
                    maze.setMessage("Do you want to Decline the Service Request?");
                    maze.setPositiveButton("Yes_Decline", (yt, t) -> {
                    });
                    maze.setNegativeButton("Back", (yt, t) -> {
                    });
                    AlertDialog key = maze.create();
                    key.show();
                    key.setCancelable(false);
                    key.setCanceledOnTouchOutside(false);
                    key.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                    key.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view2 -> {
                        key.cancel();
                    });
                    key.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view2 -> {
                        Toast.makeText(this, Html.fromHtml("<font color='green'>Please click the Yes_Decline button</font>"), Toast.LENGTH_SHORT).show();
                    });
                    key.getButton(DialogInterface.BUTTON_POSITIVE).setOnLongClickListener(view2 -> {
                        requestQueue = Volley.newRequestQueue(getApplicationContext());
                        requestQueue.add(new StringRequest(Request.Method.POST, Manage.updateSe,
                                response -> {
                                    try {
                                        jsonObject = new JSONObject(response);
                                        int st = jsonObject.getInt("success");
                                        String msg = jsonObject.getString("message");
                                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                        if (st == 1) {
                                            startActivity(new Intent(getApplicationContext(), NewService.class));
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
                            Toast.makeText(this, "connection error", Toast.LENGTH_SHORT).show();
                        }) {
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> pare = new HashMap<>();
                                pare.put("ser_id", servMode.getSer_id());
                                pare.put("cust_id", servMode.getCust_id());
                                pare.put("status", "2");
                                pare.put("price", "0");
                                pare.put("date", new SimpleDateFormat("dd-MM-yyyy hh:mm a").format(new Date()));
                                return pare;
                            }
                        });
                        return true;
                    });
                    return true;
                });
                dede.getButton(DialogInterface.BUTTON_POSITIVE).setOnLongClickListener(view1 -> {
                    AlertDialog.Builder maze = new AlertDialog.Builder(this);
                    maze.setTitle("Add Estimated Cost");
                    final EditText editText = new EditText(this);
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                    editText.setHint("Enter cost");
                    frameLayout = new FrameLayout(this);
                    params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.leftMargin = getResources().getDimensionPixelSize(R.dimen.richer);
                    params.rightMargin = getResources().getDimensionPixelSize(R.dimen.richer);
                    editText.setLayoutParams(params);
                    frameLayout.addView(editText);
                    maze.setView(frameLayout);
                    maze.setPositiveButton("Submit", (yoo, t) -> {
                    });
                    maze.setNegativeButton("Close", (yoo, t) -> {
                    });
                    AlertDialog key = maze.create();
                    key.show();
                    key.setCancelable(false);
                    key.setCanceledOnTouchOutside(false);
                    key.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                    key.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view2 -> {
                        key.cancel();
                    });
                    key.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view2 -> {
                        Toast.makeText(this, Html.fromHtml("<font color='green'>Please click the Submit button</font>"), Toast.LENGTH_SHORT).show();
                    });
                    key.getButton(DialogInterface.BUTTON_POSITIVE).setOnLongClickListener(view2 -> {
                        final String pesa = editText.getText().toString().trim();
                        if (pesa.isEmpty()) {
                            editText.setError("Cost Required");
                            editText.requestFocus();
                        } else if (Float.parseFloat(pesa) == 0) {
                            editText.setError("What are you doing?");
                            editText.requestFocus();
                        } else {
                            requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(new StringRequest(Request.Method.POST, Manage.updateSe,
                                    response -> {
                                        try {
                                            jsonObject = new JSONObject(response);
                                            int st = jsonObject.getInt("success");
                                            String msg = jsonObject.getString("message");
                                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                            if (st == 1) {
                                                startActivity(new Intent(getApplicationContext(), NewService.class));
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
                                Toast.makeText(this, "connection error", Toast.LENGTH_SHORT).show();
                            }) {
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> pare = new HashMap<>();
                                    pare.put("ser_id", servMode.getSer_id());
                                    pare.put("cust_id", servMode.getCust_id());
                                    pare.put("status", "1");
                                    pare.put("price", pesa);
                                    pare.put("date", new SimpleDateFormat("dd-MM-yyyy hh:mm a").format(new Date()));
                                    return pare;
                                }
                            });
                        }
                        return true;
                    });
                    return true;
                });
                return true;
            });
        }));
    }

    private void getServ() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(new StringRequest(Request.Method.POST, Manage.newly,
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
                            servAda = new ServAda(NewService.this, R.layout.acc, servModeArrayList);
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
                startActivity(new Intent(getApplicationContext(), ServiceHist.class));
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}