package com.example.kenbro.Invent;

import android.Manifest;
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
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.kenbro.Home.CategoryAda;
import com.example.kenbro.Home.CategoryMode;
import com.example.kenbro.Home.InventSession;
import com.example.kenbro.Home.Manage;
import com.example.kenbro.Home.ProdAda;
import com.example.kenbro.Home.ProdMode;
import com.example.kenbro.Home.StaffModel;
import com.example.kenbro.MainActivity;
import com.example.kenbro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InventDash extends AppCompatActivity {
    RequestQueue requestQueue;
    ListView listView, lister;
    SearchView searchView, myse;
    ArrayList<CategoryMode> SubjectList = new ArrayList<>();
    CategoryAda suppAda;
    CategoryMode productMode;
    FrameLayout.LayoutParams params;
    FrameLayout frameLayout;
    AlertDialog.Builder builder, alert;
    Rect rect;
    Window window;
    LayoutInflater layoutInflater;
    AlertDialog alertDialog, dialog;
    View veve, jigijigi;
    ArrayList<ProdMode> prodModeArrayList = new ArrayList<>();
    ProdAda prodAda;
    ProdMode prodMode;
    StaffModel custModel;
    InventSession inventSession;
    Spinner divi, type;
    EditText qty, des, pri;
    Button subb;
    String myTyped, encodedimage;
    CardView bid, stoko, supp, sende;
    JSONArray jsonArray;
    JSONObject jsonObject;
    ImageView imageView;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inventSession = new InventSession(getApplicationContext());
        custModel = inventSession.getInventDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + custModel.getFname());
        setContentView(R.layout.activity_invent_dash);
        subb = findViewById(R.id.btnAdd);
        sende = findViewById(R.id.Shipper);
        bid = findViewById(R.id.stock);
        stoko = findViewById(R.id.artisan);
        supp = findViewById(R.id.installation);
        listView = findViewById(R.id.listing);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.mySearch);
        supp.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), Suppliers.class));
        });
        bid.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ManageBid.class));
        });
        stoko.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), InventDash.class));
        });
        sende.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), Messag.class));
        });
        subb.setOnClickListener(v -> {
            builder = new AlertDialog.Builder(this, R.style.Arap);
            rect = new Rect();
            window = this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            layoutInflater = (LayoutInflater) (this.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
            veve = layoutInflater.inflate(R.layout.prody, null);
            veve.setMinimumHeight((int) (rect.height() * 0.02));
            veve.setMinimumWidth((int) (rect.width() * 0.9));
            divi = veve.findViewById(R.id.spindiv);
            type = veve.findViewById(R.id.spinType);
            des = veve.findViewById(R.id.editDesc);
            qty = veve.findViewById(R.id.edtQty);
            pri = veve.findViewById(R.id.edtPrice);
            imageView = veve.findViewById(R.id.imager);
            imageView.setOnClickListener(v1 -> {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        4
                );
            });
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
            builder.setTitle("Upload Product");
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
                Drawable drawable = imageView.getDrawable();
                final String myDesc = des.getText().toString().trim();
                final String myQty = qty.getText().toString().trim();
                final String myPri = pri.getText().toString().trim();
                if (myDiv.equals("Select Category")) {
                    Toast.makeText(this, "Please select Category", Toast.LENGTH_SHORT).show();
                } else {
                    myTyped = type.getSelectedItem().toString().trim();
                    if (myTyped.equals("Select Type")) {
                        Toast.makeText(this, "select product Type", Toast.LENGTH_SHORT).show();
                    } else if (myDesc.isEmpty()) {
                        des.setError("required");
                        des.requestFocus();
                    } else if (myQty.isEmpty()) {
                        qty.setError("required");
                        qty.requestFocus();
                    } else if (Float.parseFloat(myQty) < 1) {
                        qty.setError("required");
                        qty.requestFocus();
                    } else if (myPri.isEmpty()) {
                        pri.setError("required");
                        pri.requestFocus();
                    } else if (Float.parseFloat(myPri) < 10) {
                        pri.setError("required");
                        pri.requestFocus();
                    } else if (drawable == null) {
                        Toast.makeText(this, "Add Resume", Toast.LENGTH_SHORT).show();
                    } else {
                        requestQueue = Volley.newRequestQueue(getApplicationContext());
                        requestQueue.add(new StringRequest(Request.Method.POST, Manage.upload,
                                response -> {
                                    try {
                                        jsonObject = new JSONObject(response);
                                        int st = jsonObject.getInt("success");
                                        String msg = jsonObject.getString("message");
                                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                        if (st == 1) {
                                            alertDialog.dismiss();
                                            startActivity(new Intent(getApplicationContext(), InventDash.class));
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
                                para.put("description", myDesc);
                                para.put("quantity", myQty);
                                para.put("price", myPri);
                                para.put("category", myDiv);
                                para.put("type", myTyped);
                                para.put("image", encodedimage);
                                para.put("date", new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date()));
                                return para;
                            }
                        });
                    }
                }
            });

        });
        listView.setOnItemClickListener((parent, view, position, id) -> {
            productMode = (CategoryMode) parent.getItemAtPosition(position);
            builder = new AlertDialog.Builder(this, R.style.Arap);
            rect = new Rect();
            window = this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            layoutInflater = (LayoutInflater) (this.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
            veve = layoutInflater.inflate(R.layout.activity_view_prod, null);
            veve.setMinimumHeight((int) (rect.height() * 0.02));
            veve.setMinimumWidth((int) (rect.width() * 0.99));
            lister = veve.findViewById(R.id.listing);
            lister.setTextFilterEnabled(true);
            myse = veve.findViewById(R.id.mySearch);
            getMesso();
            lister.setOnItemClickListener((pare, vie, pos, ide) -> {
                prodMode = (ProdMode) pare.getItemAtPosition(pos);
                alert = new AlertDialog.Builder(this, R.style.Arap);
                rect = new Rect();
                window = this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rect);
                layoutInflater = (LayoutInflater) (this.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
                jigijigi = layoutInflater.inflate(R.layout.have_all, null);
                jigijigi.setMinimumHeight((int) (rect.height() * 0.02));
                jigijigi.setMinimumWidth((int) (rect.width() * 0.9));
                des = jigijigi.findViewById(R.id.editDesc);
                qty = jigijigi.findViewById(R.id.edtQty);
                pri = jigijigi.findViewById(R.id.edtPrice);
                imageView = jigijigi.findViewById(R.id.imager);
                des.setText(prodMode.getDescription());
                qty.setText(prodMode.getQuantity());
                pri.setText(prodMode.getPrice());
                pri.setHint("Modify Price for Approval");
                Glide.with(this).load(prodMode.getImage()).into(imageView);
                frameLayout = new FrameLayout(this);
                params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = getResources().getDimensionPixelSize(R.dimen.richer);
                params.rightMargin = getResources().getDimensionPixelSize(R.dimen.richer);
                jigijigi.setLayoutParams(params);
                frameLayout.addView(jigijigi);
                alert.setView(frameLayout);
                alert.setTitle(prodMode.getCategory());
                alert.setMessage("Type: " + prodMode.getType() + "\nDate: " + prodMode.getReg_date() + "\nQuantityOrdered: " + String.format("%.0f", (Float.parseFloat(prodMode.getQty()) - Float.parseFloat(prodMode.getQuantity()))));
                alert.setNegativeButton("Close", (v1, ve) -> {
                });
                alert.setPositiveButton(Html.fromHtml("<font color='#1E8103'>Update</font>"), (v1, ve) -> {
                });
                alert.setNeutralButton(Html.fromHtml("<font color='#ff0000'>Delete</font>"), (v1, ve) -> {
                });
                dialog = alert.create();
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(viewe -> {
                    dialog.cancel();
                });
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(viewe -> {
                    final String myQty = qty.getText().toString().trim();
                    final String myDesc = des.getText().toString().trim();
                    final String myPri = pri.getText().toString().trim();
                    if (myDesc.isEmpty()) {
                        des.setError("required");
                        des.requestFocus();
                    } else if (myQty.isEmpty()) {
                        qty.setError("required");
                        qty.requestFocus();
                    } else if (Float.parseFloat(myQty) < 1) {
                        qty.setError("required");
                        qty.requestFocus();
                    } else if (myPri.isEmpty()) {
                        pri.setError("required");
                        pri.requestFocus();
                    } else if (Float.parseFloat(myPri) < 10) {
                        pri.setError("required");
                        pri.requestFocus();
                    } else {
                        requestQueue = Volley.newRequestQueue(getApplicationContext());
                        requestQueue.add(new StringRequest(Request.Method.POST, Manage.modiPr,
                                response -> {
                                    try {
                                        jsonObject = new JSONObject(response);
                                        int st = jsonObject.getInt("success");
                                        String msg = jsonObject.getString("message");
                                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                        if (st == 1) {
                                            alertDialog.dismiss();
                                            startActivity(new Intent(getApplicationContext(), InventDash.class));
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
                                para.put("description", myDesc);
                                para.put("quantity", myQty);
                                para.put("qty", String.format("%.0f", ((Float.parseFloat(prodMode.getQty()) - Float.parseFloat(prodMode.getQuantity())) + Float.parseFloat(myQty))));
                                para.put("price", myPri);
                                para.put("prod_id", prodMode.getId());
                                return para;
                            }
                        });
                    }
                });
                dialog.getButton(DialogInterface.BUTTON_NEUTRAL).setOnClickListener(viewe -> {
                    if (prodMode.getQty().equals(prodMode.getQuantity())) {
                        requestQueue = Volley.newRequestQueue(getApplicationContext());
                        requestQueue.add(new StringRequest(Request.Method.POST, Manage.dropPr,
                                response -> {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        int status = jsonObject.getInt("success");
                                        String msg = jsonObject.getString("message");
                                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                        if (status == 1) {
                                            startActivity(new Intent(getApplicationContext(), InventDash.class));
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
                                para.put("prod_id", prodMode.getId());
                                return para;
                            }
                        });
                    } else {
                        Toast.makeText(this, "Could not Delete this Product", Toast.LENGTH_SHORT).show();
                    }
                });
            });
            frameLayout = new FrameLayout(this);
            params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = getResources().getDimensionPixelSize(R.dimen.richer);
            params.rightMargin = getResources().getDimensionPixelSize(R.dimen.richer);
            veve.setLayoutParams(params);
            frameLayout.addView(veve);
            builder.setView(frameLayout);
            builder.setNegativeButton("Close", (v1, ve) -> {
            });
            builder.setTitle(Html.fromHtml("<font><u>" + productMode.getType() + "</u></font>"));
            alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.origi);
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view1 -> {
                if (!prodModeArrayList.isEmpty()) {
                    prodModeArrayList.clear();
                    prodAda.notifyDataSetChanged();
                }
                alertDialog.cancel();
            });
        });
        getProd();
    }

    private void getMesso() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(new StringRequest(Request.Method.POST, Manage.viewP,
                response -> {
                    try {
                        jsonObject = new JSONObject(response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            jsonArray = jsonObject.getJSONArray("victory");
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
                                prodMode = new ProdMode(id, category, type, description, imagery, qty, quantity, price, reg_date);
                                prodModeArrayList.add(prodMode);
                            }
                            prodAda = new ProdAda(InventDash.this, R.layout.category, prodModeArrayList);
                            lister.setAdapter(prodAda);
                            myse.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String text) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    prodAda.getFilter().filter(newText);
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
                para.put("category", productMode.getType());
                return para;
            }
        });
    }

    private void getProd() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(new StringRequest(Request.Method.POST, Manage.viewD,
                response -> {
                    try {
                        jsonObject = new JSONObject(response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String category = jsonObject.getString("category");
                                String image = jsonObject.getString("image");
                                String imagery = Manage.img + image;
                                productMode = new CategoryMode(category, imagery);
                                SubjectList.add(productMode);
                            }
                            suppAda = new CategoryAda(InventDash.this, R.layout.category, SubjectList);
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
                alert.setMessage("Serial: " + custModel.getSerial_no() + "\nFirstname: " + custModel.getFname() + "\nLastname: " + custModel.getLname() + "\nEmail: " + custModel.getEmail() + "\nPhone: " + custModel.getPhone() + "\nRole: " + custModel.getRole() + "\nUsername: " + custModel.getUsername() + "\nStatus: " + custModel.getStatus() + "\nDate: " + custModel.getDate());
                alert.setNegativeButton("quit", (dialog, id) -> dialog.cancel());
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                break;
            case R.id.logOut:
                inventSession.logoutInvent();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 4) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 4);
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 4 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
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