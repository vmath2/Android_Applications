package com.example.fragmentbasics;

import android.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements HeadlineFragment.OnHeadlineSelectedListener {
@Override
public void onAttachFragment(Fragment fragment){
    if(fragment instanceof HeadlineFragment){
        HeadlineFragment headlinesFragment = (HeadlineFragment) fragment;
        headlinesFragment.setOnHeadlineSelectedListener(this);
    }
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onArticleSelected(int position){
        NewsFragment newsFragment = (NewsFragment) getSupportFragmentManager().findFragmentById(R.id.news_fragment);
        if (newsFragment !=null){
            newsFragment.updateArticleView(position);
        }else{
            NewsFragment newFragment = new NewsFragment();
            Bundle args = new Bundle();
            args.putInt(newFragment.ARG_POSITION, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
