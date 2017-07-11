package com.testdoaapp.testdoaapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    EditText input_email1, input_password, input_conpassword, input_phonenho;
    Button btn_submit;

    String email, password, conpassword, phoneno;
    int data;
    DatabaseHandler db;
    Registerdata reg;
    SharedPreferences sp;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        sp = getSharedPreferences("login", MODE_PRIVATE);

        input_email1 = (EditText) findViewById(R.id.input_email1);
        input_password = (EditText) findViewById(R.id.input_password);
        input_conpassword = (EditText) findViewById(R.id.input_conpassword);
        input_phonenho = (EditText) findViewById(R.id.input_phonenho);
        btn_submit = (Button) findViewById(R.id.btn_submit);


        db = new DatabaseHandler(SignupActivity.this, null, null, 1);
        reg = new Registerdata();


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = input_email1.getText().toString();
                password = input_password.getText().toString();
                conpassword = input_conpassword.getText().toString();
                phoneno = input_phonenho.getText().toString();
                if (conpassword.equals(password)) {
                    reg.setEmail_id(email);
                    reg.setMobile_number(phoneno);
                    reg.setPassword(password);
                    if (!db.isRegistered(email)) {
                        reg = db.addregister(reg);
                        int id = reg.getId();
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean("isLogin", true);
                        editor.putInt("userid", id);
                        editor.putString("username",reg.getEmail_id());
                        editor.commit();
                        Toast.makeText(getApplicationContext(), "Registered sucessfully", Toast.LENGTH_LONG).show();
                        Intent in3 = new Intent(SignupActivity.this, Fingerprint.class);
                        data = reg.getId();
//                        System.out.println("=====id=====" + data);
//                        in3.putExtra("id", data);
                        startActivity(in3);
                    } else {
                        Toast.makeText(getApplicationContext(), "Email already Registered.", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "please try again", Toast.LENGTH_LONG).show();
                    input_email1.setText("");
                    input_phonenho.setText("");
                    input_password.setText("");
                    input_conpassword.setText("");
                }
            }
        });

    }
}
