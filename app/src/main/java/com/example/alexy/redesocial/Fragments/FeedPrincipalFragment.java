package com.example.alexy.redesocial.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexy.redesocial.R;
import com.example.alexy.redesocial.Singletons.RetrofitSingleton;
import com.example.alexy.redesocial.models.Historia;
import com.example.alexy.redesocial.models.LikeDislike;
import com.example.alexy.redesocial.utils.ConversorBase64;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedPrincipalFragment extends Fragment {


    private ViewGroup feed;

    public FeedPrincipalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_feed_principal, container, false);

        feed = v.findViewById(R.id.container);


        Call<List<Historia>> getFeedApi = RetrofitSingleton.getInstance().redesocialapi.getPerfilHistorias(RetrofitSingleton.getInstance().token.userid);
        Callback<List<Historia>> callbackFeed =  new Callback<List<Historia>>() {
            @Override
            public void onResponse(Call<List<Historia>> call, Response<List<Historia>> response) {
                List<Historia> historiasAmigos = response.body();
                for (Historia h : historiasAmigos)
                {
                    popularFeed(h);
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
        final Historia historia = h;
        CardView cardView;
        if(historia.foto != null){
            cardView = (CardView) getActivity().getLayoutInflater().inflate(R.layout.feed_principal_card_com_foto,feed,false);
            TextView nome = (TextView) cardView.findViewById(R.id.publicacaoNome);
            TextView data = (TextView) cardView.findViewById(R.id.publicacaoDataHora);
            TextView mensagem = (TextView) cardView.findViewById(R.id.publicacaoTexto);
            final TextView likes = (TextView) cardView.findViewById(R.id.publicacaoLikeCounter);
            final TextView dislikes = (TextView) cardView.findViewById(R.id.publicacaoDislikeCounter);
            ImageView foto = (ImageView) cardView.findViewById(R.id.publicacaoFoto);
            final Button likeButton = (Button) cardView.findViewById(R.id.publicacaoLikeButton);
            final Button dislikeButton = (Button) cardView.findViewById(R.id.publicacaoDislikeButton);
            final Button deletarButton = (Button) cardView.findViewById(R.id.deletarButton);

            if(historia.userId != RetrofitSingleton.getInstance().token.userid){
                deletarButton.setVisibility(View.GONE);
                deletarButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Historia hist = new Historia();
                        hist.id = historia.id;
                        Call<Void> deletarHistoriaCall = RetrofitSingleton.getInstance().redesocialapi.deletarHistoria(hist);
                        Callback<Void> deletarHistoriaCallback = new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        };
                        deletarHistoriaCall.enqueue(deletarHistoriaCallback);
                    }
                });
            }else{

            }

            switch(historia.deulike){
                case 1:
                    likeButton.setEnabled(false);
                    break;
                case 2:
                    dislikeButton.setEnabled(false);
                    break;
            }
            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LikeDislike ld = new LikeDislike();
                    ld.historiaid = historia.id;
                    ld.userid = RetrofitSingleton.getInstance().token.userid;
                    ld.likedislike = 1;
                    Call<Void> likeCall = RetrofitSingleton.getInstance().redesocialapi.darlikedislike(ld);
                    Callback<Void> likeCallback = new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(getActivity(), "Like realizado com sucesso", Toast.LENGTH_SHORT).show();
                            if(!dislikeButton.isEnabled()) {
                                dislikeButton.setEnabled(true);
                            }
                            likeButton.setEnabled(false);
                            likes.setText(String.valueOf(Integer.valueOf(likes.getText().toString()) +1));
                            dislikes.setText(String.valueOf(Integer.valueOf(dislikes.getText().toString())-1));
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getActivity(), "Erro no like", Toast.LENGTH_SHORT).show();
                        }
                    };
                    likeCall.enqueue(likeCallback);
                }
            });
            dislikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LikeDislike ld = new LikeDislike();
                    ld.historiaid = historia.id;
                    ld.userid = RetrofitSingleton.getInstance().token.userid;
                    ld.likedislike = 0;
                    Call<Void> dislikeCall = RetrofitSingleton.getInstance().redesocialapi.darlikedislike(ld);
                    Callback<Void> likeCallback = new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(getActivity(), "Dislike realizado com sucesso", Toast.LENGTH_SHORT).show();
                            if(!likeButton.isEnabled()) {
                                likeButton.setEnabled(true);
                            }
                            dislikeButton.setEnabled(false);
                            likes.setText(String.valueOf(Integer.valueOf(likes.getText().toString()) -1));
                            dislikes.setText(String.valueOf(Integer.valueOf(dislikes.getText().toString())+1));
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getActivity(), "Erro no dislike", Toast.LENGTH_SHORT).show();
                        }
                    };
                    dislikeCall.enqueue(likeCallback);
                }
            });

            nome.setText(historia.getUsername());
            data.setText(String.valueOf(historia.deulike));
            mensagem.setText(historia.getMensagem());
            likes.setText(String.valueOf(h.getLikes()));
            dislikes.setText(String.valueOf(h.getDislikes()));
            foto.setImageBitmap(ConversorBase64.b64tobitmap(h.foto));
        }else{
            cardView = (CardView) getActivity().getLayoutInflater().inflate(R.layout.feed_principal_card_sem_foto,feed,false);
            TextView nome = (TextView) cardView.findViewById(R.id.publicacaoNome);
            TextView data = (TextView) cardView.findViewById(R.id.publicacaoDataHora);
            TextView mensagem = (TextView) cardView.findViewById(R.id.publicacaoTexto);
            final TextView likes = (TextView) cardView.findViewById(R.id.publicacaoLikeCounter);
            final TextView dislikes = (TextView) cardView.findViewById(R.id.publicacaoDislikeCounter);
            final Button likeButton = (Button) cardView.findViewById(R.id.publicacaoLikeButton);
            final Button dislikeButton = (Button) cardView.findViewById(R.id.publicacaoDislikeButton);
            final Button deletarButton = (Button) cardView.findViewById(R.id.deletarButton);
            switch(historia.deulike){
                case 1:
                    likeButton.setEnabled(false);
                    break;
                case 2:
                    dislikeButton.setEnabled(false);
                    break;
            }
            if(historia.userId != RetrofitSingleton.getInstance().token.userid){
                deletarButton.setVisibility(View.GONE);
                deletarButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Historia hist = new Historia();
                        hist.id = historia.id;
                        Call<Void> deletarHistoriaCall = RetrofitSingleton.getInstance().redesocialapi.deletarHistoria(hist);
                        Callback<Void> deletarHistoriaCallback = new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        };
                        deletarHistoriaCall.enqueue(deletarHistoriaCallback);
                    }
                });
            }

            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LikeDislike ld = new LikeDislike();
                    ld.historiaid = historia.id;
                    ld.userid = RetrofitSingleton.getInstance().token.userid;
                    ld.likedislike = 1;
                    Call<Void> likeCall = RetrofitSingleton.getInstance().redesocialapi.darlikedislike(ld);
                    Callback<Void> likeCallback = new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(getActivity(), "Like realizado com sucesso", Toast.LENGTH_SHORT).show();
                            if(!dislikeButton.isEnabled()) {
                                dislikeButton.setEnabled(true);
                            }
                            likeButton.setEnabled(false);
                            likes.setText(String.valueOf(Integer.valueOf(likes.getText().toString()) +1));
                            dislikes.setText(String.valueOf(Integer.valueOf(dislikes.getText().toString())-1));
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getActivity(), "Erro no like", Toast.LENGTH_SHORT).show();
                        }
                    };
                    likeCall.enqueue(likeCallback);
                }
            });
            dislikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LikeDislike ld = new LikeDislike();
                    ld.historiaid = historia.id;
                    ld.userid = RetrofitSingleton.getInstance().token.userid;
                    ld.likedislike = 0;
                    Call<Void> dislikeCall = RetrofitSingleton.getInstance().redesocialapi.darlikedislike(ld);
                    Callback<Void> likeCallback = new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(getActivity(), "Dislike realizado com sucesso", Toast.LENGTH_SHORT).show();
                            if(!likeButton.isEnabled()) {
                                likeButton.setEnabled(true);
                            }
                            dislikeButton.setEnabled(false);
                            likes.setText(String.valueOf(Integer.valueOf(likes.getText().toString()) -1));
                            dislikes.setText(String.valueOf(Integer.valueOf(dislikes.getText().toString())+1));
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getActivity(), "Erro no dislike", Toast.LENGTH_SHORT).show();
                        }
                    };
                    dislikeCall.enqueue(likeCallback);
                }
            });

            nome.setText(historia.getUsername());
            data.setText(String.valueOf(historia.deulike));
            mensagem.setText(historia.getMensagem());
            likes.setText(String.valueOf(historia.getLikes()));
            dislikes.setText(String.valueOf(historia.getDislikes()));
        }



        feed.addView(cardView);
    }

}
