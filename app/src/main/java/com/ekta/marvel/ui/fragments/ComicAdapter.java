package com.ekta.marvel.ui.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ekta.marvel.R;
import com.ekta.marvel.network.response.Comics.Result;
import com.ekta.marvel.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ekta on 11-06-2017.
 */

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ViewHolderComic> {


    //    public VolleySingleton volleySingleton;
//    public ImageLoader imageLoader;
//    public ArrayList<Movie> listMovies = new ArrayList<>();
    private LayoutInflater layoutInflater;
    public Context context;
    public ClickListener clickListener;
    public List<Result> listResult = new ArrayList<>();


    public ComicAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);

        this.context = context;

    }

    public void setComicList(List<Result> listResult) {

        this.listResult = listResult;
        notifyItemChanged(0, listResult.size());
    }

    @Override
    public ViewHolderComic onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.cell_comic, parent, false);
        ViewHolderComic viewHolder = new ViewHolderComic(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderComic holder, int position) {
        Result currentComic = listResult.get(position);

        holder.mText.setText(currentComic.getTitle());
        String url = currentComic.getThumbnail().getPath() + Constants.THUMBNAIL + currentComic.getThumbnail().getExtension();
        Glide.with(context).load(url)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mThumnail);
    }


    public void setClickListener(ClickListener clickListener) {

        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return listResult.size()
                ;
    }


    class ViewHolderComic extends RecyclerView.ViewHolder implements View.OnClickListener {


        public ImageView mThumnail;
        public TextView mText;


        public ViewHolderComic(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mThumnail = (ImageView) itemView.findViewById(R.id.ivComicThumbnail);
            mText = (TextView) itemView.findViewById(R.id.tvComicTitle);

        }


        public String getTitle(String title) {

            return title;
        }

        @Override
        public void onClick(View v) {

            String title;
            if (clickListener != null) {

                clickListener.itemClicked(v, getPosition());
            }

        }
    }

    public interface ClickListener {

        public void itemClicked(View view, int position);


    }
}
