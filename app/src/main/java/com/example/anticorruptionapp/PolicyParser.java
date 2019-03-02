package com.example.anticorruptionapp;

import android.util.Log;

import com.example.anticorruptionapp.data.Policy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class PolicyParser {

    public ArrayList<Policy> parsePolicies(Map<String,Object> data){
        ArrayList<Policy> result = new ArrayList<>();
        for (Map.Entry<String, Object> entry : data.entrySet()){
            Log.w("xxx", "length"+result.size());
                Policy aPolicy = new Policy();
            //Get user map
            Map node = (Map) entry.getValue();

            aPolicy.setName((String)node.get("name"));
            aPolicy.setDistrict((String)node.get("location"));

             result.add(aPolicy);
        }
        return result;
    }


    //utils
    static String getStringByKey(JSONObject jsonObject,String key){
        if (jsonObject != null && jsonObject.has(key)) {
            try {
                return jsonObject.getString(key);
            } catch (JSONException e) {
                //TODO(2) handle all exceptions everywhere
            }
        } else {
            return "";
        }
        return "";
    }

}
