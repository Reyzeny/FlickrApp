package com.reyzeny.br.Home;

import android.content.Intent;
import android.os.Bundle;

import com.reyzeny.br.PhotoSearch.PhotoSearchActivity;
import com.reyzeny.br.R;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize view model
        MainActivityViewModel mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);


        // Setup recyclerview
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mainActivityViewModel.adapter);

        // setup progressbar
        progressBar = findViewById(R.id.progressBar);
        mainActivityViewModel.isProgressVisible.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean visible) {
                if (visible)
                    progressBar.setVisibility(View.VISIBLE);
                else
                    progressBar.setVisibility(View.INVISIBLE);
            }
        });

        //request for photos
        mainActivityViewModel.fetchPhotos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.main_search) {
            startActivity(new Intent(this, PhotoSearchActivity.class));
            return true;
        }
        return true;
    }
}
