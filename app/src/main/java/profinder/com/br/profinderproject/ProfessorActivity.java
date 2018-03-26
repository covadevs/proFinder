package profinder.com.br.profinderproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

public class ProfessorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Usuario usuario;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);
        initComponents();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfessorActivity.this, CadastroProjetoActivity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });
    }

    private void initComponents() {
        this.usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_professor);
        this.mActionBarDrawerToggle = new ActionBarDrawerToggle(this, this.mDrawerLayout,
                R.string.open, R.string.close);
        this.mDrawerLayout.addDrawerListener(this.mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navgation_view);
        navigationView.setNavigationItemSelectedListener(this);

        setTitle("Início");
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment, homeFragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(this.mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (id) {
            case(R.id.home_nav):
                setTitle("Início");
                HomeFragment homeFragment = new HomeFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment, homeFragment).commit();
                mDrawerLayout.closeDrawers();
                break;
            case(R.id.meus_projetos):
                setTitle("Meus Projetos");
                MeusProjetosFragment meusProjetosFragment = new MeusProjetosFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment, meusProjetosFragment).commit();
                mDrawerLayout.closeDrawers();
                break;
            case(R.id.notificacoes):
                setTitle("Notificações");
                NotificacoesFragment notificacoesFragment = new NotificacoesFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment, notificacoesFragment).commit();
                mDrawerLayout.closeDrawers();
                break;
            case(R.id.sair):
                Intent intent = new Intent(ProfessorActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return false;
    }
}
