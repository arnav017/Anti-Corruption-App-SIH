package com.example.anticorruptionapp.Profile;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.example.anticorruptionapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URI;

public class Presenter {

    ProfilePage context;
    public Presenter(ProfilePage context){
        this.context = context;
    }

    public void setProfile(Model model){
        FirebaseDatabase.getInstance().getReference("users").child("profiles").child(model.getContact())
                .setValue(model);
    }

    public void preFillprofile(){
        FirebaseDatabase.getInstance().getReference("users").child("profiles").child("8853487447")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Model model ;
                        if(dataSnapshot.exists()){
                            model = dataSnapshot.getValue(Model.class);
                            context.progressBar.setVisibility(View.GONE);
                            if(model != null) {
                                if(model.getImage_uri() != null){
                                    Glide.with(context).load(Uri.parse(model.getImage_uri()))
                                            .override(130,130).centerCrop().into(context.profile_image);
                                }
                                if (model.getName() != null)
                                    context.name.setText(model.getName());
                                else
                                    context.name.setText(R.string.name);
                                if (model.getEmail() != null)
                                    context.email.setText(model.getEmail());
                                else
                                    context.email.setText(R.string.email);
                                if (model.getDesignation() != null)
                                    context.designation.setText(model.getDesignation());
                                else
                                    context.designation.setText(R.string.designation);
                                if (model.getContact() != null)
                                    context.contact.setText(model.getContact());
                                else
                                    context.contact.setText(R.string.contact);
                                if (model.getHome_address() != null)
                                    context.home_address.setText(model.getHome_address());
                                else
                                    context.home_address.setText(R.string.home_address);
                                if (model.getHome_district() != null)
                                    context.home_district.setText(model.getHome_district());
                                else
                                    context.home_district.setText(R.string.district);
                                if (model.getWork_address() != null)
                                    context.work_address.setText(model.getWork_address());
                                else
                                    context.work_address.setText(R.string.work_address);
                                if (model.getWork_district() != null)
                                    context.work_district.setText(model.getWork_district());
                                else
                                    context.work_district.setText(R.string.district);
                            }
                        }
                        else {
                            context.progressBar.setVisibility(View.GONE);
                            context.name.setText(R.string.name);
                            context.email.setText(R.string.email);
                            context.designation.setText(R.string.designation);
                            context.contact.setText(R.string.contact);
                            context.home_address.setText(R.string.home_address);
                            context.home_district.setText(R.string.district);
                            context.work_address.setText(R.string.work_address);
                            context.work_district.setText(R.string.district);
                        }
                        context.profile_container.setClickable(true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
