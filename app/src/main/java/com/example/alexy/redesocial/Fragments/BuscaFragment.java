package com.example.alexy.redesocial.Fragments;

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

import com.example.alexy.redesocial.R;
import com.example.alexy.redesocial.Singletons.RetrofitSingleton;
import com.example.alexy.redesocial.models.User;
import com.example.alexy.redesocial.utils.ConversorBase64;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BuscaFragment extends Fragment {


    private ViewGroup busca;

    public BuscaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container2,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_busca_container, container2, false);

        busca = v.findViewById(R.id.container2);

        String texto_busca = getArguments().getString("busca");
        System.out.println(texto_busca);
        Call<List<User>> retornoBusca = RetrofitSingleton.getInstance().redesocialapi.buscarAmigos(texto_busca);
        Callback<List<User>> callbackBusca = new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                //Toast.makeText(getActivity(), String.valueOf(response.body().size()), Toast.LENGTH_SHORT).show();
                List<User> resultado = response.body();
                for(User u : resultado){
                    cardbusca(u);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getActivity(), "Erro na requisição de busca de amigos", Toast.LENGTH_SHORT).show();
            }
        };
        retornoBusca.enqueue(callbackBusca);


        return v;
    }


    private void cardbusca(User u) {
        CardView card = (CardView) getActivity().getLayoutInflater().inflate(R.layout.fragment_busca, busca, false);
        TextView t = (TextView) card.findViewById(R.id.ResultadoNome);
        TextView t2 = (TextView) card.findViewById(R.id.ResultadoDado);
        ImageView foto = (ImageView) card.findViewById(R.id.ResultadoImagem);

        t.setText(u.getNome());
        if(u.getFotoPerfil() != null)
            foto.setImageBitmap(ConversorBase64.b64tobitmap(u.fotoPerfil));
        busca.addView(card);
    }

}