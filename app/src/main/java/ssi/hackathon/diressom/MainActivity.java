package ssi.hackathon.diressom;

import android.media.MediaRecorder;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TransitionDrawable backgroundDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaRecorder recorder = new MediaRecorder();


        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile("deu_bom");
        try {
            recorder.prepare();
        } catch (IOException e){

        }

        recorder.start();   // Recording is now started
             int x = recorder.getMaxAmplitude();
                    if(x>1) {
                        startAlert();
                    } else {
                        finishAlert();
                    }

        recorder.stop();
        recorder.reset();   // You can reuse the object by going back to setAudioSource() step
        recorder.release(); // Now the object cannot be reused

        View layout = findViewById(R.id.background);
        this.backgroundDrawable = (TransitionDrawable) layout.getBackground();
    }

    protected void onStart() {
        super.onStart();
        startAlert();
    }

    private void startAlert() {
        backgroundDrawable.startTransition(2000);
    }

    private void finishAlert() {
        backgroundDrawable.reverseTransition(2000);
    }
}
