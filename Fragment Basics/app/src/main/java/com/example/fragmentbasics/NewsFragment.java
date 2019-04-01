package com.example.fragmentbasics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewsFragment extends Fragment {
    static final String ARG_POSITION = "position";
    int mCurrentPosition = -1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_news,container, false);
         }
         @Override
    public void onStart(){
        super.onStart();
        Bundle args = getArguments();
        if(args !=null){
            updateArticleView(args.getInt(ARG_POSITION));

        }
        else if(mCurrentPosition!=-1){
            updateArticleView(mCurrentPosition);
        }
        }
        public void updateArticleView(int position){
            TextView article =  (TextView)getActivity().findViewById(R.id.news);
            article.setText(Ipsum.Articles[position]);
            mCurrentPosition = position;
        }
        @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_POSITION, mCurrentPosition);
        }
}
