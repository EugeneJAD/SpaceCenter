package com.eugene.spacecenter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlanetsInfoFragment extends Fragment {


    public PlanetsInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_solar_system, container, false);

        ArrayList<PlanetItem> planetList = new ArrayList<PlanetItem>();
        planetList.add(new PlanetItem(getContext(),R.drawable.sun,R.string.sun_title,R.string.sun_info, R.string.sun_url));
        planetList.add(new PlanetItem(getContext(),R.drawable.mercury,R.string.mercury_title,R.string.mercury_info, R.string.mercury_url));
        planetList.add(new PlanetItem(getContext(),R.drawable.venus,R.string.venus_title,R.string.venus_info, R.string.venus_url));
        planetList.add(new PlanetItem(getContext(),R.drawable.earth, R.string.earth_title,R.string.earth_info, R.string.earth_url));
        planetList.add(new PlanetItem(getContext(),R.drawable.mars,R.string.mars_title,R.string.mars_info, R.string.mars_url));
        planetList.add(new PlanetItem(getContext(),R.drawable.ceres,R.string.ceres_title,R.string.ceres_info, R.string.ceres_url));
        planetList.add(new PlanetItem(getContext(),R.drawable.jupiter,R.string.jupiter_title,R.string.jupiter_info, R.string.jupiter_url));;
        planetList.add(new PlanetItem(getContext(),R.drawable.saturn,R.string.saturn_title,R.string.saturn_info, R.string.saturn_url));;
        planetList.add(new PlanetItem(getContext(),R.drawable.uranus,R.string.uranus_title,R.string.uranus_info, R.string.uranus_url));;
        planetList.add(new PlanetItem(getContext(),R.drawable.neptune,R.string.neptune_title,R.string.neptune_info, R.string.neptune_url));;
        planetList.add(new PlanetItem(getContext(),R.drawable.pluto,R.string.pluto_title,R.string.pluto_info, R.string.pluto_url));;
        planetList.add(new PlanetItem(getContext(),R.drawable.makemake,R.string.makemake_title,R.string.makemake_info, R.string.makemake_url));;
        planetList.add(new PlanetItem(getContext(),R.drawable.haumea,R.string.haumea_title,R.string.haumea_info, R.string.haumea_url));;
        planetList.add(new PlanetItem(getContext(),R.drawable.eris,R.string.eris_title,R.string.eris_info, R.string.eris_url));;

        final PlanetListAdapter adapter = new PlanetListAdapter(getActivity(),planetList);

        ListView listView = (ListView)rootView.findViewById(R.id.list_solar_system);

        View loadingIndicator = rootView.findViewById(R.id.loading_indicator);
        listView.setEmptyView(loadingIndicator);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PlanetItem current = adapter.getItem(position);

                Bundle b = new Bundle();
                b.putString("url", current.getURL());
                WikiSolarSystemFragment wikiSolarSystemFragment = new WikiSolarSystemFragment();
                wikiSolarSystemFragment.setArguments(b);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_for_solar_sys, wikiSolarSystemFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        return rootView;
    }



}
