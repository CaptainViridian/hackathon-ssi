package ssi.hackathon.diressom;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private TransitionDrawable backgroundDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
