package com.annatarhe.athena;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.annatarhe.athena.Adapter.IndexListAdapter;
import com.annatarhe.athena.Model.Config;
import com.annatarhe.athena.ViewController.AuthActivity;
import com.annatarhe.athena.fragment.FetchGirls;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<InitCategoriesQuery.Category> categories;
    private List<FetchGirlsQuery.Girl> cells = new ArrayList<FetchGirlsQuery.Girl>();

    private int currentCategory = -1;

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.initData();
        this.initView();

        this.loadCategories();
    }

    // TODO: loadCategories from graphql server
    private void loadCategories() {
        Config.getApolloClient().query(InitCategoriesQuery.builder().build()).enqueue(new ApolloCall.Callback<InitCategoriesQuery.Data>() {
            @Override
            public void onResponse(@Nonnull Response<InitCategoriesQuery.Data> response) {
                categories = response.data().categories();
                Log.i("apollo", categories.toString());

                invalidateOptionsMenu();
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                e.getStackTrace();
                Log.i("apollo", e.toString());
            }
        });
    }

    private void initData() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new IndexListAdapter(MainActivity.this, cells);
    }

    private void initView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.indexListView);
        // 设置布局管理器
        recyclerView.setLayoutManager(this.layoutManager);
        // 设置adapter
        recyclerView.setAdapter(adapter);
        Log.i("i", "inited recyclerview ");
    }

    private void getData(final Boolean loadMore) {

        Config.getApolloClient().query(
                FetchGirlsQuery.builder()
                        .from(currentCategory)
                        .take(20)
                        .offset((loadMore && cells != null) ? cells.size() :0)
                        .build()
        ).enqueue(new ApolloCall.Callback<FetchGirlsQuery.Data>() {
            @Override
            public void onResponse(@Nonnull Response<FetchGirlsQuery.Data> response) {
                if (!loadMore) {
                    if (cells != null) {
                        cells.clear();
                    }
                }

                cells.addAll(response.data().girls());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        adapter.notifyDataSetChanged();
                    }
                });

            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Snackbar.make(findViewById(R.id.indexListView), "fail load images", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("i", "on start");
        this.checkLogin();
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Log.i("i", "on restart");
//        this.checkLogin();
//    }

    private void checkLogin() {

        // TODO: delete me for production
        if (!Config.token.equals("")) {
            return;
        }

        Intent intent = new Intent(MainActivity.this, AuthActivity.class);

        // TODO: add return code
        startActivity(intent);
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

        if (categories == null) {
            return true;
        }

        for (InitCategoriesQuery.Category category : categories) {
            int id = Integer.parseInt(category.id());
            menu.add(1, id, id, category.name());
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // set current category
        this.currentCategory = id;

        this.getData(false);

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
