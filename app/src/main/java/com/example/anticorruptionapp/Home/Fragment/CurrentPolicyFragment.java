package com.example.anticorruptionapp.Home.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anticorruptionapp.Home.PolicyAdapter;
import com.example.anticorruptionapp.PolicyParser;
import com.example.anticorruptionapp.R;
import com.example.anticorruptionapp.data.Policy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class CurrentPolicyFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Policy> policyArrayList;
    private DatabaseReference databaseReference,policyReference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_policies,container,false);

        recyclerView=view.findViewById(R.id.current_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchPoliciesFromFirebase();

        return view;
    }

    public ArrayList<Policy> fetchPoliciesFromFirebase(){
        policyArrayList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        policyReference = databaseReference.child("policies").child("currrent");

        policyReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                    policyArrayList = new PolicyParser().parsePolicies((Map<String,Object>) dataSnapshot.getValue());
                recyclerView.setAdapter(new PolicyAdapter(policyArrayList));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("errxxx", "Failed to read value.", error.toException());
            }
        });
        return policyArrayList;
    }
}
