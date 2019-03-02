package com.example.anticorruptionapp.Profile;

public class Model {
    private String key , contact , name , email , designation , home_address , home_district , work_address , work_district ,
            home_state , work_state ;
    private String image_uri;

    public String getKey() {
        return key;
    }

    public String getHome_state() {
        return home_state;
    }

    public void setHome_state(String home_state) {
        this.home_state = home_state;
    }

    public String getWork_state() {
        return work_state;
    }

    public void setWork_state(String work_state) {
        this.work_state = work_state;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getHome_address() {
        return home_address;
    }

    public void setHome_address(String home_address) {
        this.home_address = home_address;
    }

    public String getHome_district() {
        return home_district;
    }

    public void setHome_district(String home_district) {
        this.home_district = home_district;
    }

    public String getWork_address() {
        return work_address;
    }

    public void setWork_address(String work_address) {
        this.work_address = work_address;
    }

    public String getWork_district() {
        return work_district;
    }

    public void setWork_district(String work_district) {
        this.work_district = work_district;
    }

    public String getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }
}
