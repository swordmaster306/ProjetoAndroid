package com.example.alexy.redesocial;
import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FeedPrincipal frag = new FeedPrincipal();
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

                        return true;
                    case R.id.meu_perfil:

                        return true;
                    case R.id.amigos:

                        return true;
                    case R.id.configuracoes:

                        return true;
                    case R.id.sobre:

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
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
