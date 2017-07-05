package com.testdoaapp.testdoaapp;

/**
 * Created by valkeshpatel on 12/6/17.
 */

public class Contact {

    //private variables
    int _id;
    String _name;
    String _phone_number;
    String _check_in_time;
    String _check_out_time;
    String _date_time;
    int _isInTime;
    String longtitude;
    String latilude;


    // Empty constructor
    public Contact() {

    }

    // constructor
    public Contact(int id, String name, String _phone_number, String _check_in_time, String _check_out_time, int _isInTime, String latilude, String longtitude) {
        this._id = id;
        this._name = name;
        this._phone_number = _phone_number;
        this._check_in_time = _check_in_time;
        this._check_out_time = _check_out_time;
        this._isInTime = _isInTime;
        this.longtitude = longtitude;
        this.latilude = latilude;
    }

    // constructor
    public Contact(String name, String _phone_number) {
        this._name = name;
        this._phone_number = _phone_number;
    }

    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int id) {
        this._id = id;
    }

    // getting name
    public String getName() {
        return this._name;
    }

    // setting name
    public void setName(String name) {
        this._name = name;
    }

    // getting phone number
    public String getPhoneNumber() {
        return this._phone_number;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number) {
        this._phone_number = phone_number;
    }


    public String get_check_in_time() {
        return _check_in_time;
    }

    public void set_check_in_time(String _check_in_time) {
        this._check_in_time = _check_in_time;
    }

    public String get_check_out_time() {
        return _check_out_time;
    }

    public void set_check_out_time(String _check_out_time) {
        this._check_out_time = _check_out_time;
    }


    public String get_date_time() {
        return _date_time;
    }

    public void set_date_time(String _date_time) {
        this._date_time = _date_time;
    }

    public int is_isInTime() {
        return _isInTime;
    }

    public void set_isInTime(int _isInTime) {
        this._isInTime = _isInTime;
    }


    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getLatilude() {
        return latilude;
    }

    public void setLatilude(String latilude) {
        this.latilude = latilude;
    }
}