package com.eugene.spacecenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Администратор on 22.03.2017.
 */

class AsteroidsAdapter extends RecyclerView.Adapter<AsteroidsAdapter.ViewHolder>{

    private List<Asteroid> asteroidsList;
    private Context context;

    public AsteroidsAdapter (List<Asteroid> list) {
        asteroidsList = list;
    }

    @Override
    public AsteroidsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.list_item_asteroid,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AsteroidsAdapter.ViewHolder holder, int position) {

        Asteroid asteroid = asteroidsList.get(position);

        Glide.with(context).load(asteroid.getImageRes()).into(holder.asteroidImage);
        holder.nameView.setText(asteroid.getName());
        holder.diameterView.setText(asteroid.getDiameterMeters());
        holder.approachView.setText(asteroid.getCloseApproachData());
        holder.velocityView.setText(asteroid.getVelocity());
        holder.distanceView.setText(asteroid.getMissDistance());
        holder.hazardousView.setText(getHasardousString(asteroid.isHazardousAsteroid()));

        if (asteroid.isHazardousAsteroid())
            holder.hazardousView.setTextColor(context.getResources().getColor(R.color.red));
        else
            holder.hazardousView.setTextColor(context.getResources().getColor(R.color.green));

    }

    @Override
    public int getItemCount() {
        if (asteroidsList==null) return 0;
        return asteroidsList.size();
    }

    public void setAsteroidsData(List<Asteroid> data){
        asteroidsList = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView asteroidImage;
        TextView nameView;
        TextView diameterView;
        TextView approachView;
        TextView velocityView;
        TextView distanceView;
        TextView hazardousView;


        public ViewHolder(View itemView) {
            super(itemView);
            asteroidImage = (ImageView) itemView.findViewById(R.id.asteroid_imageview);
            nameView = (TextView) itemView.findViewById(R.id.name_asteroid);
            diameterView = (TextView) itemView.findViewById(R.id.diametr_asteroid);
            approachView = (TextView) itemView.findViewById(R.id.close_approach_data);
            velocityView = (TextView) itemView.findViewById(R.id.velocity_asteroid);
            distanceView = (TextView) itemView.findViewById(R.id.miss_distance);
            hazardousView = (TextView) itemView.findViewById(R.id.hazardous_view);
        }
    }


    private String getHasardousString(boolean hazardous) {

        if (hazardous) return "Yes";
        else return "No";

    }


}
