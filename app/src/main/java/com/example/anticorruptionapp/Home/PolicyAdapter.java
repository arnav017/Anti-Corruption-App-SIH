package com.example.anticorruptionapp.Home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anticorruptionapp.R;
import com.example.anticorruptionapp.data.Policy;

import java.util.ArrayList;

public class PolicyAdapter extends RecyclerView.Adapter<PolicyAdapter.PolicyViewHolder> {
    ArrayList<Policy> policyArrayList;

    public PolicyAdapter(ArrayList<Policy> list){
    policyArrayList = list;
    }

    @NonNull
    @Override
    public PolicyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_policy,viewGroup,false);
        return new PolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PolicyViewHolder policyViewHolder, int i) {
        policyViewHolder.name.setText(policyArrayList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        Log.w("xxx", "length"+policyArrayList.size());
        return policyArrayList.size();
    }

    class PolicyViewHolder extends RecyclerView.ViewHolder{

        public final View mView;
        public final TextView name;


        public PolicyViewHolder(View view) {
            super(view);
            mView = view;
            name =  view.findViewById(R.id.policy_name);
        }
    }
}
