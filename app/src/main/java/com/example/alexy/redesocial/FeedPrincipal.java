package com.example.alexy.redesocial;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedPrincipal extends Fragment {


    private ViewGroup feed;

    public FeedPrincipal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_feed_principal, container, false);

        feed = v.findViewById(R.id.container);
        popularFeed();
        popularFeed();
        popularFeed();
        popularFeed();
        popularFeed();
        popularFeed();


        return v;
    }



    private void popularFeed(){
        CardView cardView = (CardView) getActivity().getLayoutInflater().inflate(R.layout.feed_principal_card,feed,false);
        TextView tv = (TextView) cardView.findViewById(R.id.publicacaoNome);
        tv.setText("GIBBY");
        feed.addView(cardView);
    }

}
