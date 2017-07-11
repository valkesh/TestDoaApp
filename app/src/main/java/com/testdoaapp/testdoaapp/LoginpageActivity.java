package com.testdoaapp.testdoaapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginpageActivity extends AppCompatActivity {


    EditText input_email, input_password;
    Button btn_login, btn_signup, bt;
    String email, password;
    DatabaseHandler db;
    Cursor cursor;
    Registerdata r;
    public SharedPreferences sp;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);


        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_signup = (Button) findViewById(R.id.btn_signup);

        r = new Registerdata();


        //saveLogin = sharedPreferences.getBoolean("saveLogin", false);
        sp = getSharedPreferences("login", MODE_PRIVATE);


        db = new DatabaseHandler(LoginpageActivity.this, null, null, 1);

        btn_signup.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {

                                              Intent in = new Intent(LoginpageActivity.this, SignupActivity.class);
                                              startActivity(in);
                                          }
                                      }
        );


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = input_email.getText().toString();
                password = input_password.getText().toString();


                r = db.getregister(email, password);
                int id = r.getId();
                System.out.println("====logid=====" + id);
                if (id > 0) {

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("isLogin", true);
                    editor.putInt("userid", id);
                    editor.putString("username",email);
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "login successfully", Toast.LENGTH_LONG).show();

                    Intent in1 = new Intent(LoginpageActivity.this, Fingerprint.class);
//                    in1.putExtra("id", id);
                    startActivity(in1);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Username/Password incorrect", Toast.LENGTH_LONG).show();
                    input_email.setText("");
                    input_password.setText("");
                }


//                    in1.putExtra("email",email);
////


                //  System.out.println("==================log============");
//
//                if()
//                {
//
//
//
//
//
//                    Toast.makeText(getApplicationContext(), "Login sucessfully",Toast.LENGTH_LONG).show();
//
//
//                    Intent in1=new Intent(LoginpageActivity.this,WelcomeHomepageActivity.class);
//
//                    in1.putExtra("email",email);
//
//
//
//
//                    startActivity(in1);
//
//                }
//
//                else{
//                    Toast.makeText(getApplicationContext(), "Username/Password incorrect", Toast.LENGTH_LONG).show();
//                    input_email.setText("");
//                    input_password.setText("");
//                }
//

            }
        });

    }
}
