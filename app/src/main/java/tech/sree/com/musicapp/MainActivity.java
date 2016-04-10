package tech.sree.com.musicapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {



    private TextView setTime;
    private TimePicker timePicker;
    private PendingIntent pi;
    private AlarmManager am;
Button playList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     //   setTime = (TextView) findViewById(R.id.setTime);
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        playList = (Button)findViewById(R.id.playList);

    }

    public void playListMusic(View V){

        String[] proj = {"*"};
        Uri tempPlaylistURI = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;

        Cursor playListCursor= getContentResolver().query(tempPlaylistURI, proj, null, null, null);

        if(playListCursor == null){
           Toast.makeText(getApplicationContext(),"Not having any Playlist on phone ----------",Toast.LENGTH_LONG).show();
            return;
        }
        System.gc();
        String playListName = null;
        Toast.makeText(getApplicationContext(),">>>>>>>  CREATING AND DISPLAYING LIST OF ALL CREATED PLAYLIST  <<<<<<",Toast.LENGTH_LONG).show();

        for(int i = 0; i <playListCursor.getCount() ; i++)
        {
            playListCursor.moveToPosition(i);
            playListName = playListCursor.getString(playListCursor.getColumnIndex("name"));
            Toast.makeText(getApplicationContext(),
                    "> " + i+1 + "  : " + playListName,Toast.LENGTH_LONG).show();
        }

        if(playListCursor != null)
            playListCursor.close();

    }


    public void playMusic(View V){
        Toast.makeText(getApplicationContext(),"Play Music ",Toast.LENGTH_LONG).show();
        int hour = 0;
        int minute = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        }

        else {
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();

        }
        Calendar futureDate = Calendar.getInstance();
        // futureDate.setTimeZone(TimeZone.getTimeZone("GMT"));
        Toast.makeText(getApplicationContext(),"Future set for [ "+hour+" : "+minute+"  ]",Toast.LENGTH_LONG).show();
        futureDate.set(Calendar.HOUR_OF_DAY, hour);
        futureDate.set(Calendar.MINUTE, minute);

        Intent intent = new Intent(this, MusicPlay.class);
        pi = PendingIntent.getActivity(this, 2, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP,futureDate.getTimeInMillis(), pi);
    }
}
