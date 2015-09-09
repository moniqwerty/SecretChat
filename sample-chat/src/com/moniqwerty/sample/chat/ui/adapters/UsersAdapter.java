package com.moniqwerty.sample.chat.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.moniqwerty.sample.chat.core.ChatService;
import com.quickblox.users.model.QBUser;
import com.quickblox.sample.chat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moniqwerty on 9/12/14.
 */
public class UsersAdapter extends BaseAdapter {

    private List<QBUser> dataSource;
    private LayoutInflater inflater;
    private List<QBUser> selected = new ArrayList<QBUser>();
    private List<QBUser> filtered;

    public UsersAdapter(List<QBUser> dataSource, Context ctx) {
        this.dataSource = dataSource;
        this.inflater = LayoutInflater.from(ctx);
        filtered = new ArrayList<>();
        final QBUser self = ChatService.getInstance().getCurrentUser();
        String location = self.getCustomData().toString();
        String [] latLong = location.split(";");
        double myLat = Double.parseDouble(latLong[0]);
        double myLon = Double.parseDouble(latLong[1]);

        for (QBUser u : dataSource)
        {
            try
            {
                location = u.getCustomData().toString();
                latLong = location.split(";");
                if (directDistance(myLat,myLon,Double.parseDouble(latLong[0]),Double.parseDouble(latLong[1]))>100)
                {
                    continue;
                }
            }
            catch (Exception e)
            {
                continue;
            }
            filtered.add(u);
        }
    }

    private double ToRadians(double degrees)
    {
        double radians = degrees * Math.PI / 180;
        return radians;
    }

    public double directDistance(double lat1, double lng1, double lat2, double lng2)
    {
        double earthRadius = 3958.75;
        double dLat = ToRadians(lat2-lat1);
        double dLng = ToRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat / 2) +
                Math.cos(ToRadians(lat1)) * Math.cos(ToRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;
        double meterConversion = 1609.00;
        return dist * meterConversion;
    }
    public List<QBUser> getSelected() {
        return selected;
    }

    @Override
    public int getCount() {
        //return dataSource.size();
        return filtered.size();
    }

    @Override
    public Object getItem(int position) {
        //return dataSource.get(position);
        return filtered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_user, null);
            holder = new ViewHolder();
            holder.login = (TextView) convertView.findViewById(R.id.userLogin);
            holder.add = (CheckBox) convertView.findViewById(R.id.addCheckBox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //final QBUser user = dataSource.get(position);
        final QBUser user = filtered.get(position);

            if (user != null) {
                holder.login.setText(user.getLogin());
                holder.add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ((((CheckBox) v).isChecked())) {
                            selected.add(user);
                        } else {
                            selected.remove(user);
                        }
                    }
                });
                holder.add.setChecked(selected.contains(user));
            }


        return convertView;
    }

    private static class ViewHolder {
        TextView login;
        CheckBox add;
    }
}
