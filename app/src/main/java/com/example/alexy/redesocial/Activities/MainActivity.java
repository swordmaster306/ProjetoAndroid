package com.example.alexy.redesocial.Activities;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.alexy.redesocial.Fragments.FeedPrincipalFragment;
import com.example.alexy.redesocial.Fragments.MeusAmigosFragment;
import com.example.alexy.redesocial.Fragments.SobreFragment;
import com.example.alexy.redesocial.R;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    Busca buscafrag = new Busca();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FeedPrincipalFragment frag = new FeedPrincipalFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,frag).commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

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
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,frag).commit();
                        return true;
                    case R.id.meu_perfil:
                    case R.id.amigos:
                        MeusAmigosFragment meusAmigosFragment = new MeusAmigosFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,meusAmigosFragment).commit();
                        return true;
                    case R.id.configuracoes:
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.botao_busca:
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
        return true;
    }

}