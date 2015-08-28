package com.mastercard.travel.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mastercard.travel.R;

/**
 * Created by diego.rotondale on 23/09/14.
 */
public class NavItemAdapter extends ArrayAdapter<NavItem> {
    private Context context;

    public NavItemAdapter(Context context, NavItem[] items) {
        super(context, R.layout.navigation_bar_item);
        this.context = context;
        super.addAll(items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ItemHolder holder;
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            holder = new ItemHolder();
            view = inflater.inflate(R.layout.navigation_bar_item, parent, false);
            holder.icon = (ImageView) view.findViewById(R.id.icon);
            holder.title = (TextView) view.findViewById(R.id.text);
            view.setTag(holder);
        } else {
            holder = (ItemHolder) view.getTag();
        }

        final NavItem item = getItem(position);
        holder.icon.setBackgroundResource(item.getResImageId());
        holder.title.setText(item.getResTextId());

        return view;
    }


    private static class ItemHolder {
        ImageView icon;
        TextView title;
    }

}
