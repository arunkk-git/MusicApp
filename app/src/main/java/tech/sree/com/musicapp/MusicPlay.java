package tech.sree.com.musicapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import tech.sree.com.musicapp.R;

public class MusicPlay extends AppCompatActivity {
    MediaPlayer mPlayer;
    ImageButton play,pause,stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);
//        play = (ImageButton)findViewById(R.id.play);
//        pause = (ImageButton)findViewById(R.id.pause);
//        stop = (ImageButton)findViewById(R.id.stop);
        mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.music);

        playSound();
    }
    public void exitMusicApp(View V){
        mPlayer.stop();
        finish();
    }


    public void  playSound(){
        mPlayer.start();

    }

}
