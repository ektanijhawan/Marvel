package com.ekta.marvel.ui.fragments;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ekta.marvel.R;
import com.ekta.marvel.network.response.Comics.Item_;
import com.ekta.marvel.network.response.Comics.Result;
import com.ekta.marvel.network.response.characters.Comics;
import com.ekta.marvel.network.response.characters.Item;
import com.ekta.marvel.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ekta on 12-06-2017.
 */

public class CharacterDetailAdapter extends RecyclerView.Adapter<CharacterDetailAdapter.ViewHolder> {


    private LayoutInflater layoutInflater;
    public Context context;
    public ComicAdapter.ClickListener clickListener;
    public List<Item> charResult = new ArrayList<>();
    public List<Item_> comicResult = new ArrayList<>();

    public Comics comics;
    String type;

//    public CharacterDetailAdapter(Context context) {
//        layoutInflater = LayoutInflater.from(context);
//
//        this.context = context;
//
//    }

    public CharacterDetailAdapter(Context context, String type) {
        layoutInflater = LayoutInflater.from(context);

        this.context = context;
        this.type = type;

    }


    public void setComicList(List<Item> listResult) {

        this.charResult = listResult;
        notifyItemChanged(0, listResult.size());
    }

    public void setCharacterList(List<Item_> listResult) {

        this.comicResult = listResult;
        notifyItemChanged(0, listResult.size());
    }

    @Override
    public CharacterDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.row_character_comic, parent, false);
        CharacterDetailAdapter.ViewHolder viewHolder = new CharacterDetailAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CharacterDetailAdapter.ViewHolder holder, int position) {
        if (type.equals(Constants.CHARACTER_FRAGMENT)) {
            Item currentItem = charResult.get(position);

            holder.mComicName.setText(currentItem.getName());
        } else {
            Item_ currentItem = comicResult.get(position);
            holder.mComicName.setText(currentItem.getName());


        }


    }


    public void setClickListener(ComicAdapter.ClickListener clickListener) {

        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        if (type.equals(Constants.CHARACTER_FRAGMENT)) {

            return charResult.size();
        } else
            return comicResult.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView mComicName;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mComicName = (TextView) itemView.findViewById(R.id.tvCharacterDetailComic);

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
