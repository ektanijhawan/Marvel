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
import com.ekta.marvel.network.response.characters.Comics;
import com.ekta.marvel.network.response.characters.Result;
import com.ekta.marvel.network.response.characters.Thumbnail;
import com.ekta.marvel.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class CharacterDetailFragment extends BaseFragment {

    ImageView mCharacterImage;
    TextView mCharacterName;
    TextView mCharacterDesc;
    Activity activity;
    LinearLayout mListView;
    TextView mListText;
    Result character_result;
    Thumbnail thumbnail;
    String characterName;
    String characterDesc;
    Comics characterComics;
    RecyclerView recyclerView;
    CharacterDetailAdapter mAdapter;
    public CharacterDetailFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_character_detail, container, false);
        mCharacterImage = (ImageView) view.findViewById(R.id.ivCharacterDetail);
        mCharacterName = (TextView) view.findViewById(R.id.tvCharacterDetailName);
        mCharacterDesc = (TextView) view.findViewById(R.id.tvCharacterDetailDesc);
        recyclerView= (RecyclerView) view.findViewById(R.id.rvCharacterDetailComics);
        mListView= (LinearLayout) view.findViewById(R.id.list);
        mListText= (TextView) view.findViewById(R.id.tvlist);
        thumbnail = getArguments().getParcelable(Constants.CHARACTER_THUMBNAIL);
        characterName = getArguments().getString(Constants.CHARACTER_NAME);
        characterDesc = getArguments().getString(Constants.CHARACTER_DESc);
        characterComics = getArguments().getParcelable(Constants.CHARACTER_DETAIL_COMICS);


        setData();
        return view;
    }


    public void setData(){

        String url = thumbnail.getPath() + Constants.LANDSCAPE_THUMBNAIL + thumbnail.getExtension();
        Glide.with(getActivity()).load(url)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mCharacterImage);
        mCharacterName.setText(getString(R.string.name)+" "+characterName);
        if(!characterDesc.equals(""))
        mCharacterDesc.setText(getString(R.string.desc)+" "+characterDesc);
        else
            mCharacterDesc.setText(getString(R.string.desc)+" "+ getString(R.string.no_desc_available));

        mAdapter = new CharacterDetailAdapter(getActivity(),Constants.CHARACTER_FRAGMENT);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        if(characterComics.getAvailable()>=1)
        {

            mListText.setText(R.string.character_comics_text);
            mAdapter.setComicList(characterComics.getItems());
        }else {
            mListText.setText(R.string.no_comics_available);

        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
    @Override
    public void initialize(View view) {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
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
        activity.finish();

        return true;
    }



}
