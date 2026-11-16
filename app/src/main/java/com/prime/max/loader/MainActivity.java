package com.prime.max.loader;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.content.Intent;
import android.media.MediaPlayer;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private final Handler handler = new Handler(Looper.getMainLooper());
    private ProgressBar progressBar;
    private TextView percentText, statusText;
    private Button soundButton;
    private MediaPlayer player;
    private int progress = 1;
    private int soundIndex = -1;
    private boolean soundEnabled = true;
    private boolean loading = false;

    private final Runnable loadingTick = new Runnable() {
        @Override public void run() {
            if (!loading) return;

            progressBar.setProgress(progress);
            percentText.setText(progress + "%");
            statusText.setText(progress < 100 ? "Preparing launcher..." : "READY");

            if (progress % 20 == 0) playNextEngineSound();

            if (progress >= 100) {
                loading = false;
                stopSound();
                statusText.setText("READY TO LAUNCH");
                return;
            }

            progress++;
            handler.postDelayed(this, 120);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        percentText = findViewById(R.id.percentText);
        statusText = findViewById(R.id.statusText);
        soundButton = findViewById(R.id.soundButton);

        soundButton.setOnClickListener(v -> {
            soundEnabled = !soundEnabled;
            soundButton.setText(soundEnabled ? "ENGINE SOUND: ON" : "ENGINE SOUND: OFF");
            if (!soundEnabled) stopSound();
        });

        findViewById(R.id.facebookButton).setOnClickListener(v ->
            Toast.makeText(this,
                "Add official Meta OAuth configuration before enabling Facebook login.",
                Toast.LENGTH_LONG).show()
        );

        findViewById(R.id.launchButton).setOnClickListener(v -> launchBgmi());

        findViewById(R.id.restartButton).setOnClickListener(v -> startLoading());

        startLoading();
    }

    private void startLoading() {
        handler.removeCallbacks(loadingTick);
        stopSound();
        progress = 1;
        loading = true;
        progressBar.setProgress(1);
        percentText.setText("1%");
        statusText.setText("Starting...");
        handler.post(loadingTick);
    }

    private void playNextEngineSound() {
        if (!soundEnabled) return;

        stopSound();
        int[] sounds = { R.raw.car_engine, R.raw.superbike_engine };
        soundIndex = (soundIndex + 1) % sounds.length;

        try {
            player = MediaPlayer.create(this, sounds[soundIndex]);
            if (player != null) {
                player.setLooping(false);
                player.setVolume(0.75f, 0.75f);
                player.setOnCompletionListener(mp -> stopSound());
                player.start();
            }
        } catch (RuntimeException ex) {
            stopSound();
            Toast.makeText(this, "Check engine audio files in res/raw.", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopSound() {
        if (player != null) {
            try {
                if (player.isPlaying()) player.stop();
            } catch (IllegalStateException ignored) { }
            player.release();
            player = null;
        }
    }

    private void launchBgmi() {
        // BGMI's package name may vary by build/region. Verify on your device.
        final String packageName = "com.pubg.imobile";
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(packageName);
        if (launchIntent == null) {
            Toast.makeText(this, "BGMI app not found. Open BGMI manually.", Toast.LENGTH_LONG).show();
            return;
        }
        startActivity(launchIntent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopSound();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        stopSound();
        super.onDestroy();
    }
}
