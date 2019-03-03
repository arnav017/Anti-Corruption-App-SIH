package com.example.anticorruptionapp.Associated_Policies;

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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.PolicyViewHolder> {
    ArrayList<Model> policyArrayList;
    AssociatedPolicies context;

    public MyAdapter(ArrayList<Model> list , AssociatedPolicies context){
        policyArrayList = list;
        this.context = context;
    }

    @NonNull
    @Override
    public PolicyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_policy,viewGroup,false);
        return new PolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PolicyViewHolder policyViewHolder, int i) {
        policyViewHolder.name.setText(policyArrayList.get(i).getKey());
        if(policyArrayList.get(i).getValue().equals("completed"))
            policyViewHolder.name.setBackground(context.getResources().getDrawable(R.color.white));
        if(policyArrayList.get(i).getValue().equals("accepted")){
            policyViewHolder.name.setBackground(context.getResources().getDrawable(R.color.green));
            policyViewHolder.name.setTextColor(context.getResources().getColor(R.color.white));
        }
        if(policyArrayList.get(i).getValue().equals("requested")){
            policyViewHolder.name.setBackground(context.getResources().getDrawable(R.color.red));
            policyViewHolder.name.setTextColor(context.getResources().getColor(R.color.white));
        }
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
