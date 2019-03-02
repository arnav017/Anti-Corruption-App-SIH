package com.example.anticorruptionapp.Home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anticorruptionapp.R;

public class TreeAdapter extends RecyclerView.Adapter<TreeAdapter.TreeViewHolder> {


    @NonNull
    @Override
    public TreeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_node,viewGroup,false);
        return new TreeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TreeViewHolder treeViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class TreeViewHolder extends RecyclerView.ViewHolder{

        public TreeViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
