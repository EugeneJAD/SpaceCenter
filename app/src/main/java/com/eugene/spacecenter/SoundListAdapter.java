package com.eugene.spacecenter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Администратор on 20.09.2016.
 */
public class SoundListAdapter extends ArrayAdapter<SoundBox>{


    public SoundListAdapter(Activity context, ArrayList<SoundBox> soundBoxes) {
        super(context, 0, soundBoxes);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_sounds, parent, false);
        }


        SoundBox current = getItem(position);

        TextView titleText = (TextView) listItemView.findViewById(R.id.title_sound);
        titleText.setText(current.getTitle());


        TextView durationLength = (TextView) listItemView.findViewById(R.id.duration_sec);
        String formattedDuration = getFormattedDuration(current.getDuration())+" "+getContext().getResources().getString(+R.string.second);
        durationLength.setText(formattedDuration);


        return listItemView;


    }

    private String getFormattedDuration(long duration) {

        int seconds = (int) (duration/1000);
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(seconds);

    }


}
