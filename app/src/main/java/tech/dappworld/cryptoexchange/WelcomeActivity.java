package tech.dappworld.cryptoexchange;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by OBINNA on 10/19/2017.
 */

public class WelcomeActivity extends Activity {
    //SPLASH SCREEN ACTIVITY FOR 3000
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_main);

        new Handler().postDelayed(new Runnable(){
            @Override
            public  void run(){
                Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        },SPLASH_TIME_OUT);

    }
}
