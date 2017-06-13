package com.ekta.marvel.network;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.ekta.marvel.R;
import com.ekta.marvel.ui.activities.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.ekta.marvel.network.Endpoints.MARVEL_ENDPOINT;
import static java.security.AccessController.getContext;

/**
 * Created by Ekta on 11-06-2017.
 */

public class NetworkClient extends Application {

    private static NetworkClient instance;

    private static Context myContext;

    Context mContext;

    static ProgressDialog dialog;

    private BaseActivity activity;



    public static NetworkClient getInstance(BaseActivity activity1) {
        if (instance == null) {
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
            if (getActivity() == null) {
                Log.e(this.getActivity().getLocalClassName(), "Activity is null");
            } else {
                Log.e(this.getActivity().getLocalClassName(), "Activity is not null");
            }

            if (isConnectingToInternet(true)) {

                showLoader();


                return serviceObserable
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(new Func1<Throwable, JsonElement>() {
                            @Override
                            public JsonElement call(Throwable throwable) {
                                if (throwable instanceof HttpException) {
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        public void run() {
                                            Toast.makeText(getActivity(), "Something went wrong. Please try again later.", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                                Log.e("Error ", throwable.getMessage());
                                closeLoader();
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
                                        closeLoader();
                                        Gson gson = new Gson();
                                        return gson.fromJson(baseGsonBean, serviceResponseType);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                return null;

                            }

                        });

            } else {
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.net_not_available), Toast.LENGTH_SHORT).show();
                return serviceObserable.map(new Func1<JsonElement, T>() {
                    @Override
                    public T call(JsonElement jsonElement) {

                        return null;
                    }
                });
            }
        } catch (Exception e) {
            Log.e("Hi " + this.getClass().getName(), e.getMessage());
            return null;
        }

    }

    public boolean isConnectingToInternet(boolean isShowMessage) {

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
                Toast.makeText(getActivity(), getActivity().getString(R.string.net_not_available), Toast.LENGTH_SHORT).show();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isConnectingToInternet2(boolean isShowMessage) {

        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) instance.getSystemService(Context.CONNECTIVITY_SERVICE);
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
//                Toast.makeText(get, getString(R.string.net_not_available),Toast.LENGTH_SHORT);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public BaseActivity getActivity() {
        return activity;
    }

    public <T> Observable<T> makeBackgroundServiceReq(Observable<JsonElement> serviceObserable, Class<T> serviceResponseType, Boolean isCache) {

        try {

            Observable<T> webServiceData1 = serviceObserable
                    .timeout(new Func1<JsonElement, Observable<Object>>() {
                        @Override
                        public Observable<Object> call(JsonElement jsonElement) {

                            return null;
                        }
                    })
                    .onErrorReturn(new Func1<Throwable, JsonElement>() {
                        @Override
                        public JsonElement call(Throwable throwable) {

                            return null;
                        }
                    })
                    .onErrorResumeNext(new Func1<Throwable, Observable<? extends JsonElement>>() {
                        @Override
                        public Observable<? extends JsonElement> call(Throwable throwable) {
                            return Observable.empty();
                        }
                    })
                    .doOnError(new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Log.e("Error", throwable.getLocalizedMessage() + " " + throwable.getMessage());

                            closeLoader();
                        }
                    })

                    .map(new Func1<JsonElement, T>() {
                        @Override
                        public T call(JsonElement baseGsonBean) {
                            Log.e("Data", "data " + baseGsonBean);
                            if (baseGsonBean != null) {
                                Gson gson = new Gson();
                                return gson.fromJson(String.valueOf(baseGsonBean), serviceResponseType);
                            }
                            return null;

                        }

                    })
                    .filter(new Func1<T, Boolean>() {
                        @Override
                        public Boolean call(T baseGsonBean) {


                            return true;

                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io());


            return webServiceData1;
        } catch (Exception e) {
            return null;
        }

    }

    static Retrofit retrofit;

    public Retrofit getRetrofit() {
        if (retrofit == null) {

            File httpCacheDirectory = new File(activity
                    .getCacheDir(), "responses");
            int cacheSize = 10 * 1024 * 1024;
            Cache cache = new Cache(httpCacheDirectory, cacheSize);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addNetworkInterceptor(
                            REWRITE_CACHE_CONTROL_INTERCEPTOR)
                    .cache(cache)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(MARVEL_ENDPOINT)
                    .client(client)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }


        return retrofit;
    }

    public String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("responses/");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    Log.d("file", receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());

            if (NetworkClient.isConnectingToInternet2(true)) {
                int maxAge = 60; // read from cache for 1 minute
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };


    private void showLoader() {
        if (!(getActivity().isFinishing())) {
            if (dialog != null && dialog.isShowing()) {
                return;
            }
            dialog = ProgressDialog.show(getActivity(), "",
                    getActivity().getString(R.string.loading), true);
        }
    }

    private void closeLoader() {
        try {
            dialog.dismiss();
            dialog = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
