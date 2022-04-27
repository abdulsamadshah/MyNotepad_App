package com.example.mynotepadapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.car.ui.AlertDialogBuilder;
import com.example.mynotepadapp.Adapter.Adapters;
import com.example.mynotepadapp.Database.DBhelper;
import com.example.mynotepadapp.Models.Models;
import com.example.mynotepadapp.bottomNavigation.CalenderFragment;
import com.example.mynotepadapp.bottomNavigation.MoreFragment;
import com.example.mynotepadapp.bottomNavigation.SearchFragment;
import com.example.mynotepadapp.bottomNavigation.homeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.shadow.ShadowRenderer;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomnavigation;
    FloatingActionButton adds;
    FirebaseAuth auth;
    Adapters adapter;
    Button changlanguage;

//    private final String CHANNEL_ID = "simple_notification";
//    private final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomnavigation = findViewById(R.id.bottomnavigation);
        adds = findViewById(R.id.adds);



        //NOtifcation send
//        click.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                createNotification();
//                addNotification();
//            }
//        });


        adds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoteWriteActivity.class);
                startActivity(intent);
            }
        });


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new homeFragment());
        transaction.commit();

        bottomnavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.home:
                        transaction.replace(R.id.container, new homeFragment());
                        break;

                    case R.id.Calender:
                        transaction.replace(R.id.container, new CalenderFragment());
                        break;

                    case R.id.search:
                        transaction.replace(R.id.container, new SearchFragment());
                        break;

                    case R.id.More:
                        transaction.replace(R.id.container, new MoreFragment());
                        break;

                }
                transaction.commit();
                return true;
            }
        });


    }

//    private void addNotification() {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
//        builder.setSmallIcon(R.drawable.grid);
//        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.calendar));
//        builder.setContentTitle("Simple Notification");
//        builder.setContentText("This is all about Simple Notification");
//        builder.setAutoCancel(true);
//        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
//        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
//
//
//    }

//    private void createNotification() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//            CharSequence name = "Simple Notification";
//            String desc = "This is all about simple Notification.";
//
//            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
//            notificationChannel.setDescription(desc);
//
//            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//            notificationManager.createNotificationChannel(notificationChannel);
//
//
//
//        }
//    }


}