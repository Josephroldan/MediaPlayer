package com.example.student.mediaplayer;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public Button pauseButtonVar, playButtonVar, rewindButtonVar, fastforwardButtonVar, stopButtonVar;
    private MediaPlayer song1;
    public TextView currentTimeViewVar, endTimeViewVar, songArtistVar, songTitleVar;
    public MediaPlayer songPlayer;
    public double endTimeMS, currentTimeMS;
    public Handler myHandler = new Handler();
    private SeekBar mySongBarVar;
    MediaMetadataRetriever songInfo = new MediaMetadataRetriever();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playButtonVar = (Button) findViewById(R.id.play);
        pauseButtonVar = (Button) findViewById(R.id.pause);
        rewindButtonVar = (Button) findViewById(R.id.backwards);
        fastforwardButtonVar = (Button) findViewById(R.id.forwards);
        stopButtonVar = (Button) findViewById(R.id.stop);
        song1 = MediaPlayer.create(this, R.raw.bargainsinatuxedo);


    public void playSong(View view) {
        Toast.makeText(getApplicationContext(), "Playing Song", Toast.LENGTH_SHORT).show();
        pauseButtonVar.setEnabled(true);
        playButtonVar.setEnabled(false);
        song1.start();
        stopButtonVar.setEnabled(true);
    }

    public void pauseSong(View view) {
        Toast.makeText(getApplicationContext(), "Pausing Song", Toast.LENGTH_SHORT).show();
        pauseButtonVar.setEnabled(false);
        playButtonVar.setEnabled(true);
        stopButtonVar.setEnabled(true);
        song1.pause();
    }

    public void stopSong(View view) {
        Toast.makeText(getApplicationContext(), "Stoping Song", Toast.LENGTH_SHORT).show();
        pauseButtonVar.setEnabled(false);
        playButtonVar.setEnabled(true);
        stopButtonVar.setEnabled(false);
        songPlayer.seekTo(0);
        songPlayer.pause();
    }

    public void forward(View view) {
        Toast.makeText(getApplicationContext(), "Forward 5s", Toast.LENGTH_SHORT).show();
        if (currentTimeMS < endTimeMS - 5000) {
            songPlayer.seekTo((int) currentTimeMS + 5000);
        }
    }

    public void backward(View view) {
        Toast.makeText(getApplicationContext(), "Rewind 5s", Toast.LENGTH_SHORT).show();
        if (currentTimeMS > 5000) {
            songPlayer.seekTo((int) currentTimeMS - 5000);
        }
    }


}