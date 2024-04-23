package com.example.kenbro;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.kenbro.Artisan.ArtisanDash;
import com.example.kenbro.Artisan.ArtisanLog;
import com.example.kenbro.Customer.CustDash;
import com.example.kenbro.Customer.CustLog;
import com.example.kenbro.Dispa.DisLog;
import com.example.kenbro.Dispa.DispHome;
import com.example.kenbro.Driver.DriverDash;
import com.example.kenbro.Driver.DriverLog;
import com.example.kenbro.Finance.FinaDash;
import com.example.kenbro.Finance.Finalog;
import com.example.kenbro.Home.ArtisanSession;
import com.example.kenbro.Home.CustModel;
import com.example.kenbro.Home.CustSession;
import com.example.kenbro.Home.DipSession;
import com.example.kenbro.Home.DrivModel;
import com.example.kenbro.Home.DrivSession;
import com.example.kenbro.Home.FinaSession;
import com.example.kenbro.Home.InstalSession;
import com.example.kenbro.Home.InventSession;
import com.example.kenbro.Home.StaffModel;
import com.example.kenbro.Home.SupModel;
import com.example.kenbro.Home.SupSession;
import com.example.kenbro.Install.InstallDash;
import com.example.kenbro.Install.InstallLog;
import com.example.kenbro.Invent.InventDash;
import com.example.kenbro.Invent.InventLog;
import com.example.kenbro.Supplier.SupDash;
import com.example.kenbro.Supplier.SuppLog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    CustModel custModel;
    CustSession custSession;
    SupModel supModel;
    SupSession supSession;
    DrivModel drivModel;
    DrivSession drivSession;
    StaffModel staffModel;
    FinaSession finaSession;
    ArtisanSession artisanSession;
    InventSession inventSession;
    DipSession dipSession;
    InstalSession instalSession;
    CardView Finance, Stock, Installer, Artisan;
    BottomNavigationView bottomNavigationView;
    ImageView imageView;
    Rect rect;
    Window window;
    AlertDialog.Builder builder, alert;
    AlertDialog alertDialog, dialog;
    LayoutInflater layoutInflater;
    View volvo;
    Button about, help, exit, cont;
    FrameLayout frameLayout;
    FrameLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Dashboard");
        setContentView(R.layout.activity_main);
        custSession = new CustSession(getApplicationContext());
        custModel = custSession.getCustDetails();
        drivSession = new DrivSession(getApplicationContext());
        drivModel = drivSession.getDrivDetails();
        supSession = new SupSession(getApplicationContext());
        supModel = supSession.getSupDetails();
        finaSession = new FinaSession(getApplicationContext());
        staffModel = finaSession.getFinaDetails();
        artisanSession = new ArtisanSession(getApplicationContext());
        staffModel = artisanSession.getArtDetails();
        inventSession = new InventSession(getApplicationContext());
        staffModel = inventSession.getInventDetails();
        dipSession = new DipSession(getApplicationContext());
        staffModel = dipSession.getUserDetails();
        instalSession = new InstalSession(getApplicationContext());
        staffModel = instalSession.getInstalDetails();
        bottomNavigationView = findViewById(R.id.bottom);
        Finance = findViewById(R.id.finance);
        Stock = findViewById(R.id.stock);
        Installer = findViewById(R.id.installation);
        Artisan = findViewById(R.id.artisan);
        imageView = findViewById(R.id.myImage);
        imageView.setOnClickListener(view -> {
            builder = new AlertDialog.Builder(this, R.style.Arap);
            rect = new Rect();
            window = this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            layoutInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
            volvo = layoutInflater.inflate(R.layout.basement, null);
            about = volvo.findViewById(R.id.btnAbout);
            help = volvo.findViewById(R.id.btnHelp);
            exit = volvo.findViewById(R.id.btnExit);
            cont = volvo.findViewById(R.id.btnCont);
            exit.setOnClickListener(view1 -> {
                finishAffinity();
            });
            about.setOnClickListener(view1 -> {
                alert = new AlertDialog.Builder(this);
                alert.setTitle(Html.fromHtml("<font><b><u>About KenBro LTD</u></b></font>"));
                alert.setMessage(getString(R.string.about));
                alert.setPositiveButton("Close", (dr, d) -> dr.cancel());
                dialog = alert.create();
                dialog.show();
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
            });
            help.setOnClickListener(view1 -> {
                alert = new AlertDialog.Builder(this);
                alert.setTitle(Html.fromHtml("<font><b><u>Frequently Asked Questions</u></b></font>"));
                alert.setMessage(getString(R.string.Help));
                alert.setPositiveButton("Close", (dr, d) -> dr.cancel());
                dialog = alert.create();
                dialog.show();
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
            });
            cont.setOnClickListener(view1 -> {
                alert = new AlertDialog.Builder(this);
                alert.setTitle(Html.fromHtml("<font><b><u>KenBro LTD Contact</u></b></font>"));
                alert.setMessage(getString(R.string.cont));
                alert.setPositiveButton("Close", (dr, d) -> dr.cancel());
                dialog = alert.create();
                dialog.show();
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.origi);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
            });
            builder.setView(volvo);
            alertDialog = builder.create();
            alertDialog.show();
            alertDialog.getWindow().setGravity(Gravity.BOTTOM);
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.origi);
            alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        });
        bottomNavigationView.setSelectedItemId(R.id.customer);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.driver:
                    /*if (drivSession.loggedDriv())
                        startActivity(new Intent(getApplicationContext(), DriverDash.class));
                    else*/
                        startActivity(new Intent(getApplicationContext(), DriverLog.class));
                    overridePendingTransition(10, 10);
                    return true;
                case R.id.customer:
                    /*if (custSession.loggedCust())
                        startActivity(new Intent(getApplicationContext(), CustDash.class));
                    else*/
                        startActivity(new Intent(getApplicationContext(), CustLog.class));
                    overridePendingTransition(10, 10);
                    return true;
                case R.id.supplier:
                    /*if (supSession.loggedSup())
                        startActivity(new Intent(getApplicationContext(), SupDash.class));
                    else*/
                        startActivity(new Intent(getApplicationContext(), SuppLog.class));
                    overridePendingTransition(10, 10);
                    return true;
            }
            return false;
        });
        Finance.setOnClickListener(v -> {
            /*if (finaSession.loggedFina())
                startActivity(new Intent(getApplicationContext(), FinaDash.class));
            else*/
                startActivity(new Intent(getApplicationContext(), Finalog.class));
        });
        Installer.setOnClickListener(v -> {
            /*if (instalSession.loggedinstal())
                startActivity(new Intent(getApplicationContext(), InstallDash.class));
            else*/
                startActivity(new Intent(getApplicationContext(), InstallLog.class));
        });
        Artisan.setOnClickListener(v -> {
           /* if (artisanSession.loggedArt())
                startActivity(new Intent(getApplicationContext(), ArtisanDash.class));
            else*/
                startActivity(new Intent(getApplicationContext(), ArtisanLog.class));
        });
        Stock.setOnClickListener(v -> {
            //builder = new AlertDialog.Builder(this, R.style.Well);
            rect = new Rect();
            window = this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            layoutInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
            volvo = layoutInflater.inflate(R.layout.shipper, null);
            frameLayout = new FrameLayout(this);
            params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = getResources().getDimensionPixelSize(R.dimen.richer);
            params.rightMargin = getResources().getDimensionPixelSize(R.dimen.richer);
            volvo.setLayoutParams(params);
            frameLayout.addView(volvo);
            volvo.findViewById(R.id.Inventory).setOnClickListener(view -> {
                /*if (inventSession.loggedInvent())
                    startActivity(new Intent(getApplicationContext(), InventDash.class));
                else*/
                    startActivity(new Intent(getApplicationContext(), InventLog.class));
            });
            volvo.findViewById(R.id.Disb).setOnClickListener(view -> {
                /*if (dipSession.loggedDisp())
                    startActivity(new Intent(getApplicationContext(), DispHome.class));
                else*/
                    startActivity(new Intent(getApplicationContext(), DisLog.class));
            });
            BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this, R.style.BottomSheetDialog);
            dialog.setContentView(frameLayout);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.show();
        });
    }
}