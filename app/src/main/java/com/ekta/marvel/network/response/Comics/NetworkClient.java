package com.ekta.marvel.network.response.Comics;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.ekta.marvel.ui.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static java.security.AccessController.getContext;

/**
 * Created by Ekta on 11-06-2017.
 */

public class NetworkClient {
    private static final String ERROR_SESSION_EXPIRED = "500";

    private static NetworkClient instance;

    private static Context myContext;

    Context mContext;

    static ProgressDialog dialog;

    private BaseActivity activity;

//    public static NetworkClient getInstance(Context context) {
//        if (instance == null) {
//            instance = new NetworkClient(context);
//            myContext = context;
//        }
//
//        return instance;
//    }

    public static NetworkClient getInstance(BaseActivity activity1) {
        if (instance == null ) {
            instance = new NetworkClient(activity1);
            myContext = activity1;
        }

        return instance;
    }
    public NetworkClient(BaseActivity activity) {
        this.activity = activity;
        mContext = activity;
    }
    public <T> Observable<T> makeServiceReq(Observable<JsonElement> serviceObserable, final Class<T> serviceResponseType, Boolean isCache) {

        try {
            if(getActivity()==null){
                Log.e(this.getActivity().getLocalClassName(),"Activity is null");
            }else{
                Log.e(this.getActivity().getLocalClassName(),"Activity is not null");
            }
//        dialog = ProgressDialog.show(getActivity(), "",
//                getContext().getString(R.string.mesg_loading), true);
            if (isConnectingToInternet(true)) {

//                showLoader();


                return serviceObserable
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(new Func1<Throwable, JsonElement>() {
                            @Override
                            public JsonElement call(Throwable throwable) {
                                if(throwable instanceof HttpException) {
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        public void run() {
//                                            CustomToast.showLongMessage(getContext(), "Something went wrong. Please try again later" +
//                                                    ".");
                                        }
                                    });

                                }
                                Log.e("Error ",throwable.getMessage());
//                                closeLoader();
                                return null;
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new Func1<JsonElement, T>() {
                            @Override
                            public T call(JsonElement baseGsonBean) {
                                Log.e("Data", "data acf " + baseGsonBean);
                                try {
                                    if (baseGsonBean != null) {
//                                        closeLoader();
                                        Gson gson = new Gson();
                                        return gson.fromJson(baseGsonBean,serviceResponseType);
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                                return null;

                            }

                        });

            } else {
//                CustomToast.showLongMessage(getContext(),"Internet is not available");
                return serviceObserable.map(new Func1<JsonElement, T>() {
                    @Override
                    public T call(JsonElement jsonElement) {

                        return null;
                    }
                });
            }
        } catch (Exception e) {
            Log.e("Hi "+this.getClass().getName(), e.getMessage());
            return null;
        }

    }
    public boolean isConnectingToInternet(boolean isShowMessage) {

//        final ConnectivityManager connectivityManager = ((ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE));
//        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Network[] networks = connectivityManager.getAllNetworks();
                NetworkInfo networkInfo;
                for (Network mNetwork : networks) {
                    networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                    if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                        return true;
                    }
                }
            } else {
                if (connectivityManager != null) {
                    boolean haveConnectedWifi = false;
                    boolean haveConnectedMobile = false;
                    //noinspection deprecation
                    NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                    if (info != null) {
                        for (NetworkInfo ni : info) {
                            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                                if (ni.isConnected())
                                    haveConnectedWifi = true;
                            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                                if (ni.isConnected())
                                    haveConnectedMobile = true;
                        }
                        return haveConnectedWifi || haveConnectedMobile;
                    }
                }
            }
            if (isShowMessage) {
//                CustomToast.showShortMessage(getActivity(), getActivity().getString(R.string.mesg_network_not_available));
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public BaseActivity getActivity() {
        return activity;
    }

}
