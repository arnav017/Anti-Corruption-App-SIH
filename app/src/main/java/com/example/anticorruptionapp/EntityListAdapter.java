package com.example.anticorruptionapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.api.services.language.v1.model.Entity;

import java.util.List;
import java.util.Map;

/**
 * Created by brijesh on 25/7/17.
 */

public class EntityListAdapter extends RecyclerView.Adapter<EntityListAdapter.EntityViewHolder> {

    private List<Entity> entityList;
    private Context mContext;

    public EntityListAdapter(List<Entity> entityList) {
        this.entityList = entityList;
    }

    @Override
    public EntityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_entity, parent, false);
        return new EntityViewHolder(view);
    }


    @Override
    public void onBindViewHolder(EntityViewHolder holder, int position) {
        Entity et = entityList.get(position);
        holder.entityName.setText(et.getName());
        holder.entityType.setText(et.getType());
        holder.salience.setText("Salience: " + et.getSalience());

        final Map<String, String> metadata = et.getMetadata();
        if (metadata != null && metadata.containsKey("wikipedia_url")) {
            holder.entityUrl.setVisibility(View.VISIBLE);
            holder.entityUrl.setText(metadata.get("wikipedia_url"));
        }
    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }

    static class EntityViewHolder extends RecyclerView.ViewHolder {

        TextView entityName;
        TextView entitySentiment;
        TextView entityType;
        TextView entityUrl;
        TextView salience;

        public EntityViewHolder(View itemView) {
            super(itemView);
            entityName = itemView.findViewById(R.id.entityName);
            entitySentiment = itemView.findViewById(R.id.entitySentiment);
            entityType = itemView.findViewById(R.id.entityType);
            salience = itemView.findViewById(R.id.salience);
        }
    }
}
