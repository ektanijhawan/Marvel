package com.ekta.marvel.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekta.marvel.ui.activities.BaseActivity;

/**
 * Created by Ekta on 11-06-2017.
 */

public abstract class BaseFragment extends Fragment {

    String headerText = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, View mainView, String headerContent) {
        super.onCreateView(inflater, container, savedInstanceState);

        try {
            initialize(mainView);
            setHeaderText(mainView, headerContent);
        } catch (Exception e) {
        }
        return mainView;

    }


    abstract public void initialize(View view);


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            onDestroyFragment();
        } catch (Exception e) {
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    /**
     * pop immediate Activity/Fragment at the top of the stack.
     */
    abstract void onDestroyFragment();

    public void setHeaderText(View view, String headerText) {
        this.headerText = headerText;
        try {
        } catch (Exception e) {
        }
    }

    abstract String getFragName();

    abstract void setFragName(String name);

    public String getHeaderText() {
        return headerText;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {


        } catch (Exception e) {
        }
        try {

            BaseFragment f = this;
        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }


    public abstract boolean overrideOnBackPress();

}

