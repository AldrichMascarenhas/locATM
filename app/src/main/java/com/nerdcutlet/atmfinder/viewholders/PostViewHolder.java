package com.nerdcutlet.atmfinder.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nerdcutlet.atmfinder.R;
import com.nerdcutlet.atmfinder.model.AtmModelData;

/**
 * Created by Aldrich on 13-11-2016.
 */

public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView date;
    public TextView n100;
    public TextView n200;
    public TextView total;


    public PostViewHolder(View itemView) {
        super(itemView);


        date = (TextView) itemView.findViewById(R.id.item_post_tv_date);
        n100 = (TextView) itemView.findViewById(R.id.item_post_tv_n100);
        n200 = (TextView) itemView.findViewById(R.id.item_post_tv_n2000);
        total = (TextView) itemView.findViewById(R.id.item_post_tv_total);


    }

    public void bindToPost(AtmModelData post) {

        date.setText(String.valueOf(post.getDatetime()));
        n100.setText(String.valueOf(post.getN100()));
        n200.setText(String.valueOf(post.getN2000()));
        total.setText(String.valueOf(post.getTotal()));


    }
}