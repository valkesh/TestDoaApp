package com.testdoaapp.testdoaapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by valkeshpatel on 12/6/17.
 */

@SuppressLint("ValidFragment")
public class DatePicker extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    DatabaseHelper db;

    @SuppressLint("ValidFragment")
    public DatePicker(DatabaseHelper db) {
        System.out.println("===db===="+ db);
        this.db = db;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user

        System.out.println("=========hourOfDay========" + hourOfDay);
        System.out.println("=========minute========" + minute);
        String _time = String.valueOf(hourOfDay) + " " + String.valueOf(minute);

        Contact contact =  new Contact(1, "valkesh", "9687605815", _time, _time);
        if (contact != null) {
            long count =  db.addContact(contact);
            Toast.makeText(getActivity(), "Record added successfuly"+ count , Toast.LENGTH_LONG).show();
        }
    }
}
