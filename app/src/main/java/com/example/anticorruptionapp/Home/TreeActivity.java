package com.example.anticorruptionapp.Home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.anticorruptionapp.R;

public class TreeActivity extends AppCompatActivity {

    RecyclerView nodeRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree);
        nodeRecyclerView = findViewById(R.id.node_recycler_view);
        nodeRecyclerView.setAdapter(new TreeAdapter());
        nodeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
