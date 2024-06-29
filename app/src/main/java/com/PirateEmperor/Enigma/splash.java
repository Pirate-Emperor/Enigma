package com.PirateEmperor.Enigma;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    private Window mWindow;

    @Override // android.support.v7.app.AppCompatActivity,
              // android.support.v4.app.SupportActivity,
              // android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.splash);
        this.mWindow = getWindow();
        this.mWindow.getDecorView().setSystemUiVisibility(3846);
        new Handler().postDelayed(new Runnable() {
            /* class cryptocalsi.it.cspit.charusat.crypto.Splash_Screen.AnonymousClass1 */

            public void run() {
                splash.this.startActivity(new Intent(splash.this, home.class));
                splash.this.finish();
            }
        }, (long) SPLASH_TIME_OUT);
    }
}
