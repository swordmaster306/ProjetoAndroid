package com.example.alexy.redesocial.Fragments;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.alexy.redesocial.Activities.MainActivity;
import com.example.alexy.redesocial.R;
import com.example.alexy.redesocial.Singletons.RetrofitSingleton;
import com.example.alexy.redesocial.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Configuracoes extends Fragment {

    EditText userName;
    EditText password;
    EditText passwordConfirmation;

    public Configuracoes() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container2, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_configuracoes, container2, false);
        final Retrofit retrofit = RetrofitSingleton.retrofit;
        userName = v.findViewById(R.id.novo_nome);
        password = v.findViewById(R.id.new_pass);
        passwordConfirmation = v.findViewById(R.id.new_pass_confirmation);
        Button updateProfileButton = v.findViewById(R.id.atualizar_info_button);
        Call<User> getPerfil = RetrofitSingleton.getInstance().redesocialapi.getPerfil(RetrofitSingleton.getInstance().token.userid);
        getPerfil.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User u = response.body();
                userName.setText(u.nome);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), "Erro na requisição de perfil", Toast.LENGTH_SHORT).show();
            }
        });
        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidForm()) {
                    User u = new User();
                    u.nome = userName.getText().toString().trim();
                    if (!password.getText().toString().isEmpty())
                        u.senha = password.getText().toString();
                    Call<User> editarPerfil = RetrofitSingleton.getInstance().redesocialapi.editarPerfil(u);
                    editarPerfil.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Toast.makeText(getActivity(), "Perfil atualizado com sucesso", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(getActivity(), "Erro ao atualizar informações do perfil", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        return v;
    }

    private Boolean isValidForm() {
        String userName = this.userName.getText().toString().trim();
        String password = this.password.getText().toString().trim();
        String passwordConfirmation = this.passwordConfirmation.getText().toString().trim();
        if (userName.isEmpty()) {
            this.userName.setError("Novo nome inválido");
            return false;
        };
        if (!password.isEmpty()) {
            if (!password.equals(passwordConfirmation)) {
                this.passwordConfirmation.setError("A senha e a confirmação precisam ser iguais");
                return false;
            }
        }
        return true;
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
