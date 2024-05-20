package com.mojang.minecraftpe;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
public class MainActivity extends Activity {
    private Vietnq308Activity vietnq308Activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(1024, 1024);
        getWindow().getDecorView().setSystemUiVisibility(6);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if (vietnq308Activity == null) {
            vietnq308Activity = new Vietnq308Activity(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        vietnq308Activity.onResumeApp();
    }

    @Override
    protected void onPause() {
        super.onPause();
        vietnq308Activity.onPauseApp();
    }
}
