package com.example.littlecafeshop;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sign_Up extends AppCompatActivity {
    Button backButton;
    ImageView imageView;
    Button signupbutton;
    EditText fullname;
    EditText email;
    EditText password;
    EditText confirmpassword;
    private static int RESULT_LOAD_IMAGE;
    private DatabaseReference mDatabase;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();

        signupbutton = findViewById(R.id.signupbtn);
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpasword);


        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = fullname.getText().toString().trim();
                String mail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();

                boolean error = false;

                if (name.equals("")){
                    fullname.setError("Full name is reqiured");
                    error = true;
                }
                if (pass.length() < 4) {
                    password.setError("password minimum contain 4 character");
                    password.requestFocus();
                    error = true;
                }
                if (pass.equals("")) {
                    password.setError("Password is required");
                       password.requestFocus();
                    error = true;
                }
                if (!(Patterns.EMAIL_ADDRESS.matcher(mail).matches())) {
                    email.setError("please enter valid email address");
                            email.requestFocus();
                    error = true;
                }
                if (mail.equals("")) {
                    email.setError("Email is required");
                            email.requestFocus();
                    error = true;
                }

                if (!(confirmpassword.getText().toString().equals(pass))){
                    confirmpassword.setError("Password doese not match");
                    confirmpassword.requestFocus();
                    error = true;
//                    return;
                }
                if (!error){

                    insertUser();

                }
            }
        });

        backButton = findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] selectedImage = {"Camera", "Galery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Sign_Up.this);
                builder.setTitle("Upload Image Option");
                builder.setItems(selectedImage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexOption) {
                        if(indexOption == 1) {
                            Intent i = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(i, RESULT_LOAD_IMAGE);
                        } else if(indexOption == 0){
                            Intent imageLoader = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(imageLoader, 1);
                        }
                    }
                });
                builder.show();
            }
        });
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                imageView.setImageURI(selectedImage);
            }
            if (resultCode == RESULT_OK){
                switch (requestCode) {
                    case 1:
                        imageView.setImageBitmap((Bitmap) data.getExtras().get("data"));  //use this if you trying to set image on Imageview
                        break;
                }
            }
    }

    private void insertUser()
    {

        final String name = fullname.getText().toString().trim();
        final String mail = email.getText().toString().trim();
        final String pass = password.getText().toString().trim();


        final String em = mail.replace(".",",");

        mDatabase.child("users").child(em).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    email.setError("Email Existed!!!");
                }
                else
                {
                    mDatabase.child("users").child(em).child("Username").setValue(name);
                    mDatabase.child("users").child(em).child("Password").setValue(pass);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //signup

    public class User {

        public String username;
        public  String password;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String username, String email, String password) {
            this.username = username;
            this.password  = password;
        }

    }
    private void writeNewUser(String name, String email,String password) {
        User user = new User(name, email, password);

        mDatabase.child("users").child(email).setValue(user);
    }
}





