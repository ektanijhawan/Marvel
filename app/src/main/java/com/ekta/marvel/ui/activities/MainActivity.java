package com.ekta.marvel.ui.activities;

import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ekta.marvel.R;
import com.ekta.marvel.ui.activities.BaseActivity;
import com.ekta.marvel.ui.fragments.BaseFragment;
import com.ekta.marvel.ui.fragments.CharacterDetailFragment;
import com.ekta.marvel.ui.fragments.CharactersFragment;
import com.ekta.marvel.ui.fragments.ComicsFragment;
import com.ekta.marvel.ui.fragments.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import static com.ekta.marvel.utils.Constants.FRAGMENT_STACK;

public class MainActivity extends BaseActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    BaseFragment baseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HomeFragment fragment= new HomeFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container,fragment);
        fragmentTransaction.addToBackStack(FRAGMENT_STACK);
        fragmentTransaction.commit();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getActivity().finish();
    }

    @Override
    public BaseActivity getActivity() {
        return null;
    }
}