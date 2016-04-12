package tech.sree.com.musicapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class MusicPlay extends AppCompatActivity {
    MediaPlayer mPlayer;
    ImageButton play,pause,stop;
    int numMessages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);
//        play = (ImageButton)findViewById(R.id.play);
//        pause = (ImageButton)findViewById(R.id.pause);
//        stop = (ImageButton)findViewById(R.id.stop);
        mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.music);

        playSound();
        Notify("Music Player","Plaing Song Number ~~~~~~~");
    }
    public void exitMusicApp(View V){
        mPlayer.stop();
        finish();
    }


    public void  playSound(){
        mPlayer.start();

    }


    private void Notify(String notificationTitle, String notificationMessage){
// prepare intent which is triggered if the
// notification is selected

        Intent intent = new Intent(this, MusicPlay.class);
// use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

// build notification
// the addAction re-use the same intent to keep the example short
        Notification n  = new Notification.Builder(this)
                .setContentTitle(notificationTitle)
                .setContentText(notificationMessage)
                .setSmallIcon(R.drawable.music)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .addAction(R.drawable.music, "play", pIntent)
                .addAction(R.drawable.music, "pause", pIntent)
                .addAction(R.drawable.music, "Exit", pIntent).build();


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
    }



}