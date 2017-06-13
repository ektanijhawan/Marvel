package com.ekta.marvel.ui.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ekta.marvel.R;

/**
 * Created by Ekta on 11-06-2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public abstract BaseActivity getActivity();
    public void activityBackPressBtn() {
        try {

            this.finish();

            overridePendingTransition(R.anim.right_in, R.anim.right_out);
            getActivity().finish();
        }catch (Exception e){
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }
}
