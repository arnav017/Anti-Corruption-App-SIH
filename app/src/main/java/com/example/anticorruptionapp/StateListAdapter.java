package com.example.anticorruptionapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StateListAdapter extends ArrayAdapter<State> implements Filterable {
    private Context mContext;
    int mResource;
    ArrayList<State> mOriginalValues;
    ArrayList<State> mDisplayedValues;
    LayoutInflater inflater;


    public StateListAdapter(@NonNull Context context, int resource, ArrayList<State> state_list) {
        super(context, resource);
        mContext = context;
        mResource = resource;
        mOriginalValues = state_list;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // return super.getView(position, convertView, parent);
        String state_name=getItem(position).getState_name();
        State state=new State(state_name);
        LayoutInflater inflater=LayoutInflater.from(mContext);
        convertView=inflater.inflate(mResource,parent,false);
        TextView tname=convertView.findViewById(R.id.state_text);
        tname.setText(state_name);
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                mDisplayedValues = (ArrayList<State>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<State> FilteredArrList = new ArrayList<State>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<State>(mDisplayedValues); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        String data = mOriginalValues.get(i).getState_name();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new State(mOriginalValues.get(i).getState_name()));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }
}