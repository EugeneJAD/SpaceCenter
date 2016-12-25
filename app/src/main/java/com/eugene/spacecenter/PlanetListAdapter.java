package com.eugene.spacecenter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Администратор on 18.09.2016.
 */
public class PlanetListAdapter extends ArrayAdapter<PlanetItem>{

    public PlanetListAdapter(Activity context, ArrayList<PlanetItem> planetLists) {
        super(context, 0, planetLists);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_planet, parent, false);
        }

        PlanetItem currentItem = getItem(position);

        ImageView imagePlanet = (ImageView)listItemView.findViewById(R.id.image_planet);
        imagePlanet.setImageResource(currentItem.getImageResourceId());

        TextView planetNameView = (TextView)listItemView.findViewById(R.id.planet_name);
        planetNameView.setText(currentItem.getPlanetName());

        TextView planetInfoView = (TextView)listItemView.findViewById(R.id.planet_description);
        planetInfoView.setText(currentItem.getPlanetInfo());

        return listItemView;


    }
}
