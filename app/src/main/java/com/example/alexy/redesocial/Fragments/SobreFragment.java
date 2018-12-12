package com.example.alexy.redesocial.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.alexy.redesocial.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SobreFragment extends Fragment {


    public SobreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        int count = getActivity().getSupportFragmentManager().getFragments().size();
        Toast.makeText(getActivity(), String.valueOf(count), Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.activity_sobre, container, false);
    }

}
