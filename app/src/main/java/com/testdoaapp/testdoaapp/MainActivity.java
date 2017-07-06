package com.testdoaapp.testdoaapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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


    public void getPermit(boolean is_in) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

                return;
            } else {
                checkGps(is_in);
            }
        } else {
            checkGps(is_in);
        }
    }


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


    public void inTime(View v) {
//        DialogFragment newFragment = new DatePicker(db);
//        newFragment.show(getSupportFragmentManager(), "timePicker");
        getPermit(true);
    }

    public void outTime(View v) {
//        DialogFragment newFragment = new DatePicker(db);
//        newFragment.show(getSupportFragmentManager(), "timePicker");
        getPermit(false);
    }

    String address = "";

    public void checkGps(boolean is_in) {
        GPSTracker gps = new GPSTracker(MainActivity.this);
        if (gps.canGetLocation()) { // gps enabled} // return boolean true/false

            gps.getLatitude(); // returns latitude
            gps.getLongitude(); // returns longitude


            System.out.println("====" + gps.getLatitude());
            System.out.println("====" + gps.getLongitude());


            address = getCompleteAddressString(gps.getLatitude(), gps.getLongitude());

            System.out.println("address" + address);
            if (is_in) {
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                Contact contact = new Contact(1, "valkesh", "9687605815", currentDateTimeString, currentDateTimeString, 1, gps.getLatitude() + "", gps.getLongitude() + "", address);
                if (contact != null) {
                    long count = db.addContact(contact);
                    Toast.makeText(MainActivity.this, "Record added successfuly  " + count, Toast.LENGTH_LONG).show();
                }
            } else if (!is_in) {
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                Contact contact = new Contact(1, "valkesh", "9687605815", currentDateTimeString, currentDateTimeString, 2, gps.getLatitude() + "", gps.getLongitude() + "", address);
                if (contact != null) {
                    long count = db.addContact(contact);
                    Toast.makeText(MainActivity.this, "Record added successfuly  " + count, Toast.LENGTH_LONG).show();
                }
            }

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }


    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.i("My Current loction address", "" + strReturnedAddress.toString());
            } else {
                Log.i("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }
}

