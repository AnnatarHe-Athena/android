package com.annatarhe.athena.Adpter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.annatarhe.athena.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Annatarhe on 9/10/2017.
 * @author AnnatarHe
 * @email iamhele1994@gmail.com
 */

public class IndexListAdapter extends RecyclerView.Adapter<IndexListAdapter.ViewHolder> {

    private ArrayList<String> data;
    private Context context;

    public IndexListAdapter(Context context, ArrayList<String> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_item, parent, false);
        // 实例化viewholder
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.i("info", data.get(position));
//        Picasso.with(context).load(data.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.cellImage);
        }

    }
}
