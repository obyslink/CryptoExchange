package tech.dappworld.cryptoexchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class AboutActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        getMenuInflater().inflate(R.menu.general_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Menu options for repository, about and exit
        if (id == R.id.action_exit) {
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //Navigation bar menu
        int id = item.getItemId();

        if (id == R.id.quick_convertMenu) {
            Intent intent = new Intent(AboutActivity.this, FastCalculatorNavActivity.class);
            startActivity(intent);
        } else if (id == R.id.quick_aboutMenu) {
            Intent intent = new Intent(AboutActivity.this, AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.homeExchangeRate) {
            Intent intent = new Intent(AboutActivity.this, MainActivity.class);
            startActivity(intent);
        }else if (id == R.id.offlineCalculator) {
            Intent intent = new Intent(AboutActivity.this, OfflineCalculatorActivity.class);
            startActivity(intent);
        } else if (id == R.id.visit_repo) {
            Intent intent = new Intent(AboutActivity.this, RepositoryWebViewActivity.class);
            startActivity(intent);
        } else if (id == R.id.share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent
                    .putExtra(
                            Intent.EXTRA_TEXT,
                            "I am using "
                                    + getString(R.string.app_name)
                                    + " App ! Why don't you try it out...\nInstall "
                                    + getString(R.string.app_name)
                                    + " now !\nhttps://play.google.com/store/apps/details?id="
                                    + getPackageName());
            sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                    getString(R.string.app_name) + " App !");
            sendIntent.setType("text/plain");

            startActivity(Intent.createChooser(sendIntent,
                    getString(R.string.text_share_app)));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

