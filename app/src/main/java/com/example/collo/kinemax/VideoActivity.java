package com.example.collo.kinemax;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnInfoListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    private VideoView mainVideoView;
    private ImageView playBtn;
   private TextView currentTimer;
    private TextView durationTimer;
    private ProgressBar currentProgress;
    private ProgressBar bufferProgress;

    private  boolean isPlaying;


    private Uri videoUri;

    private int current=0;
    private int duration=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        isPlaying =false;

        //initialization
        mainVideoView=(VideoView)findViewById(R.id.mainVideoView);
        playBtn=(ImageView) findViewById(R.id.playBtn);
        currentProgress=(ProgressBar)findViewById(R.id.videoProgress);
        currentTimer=(TextView)findViewById(R.id.currentTimer);
        durationTimer=(TextView)findViewById(R.id.durationTimer);
        bufferProgress=(ProgressBar)findViewById(R.id.bufferProgress);

        currentProgress.setMax(100);

        videoUri=Uri.parse("https://firebasestorage.googleapis.com/v0/b/kinemax-a68c1.appspot.com/o/THE%20EQUALIZER%202%20-%20Official%20Trailer%20(HD)_x264.mp4?alt=media&token=f7b56686-93d3-49c4-987f-a6a707c89777");

mainVideoView.setVideoURI(videoUri);
mainVideoView.requestFocus();


            mainVideoView.setOnInfoListener(new OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
                    if(i == mediaPlayer.MEDIA_INFO_BUFFERING_START){
                        bufferProgress.setVisibility(View.VISIBLE);

                    }else if(i == mediaPlayer.MEDIA_INFO_BUFFERING_END){

                        bufferProgress.setVisibility(View.INVISIBLE);
                    }

                    return false;
                }
            });

            mainVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    duration=mp.getDuration()/1000;
                    String durationString=String.format("%02d:%02d",duration /60, duration % 60);
                    durationTimer.setText(durationString);

                }
            });


        mainVideoView.start();
isPlaying=true;
playBtn.setImageResource(R.drawable.ic_action_pause);

new videoProgress().execute();

playBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        if(isPlaying){
            mainVideoView.pause();
            isPlaying=false;
            playBtn.setImageResource(R.drawable.ic_action_play);
        }else {
            mainVideoView.start();
            isPlaying=true;
            playBtn.setImageResource(R.drawable.ic_action_pause);
        }

    }
});


    }

    @Override
    protected void onStop() {
        super.onStop();

        isPlaying=false;
    }

    public class videoProgress extends AsyncTask<Void ,Integer,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            do{
               if(isPlaying) {

                   current = mainVideoView.getCurrentPosition() / 1000;
                   publishProgress(current);

               }

            }while(currentProgress.getProgress()<= 100);

            return null;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            try {
                int currentPercent=values[0]*100/duration;
                currentProgress.setProgress(currentPercent);

                String currentString=String.format("%02d:%02d", values[0]/60, values[0]%60);
                currentTimer.setText(currentString);

            }catch (Exception e){

            }

        }
    }

}
