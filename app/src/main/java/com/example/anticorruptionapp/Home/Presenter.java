package com.example.anticorruptionapp.Home;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.anticorruptionapp.Profile.Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Presenter {

    HomePage context;
    public  Presenter(HomePage context){
        this.context = context;
    }

    public void setHeader(){
        FirebaseDatabase.getInstance().getReference("users").child("profiles")
                .child("8853487447").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                com.example.anticorruptionapp.Profile.Model model;
                if(dataSnapshot.exists()){
                    model = dataSnapshot.getValue(Model.class);
                    if(model != null){
                        if(model.getImage_uri() != null){
                            Glide.with(context).load(Uri.parse(model.getImage_uri()))
                                    .override(80,80).centerCrop().into(context.profilePic);
                        }
                        if(model.getName() != null){
                            context.name.setText(model.getName());
                        }
                        if(model.getEmail() != null){
                            context.email.setText(model.getEmail());
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
