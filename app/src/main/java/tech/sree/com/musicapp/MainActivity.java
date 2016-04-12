package tech.sree.com.musicapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {



    private TextView setTime,toastText;
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
        toastText = (TextView)findViewById(R.id.toastText);

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
        String info =" Future set for [ "+hour+" : "+minute+"  ]";
     //   toastText.setText(info);
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_root));
        TextView text = (TextView) layout.findViewById(R.id.toastText);
        text.setText(info);
        Toast toast = new Toast(getApplicationContext());
       // toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
                //.makeText(getApplicationContext(),"Future set for [ "+hour+" : "+minute+"  ]",Toast.LENGTH_LONG).show();
        futureDate.set(Calendar.HOUR_OF_DAY, hour);
        futureDate.set(Calendar.MINUTE, minute);

        Intent intent = new Intent(this, MusicPlay.class);
        pi = PendingIntent.getActivity(this, 2, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP,futureDate.getTimeInMillis(), pi);
    }
  /*
    public void customToast(String info){
        LinearLayout layout=new LinearLayout(this);
        layout.setBackgroundResource(R.color.LightOrange);

        TextView  tv=new TextView(this);
        // set the TextView properties like color, size etc
        tv.setTextColor(Color.RED);
        tv.setTextSize(15);

        tv.setGravity(Gravity.CENTER_VERTICAL);

        // set the text you want to show in  Toast
        tv.setText("My Custom Toast at Bottom of Screen");

        ImageView   img=new ImageView(this);

        // give the drawble resource for the ImageView
        img.setImageResource("mipmap/ic_launcher");

        // add both the Views TextView and ImageView in layout
        layout.addView(img);
        layout.addView(tv);

        Toast toast=new Toast(this); //context is object of Context write "this" if you are an Activity
        // Set The layout as Toast View
        toast.setView(layout);

        // Position you toast here toast position is 50 dp from bottom you can give any integral value
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.show();
    }
    */
}
