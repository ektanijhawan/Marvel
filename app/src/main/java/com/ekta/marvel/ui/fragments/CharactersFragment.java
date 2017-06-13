package com.ekta.marvel.ui.fragments;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekta.marvel.R;
import com.ekta.marvel.network.Endpoints;
import com.ekta.marvel.network.IdentityService;
import com.ekta.marvel.network.NetworkClient;
import com.ekta.marvel.network.response.characters.PayloadCharacterRes;
import com.ekta.marvel.network.response.characters.Result;
import com.ekta.marvel.ui.activities.BaseActivity;
import com.ekta.marvel.ui.activities.Detail;
import com.ekta.marvel.utils.Constants;
import com.google.gson.JsonElement;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class CharactersFragment extends BaseFragment implements CharacterAdapter.ClickListener {

    CharacterAdapter mCharacterAdapter;
    RecyclerView mCharacterList;
    public List<com.ekta.marvel.network.response.characters.Result> listResult = new ArrayList<>();
    Result result;

    public CharactersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_characters, container, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mCharacterList = (RecyclerView) view.findViewById(R.id.rvCharacters);

            gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mCharacterList.setLayoutManager(gridLayoutManager);
        mCharacterAdapter = new CharacterAdapter(getActivity());
        mCharacterAdapter.setClickListener(this);
        NetworkClient n = new NetworkClient(getBaseActivity());

        if (n.isConnectingToInternet(true))
            callCharacters();

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
        return "";
    }

    @Override
    void setFragName(String name) {

    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void callCharacters() {
        NetworkClient networkClient = new NetworkClient(getBaseActivity());

        Retrofit retrofit = networkClient.getRetrofit();
        long timeStamp = System.currentTimeMillis();

        String stringToHash = timeStamp + Endpoints.PRIVATE_API_KEY + Endpoints.PUBLIC_API_KEY;
        String hash = new String(Hex.encodeHex(DigestUtils.md5(stringToHash)));

        IdentityService identityService = retrofit.create(IdentityService.class);
        String url = Endpoints.MARVEL_CHARACTERS + Endpoints.TIMESTAMP + timeStamp + Endpoints.API_KEY + Endpoints.PUBLIC_API_KEY + Endpoints.HASH + hash + Endpoints.LIMIT;
        Observable<JsonElement> jobListData = identityService.callApi(url);

        NetworkClient kala = NetworkClient.getInstance(getBaseActivity());

        Observable<PayloadCharacterRes> jobList = kala.makeServiceReq(jobListData, PayloadCharacterRes.class, false);

        if (jobList != null) {
            jobList.doOnError(throwable -> {
                Log.e("Error", "Error" + throwable);
            })
                    .subscribeOn(Schedulers.io())

                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(res -> {
                        if (res != null) {
                            mCharacterAdapter.setCharacterList(res.getData().getResults());
                            listResult.addAll(res.getData().getResults());
                            mCharacterList.setAdapter(mCharacterAdapter);

                            Log.e("Service Res 1 ", res.toString());

                        } else {
                            try {
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Log.e("Service Res", "Data Empty");
                        }
                    });
        }
    }

    BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    @Override
    public boolean overrideOnBackPress() {
        return false;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void itemClicked(View view, int position) {
        result = this.listResult.get(position);
        Intent i = new Intent(getActivity(), Detail.class);
        i.putExtra(Constants.SOURCE, Constants.CHARACTER_FRAGMENT);

        i.putExtra(Constants.CHARACTER_NAME, result.getName());
        i.putExtra(Constants.CHARACTER_DESc, result.getDescription());
        i.putExtra(Constants.CHARACTER_THUMBNAIL, (Parcelable) result.getThumbnail());
        i.putExtra(Constants.CHARACTER_DETAIL_COMICS, (Parcelable) result.getComics());
        startActivity(i);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }


}
