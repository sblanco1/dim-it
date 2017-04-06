package com.att.a56withrice.dimit;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by mr088w on 4/4/2017.
 */

public class ListAdapter extends ArrayAdapter<LightFixture> implements View.OnClickListener{

    private ArrayList<LightFixture> mDataSource;
    private LayoutInflater mInflater;
    Context mContext;

    private static class ViewHolder {

    }

    public ListAdapter(ArrayList<LightFixture> data, Context context) {
        super(context, R.layout.list_item, data);
        this.mDataSource = data;
        this.mContext=context;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        LightFixture dataModel = (LightFixture) object;
        Toast.makeText(this.mContext, dataModel.getName() + " clicked.",Toast.LENGTH_SHORT).show();
    }

    public void flipSmartStatus(View v) {
        int position=(Integer) v.getTag();
        Object object= getItem(position);
        LightFixture dataModel = (LightFixture) object;

        SeekBar lightValue = (SeekBar) v.findViewById(R.id.lightValue);
        lightValue.setEnabled(dataModel.getSmartStatus());
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public LightFixture getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item

        View rowView = mInflater.inflate(R.layout.list_item, parent, false);

        // Get all the display elements from the list_item
        TextView titleTextView = (TextView) rowView.findViewById(R.id.lightTitle);
        ImageView statusIcon = (ImageView) rowView.findViewById(R.id.status_icon);
        SeekBar lightValue = (SeekBar) rowView.findViewById(R.id.lightValue);
        Switch switchValue = (Switch) rowView.findViewById(R.id.switchValue);

        // Set all the values of the UI elements to reflect the lightFixture objects
        LightFixture lightFixture = getItem(position);
        titleTextView.setText(lightFixture.getName());

        if(lightFixture.getLightStatus()) {
            statusIcon.setImageResource(R.drawable.light_bulb_lit);
        }
        else {
            statusIcon.setImageResource(R.drawable.light_bulb_test);
        }
        statusIcon.setOnClickListener(new LightListener(position, statusIcon));


        lightValue.setProgress(lightFixture.getLightValue());
        lightValue.setEnabled(lightFixture.getSmartStatus());


        switchValue.setChecked(lightFixture.getSmartStatus());
        switchValue.setOnClickListener(new SwitchListener(position, lightValue));

        return rowView;
    }

    class LightListener implements View.OnClickListener {

        int position;
        ImageView statusIcon;

        public LightListener(int position, ImageView statusIcon) {
            this.position = position;
            this.statusIcon = statusIcon;
        }

        @Override
        public void onClick(View v) {

            LightFixture lightFixture = getItem(position);
            boolean lightStatus = lightFixture.getLightStatus();

            lightFixture.setLightStatus(!lightStatus);
            // Log.e("ListAdapter", "New Light Status: " + lightFixture.getLightStatus());

            if(lightFixture.getLightStatus()) {
                statusIcon.setImageResource(R.drawable.light_bulb_lit);
            }
            else {
                statusIcon.setImageResource(R.drawable.light_bulb_test);
            }
        }
    }


    class SwitchListener implements View.OnClickListener {

        int position;
        SeekBar lightValue;

        public SwitchListener(int position, SeekBar lightValue) {
            this.position = position;
            this.lightValue = lightValue;
        }

        @Override
        public void onClick(View v) {
            LightFixture lightFixture = getItem(position);
            boolean smartStatus = lightFixture.getSmartStatus();
            lightFixture.setSmartStatus(!smartStatus);
            lightValue.setEnabled(!smartStatus);
        }

    }
}


