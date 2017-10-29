package davidzapps.cryptoexchange;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.about_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //Menu options for repository, credits and exit
        if (id == R.id.visit_repo) {
            Intent intent = new Intent(AboutActivity.this, RepositoryWebView.class);
            startActivity(intent);
        }else if (id == R.id.action_credits) {
            Intent intent = new Intent(AboutActivity.this, CreditsActivity.class);
            startActivity(intent);
        }else if (id == R.id.action_exit) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}

