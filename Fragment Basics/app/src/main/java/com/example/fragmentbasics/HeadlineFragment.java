package com.example.fragmentbasics;


import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HeadlineFragment extends ListFragment {
    OnHeadlineSelectedListener mCallBack;

    public void setOnHeadlineSelectedListener(MainActivity mainActivity) {
        mCallBack = mainActivity;
    }


    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            mCallBack = (OnHeadlineSelectedListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement OnHeadlineSelectedListener");
        }
    }
@Override
public void onListItemClick(ListView l, View v, int position, long id){
    mCallBack.onArticleSelected(position);
    getListView().setItemChecked(position,true);

}

@Override
    public void onCreate(Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        int layout = android.R.layout.simple_list_item_activated_1;
        setListAdapter(new ArrayAdapter<String>(getActivity(),layout,Ipsum.Headlines));
}
@Override
    public void onStart(){
        super.onStart();
        if(getFragmentManager().findFragmentById(R.id.news_fragment)!=null){
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
}
}
