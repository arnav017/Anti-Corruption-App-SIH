package com.example.anticorruptionapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.anticorruptionapp.Fragment.ItemFragment;
import com.example.anticorruptionapp.data.RecentData;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.container,new ItemFragment()).commit();

    /*    Intent firstActivity = new Intent(this,DrawerActivity.class);
        startActivity(firstActivity);*/
    }

    /*@Override
    public void onListFragmentInteraction(RecentData.DummyItem item) {

    }*/
}
