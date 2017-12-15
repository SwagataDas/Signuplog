package com.example.debjit.signuplog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserprofileActivity extends AppCompatActivity {

    TextView t1,t2,t3,t4,t5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        t1 = (TextView) findViewById(R.id.firstname);
        t2 = (TextView) findViewById(R.id.lastname);
        t3 = (TextView) findViewById(R.id.email);
        t4 = (TextView) findViewById(R.id.gender);
        t5 = (TextView) findViewById(R.id.dob);

        Intent intent3 = getIntent();
        Intent intent4 = getIntent();

        String firstname = intent3.getStringExtra("firstname");
        String lastname = intent3.getStringExtra("lastname");
        String email = intent3.getStringExtra("email");
        String gender = intent3.getStringExtra("gender");
        String dob = intent3.getStringExtra("dob");

        t1.append(firstname);
        t2.append(lastname);
        t3.append(email);
        t4.append(gender);
        t5.append(dob);
    }
}