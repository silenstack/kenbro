package com.example.kenbro.Supplier;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
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
import com.example.kenbro.Home.Manage;
import com.example.kenbro.Home.ModelBid;
import com.example.kenbro.Home.PesaAda;
import com.example.kenbro.Home.SupModel;
import com.example.kenbro.Home.SupSession;
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

public class OldTender extends AppCompatActivity {
    SupSession supSession;
    SupModel custModel;
    EditText des, qty, pri;
    ImageView imageView;
    AlertDialog.Builder builder;
    Rect rect;
    Window window;
    LayoutInflater layoutInflater;
    AlertDialog alertDialog;
    View veve;
    RequestQueue requestQueue;
    ListView listView;
    SearchView searchView;
    Bitmap bitmap;
    String encodedimage;
    CardView bid, stoko;
    ArrayList<ModelBid> modelBidArrayList = new ArrayList<>();
    PesaAda modelAda;
    ModelBid modelBid;
    JSONArray jsonArray;
    JSONObject jsonObject;
    FrameLayout frameLayout;
    FrameLayout.LayoutParams params;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Older Tenders");
        setContentView(R.layout.activity_old_tender);
        listView = findViewById(R.id.listing);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.mySearch);
        getProd();
        supSession = new SupSession(getApplicationContext());
        custModel = supSession.getSupDetails();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            modelBid = (ModelBid) parent.getItemAtPosition(position);
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
            imageView = veve.findViewById(R.id.imager);
            imageView.setOnClickListener(v1 -> {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        000
                );
            });
            frameLayout = new FrameLayout(this);
            params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = getResources().getDimensionPixelSize(R.dimen.richer);
            params.rightMargin = getResources().getDimensionPixelSize(R.dimen.richer);
            veve.setLayoutParams(params);
            frameLayout.addView(veve);
            builder.setView(frameLayout);
            builder.setTitle(modelBid.getCategory());
            builder.setMessage(modelBid.getType() + "\nRequired: " + modelBid.getQuantity() + "\nDate: " + modelBid.getReg_date());
            builder.setNegativeButton("Close", (v1, ve) -> {
            });
            builder.setPositiveButton(Html.fromHtml("<font color='#1E8103'>Upload</font>"), (v1, ve) -> {
            });
            alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.origi);
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(viewe -> {
                alertDialog.cancel();
            });
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(viewe -> {
                Drawable drawable = imageView.getDrawable();
                final String myDesc = des.getText().toString().trim();
                final String myQty = qty.getText().toString().trim();
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
                } else if (Float.parseFloat(myQty) > Float.parseFloat(modelBid.getQuantity())) {
                    qty.setError("Reduce Quantity\nmax needed=" + modelBid.getQuantity());
                    qty.requestFocus();
                } else if (myPri.isEmpty()) {
                    pri.setError("required");
                    pri.requestFocus();
                } else if (Float.parseFloat(myPri) > Float.parseFloat(modelBid.getPrice())) {
                    pri.setError("Amazing.\nYour pricing is too high");
                    pri.requestFocus();
                } else if (drawable == null) {
                    Toast.makeText(this, "Add Resume", Toast.LENGTH_SHORT).show();
                } else {
                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(new StringRequest(Request.Method.POST, Manage.uploadS,
                            response -> {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    int st = jsonObject.getInt("success");
                                    String msg = jsonObject.getString("message");
                                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                    if (st == 1) {
                                        alertDialog.dismiss();
                                        startActivity(new Intent(getApplicationContext(), OldTender.class));
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
                            para.put("qty", String.format("%.0f", (Float.parseFloat(modelBid.getQuantity()) - Float.parseFloat(myQty))));
                            para.put("price", myPri);
                            para.put("category", modelBid.getCategory());
                            para.put("type", modelBid.getType());
                            para.put("supplier", custModel.getEntry_no());
                            para.put("id", modelBid.getId());
                            para.put("reg_date", new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date()));
                            para.put("image", encodedimage);
                            return para;
                        }
                    });
                }
            });
        });
    }

    private void getProd() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(new StringRequest(Request.Method.POST, Manage.older,
                response -> {
                    try {
                        jsonObject = new JSONObject(response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String category = jsonObject.getString("category");
                                String type = jsonObject.getString("type");
                                String pric = jsonObject.getString("price");
                                String qty = jsonObject.getString("qty");
                                String quantity = jsonObject.getString("quantity");
                                String reg_date = jsonObject.getString("reg_date");
                                modelBid = new ModelBid(id, category, type, pric, qty, quantity, reg_date);
                                modelBidArrayList.add(modelBid);
                            }
                            modelAda = new PesaAda(OldTender.this, R.layout.see_b, modelBidArrayList);
                            listView.setAdapter(modelAda);
                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String text) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    modelAda.getFilter().filter(newText);
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
                para.put("date", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                para.put("timer1", "00:00 AM");
                para.put("timer2", "23:59 PM");
                return para;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 000) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 000);
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 000 && resultCode == RESULT_OK && data != null) {
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