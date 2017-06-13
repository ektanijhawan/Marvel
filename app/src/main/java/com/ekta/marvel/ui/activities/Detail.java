package com.ekta.marvel.ui.activities;

import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ekta.marvel.R;
import com.ekta.marvel.network.response.Comics.Characters;
import com.ekta.marvel.network.response.characters.Comics;
import com.ekta.marvel.network.response.characters.Thumbnail;
import com.ekta.marvel.ui.fragments.BaseFragment;
import com.ekta.marvel.ui.fragments.CharacterDetailFragment;
import com.ekta.marvel.ui.fragments.ComicDetailFragment;
import com.ekta.marvel.utils.Constants;

public class Detail extends BaseActivity {
    Bundle bundle;
    Fragment movieDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        bundle = new Bundle();
        if(getIntent().getStringExtra(Constants.SOURCE).equals(Constants.CHARACTER_FRAGMENT)) {
            Thumbnail thumbnail = getIntent().getParcelableExtra(Constants.CHARACTER_THUMBNAIL);
            String name = getIntent().getStringExtra(Constants.CHARACTER_NAME);
            String desc = getIntent().getStringExtra(Constants.CHARACTER_DESc);
            Comics comics = getIntent().getParcelableExtra(Constants.CHARACTER_DETAIL_COMICS);
            bundle.putParcelable(Constants.CHARACTER_THUMBNAIL, thumbnail);
            bundle.putString(Constants.CHARACTER_NAME, name);
            bundle.putString(Constants.CHARACTER_DESc, desc);
            bundle.putParcelable(Constants.CHARACTER_DETAIL_COMICS, comics);
            movieDetail = new CharacterDetailFragment();
            movieDetail.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, movieDetail)
                    .commit();
        }
        else{
            com.ekta.marvel.network.response.Comics.Thumbnail thumbnail = getIntent().getParcelableExtra(Constants.COMIC_THUMBNAIL);
            String title = getIntent().getStringExtra(Constants.COMIC_TITLE);
            String desc = getIntent().getStringExtra(Constants.COMIC_DESC);
            Characters comics = getIntent().getParcelableExtra(Constants.COMIC_DETAIL_CHARACTERS);
            bundle.putParcelable(Constants.COMIC_THUMBNAIL, thumbnail);
            bundle.putString(Constants.COMIC_TITLE, title);
            bundle.putString(Constants.COMIC_DESC, desc);
            bundle.putParcelable(Constants.COMIC_DETAIL_CHARACTERS, comics);
            movieDetail = new ComicDetailFragment();
            movieDetail.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, movieDetail)
                    .commit();

        }
    }

    @Override
    public BaseActivity getActivity() {
        return null;
    }
}
