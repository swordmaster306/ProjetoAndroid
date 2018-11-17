package com.example.alexy.redesocial.Fragments;


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
                Toast.makeText(getActivity(), meusAmigos.get(0).getNome(), Toast.LENGTH_SHORT).show();
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



    private void popularFeed(User u){

        CardView cardView = (CardView) getActivity().getLayoutInflater().inflate(R.layout.usuario_card,feed,false);
        TextView nome = (TextView) cardView.findViewById(R.id.publicacaoNome);
        ImageView foto = (ImageView) cardView.findViewById(R.id.publicacaoFoto);

        nome.setText(u.getNome());
        if(u.getFotoPerfil() != null)
            foto.setImageBitmap(ConversorBase64.b64tobitmap(u.getFotoPerfil()));

        feed.addView(cardView);
    }

}
