package com.eugene.spacecenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Администратор on 16.03.2017.
 */

class APODRecyclerViewAdapter extends RecyclerView.Adapter<APODRecyclerViewAdapter.ViewHolder>{

    private List<ApodBox> dataApodBoxes;
    private Context context;

    final private ListItemClickListener onClickListener;

    public interface ListItemClickListener {
         void onClick(int indexOfItem);
    }

    public APODRecyclerViewAdapter(ArrayList<ApodBox> apodBoxes, ListItemClickListener onClickHandler) {
        dataApodBoxes = apodBoxes;
        onClickListener = onClickHandler;

    }

    @Override
    public APODRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.list_item_apod,parent,false);

        return new APODRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(APODRecyclerViewAdapter.ViewHolder holder, int position) {

        ApodBox apodBox = dataApodBoxes.get(position);

        Glide.with(context).load(apodBox.getUrl())
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(holder.apodImage);

        holder.titleText.setText(apodBox.getTitle());

    }

    @Override
    public int getItemCount() {
        if (dataApodBoxes==null) return 0;
        return dataApodBoxes.size();
    }

    public void setAPODImagesData(List<ApodBox> data){
        dataApodBoxes = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener{

        public ImageView apodImage;
        public TextView titleText;

        public ViewHolder(View itemView) {
            super(itemView);

            apodImage =(ImageView) itemView.findViewById(R.id.apod_image_view);
            titleText = (TextView) itemView.findViewById(R.id.title_apod);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            onClickListener.onClick(clickedPosition);
        }
    }

}
