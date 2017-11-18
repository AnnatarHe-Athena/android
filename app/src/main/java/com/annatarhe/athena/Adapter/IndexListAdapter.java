package com.annatarhe.athena.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.annatarhe.athena.FetchGirlsQuery;
import com.annatarhe.athena.InitialQuery;
import com.annatarhe.athena.R;
import com.annatarhe.athena.Utils.Utils;
import com.annatarhe.athena.fragment.FetchGirls;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Annatarhe on 9/10/2017.
 * @author AnnatarHe
 * @email iamhele1994@gmail.com
 */

public class IndexListAdapter extends RecyclerView.Adapter<IndexListAdapter.ViewHolder> {

    private List<FetchGirlsQuery.Girl> data;
    private Context context;

    public IndexListAdapter(Context context, List<FetchGirlsQuery.Girl> data) {
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
        String imgSrc = Utils.getRealSrcLink(data.get(position).fragments().fetchGirls().img());
        Log.i("image", imgSrc);
        Picasso.with(context).load(imgSrc).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.cellImage);
        }

    }
}
