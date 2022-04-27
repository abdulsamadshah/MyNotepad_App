package com.example.mynotepadapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mynotepadapp.Database.DBhelper;
import com.example.mynotepadapp.bottomNavigation.homeFragment;

import java.util.Objects;

public class NoteWriteActivity extends AppCompatActivity {
    EditText write_titles, write_descriptions;
    DBhelper dBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_write);


        write_titles = findViewById(R.id.write_titles);
        write_descriptions = findViewById(R.id.write_descriptions);
        final String title = getIntent().getStringExtra("title");
        final String description = getIntent().getStringExtra("description");
        write_titles.setText(title);
        write_descriptions.setText(description);
        dBhelper = new DBhelper(this);


        getSupportActionBar().setTitle(" Notepad Par.... ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        getMenuInflater().inflate(R.menu.notewrite, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save:
                boolean isInserted = dBhelper.insertOrder(
                        write_titles.getText().toString(),
                        write_descriptions.getText().toString());

                if (isInserted) {
                    Intent intent = new Intent(NoteWriteActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plan");
                String title = write_titles.getText().toString();
                String description = write_descriptions.getText().toString();

                String sharebody = title + description;
                String sharesub = "Notepad data Share";

                shareIntent.putExtra(Intent.EXTRA_SUBJECT, sharesub);
                shareIntent.putExtra(Intent.EXTRA_TEXT, sharebody);

                startActivity(Intent.createChooser(shareIntent, "share using"));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }
}