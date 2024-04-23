package com.example.kenbro.Customer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kenbro.Home.CategoryAda;
import com.example.kenbro.Home.CategoryMode;
import com.example.kenbro.Home.CustModel;
import com.example.kenbro.Home.CustSession;
import com.example.kenbro.Home.Manage;
import com.example.kenbro.MainActivity;
import com.example.kenbro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustDash extends AppCompatActivity {
    CustModel custModel;
    CustSession custSession;
    AlertDialog.Builder builder;
    Dialog dialog;
    Rect rect;
    Window window;
    LayoutInflater layoutInflater;
    AlertDialog alertDialog;
    View veve;
    String encodedimage, myTyped;
    RequestQueue requestQueue;
    ListView listView;
    SearchView searchView;
    ArrayList<CategoryMode> SubjectList = new ArrayList<>();
    CategoryAda suppAda;
    CategoryMode productMode;
    Button btn;
    CircleImageView circleImageView;
    CardView serv, ord;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        custSession = new CustSession(getApplicationContext());
        custModel = custSession.getCustDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + custModel.getFname());
        setContentView(R.layout.activity_cust_dash);
        listView = findViewById(R.id.listing);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.mySearch);
        btn = findViewById(R.id.btnAdCart);
        circleImageView = findViewById(R.id.myMess);
        serv = findViewById(R.id.finance);
        ord = findViewById(R.id.installation);
        ord.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), OrderHist.class));
        });
        findViewById(R.id.artisan).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), Delivery.class));
        });
        findViewById(R.id.stock).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), Reports.class));
        });
        serv.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ServiceNew.class));
        });
        circleImageView.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), Messages.class));
        });
        btn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), Cart.class));
        });
        listView.setOnItemClickListener((parent, view, position, id) -> {
            productMode = (CategoryMode) parent.getItemAtPosition(position);
            Intent intent = new Intent(this, Classification.class);
            intent.putExtra("category", productMode.getType());
            startActivity(intent);
        });
        getProd();
    }

    private void getProd() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(new StringRequest(Request.Method.POST, Manage.viewDiv,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String category = jsonObject.getString("category");
                                String image = jsonObject.getString("image");
                                String imagery = Manage.img + image;
                                productMode = new CategoryMode(category, imagery);
                                SubjectList.add(productMode);
                            }
                            suppAda = new CategoryAda(CustDash.this, R.layout.category, SubjectList);
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

        }));
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
                alert.setMessage("Registry: " + custModel.getEntry_no() + "\nFirstname: " + custModel.getFname() + "\nLastname: " + custModel.getLname() + "\nEmail: " + custModel.getEmail() + "\nPhone: " + custModel.getPhone() + "\nResidence: " + custModel.getResidence() + "\nUsername: " + custModel.getUsername() + "\nStatus: " + custModel.getStatus() + "\nDate: " + custModel.getDate());
                alert.setNegativeButton("quit", (dialog, id) -> dialog.cancel());
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                break;
            case R.id.logOut:
                custSession.logoutCust();
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