package com.example.kenbro.Dispa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.kenbro.Home.DipSession;
import com.example.kenbro.Home.InstalSession;
import com.example.kenbro.Home.StaffModel;
import com.example.kenbro.Install.AssignArt;
import com.example.kenbro.Install.CompletePro;
import com.example.kenbro.Install.NewService;
import com.example.kenbro.MainActivity;
import com.example.kenbro.R;

import java.util.Objects;

public class DispHome extends AppCompatActivity {
    StaffModel custModel;
    DipSession instalSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instalSession = new DipSession(getApplicationContext());
        custModel = instalSession.getUserDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + custModel.getFname());
        setContentView(R.layout.activity_disp_home);
        findViewById(R.id.Orders).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ShipNew.class));
        });
        findViewById(R.id.Serv).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), OldShip.class));
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
                instalSession.logoutUser();
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