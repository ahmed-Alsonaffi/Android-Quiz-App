package com.example.quizapp2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CustomList extends BaseAdapter {
     String[] ListNames;
    private String[] durations;
    private int[] imageId;
    Context context;

    public CustomList(String[] listNames, String[] durations, int[] imageId, Context context) {
        ListNames = listNames;
        this.durations = durations;
        this.imageId = imageId;
        this.context = context;
    }

    @Override
    public int getCount() {
        return ListNames.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_row, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.title);
            viewHolder.txtDuration = (TextView) convertView.findViewById(R.id.duration);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.list_image);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtName.setText(ListNames[position]);
        viewHolder.txtDuration.setText(durations[position]);
        viewHolder.icon.setImageResource(imageId[position]);

        return convertView;
    }

    private static class ViewHolder {

        TextView txtName;
        TextView txtDuration;
        ImageView icon;

    }
}
