package com.example.anticorruptionapp;

import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Sign_Up extends AppCompatActivity {
    String jsonString;
    ArrayList<String> mListState,mListDistrict;
    MaterialEditText state,district;
   // StateListAdapter stateAdapter,districtAdapter;
    ExpandableHeightListView mListStateView,mListDistrictView;
    ArrayList<String> DisplayList,DisplayListDistrict;
    ArrayAdapter<String> stringArrayAdapter,districtArrayAdapter;
    AlertDialog.Builder dlgAlertDistrict,dlgAlertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);
        state = findViewById(R.id.edtState);
        district=findViewById(R.id.edtDistrict);
        mListStateView = findViewById(R.id.state_list);
        mListDistrictView=findViewById(R.id.district_list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mListStateView.setNestedScrollingEnabled(true);
        }
        mListDistrict=new ArrayList<>();
        DisplayListDistrict=new ArrayList<>();
        mListState = new ArrayList<String>();
        DisplayList = new ArrayList<>();
        jsonString = loadJSONFromAsset();
        try {
            fillArrayList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // stateAdapter=new StateListAdapter(this,R.layout.state_list_view,mListState);
        //Toast.makeText(this,mListState.get(4),Toast.LENGTH_LONG).show();
        stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, DisplayList);
        mListStateView.setAdapter(stringArrayAdapter);
        districtArrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,DisplayListDistrict);
        mListDistrictView.setAdapter(districtArrayAdapter);
        mListStateView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                state.setText((String) adapterView.getItemAtPosition(i));
               mListStateView.setVisibility(View.GONE);
               mListDistrict.clear();
               DisplayListDistrict.clear();
               districtArrayAdapter.notifyDataSetChanged();
                try {
                    fillDistrictList( (String) adapterView.getItemAtPosition(i));
                    //Toast.makeText(Sign_Up.this,(String) adapterView.getItemAtPosition(i),Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mListDistrictView.setVisibility(View.VISIBLE);
            }
        });
        state.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mListStateView.setVisibility(View.VISIBLE);
                mListStateView.setExpanded(true);
             //   stringArrayAdapter.clear();
                DisplayList.clear();
                for (int k = 0; k < mListState.size(); k++) {
                    if (mListState.get(k).toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        DisplayList.add(mListState.get(k));
                    }
                }
                stringArrayAdapter.notifyDataSetChanged();
                // Sign_Up.this.stateAdapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //mListStateView.setVisibility(View.GONE);
                if(state.getText().toString().matches("")){
                    mListStateView.setVisibility(View.GONE);
                }
            }
        });
        district.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mListDistrictView.setVisibility(View.VISIBLE);
                mListDistrictView.setExpanded(true);
                //   stringArrayAdapter.clear();
                DisplayListDistrict.clear();
                for (int k = 0; k < mListDistrict.size(); k++) {
                    if (mListDistrict.get(k).toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        DisplayListDistrict.add(mListDistrict.get(k));
                    }
                }
                districtArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public String loadJSONFromAsset() {
        String json = "";
        StringBuilder builder = new StringBuilder();
        try {
            InputStream is = this.getAssets().open("states.json");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(is, "UTF-8"));
            while ((json = bufferedReader.readLine()) != null) {
                builder.append(json);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        json = new String(builder);
        return json;
    }

    void fillArrayList() throws JSONException {
        JSONObject reader = new JSONObject(jsonString);
        JSONArray initial= reader.getJSONArray("states");
        //Toast.makeText(this,initial.getJSONObject(2).getString("state"), Toast.LENGTH_SHORT).show();
        for (int i = 0; i < initial.length(); ++i) {
            JSONObject menuItemObject = initial.getJSONObject(i);
            mListState.add(menuItemObject.getString("state"));
        }
    }
    void fillDistrictList(String district) throws JSONException {
        JSONObject reader = new JSONObject(jsonString);
        JSONArray initial= reader.getJSONArray("states");
        //Toast.makeText(this,initial.getJSONObject(2).getString("state"), Toast.LENGTH_SHORT).show();
        //JSONObject menuItemObject = initial.getJSONObject(i);
      //  Toast.makeText(this,initial.getJSONObject(2).getJSONArray("districts").getString(2),Toast.LENGTH_LONG).show();
        int pos=-1;
        for (int i = 0; i < initial.length(); ++i) {
            JSONObject menuItemObject = initial.getJSONObject(i);
            if (menuItemObject.getString("state").equals(district)) {
                pos = i;
                break;
            }
        }
          //  Toast.makeText(Sign_Up.this,pos,Toast.LENGTH_LONG).show();
            if(pos!=-1){
                mListDistrict.clear();
                DisplayListDistrict.clear();
                for( int i=0;i<initial.getJSONObject(pos).getJSONArray("districts").length();i++){
                    mListDistrict.add(initial.getJSONObject(pos).getJSONArray("districts").getString(i));
                    DisplayListDistrict.add(initial.getJSONObject(pos).getJSONArray("districts").getString(i));
                }
                districtArrayAdapter.notifyDataSetChanged();
              //  Toast.makeText(Sign_Up.this,initial.getJSONObject(pos).getJSONArray("districts").getString(i),Toast.LENGTH_LONG).show();
            }
            //mListState.add(menuItemObject.getString("state"));

    }
    /*public List<HashMap<String,String>> parse(String jsonData){
        JSONArray jsonArray=null;
        JSONObject jsonObject;
        try {
            jsonObject=new JSONObject(jsonData);
            jsonArray=jsonObject.getJSONArray("states");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }*/
}
