package com.eugene.spacecenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Администратор on 08.10.2016.
 */
public class CategoryAdapter extends ArrayAdapter<Category>{


    public CategoryAdapter(Context context, List<Category> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView =convertView;
        if (listItemView==null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_category,parent,false);


        Category currentCategory = getItem(position);

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image_category);
        imageView.setImageResource(currentCategory.getImageResID());


        TextView textView = (TextView) listItemView.findViewById(R.id.category_name_text);
        textView.setText(currentCategory.getCategoryName());

        return listItemView;
    }
}
