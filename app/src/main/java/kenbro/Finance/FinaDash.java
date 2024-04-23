package com.example.kenbro.Finance;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.print.PrintManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kenbro.Home.Anologies;
import com.example.kenbro.Home.FinaSession;
import com.example.kenbro.Home.Manage;
import com.example.kenbro.Home.Monie;
import com.example.kenbro.Home.StaffModel;
import com.example.kenbro.MainActivity;
import com.example.kenbro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class FinaDash extends AppCompatActivity {
    StaffModel custModel;
    FinaSession finaSession;
    CardView ord, serv, supp, acc;
    ArrayList<Monie> monieArrayList = new ArrayList<>();
    Monie monie;
    RequestQueue requestQueue;
    JSONObject jsonObject;
    JSONArray jsonArray;
    TextView textView, timed;
    View layer;
    Rect rect;
    Window window;
    LayoutInflater layoutInflater;
    FrameLayout.LayoutParams params;
    FrameLayout frameLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finaSession = new FinaSession(getApplicationContext());
        custModel = finaSession.getFinaDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + custModel.getFname());
        setContentView(R.layout.activity_fina_dash);
        ord = findViewById(R.id.Orders);
        serv = findViewById(R.id.Serv);
        supp = findViewById(R.id.Suppl);
        acc = findViewById(R.id.Acc);
        ord.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), FinaPend.class));
        });
        serv.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ServicePay.class));
        });
        supp.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), PaySup.class));
        });
        acc.setOnClickListener(view -> {
            requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(new StringRequest(Request.Method.POST, Manage.getMeme,
                    response -> {
                        try {
                            jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("trust");
                            if (success == 1) {
                                jsonArray = jsonObject.getJSONArray("victory");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    monie = new Monie(jsonObject.getString("amount"), jsonObject.getString("orders"), jsonObject.getString("service"),
                                            jsonObject.getString("supplier"), jsonObject.getString("balance"), jsonObject.getString("regdate"),
                                            jsonObject.getString("updated"));
                                    monieArrayList.add(monie);
                                }
                                AlertDialog.Builder build = new AlertDialog.Builder(this);
                                rect = new Rect();
                                window = this.getWindow();
                                window.getDecorView().getWindowVisibleDisplayFrame(rect);
                                layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                layer = layoutInflater.inflate(R.layout.monies, null);
                                layer.setMinimumWidth((int) (rect.width() * 1.0));
                                layer.setMinimumHeight((int) (rect.height() * 0.01));
                                timed = layer.findViewById(R.id.txtHead);
                                timed.setText(Html.fromHtml("<font color='#000000'><b><i>Finance Record</i><br>as at <u>" + monie.getUpdated() + "</u></b></font>"));
                                textView = layer.findViewById(R.id.txtAna);
                                textView.setText("Orders: Kes" + monie.getOrders() + "\n\nServices: Kes" + monie.getService() + "\nTOTAL: Kes" + monie.getBalance() + "\n\nPay_To_Supplier: Kes" + monie.getSupplier() + "\n\nBalance: Kes" + monie.getBalance());
                                frameLayout = new FrameLayout(this);
                                params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                params.leftMargin = getResources().getDimensionPixelSize(R.dimen.richer);
                                params.rightMargin = getResources().getDimensionPixelSize(R.dimen.richer);
                                layer.setLayoutParams(params);
                                frameLayout.addView(layer);
                                build.setView(frameLayout);
                                build.setPositiveButton("Print", (tr, r) -> {
                                });
                                build.setNegativeButton("Close", (tr, r) -> {
                                });
                                AlertDialog bon = build.create();
                                bon.show();
                                bon.setCancelable(false);
                                bon.setCanceledOnTouchOutside(false);
                                bon.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                                bon.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view2 -> {
                                    PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
                                    printManager.print(getString(R.string.app_name), new Anologies(this, layer.findViewById(R.id.myHeader)), null);
                                });
                                bon.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view2 -> {
                                    if (!monieArrayList.isEmpty()) {
                                        monieArrayList.clear();
                                    }
                                    bon.cancel();
                                });
                                bon.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("My Profile");
                alert.setMessage("Serial: " + custModel.getSerial_no() + "\nFirstname: " + custModel.getFname() + "\nLastname: " + custModel.getLname() + "\nEmail: " + custModel.getEmail() + "\nPhone: " + custModel.getPhone() + "\nRole: " + custModel.getRole() + "\nUsername: " + custModel.getUsername() + "\nStatus: " + custModel.getStatus() + "\nDate: " + custModel.getDate());
                alert.setNegativeButton("quit", (dialog, id) -> dialog.cancel());
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                break;
            case R.id.logOut:
                finaSession.logoutFina();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
            case R.id.exit:
                finishAffinity();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}