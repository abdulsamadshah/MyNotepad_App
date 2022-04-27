package com.example.mynotepadapp.bottomNavigation;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.car.ui.AlertDialogBuilder;
import com.example.mynotepadapp.R;
import com.example.mynotepadapp.SettingsActivity;
import com.example.mynotepadapp.SignupoptionsActivity;

import java.util.Locale;


public class MoreFragment extends Fragment {
    Button changlanguage;
    CardView signup;
    LinearLayout linearsetting;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("More");
        signup=view.findViewById(R.id.signup);
        linearsetting=view.findViewById(R.id.linearsetting);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), SignupoptionsActivity.class);
                startActivity(intent);
            }
        });


        linearsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        return view;
}

    }