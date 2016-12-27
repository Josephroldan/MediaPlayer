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
    public TextView currentTimeViewVar, endTimeViewVar, songArtistVar,songTitleVar;
    public MediaPlayer songPlayer;
    public double endTimeMS, currentTimeMS;
    public Handler myHandler = new Handler();
    private SeekBar mySongBarVar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaMetadataRetriever songInfo = new MediaMetadataRetriever();
        playButtonVar = (Button) findViewById(R.id.play);
        pauseButtonVar = (Button) findViewById(R.id.pause);
        rewindButtonVar = (Button) findViewById(R.id.backward);
        fastforwardButtonVar = (Button) findViewById(R.id.forward);
        stopButtonVar = (Button) findViewById(R.id.stop);

        currentTimeViewVar = (TextView) findViewById(R.id.currentTimeView);
        endTimeViewVar = (TextView) findViewById(R.id.endTimeView);

        songTitleVar = (TextView) findViewById(R.id.title);
        songArtistVar = (TextView) findViewById(R.id.artist);

        songPlayer = MediaPlayer.create(this, R.raw.bargainsinatuxedo);

        endTimeMS = songPlayer.getDuration();
        int endTimeMin = (int) endTimeMS/1000/60;
        int endTimeS = (int) (endTimeMS/1000) % 60;
        endTimeViewVar.setText(endTimeMin+" Min " + endTimeS+ " Sec");

        pauseButtonVar.setEnabled(false);
        stopButtonVar.setEnabled(false);
        myHandler.postDelayed(UpdateSongTime, 100);
        mySongBarVar = (SeekBar) findViewById(R.id.mySongBar);
        mySongBarVar.setMax((int) endTimeMS);

        Uri mediaPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bargainsinatuxedo);
        songInfo.setDataSource(this, mediaPath);
        String songTitle = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        String songArtist = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        songArtistVar.setText(songArtist);
        songTitleVar.setText(songTitle);


        mySongBarVar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int seekTime;
            @Override

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekTime = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                songPlayer.seekTo( seekTime);
                currentTimeMS = seekTime;
            }
        });
    }
    public void playSong(View view){
        Toast.makeText(getApplicationContext(), "Playing Song", Toast.LENGTH_SHORT).show();
        pauseButtonVar.setEnabled(true);
        playButtonVar.setEnabled(false);
        songPlayer.start();
        stopButtonVar.setEnabled(true);
    }
    public void pauseSong(View view){
        Toast.makeText(getApplicationContext(), "Pausing Song", Toast.LENGTH_SHORT).show();
        pauseButtonVar.setEnabled(false);
        playButtonVar.setEnabled(true);
        stopButtonVar.setEnabled(true);
        songPlayer.pause();
    }
    public void stopSong(View view){
        Toast.makeText(getApplicationContext(), "Stoping Song", Toast.LENGTH_SHORT).show();
        pauseButtonVar.setEnabled(false);
        playButtonVar.setEnabled(true);
        stopButtonVar.setEnabled(false);
        songPlayer.seekTo(0);
        songPlayer.pause();
    }

    public void forward(View view){
        Toast.makeText(getApplicationContext(), "Forward 5s", Toast.LENGTH_SHORT).show();
        if(currentTimeMS < endTimeMS - 5000) {
            songPlayer.seekTo((int) currentTimeMS + 5000);
        }
    }
    public void backward(View view){
        Toast.makeText(getApplicationContext(), "Rewind 5s", Toast.LENGTH_SHORT).show();
        if(currentTimeMS>5000) {
            songPlayer.seekTo((int) currentTimeMS - 5000);
        }
    }

    private Runnable UpdateSongTime = new Runnable(){
        public void run(){
            currentTimeMS = songPlayer.getCurrentPosition();
            int currentTimeMin = (int) currentTimeMS/1000/60;
            int currentTimeS = (int) (currentTimeMS/1000) % 60;

            currentTimeViewVar.setText(currentTimeMin+" Min " + currentTimeS+ " Sec");

            myHandler.postDelayed(this, 100);

            mySongBarVar.setProgress((int) currentTimeMS);

        }
    };
}
