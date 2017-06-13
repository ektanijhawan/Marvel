package com.ekta.marvel.ui.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ekta.marvel.R;
import com.ekta.marvel.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ekta on 11-06-2017.
 */

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolderComic> {


    private LayoutInflater layoutInflater;
    public Context context;
    public ClickListener clickListener;
    public List<com.ekta.marvel.network.response.characters.Result> listResult = new ArrayList<>();


    public CharacterAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);

        this.context = context;

    }

    public void setCharacterList(List<com.ekta.marvel.network.response.characters.Result> listResult) {

        this.listResult = listResult;
        notifyItemChanged(0, listResult.size());
    }

    @Override
    public ViewHolderComic onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.cell_character, parent, false);
        ViewHolderComic viewHolder = new ViewHolderComic(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderComic holder, int position) {
        com.ekta.marvel.network.response.characters.Result currentComic = listResult.get(position);

        holder.mText.setText(currentComic.getName());
        String url = currentComic.getThumbnail().getPath() + Constants.THUMBNAIL_STANDARD + currentComic.getThumbnail().getExtension();
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
            mThumnail = (ImageView) itemView.findViewById(R.id.ivCharacterThumbnail);
            mText = (TextView) itemView.findViewById(R.id.tvCharacterTitle);

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
