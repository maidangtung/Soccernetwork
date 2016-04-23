package com.maidangtung.soccernetwork.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.maidangtung.soccernetwork.R;
import com.maidangtung.soccernetwork.models.Field;

import java.util.ArrayList;

/**
 * Created by Ngu on 5/25/2015.
 * To-do: filterable?
 * user getItem(position)
 */
public class ListFieldAdapter extends BaseAdapter implements Filterable {

    private Context mContext;
    private ArrayList<Field> mFields;


    public ListFieldAdapter(Context context, ArrayList<Field> fields) {
        mContext = context;
        mFields = fields;
    }

    @Override
    public int getCount() {
        return mFields.size();
    }


    @Override
    public Field getItem(int position) {
        return mFields.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    private static class ViewHolder {
        TextView tv_name;
        TextView tv_district;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_list_field, parent, false);
            holder = new ViewHolder();

            // init XML
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_district = (TextView) convertView.findViewById(R.id.tv_district);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //bat su kien tai day


        setValue(holder, position);
        return convertView;
    }

    private void setValue(ViewHolder holder, int position) {
        Field field = getItem(position);//What does it means?
        holder.tv_name.setText(field.getName());
        holder.tv_district.setText(""+field.getDistrict_id());
    }

}


