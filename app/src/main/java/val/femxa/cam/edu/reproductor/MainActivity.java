package val.femxa.cam.edu.reproductor;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity{

    private TextView tvMsg;
    private Button btnPlay;
    private Button btnPause;
    private Button btnStop;
    private SeekBar skSong;
    private TextView tvTime;
    private MediaPlayer mPlayer = null;
    private Handler skHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //-- begin
        mPlayer = MediaPlayer.create(MainActivity.this, R.raw.kperry);

        tvMsg = (TextView) findViewById(R.id.tvMsg);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnStop = (Button) findViewById(R.id.btnStop);
        skSong = (SeekBar) findViewById(R.id.skSong);
        tvTime = (TextView) findViewById(R.id.tvTime);

        //----------- button PLAY
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!mPlayer.isPlaying())
                {
                    mPlayer.start();
                    tvMsg.setText("PLAY");
                }
            }
        });
        //----------- button PAUSE
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mPlayer.isPlaying())
                {
                    mPlayer.pause();
                    tvMsg.setText("PAUSE");
                }
            }
        });

        //----------- button STOP
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPlayer != null) {
                    mPlayer.stop();
                    mPlayer.release();
                    mPlayer = MediaPlayer.create(MainActivity.this, R.raw.kperry);
                    tvMsg.setText("FINALIZADO!!!");
                }
            }
        });

        //tiempo de duracion e iniciO Del bar
        tvTime.setText( getHRM(mPlayer.getDuration()) );
        skSong.setMax(mPlayer.getDuration());
        skSong.setProgress(mPlayer.getCurrentPosition());

        skHandler.postDelayed(updateskSong, 1000);


    }

    // actualizar el progreso de la reproducción
    Runnable updateskSong = new Runnable() {
        @Override
        public void run() {
            skSong.setProgress( mPlayer.getCurrentPosition() );
            tvTime.setText( getHRM(mPlayer.getDuration()) + " - " + getHRM(mPlayer.getCurrentPosition()) );
            skHandler.postDelayed(updateskSong, 1000);
        }
    };

    private String getHRM(int milliseconds )
    {
        int seconds = (int) (milliseconds / 1000) % 60 ;
        int minutes = (int) ((milliseconds / (1000*60)) % 60);
        int hours   = (int) ((milliseconds / (1000*60*60)) % 24);
        return ((hours<10)?"0"+hours:hours) + ":" +
                ((minutes<10)?"0"+minutes:minutes) + ":" +
                ((seconds<10)?"0"+seconds:seconds);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = MediaPlayer.create(MainActivity.this, R.raw.kperry);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}