package com.example.anticorruptionapp.Profile;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.anticorruptionapp.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePage extends AppCompatActivity {

    CircleImageView profile_image , change_image;
    EditText name , email , designation , contact , home_address , home_district , work_address , work_district;
    ImageView done;
    Uri uri , resultUri;
    private static final int GALLERY_INTENT=10;
    ProgressBar progressBar;
    RelativeLayout profile_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final Presenter presenter = new Presenter(ProfilePage.this);
        initViews();
        progressBar.setVisibility(View.VISIBLE);
        presenter.preFillprofile();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model model = new Model();
                String mName = name.getText().toString();
                String mEmail = email.getText().toString();
                String mDesignation = designation.getText().toString();
                String mContact = contact.getText().toString();
                String mHomeAddress = home_address.getText().toString();
                String mHomeDistrict = home_district.getText().toString();
                String mWorkAddress = work_address.getText().toString();
                String mWorkDistrict = work_district.getText().toString();

                model.setName(mName);
                model.setEmail(mEmail);
                model.setDesignation(mDesignation);
                model.setContact(mContact);
                model.setHome_address(mHomeAddress);
                model.setHome_district(mHomeDistrict);
                model.setWork_address(mWorkAddress);
                model.setWork_district(mWorkDistrict);
                model.setImage_uri(resultUri.toString());

                if(!TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mEmail) && !TextUtils.isEmpty(mDesignation) &&
                !TextUtils.isEmpty(mContact) && !TextUtils.isEmpty(mHomeAddress) && !TextUtils.isEmpty(mHomeDistrict)
                && !TextUtils.isEmpty(mWorkAddress) && !TextUtils.isEmpty(mWorkDistrict)){
                    presenter.setProfile(model);
                }
                else{
                    if(TextUtils.isEmpty(mName))
                        name.setError("Please fill your name");
                    else if(TextUtils.isEmpty(mEmail))
                        email.setError("Please fill your email address");
                    else if(TextUtils.isEmpty(mDesignation))
                        designation.setError("Please fill your Designation");
                    else if(TextUtils.isEmpty(mHomeAddress))
                        home_address.setError("Please fill your home address");
                    else if(TextUtils.isEmpty(mHomeDistrict))
                        home_district.setError("Please fill your home district");
                    else if(TextUtils.isEmpty(mWorkAddress))
                        work_address.setError("Please fill your work address");
                    else if(TextUtils.isEmpty(mWorkDistrict))
                        work_district.setError("Please fill your work district");

                }



            }
        });

        change_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intn=new Intent(Intent.ACTION_PICK);
                intn.setType("image/*");
                startActivityForResult(intn,GALLERY_INTENT);
            }
        });

    }

    public void initViews(){
        profile_image = findViewById(R.id.profile_image);
        change_image = findViewById(R.id.change_image);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        designation = findViewById(R.id.designation);
        contact = findViewById(R.id.contact);
        home_address = findViewById(R.id.home_address);
        home_district = findViewById(R.id.home_district);
        work_address = findViewById(R.id.work_address);
        work_district = findViewById(R.id.work_district);
        done = findViewById(R.id.done);
        progressBar = findViewById(R.id.progressBar);
        profile_container = findViewById(R.id.profile_container);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==GALLERY_INTENT&&resultCode==RESULT_OK)
        {
            uri=data.getData();
            CropImage.activity(uri)
                    .setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(4,3)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                Glide.with(ProfilePage.this).load(resultUri).override(130,130).centerCrop().into(profile_image);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}
