package com.example.debjit.signuplog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class LoginActivity extends AppCompatActivity {
    private EditText mail,password;
    private Button login;
    private ImageView backbtn;
    private CharSequence target;
    String pref1,pref2,pref3,pref4,pref5,pref6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mail = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        backbtn = (ImageView) findViewById(R.id.backbtn);



        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mail.getText().toString();
                String ps = password.getText().toString();



                if (TextUtils.isEmpty(ps)||ps.length()<8)
                    Toast.makeText(LoginActivity.this, "Password Minimum 8 characters", LENGTH_SHORT).show();
                else {
                    Boolean b = isValidEmail(email);
                    if (b) {
                        Toast.makeText(LoginActivity.this, "Login Successfully", LENGTH_SHORT).show();
                        Intent intent3;
                        intent3 = new Intent(LoginActivity.this,UserprofileActivity.class);
                        startActivity(intent3);
                    } else
                        Toast.makeText(LoginActivity.this, "Enter Valid Email", LENGTH_SHORT).show();
                }
            }


            public final Boolean isValidEmail(CharSequence target) {
                if (TextUtils.isEmpty(target)) {
                    Toast.makeText(LoginActivity.this, "Enter valid email", LENGTH_SHORT).show();
                    return false;
                } else {
                    return Patterns.EMAIL_ADDRESS.matcher(target).matches();
                }
            }
        });
        Intent intent3 = getIntent();
    }
}
