package com.example.alexy.redesocial;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;



public class Busca extends Fragment {


    private ViewGroup busca;

    public Busca() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container2,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_busca_container, container2, false);

        busca = v.findViewById(R.id.container2);
        cardbusca();
        cardbusca();
        cardbusca();
        cardbusca();
        cardbusca();
        cardbusca();


        return v;
    }


    private void cardbusca() {
        CardView card = (CardView) getActivity().getLayoutInflater().inflate(R.layout.fragment_busca, busca, false);
        TextView t = (TextView) card.findViewById(R.id.ResultadoNome);
        TextView t2 = (TextView) card.findViewById(R.id.ResultadoDado);
        t.setText("Pessoa");
        t2.setText("Informação");
        busca.addView(card);
    }

}