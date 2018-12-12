package com.example.alexy.redesocial.Fragments;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexy.redesocial.Activities.MainActivity;
import com.example.alexy.redesocial.R;
import com.example.alexy.redesocial.Singletons.RetrofitSingleton;
import com.example.alexy.redesocial.models.Amizade;
import com.example.alexy.redesocial.models.User;
import com.example.alexy.redesocial.utils.ConversorBase64;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BuscaFragment extends Fragment {


    private ViewGroup busca;
    MainActivity m;

    public BuscaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container2,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_busca_container, container2, false);
        m = (MainActivity) getActivity();
        m.TravarActivity();
        busca = v.findViewById(R.id.container2);

        String texto_busca = getArguments().getString("busca");
        System.out.println(texto_busca);
        Call<List<User>> retornoBusca = RetrofitSingleton.getInstance().redesocialapi.buscarAmigos(texto_busca,RetrofitSingleton.getInstance().token.userid);
        Callback<List<User>> callbackBusca = new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                BuscaFragment.this.animation.stop();
                BuscaFragment.this.loading.setVisibility(View.GONE);
                List<User> resultado = response.body();
                for(User u : resultado){
                    cardbusca(u);
                }
                m.DestravarActivity();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                BuscaFragment.this.animation.stop();
                BuscaFragment.this.loading.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Erro na requisição de busca de amigos", Toast.LENGTH_SHORT).show();
            }
        };
        retornoBusca.enqueue(callbackBusca);


        return v;
    }


    private void cardbusca(final User u) {
        CardView card = (CardView) getActivity().getLayoutInflater().inflate(R.layout.fragment_busca, busca, false);
        TextView t = (TextView) card.findViewById(R.id.ResultadoNome);
        //TextView t2 = (TextView) card.findViewById(R.id.ResultadoDado);
        ImageView foto = (ImageView) card.findViewById(R.id.ResultadoImagem);

        t.setText(u.getNome());
        if(u.getFotoPerfil() != null)
            foto.setImageBitmap(ConversorBase64.b64tobitmap(u.fotoPerfil));

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PerfilFragment perfilFragment = new PerfilFragment();
                perfilFragment.user = u;
                Call<Amizade> amizadeCall = RetrofitSingleton.getInstance().redesocialapi.getstatusAmizade(u.userId,RetrofitSingleton.getInstance().token.userid);
                amizadeCall.enqueue(new Callback<Amizade>() {
                    @Override
                    public void onResponse(Call<Amizade> call, Response<Amizade> response) {
                        perfilFragment.status = response.body().status;
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, perfilFragment).commit();
                    }

                    @Override
                    public void onFailure(Call<Amizade> call, Throwable t) {
                        Toast.makeText(getActivity(), "Falha na requisição", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        busca.addView(card);
    }
    AnimationDrawable animation;
    public void setAnimation(AnimationDrawable animation)
    {
        this.animation = animation;
    }

    ImageView loading;
    public void setLoading(ImageView loading)
    {
        this.loading = loading;
    }
}