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

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import com.ekta.marvel.R;
import com.ekta.marvel.network.Endpoints;
import com.ekta.marvel.network.IdentityService;
import com.ekta.marvel.network.NetworkClient;
import com.ekta.marvel.network.response.Comics.PayloadComicRes;
import com.ekta.marvel.network.response.Comics.Result;
import com.ekta.marvel.ui.activities.BaseActivity;
import com.ekta.marvel.ui.activities.Detail;
import com.ekta.marvel.utils.Constants;
import com.google.gson.JsonElement;

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
public class ComicsFragment extends BaseFragment implements ComicAdapter.ClickListener {

    ComicAdapter mComicAdapter;
    RecyclerView mComicList;
    Result result;
    public List<com.ekta.marvel.network.response.Comics.Result> listResult = new ArrayList<>();

    public ComicsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comics, container, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mComicList = (RecyclerView) view.findViewById(R.id.rvComics);

        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mComicList.setLayoutManager(gridLayoutManager);
        mComicAdapter = new ComicAdapter(getActivity());
        mComicAdapter.setClickListener(this);
        NetworkClient n = new NetworkClient(getBaseActivity());
        if (n.isConnectingToInternet(true))
            callComics();
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

    private void callComics() {
        NetworkClient networkClient = new NetworkClient(getBaseActivity());
        Retrofit retrofit = networkClient.getRetrofit();
        long timeStamp = System.currentTimeMillis();
        String stringToHash = timeStamp + Endpoints.PRIVATE_API_KEY + Endpoints.PUBLIC_API_KEY;
        String hash = new String(Hex.encodeHex(DigestUtils.md5(stringToHash)));

        IdentityService identityService = retrofit.create(IdentityService.class);
        String url = Endpoints.MARVEL_COMICS + Endpoints.TIMESTAMP + timeStamp + Endpoints.API_KEY + Endpoints.PUBLIC_API_KEY + Endpoints.HASH + hash + Endpoints.LIMIT;
        Observable<JsonElement> jobListData = identityService.callApi(url);

        NetworkClient kala = NetworkClient.getInstance(getBaseActivity());

        Observable<PayloadComicRes> jobList = kala.makeServiceReq(jobListData, PayloadComicRes.class, false);

        if (jobList != null) {
            jobList.doOnError(
                    throwable -> {
                        Log.e("Error", "Error" + throwable);
                    })
                    .subscribeOn(Schedulers.io())

                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(res -> {
                        if (res != null) {
                            mComicAdapter.setComicList(res.getData().getResults());
                            listResult.addAll(res.getData().getResults());

                            mComicList.setAdapter(mComicAdapter);
                            try {
                                Log.d("file", kala.readFromFile(getBaseActivity()));
                            } catch (Exception e) {

                                e.printStackTrace();
                            }
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    @Override
    public boolean overrideOnBackPress() {
        return false;
    }



    @Override
    public void itemClicked(View view, int position) {
        result = this.listResult.get(position);
        Intent i = new Intent(getActivity(), Detail.class);
        i.putExtra(Constants.SOURCE, Constants.COMIC_FRAGMENT);
        i.putExtra(Constants.COMIC_TITLE, result.getTitle());
        i.putExtra(Constants.COMIC_DESC, result.getVariantDescription());
        i.putExtra(Constants.COMIC_THUMBNAIL, (Parcelable) result.getThumbnail());
        i.putExtra(Constants.COMIC_DETAIL_CHARACTERS, (Parcelable) result.getCharacters());
        startActivity(i);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }


}
