package com.example.littlecafeshop;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingFragment extends Fragment {

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    Button saveButton;
    TextView nameEditText, titleEditText, emailEditText, passwordEditText, locationEditText;

    Context mContext;

    String email;

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        email = getArguments().get("email").toString().replace(".",",");

        nameEditText = view.findViewById(R.id.editname);
        titleEditText = view.findViewById(R.id.edittittle);
        emailEditText = view.findViewById(R.id.editemail);
        passwordEditText = view.findViewById(R.id.editpassword);
        locationEditText = view.findViewById(R.id.editlocation);

        saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("users").child(email).child("Title").setValue(titleEditText.getText().toString());
                mDatabase.child("users").child(email).child("Location").setValue(locationEditText.getText().toString());
                mDatabase.child("users").child(email).child("Username").setValue(nameEditText.getText().toString());

                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle("Saved");
                alert.setMessage("Restart App to see changed");
                alert.create();
                alert.show();
            }
        });

        getUserData();

        return view;
    }

    private void getUserData() {

        mDatabase.child("users").child(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    try {

                        String pass = dataSnapshot.child("Password").getValue().toString();
                        String userName = dataSnapshot.child("Username").getValue().toString();
                        String title = dataSnapshot.child("Title").getValue().toString();
                        String location = dataSnapshot.child("Location").getValue().toString();

                        passwordEditText.setText(pass);
                        nameEditText.setText(userName);
                        titleEditText.setText(title);
                        locationEditText.setText(location);
                        emailEditText.setText(email.replace(",","."));

                    } catch (Exception e) {

                        String password = dataSnapshot.child("Password").getValue().toString();
                        String username = dataSnapshot.child("Username").getValue().toString();

                        passwordEditText.setText(password);
                        nameEditText.setText(username);
                        titleEditText.setText("");
                        locationEditText.setText("");
                        emailEditText.setText(email.replace(",","."));

                    }
                } else {

                    nameEditText.setText("");
                    titleEditText.setText("");
                    passwordEditText.setText("");
                    emailEditText.setText(email.replace(",","."));
                    locationEditText.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
