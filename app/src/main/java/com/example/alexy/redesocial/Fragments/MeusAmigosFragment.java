package com.example.alexy.redesocial.Fragments;


import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexy.redesocial.R;
import com.example.alexy.redesocial.Singletons.RetrofitSingleton;
import com.example.alexy.redesocial.models.Historia;
import com.example.alexy.redesocial.models.User;
import com.example.alexy.redesocial.utils.ConversorBase64;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeusAmigosFragment extends Fragment {


    private ViewGroup feed;

    public MeusAmigosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_meus_amigos, container, false);

        feed = v.findViewById(R.id.container);

        Call<List<User>> getFeedApi = RetrofitSingleton.getInstance().redesocialapi.getMeusAmigos(RetrofitSingleton.getInstance().token.userid);
        Callback<List<User>> callbackMeusAmigos =  new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> meusAmigos = response.body();
                for (User u : meusAmigos)
                {
                    popularFeed(u);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        };
        getFeedApi.enqueue(callbackMeusAmigos);
        return v;
    }



    private void popularFeed(final User u){

        CardView card = (CardView) getActivity().getLayoutInflater().inflate(R.layout.fragment_busca, feed, false);
        TextView t = (TextView) card.findViewById(R.id.ResultadoNome);
        TextView t2 = (TextView) card.findViewById(R.id.ResultadoDado);
        ImageView foto = (ImageView) card.findViewById(R.id.ResultadoImagem);
        t.setText(u.getNome());
        t2.setText(u.getQtdHistorias().toString());


        if(u.getFotoPerfil() != null)
            foto.setImageBitmap(ConversorBase64.b64tobitmap(u.getFotoPerfil()));

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerfilFragment perfilFragment = new PerfilFragment();
                perfilFragment.user = u;
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, perfilFragment).commit();
            }
        });

        feed.addView(card);

    }

}
