package com.example.anticorruptionapp.data;

public class Policy {
    String name;
    String district;

    public Policy(){

    }

    public Policy(String name, String district){
        this.name = name;
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

}