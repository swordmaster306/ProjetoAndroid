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
import com.example.alexy.redesocial.utils.ConversorBase64;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {


    private ViewGroup feed;
    RetrofitSingleton retrofit;

    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_perfil, container, false);

        feed = v.findViewById(R.id.container_perfil);
        retrofit = RetrofitSingleton.getInstance();

        Call<List<Historia>> getFeedApi = retrofit.redesocialapi.getPerfilHistorias(RetrofitSingleton.getInstance().token.userid);
        Callback<List<Historia>> callbackFeed =  new Callback<List<Historia>>() {
            @Override
            public void onResponse(Call<List<Historia>> call, Response<List<Historia>> response) {
                List<Historia> historias = response.body();
                for (Historia hist: historias) {
                    popularFeed(hist);
                }
            }

            @Override
            public void onFailure(Call<List<Historia>> call, Throwable t) {
                Toast.makeText(getActivity(), "Falha na requisição", Toast.LENGTH_SHORT).show();
            }
        };
        getFeedApi.enqueue(callbackFeed);

        return v;
    }


    private void popularFeed(Historia h){
        //Configurar like e dislike, utilizar h.deulike para saber se o usuario deu like na historia e tratar devidamene
        CardView cardView;
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        if(h.foto != null) {
            cardView = (CardView) layoutInflater.inflate(R.layout.feed_principal_card_com_foto, feed,false);
            TextView nome = (TextView) cardView.findViewById(R.id.publicacaoNome);
            TextView data = (TextView) cardView.findViewById(R.id.publicacaoDataHora);
            TextView mensagem = (TextView) cardView.findViewById(R.id.publicacaoTexto);
            TextView likes = (TextView) cardView.findViewById(R.id.publicacaoLikeCounter);
            TextView dislikes = (TextView) cardView.findViewById(R.id.publicacaoDislikeCounter);
            ImageView foto = (ImageView) cardView.findViewById(R.id.publicacaoFoto);

            nome.setText(h.getUsername());
            data.setText(h.getData());
            mensagem.setText(h.getMensagem());
            likes.setText(String.valueOf(h.getLikes()));
            dislikes.setText(String.valueOf(h.getDislikes()));
            foto.setImageBitmap(ConversorBase64.b64tobitmap(h.foto));
        } else {
            cardView = (CardView) layoutInflater.inflate(R.layout.feed_principal_card_sem_foto, feed,false);
            TextView nome = (TextView) cardView.findViewById(R.id.publicacaoNome);
            TextView data = (TextView) cardView.findViewById(R.id.publicacaoDataHora);
            TextView mensagem = (TextView) cardView.findViewById(R.id.publicacaoTexto);
            TextView likes = (TextView) cardView.findViewById(R.id.publicacaoLikeCounter);
            TextView dislikes = (TextView) cardView.findViewById(R.id.publicacaoDislikeCounter);

            nome.setText(h.getUsername());
            data.setText(h.getData());
            mensagem.setText(h.getMensagem());
            likes.setText(String.valueOf(h.getLikes()));
            dislikes.setText(String.valueOf(h.getDislikes()));
        }



        feed.addView(cardView);
    }


}
