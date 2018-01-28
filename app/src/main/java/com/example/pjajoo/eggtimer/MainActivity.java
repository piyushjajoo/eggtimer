package com.example.pjajoo.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SeekBar timerSeekbar;
    private TextView timeTextview;
    private CountDownTimer eggTimer;
    private Button button;
    private MediaPlayer mediaPlayer;

    private void setTime(int secondsLeft) {
        final int minutes = (int) secondsLeft / 60;
        final int seconds = secondsLeft - minutes * 60;

        this.timeTextview.setText(minutes + ":" + String.format("%02d", seconds));
    }

    public void controlTimer(final View view) {

        this.button = (Button) view;
        Log.i("button text", this.button.getText().toString());
        if (button.getText().toString().equals("Go")) {
            this.button.setText("Stop");
            this.eggTimer = new CountDownTimer(this.timerSeekbar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    setTime((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    timeTextview.setText("0:00");
                    button.setText("Go");
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.timesup);
                    mediaPlayer.start();
                }
            }.start();
        } else {
            this.button.setText("Go");
            this.timeTextview.setText("0:30");
            this.timerSeekbar.setProgress(30);
            this.eggTimer.cancel();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.timerSeekbar = (SeekBar) findViewById(R.id.timerSeekbar);
        this.timerSeekbar.setMax(600);
        this.timerSeekbar.setProgress(30);

        this.timeTextview = (TextView) findViewById(R.id.textView);

        this.timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (eggTimer != null) {
                    eggTimer.cancel();
                }
                button.setText("Go");
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }
                setTime(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                button.setText("Go");
                setTime(seekBar.getProgress());
            }
        });
    }
}
