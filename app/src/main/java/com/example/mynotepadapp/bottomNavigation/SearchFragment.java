package com.example.mynotepadapp.bottomNavigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mynotepadapp.Adapter.Adapters;
import com.example.mynotepadapp.Models.Models;
import com.example.mynotepadapp.R;

import java.util.ArrayList;


public class SearchFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("Up Coming...");

       return view;
    }


}