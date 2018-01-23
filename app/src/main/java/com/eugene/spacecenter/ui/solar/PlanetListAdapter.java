package com.eugene.spacecenter.ui.solar;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eugene.spacecenter.R;
import com.eugene.spacecenter.data.models.Planet;

import java.util.List;


/**
 * Created by Администратор on 18.09.2016.
 */
public class PlanetListAdapter extends ArrayAdapter<Planet>{

    public PlanetListAdapter(Activity context, List<Planet> planetLists) {
        super(context, 0, planetLists);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_planet, parent, false);
        }

        Planet currentItem = getItem(position);

        ImageView imagePlanet = (ImageView)listItemView.findViewById(R.id.image_planet);
        imagePlanet.setImageResource(currentItem.getImageResId());

        TextView planetNameView = (TextView)listItemView.findViewById(R.id.planet_name);
        planetNameView.setText(currentItem.getObject());

        TextView planetInfoView = (TextView)listItemView.findViewById(R.id.planet_description);
        planetInfoView.setText(currentItem.getDescription());

        return listItemView;


    }
}
