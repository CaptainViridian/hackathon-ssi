package ssi.hackathon.diressom;

import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MediaRecorder recorder = new MediaRecorder();


        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(PATH_NAME);
        try {
            recorder.prepare();
        } catch (IOException e){

        }

        recorder.start();   // Recording is now started
             int x = recorder.getMaxAmplitude();
                    if(x>0) {
                        System.out.println("troca cor");
                    }

        recorder.stop();
        recorder.reset();   // You can reuse the object by going back to setAudioSource() step
        recorder.release(); // Now the object cannot be reused


    }
}
