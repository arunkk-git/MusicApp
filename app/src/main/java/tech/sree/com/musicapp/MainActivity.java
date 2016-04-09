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
      //  return ;

        String[] proj = {"*"};
        Uri tempPlaylistURI = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;

        // In the next line 'this' points to current Activity.
        // If you want to use the same code in other java file then activity,
        // then use an instance of any activity in place of 'this'.
        Cursor playListCursor= MainActivity.this.managedQuery(tempPlaylistURI, proj, null, null, null);
        //Cursor playListCursor= getContentResolver().query(tempPlaylistURI, proj, null, null, null);
        //playListCursor = managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,proj, null, null,MediaStore.Audio.Media.TITLE);
        if(playListCursor == null){
           // System.out.println("Not having any Playlist on phone --------------");
            Toast.makeText(getApplicationContext(),"Not having any Playlist on phone ----------",Toast.LENGTH_LONG).show();
            return;//don't have list on phone
        }

        System.gc();

        String playListName = null;

      //  System.out.println(">>>>>>>  CREATING AND DISPLAYING LIST OF ALL CREATED PLAYLIST  <<<<<<");
        Toast.makeText(getApplicationContext(),">>>>>>>  CREATING AND DISPLAYING LIST OF ALL CREATED PLAYLIST  <<<<<<",Toast.LENGTH_LONG).show();

        for(int i = 0; i <playListCursor.getCount() ; i++)
        {
            playListCursor.moveToPosition(i);
            playListName = playListCursor.getString(playListCursor.getColumnIndex("name"));
            //System.out.println("> " + i + "  : " + playListName );
            Toast.makeText(getApplicationContext(),
                    "> " + i + "  : " + playListName,Toast.LENGTH_LONG).show();
        }

        if(playListCursor != null)
            playListCursor.close();

    }


    public void playMusic(View V){
        Toast.makeText(getApplicationContext(),"Play Music on 53",Toast.LENGTH_LONG).show();
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
