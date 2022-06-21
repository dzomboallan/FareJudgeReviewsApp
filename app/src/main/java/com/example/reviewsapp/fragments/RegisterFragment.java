package com.example.reviewsapp.fragments;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.reviewsapp.R;
import com.example.reviewsapp.activites.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {

    EditText name, email, password, phone;
    Button register;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    FirebaseFirestore firestore;
    String userID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        name = (EditText) view.findViewById(R.id.fullName);
        email = (EditText) view.findViewById(R.id.email);
        password = (EditText) view.findViewById(R.id.password);
        phone = (EditText) view.findViewById(R.id.phone);
        register = (Button) view.findViewById(R.id.registerBtn);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        progressBar = view.findViewById(R.id.progressBar);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Uname = name.getText().toString().trim();
                String uEmail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String tel = phone.getText().toString().trim();

                if (TextUtils.isEmpty(Uname)) {
                    name.setError("Name field is required");
                    return;
                }
                if (TextUtils.isEmpty(uEmail)){
                    email.setError("Email field is required");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(uEmail).matches()){
                    email.setError("Please provide a valid email");
                    email.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    password.setError("Email field is required");
                    return;
                }
                if (pass.length() < 6){
                    password.setError("Password length must be greater than 6 letters");
                    return;
                }

                if (TextUtils.isEmpty(tel)){
                    phone.setError("Phone field is required");
                    return;
                }
                if (tel.length() < 10){
                    phone.setError("Incomplete phone number");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //Register the user
                firebaseAuth.createUserWithEmailAndPassword(uEmail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            //send verification email

                            FirebaseUser fuser = firebaseAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext(), "Verification email sent", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: Verification email not sent"+ e.getMessage());
                                }
                            });

                            Toast.makeText(getContext(), "User created successfully", Toast.LENGTH_SHORT).show();
                            userID = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firestore.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("Full Name", Uname);
                            user.put("Email", uEmail);
                            user.put("Tel", tel);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "onSuccess: user profile is created for "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: "+ e.getMessage());
                                }
                            });
                            progressBar.setVisibility(View.GONE);
                        }else{
                            Toast.makeText(getContext(), "Error ! "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        return view;
    }
}