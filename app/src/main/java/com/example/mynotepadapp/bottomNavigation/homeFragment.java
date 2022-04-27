package com.example.mynotepadapp.bottomNavigation;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.car.ui.AlertDialogBuilder;
import com.example.mynotepadapp.Adapter.Adapters;
import com.example.mynotepadapp.Database.DBhelper;
import com.example.mynotepadapp.MainActivity;
import com.example.mynotepadapp.Models.Models;
import com.example.mynotepadapp.NoteWriteActivity;
import com.example.mynotepadapp.R;
import com.example.mynotepadapp.SignupoptionsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Locale;
import java.util.zip.Inflater;


public class homeFragment extends Fragment {

    RecyclerView recyclerView;
    Adapters adapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        LoadLocale();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);

        notepad();
        return view;


    }


    //notepad data read
    private void notepad() {
        DBhelper dBhelper = new DBhelper(getActivity());
        ArrayList<Models> modelArrayList = dBhelper.getOrders();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new Adapters(getActivity(), modelArrayList);
        recyclerView.setAdapter(adapter);

        ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("Notepad...");

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.searchmenu, menu);
        inflater.inflate(R.menu.hview, menu);
        inflater.inflate(R.menu.language, menu);

        MenuItem item = menu.findItem(R.id.searchmenus);

        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });


        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.views:
                Dialog dialog = new Dialog(getActivity(), R.style.Dialog);

                dialog.setContentView(R.layout.dialog_layout);
                LinearLayout lists;
                LinearLayout grids;
                LinearLayout largegrids;

                lists=dialog.findViewById(R.id.lists);
                grids=dialog.findViewById(R.id.grids);
                largegrids=dialog.findViewById(R.id.largegrids);

                lists.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        dialog.dismiss();
                    }
                });


                grids.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
                        dialog.dismiss();
                    }
                });

                largegrids.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
                        dialog.dismiss();
                    }
                });



                dialog.show();

                break;

            case R.id.mylanguage:
                        ChanLanguage();

                break;


        }

        return super.onOptionsItemSelected(item);
    }

    private void ChanLanguage() {
        final String language[] = {"Englist", "Hindi","Marathi"};
        AlertDialogBuilder mbuilder =new AlertDialogBuilder(getActivity());
        mbuilder.setTitle("Choose Language");
        mbuilder.setSingleChoiceItems(language, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (i==0){
                    setLocale("");
                   requireContext();
//                    recreate();
                } else if (i==1){
                    setLocale("hi");
                    requireContext();
//                    recreate();
                }else if (i==2){
                    setLocale("mr");
                    requireContext();
//                    recreate();
                }
            }
        });
        mbuilder.create();
        mbuilder.show();


    }


    private void setLocale(String language) {
        Locale locale=new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration =new Configuration();
        configuration.locale =locale;
        getContext().getResources().updateConfiguration(configuration,getContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor=this.getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE).edit();
        editor.putString("app_lang",language);
        editor.apply();
    }



    private void LoadLocale(){
        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("Setting",Context.MODE_PRIVATE);
        String languagess =sharedPreferences.getString("app_lang","");
        setLocale(languagess);
    }
}