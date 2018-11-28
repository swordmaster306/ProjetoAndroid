package com.example.alexy.redesocial.Activities;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.alexy.redesocial.Fragments.AmizadesPendentesFragment;
import com.example.alexy.redesocial.Fragments.BuscaFragment;
import com.example.alexy.redesocial.Fragments.FeedPrincipalFragment;
import com.example.alexy.redesocial.Fragments.MeusAmigosFragment;
import com.example.alexy.redesocial.Fragments.PerfilFragment;
import com.example.alexy.redesocial.Fragments.SobreFragment;
import com.example.alexy.redesocial.R;
import com.example.alexy.redesocial.Singletons.RetrofitSingleton;
import com.example.alexy.redesocial.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FloatingActionButton publishFab;
    private AnimationDrawable animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TravarActivity();
        final ImageView loading = (ImageView)findViewById(R.id.imageView);
        animation = (AnimationDrawable)loading.getDrawable();
        animation.start();

        FeedPrincipalFragment frag = new FeedPrincipalFragment();
        frag.setAnimation(animation);
        frag.setLoading(loading);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,frag).commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        publishFab = (FloatingActionButton) findViewById(R.id.fabEdit);

        publishFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,PublishActivity.class);
                startActivity(i);
            }
        });

        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.isChecked()){
                    menuItem.setChecked(false);
                }else{
                    menuItem.setChecked(true);
                }

                drawerLayout.closeDrawers();


                int id = menuItem.getItemId();


                //Alterna o fragment do FrameLayout
                switch (id){
                    case R.id.publicacoes:
                        FeedPrincipalFragment frag = new FeedPrincipalFragment();
                        frag.setAnimation(animation);
                        frag.setLoading(loading);
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,frag).commit();
                        return true;
                    case R.id.meu_perfil:
                        //Carregar fragment de perfil
                        final PerfilFragment perfilFragment = new PerfilFragment();

                        Call<User> getPerfil = RetrofitSingleton.getInstance().redesocialapi.getPerfil(RetrofitSingleton.getInstance().token.userid);
                        getPerfil.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                User u = response.body();
                                perfilFragment.user = u;
                                perfilFragment.status = "Proprio";
                                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, perfilFragment).commit();
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Toast.makeText(MainActivity.this, "Erro na requisição de perfil", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return true;
                    case R.id.amigos:
                        MeusAmigosFragment meusAmigosFragment = new MeusAmigosFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,meusAmigosFragment).commit();
                        return true;
                    case R.id.amizadesPendentes:
                        AmizadesPendentesFragment amizadesPendentesFragment = new AmizadesPendentesFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,amizadesPendentesFragment).commit();
                        return true;
                    case R.id.configuracoes:
                        //Carregar possiveis configuracoes
                        return true;
                    case R.id.sobre:
                        SobreFragment sobreFrag = new SobreFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,sobreFrag).commit();
                        return true;
                    case R.id.sair:
                        //Deve confirmar e voltar para tela de login
                        Intent i = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(i);
                        return true;
                }


                return false;
            }

        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.botao_busca:
                //deve mostrar caixa de busca , quando o usuario digitar o texto e dar enter, executar comando abaixo com putextra do text
                BuscaFragment buscafrag = new BuscaFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,buscafrag).commit();
                return true;
        }
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Carrega o menu
        getMenuInflater().inflate(R.menu.main, menu);
        SearchView busca = (SearchView) menu.findItem(R.id.botao_busca).getActionView();

        busca.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle bundle = new Bundle();
                bundle.putString("busca", query);
                BuscaFragment buscafrag = new BuscaFragment();
                buscafrag.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,buscafrag).commit();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    public void TravarActivity(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void DestravarActivity(){
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

}
