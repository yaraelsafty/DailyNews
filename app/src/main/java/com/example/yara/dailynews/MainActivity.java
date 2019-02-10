package com.example.yara.dailynews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.yara.dailynews.UI.FavouriteFragment;
import com.example.yara.dailynews.UI.LoginActivity;
import com.example.yara.dailynews.UI.NewsFragment;
import com.example.yara.dailynews.UI.SignUpActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState != null) {
            NewsFragment newsFragment = NewsFragment.newInstance("eg", "");
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frame, newsFragment)
                    .commit();
        }


        //save user data
        prefs = this.getSharedPreferences(getString(R.string.ref_key), Context.MODE_PRIVATE);
        editor = prefs.edit();

        mAuth = FirebaseAuth.getInstance();


        NewsFragment newsFragment = new NewsFragment().newInstance("eg", "");
        ;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, newsFragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_TopHeadLines) {
            NewsFragment newsFragment = new NewsFragment().newInstance("eg", "");
            ;
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, newsFragment).addToBackStack("tag").commit();


        } else if (id == R.id.nav_Business) {
            NewsFragment newsFragment = new NewsFragment().newInstance("eg", "business");
            ;
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, newsFragment).addToBackStack("tag").commit();

        } else if (id == R.id.nav_Entertainment) {
            NewsFragment newsFragment = new NewsFragment().newInstance("eg", "entertainment");
            ;
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, newsFragment).addToBackStack("tag").commit();

        } else if (id == R.id.nav_Health) {
            NewsFragment newsFragment = new NewsFragment().newInstance("eg", "health");
            ;
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, newsFragment).addToBackStack("tag").commit();
        } else if (id == R.id.nav_Science) {
            NewsFragment newsFragment = new NewsFragment().newInstance("eg", "science");
            ;
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, newsFragment).addToBackStack("tag").commit();
        } else if (id == R.id.nav_Technology) {
            NewsFragment newsFragment = new NewsFragment().newInstance("eg", "technology");
            ;
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, newsFragment).addToBackStack("tag").commit();

        } else if (id == R.id.nav_Sport) {
            NewsFragment newsFragment = new NewsFragment().newInstance("eg", "sports");
            ;
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, newsFragment).addToBackStack("tag").commit();
        } else if (id == R.id.nav_favorite) {
            FavouriteFragment favouriteFragment = new FavouriteFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, favouriteFragment).addToBackStack("tag").commit();

        } else if (id == R.id.nav_login) {
            mAuth.signOut();
            editor.remove(getString(R.string.id)).commit();
            editor.remove(getString(R.string.email)).commit();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
