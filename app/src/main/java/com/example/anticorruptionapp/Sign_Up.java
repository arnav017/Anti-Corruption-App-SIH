package com.example.anticorruptionapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
 //   public  static  int s_i=0,d_i=0;
    ArrayList<String> mListState,mListDistrict;
    MaterialEditText state,district,contact,name,address;
    Button button;
    FirebaseDatabase database;
    DatabaseReference table_user;
    SharedPreferences.Editor editor;
   // StateListAdapter stateAdapter,districtAdapter;
    ExpandableHeightListView mListStateView,mListDistrictView;
    ArrayList<String> DisplayList,DisplayListDistrict;
    ArrayAdapter<String> stringArrayAdapter,districtArrayAdapter;
    AlertDialog.Builder dlgAlertDistrict,dlgAlertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);
        FirebaseApp.initializeApp(this);
        state = findViewById(R.id.edtState);
        district=findViewById(R.id.edtDistrict);
        //contact=findViewById(R.id.edtPhoneNo);
        name=findViewById(R.id.edtName);
        //address=findViewById(R.id.edtAddress);
        mListStateView = findViewById(R.id.state_list);
        mListDistrictView=findViewById(R.id.district_list);
        database=FirebaseDatabase.getInstance();
        editor = getSharedPreferences("Contact", MODE_PRIVATE).edit();
        table_user=database.getReference("users").child("profiles");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mListStateView.setNestedScrollingEnabled(true);
        }
        mListDistrict=new ArrayList<>();
        DisplayListDistrict=new ArrayList<>();
        mListState = new ArrayList<String>();
        DisplayList = new ArrayList<>();
        jsonString = loadJSONFromAsset();
        button=findViewById(R.id.btnSignUp);
        try {
            fillArrayList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // stateAdapter=new StateListAdapter(this,R.layout.state_list_view,mListState);
        //Toast.makeText(this,mListState.get(4),Toast.LENGTH_LONG).show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Model model=new Model();
                if(name.getText().toString().equals("")){
                    name.setError("Name not filled");
                    name.requestFocus();
                    return;
                }
                else
                model.setName(name.getText().toString());
                if(contact.getText().toString().equals("")){
                    contact.setError("Contact not filles");
                    contact.requestFocus();
                    return;
                }
                else{
                model.setContact(contact.getText().toString());
                editor.putString("phone", contact.getText().toString());
                }
                if(address.getText().toString().equals("")){
                    address.setError("Address not filled");
                    address.requestFocus();
                    return;
                }
                else
                model.setHome_address(address.getText().toString());
                if(state.getText().toString().equals("")){
                    state.setError("State not filled or selected");
                    state.requestFocus();
                    return;
                }
                else {
                    model.setState(state.getText().toString());
                }
                if(district.getText().toString().equals("")){
                    district.setError("District not filled or selected");
                    state.requestFocus();
                    return;
                }
                else{
                    model.setHome_district(district.getText().toString());
                }
               // model.setHome_district(district.getText().toString());
               // table_user.child(contact.getText().toString()).setValue(model);
                final ProgressDialog mDialog=new ProgressDialog(Sign_Up.this);
                mDialog.setMessage("Please Waiting...");
                mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(contact.getText().toString())){
                            mDialog.dismiss();
                            Toast.makeText(Sign_Up.this,"User already registered",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            mDialog.dismiss();
                            //Model user=new Model(name.getText().toString(),rollno.getText().toString(),branch.getText().toString(),phone.getText().toString());
                            //table_user.setValue(user);
                            table_user.child(contact.getText().toString()).setValue(model);
                            FirebaseAuth.getInstance().signOut();
                            Toast.makeText(Sign_Up.this,"Sign Up Successfully",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Sign_Up.this,SignIn.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, DisplayList);
        mListStateView.setAdapter(stringArrayAdapter);
        districtArrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,DisplayListDistrict);
        mListDistrictView.setAdapter(districtArrayAdapter);
        mListStateView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               String s="";
               //s_i=1;
               s=(String)adapterView.getItemAtPosition(i);
               state.setText(s);
               mListDistrict.clear();
               DisplayListDistrict.clear();
               districtArrayAdapter.notifyDataSetChanged();
                try {
                    fillDistrictList(s);
                    //Toast.makeText(Sign_Up.this,(String) adapterView.getItemAtPosition(i),Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                DisplayList.clear();
                stringArrayAdapter.notifyDataSetChanged();
                mListStateView.setVisibility(View.GONE);
                mListDistrictView.setVisibility(View.VISIBLE);
            }
        });
        mListDistrictView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s="";
                //d_i=1;
                s=(String)adapterView.getItemAtPosition(i);
                district.setText(s);
                DisplayListDistrict.clear();
                districtArrayAdapter.notifyDataSetChanged();
                mListDistrictView.setVisibility(View.GONE);
            }
        });
        state.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               //s_i=0;
                mListStateView.setVisibility(View.VISIBLE);
                mListStateView.setExpanded(true);
                if(charSequence.toString().equals("")){
                    DisplayListDistrict.clear();
                    mListDistrict.clear();
                    districtArrayAdapter.notifyDataSetChanged();
                    DisplayListDistrict.clear();
                }
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
               // d_i=0;
                mListDistrictView.setVisibility(View.VISIBLE);
                mListDistrictView.setExpanded(true);
                if(state.getText().toString().equals("")){
                    state.setError("State is not filled or selected");
                    state.requestFocus();
                    return;
                }
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
