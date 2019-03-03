package com.example.anticorruptionapp.Home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.TransitionRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anticorruptionapp.FeedbackActivity;
import com.example.anticorruptionapp.PolicyDescriptionActivity;
import com.example.anticorruptionapp.R;
import com.example.anticorruptionapp.data.Policy;

import java.util.ArrayList;

public class PolicyAdapter extends RecyclerView.Adapter<PolicyAdapter.PolicyViewHolder> {
    ArrayList<Policy> policyArrayList;
    Context context;

    public PolicyAdapter(ArrayList<Policy> list){
    policyArrayList = list;
    }

    @NonNull
    @Override
    public PolicyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_policy,viewGroup,false);
        return new PolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PolicyViewHolder policyViewHolder, int i) {
        policyViewHolder.name.setText(policyArrayList.get(i).getName());
        policyViewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(context, PolicyDescriptionActivity.class);
                context.startActivity(in);
            }
        });
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
