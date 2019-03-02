package com.example.anticorruptionapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.anticorruptionapp.Fragment.ItemFragment;
import com.example.anticorruptionapp.Home.HomePage;
import com.example.anticorruptionapp.data.RecentData;

public class MainActivity extends AppCompatActivity implements Runnable{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread main = new Thread(MainActivity.this);
        main.start();

        getSupportFragmentManager().beginTransaction().add(R.id.container,new ItemFragment()).commit();

    /*    Intent firstActivity = new Intent(this,DrawerActivity.class);
        startActivity(firstActivity);*/
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finish();
        startActivity(new Intent(MainActivity.this , HomePage.class));
    }

    /*@Override
    public void onListFragmentInteraction(RecentData.DummyItem item) {

    }*/
}
