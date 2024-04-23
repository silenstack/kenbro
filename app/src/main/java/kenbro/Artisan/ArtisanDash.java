package com.example.kenbro.Artisan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kenbro.Home.ArtisanSession;
import com.example.kenbro.Home.StaffModel;
import com.example.kenbro.MainActivity;
import com.example.kenbro.R;

import java.util.Objects;

public class ArtisanDash extends AppCompatActivity {
    StaffModel custModel;
    ArtisanSession artisanSession;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        artisanSession = new ArtisanSession(getApplicationContext());
        custModel = artisanSession.getArtDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + custModel.getFname());
        setContentView(R.layout.activity_artisan_dash);
        findViewById(R.id.Orders).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), NewProj.class));
        });
        findViewById(R.id.Serv).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ProjeHis.class));
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
                artisanSession.logoutArt();
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