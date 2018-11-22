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
import com.example.alexy.redesocial.utils.Formatter;

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


        Call<List<Historia>> getFeedApi = RetrofitSingleton.getInstance().redesocialapi.getFeedPrincipal(RetrofitSingleton.getInstance().token.userid);
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



    private void popularFeed(final Historia historia){
        //Configurar like e dislike, utilizar h.deulike para saber se o usuario deu like na historia e tratar devidamene
        int layout = historia.foto != null ? R.layout.feed_principal_card_com_foto : R.layout.feed_principal_card_sem_foto;
        CardView cardView;
        cardView = (CardView) getActivity().getLayoutInflater().inflate(layout, feed, false);
        TextView nome = (TextView) cardView.findViewById(R.id.publicacaoNome);
        TextView data = (TextView) cardView.findViewById(R.id.publicacaoDataHora);
        TextView mensagem = (TextView) cardView.findViewById(R.id.publicacaoTexto);
        final TextView likes = (TextView) cardView.findViewById(R.id.publicacaoLikeCounter);
        final TextView dislikes = (TextView) cardView.findViewById(R.id.publicacaoDislikeCounter);
        final Button likeButton = (Button) cardView.findViewById(R.id.publicacaoLikeButton);
        final Button dislikeButton = (Button) cardView.findViewById(R.id.publicacaoDislikeButton);
        final Button deletarButton = (Button) cardView.findViewById(R.id.deletarButton);

        if(historia.userId != RetrofitSingleton.getInstance().token.userid){
            deletarButton.setVisibility(View.GONE);
        }else{
            deletarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Historia hist = new Historia();
                    hist.id = historia.id;
                    Call<Void> deletarHistoriaCall = RetrofitSingleton.getInstance().redesocialapi.deletarHistoria(hist);
                    Callback<Void> deletarHistoriaCallback = new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            ((ViewGroup)deletarButton.getParent().getParent()).removeView((ViewGroup)deletarButton.getParent());
                            Toast.makeText(getActivity(), "História deletada", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getActivity(), "Erro na requisição de delete de história", Toast.LENGTH_SHORT).show();
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

        switch(historia.deulike){
            case 1:
                likeButton.setEnabled(false);
                break;
            case 2:
                dislikeButton.setEnabled(false);
                break;
        }

        nome.setText(historia.getUsername());
        String formattedDate;
        try {
            formattedDate = Formatter.date(historia.getData());
        } catch (Exception e) {
            e.printStackTrace();
            formattedDate = "";
        }
        data.setText(formattedDate);
        mensagem.setText(historia.getMensagem());
        likes.setText(String.valueOf(historia.getLikes()));
        dislikes.setText(String.valueOf(historia.getDislikes()));

        if(historia.foto != null){
            ImageView foto = (ImageView) cardView.findViewById(R.id.publicacaoFoto);
            foto.setImageBitmap(ConversorBase64.b64tobitmap(historia.foto));
        }
        feed.addView(cardView);
    }

}
