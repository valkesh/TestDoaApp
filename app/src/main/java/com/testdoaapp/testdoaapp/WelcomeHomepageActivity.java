package com.testdoaapp.testdoaapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeHomepageActivity extends AppCompatActivity {

    TextView tvUsername1, tvPhone1;
    DatabaseHandler db;
    Button btDelete, btUpdate, btLogout;
    EditText etEmail, edPhone;
    Registerdata registerdata;


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_homepage);
        Bundle bundle = getIntent().getExtras();


        int userid = bundle.getInt("id");
        db = new DatabaseHandler(WelcomeHomepageActivity.this, null, null, 1);

        tvUsername1 = (TextView) findViewById(R.id.tvUsername1);
        tvPhone1 = (TextView) findViewById(R.id.tvPhone1);
        btDelete = (Button) findViewById(R.id.btDelete);
        btUpdate = (Button) findViewById(R.id.btUpdate);
        btLogout = (Button) findViewById(R.id.btLogout);


        System.out.print("========id111======" + userid);
        registerdata = new Registerdata();
        if (userid != 0) {
            registerdata = db.getData(userid);

            tvUsername1.setText(registerdata.getEmail_id());
            tvPhone1.setText(registerdata.getMobile_number());

        }


        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //1....
                // Create custom dialog object
                final Dialog dialog = new Dialog(WelcomeHomepageActivity.this);
                // Include dialog.xml file
                dialog.setContentView(R.layout.dialog_signin);

                // Set dialog title
                dialog.setTitle("Custom Dialog");

                // set values for custom dialog components - text, image and button
                final EditText phoneno1 = (EditText) dialog.findViewById(R.id.phoneno);


                final ImageView image = (ImageView) dialog.findViewById(R.id.image);
                image.setImageResource(R.mipmap.ic_launcher);

                dialog.show();

                Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if decline button is clicked, close the custom dialog
                dialogButtonOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//..
                        final String data = phoneno1.getText().toString();

                        //here,first i'm settting my phone no to registerdata,and den i'll update it in to database.
                        registerdata.setMobile_number(data);
//

                        int id = registerdata.getId();
                        System.out.println("=========id======" + id);

                        db = new DatabaseHandler(WelcomeHomepageActivity.this, null, null, 1);
                        db.update_byID(id, data);

                        Toast.makeText(getApplicationContext(), "update sucessfully", Toast.LENGTH_LONG).show();
//last correction

                        String phn_no = registerdata.getMobile_number().toString();
                        System.out.println("=======phoneno========" + phn_no);

                        tvPhone1.setText(phn_no);


                        dialog.dismiss();
                    }
                });

                Button btCancle = (Button) dialog.findViewById(R.id.btCancle);

                btCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


            }

        });


        final String mail = tvUsername1.getText().toString();

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //db.delete_byID(mail);

                db.removeSingleContact(mail);

                Toast.makeText(getApplicationContext(), "Delete sucessfully", Toast.LENGTH_LONG).show();

                tvUsername1.setText(" ");
                tvPhone1.setText(" ");
            }
        });
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor e = sp.edit();
                e.clear();
                e.commit();

                startActivity(new Intent(WelcomeHomepageActivity.this, LoginpageActivity.class));
                finish();
            }
        });
    }
}
