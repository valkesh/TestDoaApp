package com.testdoaapp.testdoaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by valkeshpatel on 14/6/17.
 */


public class ContactAdapter extends BaseAdapter {

    private ArrayList<Contact> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView textName;
        TextView textCheckInTime;
        TextView textCheckOutTime;
        TextView textDateTime, textLocationAddress;
    }

    public ContactAdapter(ArrayList<Contact> data, Context context) {
        this.dataSet = data;
        this.mContext = context;

    }

    private int lastPosition = -1;

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Contact dataModel = (Contact) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.adapter_raw, parent, false);
            viewHolder.textName = (TextView) convertView.findViewById(R.id.textName);
            viewHolder.textCheckInTime = (TextView) convertView.findViewById(R.id.textCheckInTime);
            viewHolder.textCheckOutTime = (TextView) convertView.findViewById(R.id.textCheckOutTime);
            viewHolder.textDateTime = (TextView) convertView.findViewById(R.id.textDateTime);
            viewHolder.textLocationAddress = (TextView) convertView.findViewById(R.id.textLocationAddress);
            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.textName.setText(dataModel.getName());

        viewHolder.textDateTime.setText(dataModel.get_date_time());
        viewHolder.textLocationAddress.setText(dataModel.getAddress());
        if (dataModel.is_isInTime() == 1) {
            viewHolder.textCheckInTime.setText(dataModel.get_check_in_time());
        } else if (dataModel.is_isInTime() == 2) {
            viewHolder.textCheckOutTime.setText(dataModel.get_check_out_time());
        }

        // Return the completed view to render on screen
        return convertView;
    }
}