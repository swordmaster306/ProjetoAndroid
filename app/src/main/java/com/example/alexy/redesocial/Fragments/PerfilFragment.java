package com.example.alexy.redesocial.Fragments;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexy.redesocial.Activities.MainActivity;
import com.example.alexy.redesocial.R;
import com.example.alexy.redesocial.Singletons.RetrofitSingleton;
import com.example.alexy.redesocial.models.Historia;
import com.example.alexy.redesocial.models.LikeDislike;
import com.example.alexy.redesocial.models.User;
import com.example.alexy.redesocial.utils.ConversorBase64;
import com.example.alexy.redesocial.utils.Formatter;
import com.example.alexy.redesocial.utils.Status;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    public String status;
    public User user;
    private ViewGroup feed;
    private ImageView fotoPerfil;
    private TextView nomePerfil;
    RetrofitSingleton retrofit;


    private Button editarPerfilButton;
    private Button addDeleteButton;
    MainActivity m;


    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_perfil, container, false);
        m = (MainActivity) getActivity();
        m.TravarActivity();
        editarPerfilButton = v.findViewById(R.id.editarPerfilButton);
        if(RetrofitSingleton.getInstance().token.userid != user.userId){
            editarPerfilButton.setVisibility(View.GONE);
        }

        addDeleteButton = v.findViewById(R.id.addDeleteButton);




        if(status.equals("Inexistente")){//nao eh amigo
            //Colocar icone de adicionar
            addDeleteButton.setText("Adicionar");
            addDeleteButton.setOnClickListener(adicionarListener);
        }else if(status.equals("Aprovada")){//eh amigo
            //Colocar icone de deletar
            addDeleteButton.setText("Remover");
            addDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Call<Void> deletaramigo = RetrofitSingleton.getInstance().redesocialapi.deletaramigo(user.userId,RetrofitSingleton.getInstance().token.userid);
                    deletaramigo.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            //Colocar icone de adicionar
                            addDeleteButton.setText("Adicionar");
                            addDeleteButton.setOnClickListener(adicionarListener);
                            Toast.makeText(getActivity(), "Amigo deletado com sucesso", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getActivity(), "Falha na requisição", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else if(status.equals("Proprio")){//eh voce

            addDeleteButton.setVisibility(View.GONE);
        }else if (status.equals("Pendente")){
            addDeleteButton.setText("Pendente");
            addDeleteButton.setEnabled(false);
        }



        feed = v.findViewById(R.id.container_perfil);
        fotoPerfil = v.findViewById(R.id.fotoPerfil);
        nomePerfil = v.findViewById(R.id.nomePerfil);

        if(user.getFotoPerfil() != null){
            fotoPerfil.setImageBitmap(ConversorBase64.b64tobitmap(user.getFotoPerfil()));
        }
        nomePerfil.setText(user.getNome());


        retrofit = RetrofitSingleton.getInstance();



        Call<List<Historia>> getFeedApi = retrofit.redesocialapi.getPerfilHistorias(user.userId);
        Callback<List<Historia>> callbackFeed =  new Callback<List<Historia>>() {
            @Override
            public void onResponse(Call<List<Historia>> call, Response<List<Historia>> response) {
                List<Historia> historias = response.body();
                for (Historia hist: historias) {
                    popularFeed(hist);
                }
                m.DestravarActivity();
            }

            @Override
            public void onFailure(Call<List<Historia>> call, Throwable t) {
                Toast.makeText(getActivity(), "Falha na requisição", Toast.LENGTH_SHORT).show();
            }
        };
        getFeedApi.enqueue(callbackFeed);

        return v;
    }


    private void popularFeed(final Historia h){
        //Configurar like e dislike, utilizar h.deulike para saber se o usuario deu like na historia e tratar devidamene
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        int layout = h.foto != null ? R.layout.feed_principal_card_com_foto : R.layout.feed_principal_card_sem_foto;

        CardView cardView = (CardView) layoutInflater.inflate(layout, feed,false);
        TextView nome = (TextView) cardView.findViewById(R.id.publicacaoNome);
        TextView data = (TextView) cardView.findViewById(R.id.publicacaoDataHora);
        TextView mensagem = (TextView) cardView.findViewById(R.id.publicacaoTexto);
        final TextView likes = (TextView) cardView.findViewById(R.id.publicacaoLikeCounter);
        final TextView dislikes = (TextView) cardView.findViewById(R.id.publicacaoDislikeCounter);

        final ImageView likeButton = cardView.findViewById(R.id.likeButton);
        final ImageView dislikeButton = cardView.findViewById(R.id.dislikeButton);

        final Button deletarButton = (Button) cardView.findViewById(R.id.deletarButton);




        if(h.userId != RetrofitSingleton.getInstance().token.userid){
            deletarButton.setVisibility(View.GONE);
        }else{
            deletarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Historia hist = new Historia();
                    hist.id = h.id;
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
                ld.historiaid = h.id;
                ld.userid = RetrofitSingleton.getInstance().token.userid;
                ld.likedislike = 1;
                Call<Void> likeCall = RetrofitSingleton.getInstance().redesocialapi.darlikedislike(ld);
                Callback<Void> likeCallback = new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(getActivity(), "Like realizado com sucesso", Toast.LENGTH_SHORT).show();
                        dislikeButton.setEnabled(true);
                        dislikeButton.clearColorFilter();
                        likeButton.setEnabled(false);
                        likeButton.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
                        likes.setText(String.valueOf(Integer.valueOf(likes.getText().toString()) +1));
                        if(h.deulike == Status.DISLIKED) {
                            dislikes.setText(String.valueOf(Integer.valueOf(dislikes.getText().toString()) - 1));
                        }
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
                ld.historiaid = h.id;
                ld.userid = RetrofitSingleton.getInstance().token.userid;
                ld.likedislike = 0;
                Call<Void> dislikeCall = RetrofitSingleton.getInstance().redesocialapi.darlikedislike(ld);
                Callback<Void> likeCallback = new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(getActivity(), "Dislike realizado com sucesso", Toast.LENGTH_SHORT).show();
                        likeButton.setEnabled(true);
                        likeButton.clearColorFilter();
                        dislikeButton.setEnabled(false);
                        dislikeButton.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                        if(h.deulike == Status.LIKED) {
                            likes.setText(String.valueOf(Integer.valueOf(likes.getText().toString()) -1));
                        }
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


        switch (h.deulike) {
            case Status.LIKED:
                likeButton.setSelected(true);
                likeButton.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
                break;
            case Status.DISLIKED:
                dislikeButton.setSelected(true);
                dislikeButton.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                break;
        }


        String date;
        try {
            date = Formatter.date(h.getData());
        } catch (Exception e) {
            e.printStackTrace();
            date = "";
        }

        nome.setText(h.getUsername());
        data.setText(date);
        mensagem.setText(h.getMensagem());
        likes.setText(String.valueOf(h.getLikes()));
        dislikes.setText(String.valueOf(h.getDislikes()));
        if(h.foto != null) {
            ImageView foto = cardView.findViewById(R.id.publicacaoFoto);
            foto.setImageBitmap(ConversorBase64.b64tobitmap(h.foto));
        }

        feed.addView(cardView);
    }





    final View.OnClickListener adicionarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Call<Void> adicionarAmigo = RetrofitSingleton.getInstance().redesocialapi.adicionaramigo(user.userId,RetrofitSingleton.getInstance().token.userid);
            adicionarAmigo.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    //Colocar icone de pendente
                    addDeleteButton.setText("Pendente");
                    addDeleteButton.setEnabled(false);
                    Toast.makeText(getActivity(), "Amigo adicionado com sucesso", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getActivity(), "Falha na requisição", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
}
