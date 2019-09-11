package com.example.littlecafeshop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView photo;
    TextView name;
    NavigationView navigationView;
    NavigationView profileButton;
    TextView email;
    Button signOutButton;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
        photo = headerLayout.findViewById(R.id.imageView);
        name = headerLayout.findViewById(R.id.name);
        email = headerLayout.findViewById(R.id.email);

        signOutButton = navigationView.findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this, SignIn.class));
                finish();

            }
        });

        Intent intent = getIntent();
        user = (FirebaseUser) intent.getExtras().get("user");
        Uri photourl = (Uri) intent.getExtras().get("photo");

        name.setText(user.getDisplayName());
        email.setText(user.getEmail());

        Picasso.with(HomeActivity.this).load(photourl).into(photo);

        getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment,new HomeFragment()).commit();

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }

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


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment selectedFragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            getSupportActionBar().setTitle("Home");
            selectedFragment = new HomeFragment();

        } else if (id == R.id.nav_menu) {
            getSupportActionBar().setTitle("Menu");
            selectedFragment = new MenuFragment();

        } else if (id == R.id.nav_shop) {
            getSupportActionBar().setTitle("Shop");
            selectedFragment = new ShopFragment();
            Bundle args = new Bundle();
            args.putString("type", "all");
            selectedFragment.setArguments(args);

        } else if (id == R.id.nav_profile) {
            getSupportActionBar().setTitle("Profile");
            selectedFragment = new ProfileFragment();
            Bundle args = new Bundle();
            String email = user.getEmail().toString();
            args.putString("email", email);
            Uri photourl = (Uri) getIntent().getExtras().get("photo");
            args.putString("img", photourl.toString());
            selectedFragment.setArguments(args);

        } else if (id == R.id.nav_setting) {
            getSupportActionBar().setTitle("Settings");
            selectedFragment = new SettingFragment();
            Bundle args = new Bundle();
            String email = user.getEmail().toString();
            args.putString("email", email);
            selectedFragment.setArguments(args);
        }
        assert selectedFragment != null;
        getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment,selectedFragment).commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
