package com.example.anticorruptionapp;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.concurrent.TimeUnit;

public class SignIn extends AppCompatActivity {

    MaterialEditText phone,otp;
    String codeSent;
    Button btnSignIn,btnVerificationCode;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase database;
    DatabaseReference table_user;
    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseUser!=null){
            Toast.makeText(this,"Already Login",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,"Not Login",Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        phone=findViewById(R.id.edtPhone);
        otp=findViewById(R.id.edtOtp);
        btnSignIn=findViewById(R.id.btnSignIn);
        btnVerificationCode=findViewById(R.id.buttonGetVerificationCode);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        database=FirebaseDatabase.getInstance();
        table_user=database.getReference("users").child("profiles");
        btnVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog=new ProgressDialog(SignIn.this);
                mDialog.setMessage("Please Waiting...");
                mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(phone.getText().toString())) {
                            mDialog.dismiss();
                            //  Toast.makeText(SignIn.this, "User Found", Toast.LENGTH_SHORT).show();
                            sendVerificationCode();
                            Model user = dataSnapshot.child(phone.getText().toString()).getValue(Model.class);
                           /* if (user.getPassword().equals(edtPassword.getText().toString())) {
                                //Intent intent=new Intent(SignIn.this,.class);
                                Toast.makeText(SignIn.this, "Sign In successfully", Toast.LENGTH_SHORT).show();
                                //startActivity(intent);
                            } else {
                                Toast.makeText(SignIn.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                            }*/
                        }
                        else{
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this,"Not a registered User",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifySignInCode();
            }
        });
    }
    private void verifySignInCode(){
        String code = otp.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //here you can open new activity
                            Toast.makeText(getApplicationContext(),
                                    "Login Successfull", Toast.LENGTH_LONG).show();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(),
                                        "Incorrect Verification Code ", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private void sendVerificationCode(){

        String contact = phone.getText().toString();

        if(contact.isEmpty()){
            phone.setError("Phone number is required");
            phone.requestFocus();
            return;
        }

        if(contact.length() < 10 ){
            phone.setError("Please enter a valid phone");
            phone.requestFocus();
            return;
        }


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+contact,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(SignIn.this,"Error"+e,Toast.LENGTH_LONG).show();
            if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                // ...
                Toast.makeText(SignIn.this,"Daily quota for messages exceeded",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            codeSent = s;
        }
    };
}
