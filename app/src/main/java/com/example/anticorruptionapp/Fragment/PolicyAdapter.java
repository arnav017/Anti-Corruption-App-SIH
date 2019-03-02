package com.example.anticorruptionapp.Fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class PolicyAdapter extends RecyclerView.Adapter<PolicyAdapter.PolicyViewHolder> {

    @NonNull
    @Override
    public PolicyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PolicyViewHolder policyViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class PolicyViewHolder extends RecyclerView.ViewHolder{

        public PolicyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
