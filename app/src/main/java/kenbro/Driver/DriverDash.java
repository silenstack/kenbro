
package com.example.kenbro.Driver;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kenbro.Home.DrivModel;
import com.example.kenbro.Home.DrivSession;
import com.example.kenbro.MainActivity;
import com.example.kenbro.R;

import java.util.Objects;

public class DriverDash extends AppCompatActivity {
    DrivModel custModel;
    DrivSession drivSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drivSession = new DrivSession(getApplicationContext());
        custModel = drivSession.getDrivDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + custModel.getFname());
        setContentView(R.layout.activity_driver_dash);
        findViewById(R.id.Orders).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), NewDrive.class));
        });
        findViewById(R.id.Serv).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), DriveHistory.class));
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
                alert.setMessage("Registry: " + custModel.getEntry_no() + "\nLicense: " + custModel.getLicense() + "\nFirstname: " + custModel.getFname() + "\nLastname: " + custModel.getLname() + "\nEmail: " + custModel.getEmail() + "\nPhone: " + custModel.getPhone() + "\nAddress: " + custModel.getAddress() + "\nUsername: " + custModel.getUsername() + "\nStatus: " + custModel.getStatus() + "\nDate: " + custModel.getDate());
                alert.setNegativeButton("quit", (dialog, id) -> dialog.cancel());
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                break;
            case R.id.logOut:
                drivSession.logoutDri();
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