package com.ekta.marvel.ui.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ekta.marvel.R;
import com.ekta.marvel.network.response.Comics.Characters;
import com.ekta.marvel.network.response.characters.Comics;
import com.ekta.marvel.network.response.characters.Result;
import com.ekta.marvel.network.response.characters.Thumbnail;
import com.ekta.marvel.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComicDetailFragment extends BaseFragment {

    ImageView mComicImage;
    TextView mComicName;
    TextView mComicrDesc;
    Activity activity;
    LinearLayout mListView;
    TextView mListText;
    com.ekta.marvel.network.response.Comics.Thumbnail thumbnail;
    String comicTitle;
    String comicDesc;
    Characters comicChracters;
    RecyclerView recyclerView;
    CharacterDetailAdapter mAdapter;

    public ComicDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_character_detail, container, false);
        mComicImage = (ImageView) view.findViewById(R.id.ivCharacterDetail);
        mComicName = (TextView) view.findViewById(R.id.tvCharacterDetailName);
        mComicrDesc = (TextView) view.findViewById(R.id.tvCharacterDetailDesc);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvCharacterDetailComics);
        mListView= (LinearLayout) view.findViewById(R.id.list);
        mListText= (TextView) view.findViewById(R.id.tvlist);
        thumbnail = getArguments().getParcelable(Constants.COMIC_THUMBNAIL);
        comicTitle = getArguments().getString(Constants.COMIC_TITLE);
        comicDesc = getArguments().getString(Constants.COMIC_DESC);
        comicChracters = getArguments().getParcelable(Constants.COMIC_DETAIL_CHARACTERS);


        setData();
        return view;
    }

    @Override
    public void initialize(View view) {

    }

    @Override
    void onDestroyFragment() {

    }

    @Override
    String getFragName() {
        return null;
    }

    @Override
    void setFragName(String name) {

    }

    @Override
    public boolean overrideOnBackPress() {
        return false;
    }



    public void setData() {

        String url = thumbnail.getPath() + Constants.LANDSCAPE_THUMBNAIL + thumbnail.getExtension();
        if(!url.equals(null)) {
            Glide.with(getActivity()).load(url)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mComicImage);
        }
        mComicName.setText(getString(R.string.title)+" " +comicTitle);
        if(!comicDesc.equals(""))
        mComicrDesc.setText(getString(R.string.desc)+" " +comicDesc);
        else
        mComicrDesc.setText(getString(R.string.desc)+" "+getString(R.string.no_desc_available));
        mAdapter = new CharacterDetailAdapter(getActivity(),Constants.COMIC_FRAGMENT);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        if (comicChracters.getAvailable() >= 1) {

            mListText.setText(R.string.comics_characters);
            mAdapter.setCharacterList(comicChracters.getItems());
        }else {
            mListText.setText(R.string.no_char_available);

        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}
