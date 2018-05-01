package sameeh.com.nbapp.Screens.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import sameeh.com.nbapp.LocalData.LocalStorageManager;
import sameeh.com.nbapp.Models.User;
import sameeh.com.nbapp.R;
import sameeh.com.nbapp.Screens.LoginRegister.LoginRegisterActivity;
import sameeh.com.nbapp.Screens.Main.Players.NewPlayerFragment;
import sameeh.com.nbapp.Screens.Main.Players.PlayersListFragment;
import sameeh.com.nbapp.Screens.Main.Profile.ProfileFragment;
import sameeh.com.nbapp.Screens.SearchTeamActivity;
import sameeh.com.nbapp.Utils.CircleTransform;

public class LoggedInActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ProfileFragment.ProfileFragmentListener,
        NewPlayerFragment.PlayerFragmentListener, PlayersListFragment.PlayersListFragmentListener {

    private LocalStorageManager localStorageManager;
    private ImageView ppImageView;
    private TextView nameHeaderTextView, emailHeaderTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        localStorageManager = LocalStorageManager.getInstance(this);

        NavigationView nv = findViewById(R.id.nav_view);
        View header = nv.getHeaderView(0);
        ppImageView = header.findViewById(R.id.profile_picture);
        nameHeaderTextView = header.findViewById(R.id.name);
        emailHeaderTextView = header.findViewById(R.id.email);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView nv2 = findViewById(R.id.nav_view);
        nv2.setNavigationItemSelectedListener(this);

        addPlayersList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateHeaderViews();
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.nav_players:
                addPlayersList();
                break;

            case R.id.nav_logout:
                logout();
                break;

            case R.id.nav_profile:
                showProfileFragment();
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addPlayersList() {
        setTitle(getString(R.string.title_player));
        PlayersListFragment fragment = PlayersListFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.teams:
                Intent intent = new Intent(this, SearchTeamActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void logout() {
        LocalStorageManager.getInstance(this).deleteUser();
        Intent intent = new Intent(this, LoginRegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private void showProfileFragment() {
        setTitle(getString(R.string.title_profile));
        ProfileFragment fragment = ProfileFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

    }

    private void populateHeaderViews() {
        User user = localStorageManager.getUser();
        if (user != null) {
            if (!TextUtils.isEmpty(user.getProfilePicture())) {
                Picasso
                        .get()
                        .load(user.getProfilePicture())
                        .transform(new CircleTransform())
                        .into(ppImageView);
            }
            nameHeaderTextView.setText(user.getName());
            emailHeaderTextView.setText(user.getEmail());
        }
    }

    private void gotoAuthenticationScreen() {
        Intent intent = new Intent(this, LoginRegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onProfileFetchFailure() {
        gotoAuthenticationScreen();
    }

    @Override
    public void onRequestCreateNewPlayer() {
        setTitle(getString(R.string.title_create_player));
        NewPlayerFragment fragment = NewPlayerFragment.newInstance();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(NewPlayerFragment.TAG).commit();
    }

    @Override
    public void onErrorFetchingPlayers() {
        gotoAuthenticationScreen();
    }

    @Override
    public void onNewPlayerCreatedSuccessfully() {
        setTitle(getString(R.string.title_player));
        getFragmentManager().popBackStack();
    }

    @Override
    public void onNewPlayerCreationFailure() {
        gotoAuthenticationScreen();
    }
}
