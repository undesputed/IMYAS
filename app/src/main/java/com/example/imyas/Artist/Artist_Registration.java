package com.example.imyas.Artist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.imyas.Connection.Connection;
import com.example.imyas.MainActivity;
import com.example.imyas.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Artist_Registration extends AppCompatActivity {


    TextInputEditText firstname,lastname,email,phone,address,password,conpass;
    Button submit,selectprofilephoto;
    RadioButton radioButton,radioButton2,radioButton3;
    ImageView profile_photo;

    private final int IMG_REQUEST = 1;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist__registration);


        profile_photo = findViewById(R.id.profile_photo);

        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        password = findViewById(R.id.password);
        conpass = findViewById(R.id.conpass);


        radioButton = findViewById(R.id.radioButton);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);


        submit = findViewById(R.id.register);
        selectprofilephoto = findViewById(R.id.select_profile_photo);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                inputValidations();


            }
        });



        selectprofilephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    selectImage();

            }
        });





    }


    public void inputValidations()
    {
        firstname.setError(null);
        lastname.setError(null);
        email.setError(null);
        phone.setError(null);
        address.setError(null);
        password.setError(null);
        conpass.setError(null);



        String fname = firstname.getText().toString();
        String lname = lastname.getText().toString();
        String mail = email.getText().toString();
        String phne = phone.getText().toString();
        String add = address.getText().toString();
        String pass = password.getText().toString();
        String cpass = conpass.getText().toString();


        View focusView = null;
        boolean cancel = false;

        if (TextUtils.isEmpty(fname)) {
            firstname.setError("Field Required");
            focusView = firstname;
            cancel = true;
        }
        if (TextUtils.isEmpty(lname)) {
            lastname.setError("Field Required");
            focusView = lastname;
            cancel = true;
        }
        if (TextUtils.isEmpty(mail)) {
            email.setError("Field Required");
            focusView = email;
            cancel = true;
        }
        if (TextUtils.isEmpty(phne)) {
            phone.setError("Field Required");
            focusView = phone;
            cancel = true;
        }
        if (TextUtils.isEmpty(add)) {
            address.setError("Field Required");
            focusView = address;
            cancel = true;
        }
        if (TextUtils.isEmpty(pass)) {
            password.setError("Field Required");
            focusView = password;
            cancel = true;
        }
        if (TextUtils.isEmpty(cpass)) {
            conpass.setError("Field Required");
            focusView = conpass;
            cancel = true;
        }

        if (!pass.equals(cpass)) {
            conpass.setError("Password does not match !");
            focusView = conpass;
            cancel = true;
        }







        if (cancel) {
            // There was an error; don't attempt register and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {


                register_artist(fname,lname,mail,phne,add,pass,cpass);

        }



    }


    //------- artist registration -------------
    public void register_artist(final String arg1, final String arg2, final String arg3, final String arg4, final String arg5, final String arg6, String arg7){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Connection.URL_REG_ARTIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {


                    }
                },
                new Response.ErrorListener  () {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Artist_Registration.this,"No Internet Connection"  ,Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("profile_photo",imageToString(bitmap));
                map.put("fname",arg1);
                map.put("lname",arg2);
                map.put("mail",arg3);
                map.put("phone",arg4);
                map.put("address",arg5);
                map.put("password",arg6);


                return map;
            }


        };

        //adding our stringrequest to queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    //----------------------------------------







    //-------for profile photo-----------------

    private void selectImage(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==IMG_REQUEST && resultCode == RESULT_OK && data != null)
        {

            Uri path = data.getData();
            try {

                    bitmap  = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                    profile_photo.setImageBitmap(bitmap);

            } catch (IOException e)
            {

                e.printStackTrace();

            }

        }



    }

    private String imageToString(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);

    }

    //-----------------------------------------
}
