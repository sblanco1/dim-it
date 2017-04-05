package com.att.a56withrice.dimit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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


        LightFixture lightFixture = getItem(position);
        titleTextView.setText(lightFixture.getName());


        return rowView;
    }

}
