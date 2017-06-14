package com.testdoaapp.testdoaapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    DatabaseHelper db;
    ListView listData;
    LinearLayout lnButton;
    ContactAdapter contactAdapter;
    ArrayList<Contact> contacts = new ArrayList<>();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    lnButton.setVisibility(View.VISIBLE);
                    listData.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_dashboard:
                    listData.setVisibility(View.VISIBLE);
                    lnButton.setVisibility(View.GONE);
                    contacts = db.getAllContacts();
                    contactAdapter = new ContactAdapter(contacts, MainActivity.this);
                    listData.setAdapter(contactAdapter);

                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
        mTextMessage = (TextView) findViewById(R.id.message);
        lnButton = (LinearLayout) findViewById(R.id.lnButton);
        listData = (ListView) findViewById(R.id.listData);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        contacts = db.getAllContacts();
        contactAdapter = new ContactAdapter(contacts, this);
        listData.setAdapter(contactAdapter);
    }


    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new DatePicker(db);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

}
