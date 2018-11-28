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

import com.example.alexy.redesocial.Activities.MainActivity;
import com.example.alexy.redesocial.Fragments.PerfilFragment;
import com.example.alexy.redesocial.R;
import com.example.alexy.redesocial.Singletons.RetrofitSingleton;
import com.example.alexy.redesocial.models.User;
import com.example.alexy.redesocial.utils.ConversorBase64;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AmizadesPendentesFragment extends Fragment {


    private ViewGroup busca;
    MainActivity m;

    public AmizadesPendentesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container2,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_busca_container, container2, false);

        m = (MainActivity) getActivity();
        m.TravarActivity();
        busca = v.findViewById(R.id.container2);

        Call<List<User>> retornoBusca = RetrofitSingleton.getInstance().redesocialapi.getAmizadesPendentes(RetrofitSingleton.getInstance().token.userid);
        Callback<List<User>> callbackBusca = new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                //Toast.makeText(getActivity(), String.valueOf(response.body().size()), Toast.LENGTH_SHORT).show();
                List<User> resultado = response.body();
                for(User u : resultado){
                    cardbusca(u);
                }
                m.DestravarActivity();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getActivity(), "Erro na requisição de busca de amigos", Toast.LENGTH_SHORT).show();
            }
        };
        retornoBusca.enqueue(callbackBusca);


        return v;
    }


    private void cardbusca(final User u) {
        CardView card = (CardView) getActivity().getLayoutInflater().inflate(R.layout.fragment_amizade_pendente_card, busca, false);
        TextView t = (TextView) card.findViewById(R.id.ResultadoNome);
        ImageView foto = (ImageView) card.findViewById(R.id.ResultadoImagem);
        final Button aceitarButton = card.findViewById(R.id.aceitarButton);
        final Button rejeitarButton = card.findViewById(R.id.rejeitarButton);


        t.setText(u.getNome());
        if(u.getFotoPerfil() != null)
            foto.setImageBitmap(ConversorBase64.b64tobitmap(u.fotoPerfil));

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerfilFragment perfilFragment = new PerfilFragment();
                perfilFragment.user = u;
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, perfilFragment).commit();
            }
        });

        aceitarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> aceitar = RetrofitSingleton.getInstance().redesocialapi.aceitaramizade(u.userId,RetrofitSingleton.getInstance().token.userid);
                aceitar.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        ((ViewGroup)aceitarButton.getParent().getParent()).removeView((ViewGroup)aceitarButton.getParent());
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getActivity(), "Falha na requisição", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        rejeitarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> rejeitar = RetrofitSingleton.getInstance().redesocialapi.rejeitaramizade(u.userId,RetrofitSingleton.getInstance().token.userid);
                rejeitar.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        ((ViewGroup)rejeitarButton.getParent().getParent()).removeView((ViewGroup)rejeitarButton.getParent());
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getActivity(), "Falha na requisição", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        busca.addView(card);
    }

}
