package com.example.debjit.signuplog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import static android.widget.Toast.LENGTH_SHORT;

public class SignupActivity extends AppCompatActivity  {
    Button signupButton, asubutton;
    ImageView calenderButton;
    ImageView profilepic,backbtn;
    EditText dob, firstname, lastname, emailid, password;
    RadioGroup radioGroup;
    RadioButton gender;
    DatePickerDialog datePickerDialog;
    private int mYear, mMonth, mDay;
    private int PICK_IMAGE_REQUEST = 1;
    private int IMAGE_CAPTURE = 1;
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    ImageView imageView;
    private String userChoosenTask;
    Bitmap bm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupButton = (Button) findViewById(R.id.signup);
        myListner();
        profilepic = (ImageView) findViewById(R.id.dp);
        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                selectImage();
            }
        });
        dob = (EditText) findViewById(R.id.dob1);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        emailid = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        calenderButton = (ImageView) findViewById(R.id.dobbtn);
        asubutton = (Button) findViewById(R.id.asu);
        asubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asubutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent3 = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent3);
                    }
                });
            }
        });
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        //RadioButton rb=findViewById(radioGroup.getCheckedRadioButtonId());
        //rb.getText();
        backbtn = (ImageView) findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        calenderButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                int mMonth = c.get(Calendar.MONTH);
                int mYear = c.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(SignupActivity.this, new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        dob.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);
                    }
                }, mDay, mMonth, mYear);
                datePickerDialog.show();
            }
        });
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calenderButton.performClick();
            }
        });
        Intent intent2 = getIntent();
    }

        public void myListner(){
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rgs_id = radioGroup.getCheckedRadioButtonId();
                findViewById(rgs_id);

                gender = (RadioButton) findViewById(rgs_id);
                if (validationSuccess()) {
                    Intent intent3 = new Intent(SignupActivity.this, UserprofileActivity.class);
                    startActivity(intent3);
                } else {

                }

            }

            private boolean validationSuccess() {
                if (radioGroup.getCheckedRadioButtonId() <= 0) {
                    Toast.makeText(getApplicationContext(), "Please select Gender", LENGTH_SHORT).show();
                    return false;
                }

                String email = emailid.getText().toString();
                String fname = firstname.getText().toString();
                String lname = lastname.getText().toString();
                String ps = password.getText().toString();
                String dateofbirth = dob.getText().toString();
                String sex = gender.getText().toString();

                SharedPreferences sharedPreferences = getSharedPreferences("sd.avalgate@gmail.com", MODE_PRIVATE);


                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("emailid", email);
                editor.putString("firstname", fname);
                editor.putString("lastname", lname);
                editor.putString("password", ps);
                editor.putString("dob", dateofbirth);
                editor.putString("gender",sex );
                editor.commit();


                if (TextUtils.isEmpty(fname))
                    Toast.makeText(SignupActivity.this, "Please enter your first name", LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(lname))
                    Toast.makeText(SignupActivity.this, "Please enter your last name", LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(ps)||ps.length()<8)
                    Toast.makeText(SignupActivity.this, "Password minimum 8 characters", LENGTH_SHORT).show();
                else {
                    Boolean b = isValidEmail(email);
                    if (b) {
                        Toast.makeText(SignupActivity.this, "Registration Successful", LENGTH_SHORT).show();
                        Intent intent3;
                        intent3 = new Intent(SignupActivity.this,UserprofileActivity.class);
                        intent3.putExtra("firstname",fname);
                        intent3.putExtra("lastname",lname);
                        intent3.putExtra("email",email);
                        intent3.putExtra("gender",sex);
                        intent3.putExtra("dob",dateofbirth);
                        intent3.putExtra("BitmapImage", bm);
                        startActivity(intent3);
                    } else
                        Toast.makeText(SignupActivity.this, "Enter Valid Email", LENGTH_SHORT).show();
                }
                return false;
            }


            public final Boolean isValidEmail(CharSequence target) {
                if (TextUtils.isEmpty(target)) {
                    Toast.makeText(SignupActivity.this, "Enter valid email", LENGTH_SHORT).show();
                    return false;
                } else {
                    return Patterns.EMAIL_ADDRESS.matcher(target).matches();
                }

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Camera"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from gallery"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {

        final CharSequence[] items = {"Camera", "Choose from gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(SignupActivity.this);

                if (items[item].equals("Camera")) {
                    userChoosenTask = "Camera";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from gallery")) {
                    userChoosenTask = "Choose from gallery";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        Bitmap bitmap = BitmapFactory.decodeFile(destination.getAbsolutePath());


        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bm = thumbnail;
        profilepic.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {


        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        profilepic.setImageBitmap(bm);
    }
}

