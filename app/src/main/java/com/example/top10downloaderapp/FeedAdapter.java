package com.example.top10downloaderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class FeedAdapter extends ArrayAdapter {
    private static final String TAG = "FeedAdapter";
    private  final int LayoutResource;
    private final LayoutInflater layoutInflater;
    private List<Feedentry> applications;


    public FeedAdapter(@NonNull Context context, int resource, List<Feedentry> applications) {
        super(context, resource);
        LayoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.applications = applications;
    }


    @Override
    public int getCount() {
        return applications.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null) {
            convertView = layoutInflater.inflate(LayoutResource, parent, false);
        }
        TextView tvname=convertView.findViewById(R.id.tvName);
        TextView tvartist=convertView.findViewById(R.id.Tvartist);
        TextView tvsummary=convertView.findViewById(R.id.tvsummary);

        Feedentry currentApp=applications.get(position);


        tvname.setText(currentApp.getName());
        tvartist.setText((currentApp.getArtist()));
        tvsummary.setText(currentApp.getSummary());

        return convertView;
    }
}
