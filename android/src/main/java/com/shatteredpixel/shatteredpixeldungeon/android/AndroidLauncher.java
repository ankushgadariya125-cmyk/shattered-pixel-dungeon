package com.shatteredpixel.shatteredpixeldungeon.android;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class AndroidLauncher extends AndroidApplication {
    public static AndroidLauncher instance;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
instance = this;
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useImmersiveMode = true;

        // Ads Initialize
        MobileAds.initialize(this, initializationStatus -> {});

        // Main Layout
        FrameLayout layout = new FrameLayout(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);

        // Game View
        View gameView = initializeForView(new ShatteredPixelDungeon(new AndroidPlatformSupport()), config);

        // Banner Ad
        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-8859848812165923/1754995361");

        FrameLayout.LayoutParams adParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        adParams.gravity = android.view.Gravity.BOTTOM | android.view.Gravity.CENTER_HORIZONTAL;

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        layout.addView(gameView);
        layout.addView(adView, adParams);

        setContentView(layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null) adView.resume();
    }

    @Override
    protected void onPause() {
        if (adView != null) adView.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (adView != null) adView.destroy();
        super.onDestroy();
    }
}
