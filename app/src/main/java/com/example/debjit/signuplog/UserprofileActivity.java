package com.example.debjit.signuplog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class UserprofileActivity extends AppCompatActivity {

    EditText t1,t2,t3,t4,t5;
    ImageView imageView;
    String pref1,pref2,pref3,pref4,pref5,pref6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        t1 = (EditText) findViewById(R.id.firstname);
        t2 = (EditText) findViewById(R.id.lastname);
        t3 = (EditText) findViewById(R.id.email);
        t4 = (EditText) findViewById(R.id.gender);
        t5 = (EditText) findViewById(R.id.dob);
        imageView = (ImageView) findViewById(R.id.dp);
        Uri imageUri = getIntent().getData();
        SharedPreferences sharedPreferences = getSharedPreferences("sd.avalgate@gmail.com", MODE_PRIVATE);
        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        pref1 = sharedPreferences.getString("firstname", "");
        pref2 = sharedPreferences.getString("lastname","");
        pref3 = sharedPreferences.getString("emailid","");
        pref5 = sharedPreferences.getString("gender","");
        pref6 = sharedPreferences.getString("dob","");

        Intent intent3 = getIntent();

        Bitmap bitmapImage  = (Bitmap) intent3.getParcelableExtra("BitmapImage");
        //Intent intent4 = getIntent();



        t1.append(pref1);
        t2.append(pref2);
        t3.append(pref3);
        t4.append(pref5);
        //imageView.setImageURI(imageUri);
        t5.append(pref6);
    }
}