package ssi.hackathon.diressom;

import android.media.MediaRecorder;
import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private TransitionDrawable backgroundDrawable;
    MediaRecorder mRecorder;
    private final int INTERVAL_TO_UPDATE_MEASURE_IN_MILISECONDS = 100;
    private final int DECIBEL_LIMIT = 2500;
    private double previousMeasure = 0.0;
    Thread runner;
    private static double mEMA = 0.0;
    private static final double EMA_FILTER = 0.6;

    final Runnable updater = new Runnable(){

        public void run(){
            updateTv();
        };
    };
    final Handler mHandler = new Handler();

    private void startAlert() {
        backgroundDrawable.startTransition(500);
    }

    private void finishAlert() {
        backgroundDrawable.reverseTransition(500);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View layout = findViewById(R.id.background);
        this.backgroundDrawable = (TransitionDrawable) layout.getBackground();


        if (runner == null) {
            runner = new Thread() {
                public void run()
                {
                    while (runner != null)
                    {
                        try
                        {
                            Thread.sleep(INTERVAL_TO_UPDATE_MEASURE_IN_MILISECONDS);
                        } catch (InterruptedException e) { };
                        mHandler.post(updater);
                    }
                }
            };
            runner.start();
        }
    }

    public void onResume() {
        super.onResume();
        startRecorder();
    }

    public void onPause() {
        super.onPause();
        stopRecorder();
    }

    public void startRecorder() {
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setOutputFile("/dev/null");
            try {
                mRecorder.prepare();
            } catch (java.io.IOException ioe) {
                Log.e("[Monkey]", "IOException: " +
                        android.util.Log.getStackTraceString(ioe));

            }catch (java.lang.SecurityException e) {
                Log.e("[Monkey]", "SecurityException: " +
                        android.util.Log.getStackTraceString(e));
            }
            try {
                mRecorder.start();
            } catch (java.lang.SecurityException e) {
                Log.e("[Monkey]", "SecurityException: " +
                        android.util.Log.getStackTraceString(e));
            }
        }
    }

    public void stopRecorder() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }

    public void updateTv(){
        double amp = getAmplitudeEMA();
        Log.d("Log ", amp + " dB");


        if(amp >= DECIBEL_LIMIT && this.previousMeasure < DECIBEL_LIMIT) {
            this.startAlert();
        } else if ( this.previousMeasure >= DECIBEL_LIMIT && amp < DECIBEL_LIMIT ) {
            this.finishAlert();
        }

        this.previousMeasure = amp;
    }
    public double soundDb(double ampl){
        return  20 * Math.log10(getAmplitudeEMA() / ampl);
    }
    public double getAmplitude() {
        if (mRecorder != null) {
            return  (mRecorder.getMaxAmplitude());
        } else {
            return 0;
        }
    }
    public double getAmplitudeEMA() {
        double amp =  getAmplitude();
        mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA;
        return mEMA;
    }
}
